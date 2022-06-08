package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.Trie;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.Document;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.*;


public class TrieTests {
    //Any search method in TrieImpl or DocumentStoreImpl that returns a collection must return an empty collection, not null, if there are no matches.
    @Test
    public void notThereTests () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Comparator<Integer> comparator = (Integer firstInt, Integer secondInt) -> firstInt.compareTo(secondInt);
        List<Document> emptyDocument = new LinkedList<>();
        Set<URI> emptySet = new HashSet<>();

        assertNull(trie.delete("notThere", 3));
        assertEquals(emptyDocument, trie.getAllSorted("notThere", comparator));
        assertEquals(emptyDocument, trie.getAllWithPrefixSorted("NO", comparator));

        assertEquals(emptySet, trie.deleteAllWithPrefix("notThere"));
        assertEquals(emptySet, trie.deleteAll("notThere"));
    }

    @Test
    public void basicTest () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Comparator<Integer> comparator = (Integer firstInt, Integer secondInt) -> firstInt.compareTo(secondInt);
        List<Integer> unsortedList = new ArrayList<>();
        List<Integer> sortedList = new ArrayList<>();

        trie.put("A", 1);
        unsortedList.add(1);
        assertEquals(0, trie.getAllSorted("C", comparator).size());
        assertEquals(1, trie.getAllSorted("A", comparator).size());
        assertEquals(unsortedList, trie.getAllSorted("A", comparator));
        trie.put("B", 3);
        trie.put("B", 2);
        trie.put("A", 3);
        trie.put("A", 2);

        unsortedList.add(3);
        unsortedList.add(2);
        sortedList.add(1);
        sortedList.add(2);
        sortedList.add(3);

        assertNotEquals(sortedList, trie.getAllSorted("B", comparator));
        assertNotEquals(unsortedList, trie.getAllSorted("B", comparator));

        assertNotEquals(unsortedList, trie.getAllSorted("A", comparator));
        assertEquals(sortedList, trie.getAllSorted("A", comparator));

        sortedList.remove(0);
        int returnValue = trie.delete("A", 1);
        assertEquals(1, returnValue);
        assertEquals(sortedList, trie.getAllSorted("A", comparator));

        List<Integer> bList = new ArrayList<>();
        bList.add(2);
        bList.add(3);
        assertEquals(bList, trie.getAllSorted("B", comparator));
        assertEquals(2, trie.getAllSorted("B", comparator).size());
    }

    @Test
    public void trieSortedTests () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Comparator<Integer> comparator = (Integer firstInt, Integer secondInt) -> firstInt.compareTo(secondInt);
        trie.put("PETER", 1);
        trie.put("PETER111", 2);
        trie.put("PETER222", 3);
        trie.put("peter222", 4);
        trie.put("PETER222", 5);
        trie.put("peter", 10);
        trie.put("peter111", 9);
        trie.put("PETER222", 8);
        trie.put("peter222", 7);
        trie.put("peter222", 6);
        trie.put("PICKED", 11);
        trie.put("PICKED111", 12);
        trie.put("PICKED222", 13);
        trie.put("PICKED222", 14);
        trie.put("PICKED222", 15);
        trie.put("picked", 20);
        trie.put("picked111", 19);
        trie.put("picked222", 18);
        trie.put("picked222", 17);
        trie.put("picked222", 16);
        List<Integer> list = trie.getAllSorted("peter111", comparator);
        Assertions.assertEquals(2, list.size());

        list.addAll(trie.getAllSorted("peter222", comparator));
        Assertions.assertEquals(8, list.size());

        Set<Integer> deletedSet = trie.deleteAll("peter111");
        Assertions.assertEquals(2, deletedSet.size());

        List<Integer> emptyList = trie.getAllSorted("peter111", comparator);
        Assertions.assertEquals(0, emptyList.size());

        //getAllSorted doesn't do prefixes
        emptyList = trie.getAllSorted("pe", comparator);
        Assertions.assertEquals(0, emptyList.size());
    }

    @Test
    public void triePrefixSortedTests () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Comparator<Integer> comparator = (Integer firstInt, Integer secondInt) -> firstInt.compareTo(secondInt);
        trie.put("PETER", 1);
        trie.put("PETER111", 2);
        trie.put("PETER222", 3);
        trie.put("peter222", 4);
        trie.put("PETER222", 5);
        trie.put("peter", 10);
        trie.put("peter111", 9);
        trie.put("PETER222", 8);
        trie.put("peter222", 7);
        trie.put("peter222", 6);
        trie.put("PICKED", 11);
        trie.put("PICKED111", 12);
        trie.put("PICKED222", 13);
        trie.put("PICKED222", 14);
        trie.put("PICKED222", 15);
        trie.put("picked", 20);
        trie.put("picked111", 19);
        trie.put("picked222", 18);
        trie.put("picked222", 17);
        trie.put("picked222", 16);
        List<Integer> list = trie.getAllWithPrefixSorted("peter", comparator);
        Assertions.assertEquals(10, list.size());

        List<Integer> piList = trie.getAllWithPrefixSorted("pi", comparator);
        Assertions.assertEquals(10, piList.size());

        List<Integer> pickList = trie.getAllWithPrefixSorted("pick", comparator);
        Assertions.assertEquals(10, pickList.size());

        List<Integer> pList = trie.getAllWithPrefixSorted("p", comparator);
        Assertions.assertEquals(20, pList.size());


        Set<Integer> deletedSet = trie.deleteAllWithPrefix("PE");
        Assertions.assertEquals(10, deletedSet.size());

        //It was just deleted, so should be zero
        List<Integer> emptyList = trie.getAllWithPrefixSorted("pe", comparator);
        Assertions.assertEquals(0, emptyList.size());

        //Should only be 10 bc half of the list was already deleted
        deletedSet = trie.deleteAllWithPrefix("P");
        Assertions.assertEquals(10, deletedSet.size());
    }

    @Test
    public void manyIn () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Comparator<Integer> comparator = (Integer firstInt, Integer secondInt) -> firstInt.compareTo(secondInt);
        List<Integer> sortedList = new ArrayList<>();
        List<Integer> doubleList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            sortedList.add(i);
            doubleList.add(i);
            doubleList.add(i);
            set.add(i);
        }

        for (int i = 0; i < 1000; i++) { //The values are put in ascending order
            trie.put("H2O", i);
        }
        for (int i = 0; i < 1000; i++) { //The values are put in ascending order
            trie.put("HFO", i);
        }

        assertEquals(sortedList, trie.getAllSorted("H2O", comparator));
        assertEquals(sortedList, trie.getAllWithPrefixSorted("H2", comparator));

        //Are we not supposed to be having duplicates?
        assertEquals(sortedList, trie.getAllWithPrefixSorted("H", comparator));
        assertNotEquals(doubleList, trie.getAllWithPrefixSorted("H", comparator));

        for (int i = 0; i < 1000; i++) { //The values are put in ascending order
            trie.put("LALALALALA", i);
        }
        assertEquals(sortedList, trie.getAllWithPrefixSorted("L", comparator));
        assertEquals(sortedList, trie.getAllWithPrefixSorted("LALA", comparator));
        assertEquals(sortedList, trie.getAllWithPrefixSorted("LALALALALA", comparator));

        /*
        for (int i = 0; i < 1000; i++) {
            if (i != 0) {
                Assertions.assertEquals(i, trie.delete("H20", i));
            } else {
                Assertions.assertEquals(null, trie.delete("H20", i));
            }
        }
         */
        assertEquals(set, trie.deleteAllWithPrefix("LA"));
        assertEquals(set, trie.deleteAll("HFO"));
    }

    @Test
    public void basicDeleteTests () {
        TrieImpl<Integer> trie = new TrieImpl<>();
        Set<Integer> numberSet = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            numberSet.add(i);
            trie.put("A", i);
        }
        assertEquals(trie.deleteAll("A"), numberSet);

        for (int i = 0; i < 1000; i++) {
            trie.put("B", i);
        }

        for (int i = 0; i < 1000; i++) {
            Assertions.assertEquals(i, trie.delete("B", i));
        }
    }
}
