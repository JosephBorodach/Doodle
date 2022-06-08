package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document;

import java.util.Arrays;
import java.net.URI;
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