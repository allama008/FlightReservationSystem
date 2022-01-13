package FRSManager;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Performs all the functions related to reading records from file( SpiceJet and SilkAir)
 * Updates the number of seats and writes the booking record into a .txt file
 * @author Rashika
 */
public class DataMgr {
	
	//SpiceJet []spiceSJ;
	//SilkAir []silkSA;
	/**
         * Reads records from the 2016.spicejet.csv file and store it in the spiceSJ array
         * @param name Filename of SpiceJet
         * @return SpiceJet[]
         */
	public SpiceJet[] readSpiceJet(String name){
        SpiceJet []spiceSJ = new SpiceJet[57];
        try{
			String n = "";
			int i;
			int count;
			int l = 0;
			String []s = new String[9];
			FileReader fr = new FileReader(name);
			BufferedReader br = new BufferedReader(fr);
			for(i = 0; i < 6; i++)
				n = br.readLine();
			while(n != null)
			{
				//System.out.println(n);
				StringTokenizer st = new StringTokenizer(n,"|");
				count = st.countTokens();
				for(int j = 0; j < count; j++)
				{
					s[j] = st.nextToken();
					s[j] = s[j].trim();
				}
				//System.out.println(s[0] + "..." + s[1]);
				
				switch(s[0])
				{
					case "DELHI": s[0] = "Delhi(DEL)";
						break;
					case "MUMBAI": s[0] = "Mumbai(BOM)";
						break;
					case "PUNE": s[0] = "Pune(PNQ)";
						break;
				}
				
				switch(s[1])
				{
					case "BENGALURU": s[1] = "Bengaluru(BLR)";
						break;
					case "CHENNAI": s[1] = "Chennai(MAA)";
						break;
					case "HYDERABAD": s[1] = "Hyderabad(HYD)";
						break;
					case "KOLKATA": s[1] = "Kolkata(CCU)";
						break;
				}
				//System.out.println(s[0] + "..." + s[1]);
				
				//System.out.println(s[2] + "..." + s[2].length());
				if(s[2].equals("DAILY"))
				{
					s[2]="Monday, Tuesday, Wednesday, Thursday, Friday, Saturaday, Sunday";
				}
				else
				{
					StringTokenizer d = new StringTokenizer(s[2],",");
					count = d.countTokens();
					for(i = 0; i < count; i++)
					{
						String days = d.nextToken().trim();
						//System.out.println("days =" + days);
						switch(days)
						{
							case "MONDAY": days="Monday";
								break;
							case "TUESDAY": days="Tuesday";
							//System.out.println("IN THE SWITCH CASE");
								break;
							case "WEDNESDAY": days="Wednesday";
								break;
							case "THURSDAY": days="Thursday";
								break;
							case "FRIDAY": days="Friday";
								break;
							case "SATURDAY":days="Saturday";
								break;
							case "SUNDAY": days="Sunday";
								break;
					}

					if(i != 0){
						s[2] = s[2] + ", " + days;
						//s[2] = s[2].concat(", ");
						//s[2] = s[2].concat(days);
					}
					else 
						s[2] = days;
					}
				}
				//System.out.println(s[2] + "..." + s[2].length());
				
				//System.out.println(s[3]);
				st = new StringTokenizer(s[3]);
				s[3] = st.nextToken() + " " + st.nextToken();
				//System.out.println(s[3]);
				
				//System.out.println(s[4] + "..." + s[5]);
				SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat saveFormat = new SimpleDateFormat("HH:mm");
				try{
					Date parsed = parseFormat.parse(s[4]);
					s[4] = saveFormat.format(parsed);
					parsed = parseFormat.parse(s[5]);
					s[5] = saveFormat.format(parsed);
					//System.out.println(s[4] + "..." + s[5]);
				}
				catch(ParseException e){
					System.out.println(e.getMessage());
				}
				
				if(s[6].equals("-"))
				{
					s[6] = "N/A";
					//System.out.println(s[6]);
				}
				
				DateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
				Date d1 = new Date();
				Date d2 = new Date();
				try{
					d1 = dateFormat.parse(s[7]);
					d2 = dateFormat.parse(s[8]);
					//System.out.println(d1);
					//System.out.println(d2);
				}
				catch(ParseException ex1){
					System.out.println(ex1.getMessage());
				}
				
				
				spiceSJ[l] = new SpiceJet(s[3],s[0],s[4],s[5],s[2],d1,d2,s[6],s[1]);
				//System.out.println("\t\t SPICE JET");
				//spiceSJ[l].show();
				//System.out.println("-------------------------------------------");
				l++;
				n = br.readLine();
			}
			//this.spiceSJ = spiceSJ;
			
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return spiceSJ;
    }
    /**
     * Reads records from 2016.silkair.csv and stores them in silkSA array
     * @param name Filename of SilkAir
     * @return SilkAir[]
     */
    public SilkAir[] readSilkAir(String name)
    {
    	SilkAir []silkSA = new SilkAir[16];
    	Date []excDates = new Date[3];
    	boolean excCase;
        try{
	        String []s = new String[10];
	        FileReader fr = new FileReader(name);
	        BufferedReader br = new BufferedReader(fr);
	        String n = br.readLine();
	        for(int i = 0; i <= 2; i++)
	        	n = br.readLine();
	        int i = 0;
	        int j = 0;
	        int l = 0;
	        while(n != null)
	        {
	        	excCase = false;
	            n=n.concat(" |");
	            //System.out.println(n);
	            StringTokenizer st=new StringTokenizer(n,"|");
	            j=0;
	            int count=st.countTokens();
	            //System.out.println(count);
	            //System.out.println("Line--------------" + count);
	            for(j = 0; j < count; j++)
	            {
	                s[j]=st.nextToken();
	                s[j] = s[j].trim();
	                //if(s[j].equals(""))
	                	//System.out.println("its empty!!");
	                //System.out.println(j);
	                //System.out.println(s[j]);
	            }
	            
	            //System.out.println(s[0]);
	            st = new StringTokenizer(s[0]);
	            s[0] = st.nextToken() + st.nextToken();
	            //System.out.println(s[0]);
	            
	            StringTokenizer d = new StringTokenizer(s[1],",");
	            count = d.countTokens();
	            for(i = 0; i < count; i++)
	            {
		            String days = d.nextToken();
		            switch(days)
		            {
		                case "Mon": days="Monday";
		                break;
		                case "Tue": days="Tuesday";
		                //System.out.println("IN THE SWITCH CASE");
		                break;
		                case "Wed": days="Wednesday";
		                break;
		                case "Thu": days="Thursday";
		                break;
		                case "Fri": days="Friday";
		                break;
		                case "Sat":days="Saturday";
		                break;
		                case "Sun": days="Sunday";
		                break;
	            }
	
	            if(i != 0){
	            	s[1] = s[1] + ", " + days;
	                //s[1] = s[1].concat(", ");
	                //s[1] = s[1].concat(days);
	            }
	            else 
	                s[1] = days;
	            }
	            
	            String from = "1 OCT 16";
	            String to = "13 NOV 16";
	            String exc;
	            //System.out.println(s[4]);
	            if(s[4].contains("Disc."))
	            {
	            	if(s[4].contains("Exc.")){
	            		//System.out.println("!!Exception case!!");
	            		to = s[4].substring(9, 11) + " " + s[4].substring(6, 9) + " 16";
	            		
	            		exc = s[4].substring(18);
	            		st =new StringTokenizer(exc, ", ");
	            		String d0 = st.nextToken().trim();
	            		String d1 = st.nextToken().trim();
	            		String d2 = st.nextToken().trim();
	            		d0 = d0.substring(3) + " " + d0.substring(0, 3) + " 16";
	            		d1 = d1.substring(3) + " " + d1.substring(0, 3) + " 16";
	            		d2 = d2.substring(3) + " " + d2.substring(0, 3) + " 16";
	            		DateFormat df = new SimpleDateFormat("dd MMM yy");
	            		try{
	            			excDates[0] = df.parse(d0);
	                		excDates[1] = df.parse(d1);
	                		excDates[2] = df.parse(d2);
	            		}
	            		catch(ParseException e){
	            			System.out.println(e.getMessage());
	            		}
	            		excCase = true;
	            	}
	            	else{
	            		to = s[4].substring(9) + " " + s[4].substring(6, 9) + " 16";
	            	}
	            	
	                /*String t1;
	                StringTokenizer t = new StringTokenizer(s[4],".");
	                t.nextToken();
	                to = t.nextToken();
	                final int mid = to.length() / 2;
	                String[] parts = {to.substring(1, mid+1),to.substring(mid+1)};
	                t1 = parts[1].concat(parts[0]);
	                t1 = t1.concat(" 16");
	                to = t1;*/
	                //System.out.println(from);
	                //System.out.println(to);
	            }
	            else if(s[4].contains("Eff."))
	            {
	            	from = s[4].substring(8) + " " + s[4].substring(5, 8) + " 16";
	            	
	            	/*String t1;
	                StringTokenizer t = new StringTokenizer(s[4],".");
	                t.nextToken();
	                from = t.nextToken();
	                final int mid = from.length() / 2;
	                String[] parts = {from.substring(1, mid+1),from.substring(mid+1)};
	                t1 = parts[1].concat(parts[0]);
	                t1 = t1.concat(" 16");
	                from = t1;*/
	                //System.out.println(from);
	                //System.out.println(to);
	            }
	            else if(s[4].contains("-"))
	            {
	                String t1;
	                StringTokenizer t = new StringTokenizer(s[4], " -");
	                from = t.nextToken().trim();
	                to = t.nextToken().trim();
	                
	                from = from.substring(3) + " " + from.substring(0, 3) + " 16";
	                to = to.substring(3) + " " + to.substring(0, 3) + " 16";
	                
	                
	                /*final int mid = to.length() / 2;
	                String[] parts = {to.substring(0, mid+1),to.substring(mid+1)};
	                t1 = parts[1].concat(" ");
	                t1 = t1.concat(parts[0]);
	                t1 = t1.concat(" 16");
	                to = t1;
	                final int mid1 = from.length() / 2;
	                String[] parts1 = {from.substring(0, mid1+1),from.substring(mid1+1)};
	                t1 = parts1[1].concat(" ");
	                t1 = t1.concat(parts1[0]);
	                t1 = t1.concat(" 16");
	                from = t1;*/
	                //System.out.println(from);
	                //System.out.println(to);
	            }
	            //System.out.println(from);
	            //System.out.println(to);
	            //System.out.println(excCase);
	            
	            StringTokenizer st1 = new StringTokenizer(s[3],"/+ ");
	            s[3] = st1.nextToken();
	            s[5] = st1.nextToken();
	            //System.out.println(s[3]);
	            //System.out.println(s[5]);
	            SimpleDateFormat parseFormat = new SimpleDateFormat("HHmm");
				SimpleDateFormat saveFormat = new SimpleDateFormat("HH:mm");
				try{
					Date parsed = parseFormat.parse(s[3]);
					s[3] = saveFormat.format(parsed);
					parsed = parseFormat.parse(s[5]);
					s[5] = saveFormat.format(parsed);
					//System.out.println(s[4] + "..." + s[5]);
				}
				catch(ParseException e){
					System.out.println(e.getMessage());
				}
	            //System.out.println(s[3]);
	            //System.out.println(s[5]);
	            
		        DateFormat df = new SimpleDateFormat("dd MMM yy");
		        Date effFrom = new Date();
		        Date effTill = new Date();
		        try{
		        	effFrom = df.parse(from);
		            effTill = df.parse(to);
		        }
		        catch(ParseException ex1){
		                System.out.println(ex1.getMessage());
		        }
	            //s[0] = s[0].toUpperCase();
	            silkSA[l] = new SilkAir(s[2],s[0],s[3],s[5],s[1],effFrom,effTill);
	            if(excCase == true)
	            	silkSA[l].setExcDates(excDates);
	            else
	            	silkSA[l].setExcDates(null);
	            //System.out.println("\t\t SILK AIR");
	            //silkSA[l].show();
	            //System.out.println("-------------------------------------------");
	            n = br.readLine();
	            l++;
	        }
	        //this.silkSA = silkSA;
	    }
	    catch(FileNotFoundException e){
	        System.out.println(e.getMessage());
	    }
	    catch(IOException ex){
	        System.out.println(ex.getMessage());
	    }
        return silkSA;
    }
    /**
     * Writes the booking object in booking.txt file.
     * @param book Booking Object
     */
    public void write(Booking book){
        FileWriter fw;
        BufferedWriter bw;
        String SpiceId = book.getCombo().getFlightSJ().getFlightId();
        String SilkId = book.getCombo().getFlightSA().getFlightId();
        int noOfPassengers = book.getPassengers();
        String passengerName = book.getName();
        String bookingId = book.getId();
        Date sjDate = book.getCombo().getDateSJ();
        Date saDate = book.getCombo().getDateSA();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateSJ = df.format(sjDate);
        String dateSA = df.format(saDate);
        try
        {
            String finalbook=bookingId.concat("|"+SpiceId+"|"+dateSJ+ "|"+ SilkId+"|"+ dateSA+"|"+passengerName+"|"+noOfPassengers+"|");
            System.out.println(finalbook);
            File file =new File("booking.txt");
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		fw = new FileWriter(file.getName(),true);
		    bw = new BufferedWriter(fw);
		    bw.newLine();
		    bw.write(finalbook);
		    System.out.println("Done");
		    bw.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("Thank You!");
        }
    }
    /**
     * Updates the number of seats in SpiceJet.xml and SilkAir.xml
     * @param booking Booking Object
     */
    
    public void update(Booking booking){
    	
    	FlightCombo combo = booking.getCombo();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Document doc =null;
    	Transformer transformer = null;
    	try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
    	
    	//Updating Spice Jet
    	try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("SpiceJet.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
    	NodeList nl = doc.getElementsByTagName("sjflight");
    	for(int i = 0; i <nl.getLength(); i++){
    		Node n = nl.item(i);
    		if(n.getNodeType() == Node.ELEMENT_NODE){
    			
    			Element e = (Element)n;
    			String d =e.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
    			if(d.equals(df.format(combo.getDateSJ()))){
    				String c = e.getElementsByTagName("source").item(0).getFirstChild().getNodeValue();
    				String fid = e.getElementsByTagName("flightid").item(0).getFirstChild().getNodeValue();
    				if(c.equals(combo.getFlightSJ().getOrigin()) && fid.equals(combo.getFlightSJ().getFlightId())){
    					Node seats = e.getElementsByTagName("seats").item(0);
    					int s = Integer.parseInt(seats.getFirstChild().getNodeValue()) - booking.getPassengers();
    					seats.getFirstChild().setNodeValue(s + "");
    					
    					StreamResult output = new StreamResult(new File("SpiceJet.xml"));
    					Source input = new DOMSource(doc);
    					try {
							transformer.transform(input, output);
						} catch (TransformerException e1) {
							e1.printStackTrace();
						}
    					break;
    				}
    			}
    			
    		}
    	}
    	
    	//Updating Silk Air
    	try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("SilkAir.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
    	nl = doc.getElementsByTagName("saflight");
    	for(int i = 0; i <nl.getLength(); i++){
    		Node n = nl.item(i);
    		if(n.getNodeType() == Node.ELEMENT_NODE){
    			
    			Element e = (Element)n;
    			String d =e.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
    			if(d.equals(df.format(combo.getDateSJ()))){
    				String c = e.getElementsByTagName("source").item(0).getFirstChild().getNodeValue();
    				String fid = e.getElementsByTagName("flightid").item(0).getFirstChild().getNodeValue();
    				if(c.equals(combo.getFlightSA().getOrigin()) && fid.equals(combo.getFlightSA().getFlightId())){
    					Node seats = e.getElementsByTagName("seats").item(0);
    					int s = Integer.parseInt(seats.getFirstChild().getNodeValue()) - booking.getPassengers();
    					seats.getFirstChild().setNodeValue(s + "");
    					
    					StreamResult output = new StreamResult(new File("SilkAir.xml"));
    					Source input = new DOMSource(doc);
    					try {
							transformer.transform(input, output);
						} catch (TransformerException e1) {
							e1.printStackTrace();
						}
    					break;
    				}
    			}
    			
    		}
    	}
    	
    	
    }
}
