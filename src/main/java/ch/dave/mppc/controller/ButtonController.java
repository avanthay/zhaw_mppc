package ch.dave.mppc.controller;

import ch.dave.mppc.view.ButtonBar;

public class ButtonController {
	
	private ButtonBar view;
	
	public ButtonController(){
		
		view = new ButtonBar();
		
	}
	
	public ButtonBar getButtonBar(){
		return view;
	}

}
