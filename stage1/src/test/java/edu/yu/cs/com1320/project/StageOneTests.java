package edu.yu.cs.com1320.project;
import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.stage1.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage1.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage1.impl.DocumentStoreImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
public class StageOneTests {
   	@Test
    public void documentImplExceptions() {
		boolean test = false;
		try {
		    new DocumentImpl(nonEmptyUri, nullBytes);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);
		
		test = false;
		try {
		    new DocumentImpl(nonEmptyUri, nullString);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);

		test = false;
		try {
		    new DocumentImpl(nonEmptyUri, emptyString);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);
		
		test = false;
		try {
		    new DocumentImpl(nullUri, nonEmptyString);
		} catch (IllegalArgumentException e) {
			test = true;
		}

		test = false;
		try {
		    new DocumentImpl(nullUri, nonEmptyBytes);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);

		assertTrue(test);

		test = false;
		try {
		    new DocumentImpl(emptyUri, nonEmptyString);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);

		test = false;
		try {
		    new DocumentImpl(nonEmptyUri, emptyBytes);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);
    }

    @Test
    public void documentStoreImplExceptions() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
		boolean test = false;
		try {
		    store.putDocument(inputStreamOne, nullUri, DocumentFormat.TXT);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);

		test = false;
		try {
		    store.putDocument(inputStreamOne, emptyUri, DocumentFormat.TXT);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);
		
