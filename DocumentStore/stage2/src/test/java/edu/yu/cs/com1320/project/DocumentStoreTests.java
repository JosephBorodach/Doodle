package edu.yu.cs.com1320.project;

import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.stage2.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class DocumentStoreTests {
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

        /*
        try {
            store.undo(uriTen);
        } catch (IllegalStateException e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);
         */
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
    URI uriEleven = URI.create("https://www.myWebsite.com/");
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
