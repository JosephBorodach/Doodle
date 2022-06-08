package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.Undoable;

import java.util.*;
import java.util.function.Function;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class DocumentStoreImpl implements DocumentStore { 
    private HashTableImpl<URI, DocumentImpl> table;
    private TrieImpl<Document> trie; //DocumentImpl
    private StackImpl<Undoable> stack;
    public DocumentStoreImpl() {
        this.table = new HashTableImpl<>();
        this.trie = new TrieImpl<>();
        this.stack = new StackImpl<>();
    }

    /**
     * When a Document is Added to the DocumentStore
     * 1. You must go through the document and create a java.util.HashMap that will be stored in the Document object
     *     that maps all the words in the document to the number of times the word appears in the document.
     *     a. Be sure to ignore all characters that are not a letter or a number!
     *     b. This will help you both for implementing Document.wordCount and also for its interactions with the Trie
     * 2. For each word that appears in the Document, add the Document to the Value collection at the appropriate Node in the Trie
     *     a. The Trie stores collections of Documents at each node, not individual documents!
     *
     * @param input the document being put - If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the previous doc. If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @throws IOException if there is an issue reading input
     * @throws IllegalArgumentException if uri or format are null
     */
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
        if (uri == null || uri.toASCIIString().isBlank()) {
            throw new IllegalArgumentException();
        }
        DocumentImpl originalDocument = this.table.get(uri);
        if (input == null) {
            deleteDocument(uri);
            return returnValue(originalDocument);
        }
        if (format == null) {
            throw new IllegalArgumentException();
        }
        byte[] byteConverter = input.readAllBytes();
        DocumentImpl newDocument = this.newDocument(uri, format, byteConverter);
        Function <URI, Boolean> undo = (URI currentUri) -> { //1st address the words in trie; 2nd address the documents hashTable
            trieDeleteAndPut(newDocument, originalDocument);
            this.table.put(uri, originalDocument); //Return the hashTable to its original state
            return true;
        };
        this.stack.push(new GenericCommand<>(uri, undo));
        trieDeleteAndPut(originalDocument, newDocument);
        this.table.put(uri, newDocument);
        return returnValue(originalDocument);
    }

    /**
     * @return
     * This is very easy to read
     */
    private DocumentImpl newDocument(URI uri, DocumentFormat format, byte[] byteConverter) {
        DocumentImpl newDocument;
        if (format.equals(DocumentStore.DocumentFormat.TXT)) {
            newDocument = new DocumentImpl(uri, new String(byteConverter));
        } else {
            newDocument = new DocumentImpl(uri, byteConverter);
        }
        this.table.put(uri, newDocument);
        return newDocument;
    }

    /**
     * @return if there is no previous doc at the given URI, return 0.
     * If there is a previous doc, return the hashCode of the previous doc. 
     *      HashTable - return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */ 
    private int returnValue(DocumentImpl document) {
        return document != null ? document.hashCode() : 0;
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    public Document getDocument(URI uri) {
        return this.table.get(uri); //(Document)
    }

    /**
     * When a Document is Deleted from DocumentStore
     * 1. You must delete all references to it within all parts of the Trie.
     * 2. If the Document being removed is that last one at that node in the Trie, you must delete it and all ancestors between it
     *     and the closest ancestor that has at least one document in its Value collection.
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    public boolean deleteDocument(URI uri) {
        DocumentImpl deletedDocument = this.table.put(uri, null); //Delete document and save the delete document
        Function <URI, Boolean> function = documentUri -> {
            triePut(deletedDocument);
            this.table.put(uri, deletedDocument);
            return true;
        };
        this.stack.push(new GenericCommand<>(uri, function));
        trieDelete(deletedDocument);
        return uri != null && deletedDocument != null;
    }

    /**
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     * All Undo logic must now also deal with updating the Trie appropriately.
     * Because undo must now deal with an undo action affecting multiple documents at once,
     * the command API has been changed to include the classes listed below.
     * Please see the comments on those classes.
     */
    public void undo() throws IllegalStateException {
        if (this.stack.peek() == null) {
            throw new IllegalStateException();
        }
        this.stack.pop().undo();
    }

    /**
     * If a user calls DocumentStore.undo(URI),then your DocumentStore must undo the last command on the stack
     * that was done on the Document whose key is the given URI, without having any permanent effects on any commands
     * that are on top of it in the command stack.
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    @SuppressWarnings("unchecked")
    public void undo(URI uri) throws IllegalStateException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        StackImpl<Undoable> temporaryStack = new StackImpl<>();
        GenericCommand<URI> lastGenericCommand = null;
        CommandSet<URI> lastCommandSet = null;
        while (this.stack.peek() != null && lastGenericCommand == null && lastCommandSet == null) {
            Undoable command = this.stack.pop(); //Make sure that you don't need to be using an iterator because there is no containsTarget
            if (command instanceof GenericCommand && ((GenericCommand<URI>)command).getTarget().equals(uri)) { //I am not sure that it is necessary to use the equals method
                lastGenericCommand = (GenericCommand<URI>)command;
                lastGenericCommand.undo(); //Make sure that it is ok that CommandSet<URI> can be defined as <URI>!
            } else if (command instanceof CommandSet && ((CommandSet<URI>)command).containsTarget(uri)) {
                lastCommandSet = (CommandSet<URI>)command;
                lastCommandSet.undo(uri);
                if (lastCommandSet.size() != 0) {
                    temporaryStack.push(command);
                }
            } else {
                temporaryStack.push(command);
            }
        }
        while (temporaryStack.peek() != null) { //Must leave stack in reasonable state in case user try/catches
            this.stack.push(temporaryStack.pop());
        }
        if (lastGenericCommand == null && lastCommandSet == null) {
            throw new IllegalStateException();
        }
    }

    private String replacer(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\s+]", "").toUpperCase(); //2x check that this should also say \\s+ and also that it doesn't matter if I use Uppercase or lowercase
    }

    /**
     * Retrieve all documents whose text contains the given keyword.
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword) {
        this.nullChecker(keyword);
        String cleanKeyword = replacer(keyword); //List<Document> returnList = this.trie.getAllSorted(cleanKeyword, this.searchCompare(cleanKeyword));
        return this.trie.getAllSorted(cleanKeyword, this.searchCompare(cleanKeyword));
    }

    //2x check that this is ok!
    private Comparator<Document> searchCompare(String keyword) { //return Comparator.comparingInt((Document document) -> document.wordCount(keyword));
        return (document1, document2) -> {
            int counter1 = document1.wordCount(keyword);
            int counter2 = document2.wordCount(keyword);
            return counter2 - counter1;
        };
    }

    /**
     * Retrieve all documents whose text starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix) {
        this.nullChecker(keywordPrefix);
        String cleanKeyword = replacer(keywordPrefix);
        return this.trie.getAllWithPrefixSorted(cleanKeyword, this.searchByPrefixCompare(cleanKeyword));
    }

    private Comparator<Document> searchByPrefixCompare(String keyword) {
        return (document1, document2) -> {
            int counter1 = 0;
            for (String eachWord : document1.getWords()) {
                if (eachWord.startsWith(keyword)) {
                    counter1++;
                }
            }
            int counter2 = 0;
            for (String eachWord : document2.getWords()) {
                if (eachWord.startsWith(keyword)) {
                    counter2++;
                }
            } //return Integer.compare(counter2, counter1);
            return counter2 - counter1;
        };
    }

    /**
     * Completely remove any trace of any document which contains the given keyword
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    //Throwing error when passed "not there"!
    public Set<URI> deleteAll(String keyword) {
        this.nullChecker(keyword); // String cleanKeyword = replacer(keyword);
        Set<URI> deletedSet = new HashSet<>();
        CommandSet<URI> commandSet = new CommandSet<>();
        for (Document eachDocument : this.trie.deleteAll(replacer(keyword))) {
            URI key = eachDocument.getKey();
            Function <URI, Boolean> function = documentUri -> {
                this.table.put(key, (DocumentImpl) eachDocument);
                triePut(eachDocument);
                return true;
            };
            commandSet.addCommand(new GenericCommand<>(key, function));
            trieDelete(eachDocument);
            this.table.put(key, null);
            deletedSet.add(key);
        }
        this.stack.push(commandSet);
        return deletedSet;
    }

    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE INSENSITIVE.
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        this.nullChecker(keywordPrefix); //String cleanKeyword = replacer(keywordPrefix);
        Set<URI> deletedSet = new HashSet<>();
        CommandSet<URI> commandSet = new CommandSet<>();
        for (Document eachDocument : trie.deleteAllWithPrefix(replacer(keywordPrefix))) {
            URI key = eachDocument.getKey();
            Function <URI, Boolean> function = documentUri -> {
                this.table.put(key, (DocumentImpl)eachDocument);
                triePut(eachDocument);
                return true;
            };
            commandSet.addCommand(new GenericCommand<>(key, function));
            trieDelete(eachDocument);
            this.table.put(key, null);
            deletedSet.add(key);
        }
        this.stack.push(commandSet);
        return deletedSet;
    }

    private void nullChecker(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
    }

    private void trieDeleteAndPut (DocumentImpl deleteDocument, DocumentImpl putDocument) {
        if (deleteDocument != null) {
            for (String eachWord : deleteDocument.getWords()) {
                this.trie.delete(eachWord, deleteDocument);
            }
        }
        if (putDocument != null) {
            for (String eachWord : putDocument.getWords()) {
                this.trie.put(eachWord, putDocument);
            }
        }
    }

    private void triePut (DocumentImpl putDocument) {
        if (putDocument != null) {
            for (String eachWord : putDocument.getWords()) {
                this.trie.put(eachWord, putDocument);
            }
        }
    }

    private void triePut (Document putDocument) {
        if (putDocument != null) {
            for (String eachWord : putDocument.getWords()) {
                this.trie.put(eachWord, putDocument);
            }
        }
    }

    private void trieDelete (DocumentImpl deleteDocument) {
        if (deleteDocument != null) {
            for (String eachWord : deleteDocument.getWords()) {
                this.trie.delete(eachWord, deleteDocument);
            }
        }
    }

    private void trieDelete (Document deleteDocument) {
        if (deleteDocument != null) {
            for (String eachWord : deleteDocument.getWords()) {
                this.trie.delete(eachWord, deleteDocument);
            }
        }
    }
}