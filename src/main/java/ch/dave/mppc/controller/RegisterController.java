package ch.dave.mppc.controller;

import java.util.HashMap;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Register;
import ch.dave.mppc.model.Word;
import ch.dave.mppc.view.RegisterView;
import ch.dave.mppc.view.RegisterPanel;

public class RegisterController {
	
	private RegisterView view;
	private HashMap<String, RegisterPanel> registerPanels;

	
	public RegisterController(){
		view = new RegisterView();
		registerPanels = new HashMap<String, RegisterPanel>();
		
		createRegisterPanels();
	}
	
	/**
	 * @param name Konstante aus der Klasse Register (z.b. Register.BEFEHLSZAEHLER)
	 * @param word das Wort welches im Register platziert werden soll
	 */
	public void updateRegisterPanel(String name, Word word){
		registerPanels.get(name).updateFields(word);
	}
	
	
	public RegisterView getRegisterView() {
		return view;
	}

	public RegisterPanel getRegisterPanel(String name) {
		return registerPanels.get(name);
	}


	// internal method
	private void createRegisterPanels(){
		String[] registerName = {Register.BEFEHLSZAEHLER, Register.BEFEHLSREGISTER,
				Register.AKKU, Register.REGISTER_1, Register.REGISTER_2, Register.REGISTER_3,
				Register.CARRY};
		RegisterPanel registerView = null;
		for (int i = 0; i < 7; i++){
			if (i != 1){
			registerView = new RegisterPanel(registerName[i], new Word(0));
			} else {
			registerView = new RegisterPanel(registerName[i], new Command(new Word(0)));
			}
			registerPanels.put(registerName[i], registerView);
			view.addRegisterPanel(registerView);
		}
	}

}
