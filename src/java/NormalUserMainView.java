package java;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SpringLayout;

public class NormalUserMainView extends View implements ViewInterface{

	private JButton checkRoomButton, makeReservationButton;

	
	public NormalUserMainView(MainMenu menu) {
		super(menu);
		init();
		setSizes();
		addElements();
		menu.mainPanel = panel;
	}// end NormalUserMainView
	
	@Override
	public void init(){
		checkRoomButton = new JButton("Check rooms availability");
		makeReservationButton = new JButton("Make Reservation");
	}

	@Override
	public void setSizes() {
		/* Ustawienie rozmiarow przycikow. */
		Dimension checkRoomDim     			 =	new Dimension(200,50);	
		checkRoomButton.setPreferredSize(checkRoomDim);
		Dimension makeReservationButtonDim   =	new Dimension(150,50);	
		makeReservationButton.setPreferredSize(makeReservationButtonDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	 checkRoomButton,	(int) (windowLength/2 - checkRoomDim .getWidth()/2), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, checkRoomButton,	75,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 makeReservationButton,	(int) (windowLength/2 - makeReservationButtonDim.getWidth()/2), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, makeReservationButton,	 75*2,	SpringLayout.NORTH, panel);
		
	}

	@Override
	public void addElements() {
		panel.add(checkRoomButton); 	   checkRoomButton.addActionListener(client);
		panel.add(makeReservationButton);  makeReservationButton.addActionListener(client);
	}

	@Override
	public String[] getMainActionString() {
		String mainAction[] = null;
		return mainAction;
	}

}
