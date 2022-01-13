package FRSManager;
import java.util.Date;
/**
 * Extends Flight Class and used for extra attributes associated with SilkAir Flight only
 * @author Rashika
 */
public class SilkAir extends Flight {

	private final String destination;
	private Date []excDates;
	/**
         * Calls the Flight Class Constructor and initializes destination
         * @param flightId FlightId
         * @param origin Origin City
         * @param departure Departure time of the flight
         * @param arrival Arrival Time of the flight
         * @param daysOfWeek Days of operation
         * @param effectiveFrom Date it is Effective From
         * @param effectiveTo Date it is Effective To
         */
	public SilkAir(String flightId, String origin, String departure, String arrival, String daysOfWeek,
			Date effectiveFrom, Date effectiveTo) {
		super(flightId, origin, departure, arrival, daysOfWeek, effectiveFrom, effectiveTo);
		destination = "Singapore(SIN)";
	}
        /**
         * Returns the destination of the SilkAir Flight
         * @return String
         */
	public String getDestination() {
		return destination;
	}
	/**
         * Returns the exception dates of the SilkAir Flight
         * @return Date[]
         */
	public Date[] getExcDates() {
		return excDates;
	}
        /**
         * Sets the exception dates for the SilkAir Flight, if any
         * @param excDates Exception Dates
         */
	public void setExcDates(Date[] excDates) {
		
		this.excDates = excDates;
		/*if(this.getExcDates() == null)
			System.out.println("Setting null Dates");
		else
			System.out.println("Setting Dates");*/
	}


}
