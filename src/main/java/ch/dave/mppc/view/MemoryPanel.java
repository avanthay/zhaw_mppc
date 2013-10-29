package ch.dave.mppc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ch.dave.mppc.model.Command;
import ch.dave.mppc.model.Word;

public class MemoryPanel extends JPanel{
	
	private static final long serialVersionUID = -5726004405193005804L;
	
	private JTextField idTextField;
	private JTextField binaryTextField;
	private JTextField decodedTextField;
	
	private Color originalBackGroundColor;

	
	public MemoryPanel(Integer id, Word word){
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		originalBackGroundColor = getBackground();
		
		idTextField = new JTextField(6);
		idTextField.setEditable(false);
		idTextField.setFocusable(false);
		add(idTextField);
		
		binaryTextField = new JTextField(13);
		binaryTextField.setName("binaryField");
		binaryTextField.setHorizontalAlignment(JTextField.CENTER);
		add(binaryTextField);
		
		decodedTextField = new JTextField(10);
		decodedTextField.setName("decodedField");
		add(decodedTextField);
		
		initializeFields(id, word);
		addGuiFeatureListeners();
	}

	public void setColoredPanel(boolean colored){
		if (colored){
			setBackground(Color.ORANGE);
		} else {
			setBackground(originalBackGroundColor);
		}
	}
	
	public void setColoredTextFields(){
		idTextField.setBackground(Color.YELLOW);
		binaryTextField.setBackground(Color.YELLOW);
		decodedTextField.setBackground(Color.YELLOW);
		hideColor();
	}
	
	public void showError(){
		binaryTextField.setForeground(Color.RED);
		decodedTextField.setForeground(Color.RED);
		hideColor();
	}
	
	public void updateFields(Word word){
		binaryTextField.setText(word.getSplittedString());
		if (word instanceof Command){
			decodedTextField.setText(((Command) word).getMnemonics());
		} else {
			decodedTextField.setText(String.valueOf(word.getValue()));
		}
	}
	
	public void setBinaryTextFieldListener(KeyListener keyListener){
		binaryTextField.addKeyListener(keyListener);
	}
	
	public void setDecodedTextFieldListener(KeyListener keyListener){
		decodedTextField.addKeyListener(keyListener);
	}

	public String getIdTextField(){
		return idTextField.getText();
	}
	
	public String getBinaryTextField(){
		return binaryTextField.getText();
	}
	
	public String getDecodedTextField(){
		return decodedTextField.getText();
	}
	
	
	// internal Methods
	private void initializeFields(Integer id, Word word){
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
				binaryTextField.setForeground(Color.BLACK);
				decodedTextField.setForeground(Color.BLACK);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void addGuiFeatureListeners(){
		FocusListener listener = new FocusListener() {
			private String textBefore = "";
			public void focusLost(FocusEvent e) {
				if (((JTextField) e.getComponent()).getText().equals("")){
					((JTextField) e.getComponent()).setText(textBefore);
				}
			}
			public void focusGained(FocusEvent e) {
				textBefore = ((JTextField) e.getComponent()).getText();
				((JTextField) e.getComponent()).setText("");
			}
		};
		binaryTextField.addFocusListener(listener);
		decodedTextField.addFocusListener(listener);
	}
}
