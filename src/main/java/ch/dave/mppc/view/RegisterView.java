package ch.dave.mppc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Word;

public class RegisterView extends JPanel {

	private static final long serialVersionUID = 1334002883379903634L;
	
	private JTextField binaryTextField;
	private JTextField decodedTextField;
	
	public RegisterView(String name, Word word){
		
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), name));
		
		binaryTextField = new JTextField(13);
		binaryTextField.setHorizontalAlignment(JTextField.CENTER);
		binaryTextField.setEditable(false);
		add(binaryTextField);
		
		decodedTextField = new JTextField(10);
		decodedTextField.setEditable(false);
		add(decodedTextField);
		
		updateFieldsWithoutColor(word);
	}
	
	public void updateFields(Word word){
		updateFieldsWithoutColor(word);
		binaryTextField.setBackground(Color.YELLOW);
		decodedTextField.setBackground(Color.YELLOW);
		hideColor();
	}
	
	// internal Method
	private void updateFieldsWithoutColor(Word word){
		binaryTextField.setText(word.getSplittedString());
		if(word instanceof Command){
			decodedTextField.setText(((Command) word).getMnemonics());
		} else {
			decodedTextField.setText(String.valueOf(word.getValue()));
		}
	}
	
	private void hideColor(){
		Timer timer = new Timer(600, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				binaryTextField.setBackground(Color.WHITE);
				decodedTextField.setBackground(Color.WHITE);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
}
