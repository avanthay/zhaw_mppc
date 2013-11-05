package ch.dave.mppc.controller;

import java.util.HashMap;

import ch.dave.mppc.model.Register;

public class RegisterController {
	
	private HashMap<String, Register> registers;
	
	public RegisterController(){
		createRegisters();
	}
	
	
	
	
	// interne Methoden
	
	private void createRegisters(){
		registers = new HashMap<String, Register>();
		String arr[] = {"Counter", "Command", "Akku", "Reg1", "Reg2", "Reg3", "Carry"};
		for (int i = 0; i < arr.length; i++){
		registers.put(arr[i], new Register(arr[i]));
		}
	}
	
	// getter und setter
	
	public Register getRegister(String string){
		return registers.get(string);
	}

}
