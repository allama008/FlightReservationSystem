package FRSManager;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * The class SearchMgr searches the possible flight combinations of type SpiceJet and SilkAir
 * that is available from a particular city, on a given date with a specific number of passengers.
 * 
 * @author Payal Paul , Oindrila Dhar
 *
 */
public class SearchMgr {

	private int countSJ;
	private int countSA;
	private int countCombo;
	
	/**
	 * Returns the SpiceJet array of flights that are possible in a particular case
	 * 
	 * @param city 	  The departing city
	 * @param date 	  The date of departure
	 * @param spiceSJ A SpiceJet array with all possible options
	 * @return 		  A SpiceJet array with the options possible in this particular case
	 * @see 		  SpiceJet
	 */
	private SpiceJet[] findFlights(String city, Date date, SpiceJet spiceSJ[]){
		SpiceJet []flights = new SpiceJet[20];
		int i;
		int countFlight = 0;

		for(i = 0; i < spiceSJ.length; i++){
			
			boolean bCity = city.equals(spiceSJ[i].getOrigin());
			boolean bEffFrom = date.compareTo(spiceSJ[i].getEffectiveFrom()) >=0;
			boolean bEffTo = date.compareTo(spiceSJ[i].getEffectiveTo()) <= 0;
			boolean bDual = false;
			if(countFlight > 0){
				if(flights[countFlight - 1].getFlightId().equals(spiceSJ[i].getFlightId()))
					bDual = true;
				else
					bDual = false;
			}
			
			if(bCity && bEffFrom && bEffTo && !bDual){
				flights[countFlight] = spiceSJ[i];
				countFlight++;
			}
		}
		countSJ = countFlight;
		return flights;
	}
	
	/**
	 * Returns the SilkAir array of flights that are possible in a particular case
	 * 
	 * @param city 	 The departing city
	 * @param date 	 The date of departure
	 * @param silkSA A SilkAir array with all possible options
	 * @return 		 A SpiceJet array with the options possible in this particular case
	 * @see 		 SilkAir
	 */
	private SilkAir[] findFlights(String city, Date date, SilkAir silkSA[]){
		SilkAir []flights = new SilkAir[10];
		int i;
		int countFlight = 0;
		for(i = 0; i < silkSA.length; i++){
			
			boolean bCity = city.equals(silkSA[i].getOrigin());
			boolean bEffFrom = date.compareTo(silkSA[i].getEffectiveFrom()) >= 0;
			boolean bEffTo = date.compareTo(silkSA[i].getEffectiveTo()) <= 0;
			boolean bDual = false;
			if(countFlight > 0){
				if(flights[countFlight - 1].getFlightId().equals(silkSA[i].getFlightId()))
					bDual = true;
				else
					bDual = false;
			}
			
			if(bCity && bEffFrom &&bEffTo && !bDual){
				flights[countFlight] = silkSA[i];
				countFlight++;
			}
		}
		countSA = countFlight;
		return flights;
	}
	
