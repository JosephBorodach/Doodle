package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.Trie;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.Undoable;

import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.nanoTime;
import java.net.URI;
import java.util.*;

public class DocumentStoreImpl implements DocumentStore {
    private final MinHeap<Document> heap;
    private final Stack<Undoable> stack;
    private final HashTable<URI, Document> table;
    private final Trie<Document> trie;
    private int currentDocumentCount;
    private int currentByteCount;
    private int maxDocumentCount;
    private int maxDocumentBytes;
    private long time;
    public DocumentStoreImpl() {
        this.heap = new MinHeapImpl<>();
        this.stack = new StackImpl<>();
        this.table = new HashTableImpl<>();
        this.trie = new TrieImpl<>();
        this.currentDocumentCount = 0;
        this.currentByteCount = 0;
        this.maxDocumentCount = -1;
        this.maxDocumentBytes = -1;
        this.time = 0;
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
        if (uri == null || uri.toASCIIString().isBlank()) {
            throw new IllegalArgumentException();
        }
        Document original = this.table.get(uri);
        if (input == null) {
            this.deleteDocument(uri);
            return this.returnVal(original);
        } else if (format == null) {
            throw new IllegalArgumentException();
        }
        byte[] bytes = input.readAllBytes();
        this.stack.push(new GenericCommand<>(uri, (URI) -> this.putDocument(uri, original)));
        Document newDoc = format.equals(DocumentFormat.TXT) ? new DocumentImpl(uri, new String(bytes)) : new DocumentImpl(uri, bytes);
        this.putDocument(uri, newDoc);
        return this.returnVal(original);
    }

    /**
     * Just to make putDocument easier to read
     * @param doc
     * @return
     */
    private int returnVal(Document doc) {
        return doc != null ? doc.hashCode() : 0;
    }

