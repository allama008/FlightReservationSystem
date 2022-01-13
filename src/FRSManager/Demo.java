/*import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
	public static void main(String[] args) throws Exception{
		DataMgr dm=new DataMgr();
        SpiceJet []spiceSJ = dm.readSpiceJet();
        SilkAir []silkSA = dm.readSilkAir();
        System.out.println("Done!");
        System.out.println("Start");
        String fromCity = "Delhi(DEL)";
        //String fromCity = "Mumbai(BOM)";
        //String fromCity = "Pune(PNQ)";
        DateFormat df = new SimpleDateFormat("dd MMM yy");
        Date depDate = df.parse("5 OCT 16");
        int passengerNum = 5;
        SearchMgr sm = new SearchMgr();
        FlightCombo []myCombo = sm.search(fromCity, depDate, passengerNum, spiceSJ, silkSA);
        for(int i = 0; i<sm.getCountCombo(); i++){
        	System.out.println(myCombo[i].getFlightSJ().getFlightId() + "\t\t" + myCombo[i].getFlightSA().getFlightId());
        	System.out.println(myCombo[i].getFlightSJ().getOrigin()+ " " + myCombo[i].getFlightSJ().getDestination() + "\t\t" +
        						myCombo[i].getFlightSA().getOrigin() + " " +  myCombo[i].getFlightSA().getDestination());
        	System.out.println(myCombo[i].getFlightSJ().getDeparture()+ " " + myCombo[i].getFlightSJ().getArrival() + "\t\t\t\t" +
								myCombo[i].getFlightSA().getDeparture() + " " +  myCombo[i].getFlightSA().getArrival());
        	System.out.println(myCombo[i].getDateSA() + "\t\t" + myCombo[i].getDateSA());
        	System.out.println("Travel : " + myCombo[i].getTravelTime() + "\tTransit : " + myCombo[i].getTransitTime());
        	System.out.println();
        }
        
        //dm.update();
        //System.out.println("Updated");
        
        
        /*int maxcount = 0;
        for(int j = 1; j <= 31; j++){
        	fromCity = "Mumbai(BOM)";
        	depDate = df.parse(j + " OCT 16");
        	System.out.println("*****************" + fromCity + j + " OCT 16" + "************************");
        	myCombo = sm.search(fromCity, depDate, passengerNum, spiceSJ, silkSA);
        	for(int i = 0; i<sm.getCountCombo(); i++){
            	System.out.println(myCombo[i].getFlightSJ().getFlightId() + "\t\t" + myCombo[i].getFlightSA().getFlightId());
            	System.out.println(myCombo[i].getFlightSJ().getOrigin()+ " " + myCombo[i].getFlightSJ().getDestination() + "\t\t" +
            						myCombo[i].getFlightSA().getOrigin() + " " +  myCombo[i].getFlightSA().getDestination());
            	System.out.println(myCombo[i].getFlightSJ().getDeparture()+ " " + myCombo[i].getFlightSJ().getArrival() + "\t\t\t\t" +
    								myCombo[i].getFlightSA().getDeparture() + " " +  myCombo[i].getFlightSA().getArrival());
            	System.out.println(myCombo[i].getDateSA() + "\t\t" + myCombo[i].getDateSA());
            	System.out.println("Travel : " + myCombo[i].getTravelTime() + "\tTransit : " + myCombo[i].getTransitTime());
            	System.out.println();
            }
        	if(sm.getCountCombo() >= maxcount)
        		maxcount = sm.getCountCombo();
        }
        
        for(int j = 1; j <= 13; j++){
        	fromCity = "Mumbai(BOM)";
        	depDate = df.parse(j + " NOV 16");
        	System.out.println("*****************" + fromCity + j + " NOV 16" + "************************");
        	myCombo = sm.search(fromCity, depDate, passengerNum, spiceSJ, silkSA);
        	for(int i = 0; i<sm.getCountCombo(); i++){
            	System.out.println(myCombo[i].getFlightSJ().getFlightId() + "\t\t" + myCombo[i].getFlightSA().getFlightId());
            	System.out.println(myCombo[i].getFlightSJ().getOrigin()+ " " + myCombo[i].getFlightSJ().getDestination() + "\t\t" +
            						myCombo[i].getFlightSA().getOrigin() + " " +  myCombo[i].getFlightSA().getDestination());
            	System.out.println(myCombo[i].getFlightSJ().getDeparture()+ " " + myCombo[i].getFlightSJ().getArrival() + "\t\t\t\t" +
    								myCombo[i].getFlightSA().getDeparture() + " " +  myCombo[i].getFlightSA().getArrival());
            	System.out.println(myCombo[i].getDateSA() + "\t\t" + myCombo[i].getDateSA());
            	System.out.println("Travel : " + myCombo[i].getTravelTime() + "\tTransit : " + myCombo[i].getTransitTime());
            	System.out.println();
            }
        	if(sm.getCountCombo() >= maxcount)
        		maxcount = sm.getCountCombo();
        }*/
        
        //System.out.println("Found");

  //  }
//}
