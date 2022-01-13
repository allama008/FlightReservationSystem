package FRSManager;

import java.io.IOException;
import java.util.*;
public class FRSManager 
{
    DataMgr dataMgr;
    SearchMgr searchMgr;
    //static int z = 2;
    DisplayManager displayMgr;
    CLIManager cliMgr;
    BookingMgr bookingMgr;
    SpiceJet []spiceSJ;
    SilkAir []silkSA;
    FlightCombo []myCombo;
    FlightCombo selectedCombo;
    String passengerName;
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        FRSManager mgr = new FRSManager(); 
        mgr.dataMgr = new DataMgr();
        mgr.searchMgr = new SearchMgr();
        mgr.bookingMgr = new BookingMgr();
        mgr.spiceSJ = mgr.dataMgr.readSpiceJet(args[0]);
        mgr.silkSA = mgr.dataMgr.readSilkAir(args[1]);
        String choice = "";
        if(args.length > 2)
            choice= args[2].trim();
        if(choice.equals("text"))
        {
            mgr.cliMgr=new CLIManager(mgr);
            mgr.cliMgr.ShowScreen1();        
        }
        else
        {
            if(choice.length() > 0)
                System.out.println(choice + " has not yet been implemented. Defaulting to Swing Display");
            mgr.displayMgr = new DisplayManager(mgr);
            mgr.displayMgr.ShowScreen1();
        }     
    }
}
