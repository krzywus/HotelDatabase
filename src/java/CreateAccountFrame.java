package java;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class CreateAccountFrame extends JFrame{

	/** Wymiary okna. */
	private final static int windowLength = 450;
	private final static int windowHeight = 450;	
	/** Klient. */
	private Client client;
	/** Przyciski do wyboru w menu. */
	private static JButton exit, register;
	/** Pola do wpisania nazwy uzytkownika i hasla. */
	public JTextField usernameField, seriesField;
	public JPasswordField passwordField;
	/** Labele informacyjne. */
	private JLabel usernameLabel, passwordLabel, seriesLabel;
	public JLabel infoLabel;
	
/*-------------------------------------------------------------------------------------------------------------------*/
	
	/** Konstruktor. Ustawia rozmiar i dodaje przyciski. */
	public CreateAccountFrame(Client client){
		super();
		setTitle("Urnova Hotel App Sign in");
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
		seriesLabel	  = new JLabel(); seriesLabel.setText("IDCardSeries:");
		infoLabel = new JLabel();infoLabel.setText("");
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		seriesField	  = new JTextField();
		exit = new JButton("Exit");	register = new JButton("Register");
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
		Dimension registerDim    =	new Dimension(125,30);		register.setPreferredSize(registerDim);
		Dimension textFieldDim=	new Dimension(100,20);	
		usernameField.setPreferredSize(textFieldDim); passwordField.setPreferredSize(textFieldDim);
		seriesField.setPreferredSize(textFieldDim);
		Dimension textLabelDim=	new Dimension(80,20);	
		usernameLabel.setPreferredSize(textLabelDim); passwordLabel.setPreferredSize(textLabelDim);
		seriesLabel.setPreferredSize(textLabelDim);
		Dimension infoLabelDim=	new Dimension(200,20);
		infoLabel.setPreferredSize(infoLabelDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	usernameLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, usernameLabel,		50,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	passwordLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel,		50*2,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	seriesLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, seriesLabel,		50*3,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	infoLabel,		(int) (windowLength/2-textLabelDim.getWidth()), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, infoLabel,		50*4,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	usernameField,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, usernameField,		50,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	passwordField,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, passwordField,		50*2,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, seriesField,		(int) (windowLength/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, seriesField,		50*3,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	register,		(int) (windowLength/2-registerDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, register,		75*3,	SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,	exit,		(int) (windowLength/2-exitDim.getWidth()/2), 		SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, exit,		75*4,	SpringLayout.NORTH, this);
	
	} // end setSizes

	/** Metoda tworzaca i dodajaca przyciski do okna. */
	private void addElements(){		
		exit.addActionListener(client);		add(exit);
		register.addActionListener(client);		add(register);
		add(usernameLabel);		add(passwordLabel);		
		add(usernameField);	add(passwordField);	
		add(seriesField);	add(seriesLabel);
		add(infoLabel);
	} // end addButtons
	
}
