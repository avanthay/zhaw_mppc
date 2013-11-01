package ch.dave.mppc.model;

/**
 * Das Wort ist 2Byte lang (16 bits), das erste bit wird f√ºr das Vorzeichen
 * verwendet Ein Wort kann nur die Zeichen 0 oder 1 annehmen
 * 
 * @author dave
 * @version 2.0
 */
public class Word {

	private int MSb;
	private String amount;
	private int value;

	public Word() {
		setWordString("0");
	}

	public Word(String string) {
		setWordString(string);
	}

	public Word(int MSb, String amount) {
		setMSb(MSb);
		setAmount(amount);
	}
	
	public Word(int value){
		setValue(value);
	}

	
	
	// Interne Methoden

	private String completeString(String string) {
		while (string.length() < 15) {
			string = '0' + string;
		}
		return string;
	}
	
	private void calculateValue() {
		int word = Integer.valueOf(amount, 2);
		if (MSb == 1) {
			word = word - 32768;
		}
		this.value = word;
	}


	
	
	// Getter und Setter

	/**
	 * 
	 * @param index
	 *            Index des gewünschten index im gesamten Wort (16bit)
	 * @return das char aus dem 16bit Index
	 */
	public int getIntAt(int index) {
		if (index == 0) {
			return Integer.valueOf(String.valueOf(this.MSb));
		} else {
			return Integer.valueOf(String.valueOf(amount.charAt(index - 1)));
		}
	}
	
	public String getStringAt(int index) {
		return String.valueOf(getIntAt(index));
	}
	
	public String getSequence(int start){
		return getSequence(start, 16);
	}
	
	public String getSequence(int start, int end){
		return getWordString().substring(start, end);
	}

	
	public String getSplittedString(){
		return getSequence(0, 8) + " " + getSequence(8);
	}

	/**
	 * 
	 * @return Das MSb (Vorzeichenbit). Dieser ist mit 1 negativ und mit 0
	 *         positiv
	 */
	public int getMSb() {
		return MSb;
	}

	/**
	 * 
	 * @param MSb
	 *            Das MSb (Vorzeichenbit). Dieser kann mit 1 negativ und mit 0
	 *            positiv gesetzt werden
	 */
	public void setMSb(int MSb) {
		if (String.valueOf(MSb).matches("(0*1*)*")) {
			this.MSb = MSb;
		}
		if (amount != null){
		calculateValue();
		}
	}

	/**
	 * 
	 * @param amount
	 *            Den 15bit langen Wert des Wortes, ohne MSb (Vorzeichenbit)
	 */
	public void setAmount(String amount) {
		amount = amount.replaceAll(" ", "");
		if (amount.matches("(0*1*)*") == false) {
			setAmount("0");
		} else if (amount.length() > 15) {
			setAmount(amount.substring(-(15 - amount.length())));
		} else {
			this.amount = completeString(amount);
		}
		calculateValue();
	}

	/**
	 * 
	 * @return Den 15bit langen Wert des Wortes, ohne MSb (Vorzeichenbit)
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 
	 * @param string
	 *            Das gesamte Wort (16bit, inklusive MSb)
	 */
	public void setWordString(String string) throws IllegalArgumentException{
		if (string != null){
		string = string.replaceAll(" ", "");
		}
		if (string.equals("")){
			throw new IllegalArgumentException("Word " + string + " is not accepted as word");
		} else if (string.matches("(0*1*)*") == false) {
			throw new IllegalArgumentException("Word " + string + "is not accepted as word");
		} else if (string.length() > 16) {
			setWordString(string.substring(-(16 - string.length())));
		} else if (string.length() == 16) {
			setMSb(Integer.valueOf(string.substring(0, 1)));
			setAmount(string.substring(1));
		} else if (string.length() < 16) {
			setMSb(0);
			setAmount(string);
		}
	}
	
	public void setValue(int value) throws IllegalArgumentException{
		if (value == (short) value){
			setWordString(Integer.toBinaryString(value));
		} else {
			throw new IllegalArgumentException("Word " + value + " is to big - please choose an index between -32768 and 32767");
		}
	}

	/**
	 * 
	 * @return das gesamte Wort als String, inklusive MSb (16bit)
	 */
	public String getWordString() {
		String word = String.valueOf(MSb);
		return word.concat(amount);
	}
	
	public int getValue(){
		return value;
	}
	
	/**
	 * 
	 * @param word das zu addierende Wort
	 * @return true bei einem Überlauf
	 */
	public boolean add(Word word){
		int sum = value + word.value;
		if (sum == (short) sum){
			setValue(sum);
			return false;
		}
		setValue((short) sum);
		return true;
	}
	
	/**
	 * 
	 * @param left true for left shift - false for right shift
	 * @return true bei Überlauf
	 */
	public boolean shiftArithmetic(boolean left){
		if (left){
			int over = getIntAt(1);
			setValue((short) (getValue()*2));
			if (over == 1){
				return true;
			}
			return false;
		} else {
			if(getValue()%2 == 1){
				setValue((getValue() - 1) / 2);
				return true;
			} else {
				setValue(getValue() / 2);
				return false;
			}
		}
	}
	
	/**
	 * 
	 * @param left true for left shift - false for right shift
	 * @return true bei Überlauf
	 */
	public boolean shiftLogic(boolean left){
		if (left){
			int over = getIntAt(0);
			setMSb(getIntAt(1));
			setAmount(amount.concat("0"));
			if (over == 1){
				return true;
			}
			return false;
		} else {
			int over = getIntAt(15);
			setAmount(getStringAt(0).concat(amount.substring(0, 14)));
			setMSb(0);
			if (over == 1){
				return true;
			}
			return false;
		}
	}
	
	
	
	// toString & haschcode

	@Override
	public String toString() {
		return getWordString();
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Word == false)
			return false;
		else if (value == ((Word) o).value)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 47 + MSb;
		for (int i = 0; i < amount.length(); i++)
			result = result * 47 + amount.charAt(i);
		return result;
	}

}
