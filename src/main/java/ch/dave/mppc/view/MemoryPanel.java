package ch.dave.mppc.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ch.dave.mppc.utilities.TitlePanel;

public class MemoryPanel extends JPanel{
	
	private static final long serialVersionUID = -4976705482061558691L;
	
	private JPanel programmViewPanel;
	private JPanel dataViewPanel;
	
	public MemoryPanel() {
		
		setLayout(new GridLayout(1, 2));
		
		// ProgrammPanel
		JPanel programmPanel = new JPanel();
		programmPanel.setLayout(new BorderLayout());
		add(programmPanel);
		
		programmPanel.add(new TitlePanel("Programm", 0, 1, 1, 1), BorderLayout.NORTH);
		
		programmViewPanel = new JPanel();
		programmViewPanel.setLayout(new BoxLayout(programmViewPanel, BoxLayout.Y_AXIS));
		programmPanel.add(programmViewPanel, BorderLayout.CENTER);
		
		
		// DataPanel
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		add(dataPanel);
		
		dataPanel.add(new TitlePanel("Daten", 0, 0, 1, 0), BorderLayout.NORTH);
		
		JPanel dataViewHelpPanel = new JPanel();
		dataPanel.add(dataViewHelpPanel, BorderLayout.CENTER);
		
		dataViewPanel = new JPanel();
		dataViewPanel.setLayout(new BoxLayout(dataViewPanel, BoxLayout.Y_AXIS));
		dataViewHelpPanel.add(dataViewPanel);
	}
	
	public void setDataMemoryViews(MemoryView memoryView){
		dataViewPanel.add(memoryView);
	}

}
