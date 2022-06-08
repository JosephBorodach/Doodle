package edu.yu.cs.com1320.project;

import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import org.junit.jupiter.api.Test;

import javax.management.RuntimeErrorException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashTableTests {
    @Test
    public void manyInManyOut () {
        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
        for (int i = 0; i < 1000000; i++) {
            table.put(i, i + 1);
            assertEquals(table.get(i), i+1);
        }

        for (int i = 999999; i >= 0; i--) {
            assertEquals(table.put(i, null), i+1);
        }

        /*Make sure that they are deleted*/
        for (int i = 999999; i >= 0; i--) {
            assertNull(table.get(i));
        }
    }

    public void manyStringsInManyOut () {
        HashTableImpl<Integer, String> table = new HashTableImpl<>();
        for (int i = 0; i < 1000000; i++) {
            table.put(i, i + "String" + i);
            assertEquals(table.get(i), i + "String" + i);
        }

        for (int i = 999999; i >= 0; i--) {
            assertEquals(table.put(i, null), i + "String" + i);
        }

        /*Make sure that they are deleted*/
        for (int i = 999999; i >= 0; i--) {
            assertNull(table.get(i));
        }
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
    public void hashTableSizeCheck() {
        HashTableImpl<Integer, String> table = new HashTableImpl<>();

        for (int i = 1; i <= 5; i++) {
            assertNull(table.get(i));
        }

        assertNull(table.put(1, "First"));
        assertNull(table.put(2, "Second"));
        assertNull(table.put(3, "Third"));
        assertNull(table.put(4, "Fourth"));
        assertNull(table.put(5, "Fifth"));

        for (int i = 6; i <= 10; i++) {
            assertNull(table.get(i));
        }

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

        /*The table should remain at size 20 regardless of how many elements are currently in the table*/
        assertEquals("Crash", table.put(1, null));
        assertEquals("Bang", table.put(2, null));
        assertEquals("Boom", table.put(3, null));
        assertEquals("Blah", table.put(4, null));
        assertEquals("Bye", table.put(5, null));

        for (int i = 1; i <= 5; i++) {
            assertNull(table.get(i));
        }

        /*Remove before handing in
        assertEquals(5, table.getNumberOfNodes());
        assertEquals(20, table.size());
        */

        assertEquals("First", table.put(6, null));
        assertEquals("Second", table.put(7, null));
        assertEquals("Third", table.put(8, null));
        assertEquals("Fourth", table.put(9, null));
        assertEquals("Fifth", table.put(10, null));

        for (int i = 6; i <= 10; i++) {
            assertNull(table.get(i));
        }

        /*Remove before handing in
        assertEquals(0, table.getNumberOfNodes());
        assertEquals(20, table.size());
        */
    }

    public void hashTableExceptionsTests() {
        Boolean test = true;
        try {
            new HashTableImpl<>(); //StackImpl must have a constructor that takes no arguments
        } catch (RuntimeErrorException e) {
            test = false;
        }
        assertTrue(test);
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
