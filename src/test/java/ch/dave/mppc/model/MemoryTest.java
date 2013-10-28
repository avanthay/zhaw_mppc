package ch.dave.mppc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemoryTest {

	private Memory instance;
	private Command command;

	@Before
	public void setUp() throws Exception {
		instance = new Memory();
		command = new Command("SWDD R3, #500");
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
		command = null;
	}

	@Test
	public void testSaveCommand() {
		instance.setCommand(100, command);
		instance.setCommand(498, command);
		assertEquals(command, instance.get(100));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testBadIndexCommand() {
		instance.setCommand(500, command);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testBadIndexValue() {
		instance.setValue(499, command);
	}

	@Test
	public void testGet() {
		command = new Command(new Word(null));
		assertTrue("0000000000000000".equals(instance.get(100).getWordString()));
		assertTrue("0000000000000000".equals(instance.get(498).getWordString()));
		assertTrue("0000000000000000".equals(instance.get(500).getWordString()));
		assertTrue("0000000000000000".equals(instance.get(998).getWordString()));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetBadIndexValue(){
		instance.get(101).getWordString();
	}
}
