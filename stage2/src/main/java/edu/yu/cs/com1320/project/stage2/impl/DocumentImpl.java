package edu.yu.cs.com1320.project.stage2.impl;
import edu.yu.cs.com1320.project.stage2.Document;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
public class DocumentImpl implements Document{ 
	private String text = null;
	private URI uri;
	private byte[] binaryData = null;
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
       public URI getKey() {
       	return this.uri;
       }


	/**
	 * @return the boolean relationship between the 2 variables
	 * DocumentImpl must override the default hashCode method - Two documents are considered equal if they have the same hashCode.
	 */ 
	@Override
	public boolean equals(Object o) {
		if (o.hashCode() == this.hashCode()) {
			return true;
		} 
		return false;
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
}
/**
 * #1 A Document is made up of a unique identifier (java.net.URI) and the data that comprises the content of the document, either a java.lang.String for text or a byte[] for binary data.
 * #2 You must write a class called edu.yu.cs.com1320.project.stage1.impl.DocumentImpl that implements the edu.yu.cs.com1320.project.stage1.Document interface.
 * #3 DocumentImpl MUST NOT implement java.lang.Comparable
 * 		+ Comparable - represents an object which can be compared to other objects. 
 * 		+ For instance, numbers can be compared, strings can be compared using alphabetical comparison etc. 
 * 		+ Several of the built-in classes in Java implements the Java Comparable interface.
 * 		+ The Comparable interface defines the `compareTo` method used to compare objects. 
 * 		+ If a class implements the Comparable interface, objects created from that class can be sorted using Java's sorting algorithms.
 * #4 DocumentImpl must override the default equals and hashCode methods. Two documents are considered equal if they have the same hashCode.
 * #5 DocumentImpl must provide the following two constructors, which should throw an java.lang.IllegalArgumentException if either argument is null or empty/blank:
 * 		a. public DocumentImpl(URI uri, String txt)
 * 		b. public DocumentImpl(URI uri, byte[] binaryData)
 * 		null must be checked before isempty because otherwise isempty could throw a NPE
 * 		The function checks the actual contents of the string, the == operator checks whether the references to the objects are equal. Note that string constants are usually "interned" such that two constants with the same value can actually be compared with ==, but it's better not to rely on that.
 * 		A URI cannot be empty as defined by isEmpty() - (uri.isEmpty()) - https://stackoverflow.com/questions/43333664/is-there-an-empty-uri
 *		Check if uri is empty - Could also use .compareTo() and see if it equals 0
 */ 