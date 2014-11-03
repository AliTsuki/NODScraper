package nodscraper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//this class is to gather all the information from the website and write it to a CSV file
public class Gather{

	static int returnInt = 0;	//initialize the return value for collect method
	
	public Gather(){
		
	}//end of Gather constructor
	
	public static int collect(String MM, String DD, String YYYY){
		try{
			print("NOD Scraper version: "+Main.versionNumber);
			print("for Support, contact Ali M <photonphighter@gmail.com>");
			CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			jarDir = jarDir+"/";
			Date date = new Date();	//get date for use below
			Calendar cal = Calendar.getInstance();	//get date as calendar for below
			cal.setTime(date);	//set calendar time to date
			int month = cal.get(Calendar.MONTH)+1;	//get current month, add 1 because calendar starts at 0 in java
			int day = cal.get(Calendar.DAY_OF_MONTH);	//get current day
			int year = cal.get(Calendar.YEAR);	//get current year
			int hour = cal.get(Calendar.HOUR);	//get current hour
			int minutes = cal.get(Calendar.MINUTE);	//get current minute
			int seconds = cal.get(Calendar.SECOND);	//get current seconds
			String stringDateExecuted = (month+"."+day+"."+year+"-"+hour+"."+minutes+"."+seconds);	//put all the current date data into a string for naming files
			String sFileName = (jarDir+"DATA/"+MM+"."+DD+"."+YYYY+"--"+stringDateExecuted);	//set file name to date looked up and date app ran
			File dirDATA = new File(jarDir+"DATA/");	//create the bones for a new directory for DATA
			if (!dirDATA.exists()){	//check if directory already exists, if not do below
				System.out.println("creating directory: " + dirDATA + "\n");	//print to console to affirm creation of directory
			    boolean result = false;	//starting value
			    try{
			        dirDATA.mkdir();	//create the directory
			        result = true;	//end value
			     }//end of try
			    catch(SecurityException se){}	//end of catch     
			     if (result){	//if result is at end value, do below 
			       System.out.println("DIR created\n");	//print to console to affirm completion of directory creation
			     }//end of if statement
			}//end of if statement
			File dirLOGS = new File(jarDir+"LOGS/");	//create the bones for a new directory for LOGS
			if (!dirLOGS.exists()){	//check if directory already exists, if not do below
				System.out.println("creating directory: " + dirLOGS + "\n");	//print to console to affirm creation of directory
				boolean result = false;	//starting value
				try{
					dirLOGS.mkdir();	//create the directory
					result = true;	//end value
				} //end of try
				catch(SecurityException se){}	//end of catch        
				if (result){	//if result is at end value, do below  
					System.out.println("DIR created\n");	//print to console to affirm completion of directory creation
				}//end of if statement
			}//end of if statement
			System.out.println(sFileName+"\n");	//print to console the file name for logging
			FileWriter writer = new FileWriter(sFileName+".csv");	//set up writer for CSV file
			File fileCSV = new File(sFileName+".csv");	//set up the bones for the CSV file
			if(!fileCSV.exists()) fileCSV.createNewFile();	//if CSV file doesn't exist, it shouldn't, then create it
			FileOutputStream fileLOG = new FileOutputStream(jarDir+"/LOGS/log-ver"+Main.versionNumber+"-"+month+"."+day+"."+year+"."+minutes+"."+seconds+".txt");	//set up stream to capture console to log
		    NODPrintStream NOD = new NODPrintStream(fileLOG, System.out);	//set up console logging
		    System.setOut(NOD);	//set up console logging
		    writer.append("Tax ID");	//setting up row names of CSV file
			writer.append(';');
			writer.append("Grantee Name");
			writer.append(';');
			writer.append("Property Address");
			writer.append(';');
			writer.append("Mailing Address");
			writer.append('\n');	//rows complete
			String stringURLofStartSearchPage = "http://www.utahcounty.gov/LandRecords/DocKoi.asp?avKoi=ND&avEntryDate="+MM+"%2F"+DD+"%2F"+YYYY+"&submit=Search";	//the URL of the page including date
			print("OOO-->Fetching current ND records from Date %s/%s/%s\n site: <%s>...\n", MM, DD, YYYY, stringURLofStartSearchPage);	//print current action
			try{
				try{
					Connection conn = Jsoup.connect(stringURLofStartSearchPage);	//set up connection
					conn.timeout(120000);	//generous timeout length
					Document documentUponCompletedSearchPageData = conn.get();	//connect to URL
					Elements linksFromDocumentUponCompletedSearchPageData = documentUponCompletedSearchPageData.select("a[href]");	//get all the links in the current document and put them in a list
					print("Total Links on Page before scrub: " + linksFromDocumentUponCompletedSearchPageData.size());
					linksFromDocumentUponCompletedSearchPageData.remove(0);	//remove first link, as it is superfluous
					for (int i=0; i<4; i++){	//iterate to remove the bottom 4 links, as they are superfluous
						linksFromDocumentUponCompletedSearchPageData.remove(linksFromDocumentUponCompletedSearchPageData.size()-1);	//just said it, right there above
					}//end of for loop
					print("---->Number of total records from date forward: " + linksFromDocumentUponCompletedSearchPageData.size()+"\n");	//print how many links are left in the list from completed search
					if (linksFromDocumentUponCompletedSearchPageData.size() >= 100){
						//need to fix if searching over 100 records
					}//end of if statement
					for (Element link : linksFromDocumentUponCompletedSearchPageData){	//for every link element in the list of links do what's below
						String stringFileEntryNumber = trim(link.text(), 35);	//collect the entry number for each case
						String stringURLOfFileEntryNumberLinkOnPage = link.attr("abs:href");	//collect the URL for connecting to particular entry number's page
						print("++++++ URL of file entry number page: <%s>", stringURLOfFileEntryNumberLinkOnPage);	//ignore, for debugging
						print(" -- File Entry Number: (%s)", stringFileEntryNumber);	//print out the gathered entry number
						Document documentEntryNumberPageData = Jsoup.connect(stringURLOfFileEntryNumberLinkOnPage).get();	//connect to the URL for the entry number's page, to get the name
						Elements linksFromEntryNumberPageData = documentEntryNumberPageData.select("a[href]");	//get all the links listed on entry number page
						print(" -- Links on file entry page: " + linksFromEntryNumberPageData.size());	//ignore, for debugging
						linksFromEntryNumberPageData.remove(0);	//remove first link as it is superfluous
						linksFromEntryNumberPageData.remove(0);	//remove second link as it is superfluous
						for (int i=linksFromEntryNumberPageData.size(); i>1; i--){	//iterate through the links and remove all the superfluous ones left at bottom of list
							linksFromEntryNumberPageData.remove(linksFromEntryNumberPageData.size()-1);	//yep
						}//end of nested for loop
						String stringURLOfGranteeNamePage = linksFromEntryNumberPageData.attr("abs:href");	//get the URL containing the grantee's name
						String stringGranteeName = trim(linksFromEntryNumberPageData.text(), 35);	//get the actual name out of the link
						for (Element link2 : linksFromEntryNumberPageData){	//for all the links left, do below
							stringURLOfGranteeNamePage = link2.attr("abs:href");	//collect URL containing grantee name
							print(" -- URL of grantee name page: <%s>", stringURLOfGranteeNamePage);	//ignore, for debugging
							print(" -- Grantee Name: (%s)", stringGranteeName);	//print the Grantee's name, going to use it for search later
							String stringURLOfNameSearchPage = "http://www.utahcounty.gov/LandRecords/NameSearch.asp?av_name="+stringGranteeName+"&av_valid=...&Submit=Search";	//URL to use in name search
							Document documentCompletedNameSearchResultsPageData = Jsoup.connect(stringURLOfNameSearchPage).get();	//connect to the URL for specific name search
							Elements linksFromCompletedNameSearchResultsPageData = documentCompletedNameSearchResultsPageData.select("a[href]");	//collect all links on the specified URL
							print(" -- Links on completed name search page before scrub: " + linksFromCompletedNameSearchResultsPageData.size());	//ignore, for debugging
							linksFromCompletedNameSearchResultsPageData.remove(0);	//remove first result, as it is superfluous
							for (int i=linksFromCompletedNameSearchResultsPageData.size(); i>1; i--){	//remove superfluous links on page from list
								linksFromCompletedNameSearchResultsPageData.remove(linksFromCompletedNameSearchResultsPageData.size()-1);
							}//end of nested for loop
							for (Element link3 : linksFromCompletedNameSearchResultsPageData){	//for all the links left, do below
								String stringURLOfSerialNumberPage = link3.attr("abs:href");	//collect serial number URL
								String stringSerialNumber = trim(link3.text(), 35);	//collect serial number
								print(" -- URL of name search page: <%s>", stringURLOfNameSearchPage);	//ignore, for debugging
								print(" -- Links on completed name search page: " + linksFromCompletedNameSearchResultsPageData.size());	//ignore, for debugging
								print(" -- URL of serial number page for entry: <%s>", stringURLOfSerialNumberPage);	//ignore, for debugging
								print(" -- Serial Number: (%s)", stringSerialNumber);	//ignore, for debugging
								if (stringURLOfSerialNumberPage.length() > 50){	//make sure that the serial number URL is a correct URL by checking length is in normal range
									Document documentSerialNumberPageData = Jsoup.connect(stringURLOfSerialNumberPage).get();	//connect to serial number page
									Elements innerTable = documentSerialNumberPageData.select("body > table:nth-child(2) > tbody > tr > td > table > tbody > tr > td > table:nth-child(2)");	//select the correct tables of data
									String stringPropertyAddress = ((org.jsoup.nodes.TextNode)innerTable.select("tr:nth-child(3) > td > strong").first().nextSibling()).text();	//grab property address
									String stringMailingAddress = ((org.jsoup.nodes.TextNode)innerTable.select("tr:nth-child(4) > td > strong").first().nextSibling()).text();	//grab mailing address
									System.out.printf(" -- Property Address: %s\n -- Mailing Address: %s\n", stringPropertyAddress, stringMailingAddress);	//print out addresses
									stringSerialNumber = stringSerialNumber.trim();
									if (stringSerialNumber != null) writer.append(stringSerialNumber);	//write to CSV the different datas, if it is null, make us aware
									else writer.append("NO TAX ID PROVIDED");
									writer.append(';');
									stringGranteeName = stringGranteeName.trim();
									if (stringGranteeName != null) writer.append(stringGranteeName);
									else writer.append("NO GRANTEE NAME PROVIDED");
									writer.append(';');
									stringPropertyAddress = stringPropertyAddress.trim();
									if (stringPropertyAddress != null) writer.append(stringPropertyAddress);
									else writer.append("NO PROPERTY ADDRESS PROVIDED");
									writer.append(';');
									stringMailingAddress = stringMailingAddress.trim();
									if (stringMailingAddress != null) writer.append(stringMailingAddress);
									else writer.append("NO MAILING ADDRESS PROVIDED");
									writer.append('\n');
									writer.flush();	//flush the writer after each iteration to keep it from crashing
									returnInt = linksFromDocumentUponCompletedSearchPageData.size();	//set return value to number of results processed
								}//end of if statement
								else print("*** ERROR *** Serial Number Page not available for that name");	//let us know if there is no TAX ID for entry
							}//end of nested for loop
						}//end of nested for loop
					}//end of for loop
					System.out.println("\n----> END OF DATA STREAM");	//print end of data disclosure for logs
					writer.close();	//close up the writer to terminate
				}//end of try statement
				catch (SocketTimeoutException e){
					e.printStackTrace();	//if there is a timeout, print stack trace
					System.out.println("!*!*!*! CONNECTION TIMEOUT !*!*!*!");	//also print to console, just cause
				}//end of catch statement
			}//end of try statement
			catch(IOException e){e.printStackTrace();}	//the usual
		}//end of try statement
		catch (IOException | URISyntaxException e){e.printStackTrace();}	//the usual
		return returnInt;	//return the results int for the method
	}//end of collect method

	private static void print(String msg, Object... args){
		System.out.println(String.format(msg,  args));	//useful method for printing to console for debugging
	}//end of print method
	
	private static String trim(String s, int width){
		if (s.length() > width)
			return s.substring(0, width-1)+".";
		else
			return s;	//useful method for trimming Strings for links list and such
	}//end of trim method
}//end of Gather class