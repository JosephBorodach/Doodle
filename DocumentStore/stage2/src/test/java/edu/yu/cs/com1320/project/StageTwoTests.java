package edu.yu.cs.com1320.project;

import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;

import edu.yu.cs.com1320.project.impl.StackImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

public class StageTwoTests {

	/**
	 * 
	 */ 
	@Test
	public void stackTests() {
		StackImpl stack = new StackImpl();
		assertNull(stack.peek());		
		assertNull(stack.pop());
		assertEquals(stack.size(), 0);
		//stack.push("");
		stack.push(stringOne);
		stack.push(stringTwo);
		stack.push(stringThree);
		stack.push(stringFour);
		stack.push(stringFive);
		assertEquals(stack.size(), 5);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		assertEquals(stack.size(), 10);

		assertEquals(stack.peek(), 5);
		assertEquals(stack.pop(), 5);
		assertEquals(stack.size(), 9);

		assertEquals(stack.peek(), 4);
		assertEquals(stack.pop(), 4);
		assertEquals(stack.size(), 8);

		assertEquals(stack.peek(), 3);
		assertEquals(stack.pop(), 3);
		assertEquals(stack.size(), 7);

		assertEquals(stack.peek(), 2);
		assertEquals(stack.pop(), 2);
		assertEquals(stack.size(), 6);

		assertEquals(stack.peek(), 1);
		assertEquals(stack.pop(), 1);
		assertEquals(stack.size(), 5);

		assertEquals(stack.peek(), stringFive);
		assertEquals(stack.pop(), stringFive);
		assertEquals(stack.size(), 4);

		assertEquals(stack.peek(), stringFour);
		assertEquals(stack.pop(), stringFour);
		assertEquals(stack.size(), 3);

		assertEquals(stack.peek(), stringThree);
		assertEquals(stack.pop(), stringThree);
		assertEquals(stack.size(), 2);

		assertEquals(stack.peek(), stringTwo);
		assertEquals(stack.pop(), stringTwo);
		assertEquals(stack.size(), 1);

		assertEquals(stack.peek(), stringOne);
		assertEquals(stack.pop(), stringOne);
		assertEquals(stack.size(), 0);

		assertNull(stack.peek());		
		assertNull(stack.pop());
	}

	/**
	 * Push edge cases
	 * null
	 * empty
	 * 
	 */ 
	@Test
	public void stackExceptionsTests() {
		StackImpl stack = new StackImpl();
		boolean test = false;
		try {
			stack.push(null);
		} catch (IllegalArgumentException e) {
			test = true;
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