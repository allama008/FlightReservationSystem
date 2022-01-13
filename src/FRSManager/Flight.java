package FRSManager;
import java.util.Date;
/**
 * Entity class for storing all the attributes related to a flight
 * @author Rashika
 */
public class Flight {
	
    private String flightId="";
    private String origin="";
    private String departure="";
    private String arrival="";
    private String daysOfWeek="";
    private Date effectiveFrom;
    private Date effectiveTo;

    /**
     * Initializes FlightId, OriginCity, Departure, Arrival and Departure time, Effective Dates and Days of the week it operates
     * @param flightId Flight id 
     * @param origin Origin City 
     * @param departure Departure Time of the flight
     * @param arrival Arrival time of the flight
     * @param daysOfWeek Days of operation
     * @param effectiveFrom Date it is effective from
     * @param effectiveTo Date it is effective to
     */
    public Flight(String flightId, String origin, String departure, String arrival, String daysOfWeek, Date effectiveFrom, Date effectiveTo){
    	this.flightId = flightId;
    	this.origin = origin;
    	this.departure = departure;
    	this.arrival = arrival;
    	this.daysOfWeek = daysOfWeek;
    	this.effectiveFrom = effectiveFrom;
    	this.effectiveTo = effectiveTo;
    }
        /**
         * Returns the flightId of the Flight Object
         * @return String
         */
	public String getFlightId() {
		return flightId;
	}
        /**
         * Returns the origin or source city of the Flight Object
         * @return String
         */
        
	public String getOrigin() {
		return origin;
	}
        /**
         * Returns the Departure city of the Flight Object
         * @return String
         */
	public String getDeparture() {
		return departure;
	}
        /**
         * Returns the Arrival time of the Flight Object
         * @return String
         */
	public String getArrival() {
		return arrival;
	}
        /**
         * Returns the Days of the week it operates on
         * @return String
         */
	public String getDaysOfWeek() {
		return daysOfWeek;
	}
        /**
         * Returns the Date it is effective from
         * @return Date
         */
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
        /**
         * Returns the Date it is effective upto
         * @return Date
         */
	public Date getEffectiveTo() {
		return effectiveTo;
	}
    
}
