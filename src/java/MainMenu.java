package java;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class MainMenu extends JFrame{

	/** Wymiary okna. */
	private final static int windowLength = 450;
	private final static int windowHeight = 450;
	/** Klient. */
	protected Client client;
	/** Przyciski do wyboru w menu. */
	private static JButton exit;
	/** Panel glowny i poboczny. */
	protected JPanel mainPanel;
	protected JPanel secondaryPanel;
	/** Widoki (wyglady menu). */
	private View currentView;
	
/*-------------------------------------------------------------------------------------------------------------------*/

	/** Konstruktor. Ustawia rozmiar i dodaje przyciski. */
	public MainMenu(Client client){
		super();
		setTitle("Urnova Hotel App");
		this.client = client;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		init();
		addElements();
		setSizes();
		currentView = new NormalUserMainView(this);
		this.setContentPane(mainPanel);
		repaint();
	} // end MainMenu constructor

	/** Metoda inicjujaca pola klasy. */
	private void init(){
		exit = new JButton("Exit");
		mainPanel = new JPanel();
	}// end init
	
	/** Metoda ustala rozmiar okna i rozmieszczenie przyciskow. */
	private void setSizes(){
		setResizable(false);
		setSize(windowLength,windowHeight);
		mainPanel.setSize(windowLength,windowHeight);
		setLocation(400,125);
		SpringLayout layout= new SpringLayout();
		mainPanel.setLayout(layout);
		/* Ustawienie rozmiarow przycikow. */
		Dimension exitDim     =	new Dimension(100,50);		exit.setPreferredSize(exitDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	exit,		(int) (windowLength/2-exitDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, exit,		75*3,	SpringLayout.NORTH, this);
	} // end setSizes

	/** Metoda tworzaca i dodajaca przyciski do okna. */
	private void addElements(){		
		exit = new JButton("Exit");					exit.addActionListener(client);
		mainPanel.add(exit);
	} // end addButtons
	
	public View getView(){
		return currentView;
	}
	
	/** Metoda zmienia widok okna.*/
	public void changeView(String command){
		this.getContentPane().removeAll();
		if(command.startsWith("Check rooms availability")){
			currentView = new CheckRoomView(this);
			secondaryPanel = currentView.getPanel();
			this.setContentPane(secondaryPanel);
		}else if(command.startsWith("Make Reservation")){
			currentView = new MakeReservationView(this);
			secondaryPanel = currentView.getPanel();
			this.setContentPane(secondaryPanel);
		}else if(command.startsWith("Back")){
			if(currentView.getClass() == 
					(new NormalUserMainView(this)).getClass()	 ){
				client.actionPerformed(new ActionEvent(this, 0, "Logout"));
			}else{
				currentView = new NormalUserMainView(this);
				mainPanel = currentView.getPanel();
				this.setContentPane(mainPanel);
			}
		}
		this.revalidate();
		repaint();
	}// end changeView

	public String[] getReservationSubmit() {
		return currentView.getMainActionString();
	}

	public String[] getCheckSubmit() {
		return currentView.getMainActionString();
	}
	
}
