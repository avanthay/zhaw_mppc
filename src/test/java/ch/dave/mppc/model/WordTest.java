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
		instance.setWord("0101111000010011111");
		assertTrue("1111000010011111".equals(instance.getWord()));
		instance.setWord("101000010100100");
		assertTrue("0101000010100100".equals(instance.getWord()));
		instance.setWord("00011010");
		assertTrue("0000000000011010".equals(instance.getWord()));
		instance.setAmount("0");
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setAmount("1");
		assertTrue("0000000000000001".equals(instance.getWord()));
		instance.setMSb('1');
		assertTrue("1000000000000001".equals(instance.getWord()));
		instance.setMSb('0');
		assertTrue("0000000000000001".equals(instance.getWord()));
	}
	
	@Test
	public void testNegativeRegex(){
		instance.setWord("00111020");
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setWord("00101A0R34");
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setMSb('A');
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setMSb('8');
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setAmount("12345678");
		assertTrue("0000000000000000".equals(instance.getWord()));
		instance.setAmount("abcede0");
		assertTrue("0000000000000000".equals(instance.getWord()));
	}
	
	@Test
	public void testLength(){
		instance.setWord("1011111111111111111000000000000000000000011111111110100000000010000000");
		assertTrue("0000000010000000".equals(instance.getWord()));
		instance.setAmount("1011111111111111111000000000000000000000011111111110100000000010000000");
		assertTrue("0000000010000000".equals(instance.getWord()));
		instance.setWord("100101");
		assertTrue("0000000000100101".equals(instance.getWord()));
		assertTrue("000000000100101".equals(instance.getAmount()));
	}
	
	@Test
	public void testMSb(){
		instance.setWord("1000000000000011");
		instance.setAmount("1001");
		assertTrue('1' == instance.getMSb());
		instance.setMSb('1');
		assertTrue('1' == instance.getMSb());
		instance.setMSb('0');
		assertTrue('0' == instance.getMSb());
	}
	
	@Test
	public void testAmount(){
		instance.setWord("1001");
		instance.setMSb('1');
		assertTrue("1000000000001001".equals(instance.getWord()));
		instance.setMSb('0');
		assertTrue("000000000001001".equals(instance.getAmount()));
	}
	
	@Test
	public void testGetChar(){
		instance.setWord("1111000011110000");
		assertTrue('1' == instance.getCharAt(0));
		assertTrue('1' == instance.getCharAt(3));
		assertTrue('0' == instance.getCharAt(4));
		assertTrue('1' == instance.getCharAt(11));
		assertTrue('0' == instance.getCharAt(12));
		assertTrue('0' == instance.getCharAt(15));
	}

}
