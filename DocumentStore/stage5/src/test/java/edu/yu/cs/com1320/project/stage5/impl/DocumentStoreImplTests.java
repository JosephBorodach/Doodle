package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.*;
import edu.yu.cs.com1320.project.stage5.DocumentStore.DocumentFormat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class DocumentStoreImplTests {
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
    Document documentOne;
    Document documentTwo;
    Document documentThree;
    Document documentFour;
    Document documentFive;
    Document documentSix;
    Document documentSeven;
    Document documentEight;
    Document documentNine;
    Document documentTen;
    private URI emptyUri;
    private URI nullUri;

    @BeforeEach
    public void initiate() throws URISyntaxException {
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
        this.uriOne = new URI("https://www.google.com/");
        this.uriTwo = new URI("https://www.youtube.com/");
        this.uriThree = new URI("https://www.facebook.com/");
        this.uriFour = new URI("https://www.wikipedia.org/");
        this.uriFive = new URI("https://www.yahoo.com/");
        this.uriSix = new URI("https://www.Twitter.com/");
        this.uriSeven = new URI("https://www.Instagram.com/");
        this.uriEight = new URI("https://www.Baidu.com/");
        this.uriNine = new URI("https://www.whatsapp.com/");
        this.uriTen = new URI("https://www.netflix.com/");
        this.emptyUri = URI.create("");
        this.nullUri = null;
    }

    @AfterEach
    public void deleteAll() throws IOException {
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
        this.documentOne = null;
        this.documentTwo = null;
        this.documentThree = null;
        this.documentFour = null;
        this.documentFive = null;
        this.documentSix = null;
        this.documentSeven = null;
        this.documentEight = null;
        this.documentNine = null;
        this.documentTen = null;
    }

    public void setUpTextDocuments() {
        this.documentOne = new DocumentImpl(uriOne, this.stringOne, new HashMap<>());
        this.documentTwo = new DocumentImpl(uriTwo, this.stringTwo, new HashMap<>());
        this.documentThree = new DocumentImpl(uriThree, this.stringThree, new HashMap<>());
        this.documentFour = new DocumentImpl(uriFour, this.stringFour, new HashMap<>());
        this.documentFive = new DocumentImpl(uriFive, this.stringFive, new HashMap<>());
        this.documentSix = new DocumentImpl(uriSix, this.stringSix, new HashMap<>());
        this.documentSeven = new DocumentImpl(uriSeven, this.stringSeven, new HashMap<>());
        this.documentEight = new DocumentImpl(uriEight, this.stringEight, new HashMap<>());
        this.documentNine = new DocumentImpl(uriNine, this.stringNine, new HashMap<>());
        this.documentTen = new DocumentImpl(uriTen, this.stringTen, new HashMap<>());
    }

    public void setUpByteDocuments() {
        this.documentOne = new DocumentImpl(uriOne, this.stringOne.getBytes());
        this.documentTwo = new DocumentImpl(uriTwo, this.stringTwo.getBytes());
        this.documentThree = new DocumentImpl(uriThree, this.stringThree.getBytes());
        this.documentFour = new DocumentImpl(uriFour, this.stringFour.getBytes());
        this.documentFive = new DocumentImpl(uriFive, this.stringFive.getBytes());
        this.documentSix = new DocumentImpl(uriSix, this.stringSix.getBytes());
        this.documentSeven = new DocumentImpl(uriSeven, this.stringSeven.getBytes());
        this.documentEight = new DocumentImpl(uriEight, this.stringEight.getBytes());
        this.documentNine = new DocumentImpl(uriNine, this.stringNine.getBytes());
        this.documentTen = new DocumentImpl(uriTen, this.stringTen.getBytes());
    }

    public void putTenBinaryDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFive.getBytes()), uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSix.getBytes()), uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSeven.getBytes()), uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringEight.getBytes()), uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringNine.getBytes()), uriNine, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTen.getBytes()), uriTen, DocumentFormat.BINARY));
    }

    public void putTenTxtDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFive.getBytes()), uriFive, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSix.getBytes()), uriSix, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringSeven.getBytes()), uriSeven, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringEight.getBytes()), uriEight, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringNine.getBytes()), uriNine, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTen.getBytes()), uriTen, DocumentFormat.TXT));
    }

    public void getDocumentHashEqualsTest (DocumentStore store) {
        assertEquals(this.documentOne.hashCode(), store.getDocument(this.uriOne).hashCode());
        assertEquals(documentTwo.hashCode(), store.getDocument(uriTwo).hashCode());
        assertEquals(documentThree.hashCode(), store.getDocument(uriThree).hashCode());
        assertEquals(documentFour.hashCode(), store.getDocument(uriFour).hashCode());
        assertEquals(documentFive.hashCode(), store.getDocument(uriFive).hashCode());
        assertEquals(documentSix.hashCode(), store.getDocument(uriSix).hashCode());
        assertEquals(documentSeven.hashCode(), store.getDocument(uriSeven).hashCode());
        assertEquals(documentEight.hashCode(), store.getDocument(uriEight).hashCode());
        assertEquals(documentNine.hashCode(), store.getDocument(uriNine).hashCode());
        assertEquals(documentTen.hashCode(), store.getDocument(uriTen).hashCode());
    }

    public void getDocumentEqualsTest(DocumentStore store) {
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
    }

    public void getDocumentNull (DocumentStore store, String string) {
        if (string == "Null") {
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
        } else {
            assertNotNull(store.getDocument(uriOne));
            assertNotNull(store.getDocument(uriTwo));
            assertNotNull(store.getDocument(uriThree));
            assertNotNull(store.getDocument(uriFour));
            assertNotNull(store.getDocument(uriSix));
            assertNotNull(store.getDocument(uriSeven));
            assertNotNull(store.getDocument(uriEight));
            assertNotNull(store.getDocument(uriNine));
            assertNotNull(store.getDocument(uriTen));
        }
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

    @Test
    public void directoryTest () throws IOException {
        File directory = new File("C:/Users/josephborodach/temp/new/hello/by");
        DocumentStoreImpl store = new DocumentStoreImpl(directory);
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.TXT));
        Document doc = store.getDocument(this.uriOne);
        assertNotNull(doc);
        assertTrue(store.deleteDocument(this.uriOne));
        assertNull(store.getDocument(this.uriOne));

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.TXT));
        store.setMaxDocumentCount(0);
        assertNotNull(store.getDocument(this.uriOne));

        store.setMaxDocumentCount(1);
        assertEquals(doc.hashCode(), store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.TXT));
        this.closure(store);
    }

    @Test
    public void byteOverFlowError() {
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentBytes(0);

        assertDoesNotThrow(() -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.TXT));

        store.setMaxDocumentBytes(10000);
        store.setMaxDocumentCount(0);
        //STAGE 5 UPDATE
        assertDoesNotThrow(() -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        this.closure(store);
    }

    @Test
    public DocumentStoreImpl getTextStore() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentFormat.TXT));
        return store;
    }

    @Test
    public void setMaxTests() throws IOException {
        DocumentStoreImpl store = getTextStore();
        assertDoesNotThrow (() -> store.setMaxDocumentCount(0));

        assertThrows(IllegalArgumentException.class, () -> store.setMaxDocumentCount(-1));

        assertDoesNotThrow (() -> store.setMaxDocumentCount(0));

        assertThrows(IllegalArgumentException.class, () -> store.setMaxDocumentCount(-1));
        this.closure(store);
    }

    @Test
    public void undoRemovesDeletedDoc() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.TXT));
        //Uri1 will be pushed out of memory
        store.setMaxDocumentCount(1);
        //If undo is called, it will bring doc1 back into memory and then completely remove it
        assertDoesNotThrow (() -> store.undo(this.uriOne));
        assertNull(store.getDocument(this.uriOne));
        this.closure(store);
    }

    @Test
    public void deleteMaxedOutDoc() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        assertNotNull(store.getDocument(this.uriOne));
        store.setMaxDocumentCount(0);
        assertTrue(store.deleteDocument(this.uriOne));
        store.setMaxDocumentCount(1);
        //Undoing the document should bring it back into memory
        store.undo(this.uriOne);
        assertNotNull(store.getDocument(this.uriOne));
        this.closure(store);

    }

    @Test
    public void maxedOutUndo() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        store.setMaxDocumentCount(0);
        //If undo is called, it will bring doc1 back into memory and then completely remove it
        assertDoesNotThrow (() -> store.undo(this.uriOne));
        store.setMaxDocumentCount(1);
        assertNull(store.getDocument(this.uriOne));
        this.closure(store);

    }

    @Test
    public void simpleUndoDoc() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        store.setMaxDocumentCount(0);
        //STAGE 5 UPDATE: overflows can be returned
        assertNotNull(store.getDocument(this.uriOne));
        store.setMaxDocumentCount(1);
        assertNotNull(store.getDocument(this.uriOne));
        store.undo(this.uriOne); //Will be completey removed
        assertNull(store.getDocument(this.uriOne));
        this.closure(store);
    }

    @Test
    public void complicatedUndoTest() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        List<URI> uriList = new ArrayList<>();
        List<Document> completeList;
        List<Document> emptyList = new ArrayList<>();
        Set<URI> deleted;

        assertThrows(IllegalStateException.class, () -> store.undo());

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));

        List<Document> justOneList = store.search("pickled");
        assertEquals(1, justOneList.size());

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.TXT));
        List<Document> twoList = store.search("pickled");
        assertEquals(2, twoList.size());

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentFormat.TXT));
        List<Document> threeList = store.search("pickled");
        assertEquals(3, threeList.size());

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentFormat.TXT));
        completeList = store.search("pickled");
        assertEquals(4, completeList.size());
        for (Document document : completeList) {
            uriList.add(document.getKey());
        }
        deleted = store.deleteAll("pickled");
        assertEquals(emptyList, store.search("pickled"));
        assertTrue(deleted.containsAll(uriList));
        assertNull(store.getDocument(this.uriOne));
        assertNull(store.getDocument(this.uriTwo));
        assertNull(store.getDocument(this.uriThree));
        assertNull(store.getDocument(this.uriFour));
        store.undo();
        assertEquals(4, store.search("pickled").size());
        assertEquals(completeList, store.search("pickled"));
        assertNotNull(store.getDocument(this.uriOne));
        assertNotNull(store.getDocument(this.uriTwo));
        assertNotNull(store.getDocument(this.uriThree));
        assertNotNull(store.getDocument(this.uriFour));

        //Delete them one by one - making sure that they are deleted in descending order 4 to 1
        store.undo();
        assertEquals(threeList, store.search("pickled"));

        store.undo();
        assertEquals(twoList, store.search("pickled"));

        store.undo();
        assertEquals(justOneList, store.search("pickled"));

        store.undo();
        assertEquals(emptyList, store.search("pickled"));
        //There is nothing to undo!
        assertThrows(IllegalStateException.class, () -> store.undo());

        this.closure(store);
    }

    @Test
    public void theHardTest() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        List<URI> uriList = new ArrayList<>();
        List<Document> completeList;
        Set<URI> deleted;
        assertThrows(IllegalStateException.class, () -> store.undo(this.uriOne));

        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), uriTwo, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriThree, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringFour.getBytes()), uriFour, DocumentFormat.TXT));

        completeList = store.searchByPrefix("Pi");
        assertEquals(4, completeList.size());
        for (Document document : completeList) {
            uriList.add(document.getKey());
        }
        assertEquals(4, uriList.size());
        store.undo(this.uriThree);
        assertNull(store.getDocument(this.uriThree));
        List<Document> shortList = store.searchByPrefix("Pi");
        assertEquals(3, shortList.size());
        InputStream newInputStream = new ByteArrayInputStream(stringThree.getBytes()); //For some reason the previous input stream was deleted
        store.putDocument(newInputStream, this.uriThree, DocumentFormat.TXT); //List<Document> longListCopy = store.searchByPrefix("Pi");
        assertEquals(4, store.searchByPrefix("Pi").size());
        deleted = store.deleteAllWithPrefix("Pi"); //Now all are deleted
        assertEquals(4, deleted.size());
        assertNull(store.getDocument(this.uriOne));
        assertNull(store.getDocument(this.uriTwo));
        assertNull(store.getDocument(this.uriThree));
        assertNull(store.getDocument(this.uriFour));
        assertEquals(0, store.searchByPrefix("Pi").size());
        store.undo(this.uriOne);
        store.undo(this.uriThree);
        store.undo(this.uriFour);
        assertNotNull(store.getDocument(this.uriOne));
        assertNull(store.getDocument(this.uriTwo));
        assertNotNull(store.getDocument(this.uriThree));
        assertNotNull(store.getDocument(this.uriFour));
        assertEquals(3, store.searchByPrefix("Pi").size());
        deleted = store.deleteAllWithPrefix("Pi"); //Now all are deleted
        assertEquals(3, deleted.size());

        assertNull(store.getDocument(this.uriTwo));
        store.undo(this.uriTwo);
        assertNotNull(store.getDocument(this.uriTwo));
        deleted.addAll(store.deleteAllWithPrefix("Pi")); //Now all are deleted
        assertEquals(4, deleted.size());
        assertTrue(deleted.containsAll(uriList));
        assertNull(store.getDocument(this.uriOne));
        assertNull(store.getDocument(this.uriTwo));
        assertNull(store.getDocument(this.uriThree));
        assertNull(store.getDocument(this.uriFour));
        store.undo(this.uriOne);
        store.undo(this.uriTwo);
        store.undo(this.uriThree);
        store.undo(this.uriFour);
        assertNotNull(store.getDocument(this.uriOne));
        assertNotNull(store.getDocument(this.uriTwo));
        assertNotNull(store.getDocument(this.uriThree));
        assertNotNull(store.getDocument(this.uriFour));
        assertEquals(4, store.searchByPrefix("Pi").size());
        store.undo(this.uriOne);
        store.undo(this.uriTwo);
        store.undo(this.uriThree);
        store.undo(this.uriFour);
        assertEquals(0, store.searchByPrefix("Pi").size());
        assertNull(store.getDocument(this.uriOne));
        assertNull(store.getDocument(this.uriTwo));
        assertNull(store.getDocument(this.uriThree));
        assertNull(store.getDocument(this.uriFour));
        assertEquals(0, store.deleteAllWithPrefix("Pi").size());

        assertThrows(IllegalStateException.class, () -> store.undo(this.uriOne));

        assertThrows(IllegalStateException.class, () -> store.undo(this.uriTwo));

        assertThrows(IllegalStateException.class, () -> store.undo(this.uriThree));

        assertThrows(IllegalStateException.class, () -> store.undo(this.uriFour));

        this.closure(store);
    }

    @Test
    public void simpleSearch() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        Document docOne = new DocumentImpl(this.uriOne, stringOne, new HashMap<>()); //Peter Piper picked a peck of pickled picked peppers
        Document docTwo = new DocumentImpl(this.uriTwo, stringTwo, new HashMap<>()); //A peck of pickled peppers Peter Piper picked
        store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), this.uriOne, DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.stringTen.getBytes()), this.uriTen, DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), this.uriTwo, DocumentFormat.TXT);
        List<Document> correctOrder = new ArrayList<>();
        correctOrder.add(docOne);
        correctOrder.add(docTwo);
        assertEquals(0, store.search("Laugh").size());
        assertEquals(2, store.search("picked").size());
        assertEquals(correctOrder, store.search("picked"));
        assertEquals(correctOrder, store.search("PICKED"));

        this.closure(store);
    }

    @Test
    public void simpleSearchByPrefix() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), this.uriOne, DocumentFormat.TXT); //Peter Piper picked a peck of pickled picked peppers
        store.putDocument(new ByteArrayInputStream(this.stringTwo.getBytes()), this.uriTwo, DocumentFormat.TXT); //A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers
        assertEquals(0, store.searchByPrefix("Laugh").size());
        assertEquals(2, store.searchByPrefix("pickled").size());
        assertEquals(2, store.searchByPrefix("pickled").size());
        assertEquals(2, store.searchByPrefix("PICKLED").size());
        assertEquals(2, store.searchByPrefix("PiCkLeD").size());
        assertEquals(2, store.searchByPrefix("PiCk").size());
        assertEquals(2, store.searchByPrefix("P").size());
        assertEquals(2, store.searchByPrefix("PET").size());
        assertEquals(1, store.searchByPrefix("picked").get(1).wordCount("PICKED"));
        store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), this.uriThree, DocumentFormat.TXT); //A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers
        assertEquals(6, store.search("PICKLED").get(0).wordCount("PICKLED"));

        this.closure(store);
    }

    @Test
    public void documentStoreImplExceptions() {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertThrows(IllegalArgumentException.class, () -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), nullUri, DocumentFormat.TXT));

        assertThrows(IllegalArgumentException.class, () -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), emptyUri, DocumentFormat.TXT));

        assertThrows(IllegalArgumentException.class, () -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, null));

        assertDoesNotThrow (() -> store.search("does not exist"));

        this.closure(store);
    }

    @Test
    public void undoExceptions () throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.BINARY));
        store.undo();
        assertNull(store.getDocument(uriOne));
        assertThrows(IllegalStateException.class, () -> store.undo());

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertThrows(IllegalStateException.class, () -> store.undo(uriNotInStore));

        this.closure(store);
    }

    //Any search method in TrieImpl or DocumentStoreImpl that returns a collection must return an empty collection, not null, if there are no matches.
    @Test
    public void notThereTests () {
        DocumentStoreImpl store = new DocumentStoreImpl();
        List<Document> emptyDocument = new LinkedList<>();

        assertEquals(emptyDocument , store.search("not there"));
        assertEquals(emptyDocument , store.searchByPrefix("not there"));

        Set<URI> emptySet = new HashSet<>();

        assertEquals(emptySet , store.deleteAll("notThere"));
        assertEquals(emptySet , store.deleteAll("not there"));

        assertEquals(emptySet , store.deleteAllWithPrefix("notThere"));
        assertEquals(emptySet , store.deleteAllWithPrefix("not There"));

        this.closure(store);
    }

    @Test
    public void deleteNotExistantURI () {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));

        assertDoesNotThrow (() -> store.undo());

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo(uriNotInStore));

        this.closure(store);
    }

    @Test
    public void parameterlessUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.setUpByteDocuments();
        this.putTenBinaryDocuments(store);
        this.getDocumentHashEqualsTest(store);

        store.undo();
        assertNull(store.getDocument(uriTen));

        store.undo();
        assertNull(store.getDocument(uriNine));

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo());

        assertNotNull(store.getDocument(this.uriEight));

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo(uriNotInStore));

        assertNull(store.getDocument(uriNotInStore));

        assertNotNull(store.getDocument(this.uriEight));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(uriEight));

        assertNotNull(store.getDocument(this.uriSeven));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(uriSeven));

        assertNotNull(store.getDocument(this.uriSix));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(uriSix));

        assertNotNull(store.getDocument(this.uriFive));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(uriFive));

        assertNotNull(store.getDocument(this.uriFour));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(uriFour));

        assertNotNull(store.getDocument(this.uriThree));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(this.uriThree));

        assertNotNull(store.getDocument(this.uriTwo));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(this.uriTwo));

        assertNotNull(store.getDocument(this.uriOne));
        assertDoesNotThrow (() -> store.undo());
        assertNull(store.getDocument(this.uriOne));

        this.closure(store);
    }

    @Test
    public void parameteredUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        this.setUpTextDocuments();
        this.getDocumentHashEqualsTest(store);

        assertDoesNotThrow (() -> store.undo(uriTen));
        assertNull(store.getDocument(uriTen));

        assertDoesNotThrow (() -> store.undo(uriOne));
        assertNull(store.getDocument(uriOne));

        assertDoesNotThrow (() -> store.undo(uriNine));
        assertNull(store.getDocument(uriNine));

        assertDoesNotThrow (() -> store.undo(uriTwo));
        assertNull(store.getDocument(uriTwo));

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo());
        assertThrows(IllegalStateException.class, () -> store.undo(uriNotInStore));

        store.undo(uriEight);
        assertNull(store.getDocument(uriEight));

        store.undo(uriThree);
        assertNull(store.getDocument(uriThree));

        store.undo(uriSeven);
        assertNull(store.getDocument(uriSeven));

        store.undo(uriFour);
        assertNull(store.getDocument(uriFour));

        store.undo(uriSix);
        assertNull(store.getDocument(uriSix));

        store.undo(uriFive);
        assertNull(store.getDocument(uriFive));

        this.closure(store);
    }

    @Test
    public void parameteredDeleteUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.setUpByteDocuments();
        this.putTenBinaryDocuments(store);

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo());

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        assertDoesNotThrow (() -> store.undo(uriNotInStore));

        assertEquals(documentTen.hashCode(), store.putDocument(new ByteArrayInputStream(this.stringTen.getBytes()), uriTen, DocumentFormat.BINARY));

        this.getDocumentHashEqualsTest(store);

        assertTrue(store.deleteDocument(uriTen));
        assertNull(store.getDocument(uriTen));

        assertTrue(store.deleteDocument(uriNine));
        assertNull(store.getDocument(uriNine));

        assertTrue(store.deleteDocument(uriEight));
        assertNull(store.getDocument(uriEight));

        assertTrue(store.deleteDocument(uriSeven));
        assertNull(store.getDocument(uriSeven));

        assertTrue(store.deleteDocument(uriSix));
        assertNull(store.getDocument(uriSix));

        assertTrue(store.deleteDocument(uriFive));
        assertNull(store.getDocument(uriFive));

        assertTrue(store.deleteDocument(uriFour));
        assertNull(store.getDocument(uriFour));

        assertTrue(store.deleteDocument(uriThree));
        assertNull(store.getDocument(uriThree));
        assertTrue(store.deleteDocument(uriTwo));
        assertNull(store.getDocument(uriTwo));
        assertTrue(store.deleteDocument(uriOne));
        assertNull(store.getDocument(uriOne));

        /**
         * By the order the documents where put in, uriOne is on the top, so lets start with uriTen
         * First undo deleting each document, followed by undoing ever adding it in the 1st place
         */
        assertNull(store.getDocument(uriTen));
        store.undo(uriTen);
        assertNotNull(store.getDocument(uriTen));
        assertEquals(documentTen ,store.getDocument(uriTen));
        //On this undo call, the document is again "returned" and yet to be removed because put was called replacing the document with itself.
        store.undo(uriTen);
        store.undo(uriTen); //When the following undo is called, it should remove the document completely.
        assertNull(store.getDocument(uriTen));

        assertNull(store.getDocument(uriOne));
        store.undo(uriOne);
        assertEquals(documentOne ,store.getDocument(uriOne));
        store.undo(uriOne);
        assertNull(store.getDocument(uriOne));

        /**
         * undo a uri that does not exist:
         * (a) make sure it properly throws exception and
         * (b) make sure it leaves the other documents in the correct order
         */
        assertThrows(IllegalStateException.class, () -> store.undo(uriOne));

        assertNull(store.getDocument(uriNine));
        store.undo(uriNine);
        assertEquals(documentNine ,store.getDocument(uriNine));
        store.undo(uriNine);
        assertNull(store.getDocument(uriNine));

        assertNull(store.getDocument(uriTwo));
        store.undo(uriTwo);
        assertEquals(documentTwo ,store.getDocument(uriTwo));
        store.undo(uriTwo);
        assertNull(store.getDocument(uriTwo));

        assertThrows(IllegalStateException.class, () -> store.undo(uriTwo));

        assertNull(store.getDocument(uriEight));
        store.undo(uriEight);
        assertEquals(documentEight ,store.getDocument(uriEight));
        store.undo(uriEight);
        assertNull(store.getDocument(uriEight));

        assertNull(store.getDocument(uriThree));
        store.undo(uriThree);
        assertEquals(documentThree ,store.getDocument(uriThree));
        store.undo(uriThree);
        assertNull(store.getDocument(uriThree));

        assertNull(store.getDocument(uriSeven));
        store.undo(uriSeven);
        assertEquals(documentSeven ,store.getDocument(uriSeven));
        store.undo(uriSeven);
        assertNull(store.getDocument(uriSeven));

        assertNull(store.getDocument(uriFour));
        store.undo(uriFour);
        assertEquals(documentFour ,store.getDocument(uriFour));
        store.undo(uriFour);
        assertNull(store.getDocument(uriFour));

        assertNull(store.getDocument(uriSix));
        store.undo(uriSix);
        assertEquals(documentSix ,store.getDocument(uriSix));
        store.undo(uriSix);
        assertNull(store.getDocument(uriSix));

        assertNull(store.getDocument(uriFive));
        store.undo(uriFive);
        assertEquals(documentFive ,store.getDocument(uriFive));
        store.undo(uriFive);
        assertNull(store.getDocument(uriFive));

        /**
         * undo a uri that does not exist:
         * (a) make sure it properly throws exception and
         * (b) make sure it leaves the other documents in the correct order
         */
        assertThrows(IllegalStateException.class, () -> store.undo(uriOne));
        assertThrows(IllegalStateException.class, () -> store.undo(uriOne));

        this.closure(store);
    }

    @Test
    public void parameterlessDeleteUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.setUpByteDocuments();
        this.putTenBinaryDocuments(store);
        this.getDocumentHashEqualsTest(store);

        assertTrue(store.deleteDocument(uriOne));
        assertNull(store.getDocument(uriOne));
        store.undo();
        assertEquals(documentOne ,store.getDocument(uriOne));

        assertTrue(store.deleteDocument(uriTen));
        assertNull(store.getDocument(uriTen));
        store.undo();
        assertEquals(documentTen ,store.getDocument(uriTen));

        assertTrue(store.deleteDocument(uriTwo));
        assertNull(store.getDocument(uriTwo));
        store.undo();
        assertEquals(documentTwo ,store.getDocument(uriTwo));

        assertTrue(store.deleteDocument(uriNine));
        assertNull(store.getDocument(uriNine));
        store.undo();
        assertEquals(documentNine ,store.getDocument(uriNine));

        assertTrue(store.deleteDocument(uriThree));
        assertNull(store.getDocument(uriThree));
        store.undo();
        assertEquals(documentThree ,store.getDocument(uriThree));

        assertTrue(store.deleteDocument(uriEight));
        assertNull(store.getDocument(uriEight));
        store.undo();
        assertEquals(documentEight ,store.getDocument(uriEight));

        assertTrue(store.deleteDocument(uriFour));
        assertNull(store.getDocument(uriFour));
        store.undo();
        assertEquals(documentFour ,store.getDocument(uriFour));

        assertTrue(store.deleteDocument(uriSeven));
        assertNull(store.getDocument(uriSeven));
        store.undo();
        assertEquals(documentSeven ,store.getDocument(uriSeven));

        assertTrue(store.deleteDocument(uriFive));
        assertNull(store.getDocument(uriFive));
        store.undo();
        assertEquals(documentFive ,store.getDocument(uriFive));

        assertTrue(store.deleteDocument(uriSix));
        assertNull(store.getDocument(uriSix));
        store.undo();
        assertEquals(documentSix ,store.getDocument(uriSix));

        /*Undone adding the documents to begin with*/
        assertEquals(documentTen ,store.getDocument(uriTen));
        store.undo();
        assertNull(store.getDocument(uriTen));

        assertEquals(documentNine ,store.getDocument(uriNine));
        store.undo();
        assertNull(store.getDocument(uriNine));

        assertEquals(documentEight ,store.getDocument(uriEight));
        store.undo();
        assertNull(store.getDocument(uriEight));

        assertEquals(documentSeven ,store.getDocument(uriSeven));
        store.undo();
        assertNull(store.getDocument(uriSeven));

        assertEquals(documentSix ,store.getDocument(uriSix));
        store.undo();
        assertNull(store.getDocument(uriSix));

        assertEquals(documentFive ,store.getDocument(uriFive));
        store.undo();
        assertNull(store.getDocument(uriFive));

        assertEquals(documentFour ,store.getDocument(uriFour));
        store.undo();
        assertNull(store.getDocument(uriFour));

        assertEquals(documentThree ,store.getDocument(uriThree));
        store.undo();
        assertNull(store.getDocument(uriThree));

        assertEquals(documentTwo ,store.getDocument(uriTwo));
        store.undo();
        assertNull(store.getDocument(uriTwo));

        assertEquals(documentOne ,store.getDocument(uriOne));
        store.undo();
        assertNull(store.getDocument(uriOne));

        this.closure(store);
    }

    @Test
    public void deleteTestsInOrder() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenBinaryDocuments(store);
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
    public void simpleDeleteTest() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(stringOne.getBytes()), this.uriOne, DocumentFormat.TXT);
        assertTrue(store.deleteDocument(this.uriOne));
        assertFalse(store.deleteDocument(this.uriOne));

        this.closure(store);
    }

    @Test
    public void deleteTestsBackwards() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenBinaryDocuments(store);
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
        assertNull(store.getDocument(this.uriFour));
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
        this.putTenBinaryDocuments(store);

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

        this.closure(store);
    }


    @Test
    public void deleteDocumentSingleTests() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentFormat.BINARY));

        assertNotNull(store.getDocument(uriOne));
        Document documentOne = new DocumentImpl(uriOne, this.stringOne.getBytes());

        assertEquals(documentOne.hashCode(), store.getDocument(uriOne).hashCode());
        assertNotNull(store.getDocument(uriOne));
        assertTrue(store.deleteDocument(uriOne));
        assertFalse(store.deleteDocument(URI.create("https://www.yahoo.com/")));

        assertNull(store.getDocument(uriOne));

        Document documentOnePartA = new DocumentImpl(uriOne, stringThree.getBytes());
        assertEquals(0, store.putDocument(new ByteArrayInputStream(this.stringThree.getBytes()), uriOne, DocumentFormat.BINARY));
        assertEquals(documentOnePartA.hashCode(), store.putDocument(null, uriOne, DocumentFormat.BINARY));
        assertNull(store.getDocument(uriOne));

        this.closure(store);
    }

    @Test
    public void deleteDocumentMultipleTests() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        this.setUpByteDocuments();
        this.getDocumentNull(store, "Null");
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

        this.putTenBinaryDocuments(store);

        this.getDocumentNull(store,"Not Null");

        this.getDocumentEqualsTest(store);

        this.getDocumentHashEqualsTest(store);
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

        this.getDocumentNull(store, "Null");

        this.closure(store);
    }

    @Test
    public void binaryTest() {
        DocumentStore store = new DocumentStoreImpl();
        this.setUpByteDocuments();
        assertDoesNotThrow (() -> this.putTenBinaryDocuments(store));
        this.getDocumentHashEqualsTest(store);

        this.closure(store);
    }

    @Test
    public void textTest() {
        DocumentStore store = new DocumentStoreImpl();
        assertDoesNotThrow (() -> this.putTenTxtDocuments(store));
        this.setUpTextDocuments();

        this.getDocumentEqualsTest(store);

        this.getDocumentHashEqualsTest(store);

        this.closure(store);
    }

    @Test
    public void hashCodeTextTest() {
        DocumentStore store = new DocumentStoreImpl();
        assertDoesNotThrow (() -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), uriOne, DocumentStore.DocumentFormat.TXT));
        Document documentOne = new DocumentImpl(uriOne, stringOne, new HashMap<>());
        assertTrue(store.getDocument(uriOne).equals(documentOne));
        assertEquals(store.getDocument(uriOne), documentOne);
        assertEquals(store.getDocument(uriOne).hashCode(), documentOne.hashCode());

        this.closure(store);
    }

    @Test void exceptions() {
        DocumentStore store = new DocumentStoreImpl();

        assertThrows(IllegalArgumentException.class, () -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), null, DocumentFormat.TXT));

        assertThrows(IllegalArgumentException.class, () -> store.putDocument(null, null, DocumentFormat.TXT));

        assertDoesNotThrow (() -> store.putDocument(null, this.uriOne, DocumentFormat.TXT));
        assertDoesNotThrow (() -> store.undo());

        assertThrows(IllegalArgumentException.class, () -> store.putDocument(new ByteArrayInputStream(this.stringOne.getBytes()), this.emptyUri, DocumentFormat.TXT));

        assertThrows(IllegalArgumentException.class, () -> store.getDocument(null));

        assertThrows(IllegalArgumentException.class, () -> store.deleteDocument(null));

        assertThrows(IllegalStateException.class, () -> store.undo());

        assertThrows(IllegalArgumentException.class, () -> store.undo(null));

        assertThrows(IllegalStateException.class, () -> store.undo());

        assertThrows(IllegalStateException.class, () -> store.undo(this.uriOne));

        assertThrows(IllegalArgumentException.class, () -> store.search(null));

        assertThrows(IllegalArgumentException.class, () -> store.searchByPrefix(null));

        assertThrows(IllegalArgumentException.class, () -> store.deleteAll(null));

        assertThrows(IllegalArgumentException.class, () -> store.deleteAllWithPrefix(null));

        this.closure(store);
    }
}