	/**
	 * Returns the FlightCombo array of flight combinations that are possible in a particular case
	 * 
	 * @param city 	  The departing city
	 * @param date 	  The date of departure
	 * @param people  The number of passengers
	 * @param spiceSJ A SpiceJet array with all possible options
	 * @param silkSA  A SilkAir array with all possible options
	 * @return 		  A FlightCombo array of flight combinations that are possible in this particular case
	 * @see 		  FlightCombo
	 */
	public FlightCombo[] search(String city, Date date, int people, SpiceJet spiceSJ[], SilkAir silkSA[]){
		
		FlightCombo[] flcombo = new FlightCombo[50];
		int numOfCombos = 0;
		SpiceJet []mySJ = new SpiceJet[20];
		SilkAir []mySA = new SilkAir[10];
		int i, j;
		
		mySJ = findFlights(city, date, spiceSJ);
		
		for(i = 0; i < countSJ; i++){
			
			mySA = findFlights(mySJ[i].getDestination(), date, silkSA);
			
			for(j = 0; j < countSA; j++){
				flcombo[numOfCombos] = new FlightCombo(mySJ[i], mySA[j], date, date);
				long transit = flcombo[numOfCombos].getTransitTime();
				if((transit >= 120 && transit <= 360))
					numOfCombos++;
				else
					flcombo[numOfCombos] = null;
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			
			mySA = findFlights(mySJ[i].getDestination(), cal.getTime(), silkSA);
			
			for(j = 0; j < countSA; j++){
				flcombo[numOfCombos] = new FlightCombo(mySJ[i], mySA[j], date, cal.getTime());
				long transit = flcombo[numOfCombos].getTransitTime();
				if((transit >= 120 && transit <= 360) )
					numOfCombos++;
				else{
					
					flcombo[numOfCombos] = null;
				}
			}
			
			
			
		}
		
		for(i = 0; i < numOfCombos; i++){
			
			if(checkCombo(flcombo[i], people) == false){
				for(j = i; j < numOfCombos - 1; j++){
					flcombo[j] = flcombo[j+1];
				}
				i--;
				numOfCombos--;
			}
			
		}
		countCombo = numOfCombos;
		
		sort(flcombo,numOfCombos);
		return flcombo;
		
	}
	
	/**
	 * Checks whether the given flight combination is possible with the given number of passengers.
	 * Returns true if the given combination can accommodate the given number of passengers. Otherwise it returns false
	 * 
	 * @param combo  A FlightCombo object
	 * @param people The number of passengers flying
	 * @return 		 Returns true if the given flight combination can accommodate given number of passengers ,else
	 * 				 returns false
	 * @see 		 FlightCombo
	 */
	private boolean checkCombo(FlightCombo combo, int people){
		
		boolean bPeopleSJ = true, bPeopleSA = true;
		
		//Checking for SpiceJet
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("SpiceJet.xml");
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		NodeList nl = doc.getElementsByTagName("sjflight");
		
		for(int k = 0; k < nl.getLength(); k++){
			Node n = nl.item(k);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element)n;
				String d = e.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
				
				if(d.equals(df.format(combo.getDateSJ()))){
					String c = e.getElementsByTagName("source").item(0).getFirstChild().getNodeValue();
					String fid = e.getElementsByTagName("flightid").item(0).getFirstChild().getNodeValue();
					String departure = e.getElementsByTagName("departure").item(0).getFirstChild().getNodeValue();
					String arrival = e.getElementsByTagName("arrival").item(0).getFirstChild().getNodeValue();
					int seats = Integer.parseInt(e.getElementsByTagName("seats").item(0).getFirstChild().getNodeValue());
					
					if(c.equals(combo.getFlightSJ().getOrigin()) && fid.equals(combo.getFlightSJ().getFlightId()) && seats >= people){
						
						DateFormat deparr  = new SimpleDateFormat("HH:mm");
						Date  dep1 = null, dep2 = null, arr1 = null, arr2 = null;
						try {
							dep1 = deparr.parse(departure);
							dep2 = deparr.parse(combo.getFlightSJ().getDeparture());
							arr1 = deparr.parse(arrival);
							arr2 = deparr.parse(combo.getFlightSJ().getArrival());
							
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if(dep1.compareTo(dep2)==0 && arr1.compareTo(arr2)==0){
							bPeopleSJ = true;
							break;
						}
						
					}
						
				}
				else
					bPeopleSJ = false;
			}
		}
		
		
		//Checking for SilkAir
		doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("SilkAir.xml");
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		df = new SimpleDateFormat("dd/MM/yyyy");
		nl = doc.getElementsByTagName("saflight");
		
		for(int k = 0; k < nl.getLength(); k++){
			Node n = nl.item(k);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element)n;
				String d = e.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
				
				if(d.equals(df.format(combo.getDateSA()))){
					String c = e.getElementsByTagName("source").item(0).getFirstChild().getNodeValue();
					String fid = e.getElementsByTagName("flightid").item(0).getFirstChild().getNodeValue();
					String departure = e.getElementsByTagName("departure").item(0).getFirstChild().getNodeValue();
					String arrival = e.getElementsByTagName("arrival").item(0).getFirstChild().getNodeValue();
					int seats = Integer.parseInt(e.getElementsByTagName("seats").item(0).getFirstChild().getNodeValue());
					
					if(c.equals(combo.getFlightSA().getOrigin()) && fid.equals(combo.getFlightSA().getFlightId()) && seats >= people){
						
						DateFormat deparr  = new SimpleDateFormat("HH:mm");
						Date  dep1 = null, dep2 = null, arr1 = null, arr2 = null;
						try {
							dep1 = deparr.parse(departure);
							dep2 = deparr.parse(combo.getFlightSA().getDeparture());
							arr1 = deparr.parse(arrival);
							arr2 = deparr.parse(combo.getFlightSA().getArrival());
							
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if(dep1.compareTo(dep2)==0 && arr1.compareTo(arr2)==0){
							bPeopleSA = true;
							break;
						}
					}	
				}
				else
					bPeopleSA = false;
			}
		}
		
		if(bPeopleSJ && bPeopleSA)
			return true;
		else
			return false;
	
	}
	
	/**
	 * Sorts the FlightCombo array in ascending order of their travel time
	 * 
	 * @param flcombo An array of FlightCombo type
	 * @param n		  The number of elements in the array
	 */
	private void sort(FlightCombo[] flcombo, int n) {
		int i,j;
		FlightCombo temp;
		for(i = 0; i < n-1; i++)
		{
			for(j = 0; j < n-1-i; j++)
			{
				if(flcombo[j].getTravelTime() > flcombo[j+1].getTravelTime())
				{
					temp = flcombo[j];
					flcombo[j] = flcombo[j+1];
					flcombo[j+1] = temp;
				}
			}
		}
		
	}

	/**
	 * Returns the number of flight combinations that are possible
	 * @return Returns the possible number of flight combinations
	 */
	public int getCountCombo() {
		return countCombo;
	}
	
}
