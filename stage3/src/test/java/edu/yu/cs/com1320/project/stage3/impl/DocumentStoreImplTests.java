package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import edu.yu.cs.com1320.project.stage3.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage3.impl.DocumentStoreImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;

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
    private byte[] bytesOne;
    private byte[] bytesTwo;
    private byte[] bytesThree;
    private byte[] bytesFour;
    private byte[] bytesFive;
    private byte[] bytesSix;
    private byte[] bytesSeven;
    private byte[] bytesEight;
    private byte[] bytesNine;
    private byte[] bytesTen;
    private InputStream inputStreamOne;
    private InputStream inputStreamTwo;
    private InputStream inputStreamThree;
    private InputStream inputStreamFour;
    private InputStream inputStreamFive;
    private InputStream inputStreamSix;
    private InputStream inputStreamSeven;
    private InputStream inputStreamEight;
    private InputStream inputStreamNine;
    private InputStream inputStreamTen;
    private URI emptyUri;
    private URI nullUri;

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
        this.bytesOne = stringOne.getBytes();
        this.bytesTwo = stringTwo.getBytes();
        this.bytesThree = stringThree.getBytes();
        this.bytesFour = stringFour.getBytes();
        this.bytesFive = stringFive.getBytes();
        this.bytesSix = stringSix.getBytes();
        this.bytesSeven = stringSeven.getBytes();
        this.bytesEight = stringEight.getBytes();
        this.bytesNine = stringNine.getBytes();
        this.bytesTen = stringTen.getBytes();
        this.inputStreamOne = new ByteArrayInputStream(stringOne.getBytes());
        this.inputStreamTwo = new ByteArrayInputStream(stringTwo.getBytes());
        this.inputStreamThree = new ByteArrayInputStream(stringThree.getBytes());
        this.inputStreamFour = new ByteArrayInputStream(stringFour.getBytes());
        this.inputStreamFive = new ByteArrayInputStream(stringFive.getBytes());
        this.inputStreamSix = new ByteArrayInputStream(stringSix.getBytes());
        this.inputStreamSeven = new ByteArrayInputStream(stringSeven.getBytes());
        this.inputStreamEight = new ByteArrayInputStream(stringEight.getBytes());
        this.inputStreamNine = new ByteArrayInputStream(stringNine.getBytes());
        this.inputStreamTen = new ByteArrayInputStream(stringTen.getBytes());
        this.emptyUri = URI.create("");
        this.nullUri = null;
    }

    @Test
    public void complicatedUndoTest() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        List<URI> uriList = new ArrayList<>();
        List<Document> completeList;
        List<Document> emptyList = new ArrayList<>();
        Set<URI> deleted;

        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        assertEquals(0, store.putDocument(this.inputStreamOne, this.uriOne, DocumentFormat.TXT));
        List<Document> justOneList = store.search("pickled");
        assertEquals(1, justOneList.size());

        assertEquals(0, store.putDocument(this.inputStreamTwo, this.uriTwo, DocumentFormat.TXT));
        List<Document> twoList = store.search("pickled");
        assertEquals(2, twoList.size());

        assertEquals(0, store.putDocument(this.inputStreamThree, this.uriThree, DocumentFormat.TXT));
        List<Document> threeList = store.search("pickled");
        assertEquals(3, threeList.size());

        assertEquals(0, store.putDocument(this.inputStreamFour, this.uriFour, DocumentFormat.TXT));
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
        test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void theHardTest() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        List<URI> uriList = new ArrayList<>();
        List<Document> completeList;
        Set<URI> deleted;

        boolean test = false;
        try {
            store.undo(this.uriOne);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        assertEquals(0, store.putDocument(this.inputStreamOne, this.uriOne, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(this.inputStreamTwo, this.uriTwo, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(this.inputStreamThree, this.uriThree, DocumentFormat.TXT));
        assertEquals(0, store.putDocument(this.inputStreamFour, this.uriFour, DocumentFormat.TXT));
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
        test = false;
        try {
            store.undo(this.uriOne);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        test = false;
        try {
            store.undo(this.uriTwo);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        test = false;
        try {
            store.undo(this.uriThree);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        test = false;
        try {
            store.undo(this.uriFour);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void simpleSearch() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        Document docOne = new DocumentImpl(this.uriOne, stringOne); //Peter Piper picked a peck of pickled picked peppers
        Document docTwo = new DocumentImpl(this.uriTwo, stringTwo); //A peck of pickled peppers Peter Piper picked
        store.putDocument(this.inputStreamOne, this.uriOne, DocumentFormat.TXT);
        store.putDocument(this.inputStreamTen, this.uriTen, DocumentFormat.TXT);
        store.putDocument(this.inputStreamTwo, this.uriTwo, DocumentFormat.TXT);
        List<Document> correctOrder = new ArrayList<>();
        correctOrder.add(docOne);
        correctOrder.add(docTwo);
        assertEquals(0, store.search("Laugh").size());
        assertEquals(2, store.search("picked").size());
        assertEquals(correctOrder, store.search("picked"));
        assertEquals(correctOrder, store.search("PICKED"));
    }

    @Test
    public void simpleSearchByPrefix() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.putDocument(this.inputStreamOne, this.uriOne, DocumentFormat.TXT); //Peter Piper picked a peck of pickled picked peppers
        store.putDocument(this.inputStreamTwo, this.uriTwo, DocumentFormat.TXT); //A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers
        assertEquals(0, store.searchByPrefix("Laugh").size());
        assertEquals(2, store.searchByPrefix("pickled").size());
        assertEquals(2, store.searchByPrefix("pickled").size());
        assertEquals(2, store.searchByPrefix("PICKLED").size());
        assertEquals(2, store.searchByPrefix("PiCkLeD").size());
        assertEquals(2, store.searchByPrefix("PiCk").size());
        assertEquals(2, store.searchByPrefix("P").size());
        assertEquals(2, store.searchByPrefix("PET").size());
        assertEquals(1, store.searchByPrefix("picked").get(1).wordCount("PICKED"));
        store.putDocument(this.inputStreamThree, this.uriThree, DocumentFormat.TXT); //A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers
        assertEquals(6, store.search("PICKLED").get(0).wordCount("PICKLED"));
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

        test = false;
        try {
            store.search("does not exist");
        } catch (IllegalArgumentException e) {
            test = true;
        }
        assertFalse(test);
    }

    @Test
    public void undoExceptions () throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        store.undo();
        assertNull(store.getDocument(uriOne));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        test = false;
        try {
            store.undo(uriNotInStore);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
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

    }

    @Test
    public void deleteNotExistantURI () throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        test = false;
        try {
            store.undo(uriNotInStore);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown
    }

    @Test
    public void parameterlessUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
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

        store.undo();
        assertNull(store.getDocument(uriTen));

        store.undo();
        assertNull(store.getDocument(uriNine));

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        test = false;
        try {
            store.undo(uriNotInStore);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown

        store.undo();
        assertNull(store.getDocument(uriEight));

        store.undo();
        assertNull(store.getDocument(uriSeven));

        store.undo();
        assertNull(store.getDocument(uriSix));

        store.undo();
        assertNull(store.getDocument(uriFive));

        store.undo();
        assertNull(store.getDocument(uriFour));

        store.undo();
        assertNull(store.getDocument(uriThree));

        store.undo();
        assertNull(store.getDocument(uriTwo));

        store.undo();
        assertNull(store.getDocument(uriOne));
    }

    @Test
    public void parameteredUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
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

        store.undo(uriTen);
        assertNull(store.getDocument(uriTen));

        store.undo(uriOne);
        assertNull(store.getDocument(uriOne));

        store.undo(uriNine);
        assertNull(store.getDocument(uriNine));

        store.undo(uriTwo);
        assertNull(store.getDocument(uriTen));

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        test = false;
        try {
            store.undo(uriNotInStore);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertFalse(test); //Should be false bc there was something to undo and no exception should have been thrown

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
    }

    @Test
    public void parameteredDeleteUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
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

        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentFormat.BINARY));

        URI uriNotInStore = URI.create("https://www.myWebsite.com/");
        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        Boolean errorThrown = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertFalse(errorThrown); //Should be false bc there was something to undo and no exception should have been thrown

        assertNull(store.getDocument(uriNotInStore));
        assertFalse(store.deleteDocument(uriNotInStore));
        errorThrown = false;
        try {
            store.undo(uriNotInStore);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertFalse(errorThrown); //Should be false bc there was something to undo and no exception should have been thrown

        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentFormat.BINARY));
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
        assertEquals(documentTen ,store.getDocument(uriTen));
        store.undo(uriTen);
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
        errorThrown = false;
        try {
            store.undo(uriOne);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);

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

        errorThrown = false;
        try {
            store.undo(uriTwo);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);

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
        errorThrown = false;
        try {
            store.undo(uriOne);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);

        errorThrown = false;
        try {
            store.undo(uriOne);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);

    }




    @Test
    public void parameterlessDeleteUndoTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
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
    /*
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

    String nonEmptyString = "not empty!";
    String emptyString = "";
    String nullString = null;
    byte[] nonEmptyBytes = nonEmptyString.getBytes();
    byte[] emptyBytes = emptyString.getBytes();
    byte[] nullBytes = null;
    URI nonEmptyUri = URI.create("https://www.google.com/");
    */
}
