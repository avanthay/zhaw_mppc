
package ch.dave.mppc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Register;
import ch.dave.mppc.model.Word;
import ch.dave.mppc.view.MainView;
import ch.dave.mppc.view.RegisterPanel;

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
				new Programm().doStep();
				
				showView();
			}
		});
		controllButtons.setActionListener("Reset", new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
	
	
	private class Programm implements Runnable{

		public void run(){
			while (true){
				doStep();
			}
		}
		
		public void doStep(){
			// lade Befehl in Befehlsregister
			int aktuellerBefehl = registerController.getRegisterPanel(Register.BEFEHLSZAEHLER).getWord().getValue();
			registerController.updateRegisterPanel(Register.BEFEHLSREGISTER, new Command(new Word(memoryController.getMemoryPanel(aktuellerBefehl).getBinaryTextField())));
			// befehl interpretieren
			doCommand((Command) registerController.getRegisterPanel(Register.BEFEHLSREGISTER).getWord());
			//...
		}
		
		public void doCommand(Command command){
			String name = command.getName();
			int number = command.getNumber();
			int address = command.getAddress();
			RegisterPanel akkuReg = registerController.getRegisterPanel(Register.AKKU);
			Word akkuWord = registerController.getRegisterPanel(Register.AKKU).getWord();
			String register = null;
			if (command.getRegisterNr() == 0){
				register = Register.AKKU;
			} else if (command.getRegisterNr() == 1){
				register = Register.REGISTER_1;
			} else if (command.getRegisterNr() == 2){
				register = Register.REGISTER_2;
			} else if (command.getRegisterNr() == 3){
				register = Register.REGISTER_3;
			}
				
					
			if (name.equals("CLR")){
				registerController.getRegisterPanel(register).updateFields(new Word(0));
			} else if (name.equals("ADD")){
				if (akkuWord.add(registerController.getRegisterPanel(register).getWord())){
					akkuReg.updateFields(akkuWord);
				} else {
					//†berlauf!!
				}
			} else if (name.equals("ADDD")){
				if (akkuWord.add(new Word(number))){
					akkuReg.updateFields(akkuWord);
				}
			}
			
		}
		
	}
	
}