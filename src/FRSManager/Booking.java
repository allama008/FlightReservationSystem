package FRSManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**The class Booking does the job of generating the booking id of the passenger.
 *
 * @author Rashika
 */
public class Booking {
    private String bookingId;
    private String name;
    private FlightCombo combo;
    private int noOfPassengers;

    /**Constructor that initializes the argument i.e., name of the passenger, flight combo selected by the passenger and the number of passenger received from the BookingMgr. 
     *
     * @param name Name of the passenger.
     * @param fc Flight Combo object selected by the passenger. 
     * @param noOfPass Number of passenger selected.
     */
    public Booking(String name, FlightCombo fc, int noOfPass )
    {
        this.name=name;
        this.combo=fc;
        this.noOfPassengers=noOfPass;
        this.bookingId= setId();
    }

    /**Sets the booking Id of the passenger.
     *
     * @return Booking Id of the passenger.
     */
    public String setId()
    {
        String filename= new String("GetID.txt");
        File idFile=new File(filename);
        if(!(idFile.exists()))
        {
            try
            {
            FileWriter fw = new FileWriter("GetID.txt",true);
    	    BufferedWriter bw = new BufferedWriter(fw);
            bw.write("1000");
            bw.close();
            }
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        try
        {
            FileReader fr=new FileReader("GetID.txt");
            BufferedReader br=new BufferedReader(fr);
            String n=br.readLine();
            int id= Integer.parseInt(n);
            id=id+1;
            bookingId=id+"";
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException ex)
        {    
            System.out.println(ex.getMessage());
        }
        try
        {
                FileWriter fw = new FileWriter("GetID.txt",false);
    	        BufferedWriter bw = new BufferedWriter(fw);
                bw.write(bookingId);
                bw.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException ex)
        {    
            System.out.println(ex.getMessage());
        }
        return bookingId;   
    }

    /**
     *
     * @return Booking Id of the passenger.
     */
    public String getId()
    {
        return bookingId;
    }

    /**
     *
     * @return Name of the passenger.
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return Number of passengers.
     */
    public Integer getPassengers()
    {
        return noOfPassengers;
    }

    /**
     *
     * @return Flight Combo selected by the passenger.
     */
    public FlightCombo getCombo()
    {
        return combo;
    }
}