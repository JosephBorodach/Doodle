package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.stage4.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.net.URI;

public class DocumentImpl implements Document {
    private final byte[] binaryData;
    private final Map<String, Integer> count = new HashMap<>();
    private final String text;
    private final URI uri;
    private long lastUseTime;

    /**
     * @param uri the unique identifier of the document to delete
     * @throws IllegalArgumentException if either argument is null or empty/blank
     */
    public DocumentImpl(URI uri, String txt) {
        if (uri == null || txt == null || uri.toASCIIString().isBlank() || txt.length() == 0 || txt.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.uri = uri;
        this.text = txt;
        this.binaryData = null;
        for (String word : this.replacer(txt).trim().split("\\s+")) {
            Integer i = this.count.get(this.replacer(word));
            this.count.put(word, i == null ? 1 : i + 1);
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
     */
    public String getDocumentTxt() {
        if (this.binaryData != null) {
            return null;
        }
        String getDocumentTxt = this.text;
        return getDocumentTxt;
    }

    /**
     * @return content of binary data document
     */
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
        return s.replaceAll("[^a-zA-Z0-9\\s+]", "").toUpperCase(); //2x check that this should also say \\s+ and also that it doesn't matter if I use Uppercase or lowercase
    }

    /**
     * Be sure to ignore all characters that are not a letter or a number!
     * how many times does the given word appear in the document?
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
        return Long.compare(this.getLastUseTime(), o.getLastUseTime());
    }

    /**
     * @return
     * DocumentImpl must override the default hashCode method
     * Two documents are considered equal if they have the same hashCode.
     * The number used is arbitrary - it just needs to be a prime number to help avoid collisions
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
     */
    @Override
    public boolean equals(Object o) {
        if (! (o instanceof DocumentImpl)) {
            return false;
        }
        return o.hashCode() == this.hashCode();
    }
}