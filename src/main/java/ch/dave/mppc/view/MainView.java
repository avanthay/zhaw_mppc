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
	private JPanel westCenterPanel;
	private JPanel southPanel;
	
	public MainView(){
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 600));
		
		// South
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(southPanel, BorderLayout.SOUTH);
		
		// WestPanel
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		add(westPanel, BorderLayout.WEST);
		
		westCenterPanel = new JPanel();
		westCenterPanel.setLayout(new BorderLayout());
		westPanel.add(westCenterPanel, BorderLayout.CENTER);
		
		westCenterPanel.add(new TitlePanel("Register", 1, 0, 1, 0), BorderLayout.NORTH);
		
		
		// CenterPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);		
		
		centerPanel.add(new TitlePanel("Speicher", 1, 1, 1, 0), BorderLayout.NORTH);
		
		
		
		
		

	}
	
	public void setMemoryView(MemoryView memoryView){
		centerPanel.add(memoryView, BorderLayout.CENTER);		
	}
	
	public void setRegisterView(RegisterView registerView){
		westCenterPanel.add(registerView, BorderLayout.CENTER);
	}

	public void setButtonBar(ButtonBar buttonBar) {
		southPanel.add(buttonBar);
	}
}
















