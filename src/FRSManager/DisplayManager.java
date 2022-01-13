package FRSManager;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;

/**
 * Handles all the transitions and GUI operations of different
 * screens
 * @author Allama
 */
public class DisplayManager implements DisplayInterface
{
    
    FRSManager mgr;
    Screen1 s1;
    Screen2 s2;
    Screen3 s3;
    Screen4 s4;
    About ab;
    String connCity, departureCity, fromCity;
    String flight1, flight2;
    Date depDate;
    int passengerNum, ch;
    int index;
    String namePass, transTime, travelTime;
    String depTime1, depTime2, arrTime1, arrTime2, dur1, dur2;
    /**
     * Creation of the Screens, FRSManager mgr is used as parameter to
     * maintain a single object
     * @param mgr 
     */
    public DisplayManager(FRSManager mgr)
    {
        this.mgr = mgr;
        s1 = new Screen1(mgr);
        s2 = new Screen2(mgr);
        s3 = new Screen3(mgr);
        s4 = new Screen4(mgr);
        ab = new About(mgr);
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
     * Utility function to change the date as per the time of the flight
     * @param strDate 
     */
    public void setDate(String strDate)
    {
        StringTokenizer at1 = new StringTokenizer(arrTime1);
        String arrT1 = (at1.nextToken(":"));
        StringTokenizer dt2 = new StringTokenizer(depTime2);
        String depT2 = dt2.nextToken(":");
        StringTokenizer dd1 = new StringTokenizer(strDate);
        String depD1 = dd1.nextToken();
        int date = Integer.parseInt(depD1);
        if(date!=31)
        {
            date++;
        }
        else
            date = 1;
        String month = dd1.nextToken();
        String year = dd1.nextToken();
        String finalDate = date + " " + month + " " + year;
        if("21".equals(arrT1) && "23".equals(depT2))
        {
            s4.jLabel25.setText(strDate);
            s4.jLabel26.setText(finalDate);
        }
        else if("21".equals(arrT1) && "00".equals(depT2))
        {
            s4.jLabel25.setText(finalDate);
            s4.jLabel26.setText(finalDate);
        }
        else if("23".equals(depT2))
        {
            s4.jLabel25.setText(strDate);
            s4.jLabel26.setText(finalDate);
        }
        else
        {
            s4.jLabel25.setText(strDate);
            s4.jLabel26.setText(strDate);
        }
    }
    /**
     * Sets the Screen1 fields and get Screen 1 ready for display
     */
    public void ShowScreen1()
    {
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
        ab.setVisible(false);
        Font font = new Font("Tahoma", Font.ITALIC,11);
        s1.jTextField1.setFont(font);
        s1.jTextField1.setText("Departure airport");
        s1.jComboBox1.setSelectedIndex(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy"); 
        Date min_date = new Date(0); 
        try { 
         min_date = formatter.parse(s1.ORIGIN_DATE); 
        } catch (ParseException e) { 
         // TODO Auto-generated catch block 
         e.printStackTrace(); 
        } 
        s1.jDateChooser1.setDate(min_date);
        s1.setVisible(true);
    }
    /**
     * Used to display the designation of each member
     */
    public void ShowAbout()
    {
        ab.setVisible(true);
    }
    /**
     * Sets the Screen2 fields and gets Screen2 ready for display
     * Changes the screen size according to the number of possible
     * flight combinations
     */
    public void ShowScreen2()
    {
        s1.setVisible(false);
        s2.buttonGroup1.clearSelection();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = dateFormat.format(mgr.displayMgr.depDate);
        s2.jLabel8.setText(strDate);
        s2.jLabel6.setText(mgr.displayMgr.fromCity);
        s2.j1.setText(mgr.displayMgr.fromCity);
        s2.jLabel9.setText(Integer.toString(mgr.displayMgr.passengerNum));
        s2.jLabel109.setText("( " + ch + " options )");
        switch(0)
        {
            case 0: s2.jScrollPane1.setVisible(false);
                    s2.jPanel3.setVisible(false);
                    s2.jPanel10.setVisible(false);
                    s2.jPanel2.setVisible(false);
                    s2.jLabel124.setText("Sorry, no flights are available on "+ strDate + " for Singapore from " + mgr.displayMgr.fromCity + ". Please search again with another traveling date");
                    s2.jLabel124.setVisible(true);
                    if(ch==0) break;
                    
            case 1: s2.jScrollPane1.setVisible(true);
                    s2.jPanel3.setVisible(true);
                    s2.jPanel10.setVisible(true);
                    s2.jPanel2.setVisible(true);
                    s2.jLabel124.setVisible(false);
                    
                    s2.jPanel3.setPreferredSize(new Dimension(1250, 181));
                    s2.jScrollPane1.setPreferredSize(new Dimension(1250, 181));
                    s2.jScrollPane1.setSize(new Dimension(1250, 181));
                    s2.j1.setText(mgr.displayMgr.departureCity);

                    s2.j2.setText(mgr.myCombo[0].getFlightSJ().getDestination()); s2.jLabel22.setText(mgr.myCombo[0].getFlightSJ().getDeparture());                   
                    s2.jLabel23.setText(mgr.myCombo[0].getFlightSJ().getArrival()); s2.jLabel24.setText(time(mgr.myCombo[0].getTravelSJ()));
                    s2.jLabel25.setText(mgr.myCombo[0].getFlightSJ().getFlightId()); s2.jLabel27.setText(s2.j2.getText());
                    s2.jLabel30.setText(mgr.myCombo[0].getFlightSA().getDeparture()); s2.jLabel31.setText(mgr.myCombo[0].getFlightSA().getArrival());
                    s2.jLabel32.setText(time(mgr.myCombo[0].getTravelSA())); s2.jLabel33.setText(mgr.myCombo[0].getFlightSA().getFlightId());
                    s2.jLabel26.setText("Connection in " + s2.j2.getText() + ": " + time(mgr.myCombo[0].getTransitTime()));
                    if (ch==1) break;
            case 2: s2.jPanel3.setPreferredSize(new Dimension(1250, 381));
                    s2.jScrollPane1.setPreferredSize(new Dimension(1250, 360));
                    s2.jScrollPane1.setSize(new Dimension(1250, 360));
                    s2.jLabel35.setText(mgr.displayMgr.departureCity);
                    
                    s2.jLabel37.setText(mgr.myCombo[1].getFlightSJ().getDestination()); s2.jLabel34.setText(mgr.myCombo[1].getFlightSJ().getDeparture());                   
                    s2.jLabel38.setText(mgr.myCombo[1].getFlightSJ().getArrival()); s2.jLabel39.setText(time(mgr.myCombo[1].getTravelSJ()));
                    s2.jLabel40.setText(mgr.myCombo[1].getFlightSJ().getFlightId()); s2.jLabel42.setText(s2.jLabel37.getText());
                    s2.jLabel45.setText(mgr.myCombo[1].getFlightSA().getDeparture()); s2.jLabel46.setText(mgr.myCombo[1].getFlightSA().getArrival());
                    s2.jLabel47.setText(time(mgr.myCombo[1].getTravelSA())); s2.jLabel48.setText(mgr.myCombo[1].getFlightSA().getFlightId());
                    s2.jLabel41.setText("Connection in " + s2.jLabel37.getText() + ": " + time(mgr.myCombo[1].getTransitTime()));
                    if(ch==2) break;
            case 3: s2.jPanel3.setPreferredSize(new Dimension(1250, 581));
                    s2.jLabel53.setText(mgr.displayMgr.departureCity);
                    
                    s2.jLabel55.setText(mgr.myCombo[2].getFlightSJ().getDestination()); s2.jLabel56.setText(mgr.myCombo[2].getFlightSJ().getDeparture());                   
                    s2.jLabel57.setText(mgr.myCombo[2].getFlightSJ().getArrival()); s2.jLabel58.setText(time(mgr.myCombo[2].getTravelSJ()));
                    s2.jLabel59.setText(mgr.myCombo[2].getFlightSJ().getFlightId()); s2.jLabel60.setText(s2.jLabel55.getText());
                    s2.jLabel63.setText(mgr.myCombo[2].getFlightSA().getDeparture()); s2.jLabel64.setText(mgr.myCombo[2].getFlightSA().getArrival());
                    s2.jLabel65.setText(time(mgr.myCombo[2].getTravelSA())); s2.jLabel66.setText(mgr.myCombo[2].getFlightSA().getFlightId());
                    s2.jLabel49.setText("Connection in " + s2.jLabel55.getText() + ": " + time(mgr.myCombo[2].getTransitTime()));
                    if(ch==3) break;
            case 4: s2.jPanel3.setPreferredSize(new Dimension(1250, 781));
                    s2.jLabel67.setText(mgr.displayMgr.departureCity);
                    
                    s2.jLabel69.setText(mgr.myCombo[3].getFlightSJ().getDestination()); s2.jLabel70.setText(mgr.myCombo[3].getFlightSJ().getDeparture());                   
                    s2.jLabel71.setText(mgr.myCombo[3].getFlightSJ().getArrival()); s2.jLabel72.setText(time(mgr.myCombo[3].getTravelSJ()));
                    s2.jLabel73.setText(mgr.myCombo[3].getFlightSJ().getFlightId()); s2.jLabel74.setText(s2.jLabel69.getText());
                    s2.jLabel77.setText(mgr.myCombo[3].getFlightSA().getDeparture()); s2.jLabel78.setText(mgr.myCombo[3].getFlightSA().getArrival());
                    s2.jLabel79.setText(time(mgr.myCombo[3].getTravelSA())); s2.jLabel80.setText(mgr.myCombo[3].getFlightSA().getFlightId());         
                    s2.jLabel50.setText("Connection in " + s2.jLabel69.getText() + ": " + time(mgr.myCombo[3].getTransitTime()));
                    if(ch==4) break; 
            case 5: s2.jPanel3.setPreferredSize(new Dimension(1250, 981));
                    s2.jLabel81.setText(mgr.displayMgr.departureCity);
                    
                    s2.jLabel83.setText(mgr.myCombo[4].getFlightSJ().getDestination()); s2.jLabel84.setText(mgr.myCombo[4].getFlightSJ().getDeparture());                   
                    s2.jLabel85.setText(mgr.myCombo[4].getFlightSJ().getArrival()); s2.jLabel86.setText(time(mgr.myCombo[4].getTravelSJ()));
                    s2.jLabel87.setText(mgr.myCombo[4].getFlightSJ().getFlightId()); s2.jLabel88.setText(s2.jLabel83.getText());
                    s2.jLabel91.setText(mgr.myCombo[4].getFlightSA().getDeparture()); s2.jLabel92.setText(mgr.myCombo[4].getFlightSA().getArrival());
                    s2.jLabel93.setText(time(mgr.myCombo[4].getTravelSA())); s2.jLabel94.setText(mgr.myCombo[4].getFlightSA().getFlightId());                    
                    s2.jLabel51.setText("Connection in " + s2.jLabel83.getText() + ": " + time(mgr.myCombo[4].getTransitTime()));
                    if(ch==5) break; 
            case 6: s2.jPanel3.setPreferredSize(new Dimension(1250, 1181));
                    s2.jLabel95.setText(mgr.displayMgr.departureCity);
                    
                    s2.jLabel97.setText(mgr.myCombo[5].getFlightSJ().getDestination()); s2.jLabel98.setText(mgr.myCombo[5].getFlightSJ().getDeparture());                   
                    s2.jLabel99.setText(mgr.myCombo[5].getFlightSJ().getArrival()); s2.jLabel100.setText(time(mgr.myCombo[5].getTravelSJ()));
                    s2.jLabel101.setText(mgr.myCombo[5].getFlightSJ().getFlightId()); s2.jLabel102.setText(s2.jLabel97.getText());
                    s2.jLabel105.setText(mgr.myCombo[5].getFlightSA().getDeparture()); s2.jLabel106.setText(mgr.myCombo[5].getFlightSA().getArrival());
                    s2.jLabel107.setText(time(mgr.myCombo[5].getTravelSA())); s2.jLabel108.setText(mgr.myCombo[5].getFlightSA().getFlightId());                    
                    s2.jLabel52.setText("Connection in " + s2.jLabel97.getText() + ": " + time(mgr.myCombo[5].getTransitTime()));
                    if(ch==6) break; 
        }
        s2.setVisible(true);
        
    }
    /**
     * Sets the Screen3 fields and gets Screen3 ready for display
     */
    public void ShowScreen3()
    {
        s2.setVisible(true);
        s3.jLabel4.setText(Integer.toString(mgr.displayMgr.passengerNum));
        Font font1 = new Font("Times New Roman", Font.ITALIC, 18);
        s3.jTextField1.setFont(font1);
        s3.jTextField1.setText("Passenger's Name");
        s3.setVisible(true);
    }
    /**
     * Sets the Screen4 fields and gets Screen4 ready for display
     */
    public void ShowScreen4()
    {
        String firstLetter;
        s2.setVisible(false);
        s3.setVisible(false);
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = dateFormat.format(mgr.displayMgr.depDate);
        s4.jLabel10.setText(strDate);
        s4.jLabel18.setText(strDate);
        s4.jLabel19.setText(strDate);
        setDate(strDate);
        s4.jLabel9.setText(namePass);
        s4.jLabel11.setText(Integer.toString(passengerNum));
        s4.jLabel14.setText(fromCity);
        s4.jLabel16.setText(depTime1);
        s4.jLabel23.setText(depTime2);
        s4.jLabel17.setText(arrTime1);
        s4.jLabel24.setText(arrTime2);
        StringTokenizer st = new StringTokenizer(connCity);
        s4.jLabel15.setText(st.nextToken("("));
        s4.jLabel21.setText(s4.jLabel15.getText());
        firstLetter = flight2.substring(0, 1);
        if("M".equals(firstLetter))
        {
            ImageIcon II = new ImageIcon(getClass().getResource("/images/25.png"));
            s4.jLabel35.setIcon(II);
        }
        else if("S".equals(firstLetter))
        {
            ImageIcon II1 = new ImageIcon(getClass().getResource("/images/27.png"));
            s4.jLabel35.setIcon(II1);
        }
        s4.jLabel20.setText(flight1);
        s4.jLabel27.setText(flight2);
        s4.jLabel13.setText(time(mgr.selectedCombo.getTransitTime()));
        s4.jLabel12.setText(time(mgr.selectedCombo.getTravelTime()));
        s4.jLabel8.setText(mgr.bookingMgr.book.getId());
        s4.setVisible(true);
    }
    /**
     * Used to terminate the system
     */
    public void exitFRS()
    {
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
        ab.setVisible(false);
        s1.setVisible(false);
        System.exit(0);
    }
}

