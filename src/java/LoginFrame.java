package java;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame{

	/** Wymiary okna. */
	private final static int windowLength = 450;
	private final static int windowHeight = 450;	
	/** Klient. */
	private Client client;
	/** Przyciski do wyboru w menu. */
	private static JButton exit, login, sign;
	/** Pola do wpisania nazwy uzytkownika i hasla. */
	public JTextField usernameField;
	public JPasswordField passwordField;
	/** Labele informacyjne. */
	private JLabel usernameLabel, passwordLabel;
	public JLabel infoLabel;
	
/*-------------------------------------------------------------------------------------------------------------------*/
	
	/** Konstruktor. Ustawia rozmiar i dodaje przyciski. */
	public LoginFrame(Client client){
		super();
		setTitle("Urnova Hotel App Main Menu");
		this.client = client;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		init();
		addElements();
		setSizes();
		repaint();
	} // end MainMenu constructor
	
	/** Metoda inicjujaca pola klasy. */
	private void init(){
		usernameLabel = new JLabel(); usernameLabel.setText("Username:");
		passwordLabel = new JLabel(); passwordLabel.setText("Password:");
		infoLabel = new JLabel();infoLabel.setText("");
		usernameField = new JTextField("");
		passwordField = new JPasswordField("");
		exit = new JButton("Exit");	login = new JButton("Login");
		sign = new JButton("Sign In");
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
		Dimension loginDim    =	new Dimension(75,30);		login.setPreferredSize(loginDim);
		Dimension signDim    =	new Dimension(75,30);		sign.setPreferredSize(signDim);
		Dimension textFieldDim=	new Dimension(100,20);	
		usernameField.setPreferredSize(textFieldDim); passwordField.setPreferredSize(textFieldDim);
		Dimension textLabelDim=	new Dimension(80,20);	
		usernameLabel.setPreferredSize(textLabelDim); passwordLabel.setPreferredSize(textLabelDim);
		Dimension infoLabelDim=	new Dimension(200,20);	
		infoLabel.setPreferredSize(infoLabelDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	usernameLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, usernameLabel,		75,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	passwordLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel,		75*2,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	infoLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, infoLabel,		195,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	usernameField,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, usernameField,		75,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	passwordField,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, passwordField,		75*2,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	login,		(int) (windowLength/2-loginDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, login,		75*3,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	exit,		(int) (windowLength/2-exitDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, exit,		75*4,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	sign,		(int) (windowLength/4-signDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, sign,		75*4,	SpringLayout.NORTH, this);
	} // end setSizes

	/** Metoda tworzaca i dodajaca przyciski do okna. */
	private void addElements(){		
		exit.addActionListener(client);		add(exit);
		login.addActionListener(client);	add(login);
		sign.addActionListener(client);		add(sign); 
		add(usernameLabel);		add(passwordLabel);		
		add(usernameField);	add(passwordField);
		add(infoLabel);
	} // end addButtons
	
}
