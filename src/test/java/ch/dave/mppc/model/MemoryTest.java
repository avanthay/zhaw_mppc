package ch.dave.mppc.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemoryTest {

	private Memory instance;
	private Word word;

	@Before
	public void setUp() throws Exception {
		instance = new Memory();
		word = new Word("0111111100000011");
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
		word = null;
	}

	@Test
	public void testSaveCommand() {
		instance.setCommand(100, word);
		instance.setCommand(498, word);
		assertEquals(word, instance.get(100));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testBadIndexCommand() {
		instance.setCommand(500, word);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testBadIndexValue() {
		instance.setValue(499, word);
	}

	@Test
	public void testGet() {
		word = new Word(null);
		assertTrue("0000000000000000".equals(instance.get(100).getWord()));
		assertTrue("0000000000000000".equals(instance.get(498).getWord()));
		assertTrue("0000000000000000".equals(instance.get(500).getWord()));
		assertTrue("0000000000000000".equals(instance.get(998).getWord()));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetBadIndexValue(){
		instance.get(101).getWord();
	}
}
