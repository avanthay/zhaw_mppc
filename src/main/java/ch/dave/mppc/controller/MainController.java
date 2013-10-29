
package ch.dave.mppc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Register;
import ch.dave.mppc.view.MainView;

public class MainController {
	
	private MainView mainView;
	
	private RegisterController registerController;
	private MemoryController memoryController;
	private ButtonController buttonController;
	
	
	public MainController(){
		mainView = new MainView();
		registerController = new RegisterController();
		memoryController = new MemoryController();
		buttonController = new ButtonController();
		
		mainView.setRegisterView(registerController.getRegisterView());
		mainView.setMemoryView(memoryController.getMemoryView());
		mainView.setButtonBar(buttonController.getButtonBar());
		
		setListeners();
	}
	
	
	
	
	public void showView(){
		mainView.pack();
		mainView.setVisible(true);
	}
	
	// Internal Methods
	private void setListeners(){
		buttonController.setStartActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showView();
			}
		});
		buttonController.setSlowActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showView();
			}
		});
		buttonController.setStepActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.setCommand(180, new Command("SWDD R0, #230"));
				memoryController.updateProgrammPanels(180);
				showView();
			}
		});
		buttonController.setResetActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registerController.getRegisterPanel(Register.BEFEHLSREGISTER).updateFields(new Command("ADDD #423"));;
				memoryController.getMemoryPanel(180).setColoredTextFields();
				showView();
			}
		});
	}
	

	
}