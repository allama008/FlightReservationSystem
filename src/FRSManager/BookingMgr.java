
package FRSManager;
/**
 * Class that manages the Booking operations
 * @author DELL
 */
public class BookingMgr {
    Booking book;
    /**
     * Initializes the Passenger name, Selected Combo and No of passengers
     * @param name Passenger name
     * @param fc Selected Combo
     * @param noOfPass No. of Passengers
     */
    public void book(String name, FlightCombo fc, int noOfPass ){
        book = new Booking(name, fc, noOfPass );
    }
}
