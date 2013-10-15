package ch.dave.mppc.model;

/**
 * Ein Register ist eine Subklasse von Word
 * und ist 16bit lang. Beim instanzieren muss ein Name übergeben werden
 * Das Register wird mit 0-en belegt
 * 
 * @author dave
 * @version 1.0
 */
public class Register extends Word{
	
	private String name;
	
	/**
	 * Der Register wird mit 0-en belegt bei der Initialisierung
	 * 
	 * @param name Der Name des Registers
	 */
	public Register(String name){
		super();
		this.name = name;
	}
	
	/**
	 * setzt das Register auf 0 zurück
	 */
	public void resetRegister(){
		setWord("0");
	}
	
	
	
	// Getter und Setter
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	// toString & hashCode

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public int hashCode(){
		int code = 0;
		for(int i = 0; i < name.length(); i++){
			code += name.charAt(i) * 17 * i;
		}
		return code;
	}
	
}