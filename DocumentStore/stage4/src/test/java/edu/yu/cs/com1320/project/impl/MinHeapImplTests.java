package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

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
    private URI uri1;
    private URI uri2;
    private URI uri3;
    private URI uri4;
    private URI uri5;
    private URI uri6;
    private URI uri7;
    private URI uri8;
    private URI uri9;
    private URI uri10;
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
    Document document1;
    Document document2;
    Document document3;
    Document document4;
    Document document5;
    Document document6;
    Document document7;
    Document document8;
    Document document9;
    Document document10;
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
        this.uri1 = URI.create("https://www.google.com/");
        this.uri2 = URI.create("https://www.youtube.com/");
        this.uri3 = URI.create("https://www.facebook.com/");
        this.uri4 = URI.create("https://www.wikipedia.org/");
        this.uri5 = URI.create("https://www.yahoo.com/");
        this.uri6 = URI.create("https://www.Twitter.com/");
        this.uri7 = URI.create("https://www.Instagram.com/");
        this.uri8 = URI.create("https://www.Baidu.com/");
        this.uri9 = URI.create("https://www.whatsapp.com/");
        this.uri10 = URI.create("https://www.netflix.com/");
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
        this.updateBytes();
        this.emptyUri = URI.create("");
        this.nullUri = null;
        this.document1 = new DocumentImpl(uriOne, bytesOne);
        this.document2 = new DocumentImpl(uriTwo, bytesTwo);
        this.document3 = new DocumentImpl(uriThree, bytesThree);
        this.document4 = new DocumentImpl(uriFour, bytesFour);
        this.document5 = new DocumentImpl(uriFive, bytesFive);
        this.document6 = new DocumentImpl(uriSix, bytesSix);
        this.document7 = new DocumentImpl(uriSeven, bytesSeven);
        this.document8 = new DocumentImpl(uriEight, bytesEight);
        this.document9 = new DocumentImpl(uriNine, bytesNine);
        this.document10 = new DocumentImpl(uriTen, bytesTen);
        this.emptyList = new ArrayList<>();
    }

    public void updateBytes () {
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
    }

    public void putTenTxtDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamFive, uriFive, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentStore.DocumentFormat.TXT));
    }

    public void putFourTxtDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.TXT));
    }

    public void getDocumentNull(DocumentStore store, int i) {
        if (i == 1) {
            assertNull(store.getDocument(uriOne));
            assertNull(store.getDocument(uriTwo));
            assertNull(store.getDocument(uriThree));
            assertNull(store.getDocument(uriFour));
            assertNull(store.getDocument(uriFive));
        } else {
            assertNull(store.getDocument(uriSix));
            assertNull(store.getDocument(uriSeven));
            assertNull(store.getDocument(uriEight));
            assertNull(store.getDocument(uriNine));
            assertNull(store.getDocument(uriTen));
        }
    }

    public void getDocumentNotNull(DocumentStore store, int i) {
        if (i == 1) {
            assertNotNull(store.getDocument(uriOne));
            assertNotNull(store.getDocument(uriTwo));
            assertNotNull(store.getDocument(uriThree));
            assertNotNull(store.getDocument(uriFour));
            assertNotNull(store.getDocument(uriFive));
        } else {
            assertNotNull(store.getDocument(uriSix));
            assertNotNull(store.getDocument(uriSeven));
            assertNotNull(store.getDocument(uriEight));
            assertNotNull(store.getDocument(uriNine));
            assertNotNull(store.getDocument(uriTen));
        }
    }

    @Test
    public void heapExceptionsTest () {
        MinHeap<Document> heap = new MinHeapImpl<>();
        assertThrows(IllegalArgumentException.class, () -> {
            heap.reHeapify(null);
        });
        //The heap only doens't throw an exception because it is empty
        assertDoesNotThrow (() -> heap.reHeapify(this.document1));

        heap.insert(this.document1);
        assertThrows(NoSuchElementException.class, () -> {
            heap.reHeapify(this.document2);
        });
    }

    @Test
    public void basicHeapTest() {
        MinHeap<Integer> heap = new MinHeapImpl<>();
        for (int i = 1; i <= 500; i++) {
            heap.insert(1001-i);
            heap.insert(i);
        }
        for (int i = 1; i <= 100; i++) {
            Assertions.assertEquals(i, heap.remove());
        }
    }

    public void insertDocuments(MinHeap<Document> heap) {
        heap.insert(this.document1);
        heap.insert(this.document2);
        heap.insert(this.document3);
        heap.insert(this.document4);
        heap.insert(this.document5);
        heap.insert(this.document6);
        heap.insert(this.document7);
        heap.insert(this.document8);
        heap.insert(this.document9);
        heap.insert(this.document10);
    }

    @Test
    public void heapDocumentTests() {
        MinHeap<Document> heap = new MinHeapImpl<>();
        this.insertDocuments(heap);
        assertEquals(0, this.document1.getLastUseTime());
        assertEquals(0, this.document2.getLastUseTime());
        assertEquals(0, this.document3.getLastUseTime());
        assertEquals(0, this.document4.getLastUseTime());
        assertEquals(0, this.document5.getLastUseTime());
        assertEquals(0, this.document6.getLastUseTime());
        assertEquals(0, this.document7.getLastUseTime());
        assertEquals(0, this.document8.getLastUseTime());
        assertEquals(0, this.document9.getLastUseTime());
        assertEquals(0, this.document10.getLastUseTime());
        assertEquals(this.document1, heap.remove());
        assertEquals(this.document10, heap.remove());
        assertEquals(this.document9, heap.remove());
        assertEquals(this.document8, heap.remove());
        assertEquals(this.document7, heap.remove());
        assertEquals(this.document6, heap.remove());
        assertEquals(this.document5, heap.remove());
        assertEquals(this.document4, heap.remove());
        assertEquals(this.document3, heap.remove());
        assertEquals(this.document2, heap.remove());

        this.insertDocuments(heap);

        this.document1.setLastUseTime(3);
        heap.reHeapify(this.document1);
        this.document2.setLastUseTime(2);
        heap.reHeapify(this.document2);
        this.document3.setLastUseTime(1);
        heap.reHeapify(this.document3);

        this.document10.setLastUseTime(4);
        heap.reHeapify(this.document10);
        this.document9.setLastUseTime(5);
        heap.reHeapify(this.document9);
        this.document8.setLastUseTime(6);
        heap.reHeapify(this.document8);
        this.document7.setLastUseTime(7);
        heap.reHeapify(this.document7);
        this.document6.setLastUseTime(8);
        heap.reHeapify(this.document6);
        this.document5.setLastUseTime(9);
        heap.reHeapify(this.document5);
        this.document4.setLastUseTime(10);
        heap.reHeapify(this.document4);

        assertEquals(this.document3, heap.remove());
        assertEquals(this.document2, heap.remove());
        assertEquals(this.document1, heap.remove());
        assertEquals(this.document10, heap.remove());
        assertEquals(this.document9, heap.remove());
        assertEquals(this.document8, heap.remove());
        assertEquals(this.document7, heap.remove());
        assertEquals(this.document6, heap.remove());
        assertEquals(this.document5, heap.remove());
        assertEquals(this.document4, heap.remove());
    }

    @Test
    public void searchOverFlowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        store.search("pickled");
        store.setMaxDocumentCount(4);
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertNull(store.getDocument(uriFive));
        this.getDocumentNull(store, 6);
    }

    @Test
    public void overFLowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putTenTxtDocuments(store);
        store.setMaxDocumentCount(5);
        this.getDocumentNull(store, 1);
        this.getDocumentNotNull(store, 6);

        assertThrows(IllegalStateException.class, () -> {
            store.undo(uriOne);
        });
        assertThrows(IllegalStateException.class, () -> {
            store.undo(uriTwo);
        });
        assertThrows(IllegalStateException.class, () -> {
            store.undo(uriThree);
        });
        assertThrows(IllegalStateException.class, () -> {
            store.undo(uriFour);
        });
        assertThrows(IllegalStateException.class, () -> {
            store.undo(uriFive);
        });

        assertDoesNotThrow (() -> store.undo(this.uriSix));
        assertDoesNotThrow (() -> store.undo(this.uriSeven));
        assertDoesNotThrow (() -> store.undo(this.uriEight));
        assertDoesNotThrow (() -> store.undo(this.uriNine));
        assertDoesNotThrow (() -> store.undo(this.uriTen));

        this.getDocumentNull(store, 6);
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamFive, uriFive, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamSix, uriSix, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamSeven, uriSeven, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamEight, uriEight, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamNine, uriNine, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamTen, uriTen, DocumentStore.DocumentFormat.TXT);
        });
        this.updateBytes();
        this.putTenTxtDocuments(store);
        assertNotNull(store.search("Peter"));
        store.setMaxDocumentCount(0);
        this.getDocumentNull(store, 1);
        this.getDocumentNull(store, 6);

        assertEquals(this.emptyList, store.search("Peter"));

        this.updateBytes();
        store.setMaxDocumentCount(10);

        assertEquals(0, store.putDocument(inputStreamSix, uriSix, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamSeven, uriSeven, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamEight, uriEight, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamNine, uriNine, DocumentStore.DocumentFormat.TXT));
        assertEquals(0, store.putDocument(inputStreamTen, uriTen, DocumentStore.DocumentFormat.TXT));

        assertEquals(store.getDocument(uriSix).hashCode(), store.putDocument(inputStreamOne, uriSix, DocumentStore.DocumentFormat.TXT));
        assertEquals(store.getDocument(uriSeven).hashCode(), store.putDocument(inputStreamTwo, uriSeven, DocumentStore.DocumentFormat.TXT));
        assertEquals(store.getDocument(uriEight).hashCode(), store.putDocument(inputStreamThree, uriEight, DocumentStore.DocumentFormat.TXT));
        assertEquals(store.getDocument(uriNine).hashCode(), store.putDocument(inputStreamFour, uriNine, DocumentStore.DocumentFormat.TXT));
        assertEquals(store.getDocument(uriTen).hashCode(), store.putDocument(inputStreamFive, uriTen, DocumentStore.DocumentFormat.TXT));
        store.setMaxDocumentCount(0);
        store.setMaxDocumentCount(10);

        this.updateBytes();
        this.putTenTxtDocuments(store);

        //Flip the delete overFlow order
        this.getDocumentNotNull(store, 1);
        store.setMaxDocumentCount(5);
        this.getDocumentNull(store, 6);

        this.getDocumentNotNull(store, 1);
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

        this.getDocumentNull(store, 1);

        this.getDocumentNotNull(store, 6);

        //redoing the first 5 docs should cause the latter 5 to be removed from memory
        assertDoesNotThrow (() -> store.undo(this.uriOne));
        assertDoesNotThrow (() -> store.undo(this.uriTwo));
        assertDoesNotThrow (() -> store.undo(this.uriThree));
        assertDoesNotThrow (() -> store.undo(this.uriFour));
        assertDoesNotThrow (() -> store.undo(this.uriFive));
        this.getDocumentNull(store, 6);
        store.setMaxDocumentCount(2);

        assertNull(store.getDocument(uriOne));
        assertNull(store.getDocument(uriTwo));
        assertNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour)); //These are the 2 last used docs
        assertNotNull(store.getDocument(uriFive));
    }

    public void putFourByteDocuments(DocumentStore store) throws IOException {
        assertEquals(0, store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamThree, uriThree, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(inputStreamFour, uriFour, DocumentStore.DocumentFormat.BINARY));
    }

    @Test
    public void bytesOverFLowTests() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        this.putFourByteDocuments(store);

        store.setMaxDocumentBytes(120);
        assertNull(store.getDocument(uriOne));
        assertNull(store.getDocument(uriTwo));
        assertNotNull(store.getDocument(uriThree));
        assertNotNull(store.getDocument(uriFour));
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamOne, uriOne, DocumentStore.DocumentFormat.TXT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            store.putDocument(inputStreamTwo, uriTwo, DocumentStore.DocumentFormat.TXT);
        });
        this.updateBytes();
        assertEquals(store.getDocument(uriThree).hashCode(), store.putDocument(inputStreamOne, uriThree, DocumentStore.DocumentFormat.BINARY));
        assertEquals(store.getDocument(uriFour).hashCode(), store.putDocument(inputStreamTwo, uriFour, DocumentStore.DocumentFormat.BINARY));
        assertTrue((this.document1.getDocumentBinaryData().length + this.document2.getDocumentBinaryData().length) > 120);
        assertFalse(store.deleteDocument(uriThree)); //It was deleted bc string 2 put it over
        assertTrue(store.deleteDocument(uriFour));

        store.setMaxDocumentBytes(10000);
        this.updateBytes();
        this.putFourByteDocuments(store);
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        store.setMaxDocumentCount(2);
        assertNotNull(store.getDocument(uriOne));
        assertNotNull(store.getDocument(uriTwo));
        assertNull(store.getDocument(uriThree));
        assertNull(store.getDocument(uriFour));

        store.setMaxDocumentBytes(10);
        assertNull(store.getDocument(uriOne));
        assertNull(store.getDocument(uriTwo));
    }
}