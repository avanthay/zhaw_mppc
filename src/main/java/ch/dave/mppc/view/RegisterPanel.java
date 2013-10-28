package ch.dave.mppc.view;

import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Word;

public class RegisterPanel extends JPanel{
	
	private static final long serialVersionUID = -7123482026921783419L;
	
	private JPanel registersPanel;
	private HashMap<String, RegisterView> registerViews;

	
	public RegisterPanel() {

		registerViews = new HashMap<String, RegisterView>();
		
		registersPanel = new JPanel();
		registersPanel.setLayout(new BoxLayout(registersPanel, BoxLayout.Y_AXIS));
		add(registersPanel);
		
		createRegisterViews();
	}

	public void updateRegisterView(String name, Word word){
		registerViews.get(name).updateFields(word);
	}
	
	
	// internal Methods
	private void createRegisterViews(){
		String[] registerName = {"Befehlszähler", "Befehlsregister", "Akku", "Register 1", "Register 2", "Register 3", "Carry"};
		RegisterView registerView = null;
		for (int i = 0; i < 7; i++){
			if (i != 1){
			registerView = new RegisterView(registerName[i], new Word(0));
			} else {
			registerView = new RegisterView(registerName[i], new Command(new Word(0)));
			}
			registerViews.put(registerName[i], registerView);
			registersPanel.add(registerView);
		}
	}
}
