package FRSManager;
import static java.lang.System.in;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Implements Display Interface. It is used to display results in the Command Line Interface
 * @author Rashika and Oindrila
 */
public class CLIManager implements DisplayInterface
{
    FRSManager mgr;
    String fromCity;
    int noOfPassengers;
    Date depDate;
    int selectedIndex;
    /**
     * Initialize the FRSManager object
     * @param mgr FRS Manager Object
     */
    public CLIManager(FRSManager mgr)
    {
        this.mgr = mgr; 
    }
    /**
     * @param t
     * Utility function to convert minutes into hours and minutes
     * @return 
     */
    public String time(long t)
    {
        long hours = t / 60;
        long minutes = t % 60;
        return String.format("%d hours %02d minutes", hours, minutes);
    }
     /**
     * Takes the input from the user (Date of travel, Number of passengers and Origin City)
     */
    @Override
    public void ShowScreen1()
    {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("*****************************************************************");
        System.out.println("Welcome to Travel World");
        System.out.println("Book your travel Itinerary");
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the departure city");
        System.out.println("1.Delhi(DEL), Indira Gandhi International Airport");
        System.out.println("2.Mumbai(BOM), Chhatrapati Shivaji International Airport");
        System.out.println("3.Pune(PNQ), Pune Airport");
        System.out.println("Select the option (1, 2 or 3)");
        int option=sc.nextInt();
        String s[]=new String[3];
        s[0]="Delhi(DEL)";
        s[1]="Mumbai(BOM)";
        s[2]="Pune(PNQ)";
        if(option<0 || option>3)
        {
        while(option<0 || option>3)
        {
            System.out.println("Invalid Input");
            System.out.println("Enter the departure city");
            System.out.println("1.Delhi(DEL), Indira Gandhi International Airport");
            System.out.println("2.Mumbai(BOM), Chhatrapati Shivaji International Airport");
            System.out.println("3.Pune(PNQ), Pune Airport");
            System.out.println("Please select any one option (1, 2 or 3)");
            option= sc.nextInt();
        }
        }
        else 
        {
            mgr.cliMgr.fromCity = s[option-1];
            System.out.println("Enter the departure date (From 1st October,2016 to 13th November 2016");
            System.out.println("Enter date in dd/MM/yy Format");
            String departure= sc.next();
            int flag;
            do
            {
                flag=0;
                StringTokenizer checkDate= new StringTokenizer(departure.concat("/"),"/");
                try{
                    int d=Integer.parseInt(checkDate.nextToken());
                    int m=Integer.parseInt(checkDate.nextToken());
                    int y=Integer.parseInt(checkDate.nextToken());
                    if((m==10 && d>=1 && d<=31 && y==16) || (m==11 && d>=1 && d<=13 && y==16))
                    {
                        flag=1;
                    }
                    else 
                    {
                        System.out.println("Oops! Date is out of the given range");
                        System.out.println("Enter the departure date (From 1st October,2016 to 13th November 2016");
                        System.out.println("Enter date in dd/MM/yy Format");
                        departure=sc.next();
                    }
                    }
                catch(NumberFormatException ex)
                {
                        System.out.println("Oops! The date is not in correct format");
                        System.out.println("Enter the departure date (From 1st October,2016 to 13th November 2016");
                        System.out.println("Enter date in dd/MM/yy Format");
                        departure=sc.next();
                }
            }
            while(flag==0);
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            try{
                mgr.cliMgr.depDate = df.parse(departure);
            }
            catch(ParseException ex1){
                    System.out.println(ex1.getMessage());
            }
            System.out.println("Enter the number of passengers (<15)");
            mgr.cliMgr.noOfPassengers=sc.nextInt();
            while(mgr.cliMgr.noOfPassengers>15 || mgr.cliMgr.noOfPassengers<=0)
            {
                System.out.println("Please enter a valid input\n (From 1 to 15");
                mgr.cliMgr.noOfPassengers=sc.nextInt();
            }
            //System.out.println(mgr.cliMgr.fromCity );
            //System.out.println(mgr.cliMgr.depDate +"");
            //System.out.println(mgr.cliMgr.noOfPassengers+"");
            mgr.myCombo = mgr.searchMgr.search(mgr.cliMgr.fromCity, mgr.cliMgr.depDate, mgr.cliMgr.noOfPassengers, mgr.spiceSJ, mgr.silkSA);
            ShowScreen2();
        }
    }
    /**
     * Displays all the possible combination of flights according to the particulars entered
     */
    @Override

