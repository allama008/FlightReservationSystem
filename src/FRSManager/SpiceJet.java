package FRSManager;
import java.util.Date;
/**
 * Extends the Flight class and used for extra attributes of SpiceJet Flight
 * @author Rashika
 */
public class SpiceJet extends Flight {
	
    private String connCity;
    private String destination;
        /**
         * Initializes Connecting City and Destination and calls the Flight class constructor
         * @param flightId Flight Id
         * @param origin Origin City
         * @param departure Departure Time of the flight
         * @param arrival Arrival Time of the flight
         * @param daysOfWeek Days of operation
         * @param effectiveFrom Date it is Effective From
         * @param effectiveTo Date it is Effective to
         * @param connCity Connecting City of the Flight
         * @param destination Destination City of the Flight
         */
	public SpiceJet(String flightId, String origin, String departure, String arrival, String daysOfWeek,
			Date effectiveFrom, Date effectiveTo, String connCity, String destination) {
		super(flightId, origin, departure, arrival, daysOfWeek, effectiveFrom, effectiveTo);
		this.connCity = connCity;
		this.destination = destination;
	}
        /**
         * Returns the Connecting City of SpiceJet Flight
         * @return String
         */
	public String getConnCity() {
		return connCity;
	}
        /**
         * Returns the Destination City of SpiceJet Flight
         * @return String
         */
	public String getDestination() {
		return destination;
	}
	
	
}
