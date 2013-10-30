package ch.dave.mppc.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ch.dave.mppc.utilities.TitlePanel;

public class MemoryView extends JPanel{
	
	private static final long serialVersionUID = -4976705482061558691L;
	
	private JPanel programmViewPanel;
	private JPanel dataViewPanel;
	private JPanel programmViewHelpPanel;
	private JPanel dataViewHelpPanel;
	
	public MemoryView() {
		
		setLayout(new GridLayout(1, 2));
		
		// ProgrammPanel
		JPanel programmPanel = new JPanel();
		programmPanel.setLayout(new BorderLayout());
		add(programmPanel);
		
		programmPanel.add(new TitlePanel("Programm", 0, 1, 1, 1), BorderLayout.NORTH);
		
		programmViewHelpPanel = new JPanel();
		programmViewHelpPanel.setLayout(new BoxLayout(programmViewHelpPanel, BoxLayout.Y_AXIS));
		programmPanel.add(programmViewHelpPanel, BorderLayout.CENTER);
		
		programmViewHelpPanel.add(new JPanel());
		
		programmViewPanel = new JPanel();
		programmViewPanel.setName("programmView");
		programmViewPanel.setLayout(new BoxLayout(programmViewPanel, BoxLayout.Y_AXIS));
		programmViewHelpPanel.add(programmViewPanel);
		
		
		// DataPanel
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		add(dataPanel);
		
		dataPanel.add(new TitlePanel("Daten", 0, 0, 1, 0), BorderLayout.NORTH);
		
		dataViewHelpPanel = new JPanel();
		dataViewHelpPanel.setLayout(new BoxLayout(dataViewHelpPanel, BoxLayout.Y_AXIS));
		dataPanel.add(dataViewHelpPanel, BorderLayout.CENTER);
		
		dataViewHelpPanel.add(new JPanel());
		
		dataViewPanel = new JPanel();
		dataViewPanel.setName("dataView");
		dataViewPanel.setLayout(new BoxLayout(dataViewPanel, BoxLayout.Y_AXIS));
		dataViewHelpPanel.add(dataViewPanel);
	}
	
	public void setProgrammMemoryPanel(MemoryPanel memoryPanel){
		programmViewPanel.add(memoryPanel);
	}
	
	public void removeProgrammMemoryPanels(){
		programmViewPanel.removeAll();
	}
	
	public void setProgrammButtonBar(ButtonBar buttonBar){
		programmViewHelpPanel.add(buttonBar);
	}
	
	public void setDataMemoryPanel(MemoryPanel memoryPanel){
		dataViewPanel.add(memoryPanel);
	}

	public void removeDataMemoryPanels(){
		dataViewPanel.removeAll();
	}
	
	public void setDataButtonBar(ButtonBar buttonBar){
		dataViewHelpPanel.add(buttonBar);
	}
}