    public void ShowScreen2() {
        Scanner sc = new Scanner(System.in);
        for(int i = 1; i <= mgr.searchMgr.getCountCombo(); i++){
            
            System.out.println("Option " + i);
            System.out.println(mgr.myCombo[i-1].getFlightSJ().getFlightId() + " : From " + mgr.myCombo[i-1].getFlightSJ().getOrigin()
                                    + " To " + mgr.myCombo[i-1].getFlightSJ().getDestination());
            System.out.println("Departure : " + mgr.myCombo[i-1].getFlightSJ().getDeparture() + " Arrival : " + mgr.myCombo[i-1].getFlightSJ().getArrival());
            System.out.println("Duration : " + time(mgr.myCombo[i-1].getTravelSJ()));
            System.out.println("Transit : " + time(mgr.myCombo[i-1].getTransitTime()));
            System.out.println(mgr.myCombo[i-1].getFlightSA().getFlightId() + " : From " + mgr.myCombo[i-1].getFlightSA().getOrigin()
                                    + " To " + mgr.myCombo[i-1].getFlightSA().getDestination());
            System.out.println("Departure : " + mgr.myCombo[i-1].getFlightSA().getDeparture() + " Arrival : " + mgr.myCombo[i-1].getFlightSA().getArrival());
            System.out.println("Duration : " + time(mgr.myCombo[i-1].getTravelSA()));
            System.out.println("Total time : " + time(mgr.myCombo[i-1].getTravelTime()));
            System.out.println();
            
        }
        System.out.println("Please enter the flight combination you want to select ");
        int ch = -1;
        do{
            ch = sc.nextInt();
            if(ch<=0 || ch>mgr.searchMgr.getCountCombo())
                System.out.println("Please enter a valid choice");
        }while(ch<=0 || ch>mgr.searchMgr.getCountCombo());
        mgr.cliMgr.selectedIndex=ch;
        mgr.cliMgr.ShowScreen3();
    }
    /**
     * Takes the Passenger name and calls the functions for writing record and updating the number of seats
     */
    @Override
    public void ShowScreen3() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Please enter the Passenger Name");
        mgr.passengerName=sc.next();
        mgr.selectedCombo=mgr.myCombo[mgr.cliMgr.selectedIndex-1];
        mgr.bookingMgr.book(mgr.passengerName, mgr.selectedCombo, mgr.cliMgr.noOfPassengers);
        mgr.dataMgr.write(mgr.bookingMgr.book);
        mgr.dataMgr.update(mgr.bookingMgr.book);
        mgr.cliMgr.ShowScreen4();
    }
    /**
     * Displays the final booking that confirmed with all the details including the travel and transit time
     */
    @Override
    public void ShowScreen4() {
         System.out.println("Congratulations! Your flight is successfully booked!");
         System.out.println("-----------------------------------------------------");
         System.out.println("\nBooking Id: "+mgr.bookingMgr.book.getId());
         System.out.println("Passenger Name: "+mgr.passengerName);
         System.out.println("No. of Seats Booked: "+mgr.bookingMgr.book.getPassengers());
         DateFormat df=new SimpleDateFormat("dd-MMM-yy");
         String departure= df.format(mgr.cliMgr.depDate);
         String depSpice= df.format(mgr.selectedCombo.getFlightSJ().getDeparture());
         String depSilk= df.format(mgr.selectedCombo.getFlightSA().getDeparture());
         System.out.println("Departure Date : "+departure);
         System.out.println("\n"+mgr.selectedCombo.getFlightSJ().getOrigin()+"-------->"+mgr.selectedCombo.getFlightSJ().getDestination());
         System.out.println("Departure: "+depSpice);
         System.out.println("Arrival: "+mgr.selectedCombo.getFlightSJ().getArrival());
         System.out.println("\n"+mgr.selectedCombo.getFlightSA().getOrigin()+"-------->"+mgr.selectedCombo.getFlightSA().getDestination());
         System.out.println("Departure: "+ depSilk);
         System.out.println("Arrival: "+mgr.selectedCombo.getFlightSA().getArrival());
         System.out.println("\nTotal Travel Time :"+time(mgr.selectedCombo.getTravelTime()));
         System.out.println("Total Transit Time: "+time(mgr.selectedCombo.getTransitTime()));
         System.out.println("\nHave a safe journey \nTHANK YOU!!!!");
    }
}
