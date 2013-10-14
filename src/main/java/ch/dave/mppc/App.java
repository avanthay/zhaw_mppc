package ch.dave.mppc;

import ch.dave.mppc.model.Word;



/**
 * Die App-Klasse welche die gesamte Applikation startet
 * 
 * @author dave
 * @version draft
 */
public class App {

	public static void main(String[] args) {
		// start
		Word test = new Word("00111020");
		System.out.println(test.getWord());
	}

}