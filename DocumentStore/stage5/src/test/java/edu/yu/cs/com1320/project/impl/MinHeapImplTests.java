package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.*;
import edu.yu.cs.com1320.project.stage5.impl.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import java.net.URI;

public class MinHeapImplTests {
    private String stringOne;
    private String stringTwo;
    private String stringThree;
    private String stringFour;
    private String stringFive;
    private String stringSix;
    private String stringSeven;
    private String stringEight;
    private String stringNine;
    private String stringTen;
    private URI uriOne;
    private URI uriTwo;
    private URI uriThree;
    private URI uriFour;
    private URI uriFive;
    private URI uriSix;
    private URI uriSeven;
    private URI uriEight;
    private URI uriNine;
    private URI uriTen;
    private InputStream inputStreamOne;
    private InputStream inputStreamTwo;
    private InputStream inputStreamThree;
    private InputStream inputStreamFour;
    List<Document> emptyList;

    @BeforeEach
    public void initiate() {
        this.stringOne = "Peter Piper picked a peck of pickled picked peppers";
        this.stringTwo = "A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers";
        this.stringThree = "pickled PICKLED PiCkLeD pickled PICKLED PiCkLeD";
        this.stringFour = "Where's the peck of pickled peppers Peter Piper picked";
        this.stringFive = "How much wood would a woodchuck chuck if a woodchuck could chuck wood";
        this.stringSix = "He would chuck, he would, as much as he could, and chuck as much wood";
        this.stringSeven = "as a woodchuck would if a woodchuck could chuck wood";
        this.stringEight = "She sells";
        this.stringNine = "sea shells";
        this.stringTen = "by the seashore";
        this.uriOne = URI.create("https://www.google.com/");
        this.uriTwo = URI.create("https://www.youtube.com/");
        this.uriThree = URI.create("https://www.facebook.com/");
        this.uriFour = URI.create("https://www.wikipedia.org/");
        this.uriFive = URI.create("https://www.yahoo.com/");
        this.uriSix = URI.create("https://www.Twitter.com/");
        this.uriSeven = URI.create("https://www.Instagram.com/");
        this.uriEight = URI.create("https://www.Baidu.com/");
        this.uriNine = URI.create("https://www.whatsapp.com/");
        this.uriTen = URI.create("https://www.netflix.com/");
        this.updateBytes();
        this.emptyList = new ArrayList<>();
    }

    @AfterEach
    public void closure() {
        this.stringOne = null;
        this.stringTwo = null;
        this.stringThree = null;
        this.stringFour = null;
        this.stringFive = null;
        this.stringSix = null;
        this.stringSeven = null;
        this.stringEight = null;
        this.stringNine = null;
        this.stringTen = null;
        this.uriOne = null;
        this.uriTwo = null;
        this.uriThree = null;
        this.uriFour = null;
        this.uriFive = null;
        this.uriSix = null;
        this.uriSeven = null;
        this.uriEight = null;
        this.uriNine = null;
        this.uriTen = null;
        this.emptyList = null;
    }

    private void closure(DocumentStore store) {
        store.deleteDocument(this.uriOne);
        store.deleteDocument(this.uriTwo);
        store.deleteDocument(this.uriThree);
        store.deleteDocument(this.uriFour);
        store.deleteDocument(this.uriFive);
        store.deleteDocument(this.uriSix);
        store.deleteDocument(this.uriSeven);
        store.deleteDocument(this.uriEight);
        store.deleteDocument(this.uriNine);
        store.deleteDocument(this.uriTen);
    }

    public void updateBytes () {
        this.inputStreamOne = new ByteArrayInputStream(stringOne.getBytes());
        this.inputStreamTwo = new ByteArrayInputStream(stringTwo.getBytes());
        this.inputStreamThree = new ByteArrayInputStream(stringThree.getBytes());
        this.inputStreamFour = new ByteArrayInputStream(stringFour.getBytes());
    }

