
package ch.dave.mppc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Register;
import ch.dave.mppc.model.Word;
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
				new Programm(true).doStep();
				
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
		
		private boolean colored;
		
		public Programm(boolean colored){
			this.colored = colored;
		}

		public void run(){
			while (true){
				doStep();
			}
		}
		
		public void doStep(){
			// lade Befehl in Befehlsregister
			int aktuellerBefehl = registerController.getWord(Register.BEFEHLSZAEHLER, colored).getValue();
			registerController.updateRegisterPanel(Register.BEFEHLSREGISTER, memoryController.getCommand(aktuellerBefehl), colored);
			// befehl interpretieren
			doCommand((Command) registerController.getRegisterPanel(Register.BEFEHLSREGISTER).getWord());
			//...
		}
		
		public void doCommand(Command command){
			String name = command.getName();
			int number = command.getNumber();
			int address = command.getAddress();
			Word akkuWord = registerController.getWord(Register.AKKU, false);
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
				registerController.updateRegisterPanel(register, new Word(0), colored);
			} else if (name.equals("ADD")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				registerController.getRegisterPanel(register).colorateFields(colored);
				if (akkuWord.add(registerController.getWord(register, colored))){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, akkuWord, colored);
			} else if (name.equals("ADDD")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.add(new Word(number))){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);					
				}
				registerController.updateRegisterPanel(Register.AKKU, akkuWord, colored);
			} else if (name.equals("INC")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.add(new Word(1))){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, akkuWord, colored);
			} else if (name.equals("DEC")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.add(new Word(-1))){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, akkuWord, colored);
			} else if (name.equals("LWDD")) {
				registerController.updateRegisterPanel(register, memoryController.getData(address, colored), colored);
			} else if (name.equals("SWDD")){
				memoryController.setData(address, registerController.getWord(register, colored), colored);
			} else if (name.equals("SRA")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.getStringAt(15).equals("1")){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, new Word(akkuWord.getStringAt(0).concat(akkuWord.getSequence(0, 14))), colored);
			} else if (name.equals("SLA")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.getStringAt(1).equals("1")){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, new Word(akkuWord.getStringAt(0).concat(akkuWord.getSequence(2)).concat("0")), colored);
			} else if (name.equals("SRL")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.getStringAt(15).equals("1")){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, new Word(akkuWord.getSequence(0, 14)), colored);
			} else if (name.equals("SLL")){
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), false);
				if (akkuWord.getStringAt(0).equals("1")){
					registerController.updateRegisterPanel(Register.CARRY, new Word(1), colored);
				}
				registerController.updateRegisterPanel(Register.AKKU, new Word(akkuWord.getSequence(1, 15).concat("0")), colored);
			} else if (name.equals("AND")){
				Word andWord = new Word(0);
				for (int i = 0; i < 16; i++){
					String akkuBit = akkuWord.getStringAt(i);
					String registerBit = registerController.getWord(register, colored).getStringAt(i);
					if (akkuBit.equals("1") && registerBit.equals("1")){
						andWord.setStringAt(i, "1");
					}
				}
				registerController.updateRegisterPanel(Register.AKKU, andWord, colored);
			} else if (name.equals("OR")) {
				Word orWord = new Word(0);
				for (int i = 0; i < 16; i++){
					String akkuBit = akkuWord.getStringAt(i);
					String registerBit = registerController.getWord(register, colored).getStringAt(i);
					if (akkuBit.equals("1") || registerBit.equals("1")){
						orWord.setStringAt(i, "1");
					} 
				}
				registerController.updateRegisterPanel(Register.AKKU, orWord, colored);
			} else if (name.equals("NOT")) {
				Word notWord = new Word(0);
				for (int i = 0; i < 16; i++){
					if (akkuWord.getStringAt(i).equals("0")){
						notWord.setStringAt(i, "1");
					}
				}
				registerController.updateRegisterPanel(Register.AKKU, notWord, colored);
	//weitere Command entschlŸsseln hier!
				
				
				
				
				
				
			} else {
				throw new IllegalArgumentException("Command " + name + " doesn't exist");
			
			}
			registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(Register.BEFEHLSZAEHLER, colored).
					addWithoutCarry(new Word(2)), colored);

			
		}
		
	}
	
}





















