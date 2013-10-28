package ch.dave.mppc.model;

import java.util.regex.Pattern;

public class Command extends Word{

	private String name;
	private int number;
	private int registerNr;
	private int address;
	private String mnemonics;

	/**
	 * 
	 * @param name Befehlsname in Mnemonics
	 * @param decimalNumber Zahl welche dazu addiert werden soll als Dezimalzahl (nur für ADDD befehl Sinnvoll)
	 * @param registerNr die Registernummer als String "R0" - "R3"
	 * @param address Adresse im Speicher als Dezimalzahl
	 * @throws IllegalArgumentException Wenn der Befehlsname nicht existiert
	 */
	public Command(String name, int number, int registerNr, int address) throws IllegalArgumentException{
		this.name = name;
		this.number = number;
		this.registerNr = registerNr;
		this.address = address;
		createWord();
	}

	public Command(Word word){
		super(word.getWordString());
		createCommand();
	}
	
	public Command(String mnemonicsString){
		this.mnemonics = mnemonicsString;
		convertMnemorics();
	}
	
	

	// getter and setter
	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public int getRegisterNr() {
		return registerNr;
	}

	public int getAddress() {
		return address;
	}
	
	public String getMnemonics(){
		return mnemonics;
	}

	// internal methods
	private void createWord() throws IllegalArgumentException {
		if (name.equals("CLR")) {
			setWordString("0000" + getBinaryRegister() + "1010000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("ADD")) {
			setWordString("0000" + getBinaryRegister() + "1110000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("ADDD")) {
			setWordString("1" + new Word(number).getSequence(1));
			createMnemonics(name, number);
		} else if (name.equals("INC")) {
			setWordString("0000000100000000");
			createMnemonics(name);
		} else if (name.equals("DEC")) {
			setWordString("0000010000000000");
			createMnemonics(name);
		} else if (name.equals("LWDD")) {
			setWordString("0100" + getBinaryRegister() + getBinaryAddress());
			createMnemonics(name, registerNr, address);
		} else if (name.equals("SWDD")) {
			setWordString("0110" + getBinaryRegister() + getBinaryAddress());
			createMnemonics(name, registerNr, address);
		} else if (name.equals("SRA")) {
			setWordString("0000010100000000");
			createMnemonics(name);
		} else if (name.equals("SLA")) {
			setWordString("0000100000000000");
			createMnemonics(name);
		} else if (name.equals("SRL")) {
			setWordString("0000100100000000");
			createMnemonics(name);
		} else if (name.equals("SLL")) {
			setWordString("0000110000000000");
			createMnemonics(name);
		} else if (name.equals("AND")) {
			setWordString("0000" + getBinaryRegister() + "1000000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("OR")) {
			setWordString("0000" + getBinaryRegister() + "1100000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("NOT")) {
			setWordString("0000000010000000");
			createMnemonics(name);
		} else if (name.equals("BZ")) {
			setWordString("0001" + getBinaryRegister() + "1000000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("BNZ")) {
			setWordString("0001" + getBinaryRegister() + "0100000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("BC")) {
			setWordString("0001" + getBinaryRegister() + "1100000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("B")) {
			setWordString("0001" + getBinaryRegister() + "0000000000");
			createMnemonics(name, registerNr);
		} else if (name.equals("BZD")) {
			setWordString("001100" + getBinaryAddress());
			createMnemonics(name, address);
		} else if (name.equals("BNZD")) {
			setWordString("001010" + getBinaryAddress());
			createMnemonics(name, address);
		} else if (name.equals("BCD")) {
			setWordString("001110" + getBinaryAddress());
			createMnemonics(name, address);
		} else if (name.equals("BD")) {
			setWordString("001000" + getBinaryAddress());
			createMnemonics(name, address);
		} else if (name.equals("END")) {
			setWordString("0");
			createMnemonics(name);
		} else {
			this.name = null;
			throw new IllegalArgumentException(name
					+ " is not accepted as a command name");
		}
	}
	
	private String getBinaryRegister(){
		return new Word(registerNr).getSequence(14);
	}
	
	private String getBinaryAddress(){
		return new Word(address).getSequence(6);
	}

	private void createCommand(){
		if (getIntAt(0) == 1){
			this.name = "ADDD";
			this.number = new Word(getAmount()).getValue();
			createMnemonics(name, number);
		} else if (getIntAt(1) == 1){
			if (getIntAt(2) == 1){
				this.name = "SWDD";
			} else {
				this.name = "LWDD";
			}
			setRegisterNr();
			setAddress();
			createMnemonics(name, registerNr, address);
		} else if (getIntAt(2) == 1){
			if (getIntAt(3) == 1){
				if (getIntAt(4) == 1){
					this.name = "BCD";
				} else {
					this.name = "BZD";
				}
			} else {
				if (getIntAt(4) == 1){
					this.name = "BNZD";
				} else {
					this.name = "BD";
				}
			}
			setAddress();
			createMnemonics(name, address);
		} else if (getIntAt(3) == 1){
			if (getIntAt(6) == 1){
				if (getIntAt(7) == 1){
					this.name = "BC";
				} else {
					this.name = "BZ";
				}
			} else {
				if (getIntAt(7) == 1){
					this.name = "BNZ";
				} else {
					this.name = "B";
				}
			}
			setRegisterNr();
			createMnemonics(name, registerNr);
		} else if (getIntAt(6) == 1){
			if (getIntAt(7) == 1){
				if (getIntAt(8) == 1){
					this.name = "ADD";
				} else {
					this.name = "OR";
				}
			} else {
				if (getIntAt(8) == 1){
					this.name = "CLR";
				} else {
					this.name = "AND";
				}
			}
			setRegisterNr();
			createMnemonics(name, registerNr);
		} else if (getIntAt(4) == 1){
			if (getIntAt(5) == 1){
				this.name = "SLL";
			} else if (getIntAt(7) == 1){
				this.name = "SRL";
			} else {
				this.name = "SLA";
			}
			createMnemonics(name);
		} else if (getIntAt(5) == 1){
			if (getIntAt(7) == 1){
				this.name = "SRA";
			} else {
				this.name = "DEC";
			}
			createMnemonics(name);
		} else if (getIntAt(7) == 1){
			this.name = "INC";
			createMnemonics(name);
		} else if (getIntAt(8) == 1){
			this.name = "NOT";
			createMnemonics(name);
		} else {
			this.name = "END";
			createMnemonics(name);
		}
	}

	private void setAddress() {
		this.address = new Word(getSequence(6)).getValue();
	}

	private void setRegisterNr() {
		this.registerNr = new Word(getSequence(4, 6)).getValue();
	}
	
	
	// createMnemonics Methoden
	private void createMnemonics(String name){
		this.mnemonics = name;
	}
	
	private void createMnemonics(String name, int anumber){
		if (anumber == number){			
			this.mnemonics = name + " #" + number;
		} else if (anumber == registerNr){
			this.mnemonics = name + " R" + registerNr;
		} else if (anumber == address){
			this.mnemonics = name + " #" + address;			
		}
	}
	
	private void createMnemonics(String name, int registerName, int address){
		this.mnemonics = name + " R" + registerName + ", #" + address;
	}
	
	private void convertMnemorics(){
		String[] splittedMnemorics = mnemonics.replace("#", "").replace(",", "").split(Pattern.quote( " " ));
		this.name = splittedMnemorics[0];
		String[] namedCommands = {"INC", "DEC", "SRA", "SLA", "SRL", "SLL", "END"};
		String[] namedNumberedCommands = {"ADDD"};
		String[] namedRegistredCommands = {"CLR", "ADD", "AND", "OR", "BZ", "BNZ", "BC", "B"};
		String[] namedAdressedCommands = {"BZD", "BNZD", "BCD", "BD"};
		String[] namedRegistredAdressedCommands = {"LWDD", "SWDD"};
		String[] [] stringArray = { namedCommands, namedNumberedCommands, namedRegistredCommands, namedAdressedCommands, namedRegistredAdressedCommands};
		String[] selectedArray = null;
		for (int i = 0; i < stringArray.length; i++){
			for (int j = 0; j < stringArray[i].length; j++){
				if (stringArray[i] [j].equals(name)){
					selectedArray = stringArray[i];
					break;
				}
			}
		}
		if (selectedArray == namedCommands){
			//already named
		} else if (selectedArray == namedNumberedCommands){
			this.number = Integer.valueOf(splittedMnemorics[1]);
		} else if (selectedArray == namedRegistredCommands){
			this.registerNr = Integer.valueOf(splittedMnemorics[1]);
		} else if (selectedArray == namedAdressedCommands){
			this.address = Integer.valueOf(splittedMnemorics[1]);
		} else if (selectedArray == namedRegistredAdressedCommands){
			this.registerNr = Integer.valueOf(splittedMnemorics[1].replace("R", ""));
			this.address = Integer.valueOf(splittedMnemorics[2]);
		}
			
		createWord();
	}
}














