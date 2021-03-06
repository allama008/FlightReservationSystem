package FRSManager;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Handles event handling, displays information and performs other 
 * GUI operations of Screen 4
 * @author Allama
 */
public class Screen4 extends javax.swing.JFrame {

    /**
     * Creates new form Screen4
     */
    private FRSManager mgr;
    public Screen4(FRSManager mgr) {
        this.mgr = mgr;
        initComponents();
        initialize();
    }
    private Screen4()
    {
        initComponents();
    }
    /**
     * Function to initialize and set the software details, and other 
     * processes and utilities at the beginning
     */
    private void initialize()
    {
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
            
        });
        jDialog1.setLocationRelativeTo(null);
        jDialog1.setVisible(false);
        jDialog1.setUndecorated(true);
        setIconImage(new ImageIcon(getClass().getResource("/images/3.png")).getImage());
        setSize(1360, 720);
        setPreferredSize(new Dimension(1360, 720));
        setTitle("Travel World");
        setLocationRelativeTo(null);
        setResizable(true);
        pack();
        setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jDialog1.setSize(new java.awt.Dimension(537, 310));
        jDialog1.getContentPane().setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(153, 0, 153));
        jPanel5.setLayout(null);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/travelworld6.png"))); // NOI18N
        jPanel5.add(jLabel31);
        jLabel31.setBounds(0, 0, 180, 150);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dgasd.png"))); // NOI18N
        jPanel5.add(jLabel32);
        jLabel32.setBounds(270, 0, 140, 40);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Wishes You A Safe and Happy Journey!");
        jPanel5.add(jLabel33);
        jLabel33.setBounds(210, 40, 280, 40);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Thank You for Booking With Us! ");
        jPanel5.add(jLabel34);
        jLabel34.setBounds(230, 70, 230, 40);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setLayout(null);

        jButton3.setBackground(new java.awt.Color(153, 0, 153));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton3);
        jButton3.setBounds(410, 10, 120, 40);

        jPanel5.add(jPanel8);
        jPanel8.setBounds(0, 150, 540, 60);

        jDialog1.getContentPane().add(jPanel5);
        jPanel5.setBounds(0, 100, 537, 210);

        jPanel6.setBackground(new java.awt.Color(153, 0, 153));
        jDialog1.getContentPane().add(jPanel6);
        jPanel6.setBounds(0, 0, 540, 40);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jDialog1.getContentPane().add(jPanel7);
        jPanel7.setBounds(0, 40, 540, 60);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Booking Confirmation");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(420, 20, 470, 58);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153), 3));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel2.setText("Booking Reference      :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 90, 180, 40);

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel3.setText("Name                                 :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(70, 140, 180, 40);

        jLabel4.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel4.setText("Date                                  :");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(70, 190, 180, 40);

        jLabel5.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel5.setText("Seats Booked                :");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(70, 240, 180, 40);

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel6.setText(" Travel Time                    :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(70, 360, 180, 40);

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel7.setText("Transit Time                    :");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(70, 410, 180, 40);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153), 2));
        jPanel2.setLayout(null);

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel14.setText("jLabel14");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(50, 10, 110, 40);

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel15.setText("jLabel15");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(260, 10, 170, 40);

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel16.setText("jLabel16");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(50, 60, 150, 20);

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel17.setText("jLabel17");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(260, 60, 140, 20);

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel18.setText("jLabel18");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(50, 90, 150, 20);

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel19.setText("jLabel19");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(260, 90, 140, 20);

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel20.setText("jLabel20");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(170, 140, 70, 40);

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/15.png"))); // NOI18N
        jPanel2.add(jLabel28);
        jLabel28.setBounds(200, 10, 40, 40);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/26.png"))); // NOI18N
        jPanel2.add(jLabel30);
        jLabel30.setBounds(240, 120, 70, 70);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(620, 50, 440, 190);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153), 2));
        jPanel4.setLayout(null);

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel21.setText("jLabel21");
        jPanel4.add(jLabel21);
        jLabel21.setBounds(50, 10, 130, 40);

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel22.setText("Singapore");
        jPanel4.add(jLabel22);
        jLabel22.setBounds(260, 10, 130, 40);

        jLabel23.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel23.setText("jLabel23");
        jPanel4.add(jLabel23);
        jLabel23.setBounds(50, 60, 140, 20);

        jLabel24.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel24.setText("jLabel24");
        jPanel4.add(jLabel24);
        jLabel24.setBounds(260, 60, 140, 20);

        jLabel25.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel25.setText("jLabel25");
        jPanel4.add(jLabel25);
        jLabel25.setBounds(50, 90, 140, 20);

        jLabel26.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel26.setText("jLabel26");
        jPanel4.add(jLabel26);
        jLabel26.setBounds(260, 90, 140, 20);

        jLabel27.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel27.setText("jLabel27");
        jPanel4.add(jLabel27);
        jLabel27.setBounds(170, 140, 70, 40);

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/15.png"))); // NOI18N
        jPanel4.add(jLabel29);
        jLabel29.setBounds(200, 10, 40, 40);
        jPanel4.add(jLabel35);
        jLabel35.setBounds(240, 120, 70, 70);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(620, 280, 440, 190);

        jLabel8.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(300, 90, 180, 40);

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel9);
        jLabel9.setBounds(300, 140, 300, 40);

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel10);
        jLabel10.setBounds(300, 190, 260, 40);

        jLabel11.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel11);
        jLabel11.setBounds(300, 240, 180, 40);

        jLabel12.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel12);
        jLabel12.setBounds(300, 360, 300, 40);

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jPanel1.add(jLabel13);
        jLabel13.setBounds(300, 410, 310, 40);

        jPanel3.add(jPanel1);
        jPanel1.setBounds(110, 100, 1130, 520);

        jButton1.setBackground(new java.awt.Color(153, 0, 153));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setLabel("Book Another");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(840, 630, 190, 40);

        jButton2.setBackground(new java.awt.Color(153, 0, 153));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setLabel("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(1043, 630, 190, 40);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 1350, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Event Handling - Used to return to Screen1 in case the user wants
     * to make another booking
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mgr.displayMgr.ShowScreen1();
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * Event Handling - Used to move to the Exit Pop-up window
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jDialog1.setVisible(true);
        mgr.displayMgr.s4.setVisible(false);
        //
    }//GEN-LAST:event_jButton2ActionPerformed
    /**
     * Event Handling - Used to Exit the system
     * @param evt 
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        mgr.displayMgr.exitFRS();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel10;
    protected javax.swing.JLabel jLabel11;
    protected javax.swing.JLabel jLabel12;
    protected javax.swing.JLabel jLabel13;
    protected javax.swing.JLabel jLabel14;
    protected javax.swing.JLabel jLabel15;
    protected javax.swing.JLabel jLabel16;
    protected javax.swing.JLabel jLabel17;
    protected javax.swing.JLabel jLabel18;
    protected javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel20;
    protected javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    protected javax.swing.JLabel jLabel23;
    protected javax.swing.JLabel jLabel24;
    protected javax.swing.JLabel jLabel25;
    protected javax.swing.JLabel jLabel26;
    protected javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    protected javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    protected javax.swing.JLabel jLabel8;
    protected javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables

}

