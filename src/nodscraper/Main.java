/**
 * For Scraping HTML data from a Notice of Default search on http://www.utahcounty.gov/LandRecords
 * 
 * http://www.utahcounty.gov/LandRecords/DocKoiForm.asp
 * Enter Kind of Instrument (KOI) "ND" //Notice of Default
 * Beginning Recording Date "<TODAYSDATE>" //Search only for today
 * http://www.utahcounty.gov/LandRecords/DocKoi.asp?avKoi=ND&avEntryDate=10%2F02%2F2014&Submit=Search //autogenerated URL
 * html>body>table>tbody>tr>td>table>tbody>tr>td>a //sequence of address of info
 * <a href="document.asp?avEntry=70625&amp;avYear=2014">70625</a>
 * Go to Address
 * http://www.utahcounty.gov/LandRecords/document.asp?avEntry=70625&avYear=2014
 * html>body>table>tbody>tr>td>table>tbody>tr>td>a
 * <a href="PartyName.asp?avname=KANTARIS, DWAYNE">KANTARIS, DWAYNE</a> //STORE NAME
 * Use above Name for
 * http://www.utahcountyonline.org/LandRecords/NameSearchForm.asp
 * Enter Name to search for: "NAME"
 * http://www.utahcountyonline.org/LandRecords/NameSearch.asp?av_name=KANTARIS%2C+DWAYNE&av_valid=%25&Submit=Search
 * Go to Serial# next to Years Valid line with Green text and ellipses 
 * OWNER NAME		SERIAL NUMBER	TAX D	YEARS	PROPERTY ADDRESS //APPEARS ON PAGE
 * KANTARIS, DWAYNE	49:352:0010	 	(150)	2004...	324 E 1000 NORTH - SPANISH FORK //APPEARS ON PAGE
 * html>body>table>tbody>tr>td>table>tbody>tr>td>a
 * <a href="property.asp?av_serial=493520010003">49:352:0010</a> //STORE SERIAL #
 * http://www.utahcounty.gov/LandRecords/Property.asp?av_serial=493520010003 //URL OF SERIAL # FOR PAGE
 * Property Address:  324 E 1000 NORTH - SPANISH FORK //APPEARS ON PAGE
 * Mailing Address: 522 N 600 E OREM, UT 84097-4208 //APPEARS ON PAGE
 * html>body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>strong
 * <strong>Property Address:</strong>
 * html>body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>(text)
 * &nbsp; 324 E 1000 NORTH - SPANISH FORK //STORE AS PROPERTY ADDRESS
 * html>body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>strong
 * <strong>Mailing Address:</strong>
 * html>body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>(text)
 *  " 522 N 600 E  OREM, UT 84097-4208" //STORE AS MAILING ADDRESS
 * html>body>table>tbody>tr>td>table>tbody>tr>td>select#jumpMenu.forcestyle>option
 * <option value="Abstract.asp?av_serial=49:352:0010">Abstract</option>
 * http://www.utahcounty.gov/LandRecords/Abstract.asp?av_serial=49:352:0010 //STORE ALL HTML
 * html>body>table>tbody>tr>td>em.italic>table>tbody>tr
 * Need any number $ on far right table CONSIDERATION, SATISFACTION, TIE ENTRY NO
 * Get left side for values other than 0, GRANTOR, GRANTEE, COMMENTS //STORE ALL GRANTOR/GRANTEE INFO
 * END
 * 
 * http://jsoup.org/apidocs/ //JSOUP IS BETTER
 * https://github.com/PhotonPhighter/NODScraper
 */

package nodscraper;

import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Ali M
 */

public class Main{
	/**
	 * ARGGIMOOOLIDOO NOTHING TO SEE HERE
	 */
	public Main(){
		//Auto-generated constructor stub, exists for reasons and stuff, not really
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		//?? Experimental ??
		String MM = "10";	//month you want to start search at
		String DD = "01";	//day you want to start search at
		String YYYY = "2014";	//year you want to start search at
		String siteStringURL = "http://www.utahcounty.gov/LandRecords/DocKoi.asp?avKoi=ND&avEntryDate="+MM+"%2F"+DD+"%2F"+YYYY+"&submit=Search";	//the URL of the page including date
		try{
			Document doc = Jsoup.connect(siteStringURL+MM+"%2F"+DD+"%2F"+YYYY+"&submit=Search").get();
			doc.childNodes();
			System.out.println(doc);
		}//end of try statement
		catch (IOException e){
			e.printStackTrace();
		}//end of catch statment
	}//end of Main method
}//end of Main class