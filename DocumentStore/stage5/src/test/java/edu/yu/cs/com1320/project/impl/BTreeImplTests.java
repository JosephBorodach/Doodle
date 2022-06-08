package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;

import java.io.File;
import java.net.URI;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BTreeImplTests {

    @Test
    public void simpleTest() throws Exception {
        BTreeImpl<URI, Document> tree = new BTreeImpl<>();
        URI uri = URI.create("https://www.google.com/");
        String stringOne = "Peter Piper picked a peck of pickled picked peppers";
        byte[] bytesOne = stringOne.getBytes();
        Document doc = new DocumentImpl(uri, bytesOne);
        tree.put(uri, doc);
        File file = new File("C:/Users/josephborodach/temp/new/hello/by");
        DocumentPersistenceManager manager = new DocumentPersistenceManager(file);
        tree.setPersistenceManager(manager);
        assertThrows(IllegalArgumentException.class, () -> tree.moveToDisk(null));
        tree.moveToDisk(uri);
        manager.deserialize(uri);
    }

    @Test
    public void putExeptionsTest() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        assertThrows(IllegalArgumentException.class, () -> tree.put(null, 1));
    }

    @Test
    public void getExeptionsTest() throws Exception {
        BTreeImpl<URI, Document> tree = new BTreeImpl<>();
        assertThrows(IllegalArgumentException.class, () -> tree.get(null));

        URI uri = URI.create("https://www.google.com/");
        String stringOne = "Peter Piper picked a peck of pickled picked peppers";
        byte[] bytesOne = stringOne.getBytes();
        Document doc = new DocumentImpl(uri, bytesOne);
        tree.put(uri, doc);
        File file = new File("C:/Users/josephborodach/temp/new/hello/by");
        DocumentPersistenceManager manager = new DocumentPersistenceManager(file);
        tree.setPersistenceManager(manager);
        tree.moveToDisk(uri);
        assertThrows(IllegalArgumentException.class, () -> tree.moveToDisk(uri));
        assertThrows(IllegalArgumentException.class, () -> tree.moveToDisk(null));
    }

    @Test
    public void getTest() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        tree.put(1, 1);
        assertEquals(1, tree.get(1));

        tree.put(2, 2);
    }

    @Test
    public void heightTest() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        //assertEquals(0, tree.size());
        //assertEquals(0, tree.height());

        //Basically the max n for a given height is n ** 3 - n being the # of nodes or chunks of nodes - 0, 2, 6, 18
        for (int i = 1; i < 468; i++) {
            tree.put(i, i);
            /*
            if (i < 6) {
                assertEquals(0, tree.height());
            } else if (i > 6 && i < 18) {
                assertEquals(1, tree.height());
            } else if (i >= 18 && i < 54) {
                assertEquals(2, tree.height());
            } else if (i >= 54 && i < 162) {
                assertEquals(3, tree.height());
            } else if (i >= 162 && i < 486) {
                assertEquals(4, tree.height());
            } else if (i >= 486 && i < 1458) {
                assertEquals(5, tree.height());
            }
             */
        }

        //assertEquals(467, tree.size());

        for (int i = 1; i < 468; i++) {
            assertEquals(i, tree.put(i, null));
        }

        //The size wont actually change bc we are just replacing deleted node with null values
        //assertEquals(467, tree.size());
    }

    /*
    @Test
    public void printTests() throws Exception {
        BTreeImpl<URI, Document> tree = new BTreeImpl<>();
        File file = new File("C:/Users/josephborodach/temp/new/hello/by");
        DocumentPersistenceManager manager = new DocumentPersistenceManager(file);
        tree.setPersistenceManager(manager);
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            tree.put(uri, new DocumentImpl(uri, String.valueOf(i).getBytes()));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            System.out.println(new DocumentImpl(uri, String.valueOf(i).getBytes()));
            System.out.println(tree.get(uri));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            System.out.println(new DocumentImpl(uri, String.valueOf(i).getBytes()));
            System.out.println(tree.put(uri, null));
            System.out.println(tree.get(uri));
        }
    }
    */

    @Test
    public void serializeTests() throws Exception {
        BTreeImpl<URI, Document> tree = new BTreeImpl<>();
        File file = new File("C:/Users/josephborodach/temp/new/hello/by");
        DocumentPersistenceManager manager = new DocumentPersistenceManager(file);
        tree.setPersistenceManager(manager);
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            tree.put(uri, new DocumentImpl(uri, String.valueOf(i).getBytes()));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            assertEquals(new DocumentImpl(uri, String.valueOf(i).getBytes()), tree.get(uri));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            assertEquals(new DocumentImpl(uri, String.valueOf(i).getBytes()), tree.put(uri, null));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create(String.valueOf(i));
            assertNull(tree.get(uri));
        }
        /*
        for (int i = 0; i < 10; i++) {
            tree.moveToDisk(URI.create("i"));
        }
        for (int i = 0; i < 10; i++) {
            URI uri = URI.create("i");
            assertEquals(new DocumentImpl(uri, "i".getBytes()), tree.get(uri));
        }
         */
    }

    @Test
    public void simpleGetTest() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        //assertEquals(0, tree.size());
        //assertEquals(0, tree.height());
        assertNull(tree.get(1));
        assertNull(tree.put(1, 1));
        assertEquals(1, tree.get(1));
        assertEquals(1, tree.put(1, 2));
        assertEquals(2, tree.get(1));
        //assertEquals(1, tree.size());
        //assertEquals(0, tree.height());
        assertNull(tree.get(2));
    }

    @Test
    public void putAndGetTests() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        for (int i = 0; i < 1000; i++) {
            assertNull(tree.put(i, i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, tree.get(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, tree.put(i, i+100));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i+100, tree.get(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i+100, tree.put(i, null));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(null, tree.get(i));
        }
    }

    @Test
    public void boundaryEntries() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        assertNull(tree.put(0, 1));
        assertNull(tree.put(5, 10));
        assertEquals(1, tree.get(0));
        assertEquals(10, tree.get(5));
        assertEquals(10, tree.put(5, 100));
        assertEquals(100, tree.get(5));

        assertEquals(1, tree.put(0, 10));
        assertEquals(10, tree.get(0));

        assertNull(tree.put(6, 1000));
        assertEquals(1000, tree.get(6));
        assertEquals(1000, tree.put(6, 10000));
        assertEquals(10000, tree.get(6));
    }

    @Test
    public void collisionsTest() {
        BTreeImpl<Integer, Integer> tree = new BTreeImpl<>();
        assertNull(tree.put(1, 1));
        assertNull(tree.put(6, 6));
        assertNull(tree.put(11, 11));
        assertNull(tree.put(12, 12));
        assertNull(tree.put(18, 18));

        assertEquals(1, tree.get(1));
        assertEquals(6, tree.get(6));
        assertEquals(11, tree.get(11));
        assertEquals(12, tree.get(12));
        assertEquals(18, tree.get(18));
    }
}
