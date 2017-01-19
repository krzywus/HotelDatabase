package java;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class AdminFrame extends JFrame{
	/** Wymiary okna. */
	private final static int windowLength = 450;
	private final static int windowHeight = 450;
	/** Klient. */
	protected Client client;
	/** Przyciski do wyboru w menu. */
	private static JButton exit;
	private JButton checkUsernames;
	private JButton submitDeletion;

	/** Pole do wpisania nazwy uzytkownika do usuniecia. */
	public JTextField usernameToDeletion;
	
	public JLabel infoLabel;
	private Component usernameLabel;
	
/*-------------------------------------------------------------------------------------------------------------------*/

	/** Konstruktor. Ustawia rozmiar i dodaje przyciski. */
	public AdminFrame(Client client){
		super();
		setTitle("Urnova Hotel App");
		this.client = client;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		init();
		addElements();
		setSizes();
		
		repaint();
	} // end MainMenu constructor
	

	/** Metoda inicjujaca pola klasy. */
	private void init(){
		exit = new JButton("Back");	
		checkUsernames = new JButton("Check Usernames");
		submitDeletion = new JButton("Remove Username");
		usernameToDeletion = new JTextField();
		usernameLabel = new JLabel("Username to delete:");
		infoLabel = new JLabel();
	}// end init
	
	/** Metoda ustala rozmiar okna i rozmieszczenie przyciskow. */
	private void setSizes(){
		setResizable(false);
		setSize(windowLength,windowHeight);
		setLocation(400,125);
		SpringLayout layout= new SpringLayout();
		setLayout(layout);
		/* Ustawienie rozmiarow przycikow. */
		Dimension exitDim     =	new Dimension(100,50);		exit.setPreferredSize(exitDim);
		Dimension buttonDim     =	new Dimension(200,50);
		checkUsernames.setPreferredSize(buttonDim);	submitDeletion.setPreferredSize(buttonDim);
		Dimension textFieldDim=	new Dimension(100,20);	
		usernameToDeletion.setPreferredSize(textFieldDim);
		Dimension infoLabelDim=	new Dimension(200,20);	
		infoLabel.setPreferredSize(infoLabelDim);
		usernameLabel.setPreferredSize(infoLabelDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,		exit,		(int) (windowLength/2-exitDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	exit,		75*4,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,		checkUsernames,		(int) (windowLength/2-buttonDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	checkUsernames,		75,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,		submitDeletion,		(int) (windowLength/2-buttonDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	submitDeletion,		75*3,	SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST,		usernameToDeletion,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	usernameToDeletion,		75*2,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,		infoLabel,		(int) (windowLength/2-exitDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	infoLabel,		75*5,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,		usernameLabel,		(int) (windowLength/2-buttonDim.getWidth()+50), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, 	usernameLabel,		75*2,	SpringLayout.NORTH, this);
	} // end setSizes

	/** Metoda tworzaca i dodajaca przyciski do okna. */
	private void addElements(){		
		exit.addActionListener(client);		add(exit);
		add(checkUsernames); add(submitDeletion); 
		checkUsernames.addActionListener(client);	 
		submitDeletion.addActionListener(client);	
		add(usernameToDeletion); 
		add(usernameLabel); add(infoLabel); 
	} // end addButtons
	
}
