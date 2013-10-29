package ch.dave.mppc.controller;

import ch.dave.mppc.model.Memory;
import ch.dave.mppc.view.MemoryView;

public class MemoryController {
	
	private MemoryView view;
	private Memory model;
	
	public MemoryController(){
		view = new MemoryView();
		model = new Memory();
	}
	
	public MemoryView getMemoryView(){
		return view;
	}
	
	public Memory getMemory(){
		return model;
	}

}
