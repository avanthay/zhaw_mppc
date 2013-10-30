package ch.dave.mppc.controller;

import java.awt.event.ActionListener;

import ch.dave.mppc.view.ButtonBar;

public class ButtonController {
	
	private ButtonBar view;
	
	public ButtonController(String ...buttonNames){
		
		view = new ButtonBar(buttonNames);
		
		
	}
	
	public ButtonBar getButtonBar(){
		return view;
	}
	
	public void setActionListener(String buttonName, ActionListener actionListener){
		view.setActionListener(buttonName, actionListener);
	}

}
