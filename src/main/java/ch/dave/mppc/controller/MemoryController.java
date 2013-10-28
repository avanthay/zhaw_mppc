package ch.dave.mppc.controller;

import ch.dave.mppc.model.Memory;
import ch.dave.mppc.view.MemoryPanel;

public class MemoryController {
	
	private MemoryPanel view;
	private Memory model;
	
	public MemoryController(){
		view = new MemoryPanel();
		model = new Memory();
	}
	
	public MemoryPanel getMemoryPanel(){
		return view;
	}
	
	public Memory getMemory(){
		return model;
	}

}
