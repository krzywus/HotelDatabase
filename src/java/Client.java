package java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Client implements ActionListener{

	private static final String username = "AppUser";
	private static final String password = "application";
	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=HotelDatabase";
	private static Connection connection;
	private static Statement statement;
	private String loggedUserName;
	
	private MainMenu mainMenu;
	private LoginFrame loginFrame;
	private CreateAccountFrame createAccountFrame;
	private AdminFrame adminFrame;
	
/*-------------------------------------------------------------------------------------------------------------------*/

	
	public Client(){
		mainMenu = new MainMenu(this);
		loginFrame = new LoginFrame(this);
		createAccountFrame = new CreateAccountFrame(this);
	} // end Client constructor

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Login"){
			loginAttempt();
		}else if(e.getActionCommand() == "Sign In"){
			loginFrame.setVisible(false);
			createAccountFrame = new CreateAccountFrame(this);
			createAccountFrame.setVisible(true);
		}else if(e.getActionCommand() == "Check rooms availability"){
			mainMenu.changeView(e.getActionCommand());
		}else if(e.getActionCommand() == "Check Availability"){
			checkAvailability();
		}else if(e.getActionCommand() == "Make Reservation"){
			mainMenu.changeView(e.getActionCommand());
		}else if(e.getActionCommand() == "Submit Reservation"){
			submitReservation();
		}else if(e.getActionCommand() == "Check Usernames"){
			checkUsernames();
		}else if(e.getActionCommand() == "Remove Username"){
			removeUsername();
		}else if(e.getActionCommand() == "Back"){
			mainMenu.changeView(e.getActionCommand());
		}else if(e.getActionCommand() == "Register"){
			registerAccount();
		}else if(e.getActionCommand() == "Logout"){
			mainMenu.setVisible(false);
			loginFrame = new LoginFrame(this);
			loginFrame.setVisible(true);
		}else if(e.getActionCommand() == "Exit"){
			System.exit(0);
		}	
	}// end actionPerformed




	private void loginAttempt() {
		String username = loginFrame.usernameField.getText();
		loggedUserName = username;
		char[] password = loginFrame.passwordField.getPassword();
		int accessLevel = 0;
		try {
			CallableStatement procedure = connection.prepareCall("{ CALL LoginAttempt(?,?,?,?) }");
			procedure.setString(1, username); // IN parameter
			procedure.setString(2, String.valueOf(password) ); // IN parameter

			procedure.registerOutParameter(3, java.sql.Types.NVARCHAR); // OUT parameter
			procedure.registerOutParameter(4, java.sql.Types.INTEGER); // OUT parameter
			procedure.execute();
			String responseMessage =  procedure.getString(3);
			accessLevel = procedure.getInt(4);
			//System.out.println(responseMessage);
			//System.out.println(accessLevel);
			if(responseMessage.equals("Invalid username") || responseMessage.equals("Invalid password")){
					loginFrame.infoLabel.setText(responseMessage);
					return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(accessLevel == 1){				
			loginFrame.setVisible(false);
			mainMenu = new MainMenu(this);
			mainMenu.setVisible(true);
		}else if(accessLevel == 3){
			loginFrame.setVisible(false);
			adminFrame = new AdminFrame(this);
			adminFrame.setVisible(true);
		}
	}//end loginAttempt

	/** Method invoked when user tries to register new account for application. 
	 * On success new account i registered and login frame brought back. 
	 * On failure message is displayed in account frame. */
	private void registerAccount() {
		String username = createAccountFrame.usernameField.getText();
		String id_series = createAccountFrame.seriesField.getText();
		char[] password = createAccountFrame.passwordField.getPassword();
		if(username.equals("") || id_series.equals("") || String.valueOf(password).equals("") ){
			createAccountFrame.infoLabel.setText("Some required field is empty.");
			return;
		}
		try {
			CallableStatement procedure = connection.prepareCall("{ CALL CreateAccount(?,?,?,?,?) }");
			procedure.setString(1, username); // IN parameter
			procedure.setString(2, String.valueOf(password) ); // IN parameter
			procedure.setInt(3, 1); // IN parameter
			procedure.setString(4, String.valueOf(id_series) ); // IN parameter

			procedure.registerOutParameter(5, java.sql.Types.NVARCHAR); // OUT parameter
			procedure.execute();
			String responseMessage =  procedure.getString(5);
			System.out.println(responseMessage);
			if(responseMessage != null)
			if(responseMessage.equals("Username in use.")){
				createAccountFrame.infoLabel.setText(responseMessage);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createAccountFrame.setVisible(false);
		loginFrame.setVisible(true);
		loginFrame.infoLabel.setText("Registration successful.");
		return;
	}// end registerAccount

	/** Method invoked when admin removes and username from database. */
	private void removeUsername() {
		try {
			Statement statement = connection.createStatement();
			String Sql = "DELETE FROM AppUser WHERE Login='" + adminFrame.usernameToDeletion.getText() + "'";
			statement.executeUpdate(Sql);
			statement.close();
			adminFrame.infoLabel.setText("User " + adminFrame.usernameToDeletion.getText()+ " was removed.");
		} catch (SQLException e) {		
			JOptionPane.showMessageDialog(mainMenu, "Something went wrong. No such user.");
			e.printStackTrace();	}
	}//end removeUsername

	/** Method to invoke when admin wants to check usernames in the database. */
	private void checkUsernames() {
		try {
			Statement statement = connection.createStatement();
			String Sql = "SELECT Login FROM AppUser";
			ResultSet rs = statement.executeQuery(Sql);
			String selectResultString = "Username:\n\n";
			while (rs.next()) {
				selectResultString += rs.getString("Login") + "\n";
			}
			JTextArea msg = new JTextArea(selectResultString);
			JScrollPane scrollPane = new JScrollPane(msg);
			JOptionPane.showMessageDialog(mainMenu, scrollPane);
		} catch (SQLException e) {		
			JOptionPane.showMessageDialog(mainMenu, "Something went wrong. Sorry!");
			e.printStackTrace();	}
	}// end checkUsernames
	
	/** Metoda wykonuje akcje podczas wysylania prosby o rezewacje. */
	private void submitReservation() {
		String submit[] = mainMenu.getReservationSubmit();
		//System.out.println(submit[0]); System.out.println(submit[1]); System.out.println(submit[2]); System.out.println(submit[3]);
		//System.out.println(checkDates(submit[2],submit[3]));
		if(!checkDates(submit[2],submit[3])){
			JOptionPane.showMessageDialog(mainMenu, "Reservation not possible. Wrong date.");
			return;
		}
		try {
			//get clientID
			Statement statement = connection.createStatement();
			String Sql = "SELECT TOP 1 c.ID FROM Client c INNER JOIN AppUser ON Account = c.ID "
					+ "WHERE Login = '" + loggedUserName + "'";
			ResultSet rs = statement.executeQuery(Sql);
			String clientID = "";
			while (rs.next()) {
				clientID = rs.getString("ID");
			}
			// znalezienie id pokoju
			switch(submit[0]){
				case "Urnova Wroclaw": {
					char temp = submit[1].charAt(0);
					submit[1] = "10" + temp;
					break;			}
				case "Urnova Warszawa": {
					char temp = submit[1].charAt(0);
					submit[1] = "20" + temp;
					break;			}
				case "Urnova Katowice": {
					char temp = submit[1].charAt(0);
					submit[1] = "30" + temp;
					break;			}
			}
			String roomID = "";
			Sql = "SELECT TOP 1 r.ID FROM Room r WHERE RoomNumber = '" + submit[1] + "'";
			rs = statement.executeQuery(Sql);
			while (rs.next()) {
				roomID = rs.getString("ID");
			}
			Sql = "INSERT INTO Reservation(BegDate,EndDate,Paid, ClientID, RoomID) VALUES("
					+ "'"+submit[2]+"','" + submit[3] + "'," + "0" + "," + clientID + "," + roomID + ")";
			statement.executeUpdate(Sql);
			rs.close();
			statement.close();
			JOptionPane.showMessageDialog(mainMenu, "Reservation made for selected date.\nHave a nice stay!");
		} catch (SQLException e) {		
			JOptionPane.showMessageDialog(mainMenu, "Reservation not possible. Room reserved in that time.");
			//e.printStackTrace();	
		}
	}// submitReservation


	/** Metoda wykonuje akcje podczas sprawdzania czy pokoj jest dostepny.*/
	private void checkAvailability() {
		String submit[] = mainMenu.getCheckSubmit();
		if(!checkDates(submit[2],submit[3])){
			JOptionPane.showMessageDialog(mainMenu, "Wrong date."); return; }
		if(!checkPersonNumber(submit[1])){
			JOptionPane.showMessageDialog(mainMenu, "Please insert a number into PersonNumber field."); return; }
		try {
			Statement statement = connection.createStatement();
			String Sql = "SELECT r.RoomNumber, r.Price, t.MaxNumberOfPeople FROM Room r "
					+ "INNER JOIN RoomType t ON t.ID = Type "
					+ "WHERE Hotel = '" + submit[0] + "' AND MaxNumberOfPeople >= " + submit[1];
			ResultSet rs = statement.executeQuery(Sql);
			String selectResultString = "Rooms Available:\n\n";
			selectResultString += "RoomNumber\tPrice\tMaxPeople\n";
			while (rs.next()) {
				selectResultString += rs.getString("RoomNumber") + "\t";
				selectResultString += rs.getString("Price")+ "\t";
				selectResultString += rs.getString("MaxNumberOfPeople")+ "\n";
			}
			JTextArea msg = new JTextArea(selectResultString);
			JScrollPane scrollPane = new JScrollPane(msg);
			JOptionPane.showMessageDialog(mainMenu, scrollPane);
		} catch (SQLException e) {		
			JOptionPane.showMessageDialog(mainMenu, "Something went wrong. Sorry!");
			e.printStackTrace();	}
	}// check availability

	/** Metoda sprawdza czy data poczatkowa jest wczesniejsza niz koncowa. */
	private boolean checkDates(String begDate, String endDate){
		int begYear = Integer.parseInt(begDate.substring(0,4));
		int endYear = Integer.parseInt(endDate.substring(0,4));
		if( begYear > endYear) return false;
		if( begYear == endYear){
			int begMonth = Integer.parseInt(begDate.substring(5,7));
			int endMonth = Integer.parseInt(endDate.substring(5,7));
			if( begMonth > endMonth ) return false;
			if( begMonth == endMonth ){
				int begDay = Integer.parseInt(begDate.substring(8,10));
				int endDay = Integer.parseInt(endDate.substring(8,10));
				if( begDay >= endDay ) return false;
			}else return true;
		}else return true;
		return true;
	}// end checkDates
	
	/** Metoda sprawdza czy tekst wpisany do liczby osob jest liczba. */
	private boolean checkPersonNumber(String personNumber){
		try{
			Integer.parseInt(personNumber);
		}catch(NumberFormatException e){ return false;}
		return true;
	}// end checkDates

	/** Method*/
	private void connectDatabase(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}//end connectDatabase
	
	/** Metoda glowna klienta. Pokazuje GUI.*/
	public static void main(String[] args) {
		Client client = new Client();
		client.connectDatabase();
		client.loginFrame.setVisible(true);
	} // end main
	
}
