
package ch.dave.mppc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.dave.mppc.model.Word;
import ch.dave.mppc.view.MainView;

public class MainController {
	
	private MainView mainView;
	private MemoryController memoryController;
	
	public MainController(){
		mainView = new MainView();
		memoryController = new MemoryController();
		
		mainView.setMemoryPanel(memoryController.getMemoryPanel());
		
		//TEST
		mainView.getButtonBar().setActionListener("reset", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainView.getRegisterPanel().updateRegisterView("Register 1", new Word(34));
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