		test = false;
		try {
		    store.putDocument(inputStreamOne, uriOne, null);
		} catch (IllegalArgumentException e) {
			test = true;
		}
		assertTrue(test);
    }


	@Test 
	public void deleteTestsInOrder() throws IOException{
		DocumentStore store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentFormat.BINARY));
		assertTrue(store.deleteDocument(uriOne));
		assertFalse(store.deleteDocument(uriOne));
		assertTrue(store.deleteDocument(uriTwo));
		assertFalse(store.deleteDocument(uriTwo));
		assertTrue(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertTrue(store.deleteDocument(uriFive));
		assertFalse(store.deleteDocument(uriFive));
		assertTrue(store.deleteDocument(uriFour));
		assertFalse(store.deleteDocument(uriFour));
		assertTrue(store.deleteDocument(uriSix));
		assertFalse(store.deleteDocument(uriSix));
		assertTrue(store.deleteDocument(uriSeven));
		assertFalse(store.deleteDocument(uriSeven));
		assertTrue(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertTrue(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(uriNine));
		assertTrue(store.deleteDocument(uriTen));
		assertFalse(store.deleteDocument(uriTen));
	}

	@Test 
	public void deleteTestsBackwards() throws IOException{
		DocumentStore store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentFormat.BINARY));
		assertTrue(store.deleteDocument(uriTen));
		assertFalse(store.deleteDocument(uriTen));
		assertTrue(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertTrue(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(uriEight));
		assertTrue(store.deleteDocument(uriSeven));
		assertFalse(store.deleteDocument(uriSeven));
		assertTrue(store.deleteDocument(uriSix));
		assertFalse(store.deleteDocument(uriSix));
		assertTrue(store.deleteDocument(uriFour));
		assertFalse(store.deleteDocument(uriFour));
		assertTrue(store.deleteDocument(uriFive));
		assertFalse(store.deleteDocument(uriFive));
		assertTrue(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertTrue(store.deleteDocument(uriTwo));
		assertFalse(store.deleteDocument(uriTwo));
		assertTrue(store.deleteDocument(uriOne));
		assertFalse(store.deleteDocument(uriOne));
	}


	@Test 
	public void deleteTestsOutOfOrder() throws IOException {
		DocumentStore store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentFormat.BINARY));
		assertTrue(store.deleteDocument(uriSeven));
		assertFalse(store.deleteDocument(uriSeven));
		assertTrue(store.deleteDocument(uriOne));
		assertFalse(store.deleteDocument(uriOne));
		assertTrue(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(uriThree));
		assertTrue(store.deleteDocument(uriTen));
		assertFalse(store.deleteDocument(uriTen));
		assertTrue(store.deleteDocument(uriSix));
		assertFalse(store.deleteDocument(uriSix));
		assertTrue(store.deleteDocument(uriTwo));
		assertFalse(store.deleteDocument(uriTwo));
		assertTrue(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertTrue(store.deleteDocument(uriFive));
		assertFalse(store.deleteDocument(uriFive));
		assertTrue(store.deleteDocument(uriFour));
		assertFalse(store.deleteDocument(uriFour));
		assertTrue(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
	}

    @Test
    public void deleteDocumentSingleTests() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertNotNull(store.getDocument(uriOne)); 
		Document documentOne = new DocumentImpl(uriOne, bytesOne);
		int currentHashCode = store.getDocument(uriOne).hashCode();
		assertEquals(documentOne.hashCode(), store.getDocument(uriOne).hashCode());	
        assertNotNull(store.getDocument(uriOne));
        assertTrue(store.deleteDocument(uriOne));
        assertFalse(store.deleteDocument(URI.create("https://www.yahoo.com/")));
        assertNull(store.getDocument(uriOne));
		Document documentOnePartA = new DocumentImpl(uriOne, bytesThree);
        assertEquals(0, store.putDocument(inputStreamThree, uriOne, DocumentFormat.BINARY));
        assertEquals(documentOnePartA.hashCode(), store.putDocument(null, uriOne, DocumentFormat.BINARY));
        assertNull(store.getDocument(uriOne));
    }

    @Test
    public void deleteDocumentMultipleTests() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
		Document documentOne = new DocumentImpl(uriOne, bytesOne);
		Document documentTwo = new DocumentImpl(uriTwo, bytesTwo);
		Document documentThree = new DocumentImpl(uriThree, bytesThree);
		Document documentFour = new DocumentImpl(uriFour, bytesFour);
		Document documentFive = new DocumentImpl(uriFive, bytesFive);
		Document documentSix = new DocumentImpl(uriSix, bytesSix);
		Document documentSeven = new DocumentImpl(uriSeven, bytesSeven);
		Document documentEight = new DocumentImpl(uriEight, bytesEight);
		Document documentNine = new DocumentImpl(uriNine, bytesNine);
		Document documentTen = new DocumentImpl(uriTen, bytesTen);
		assertNull(store.getDocument(uriOne));		
		assertNull(store.getDocument(uriTwo));		
		assertNull(store.getDocument(uriThree));		
		assertNull(store.getDocument(uriFour));	
		assertNull(store.getDocument(uriFive));	
		assertNull(store.getDocument(uriSix));	
		assertNull(store.getDocument(uriSeven));	
		assertNull(store.getDocument(uriEight));	
		assertNull(store.getDocument(uriNine));	
		assertNull(store.getDocument(uriTen));	
		assertFalse(store.deleteDocument(uriOne));
		assertFalse(store.deleteDocument(uriTwo));
		assertFalse(store.deleteDocument(uriThree));
		assertFalse(store.deleteDocument(uriFour));
		assertFalse(store.deleteDocument(uriFive));
		assertFalse(store.deleteDocument(uriSix));
		assertFalse(store.deleteDocument(uriSeven));
		assertFalse(store.deleteDocument(uriEight));
		assertFalse(store.deleteDocument(URI.create("www.google.com")));
		assertFalse(store.deleteDocument(uriNine));
		assertFalse(store.deleteDocument(uriTen));
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentFormat.BINARY));
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertNotNull(store.getDocument(uriSix));
        assertNotNull(store.getDocument(uriSeven));
        assertNotNull(store.getDocument(uriEight));
        assertNotNull(store.getDocument(uriNine));
        assertNotNull(store.getDocument(uriTen));
        assertEquals(documentOne, store.getDocument(uriOne));
        assertEquals(documentTwo, store.getDocument(uriTwo));
        assertEquals(documentThree, store.getDocument(uriThree));
        assertEquals(documentFour, store.getDocument(uriFour));
        assertEquals(documentFive, store.getDocument(uriFive));
        assertEquals(documentSix, store.getDocument(uriSix));
        assertEquals(documentSeven, store.getDocument(uriSeven));
        assertEquals(documentEight, store.getDocument(uriEight));
        assertEquals(documentNine, store.getDocument(uriNine));
        assertEquals(documentTen, store.getDocument(uriTen));
		assertEquals(documentOne.hashCode(), store.getDocument(uriOne).hashCode());	
        assertEquals(documentTwo.hashCode(), store.getDocument(uriTwo).hashCode());
		assertEquals(documentThree.hashCode(), store.getDocument(uriThree).hashCode());	
		assertEquals(documentFour.hashCode(), store.getDocument(uriFour).hashCode());	
		assertEquals(documentFive.hashCode(), store.getDocument(uriFive).hashCode());	
		assertEquals(documentSix.hashCode(), store.getDocument(uriSix).hashCode());	
		assertEquals(documentSeven.hashCode(), store.getDocument(uriSeven).hashCode());	
		assertEquals(documentEight.hashCode(), store.getDocument(uriEight).hashCode());	
		assertEquals(documentNine.hashCode(), store.getDocument(uriNine).hashCode());	
		assertEquals(documentTen.hashCode(), store.getDocument(uriTen).hashCode());	
		assertTrue(store.deleteDocument(uriOne));
		assertTrue(store.deleteDocument(uriTwo));
		assertTrue(store.deleteDocument(uriThree));
		assertTrue(store.deleteDocument(uriFour));
		assertTrue(store.deleteDocument(uriFive));
		assertTrue(store.deleteDocument(uriSix));
		assertTrue(store.deleteDocument(uriSeven));
		assertTrue(store.deleteDocument(uriEight));
		assertTrue(store.deleteDocument(uriNine));
		assertTrue(store.deleteDocument(uriTen));
		assertNull(store.getDocument(uriOne));		
		assertNull(store.getDocument(uriTwo));		
		assertNull(store.getDocument(uriThree));		
		assertNull(store.getDocument(uriFour));	
		assertNull(store.getDocument(uriFive));	
		assertNull(store.getDocument(uriSix));	
		assertNull(store.getDocument(uriSeven));	
		assertNull(store.getDocument(uriEight));	
		assertNull(store.getDocument(uriNine));	
		assertNull(store.getDocument(uriTen));	
    }


	@Test 
	public void hashCodeTextTest() {
		DocumentStore store = new DocumentStoreImpl();
		boolean tester = false;
		try {
			store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT);
		} catch (IOException e) {
			tester = true;
		}
		assertFalse(tester);
		Document documentOne = new DocumentImpl(uriOne, stringOne);
		assertTrue(store.getDocument(uriOne).equals(documentOne));
		assertEquals(store.getDocument(uriOne), documentOne);			
		assertEquals(store.getDocument(uriOne).hashCode(), documentOne.hashCode());	
	}

	@Test
	public void textTest() {
		DocumentStore store = new DocumentStoreImpl();
		boolean tester = false;
		try {
			store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT);
			store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT);
			store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.TXT);
			store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.TXT);
			store.putDocument(inputStreamFive, uriFive, DocumentStore.DocumentFormat.TXT);
		} catch (IOException e) {
			tester = true;
		}
		assertFalse(tester);
		Document documentOne = new DocumentImpl(uriOne, stringOne);
		Document documentTwo = new DocumentImpl(uriTwo, stringTwo);
		Document documentThree = new DocumentImpl(uriThree, stringThree);
		Document documentFour = new DocumentImpl(uriFour, stringFour);
		Document documentFive = new DocumentImpl(uriFive, stringFive);		
		assertEquals(documentOne, store.getDocument(uriOne));	
		assertEquals(documentTwo, store.getDocument(uriTwo));	
		assertEquals(documentThree, store.getDocument(uriThree));	
		assertEquals(documentFour, store.getDocument(uriFour));	
		assertEquals(documentFive, store.getDocument(uriFive));			
		assertEquals(documentOne.hashCode(), store.getDocument(uriOne).hashCode());	
		assertEquals(documentTwo.hashCode(), store.getDocument(uriTwo).hashCode());	
		assertEquals(documentThree.hashCode(), store.getDocument(uriThree).hashCode());	
		assertEquals(documentFour.hashCode(), store.getDocument(uriFour).hashCode());	
		assertEquals(documentFive.hashCode(), store.getDocument(uriFive).hashCode());	
	
	}

	@Test
	public void binaryTest() {
		DocumentStore store = new DocumentStoreImpl();
		boolean tester = false;
		try {
			store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.BINARY);
			store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.BINARY);
			store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.BINARY);
			store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.BINARY);
			store.putDocument(inputStreamFive, uriFive, DocumentStore.DocumentFormat.BINARY);

		} catch (IOException e) {
			tester = true;
		}
		assertFalse(tester);
		Document documentOne = new DocumentImpl(uriOne, bytesOne);
		Document documentTwo = new DocumentImpl(uriTwo, bytesTwo);
		Document documentThree = new DocumentImpl(uriThree, bytesThree);
		Document documentFour = new DocumentImpl(uriFour, bytesFour);
		Document documentFive = new DocumentImpl(uriFive, bytesFive);
		int hashCodeOne = store.getDocument(uriOne).hashCode();
		int hashCodeTwo = store.getDocument(uriTwo).hashCode();
		int hashCodeThree = store.getDocument(uriThree).hashCode();		
		int hashCodeFour = store.getDocument(uriFour).hashCode();		
		int hashCodeFive = store.getDocument(uriFive).hashCode();		
		assertEquals(documentOne.hashCode(), hashCodeOne);	
		assertEquals(documentTwo.hashCode(), hashCodeTwo);	
		assertEquals(documentThree.hashCode(), hashCodeThree);	
		assertEquals(documentFour.hashCode(), hashCodeFour);	
		assertEquals(documentFive.hashCode(), hashCodeFive);		
	}

    @Test
    public void hashTableTests() {
        HashTableImpl<Integer, String> table = new HashTableImpl<>();
        assertNull(table.get(5));        
        assertNull(table.put(1, "TableTest"));
        assertNotNull(table.get(1));
        assertNull(table.get(2));
        String originalString = "TableTest";
        String replacementString = "Replacement String";
        assertNull(table.put(2, "TableTest"));
        assertEquals(table.put(2, "Replacement String").length(), originalString.length());
        HashTableImpl<Integer, Integer> integerTable = new HashTableImpl<>();
        assertNull(integerTable.put(1, 1));
        assertNull(integerTable.get(2));
        assertNotNull(integerTable.get(1));
        assertEquals(1, (int)integerTable.get(1));
        assertEquals(1, (int)integerTable.put(1, null));
    }

    @Test
    public void hashTableCoillisions() {
        HashTableImpl<Integer, String> table = new HashTableImpl<>();
        assertNull(table.put(1, "First"));
        assertNull(table.put(2, "Second"));
        assertNull(table.put(3, "Third"));
        assertNull(table.put(4, "Fourth"));
        assertNull(table.put(5, "Fifth"));
        assertNull(table.put(6, "First"));
        assertNull(table.put(7, "Second"));
        assertNull(table.put(8, "Third"));
        assertNull(table.put(9, "Fourth"));
        assertNull(table.put(10, "Fifth"));
        assertEquals("First", table.get(1));
        assertEquals("Second", table.get(2));
        assertEquals("Third", table.get(3));
        assertEquals("Fourth", table.get(4));
        assertEquals("Fifth", table.get(5));
        assertEquals("First", table.get(6));
        assertEquals("Second", table.get(7));
        assertEquals("Third", table.get(8));
        assertEquals("Fourth", table.get(9));
        assertEquals("Fifth", table.get(10));
        assertEquals("First", table.put(1, "Crash"));
        assertEquals("Second", table.put(2, "Bang"));
        assertEquals("Third", table.put(3, "Boom"));
        assertEquals("Fourth", table.put(4, "Blah"));
        assertEquals("Fifth", table.put(5, "Bye"));
        assertEquals("Crash", table.get(1));
        assertEquals("Bang", table.get(2));
        assertEquals("Boom", table.get(3));
        assertEquals("Blah", table.get(4));
        assertEquals("Bye", table.get(5));
        assertEquals("First", table.get(6));
        assertEquals("Second", table.get(7));
        assertEquals("Third", table.get(8));
        assertEquals("Fourth", table.get(9));
        assertEquals("Fifth", table.get(10));
	}

	String stringOne = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.";
	String stringTwo = "Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, can long endure.";
	String stringThree = "We are met on a great battle-field of that war.";
	String stringFour = "We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live.";
	String stringFive = "It is altogether fitting and proper that we should do this.";
	String stringSix = "But, in a larger sense, we can not dedicate -- we can not consecrate -- we can not hallow -- this ground.";
	String stringSeven = "The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract.";
	String stringEight = "The world will little note, nor long remember what we say here, but it can never forget what they did here.";
	String stringNine = "It is for us the living, rather, to be dedicated here to the unfinished work which they who fought here have thus far so nobly advanced.";
	String stringTen = "It is rather for us to be here dedicated to the great task remaining before us -- that from these honored dead we take increased devotion to that cause for which they gave the last full measure of devotion -- that we here highly resolve that these dead shall not have died in vain -- that this nation, under God, shall have a new birth of freedom -- and that government of the people, by the people, for the people, shall not perish from the earth.";	
	URI uriOne = URI.create("https://www.google.com/");
	URI uriTwo = URI.create("https://www.youtube.com/");
	URI uriThree = URI.create("https://www.facebook.com/");
	URI uriFour = URI.create("https://www.wikipedia.org/");
	URI uriFive = URI.create("https://www.yahoo.com/");
	URI uriSix = URI.create("https://www.Twitter.com/");
	URI uriSeven = URI.create("https://www.Instagram.com/");
	URI uriEight = URI.create("https://www.Baidu.com/");
	URI uriNine = URI.create("https://www.whatsapp.com/");
	URI uriTen = URI.create("https://www.netflix.com/");
	byte[] bytesOne = stringOne.getBytes();
	byte[] bytesTwo = stringTwo.getBytes();
	byte[] bytesThree = stringThree.getBytes();
	byte[] bytesFour = stringFour.getBytes();
	byte[] bytesFive = stringFive.getBytes();
	byte[] bytesSix = stringSix.getBytes();
	byte[] bytesSeven = stringSeven.getBytes();
	byte[] bytesEight = stringEight.getBytes();
	byte[] bytesNine = stringNine.getBytes();
	byte[] bytesTen = stringTen.getBytes();
	InputStream inputStreamOne = new ByteArrayInputStream(stringOne.getBytes());
	InputStream inputStreamTwo = new ByteArrayInputStream(stringTwo.getBytes());
	InputStream inputStreamThree = new ByteArrayInputStream(stringThree.getBytes());
	InputStream inputStreamFour = new ByteArrayInputStream(stringFour.getBytes());
	InputStream inputStreamFive = new ByteArrayInputStream(stringFive.getBytes());
	InputStream inputStreamSix = new ByteArrayInputStream(stringSix.getBytes());
	InputStream inputStreamSeven = new ByteArrayInputStream(stringSeven.getBytes());
	InputStream inputStreamEight = new ByteArrayInputStream(stringEight.getBytes());
	InputStream inputStreamNine = new ByteArrayInputStream(stringNine.getBytes());
	InputStream inputStreamTen = new ByteArrayInputStream(stringTen.getBytes());
	String nonEmptyString = "not empty!";
	String emptyString = "";
	String nullString = null;
	byte[] nonEmptyBytes = nonEmptyString.getBytes();
	byte[] emptyBytes = emptyString.getBytes();
	byte[] nullBytes = null;
	URI nonEmptyUri = URI.create("https://www.google.com/");
	URI emptyUri = URI.create("");
	URI nullUri = null;     
}