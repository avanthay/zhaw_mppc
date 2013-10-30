
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
	private ButtonController controllButtons;
	private ButtonController programmMemoryButtons;
	private ButtonController dataMemoryButtons;
	
	
	public MainController(){
		mainView = new MainView();
		registerController = new RegisterController();
		memoryController = new MemoryController();
		controllButtons = new ButtonController("Start", "Slow", "Step", "Reset");
		
		mainView.setRegisterView(registerController.getRegisterView());
		mainView.setMemoryView(memoryController.getMemoryView());
		mainView.setButtonBar(controllButtons.getButtonBar());
		
		programmMemoryButtons = new ButtonController("<", ">");
		memoryController.getMemoryView().setProgrammButtonBar(programmMemoryButtons.getButtonBar());
		
		dataMemoryButtons = new ButtonController("<", ">");
		memoryController.getMemoryView().setDataButtonBar(dataMemoryButtons.getButtonBar());
		
		setListeners();
	}
	
	
	
	
	public void showView(){
		mainView.pack();
		mainView.setVisible(true);
	}
	
	// Internal Methods
	private void setListeners(){
		controllButtons.setActionListener("Start", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showView();
			}
		});
		controllButtons.setActionListener("Slow", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showView();
			}
		});
		controllButtons.setActionListener("Step", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.setCommand(180, new Command("SWDD R0, #230"));
				memoryController.updateProgrammPanels(180);
				showView();
			}
		});
		controllButtons.setActionListener("Reset", new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registerController.getRegisterPanel(Register.BEFEHLSREGISTER).updateFields(new Command("ADDD #423"));;
				memoryController.getMemoryPanel(180).setColoredTextFields();
				showView();
			}
		});
		programmMemoryButtons.setActionListener("<", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.scrollProgrammPanel(-1);
				showView();
			}
		});
		programmMemoryButtons.setActionListener(">", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.scrollProgrammPanel(1);
				showView();
			}
		});
		dataMemoryButtons.setActionListener("<", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.scrollDataPanel(-1);
				showView();
			}
		});
		dataMemoryButtons.setActionListener(">", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memoryController.scrollDataPanel(1);
				showView();
			}
		});
	}
	

	
}