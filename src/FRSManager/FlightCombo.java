package FRSManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The FlightCombo class represents a combination of SpiceJet flight and SilkAir flight
 * 
 * @author Oindrila Dhar
 *
 */
public class FlightCombo {
	private SpiceJet flightSJ;
	private SilkAir flightSA;
	public Date dateSJ;
	public Date dateSA;
	private long travelTime;
	private long transitTime;
	private long travelSJ;
        private long travelSA;
	
    /**
     * Creates a FlightCombo object with the given SpiceJet and SilkAir objects and the given Dates
     *  
     * @param flightSJ A SpiceJet object
     * @param flightSA A SilkAir object
     * @param dateSJ   Date of SpiceJet flight
     * @param dateSA   Date of SilkAir flight
     * @see 		   SpiceJet
     * @see 		   SilkAir
     * @see 		   Date
     */
	FlightCombo(SpiceJet flightSJ, SilkAir flightSA, Date dateSJ, Date dateSA){
		
		this.flightSJ = flightSJ;
		this.flightSA = flightSA;
		this.dateSJ = dateSJ;
		this.dateSA = dateSA;
		calculateTransitTime();
		calculateTravelTime();
	}

	/**
	 * Calculates the time difference between two Date objects
	 * 
	 * @param from The starting time
	 * @param to   The ending time
	 * @return	   The time difference between the starting and ending time
	 * @see		   Date
	 */
	private long timeDifference(Date from, Date to){
		
		long journey;
		long start = (from.getTime() / 1000) / 60;
		long end = (to.getTime() / 1000) / 60;
		journey = end - start;
		return(journey);
	}
	
	/**
	 * Calculates the total flight time of the flight combination
	 */
	private void calculateTravelTime() {
		
		long time = 0;
		String sjStart = flightSJ.getDeparture();
		String sjEnd = flightSJ.getArrival();
		String saStart = flightSA.getDeparture();
		String saEnd = flightSA.getArrival();
		
		boolean addADay = false;
		if(saStart.compareTo(saEnd)>0){
			addADay = true;
		}
		
		DateFormat dfDate = new SimpleDateFormat("dd MMM yy");
		String sjDate = dfDate.format(dateSJ);
		String saDate = dfDate.format(dateSA);
		DateFormat df = new SimpleDateFormat("dd MMM yy HH:mm");
		Date startSJ = new Date();
		Date endSJ = new Date();
		Date startSA = new Date();
		Date endSA = new Date();
		try {
			startSJ = df.parse(sjDate + " " + sjStart);
			endSJ = df.parse(sjDate + " " + sjEnd);
			startSA = df.parse(saDate + " " + saStart);
			endSA = df.parse(saDate + " " + saEnd);
			
			if(addADay == true){
				Calendar cal = Calendar.getInstance();
				cal.setTime(endSA);
				cal.add(Calendar.DATE, 1);
				endSA = cal.getTime();
			}
			
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		travelSJ = timeDifference(startSJ,endSJ);
		travelSA = timeDifference(startSA, endSA);
		
		time = travelSJ + travelSA + getTransitTime();
		
		setTravelTime(time);
	}
	
	/**
	 * Calculates the transit time between the two flights in the combination
	 */
	private void calculateTransitTime() {
		
		long time = 0;
		String sjEnd = flightSJ.getArrival();
		String saStart = flightSA.getDeparture();
		DateFormat dfDate = new SimpleDateFormat("dd MMM yy");
		String sjDate = dfDate.format(dateSJ);
		String saDate = dfDate.format(dateSA);
		DateFormat df = new SimpleDateFormat("dd MMM yy HH:mm");
		Date endSJ = new Date();
		Date startSA = new Date();
		try {
			endSJ = df.parse(sjDate + " " + sjEnd);
			startSA = df.parse(saDate + " " + saStart);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		time = timeDifference(endSJ, startSA);
		setTransitTime(time);
	}
	
	/**
	 * Returns the SpiceJet object on the FlightCombo object
	 * 
	 * @return A SpiceJet object
	 * @see	   SpiceJet
	 */
	public SpiceJet getFlightSJ() {
		return flightSJ;
	}
	
	/**
	 * Returns the SilkAir object on the FlightCombo object
	 * 
	 * @return A SilkAir object
	 * @see    SilkAir
	 */
	public SilkAir getFlightSA() {
		return flightSA;
	}
	
	/**
	 * Returns the travel time of the FlightCombo object in minutes
	 * 
	 * @return Travel time of the FlightCombo object in minutes
	 */
	public long getTravelTime() {
		return travelTime;
	}
	
	/**
	 * Sets the travel time of the FlightCombo object in minutes
	 * 
	 * @param travelTime Travel time of the FlightCombo in minutes
	 */
	private void setTravelTime(long travelTime) {
		this.travelTime = travelTime;
	}
	
	/**
	 * Returns the transit time of the FlightCombo object in minutes
	 * 
	 * @return Transit time of the FlightCombo object in minutes
	 */
	public long getTransitTime() {
		return transitTime;
	}
	
	/**
	 * Sets the transit time of the FlightCombo object in minutes
	 * 
	 * @param transitTime Transit time of the FlightCombo in minutes
	 */
	private void setTransitTime(long transitTime) {
		this.transitTime = transitTime;
	}
	
	/**
	 * Returns the date of the SpiceJet flight
	 * 
	 * @return Date of the SpiceJet Flight
	 * @see    Date
	 */
	public Date getDateSJ() {
		return dateSJ;
	}

	/**
	 * Returns the date of the SilkAir flight
	 * 
	 * @return Date of the SilkAir flight
	 * @see    Date
	 */
	public Date getDateSA() {
		return dateSA;
	}
	
	/**
	 * Returns the travel time if SpiceJet object in minutes
	 * 
	 * @return Travel time if SpiceJet object in minutes
	 */
	public long getTravelSJ() {
		return travelSJ;
	}

	/**
	 * Returns the travel time of the SilkAir object in minutes
	 * 
	 * @return Travel time of the SilkAir object in minutes
	 */
	public long getTravelSA() {
		return travelSA;
	}
}
