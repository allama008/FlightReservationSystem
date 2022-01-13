package FRSManager;
/**
 * Interface that will be implemented by the classes CLIManager and DisplayManager
 * @author Allama
 */
public interface DisplayInterface {
        /**
         * Implements the Input Screen functionalities
         */
	public void ShowScreen1();
        /**
         * Implements the Search Screen Functionalities
         */
	public void ShowScreen2();
        /**
         * Implements the Booking Screen Functionalities
         */
	public void ShowScreen3();
        /**
         * Implements the Booking Confirmation Screen Functionalities
         */
	public void ShowScreen4();
}
