
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
	
	private boolean programmEnd = false;
	
	
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
				if (!programmEnd){
					Thread go = new Thread(new Programm(false, 0));
					go.start();
					showView();
				}
			}
		});
		controllButtons.setActionListener("Slow", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (!programmEnd){
						Thread go = new Thread(new Programm(false, 500));
						go.start();
				}
			}
		});
		controllButtons.setActionListener("Step", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!programmEnd){
					doStep(false);
					showView();
				}
			}
		});
		controllButtons.setActionListener("Reset", new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				memoryController.getCommand(100);
				boolean show = false;
				programmEnd = false;
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, new Word(100), show);
				registerController.updateRegisterPanel(Register.BEFEHLSREGISTER, new Command("END"), show);
				registerController.updateRegisterPanel(Register.AKKU, new Word(0), show);
				registerController.updateRegisterPanel(Register.REGISTER_1, new Word(0), show);
				registerController.updateRegisterPanel(Register.REGISTER_2, new Word(0), show);
				registerController.updateRegisterPanel(Register.REGISTER_3, new Word(0), show);
				registerController.updateRegisterPanel(Register.CARRY, new Word(0), show);
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
		private int sleepTime;
		
		public Programm(boolean colored, int sleepTime){
			this.colored = colored;
			this.sleepTime = sleepTime;
		}

		public void run(){
			while (!programmEnd){
				doStep(colored);
				if (sleepTime > 0){
					showView();
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	private void doStep(boolean colored){
		// lade Befehl in Befehlsregister
		int aktuellerBefehl = registerController.getWord(Register.BEFEHLSZAEHLER, colored).getValue();
		registerController.updateRegisterPanel(Register.BEFEHLSREGISTER, memoryController.getCommand(aktuellerBefehl), colored);
		// befehl interpretieren
		doCommand((Command) registerController.getRegisterPanel(Register.BEFEHLSREGISTER).getWord(), colored);
	}
	
	private void doCommand(Command command, boolean colored){
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
		} else if (name.equals("BZ")) {
			if (akkuWord.getValue() == 0){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(register, colored), colored);
				return;
			}
		} else if (name.equals("BNZ")) {
			if (akkuWord.getValue() != 0){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(register, colored), colored);
				return;
			}
		} else if (name.equals("BC")) {
			if (registerController.getWord(Register.CARRY, colored).getValue() == 1){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(register, colored), colored);
				return;
			}
		} else if (name.equals("B")) {
			registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(register, colored), colored);
			return;
		} else if (name.equals("BZD")) {
			if (akkuWord.getValue() == 0){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, new Word(address), colored);
				return;					
			}
		} else if (name.equals("BNZD")) {
			if (akkuWord.getValue() != 0){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, new Word(address), colored);
				return;					
			}
		} else if (name.equals("BCD")) {
			if (registerController.getWord(Register.CARRY, colored).getValue() == 1){
				registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, new Word(address), colored);
				return;					
			}
		} else if (name.equals("BD")) {
			registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, new Word(address), colored);
			return;
		} else if (name.equals("END")) {
			programmEnd = true;
			return;
		} else {
			throw new IllegalArgumentException("Command " + name + " doesn't exist");
		
		}
		registerController.updateRegisterPanel(Register.BEFEHLSZAEHLER, registerController.getWord(Register.BEFEHLSZAEHLER, colored).
				addWithoutCarry(new Word(2)), colored);
	}
	
}





















