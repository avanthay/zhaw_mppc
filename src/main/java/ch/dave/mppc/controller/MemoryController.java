package ch.dave.mppc.controller;

import java.util.HashMap;
import java.util.Map.Entry;

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
		updateProgrammPanel(100);
	}
	
	
	
	public MemoryView getMemoryView(){
		return view;
	}
	
	public Memory getMemory(){
		return model;
	}
	
	public void updateProgrammPanel(int index) throws IndexOutOfBoundsException{
		if (index%2 != 0 || index < 100 || index > 498){
			throw new IndexOutOfBoundsException("available index is 100 to 998, only even number");
		}
		
		memoryPanels.get(coloredMemoryPanel).setColored(false);
		coloredMemoryPanel = index;
		memoryPanels.get(index).setColored(true);
		
		view.removeProgrammMemoryPanels();
		if(index < 110){
			index = 110;
		} else if (index > 478){
			index = 478;
		}
		for (int i = (index - 10); i < (index + 22); i = i+2){
			view.setProgrammMemoryPanel(memoryPanels.get(i));
		}
	}

	
	private void createMemoryPanels(){
		for (Entry<Integer, Word> entry : model.entrySet()){
				memoryPanels.put(entry.getKey(), new MemoryPanel(entry.getKey(), entry.getValue()));
			}
		}
}
