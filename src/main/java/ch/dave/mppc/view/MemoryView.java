package ch.dave.mppc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentListener;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Word;

public class MemoryView extends JPanel{
	
	private static final long serialVersionUID = -5726004405193005804L;
	
	private JTextField idTextField;
	private JTextField binaryTextField;
	private JTextField decodedTextField;

	
	public MemoryView(Integer id, Word word){
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		idTextField = new JTextField(6);
		idTextField.setEditable(false);
		idTextField.setFocusable(false);
		add(idTextField);
		
		binaryTextField = new JTextField(13);
		binaryTextField.setHorizontalAlignment(JTextField.CENTER);
		add(binaryTextField);
		
		decodedTextField = new JTextField(10);
		add(decodedTextField);
		
		updateFieldsWithoutColor(id, word);
	}

	
	public void updateFields(Integer id, Word word){
		updateFieldsWithoutColor(id, word);
		idTextField.setBackground(Color.YELLOW);
		binaryTextField.setBackground(Color.YELLOW);
		decodedTextField.setBackground(Color.YELLOW);
		hideColor();
	}
	
	public void setDocumentListener(DocumentListener documentListener){
		binaryTextField.getDocument().addDocumentListener(documentListener);
	}

	
	
	// internal Methods
	private void updateFieldsWithoutColor(Integer id, Word word){
		idTextField.setText(String.valueOf(id) + " + " + String.valueOf(id + 1));
		binaryTextField.setText(word.getSplittedString());
		if (word instanceof Command){
			decodedTextField.setText(((Command) word).getMnemonics());
		} else {
			decodedTextField.setText(String.valueOf(word.getValue()));
		}
	}
	
	private void hideColor(){
		Timer timer = new Timer(600, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idTextField.setBackground(Color.WHITE);
				binaryTextField.setBackground(Color.WHITE);
				decodedTextField.setBackground(Color.WHITE);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
}