    /**
     * The return type is boolean in order that in the lambda can just return the same return value.
     * @param uri
     * @param document
     * @return
     */
    private boolean putDocument(URI uri, Document document) {
        this.time = nanoTime();
        deleteDocument(uri, this.table.get(uri));
        if (document != null) {
            document.setLastUseTime(this.time);
            this.table.put(uri, document);
            this.addSpace(document);
            this.triePut(document);
            this.heap.insert(document);
            this.currentByteCount += this.documentSize(document);
            this.currentDocumentCount++;
        }
        return true;
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    @Override
    public boolean deleteDocument(URI uri) {
        this.time = nanoTime();
        Document document = this.table.get(uri);
        this.stack.push(new GenericCommand<>(uri, (URI) -> this.putDocument(uri, document)));
        return this.deleteDocument(uri, document);
    }

    /**
     * @param uri
     * @param document
     * @return true if the document is deleted, false if no document exists with that URI
     */
    private boolean deleteDocument(URI uri, Document document) {
        if (document != null) {
            document.setLastUseTime(Long.MIN_VALUE);
            this.table.put(uri, null);
            this.trieDelete(document);
            this.heap.reHeapify(document);
            this.heap.remove();
            document.setLastUseTime(this.time);
            this.currentByteCount -= this.documentSize(document);
            this.currentDocumentCount--;
            return true;
        }
        return false;
    }

    /**
     * 1st if statement: If it is the key, then don't add it to the temp stack, effectively deleting it from the actual stack.
     * Regarding the iterator, see slides https://drive.google.com/file/d/1HVPfQ6HNwQ9feES_aU3nQkCRDea5b1OJ/view?usp=sharing
     */
    private void deleteLeastRecentlyUsed() {
        this.time = nanoTime();
        Document deletedDocument = this.heap.remove();
        this.heap.insert(deletedDocument);
        URI uri = deletedDocument.getKey();
        this.deleteDocument(uri, deletedDocument);
        StackImpl<Undoable> temporaryStack = new StackImpl<>();
        while (this.stack.peek() != null) {
            Undoable command = this.stack.pop();
            if ((command instanceof GenericCommand) && (!((GenericCommand<URI>)command).getTarget().equals(uri))) {
                temporaryStack.push(command);
            } else if (command instanceof CommandSet && ((CommandSet<URI>)command).containsTarget(uri)) {
                Iterator<GenericCommand<URI>> iterator = ((CommandSet<URI>) command).iterator();
                while(iterator.next().getTarget().equals(uri) == false);
                iterator.remove();
                if (((CommandSet<URI>) command).size() > 0) {
                    temporaryStack.push(command);
                }
            }
        }
        this.restack(temporaryStack);
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri) {
        Document document = this.table.get(uri);
        if (document != null) {
            document.setLastUseTime(nanoTime());
            this.heap.reHeapify(document);
        }
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
        boolean located = false;
        StackImpl<Undoable> temporaryStack = new StackImpl<>();
        while (this.stack.peek() != null && !located) {
            Undoable command = this.stack.pop();
            if (command instanceof GenericCommand && ((GenericCommand<URI>)command).getTarget().equals(uri)) {
                command.undo();
                located = true;
            } else if (command instanceof CommandSet && ((CommandSet<URI>)command).containsTarget(uri)) {
                ((CommandSet<URI>)command).undo(uri);
                located = true;
                if (((CommandSet<URI>)command).size() > 0) {
                    temporaryStack.push(command);
                }
            } else {
                temporaryStack.push(command);
            }
        }
        this.restack(temporaryStack);
        if (located == false) {
            throw new IllegalStateException();
        }
    }

    private void restack(StackImpl<Undoable> temporary) {
        while (temporary.peek() != null) {
            this.stack.push(temporary.pop());
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
        String cleanKeyword = this.replacer(keyword);
        List<Document> list = this.trie.getAllSorted(cleanKeyword, (document1, document2) -> document2.wordCount(cleanKeyword) - document1.wordCount(cleanKeyword));
        return this.heapRecorder(list);
    }

    /**
     * @param keywordPrefix
     * @throws IllegalArgumentException
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        if (keywordPrefix == null) {
            throw new IllegalArgumentException();
        }
        String cleanKeyword = this.replacer(keywordPrefix);
        List<Document> list = this.trie.getAllWithPrefixSorted(cleanKeyword, new Comparator<>() {
            @Override
            public int compare(Document document1, Document document2) {
                return this.wordCount(cleanKeyword, document1) - this.wordCount(cleanKeyword, document2);
            }
            private int wordCount(String keyword, Document document) {
                int counter = 0;
                for (String eachWord : document.getWords()) {
                    if (eachWord.startsWith(keyword)) {
                        counter++;
                    }
                }
                return counter;
            }
        });
        return this.heapRecorder(list);
    }

    /**
     * @param list
     * @return
     */
    private List<Document> heapRecorder(List<Document> list) {
        this.time = nanoTime();
        for (Document document : list) {
            document.setLastUseTime(nanoTime());
            this.heap.reHeapify(document);
        }
        return list;
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
        String cleanKeyword = this.replacer(keyword);
        Set<Document> list = this.trie.deleteAll(cleanKeyword);
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
        String cleanKeyword = this.replacer(keywordPrefix);
        Set<Document> list = this.trie.deleteAllWithPrefix(cleanKeyword);
        return this.deleteAll(list);
    }

    private Set<URI> deleteAll(Set<Document> toDelete) {
        this.time = nanoTime();
        Set<URI> deleted = new HashSet<>();
        CommandSet<URI> commandSet = new CommandSet<>();
        for (Document document : toDelete) {
            URI uri = document.getKey();
            commandSet.addCommand(new GenericCommand<>(uri, (URI) -> this.putDocument(uri, document)));
            this.deleteDocument(uri, document);
            deleted.add(uri);
        }
        this.stack.push(commandSet);
        return deleted;
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
        this.maxDocumentCount = limit;
        while (this.currentDocumentCount > limit) {
            deleteLeastRecentlyUsed();
        }
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
        this.maxDocumentBytes = limit;
        while (this.currentByteCount > limit) {
            deleteLeastRecentlyUsed();
        }
    }

    /**
     * @param doc
     */
    private void addSpace(Document doc) {
        int size = this.documentSize(doc);
        if ((this.maxDocumentCount != -1 && this.maxDocumentCount < 1) || (this.maxDocumentBytes != -1 && size > this.maxDocumentBytes)) {
            throw new IllegalArgumentException(); //IllegalStateException
        }
        while (((this.maxDocumentBytes != -1) && (this.currentByteCount + size > this.maxDocumentBytes)) || ((this.maxDocumentCount != -1) && (this.currentDocumentCount + 1 > this.maxDocumentCount))) {
            deleteLeastRecentlyUsed();
        }
    }

    /**
     * @param doc
     * @return
     */
    private int documentSize(Document doc) {
        return doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length;
    }

    /**
     * @param document
     */
    private void triePut(Document document) {
        for (String eachWord : document.getWords()) {
            this.trie.put(eachWord, document);
        }
    }

    /**
     * @param doc
     */
    private void trieDelete(Document doc) {
        for (String word : doc.getWords()) {
            this.trie.delete(word, doc);
        }
    }

    /**
     * @param s
     * @return
     */
    private String replacer(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\s+]", "").toUpperCase();
    }
}