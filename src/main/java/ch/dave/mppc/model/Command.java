package ch.dave.mppc.model;

public class Command {

	private String name;
	private int number;
	private String registerName;
	private Integer address;
	private Word commandAsWord;

	public Command(String name, int number, String registerNr, Integer address) throws IllegalArgumentException{
		this.number = number;
		this.registerName = registerNr;
		this.address = address;
		createWord(name);
	}

	public Command(Word word) {
		this.commandAsWord = word;
		createCommand();
	}

	// getter and setter
	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public String getRegisterName() {
		return registerName;
	}

	public Integer getAddress() {
		return address;
	}

	public Word getCommandAsWord() {
		return commandAsWord;
	}

	// internal methods
	private void createWord(String name) throws IllegalArgumentException {
		if (name.equals("CLR")) {
			this.name = "CLR";
			this.commandAsWord = new Word("0000" + getBinaryRegister() + "1010000000");
		} else if (name.equals("ADD")) {
			this.name = "ADD";
			this.commandAsWord = new Word("0000" + getBinaryRegister() + "1110000000");
		} else if (name.equals("ADDD")) {
			this.name = "ADDD";
			this.commandAsWord = new Word("1" + new Word(number).getSequence(1, 15));
		} else if (name.equals("INC")) {
			this.name = "INC";
			this.commandAsWord = new Word("0000000100000000");
		} else if (name.equals("DEC")) {
			this.name = "DEC";
			this.commandAsWord = new Word("0000010000000000");
		} else if (name.equals("LWDD")) {
			this.name = "LWDD";
			this.commandAsWord = new Word("0100" + getBinaryRegister() + getBinaryAddress());
		} else if (name.equals("SWDD")) {
			this.name = "SWDD";
			this.commandAsWord = new Word("0110" + getBinaryRegister() + getBinaryAddress());
		} else if (name.equals("SRA")) {
			this.name = "SRA";
			this.commandAsWord = new Word("0000010100000000");
		} else if (name.equals("SLA")) {
			this.name = "SLA";
			this.commandAsWord = new Word("0000100000000000");
		} else if (name.equals("SRL")) {
			this.name = "SRL";
			this.commandAsWord = new Word("0000100100000000");
		} else if (name.equals("SLL")) {
			this.name = "SLL";
			this.commandAsWord = new Word("0000110000000000");
		} else if (name.equals("AND")) {
			this.name = "AND";
			this.commandAsWord = new Word("0000" + getBinaryRegister() + "1000000000");
		} else if (name.equals("OR")) {
			this.name = "OR";
			this.commandAsWord = new Word("0000" + getBinaryRegister() + "1100000000");
		} else if (name.equals("NOT")) {
			this.name = "NOT";
			this.commandAsWord = new Word("0000000010000000");
		} else if (name.equals("BZ")) {
			this.name = "BZ";
			this.commandAsWord = new Word("0001" + getBinaryRegister() + "1000000000");
		} else if (name.equals("BNZ")) {
			this.name = "BNZ";
			this.commandAsWord = new Word("0001" + getBinaryRegister() + "0100000000");
		} else if (name.equals("BC")) {
			this.name = "BC";
			this.commandAsWord = new Word("0001" + getBinaryRegister() + "1100000000");
		} else if (name.equals("B")) {
			this.name = "B";
			this.commandAsWord = new Word("0001" + getBinaryRegister() + "0000000000");
		} else if (name.equals("BZD")) {
			this.name = "BZD";
			this.commandAsWord = new Word("001100" + getBinaryAddress());
		} else if (name.equals("BNZD")) {
			this.name = "BNZD";
			this.commandAsWord = new Word("001010" + getBinaryAddress());
		} else if (name.equals("BCD")) {
			this.name = "BCD";
			this.commandAsWord = new Word("001110" + getBinaryAddress());
		} else if (name.equals("BD")) {
			this.name = "BD";
			this.commandAsWord = new Word("001000" + getBinaryAddress());
		} else if (name.equals("END")) {
			this.name = "END";
			this.commandAsWord = new Word(0);
		} else {
			throw new IllegalArgumentException(name
					+ " is not accepted as a command name");
		}
	}
	
	private String getBinaryRegister(){
		return new Word(Integer.decode(Character.toString(registerName.charAt(3)))).getSequence(14, 15);
	}
	
	private String getBinaryAddress(){
		return new Word(address).getSequence(6, 15);
	}

	private void createCommand(){
		if (commandAsWord.getCharAt(0) == 1){
			this.name = "ADDD";
			this.number = new Word(commandAsWord.getAmount()).getValue();
		} else if (commandAsWord.getCharAt(1) == 1){
			if (commandAsWord.getCharAt(2) == 1){
				this.name = "SWDD";
			} else {
				this.name = "LWDD";
			}
			setRegisterName();
			setAddress();
		} else if (commandAsWord.getCharAt(2) == 1){
			if (commandAsWord.getCharAt(3) == 1){
				if (commandAsWord.getCharAt(4) == 1){
					this.name = "BCD";
				} else {
					this.name = "BZD";
				}
			} else {
				if (commandAsWord.getCharAt(4) == 1){
					this.name = "BNZD";
				} else {
					this.name = "BD";
				}
			}
			setAddress();
		} else if (commandAsWord.getCharAt(3) == 1){
			if (commandAsWord.getCharAt(6) == 1){
				if (commandAsWord.getCharAt(7) == 1){
					this.name = "BC";
				} else {
					this.name = "BZ";
				}
			} else {
				if (commandAsWord.getCharAt(7) == 1){
					this.name = "BNZ";
				} else {
					this.name = "B";
				}
			}
			setRegisterName();
		} else if (commandAsWord.getCharAt(6) == 1){
			if (commandAsWord.getCharAt(7) == 1){
				if (commandAsWord.getCharAt(8) == 1){
					this.name = "ADD";
				} else {
					this.name = "OR";
				}
			} else {
				if (commandAsWord.getCharAt(8) == 1){
					this.name = "CLR";
				} else {
					this.name = "AND";
				}
			}
			setRegisterName();
		} else if (commandAsWord.getCharAt(4) == 1){
			if (commandAsWord.getCharAt(5) == 1){
				this.name = "SLL";
			} else if (commandAsWord.getCharAt(7) == 1){
				this.name = "SRL";
			} else {
				this.name = "SLA";
			}
		} else if (commandAsWord.getCharAt(5) == 1){
			if (commandAsWord.getCharAt(7) == 1){
				this.name = "SRA";
			} else {
				this.name = "DEC";
			}
		} else if (commandAsWord.getCharAt(7) == 1){
			this.name = "INC";
		} else if (commandAsWord.getCharAt(8) == 1){
			this.name = "NOT";
		} else {
			this.name = "END";
		}
	}

	private void setAddress() {
		this.address = new Word(commandAsWord.getSequence(6, 0)).getValue();
	}

	private void setRegisterName() {
		this.registerName = "Reg".concat(Integer.toString(new Word(commandAsWord
				.getSequence(4, 5)).getValue()));
	}
}














