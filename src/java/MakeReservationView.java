package java;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

@SuppressWarnings("rawtypes")
public class MakeReservationView extends View implements ViewInterface {

	private JLabel hotelLabel, roomNumberLabel, begDateLabel, endDateLabel, infoLabel;
	private JComboBox hotelChoice, roomNumberChoice;
	private JComboBox begDateYearChoice, begDateMonthChoice, begDateDayChoice;
	private JComboBox endDateYearChoice, endDateMonthChoice, endDateDayChoice;
	private JButton submit;
	
	
	public MakeReservationView(MainMenu menu) {
		super(menu);
		init();
		setSizes();
		addElements();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		hotelLabel 		= new JLabel("Wybierz hotel:		");
		roomNumberLabel	= new JLabel("Numer pokoju:			");
		begDateLabel	= new JLabel("Przyjazd (YYYY-MM-DD):");
		endDateLabel	= new JLabel("Odjazd   (YYYY-MM-DD):");
		infoLabel		= new JLabel("info info");
		

		String hotels[] = {"Urnova Katowice","Urnova Wroclaw","Urnova Warszawa"};
		hotelChoice			= new  JComboBox(hotels);
		String rooms[] = {"1","2","3","4","5"};
		roomNumberChoice	= new  JComboBox(rooms);
		String years[] = {"2017","2018","2019","2020"};
		String months[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		String days[] = {	"01","02","03","04","05","06","07","08","09","10",
							"11","12","13","14","15","16","17","18","19","20",
							"21","22","23","24","25","26","27","28","29","30","31"};
		begDateYearChoice	= new  JComboBox(years);
		begDateMonthChoice	= new  JComboBox(months);
		begDateDayChoice	= new  JComboBox(days);
		endDateYearChoice	= new  JComboBox(years);
		endDateMonthChoice	= new  JComboBox(months);
		endDateDayChoice	= new  JComboBox(days);
		
		submit = new JButton("Submit Reservation");
	}

	@Override
	public void setSizes() {
		/* Ustawienie rozmiarow przycikow. */
		Dimension labelDim    =	new Dimension(200,20);	
		hotelLabel.setPreferredSize(labelDim); 
		roomNumberLabel.setPreferredSize(labelDim); 
		begDateLabel.setPreferredSize(labelDim); 
		endDateLabel.setPreferredSize(labelDim); 
		infoLabel.setPreferredSize(labelDim); 
		
		Dimension hotelsDim   =	new Dimension(150,20);	
		hotelChoice.setPreferredSize(hotelsDim);
		Dimension roomNumberDim   =	new Dimension(50,20);	
		roomNumberChoice.setPreferredSize(roomNumberDim);
		Dimension YearDim   =	new Dimension(55,20);			
		begDateYearChoice.setPreferredSize(YearDim);
		endDateYearChoice.setPreferredSize(YearDim);
		Dimension DayMonthDim   =	new Dimension(40,20);	
		begDateMonthChoice.setPreferredSize(DayMonthDim);
		begDateDayChoice.setPreferredSize(DayMonthDim);
		endDateMonthChoice.setPreferredSize(DayMonthDim);
		endDateDayChoice.setPreferredSize(DayMonthDim);
		/* Ustawienie miejsca obiektow w obrebie okna. */
		layout.putConstraint(SpringLayout.WEST,	 hotelLabel,	(int) (windowLength/2-labelDim.getWidth()), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, hotelLabel,	50,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 roomNumberLabel,	(int) (windowLength/2-labelDim.getWidth()), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, roomNumberLabel,	75,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 begDateLabel,	(int) (windowLength/2-labelDim.getWidth()), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, begDateLabel,	100,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 endDateLabel,	(int) (windowLength/2-labelDim.getWidth()), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, endDateLabel,	125,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 infoLabel,	(int) (windowLength/2), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, infoLabel,	150,	SpringLayout.NORTH, panel);
	
		layout.putConstraint(SpringLayout.WEST,	 hotelChoice,	(int) (windowLength/2-25), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, hotelChoice,	50,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 roomNumberChoice,	(int) (windowLength/2-25), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, roomNumberChoice,	75,	SpringLayout.NORTH, panel);
	
		layout.putConstraint(SpringLayout.WEST,	 begDateYearChoice,	(int) (windowLength/2-25), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, begDateYearChoice,	100,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 begDateMonthChoice,	(int) (windowLength/2-25+60), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, begDateMonthChoice,	100,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 begDateDayChoice,	(int) (windowLength/2-25+105), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, begDateDayChoice,	100,	SpringLayout.NORTH, panel);
	
		layout.putConstraint(SpringLayout.WEST,	 endDateYearChoice,	(int) (windowLength/2-25), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, endDateYearChoice,	125,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 endDateMonthChoice,	(int) (windowLength/2-25+60), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, endDateMonthChoice,	125,	SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST,	 endDateDayChoice,	(int) (windowLength/2-25+105), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, endDateDayChoice,	125,	SpringLayout.NORTH, panel);
	
		layout.putConstraint(SpringLayout.WEST,	 submit,	(int) (windowLength/2-labelDim.getWidth()+25), 	SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, submit,	160,	SpringLayout.NORTH, panel);
	
	}

	@Override
	public void addElements() {
		panel.add(submit); submit.addActionListener(client);
		panel.add(hotelLabel); 
		panel.add(roomNumberLabel); 
		panel.add(begDateLabel); panel.add(endDateLabel);
		panel.add(infoLabel);
		panel.add(hotelChoice);		panel.add(roomNumberChoice);
		panel.add(begDateYearChoice);		panel.add(begDateMonthChoice);		panel.add(begDateDayChoice);
		panel.add(endDateYearChoice);		panel.add(endDateMonthChoice);		panel.add(endDateDayChoice);
	}
	
	public String getHotel(){
		String ret = (String) this.hotelChoice.getItemAt(hotelChoice.getSelectedIndex());
		return ret;
	}
	
	public String getRoomNumber(){
		String ret = (String) this.roomNumberChoice.getItemAt(roomNumberChoice.getSelectedIndex());
		return ret;
	}
	
	public String getBegDate(){
		String ret = (String) this.begDateYearChoice.getItemAt(begDateYearChoice.getSelectedIndex());
		ret+= "-";
		ret += (String) this.begDateMonthChoice.getItemAt(begDateMonthChoice.getSelectedIndex());
		ret+= "-";
		ret += (String) this.begDateDayChoice.getItemAt(begDateDayChoice.getSelectedIndex());
		return ret;
	}
	
	public String getEndDate(){
		String ret = (String) this.endDateYearChoice.getItemAt(endDateYearChoice.getSelectedIndex());
		ret+= "-";
		ret += (String) this.endDateMonthChoice.getItemAt(endDateMonthChoice.getSelectedIndex());
		ret+= "-";
		ret += (String) this.endDateDayChoice.getItemAt(endDateDayChoice.getSelectedIndex());
		return ret;
	}

	@Override
	public String[] getMainActionString() {
		String mainAction[] = new String[4];
		mainAction[0] = getHotel();
		mainAction[1] = getRoomNumber();
		mainAction[2] = getBegDate();
		mainAction[3] = getEndDate();
		return mainAction;
	}

}
