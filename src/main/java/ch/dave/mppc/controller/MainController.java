
package ch.dave.mppc.controller;

import java.util.HashMap;

import ch.dave.mppc.model.Register;

public class MainController {
	
	private HashMap<String, Register> registers;
	
	public MainController(){
		createRegisters();
	}
	
	
	
	//internal methods
	private void createRegisters(){
		String name = "Akku";
		registers.put(name, new Register(name));
		name = "Reg1";
		registers.put(name, new Register(name));
		name = "Reg2";
		registers.put(name, new Register(name));
		name = "Reg3";
		registers.put(name, new Register(name));
		name = "Command";
		registers.put(name, new Register(name));
		name = "Counter";
		registers.put(name, new Register(name));
		name = "Carry";
	}
	
}