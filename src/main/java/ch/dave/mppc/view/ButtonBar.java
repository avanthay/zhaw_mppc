package ch.dave.mppc.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonBar extends JPanel {

	private static final long serialVersionUID = -6357962832524283638L;
	
	private HashMap<String, JButton> buttons;
	
	public ButtonBar(){
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		buttons = new HashMap<String, JButton>();
		
		JButton quickButton = new JButton("start");
		buttons.put(quickButton.getText(), quickButton);
		add(quickButton);
		
		JButton slowButton = new JButton("slow");
		buttons.put(slowButton.getText(), slowButton);
		add(slowButton);
		
		JButton stepButton = new JButton("step");
		buttons.put(stepButton.getText(), stepButton);
		add(stepButton);
		
		JButton resetButton = new JButton("reset");
        resetButton.setForeground(Color.RED);
        buttons.put(resetButton.getText(), resetButton);
		add(resetButton);
		
		
	}
	
	public void setActionListener(String buttonName, ActionListener actionListener){
		buttons.get(buttonName).addActionListener(actionListener);
	}
}















