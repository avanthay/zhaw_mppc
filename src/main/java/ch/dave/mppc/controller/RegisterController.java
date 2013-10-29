package ch.dave.mppc.controller;

import java.util.HashMap;

import ch.dave.mppc.model.Command;
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
	
	public void updateRegisterPanel(String name, Word word){
		registerPanels.get(name).updateFields(word);
	}
	
	
	public RegisterView getRegisterView() {
		return view;
	}

	public HashMap<String, RegisterPanel> getRegisterPanels() {
		return registerPanels;
	}


	// internal method
	private void createRegisterPanels(){
		String[] registerName = {"Befehlszähler", "Befehlsregister", "Akku", "Register 1", "Register 2", "Register 3", "Carry"};
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
