package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;

import java.util.*;
import java.net.URI;

/**
 * There's need to initialize the lastUseTime variable to anything in the Document Constructor
 * Transient: a variables modifier used in serialization. At the time of serialization,
 * if we don’t want to save value of a particular variable in a file, then we use transient keyword.
 * When JVM comes across transient keyword, it ignores original value of the variable and save default value of that variable data type.
 */
public class DocumentImpl implements Document {
    private final byte[] binaryData;
    private Map<String, Integer> count = new HashMap<>();
    private final String text;
    private final URI uri;
    private transient long lastUseTime;

    /**
     * @param uri the unique identifier of the document to delete
     * @throws IllegalArgumentException if either argument is null or empty/blank
     */
    public DocumentImpl(URI uri, String txt, Map<String, Integer> wordCountMap) {
        if (uri == null || txt == null || uri.toASCIIString().isBlank() || txt.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.uri = uri;
        this.text = txt;
        this.binaryData = null;
        if (wordCountMap == null) {
            for (String word : this.replacer(txt).trim().split("\\s+")) {
                Integer i = this.count.get(this.replacer(word));
                this.count.put(word, i == null ? 1 : i + 1);
            }
        } else {
            this.count = wordCountMap;
        }
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @throws IllegalArgumentException if either argument is null or empty/blank
     */
    public DocumentImpl(URI uri, byte[] binaryData) {
        if (uri == null || binaryData == null || uri.toASCIIString().isBlank() || binaryData.length == 0) {
            throw new IllegalArgumentException();
        }
        this.uri = uri;
        this.binaryData = binaryData;
        this.text = null;
    }

    /**
     * @return content of text document
     * Should be the original text that was read off the original inputstream, with no modifications at all.
     */
    @Override
    public String getDocumentTxt() {
        if (this.binaryData != null) {
            return null;
        }
        String getDocumentTxt = this.text;
        return getDocumentTxt;
    }

    /**
     * @return content of binary data document
     * In this case, better to return a copy.
     * I don't think it should be a problem if this null
     */
    @Override
    public byte[] getDocumentBinaryData() {
        if (this.text != null) {
            return null;
        }
        byte[] getDocumentBinaryData = this.binaryData;
        return getDocumentBinaryData;
    }

    /**
     * @return URI which uniquely identifies this document
     */
    @Override
    public URI getKey() {
        return this.uri;
    }

    private String replacer(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\s+]", "").toUpperCase();
    }

    /**
     * Be sure to ignore all characters that are not a letter or a number!
     * How many times does the given word appear in the document?
     * @param word
     * @return the number of times the given words appears in the document. If it's a binary document, return 0.
     */
    @Override
    public int wordCount(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (this.binaryData != null) {
            return 0;
        }
        Integer i = this.count.get(replacer(word));
        return i == null ? 0 : i;
    }

    /**
     * @return a copy of the word to count map so it can be serialized
     */
    @Override
    public Map<String,Integer> getWordMap() {
        return new HashMap<>(this.count);
    }

    /**
     * This must set the word to count map during deserialization
     * @param wordMap
     * @throws IllegalArgumentException if the document is binary
     */
    @Override
    public void setWordMap(Map<String,Integer> wordMap) {
        if (this.binaryData == null) {
            throw new IllegalArgumentException();
        }
        this.count = wordMap;
    }

    /**
     * @return all the words that appear in the document
     * If getWords is called on a binary Document - You should not throw an exception. Null is risky because the caller might immediately try iterating over it, returning an empty set makes the most sense.
     */
    @Override
    public Set<String> getWords() {
        return this.count.keySet();
    }

    /**
     * return the last time this document was used, via put/get or via a search result
     */
    @Override
    public long getLastUseTime(){
        return this.lastUseTime;
    }

    /**
     * @param timeInNanoseconds
     */
    @Override
    public void setLastUseTime(long timeInNanoseconds) {
        this.lastUseTime = timeInNanoseconds;
    }

    @Override
    public int compareTo(Document o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        return Long.compare(this.getLastUseTime(), o.getLastUseTime());
    }

    /**
     * @return
     * DocumentImpl must override the default hashCode method
     */
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(binaryData);
        return result;
    }

    /**
     * @return the boolean relationship between the 2 variables
     * Two documents are considered equal if they have the same hashCode.
     * The number used is arbitrary - it just needs to be a prime number to help avoid collisions.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof DocumentImpl)) {
            return false;
        }
        return o.hashCode() == this.hashCode();
    }
}