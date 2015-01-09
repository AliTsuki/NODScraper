/**
 * For Scraping HTML data from a Notice of Default search on http://www.utahcounty.gov/LandRecords
 * http://jsoup.org/apidocs/ //Documentation for Java HTML parser API
 * https://github.com/PhotonPhighter/NODScraper //Location of SOURCE CODE
 */

package nodscraper;

import java.io.IOException;

/**
 * @author Ali M (PhotonPhighter/Admiral Nero the XCIV)
 */

public class Main{

	public static String versionNumber = "2.0.1a";
	
	public Main(){
		
	}//end of Main constructor
	
	public static void main(String[] args) throws IOException{
		GUI.draw();	//run the GUI draw method, which draws the GUI and handles the call to the method to gather data from button press
	}//end of Main method
}//end of Main class