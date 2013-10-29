package ch.dave.mppc.controller;

import java.awt.event.ActionListener;

import ch.dave.mppc.model.Button;
import ch.dave.mppc.view.ButtonBar;

public class ButtonController {
	
	private ButtonBar view;
	
	public ButtonController(){
		
		view = new ButtonBar();
		
	}
	
	public ButtonBar getButtonBar(){
		return view;
	}
	
	public void setStartActionListener(ActionListener actionListener){
		view.setActionListener(Button.START, actionListener);
	}
	
	public void setSlowActionListener(ActionListener actionListener){
		view.setActionListener(Button.SLOW, actionListener);
	}
	
	public void setStepActionListener(ActionListener actionListener){
		view.setActionListener(Button.STEP, actionListener);
	}
	
	public void setResetActionListener(ActionListener actionListener){
		view.setActionListener(Button.RESET, actionListener);
	}

}
