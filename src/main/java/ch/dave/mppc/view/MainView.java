package ch.dave.mppc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.dave.mppc.utilities.TitlePanel;

public class MainView extends JFrame{

	private static final long serialVersionUID = 1875186554513342928L;
	
	private JPanel centerPanel;
	private RegisterPanel registerPanel;
	private ButtonBar buttonBar;
	
	public MainView(){
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 600));
		
		// North
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(northPanel, BorderLayout.NORTH);
		
		this.buttonBar = new ButtonBar();
		northPanel.add(buttonBar);
		
		// WestPanel
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		add(westPanel, BorderLayout.WEST);
		
		JPanel westCenterPanel = new JPanel();
		westCenterPanel.setLayout(new BorderLayout());
		westPanel.add(westCenterPanel, BorderLayout.CENTER);
		
		westCenterPanel.add(new TitlePanel("Register", 1, 0, 1, 0), BorderLayout.NORTH);
		
		this.registerPanel = new RegisterPanel();
		westCenterPanel.add(registerPanel, BorderLayout.CENTER);
		
		// CenterPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);		
		
		centerPanel.add(new TitlePanel("Speicher", 1, 1, 1, 0), BorderLayout.NORTH);
		
		
		
		
		

	}
	
	public void setMemoryPanel(MemoryPanel memoryPanel){
		centerPanel.add(memoryPanel, BorderLayout.CENTER);		
	}

	public RegisterPanel getRegisterPanel() {
		return registerPanel;
	}

	public ButtonBar getButtonBar() {
		return buttonBar;
	}
}
















