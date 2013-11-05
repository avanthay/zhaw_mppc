package ch.dave.mppc.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class RegisterView extends JPanel{
	
	private static final long serialVersionUID = -7123482026921783419L;
	
	private JPanel registersPanel;

	
	public RegisterView() {

		registersPanel = new JPanel();
		registersPanel.setLayout(new BoxLayout(registersPanel, BoxLayout.Y_AXIS));
		add(registersPanel);
		
	}
	
	public void addRegisterPanel(RegisterPanel registerPanel){
		registersPanel.add(registerPanel);
	}

}
