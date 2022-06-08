package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.impl.HashTableImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class DocumentStoreImpl implements DocumentStore {
    private HashTableImpl<URI, Document> table = new HashTableImpl<>(); 

    /**
     * @param input the document being put - If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the previous doc. 
     * @throws IOException if there is an issue reading input
     * @throws IllegalArgumentException if uri or format are null
     */
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
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
     * @param uri unique identifier for the document
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
     * If a user calls deleteDocument, or putDocument with null as the value, 
     *      you must completely remove any/all vestiges of the Document with the given URI 
     *      from your hash table, including any objects created to house it. 
     *      In other words, it should no longer exist anywhere in the array, or chained lists, of your hash table.
     */
    public boolean deleteDocument(URI uri) {
        if (uri == null || this.table.get(uri) == null) {
            return false;         
        }
        if (this.table.put(uri, null) != null) {
            return true;
        }
        return false;
    }
}