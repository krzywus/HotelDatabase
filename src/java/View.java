package java;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public abstract class View implements ViewInterface{
	
	/** Menu glowne. */
	private MainMenu menu;
	/** Wymiary glownego menu. */
	protected final int windowLength ;
	protected final int windowHeight ;
	/** Klient. */
	protected Client client;
	/** Przyciski do wyboru w menu. */
	private static JButton backButton;
	/** Layout menu. */
	protected SpringLayout layout;
	/** Panel zastepczy ( podstawiany zamiast glownego w mainMenu) */
	protected JPanel panel;
	
	public View(MainMenu menu){
		this.menu = menu;
		this.client = menu.client;
		windowLength = menu.getWidth();
		windowHeight = menu.getHeight();
		panel = new JPanel();
		layout = new SpringLayout();
		panel.setLayout(layout);
		addBackButton();
		//menu.secondaryPanel = panel;
	}
	
	private void addBackButton() {
		backButton = new JButton("Back");
		/* Ustawienie rozmiarow przycikow. */
		Dimension buttonDim     =	new Dimension(100,50);		backButton.setPreferredSize(buttonDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	 backButton,	(int) (windowLength - buttonDim.getWidth()  - 15), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, backButton,	(int) (windowHeight - buttonDim.getHeight() - 40),	SpringLayout.NORTH, panel);
		panel.add(backButton); backButton.addActionListener(client);
	}// end addBackButton

	public JPanel getPanel(){
		return panel;
	}
	
	public void init(){	System.out.println("Just View");}
	public void setSizes(){} 
	public void addElements(){}
}
