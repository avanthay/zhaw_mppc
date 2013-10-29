package ch.dave.mppc.controller;

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
	
	public MemoryController(){
		view = new MemoryView();
		model = new Memory();
		
		memoryPanels = new HashMap<Integer, MemoryPanel>();
		coloredMemoryPanel = 100;
		createMemoryPanels();
		updateProgrammPanels(100);
		setDataPanels();
	}
	
	
	
	public MemoryView getMemoryView(){
		return view;
	}
	
	public MemoryPanel getMemoryPanel(int index){
		return memoryPanels.get(index);
	}
	
	public void setCommand(int index, Command command){
		verifyIndexProgramm(index);
		model.setCommand(index, command);
		memoryPanels.get(index).updateFields(command);
	}
	
	public void updateProgrammPanels(int actualProgramm){
		verifyIndexProgramm(actualProgramm);
		
		memoryPanels.get(coloredMemoryPanel).setColoredPanel(false);
		coloredMemoryPanel = actualProgramm;
		memoryPanels.get(actualProgramm).setColoredPanel(true);
		
		view.removeProgrammMemoryPanels();
		if(actualProgramm < 110){
			actualProgramm = 110;
		} else if (actualProgramm > 478){
			actualProgramm = 478;
		}
		for (int i = (actualProgramm - 10); i < (actualProgramm + 22); i = i+2){
			view.setProgrammMemoryPanel(memoryPanels.get(i));
		}
	}
	
	// internal methods
	private void setDataPanels(){
		for (int i = 500; i < 531; i += 2 ){
			view.setDataMemoryPanel(memoryPanels.get(i));
		}
	}
	
	private void createMemoryPanels(){
		for (Entry<Integer, Word> entry : model.entrySet()){
				memoryPanels.put(entry.getKey(), new MemoryPanel(entry.getKey(), entry.getValue()));
				setListener(entry.getKey());
			}
		}
	
	private void verifyIndexProgramm(int index) throws IndexOutOfBoundsException {
		if (index%2 != 0 || index < 100 || index > 498){
			throw new IndexOutOfBoundsException("available index is 100 to 998, only even number");
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
					int id = Integer.valueOf(((MemoryPanel) e.getComponent().getParent()).getIdTextField().substring(0, 3));
					Word newWord = null;
					String written = ((JTextField) e.getComponent()).getText();
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
					memoryPanels.get(id).updateFields(newWord);
					} catch (IllegalArgumentException exp) {
						memoryPanels.get(id).updateFields(model.get(id));
						memoryPanels.get(id).showError();
					}
				}
			}
		};
		memoryPanels.get(index).setBinaryTextFieldListener(listener);
		memoryPanels.get(index).setDecodedTextFieldListener(listener);
	}
}











