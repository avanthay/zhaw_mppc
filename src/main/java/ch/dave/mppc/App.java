package ch.dave.mppc;

import ch.dave.mppc.model.Register;




/**
 * Die App-Klasse welche die gesamte Applikation startet
 * 
 * @author dave
 * @version draft
 */
public class App {

	public static void main(String[] args) {
		// start
		Register hoi = new Register("Akku");
		System.out.println(hoi.getWord());
	}

}