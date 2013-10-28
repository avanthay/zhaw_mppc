package ch.dave.mppc.model;

import java.util.HashMap;

/**
 * Memory ist eine Subklasse von HashMap<Integer, Word> und wird für den
 * Speicher verwendet Von Index 100 - 499 werden Befehle (Command) gespeichert
 * Von Index 500 - 999 werden Eingabewerte (Value) gespeichert
 * 
 * @author dave
 * @version 1.0
 */
public class Memory extends HashMap<Integer, Word> {

	private static final long serialVersionUID = 3020435842443564057L;

	public Memory() {
		super();
		initialize();
	}

	private void initialize() {
		for (int i = 100; i < 499; i += 2) {
			setCommand(i, new Command(new Word(null)));
		}
		for (int i = 500; i < 999; i += 2) {
			setValue(i, new Word(null));
		}
	}

	/**
	 * Speichert einen Befehl über 2 Byte. Beispiel setCommand(100, commandWord)
	 * setzt das 16bit Wort commandWort in den Speicherzellen 100 & 101.
	 * Indexrange geht von 100 bis 498.
	 * 
	 * @param index
	 *            Index in welchem das Wort gespeichert werden soll
	 * @param command
	 *            Das Wort welches abgespeichert werden soll
	 * @throws IndexOutOfBoundsException
	 *             wenn Index < 100 oder Index > 498
	 */
	public void setCommand(int index, Command command)
			throws IndexOutOfBoundsException {
		if (499 > index && index >= 100 && index % 2 == 0) {
			put(Integer.valueOf(index), command);
		} else {
			throw new IndexOutOfBoundsException(
					"available index is 100 to 498, only even number");
		}
	}

	/**
	 * Speichert eine Eingabewert über 2 Byte. Beispiel setValue(500, valueWord)
	 * setzt das 16bit Wort valueWord in den Speicherzellen 500 & 501.
	 * Indexrange ist von 500 bis 998.
	 * 
	 * @param index
	 *            Index in welchem das Wort gespeichert werden soll
	 * @param word
	 *            Wort welches gespeichert werden
	 * @throws IndexOutOfBoundsException
	 *             wenn Index < 500 oder Index > 998
	 */
	public void setValue(int index, Word word) throws IndexOutOfBoundsException {
		if (999 > index && index >= 500 && index % 2 == 0) {
			put(Integer.valueOf(index), word);
		} else {
			throw new IndexOutOfBoundsException(
					"available index is 500 to 998, only even number");
		}
	}

	/**
	 * 
	 * @param index
	 *            Index des gewünschten Wortes
	 * @return Gewünschte Wort am angegebenen Index
	 */
	@Override
	public Word get(Object key) throws IndexOutOfBoundsException {
		int ky = new Integer((Integer) key).intValue();
		if ((int) ky >= 100 && (int) ky < 999 && ky%2 == 0) {
			return super.get(new Integer(ky));
		} else {
			throw new IndexOutOfBoundsException(
					"available Index from 100 to 998, only even number");
		}
	}

}
