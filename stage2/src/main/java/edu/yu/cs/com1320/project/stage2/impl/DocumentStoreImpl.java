package edu.yu.cs.com1320.project.stage2.impl;

import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;

import java.io.IOException;
import java.net.URI; 
import java.io.InputStream;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore { 
    private HashTableImpl<URI, Document> table = new HashTableImpl<>();
    private StackImpl stack = new StackImpl();

    /**
     * @param input the document being put - If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the previous doc. If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @throws IOException if there is an issue reading input
     * @throws IllegalArgumentException if uri or format are null
     *
     */
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
        DocumentImpl document = (DocumentImpl) getDocument(uri);
        Function <URI, Boolean> function = documentURI -> {
            this.table.put(uri, document);
            return true;
        };
        this.stack.push(new Command(uri, function));
        if (uri == null || uri.toASCIIString().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (input == null) {
            return returnValue((DocumentImpl)this.table.put(uri, null));
        }
        if (format == null) {
            throw new IllegalArgumentException();
        }
        byte[] btyeConverter = input.readAllBytes();
        if (format.equals(DocumentStore.DocumentFormat.TXT)) {
            return returnValue((DocumentImpl)this.table.put(uri, new DocumentImpl(uri, new String(btyeConverter))));
        }
        return returnValue((DocumentImpl)this.table.put(uri, new DocumentImpl(uri, btyeConverter)));
    }

    /**
     * @return if there is no previous doc at the given URI, return 0.
     * If there is a previous doc, return the hashCode of the previous doc. 
     *      HashTable - return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */ 
    private int returnValue(DocumentImpl document) {
        if (document != null) {
            return document.hashCode();
        } 
        return 0;
    }
    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    public Document getDocument(URI uri) {
        return (Document)this.table.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    public boolean deleteDocument(URI uri) {
        DocumentImpl document = (DocumentImpl) getDocument(uri);
        Function <URI, Boolean> function = documentUri -> {
            this.table.put(uri, document);
            return true;
        };
        this.stack.push(new Command(uri, function));
        if (uri == null || this.table.get(uri) == null) {
            return false;
        }
        if (this.table.put(uri, null) != null) {
            return true;
        }
        return false;
    }

    /**
     * A lambda expression is a block of code that you can pass around so it can be executed later, once or multiple times
     * There is only one thing you can do with a lambda expression: put it in a variable whose type is a functional interface, so that it is converted to an instance of that interface.
     * Runnable - Runs an action without arguments or return value
     * Undo must be achieved by DocumentStore calling the Command.undo method on the Command that represents the
     * action to be undone.
     * DocumentStore may not implement the actual undo logic itself, although it must manage the command stack and determine which undo to call on which Command.
     * Instructions:
     * 1) There are two cases you must deal with to undo a call to DocumentStore.putDocument:
     *      a. The call to putDocument which is being undone added a brand new Document to the DocumentStore
     *      b. The call to putDocument which is being undone resulted in overwriting an existing Document with the same URI in the DocumentStore
     * 2) To undo a call to DocumentStore.deleteDocument, you must put whatever was deleted back into the DocumentStore exactly as it was before
     * 3) DO NOT add any new commands to the command stack in your undo logic. In other words, the undo itself is not
     * “recorded” as a command on the command stack, rather it simply causes the undoing of some pre-existing command. Once the undo process is completely done, there is no record at all of the fact that an undo took place.
     * If a user calls DocumentStore.undo(), then your DocumentStore must undo the last command on the stack
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException {
        if (this.stack.peek() == null) {
            throw new IllegalStateException();
        }
        Command lastAction = (Command) this.stack.pop();
        lastAction.undo();
    }

    /**
     * If a user calls DocumentStore.undo(URI),then your DocumentStore must undo the last command on the stack
     * that was done on the Document whose key is the given URI, without having any permanent effects on any commands
     * that are on top of it in the command stack.
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    public void undo(URI uri) throws IllegalStateException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        if (this.stack.peek() == null) {
            throw new IllegalStateException();
        }
        StackImpl temporaryStack = new StackImpl();
        Command currentElement = (Command) this.stack.pop();
        while (currentElement != null && !(currentElement.getUri().equals(uri))) {
            temporaryStack.push(currentElement);
            currentElement = (Command) this.stack.pop();
        }

        if (currentElement != null && currentElement.getUri().equals(uri)) {
            currentElement.undo();
            while (temporaryStack.peek() != null) { //Make sure that using size here is ok and that I don't need to worry about nulls
                this.stack.push((Command) temporaryStack.pop());
            }
        } else { //Must leave stack in reasonable state in case user try/catches
            while (temporaryStack.peek() != null) {
                this.stack.push((Command) temporaryStack.pop());
            }
            throw new IllegalStateException();
        }
    }
}