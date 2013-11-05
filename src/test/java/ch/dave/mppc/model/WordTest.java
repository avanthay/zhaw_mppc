package ch.dave.mppc.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordTest {

	private Word instance;
	
	@Before
	public void setUp(){
		this.instance = new Word();
	}

	@After
	public void tearDown(){
		this.instance = null;
	}

	@Test
	public void testPositiveRegex() {
		instance.setWordString("0101111000010011111");
		assertTrue("1111000010011111".equals(instance.getWordString()));
		instance.setWordString("101000010100100");
		assertTrue("0101000010100100".equals(instance.getWordString()));
		instance.setWordString("00011010");
		assertTrue("0000000000011010".equals(instance.getWordString()));
		instance.setAmount("0");
		assertTrue("0000000000000000".equals(instance.getWordString()));
		instance.setAmount("1");
		assertTrue("0000000000000001".equals(instance.getWordString()));
		instance.setMSb(1);
		assertTrue("1000000000000001".equals(instance.getWordString()));
		instance.setMSb(0);
		assertTrue("0000000000000001".equals(instance.getWordString()));
	}
	
	@Test
	public void testAdd(){
		assertFalse(instance.add(new Word(32767)));
		instance.setValue(32760);
		assertTrue(instance.add(new Word(8)));
	}
	
	@Test
	public void testShift(){
		instance.setValue(32760);
		assertFalse(instance.shiftLogic(true));
		instance.setValue(32760);
		assertTrue(instance.shiftArithmetic(true));
		instance.setValue(32761);
		assertTrue(instance.shiftArithmetic(false));
		instance.setValue(32761);
		assertTrue(instance.shiftLogic(false));
		instance.setValue(-1);
		assertTrue(instance.shiftLogic(true));
		instance.setValue(-1);
		assertTrue(instance.shiftArithmetic(true));
	}
	
	@Test
	public void testSetStringAt(){
		instance = new Word("0000 1111 0000 1111");
		instance.setStringAt(0, "1");
		assertTrue(instance.getWordString().equals("1000111100001111"));
		instance.setStringAt(1, "1");
		assertTrue(instance.getWordString().equals("1100111100001111"));
		instance.setStringAt(2, "1");
		assertTrue(instance.getWordString().equals("1110111100001111"));
		instance.setStringAt(15, "0");
		assertTrue(instance.getWordString().equals("1110111100001110"));
	}
	
	@Test
	public void testLength(){
		instance.setWordString("1011111111111111111000000000000000000000011111111110100000000010000000");
		assertTrue("0000000010000000".equals(instance.getWordString()));
		instance.setAmount("1011111111111111111000000000000000000000011111111110100000000010000000");
		assertTrue("0000000010000000".equals(instance.getWordString()));
		instance.setWordString("100101");
		assertTrue("0000000000100101".equals(instance.getWordString()));
		assertTrue("000000000100101".equals(instance.getAmount()));
	}
	
	@Test
	public void testMSb(){
		instance.setWordString("1000000000000011");
		instance.setAmount("1001");
		assertTrue(1 == instance.getMSb());
		instance.setMSb(1);
		assertTrue(1 == instance.getMSb());
		instance.setMSb(0);
		assertTrue(0 == instance.getMSb());
	}
	
	@Test
	public void testAmount(){
		instance.setWordString("1001");
		instance.setMSb(1);
		assertTrue("1000000000001001".equals(instance.getWordString()));
		instance.setMSb(0);
		assertTrue("000000000001001".equals(instance.getAmount()));
	}
	
	@Test
	public void testGetIndex(){
		instance.setWordString("1111000011110000");
		assertTrue("1".equals(instance.getStringAt(0)));
		assertTrue(1 == instance.getIntAt(3));
		assertTrue(0 == instance.getIntAt(4));
		assertTrue(1 == instance.getIntAt(11));
		assertTrue(0 == instance.getIntAt(12));
		assertTrue("0".equals(instance.getStringAt(15)));
	}
	
	@Test
	public void createWordNull(){
		instance = new Word(0);
		assertTrue("0000000000000000".equals(instance.getWordString()));
	}
	
	@Test
	public void testEquals(){
		instance = new Word(32);
		assertTrue(instance.equals(new Word(32)));
		assertFalse(instance.equals(new Word(33)));
		assertTrue(new Word(32).equals(instance));
	}
	
	@Test
	public void testHashCode(){
		instance = new Word(45);
		assertTrue(instance.hashCode() == new Word(45).hashCode());
		assertFalse(instance.hashCode() == new Word(44).hashCode());
		instance = new Word("0000000011111111");
		assertTrue(instance.hashCode() ==  new Word("0000000011111111").hashCode());
		assertFalse(instance.hashCode() ==  new Word("1000000011111111").hashCode());
	}

}