    public void putTenTxtDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFive.getBytes()), uriFive, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSix.getBytes()), uriSix, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSeven.getBytes()), uriSeven, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringEight.getBytes()), uriEight, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringNine.getBytes()), uriNine, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTen.getBytes()), uriTen, DocumentStore.DocumentFormat.BINARY));
    }

    public void putFourTxtDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.TXT));
    }

    private void notNull_formerFive(DocumentStore store) {
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertNotNull(store.getDocument(uriFive));
    }

    private void notNull_latterFive(DocumentStore store) {
        assertNotNull(store.getDocument(uriSix));
        assertNotNull(store.getDocument(uriSeven));
        assertNotNull(store.getDocument(uriEight));
        assertNotNull(store.getDocument(uriNine));
        assertNotNull(store.getDocument(uriTen));
    }

    private void null_formerFive(DocumentStore store) {
        assertNull(store.getDocument(uriOne));
        assertNull(store.getDocument(uriTwo));
        assertNull(store.getDocument(uriThree));
        assertNull(store.getDocument(uriFour));
        assertNull(store.getDocument(uriFive));
    }

    private void null_latterFive(DocumentStore store) {
        assertNull(store.getDocument(uriSix));
        assertNull(store.getDocument(uriSeven));
        assertNull(store.getDocument(uriEight));
        assertNull(store.getDocument(uriNine));
        assertNull(store.getDocument(uriTen));
    }

    public void putFourByteDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.BINARY));
    }

    /**
     * In no longer matters what the setMax is set to in terms of getDocument, regardless it should return the document since when it is brought back into memory.
     * @throws IOException
     */
    @Test
    public void searchOverFlowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        store.setMaxDocumentCount(4);
        this.notNull_formerFive(store);
        this.notNull_latterFive(store);
        this.closure(store);
    }

    @Test
    public void overFLowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        this.notNull_latterFive(store);
        store.setMaxDocumentCount(5);
        this.notNull_latterFive(store);

        //STAGE 5 UPDATE: overflow docs can be returned
        this.notNull_formerFive(store);
        this.notNull_latterFive(store);
        assertDoesNotThrow (() -> store.undo(uriOne));
        assertDoesNotThrow (() -> store.undo(uriTwo));
        assertDoesNotThrow (() -> store.undo(uriThree));
        assertDoesNotThrow (() -> store.undo(uriFour));
        assertDoesNotThrow (() -> store.undo(uriFive));
        assertDoesNotThrow (() -> store.undo(uriSix));
        assertDoesNotThrow (() -> store.undo(uriSeven));
        assertDoesNotThrow (() -> store.undo(uriEight));
        assertDoesNotThrow (() -> store.undo(uriNine));
        assertDoesNotThrow (() -> store.undo(uriTen));
        assertThrows(IllegalStateException.class, () -> store.undo(uriOne));
        assertThrows(IllegalStateException.class, () -> store.undo(uriTwo));
        assertThrows(IllegalStateException.class, () -> store.undo(uriThree));
        assertThrows(IllegalStateException.class, () -> store.undo(uriFour));
        assertThrows(IllegalStateException.class, () -> store.undo(uriFive));
        assertThrows(IllegalStateException.class, () -> store.undo(uriSix));
        assertThrows(IllegalStateException.class, () -> store.undo(uriSeven));
        assertThrows(IllegalStateException.class, () -> store.undo(uriEight));
        assertThrows(IllegalStateException.class, () -> store.undo(uriNine));
        assertThrows(IllegalStateException.class, () -> store.undo(uriTen));
        this.null_formerFive(store);
        this.null_latterFive(store);
        this.closure(store);
    }

    @Test
    public void newTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        assertNotNull(store.search("Peter"));
        store.setMaxDocumentCount(0);
        this.notNull_formerFive(store);
        this.notNull_latterFive(store);
        assertEquals(this.emptyList, store.search("Peter"));
        this.closure(store);
    }

    @Test
    public void deleteOverFLowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        assertTrue(store.deleteDocument(uriOne));
        assertTrue(store.deleteDocument(uriTwo));
        assertTrue(store.deleteDocument(uriThree));
        assertTrue(store.deleteDocument(uriFour));
        assertTrue(store.deleteDocument(uriFive));
        store.setMaxDocumentCount(5);
        assertNull(store.getDocument(uriOne));
        assertNull(store.getDocument(uriTwo));

        //STAGE 5 UPDATE
        //assertThrows(IllegalArgumentException.class, () -> store.getDocument(this.uriThree));
        assertNull(store.getDocument(uriThree));
        assertNull(store.getDocument(uriFour));
        assertNull(store.getDocument(uriFive));

        //redoing the first 5 docs should cause the latter 5 to be removed from memory
        assertDoesNotThrow (() -> store.undo(uriOne));
        assertDoesNotThrow (() -> store.undo(uriTwo));
        assertDoesNotThrow (() -> store.undo(uriThree));
        assertDoesNotThrow (() -> store.undo(uriFour));
        assertDoesNotThrow (() -> store.undo(uriFive));
        this.notNull_latterFive(store);
        store.setMaxDocumentCount(2);

        //STAGE 5 UPDATE - Documents should be able to be brought back into memory
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertNotNull(store.getDocument(uriFive));
        this.closure(store);
    }

    @Test
    public void bytesOverFLowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();

        this.putFourByteDocuments(store);
        store.setMaxDocumentBytes(120);
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertThrows(IllegalArgumentException.class, () -> store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT));
        assertThrows(IllegalArgumentException.class, () -> store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT));

        this.updateBytes();
        assertEquals(store.getDocument(uriThree).hashCode(), store.putDocument(inputStreamOne, uriThree, DocumentStore.DocumentFormat.BINARY));
        assertEquals(store.getDocument(uriFour).hashCode(), store.putDocument(inputStreamTwo, uriFour, DocumentStore.DocumentFormat.BINARY));
        Document document1 = new DocumentImpl(uriOne, stringOne.getBytes());
        Document document2 = new DocumentImpl(uriTwo, stringTwo.getBytes());
        assertTrue((document1.getDocumentBinaryData().length + document2.getDocumentBinaryData().length) > 120);

        //It was deleted bc string 2 put it over
        //STAGE 5 UPDATE
        assertTrue(store.deleteDocument(uriThree));
        assertTrue(store.deleteDocument(uriFour));
        store.setMaxDocumentBytes(10000);
        this.closure(store);
    }

    @Test
    public void maxTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putFourByteDocuments(store);

        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        store.setMaxDocumentCount(2);
        store.setMaxDocumentBytes(10);

        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));

        //STAGE 5 UPDATE
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));

        //store.setMaxDocumentBytes(10);
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        this.closure(store);
    }
}
