package ch.dave.mppc.utilities;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{

	private static final long serialVersionUID = 1960293712352548553L;

	public TitlePanel(String name, int top, int left, int bottom, int right){
		add(new JLabel(name));
		setFont(this.getFont().deriveFont(Font.BOLD));
		setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.GRAY));
	}

}
