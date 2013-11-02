package ch.dave.mppc.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JTextField;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Memory;
import ch.dave.mppc.model.Word;
import ch.dave.mppc.view.MemoryPanel;
import ch.dave.mppc.view.MemoryView;

public class MemoryController {
	
	private MemoryView view;
	private Memory model;
	private HashMap<Integer, MemoryPanel> memoryPanels;
	
	
	private int coloredMemoryPanel;
	private int focusedMemoryPanel;
	
	private int focusedDataPanel;
	
	public MemoryController(){
		view = new MemoryView();
		model = new Memory();
		
		memoryPanels = new HashMap<Integer, MemoryPanel>();
		coloredMemoryPanel = 100;
		createMemoryPanels();
		updateProgrammPanels(100);
		moveDataPanels(500);
		
	}
	
	
	
	public MemoryView getMemoryView(){
		return view;
	}
	
	public MemoryPanel getMemoryPanel(int index){
		return memoryPanels.get(index);
	}
	
	public void setData(int index, Word word, boolean colored){
		verifyIndexData(index);
		model.setValue(index, word);
		memoryPanels.get(index).updateFields(word, colored);
	}
	
	public void setCommand(int index, Command command, boolean colored){
		verifyIndexProgramm(index);
		model.setCommand(index, command);
		memoryPanels.get(index).updateFields(command, colored);
	}
	
	public Command getCommand(int index){
		updateProgrammPanels(index);
		return (Command) model.get(index);
	}
	
	public Word getData(int index, boolean colored){
		verifyIndexData(index);
		memoryPanels.get(index).setColoredTextFields(colored);
		return model.get(index);
	}
	
	/**
	 * 
	 * @param actualProgramm das ProgrammPanel welches an 6. Stelle und farbig dargestellt werden soll
	 */
	public void updateProgrammPanels(int actualProgramm){
		verifyIndexProgramm(actualProgramm);
		setColoredPanel(actualProgramm);
		moveProgrammPanels(actualProgramm);
	}
	
	/**
	 * 
	 * @param direction +1 for down-scroll, -1 for up-scroll
	 */
	public void scrollProgrammPanel(int direction){
		moveProgrammPanels(focusedMemoryPanel + (direction * 30));
	}
	
	/**
	 * 
	 * @param direction +1 for down-scroll, -1 for up-scroll
	 */
	public void scrollDataPanel(int direction){
		moveDataPanels(focusedDataPanel + (direction * 30));
	}
	
	// internal methods
	private void moveProgrammPanels(int actualProgramm){
		view.removeProgrammMemoryPanels();
		if(actualProgramm < 110){
			actualProgramm = 110;
		} else if (actualProgramm > 478){
			actualProgramm = 478;
		}
		focusedMemoryPanel = actualProgramm;
		for (int i = (actualProgramm - 10); i < (actualProgramm + 21); i = i+2){
			view.setProgrammMemoryPanel(memoryPanels.get(i));
		}
	}
	
	private void moveDataPanels(int actualData){
		view.removeDataMemoryPanels();
		if (actualData < 500){
			actualData = 500;
		} else if (actualData > 968){
			actualData = 968;
		}
		focusedDataPanel = actualData;
		for (int i = actualData; i < (actualData + 31); i += 2 ){
			view.setDataMemoryPanel(memoryPanels.get(i));
		}
	}
	
	private void setColoredPanel(int panel){
		memoryPanels.get(coloredMemoryPanel).setColoredPanel(false);
		coloredMemoryPanel = panel;
		memoryPanels.get(panel).setColoredPanel(true);
	}
	
	private void createMemoryPanels(){
		for (Entry<Integer, Word> entry : model.entrySet()){
				memoryPanels.put(entry.getKey(), new MemoryPanel(entry.getKey(), entry.getValue()));
				setListener(entry.getKey());
			}
		}
	
	private void verifyIndexProgramm(int index) throws IndexOutOfBoundsException {
		if (index%2 != 0 || index < 100 || index > 498){
			throw new IndexOutOfBoundsException("available index is 100 to 498, only even number");
		}
	}
	
	private void verifyIndexData(int index) throws IndexOutOfBoundsException {
		if (index%2 != 0 || index < 500 || index > 998){
			throw new IndexOutOfBoundsException("available index is 500 to 998, only even number");
		}
	}
	
	private void setListener(int index){
		KeyListener listener = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
			}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					int id = ((MemoryPanel) e.getComponent().getParent()).getId();
					Word newWord = null;
					String written = ((JTextField) e.getComponent()).getText();
					((JTextField) e.getComponent()).setBackground(Color.WHITE);
					try {
					if(e.getComponent().getParent().getParent().getName().equals("programmView")){
						if (e.getComponent().getName().equals("binaryField")){
							newWord = new Command(new Word(written));
						} else {
							newWord = new Command(written);
						}
					} else {
						if (e.getComponent().getName().equals("binaryField")){
							newWord = new Word(written);
						} else {
							newWord = new Word(Integer.valueOf(written));
						}
					}
					model.put(id, newWord);
					memoryPanels.get(id).updateFields(newWord, false);
					} catch (IllegalArgumentException exp) {
						memoryPanels.get(id).updateFields(model.get(id), false);
						memoryPanels.get(id).showError();
					}
				}
			}
		};
		memoryPanels.get(index).setBinaryTextFieldListener(listener);
		memoryPanels.get(index).setDecodedTextFieldListener(listener);
	}
}











