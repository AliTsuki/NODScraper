package nodscraper;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

//this class is for setting up the GUI and calling the collection method from Gather.class
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener{
	
	static Calendar cal = Calendar.getInstance();	//get date as calendar for below
	static int month = cal.get(Calendar.MONTH)+1;	//get current month, add 1 because calendar starts at 0 in java
	static int day = cal.get(Calendar.DAY_OF_MONTH);	//get current day
	static int year = cal.get(Calendar.YEAR);	//get current year
	//set up all the objects for the GUI below
	static JFrame f = new JFrame("NOD Scraper " + Main.versionNumber);
	static JLabel mainLabel = new JLabel("Enter Date to search in format(MM/DD/YYYY):");
	static JTextField tMM = new JTextField(""+month, 3);
	static JTextField tDD = new JTextField(""+day, 3);
	static JTextField tYYYY = new JTextField(""+year, 5);
	static JButton buttonGo = new JButton("Go");
	static JLabel errorLabel = new JLabel("");
	//end of GUI objects
	
	public GUI(){
		
	}//end of GUI constructor

	public static void draw(){
		tMM.setDocument(new LengthRestrictedDocument(2));	//call method to keep length of input to a limit
		tMM.setText(""+month);	//set text to current month
		if (tMM.getText().length() == 1){
			tMM.setText("0"+month);	//make sure there's a zero in front of numbers if it is single digit month
		}//end of if statement
		tDD.setDocument(new LengthRestrictedDocument(2));	//call method to keep length of input to a limit
		tDD.setText(""+day);	//set text to current day
		if (tDD.getText().length() == 1){
			tDD.setText("0"+day);	//make sure there's a zero in front of numbers if it is single digit day
		}//end of if statement
		tYYYY.setDocument(new LengthRestrictedDocument(4));	//call method to keep length of input to a limit
		tYYYY.setText(""+year);	//set text to current year
		f.getContentPane().setLayout(new FlowLayout());	//setup initial layout
		//add all the GUI objects to pane
		f.getContentPane().add(mainLabel);
		f.getContentPane().add(tMM);
		f.getContentPane().add(tDD);
		f.getContentPane().add(tYYYY);
		f.getContentPane().add(buttonGo);
		GUI gui = new GUI();
		buttonGo.addActionListener(gui);
		f.getContentPane().add(errorLabel);
		f.pack();
		//end of GUI objects additions
		f.setVisible(true);	//self explanatory...
		f.setSize(500, 200);	//this too
		f.setResizable(false);	//also this
		f.setLocationRelativeTo(null);	//puts screen in center
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);	//terminate app upon pane closure
	}//end of draw method

	public void actionPerformed(ActionEvent l){
		errorLabel.setText("");	//initialize error label
		String MM = ""+tMM.getText();	//month you want to start search at
		String DD = ""+tDD.getText();	//day you want to start search at
		String YYYY = ""+tYYYY.getText();	//year you want to start search at
		int intMM = Integer.parseInt(MM);	//grab int value for month, for below
		int intDD = Integer.parseInt(DD);	//grab int value for day, for below
		int intYYYY = Integer.parseInt(YYYY);	//grab int value for year, for below
		if ((intYYYY < year) || (intYYYY == year && intMM < month) || (intYYYY == year && intMM == month && intDD <= day)){	//same as above
			if (isValidDate(YYYY+MM+DD) == true){	//same as above
				try{
					int results = Gather.collect(MM, DD, YYYY);	//gather results and return results number
					errorLabel.setText("Completed, check DATA folder. Results: "+results);	//notify of completion and number of results
					}//end of try statement
					catch (Exception e){
						e.printStackTrace();	//the usual
					}//end of catch statement
			}//end of if statement
			else{
				errorLabel.setText("INVALID DATE SELECTION");	//put in a correct date dummy!
			}//end of else statement
		}//end of if statement
		else{
			errorLabel.setText("CANNOT SEARCH FUTURE DATE");	//same
		}//end of else statement
	}//end of actionPerformed method
	
	public static boolean isValidDate(String dateString){	//for checking if date is a valid date, surprise surprise
	    if (dateString == null || dateString.length() != "yyyyMMdd".length()){
	        return false;
	    }//end of if statement
	    int date;
	    try{
	        date = Integer.parseInt(dateString);
	    }//end of try statement
	    catch (NumberFormatException e){
	        return false;
	    }//end of catch statement
	    int year = date / 10000;
	    int month = (date % 10000) / 100;
	    int day = date % 100;
	    // leap years calculation not valid before 1581
	    boolean yearOk = (year >= 1581) && (year <= 2500);
	    boolean monthOk = (month >= 1) && (month <= 12);
	    boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));
	    return (yearOk && monthOk && dayOk);
	}//end of isValidDate method

	private static int daysInMonth(int year, int month){
	    int daysInMonth;
	    switch (month){
	        case 1: // fall through
	        case 3: // fall through
	        case 5: // fall through
	        case 7: // fall through
	        case 8: // fall through
	        case 10: // fall through
	        case 12:
	            daysInMonth = 31;
	            break;
	        case 2:
	            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
	                daysInMonth = 29;
	            }//end of if statement
	            else{
	                daysInMonth = 28;
	            }//end of else statement
	            break;
	        default:
	            //returns 30 even for nonexistent months
	            daysInMonth = 30;
	    }//end of switch statement
	    return daysInMonth;
	}//end of daysInMonth method
}//end of GUI class