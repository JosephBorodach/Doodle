package edu.yu.cs.com1320.project.stage3.impl;
import edu.yu.cs.com1320.project.stage3.Document;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

//import java.net.URISyntaxException;
//import java.util.Locale;
/**
 * Document MUST NOT implement java.lang.Comparable - It can still implement normally though right?
 *
 * You must actively strip out any non-alphanumeric characters from any documents that are added.
 * For the queries / keyword searches, you can assume the user will give you alphanumeric characters only.
 * In both cases (docs, keywords), you must pick either lowercase or uppercase, and convert everything to be that case.
 */
public class DocumentImpl implements Document {
	private final URI uri;
	private Map<String, Integer> count = new HashMap<>();
	private String text;
	private byte[] binaryData;

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
		this.binaryData = null; //Check if you should trim
		for (String word : this.replacer(txt).trim().split("\\s+")) { //Is it necessary to include the split or trim()? Couldn't I just split in the replacer method
			Integer i = this.count.get(this.replacer(word));
			this.count.put(word, i == null ? 1 : i + 1);
		}
	}

	/**
	 * @param uri the unique identifier of the document to delete
	 * @throws IllegalArgumentException if either argument is null or empty/blank
	 */
	public DocumentImpl(URI uri, byte[] binaryData) {
		if (uri == null || binaryData == null || uri.toASCIIString().isBlank() || binaryData.length == 0) { //uri.equals(URI.create(""))
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
	public Set<String> getWords() {
		return this.count.keySet();
	}

	/**
	 * @return
	 * DocumentImpl must override the default hashCode method - Two documents are considered equal if they have the same hashCode. The number used is arbitrary - it just needs to be a prime number to help avoid collisions
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
	 * DocumentImpl must override the default hashCode method - Two documents are considered equal if they have the same hashCode.
	 */
	@Override
	public boolean equals(Object o) { //if(!(str instanceof String)) { /* do Something */ }
		return o.hashCode() == this.hashCode();
	}
}