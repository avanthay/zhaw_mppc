
package ch.dave.mppc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.dave.mppc.model.Word;
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
		
		//TEST
		buttonController.getButtonBar().setActionListener("reset", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerController.updateRegisterPanel("Register 1", new Word(34));
			}
		});
		//TEST
	}
	
	
	
	
	public void showView(){
		mainView.pack();
		mainView.setVisible(true);
	}
	
	// Internal Methods

	

	
}