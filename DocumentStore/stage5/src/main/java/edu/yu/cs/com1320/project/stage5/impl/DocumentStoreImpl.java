package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;

import java.io.*;
import java.util.*;
import java.net.URI;
import static java.lang.System.nanoTime;

public class DocumentStoreImpl implements DocumentStore {
    private final BTree<URI, Document> bTree;
    private final MinHeap<MockDocument> heap;
    private final Set<MockDocument> memory;
    private final Stack<Undoable> stack;
    private final Trie<URI> trie;
    private int docCount;
    private int byteCount;
    private int maxDocCount;
    private int maxDocBytes;
    private long time;

    public DocumentStoreImpl(File file) {
        this.bTree = new BTreeImpl<>();
        this.heap = new MinHeapImpl<>();
        this.memory = new HashSet<>();
        this.stack = new StackImpl<>();
        this.trie = new TrieImpl<>();
        this.docCount = 0;
        this.byteCount = 0;
        this.maxDocCount = -1;
        this.maxDocBytes = -1;
        this.time = 0;
        this.bTree.setPersistenceManager(new DocumentPersistenceManager(file));
    }

    public DocumentStoreImpl() {
        this(null);
    }

    /**
     * @param input the document being put - If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the previous doc. If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @throws IOException if there is an issue reading input
     * @throws IllegalArgumentException if uri or format are null, or if uri is blank
     */
    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
        if (uri == null || uri.toASCIIString().isBlank() || format == null) {
            throw new IllegalArgumentException();
        }
        Document oldDoc = this.bTree.get(uri);
        if (input == null) {
            this.deleteDocument(uri);
        } else {
            byte[] b = input.readAllBytes();
            Document doc = format != DocumentFormat.TXT ? new DocumentImpl(uri, b) : new DocumentImpl(uri, new String(b), null);
            this.putDoc(uri, doc);
            Undoable cmd = new GenericCommand<>(uri, (URI) -> this.putDoc(uri, oldDoc));
            this.stack.push(cmd);
        }
        return oldDoc != null ? oldDoc.hashCode() : 0;
    }

    /**
     * There's no case where the private putDoc is called and the private deleteDoc is not called as well. If there is no previous document, this simply will be ineffective.
     * @param uri
     * @param doc
     * @return
     */
    private boolean putDoc(URI uri, Document doc) {
        this.time = nanoTime();
        this.deleteDoc(uri, this.bTree.get(uri));
        if (doc != null) {
            this.bTree.put(uri, doc);
            for (String word : doc.getWords()) {
                this.trie.put(word, uri);
            }
            doc.setLastUseTime(this.time);
            this.heapPut(uri, doc, this.time);
        }
        this.checkCounters();
        return true;
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    @Override
    public boolean deleteDocument(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        Document doc = this.bTree.get(uri);
        Undoable cmd = new GenericCommand<>(uri, (URI) -> this.putDoc(uri, doc));
        this.stack.push(cmd);
        return this.deleteDoc(uri, doc);
    }

    /**
     * By deleteDoc also dealing with hashTable, the method is completely independent of putDoc.
     * @param uri
     * @param doc
     * @return true if the document is deleted, false if no document exists with that URI
     */
    private boolean deleteDoc(URI uri, Document doc) {
        if (doc == null) {
            return false;
        }
        this.time = nanoTime();
        doc.setLastUseTime(Long.MIN_VALUE);
        for (String word : doc.getWords()) {
            this.trie.delete(word, uri);
        }
        MockDocument mock = new MockDocument(uri, Long.MIN_VALUE, 0);
        this.heap.insert(mock);
        this.heap.remove();
        doc.setLastUseTime(this.time);
        if (this.memory.remove(mock)) {
            int docSize = doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length;
            this.byteCount-= docSize;
            this.docCount--;
        }
        this.bTree.put(uri, null);
        return true;
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     * @throws IllegalArgumentException if uri is null
     * checkCounters() method is called because calling getDocument could result in bringing a document back from the disk.
     */
    @Override
    public Document getDocument(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        this.time = nanoTime();
        Document document = this.bTree.get(uri);
        if (document != null) {
            document.setLastUseTime(this.time);
            this.heapPut(uri, document, this.time);
        }
        this.checkCounters();
        return document;
    }

    /**
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    @Override
    public void undo() throws IllegalStateException {
        if (this.stack.peek() == null) {
            throw new IllegalStateException();
        }
        this.time = nanoTime();
        this.stack.pop().undo();
    }

    /**
     * All Undo logic must now also deal with moving things to/from disk in the BTree.
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     * @throws IllegalArgumentException
     * @return null unless an exception is thrown
     */
    @Override
    @SuppressWarnings("unchecked")
    public void undo(URI uri) throws IllegalStateException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        this.time = nanoTime();
        boolean undone = false;
        StackImpl<Undoable> tempStack = new StackImpl<>();
        while (!undone && this.stack.peek() != null) {
            Undoable command = this.stack.pop();
            if (command instanceof GenericCommand && ((GenericCommand<URI>)command).getTarget().equals(uri)) {
                command.undo();
                undone = true;
            } else if (command instanceof CommandSet && ((CommandSet<URI>) command).containsTarget(uri)) {
                ((CommandSet<URI>)command).undo(uri);
                undone = true;
                if (((CommandSet<URI>) command).size() > 0) {
                    tempStack.push(command);
                }
            } else {
                tempStack.push(command);
            }
        }
        while (tempStack.peek() != null) {
            this.stack.push(tempStack.pop());
        }
        if (!undone) {
            throw new IllegalStateException();
        }
    }

    /**
     * @param keyword
     * @throws IllegalArgumentException
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    @Override
    public List<Document> search(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException();
        }
        String clean = this.replacer(keyword);
        List<URI> list = this.trie.getAllSorted(clean, (uri1, uri2) -> this.bTree.get(uri2).wordCount(clean) - this.bTree.get(uri1).wordCount(clean));
        return this.heapRecorder(list);
    }

    /**
     * String for cleanKeyword - Need to declare a new stirng to have it be final for the lamda
     * @param keywordPrefix
     * @throws IllegalArgumentException
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        if (keywordPrefix == null) {
            throw new IllegalArgumentException();
        }
        String clean = this.replacer(keywordPrefix);
        List<URI> list = this.trie.getAllWithPrefixSorted(clean, Comparator.comparingInt(uri -> this.wordCount(clean, uri)));
        return this.heapRecorder(list);
    }

    /**
     * startswith - Tests if this string starts with the specified prefix.
     * @param keyword
     * @param uri
     * @return
     */
    private int wordCount(String keyword, URI uri) {
        int count = 0;
        Document d = this.bTree.get(uri);
        for (String word : d.getWords()) {
            if (word.startsWith(keyword)) {
                count++;
            }
        }
        return count;
    }

    private List<Document> heapRecorder(List<URI> list) {
        this.time = nanoTime();
        List<Document> returnList = new ArrayList<>();
        for (URI uri : list) {
            Document doc = this.getDocument(uri);
            doc.setLastUseTime(this.time);
            returnList.add(doc);
            MockDocument mock = this.heapPut(uri, doc, this.time);
            this.heap.reHeapify(mock);
        }
        this.checkCounters();
        return returnList;
    }

    /**
     * @param keyword
     * @throws IllegalArgumentException
     * @return a Set of URIs of the documents that were deleted.
     */
    @Override
    public Set<URI> deleteAll(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException();
        }
        String clean = this.replacer(keyword);
        Set<URI> list = this.trie.deleteAll(clean);
        return this.deleteAll(list);
    }

    /**
     * @param keywordPrefix
     * @throws IllegalArgumentException
     * @return a Set of URIs of the documents that were deleted.
     */
    @Override
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        if (keywordPrefix == null) {
            throw new IllegalArgumentException();
        }
        String clean = this.replacer(keywordPrefix);
        Set<URI> list = this.trie.deleteAllWithPrefix(clean);
        return this.deleteAll(list);
    }

    /**
     * Even if nothing was actually deleted, still push artificial delete commandSet to the stack
     * @param toDelete
     * @return
     */
    private Set<URI> deleteAll(Set<URI> toDelete) {
        this.time = nanoTime();
        Set<URI> deletedSet = new HashSet<>();
        CommandSet<URI> cmdSet = new CommandSet<>();
        for (URI uri : toDelete) {
            Document doc = this.bTree.get(uri);
            GenericCommand<URI> cmd = new GenericCommand<>(uri, (URI) -> this.putDoc(uri, doc));
            cmdSet.addCommand(cmd);
            this.deleteDoc(uri, doc);
            deletedSet.add(uri);
        }
        this.stack.push(cmdSet);
        return deletedSet;
    }

    /**
     * Set maximum number of documents that may be stored
     * @param limit
     * @throws IllegalArgumentException if limit is less than 0
     */
    @Override
    public void setMaxDocumentCount(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException();
        }
        this.maxDocCount = limit;
        this.checkCounters();
    }

    /**
     * Set maximum number of bytes of memory that may be used by all the documents in memory combined
     * @param limit
     * @throws IllegalArgumentException if limit is less than 0
     */
    @Override
    public void setMaxDocumentBytes(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException();
        }
        this.maxDocBytes = limit;
        this.checkCounters();
    }

    private void checkCounters() {
        while (((this.maxDocBytes != -1) && (this.byteCount > this.maxDocBytes)) || ((this.maxDocCount != -1) && (this.docCount > this.maxDocCount))) {
            this.deleteLeastRecentlyUsed();
        }
    }

    /**
     * When a document has to be kicked out of memory, instead of it being deleted completely,
     * it will be written to disk via a call to BTree.moveToDisk.
     * Delete from the stack, since it is nonsensical to do an UNDO on a document that no longer exists in memory;
     * It's URI should NOT be deleted from the Trie since removing it would mean we would never have that doc as a search result ever again.
     */
    private void deleteLeastRecentlyUsed() {
        MockDocument deletedDoc = this.heap.remove();
        if (this.memory.remove(deletedDoc)) {
            try {
                URI uri = deletedDoc.getUri();
                this.bTree.moveToDisk(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.byteCount -= deletedDoc.getSize();
            this.docCount--;
        }
    }

    /**
     * @param uri
     * @param d
     * @param lastUseTime
     * @return
     */
    private MockDocument heapPut(URI uri, Document d, long lastUseTime) {
        int docSize = d.getDocumentTxt() != null ? d.getDocumentTxt().getBytes().length : d.getDocumentBinaryData().length;
        MockDocument mockDoc = new MockDocument(uri, lastUseTime, docSize);
        if (mockDoc != null) {
            this.heap.insert(mockDoc);
            if (this.memory.add(mockDoc)) {
                this.docCount++;
                this.byteCount += docSize;
            }
        }
        return mockDoc;
    }

    /**
     * @param s
     * @return
     */
    private String replacer(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\s+]", "").toUpperCase();
    }

    private class MockDocument implements Comparable<MockDocument> {
        private final URI uri;
        private final int size;
        private final long time;
        private MockDocument (URI uri, long time, int size) {
            this.uri = uri;
            this.time = time;
            this.size = size;
        }

        private URI getUri () {
            return this.uri;
        }

        private long getLastUseTime() {
            return this.time;
        }

        private int getSize () {
            return this.size;
        }

        @Override
        public int hashCode() {
            return this.uri.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof MockDocument)) {
                return false;
            }
            return ((MockDocument)o).getUri().equals(this.uri);
        }

        @Override
        public int compareTo(MockDocument doc) {
            return Long.compare(this.getLastUseTime(), doc.getLastUseTime());
        }
    }
}