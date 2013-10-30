package ch.dave.mppc.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonBar extends JPanel {

	private static final long serialVersionUID = -6357962832524283638L;
	
	private HashMap<String, JButton> buttons;
	
	
	public ButtonBar(String ...buttonNames){
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.buttons = new HashMap<String, JButton>();
		
		for (String button : buttonNames){
			JButton newButton = new JButton(button);
			buttons.put(button, newButton);
			add(newButton);
		}
		
	}
	
	public void setActionListener(String buttonName, ActionListener actionListener){
		buttons.get(buttonName).addActionListener(actionListener);
	}
}















