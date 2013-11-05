package ch.dave.mppc;

import ch.dave.mppc.controller.MainController;





/**
 * Die App-Klasse welche die gesamte Applikation startet
 * 
 * @author dave
 * @version draft
 */
public class App {

	public static void main(String[] args) {
		MainController mainController = new MainController();
		mainController.showView();
	}

}