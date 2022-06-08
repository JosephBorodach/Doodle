package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import java.net.URI;

public class DocumentImplTests {
    private String stringOne;
    private String stringTwo;
    private String stringThree;
    private String[] stringArrayThree;
    private String stringFour;
    private String stringFive;
    private String stringSix;
    private String stringSeven;
    private String stringEight;
    private String stringNine;
    private String stringTen;
    private URI uriOne;
    private URI uriThree;
    private byte[] bytesOne;
    private String nonEmptyString;
    private String emptyString;
    private String nullString;
    private byte[] nonEmptyBytes;
    private byte[] emptyBytes;
    private byte[] nullBytes;
    private URI nonEmptyUri;
    private URI emptyUri;
    private URI nullUri;

    @BeforeEach
    public void initiate() { //throws Exception
        this.stringOne = "Peter Piper picked a peck of pickled picked peppers";
        this.stringTwo = "A peck of pickled peppers Peter Piper picked pickled peppers while Peter Piper pickled peppers";
        this.stringThree = "pickled PICKLED PiCkLeD pickled PICKLED PiCkLeD";
        this.stringArrayThree = new String[]{"pickled PICKLED PiCkLeD pickled PICKLED PiCkLeD"};
        this.stringFour = "Where's the peck of pickled peppers Peter Piper picked";
        this.stringFive = "How much wood would a woodchuck chuck if a woodchuck could chuck wood";
        this.stringSix = "He would chuck, he would, as much as he could, and chuck as much wood";
        this.stringSeven = "as a woodchuck would if a woodchuck could chuck wood";
        this.stringEight = "She sells";
        this.stringNine = "sea shells";
        this.stringTen = "by the seashore";
        this.uriOne = URI.create("https://www.google.com/");
        this.uriThree = URI.create("https://www.facebook.com/");
        this.bytesOne = stringOne.getBytes();
        this.nonEmptyString = "not empty!";
        this.emptyString = "";
        this.nullString = null;
        this.nonEmptyBytes = nonEmptyString.getBytes();
        this.emptyBytes = emptyString.getBytes();
        this.nullBytes = null;
        this.nonEmptyUri = URI.create("https://www.google.com/");
        this.emptyUri = URI.create("");
        this.nullUri = null;
    }

    @Test
    public void documentImplExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nonEmptyUri, nullBytes);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nonEmptyUri, nullString);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nonEmptyUri, emptyString);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nullUri, nonEmptyString);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nullUri, nonEmptyBytes);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(emptyUri, nonEmptyString);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentImpl(nonEmptyUri, emptyBytes);
        });

        DocumentImpl doc1 = new DocumentImpl(uriOne, bytesOne);
        assertThrows(IllegalArgumentException.class, () -> {
            doc1.wordCount(nullString);
        });

        assertDoesNotThrow (() -> doc1.setLastUseTime(-1));
    }

    @Test
    public void boundryCasesTest () {
        DocumentImpl doc1 = new DocumentImpl(uriOne, bytesOne);
        assertEquals(0, doc1.wordCount(stringOne));
    }

    @Test
    public void getTests () {
        DocumentImpl doc1 = new DocumentImpl(uriOne, stringOne);
        Assertions.assertEquals(2, doc1.wordCount("picked"));
        Assertions.assertEquals(0, doc1.wordCount("potato"));

        DocumentImpl doc3 = new DocumentImpl(uriThree, stringThree);
        Assertions.assertEquals(6, doc3.wordCount("pickled"));
        Assertions.assertEquals(6, doc3.wordCount("PICKLED"));
        Assertions.assertEquals(6, doc3.wordCount("PiCkLeD"));

        Set<String> set = new HashSet<>();
        for (int i = 0; i < stringArrayThree.length; i++) {
            set.add(stringArrayThree[i]);
        }
        Set<String> correctSet = new HashSet<>();
        correctSet.add("PICKLED");
        assertNotEquals(set, doc3.getWords());
        assertEquals(correctSet, doc3.getWords());
    }
}