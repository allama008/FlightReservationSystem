package FRSManager;

import AppPackage.AnimationClass;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Handles event handling, animations, auto suggest and other
 * GUI operations of Screen1
 * @author Allama
 */
/**
 * Custom AutoSuggestor class to check the words typed against 
 * and match it with the dictionary available in the AutoSuggestor
 * class which uses ArrayList<String>
 */
class AutoSuggestor {

    private final JTextField textField;
    private final Window container;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow;
    private String typedWord;
    private final ArrayList<String> dictionary = new ArrayList<>();
    private int currentIndexOfSpace, tW, tH;
    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }
    };
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;

    public AutoSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
        this.textField = textField;
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(documentListener);

        setDictionary(words);

        typedWord = "";
        currentIndexOfSpace = 0;
        tW = 0;
        tH = 0;

        autoSuggestionPopUpWindow = new JWindow(mainWindow);
        autoSuggestionPopUpWindow.setOpacity(opacity);

        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setBackground(popUpBackground);

        addKeyBindingToRequestFocusInPopUpWindow();
    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            /**
             * Focuses the first label on Pop-up Window
             */
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;
            /**
             * Allows scrolling of labels in pop window
             * @param ae 
             */
            @Override
            public void actionPerformed(ActionEvent ae) {

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    public ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        /**
         * Used to calculate size of JWindow as new jLabels are added
         */
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    protected void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);

        calculatePopUpWindowSize(suggestionLabel);

        suggestionsPanel.add(suggestionLabel);
    }

    public String getCurrentlyTypedWord() {//get newest word after last white spaceif any or the first word if no white spaces
        String text = textField.getText();
        String wordBeingTyped = "";
        if (text.contains(" ")) {
            int tmp = text.lastIndexOf(" ");
            if (tmp >= currentIndexOfSpace) {
                currentIndexOfSpace = tmp;
                wordBeingTyped = text.substring(text.lastIndexOf(" "));
            }
        } else {
            wordBeingTyped = text;
        }
        return wordBeingTyped.trim();
    }
    /**
     * To size the JWindow correctly
     * @param label 
     */
    private void calculatePopUpWindowSize(JLabel label) {
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() {
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX = 0;
        int windowY = 0;

        windowX = container.getX() + textField.getX() + 5;
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
        } else {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
        }

        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }

    public void setDictionary(ArrayList<String> words) {
        dictionary.clear();
        if (words == null) {
            return;//so we can call constructor with null value for dictionary without exception thrown
        }
        for (String word : words) {
            dictionary.add(word);
        }
    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }

    public Window getContainer() {
        return container;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addToDictionary(String word) {
        dictionary.add(word);
    }

    boolean wordTyped(String typedWord) {

        if (typedWord.isEmpty()) {
            return false;
        }
        //System.out.println("Typed word: " + typedWord);

        boolean suggestionAdded = false;

        for (String word : dictionary) {//get words in the dictionary which we added
            boolean fullymatches = true;
            for (int i = 0; i < typedWord.length(); i++) {//each string in the word
                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
                    fullymatches = false;
                    break;
                }
            }
            if (fullymatches) {
                addWordToSuggestions(word);
                suggestionAdded = true;
            }
        }
        return suggestionAdded;
    }
}

class SuggestionLabel extends JLabel {

    private boolean focused = false;
    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final AutoSuggestor autoSuggestor;
    private Color suggestionsTextColor, suggestionBorderColor;

    public SuggestionLabel(String string, final Color borderColor, Color suggestionsTextColor, AutoSuggestor autoSuggestor) {
        super(string);

        this.suggestionsTextColor = suggestionsTextColor;
        this.autoSuggestor = autoSuggestor;
        this.textField = autoSuggestor.getTextField();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

        initComponent1();
    }

    private void initComponent1() {
        setFocusable(true);
        setForeground(suggestionsTextColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
    }

    public void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    private void replaceWithSuggestedText() {
        String suggestedWord = getText();
        String text = textField.getText();
        String typedWord = autoSuggestor.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp + " ");
    }
}

public class Screen1 extends javax.swing.JFrame {

    FRSManager mgr;
    String ORIGIN_DATE = "01-Oct-2016"; 
    private String END_DATE = "13-Nov-2016";
    AnimationClass DC = new AnimationClass();
    AnimationClass AC = new AnimationClass();
    /**
     * Creates new form Screen1
     */
    public Screen1(FRSManager mgr) 
    {
        initComponents();
        initialize();
        slideshow();
        this.mgr = mgr;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField f = new JTextField(10);
        AutoSuggestor autoSuggestor;
        
        autoSuggestor = new AutoSuggestor(jTextField1, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f)
        {
            @Override
                boolean wordTyped(String typedWord)
                {
                    ArrayList<String> words = new ArrayList<>();
                    words.add("Delhi(DEL), Indira Gandhi International Airport");
                    words.add("Mumbai(BOM), Chhatrapati Shivaji International Airport");
                    words.add("Pune(PNQ), Pune Airport");
                    //words.add("Bangalore, Bengaluru International Airport (BLR)");
                    //words.add("Chennai, Chennai International Airport (MAA)");
                    //words.add("Hyderabad, Rajiv Gandhi International Airport (HYD)");
                    //words.add("Kolkata, Netaji Subhash Chandra Bose International Airport (CCU)");
                    setDictionary(words);
                    return super.wordTyped(typedWord);  
                }     
        };
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy"); 
        Date min_date = new Date(0); 
        Date max_date = new Date(0);
        try { 
         min_date = formatter.parse(ORIGIN_DATE); 
         max_date = formatter.parse(END_DATE);
        } catch (ParseException e) { 
         // TODO Auto-generated catch block 
         e.printStackTrace(); 
        } 
        jDateChooser1.setDate(min_date); 
        jDateChooser1.setMinSelectableDate(min_date); 
        jDateChooser1.setMaxSelectableDate(max_date);
        frame.pack();
        frame.setVisible(false);
    }
    private Screen1()
    {
        initComponents();
    }                                  
    /**
     * Function for Image Slider in Screen1
     */
    public void slideshow()
    {
        new Thread()
        {
            int count;
            @Override
            public void run()
            {
                try
                {
                    while(true)
                    {
                        switch(count)
                        {
                            case 0:
                                ImageIcon II = new ImageIcon(getClass().getResource("/images/7.png"));
                                jLabel8.setIcon(II);
                                Thread.sleep(5000);
                                jLabel9.setVisible(true);
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel9);
                                jLabel8.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel8);
                                count = 1;                               
                                break;
                            case 1:
                                ImageIcon II2 = new ImageIcon(getClass().getResource("/images/8.png"));
                                jLabel9.setIcon(II2);
                                Thread.sleep(7200);                                
                                jLabel10.setVisible(true);                                
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel10);
                                jLabel9.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel9);
                                count = 2;                               
                                break;
                            case 2:
                                ImageIcon II3 = new ImageIcon(getClass().getResource("/images/9.png"));
                                jLabel10.setIcon(II3);
                                Thread.sleep(7200);                                
                                jLabel11.setVisible(true);                                
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel11);
                                jLabel10.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel10);
                                count = 3;                               
                                break;
                            case 3:
                                ImageIcon II4 = new ImageIcon(getClass().getResource("/images/10.png"));
                                jLabel11.setIcon(II4);
                                Thread.sleep(7200);                                
                                jLabel12.setVisible(true);                                
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel12);
                                jLabel11.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel11);
                                count = 4;                               
                                break;
                            case 4:
                                ImageIcon II5 = new ImageIcon(getClass().getResource("/images/11.png"));
                                jLabel12.setIcon(II5);
                                Thread.sleep(7200);                                
                                jLabel13.setVisible(true);                              
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel13);
                                jLabel12.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel12);
                                count = 5;                               
                                break;
                            case 5:
                                ImageIcon II6 = new ImageIcon(getClass().getResource("/images/12.png"));
                                jLabel13.setIcon(II6);
                                Thread.sleep(7200);                                
                                jLabel8.setVisible(true);                                
                                DC.jLabelXRight(-660, 60, 30, 5, jLabel8);
                                jLabel13.setVisible(false);
                                DC.jLabelXLeft(60, -660, 30, 5, jLabel13);
                                count = 0;                               
                                break;
                            
                        }
                    }
                }
                catch(Exception e)
                {
                    
                }
            }
        }.start();
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
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH); 
        setIconImage(new ImageIcon(getClass().getResource("/images/3.png")).getImage());
        setSize(1360, 720);
        setPreferredSize(new Dimension(1360, 720));
        setTitle("Travel World");
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
        jDialog1.setIconImage(new ImageIcon(getClass().getResource("/images/22.png")).getImage());
        jDialog1.setLocationRelativeTo(null);
        jDialog1.setVisible(false);
        jTextField1.setVisible(false);
        jTextField2.setVisible(false);
        jDateChooser1.setVisible(false);
        jComboBox1.setVisible(false);
        jButton1.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel3.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        Font font = jLabel14.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        jLabel14.setFont(font.deriveFont(attributes));
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
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        jDialog1.setTitle("Error!");
        jDialog1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDialog1.setSize(new java.awt.Dimension(430, 190));
        jDialog1.setType(java.awt.Window.Type.POPUP);
        jDialog1.getContentPane().setLayout(null);

        jPanel4.setLayout(null);

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);
        jButton2.setBounds(320, 110, 80, 30);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/23.png"))); // NOI18N
        jLabel16.setText("Choose your departure airport:  This is a mandatory field; ");
        jPanel4.add(jLabel16);
        jLabel16.setBounds(10, 30, 500, 36);
        jLabel16.getAccessibleContext().setAccessibleName(" Choose your departure airport:  This is a mandatory field; ");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("please review and try again.");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(50, 60, 180, 36);

        jDialog1.getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 0, 410, 150);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("frame2");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        jButton1.setBackground(new java.awt.Color(153, 0, 153));
        jButton1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Find flights");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(820, 580, 130, 50);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComboBox1.setNextFocusableComponent(jButton1);
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(1000, 510, 160, 40);

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setText("Passengers:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(820, 510, 80, 40);

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setText("Date:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(820, 440, 50, 40);

        jDateChooser1.setDateFormatString("dd-MMM-yyyy");
        jDateChooser1.setNextFocusableComponent(jComboBox1);
        getContentPane().add(jDateChooser1);
        jDateChooser1.setBounds(970, 440, 190, 40);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/18.png"))); // NOI18N
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4);
        jLabel4.setBounds(920, 270, 40, 40);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/17.png"))); // NOI18N
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(920, 200, 40, 40);

        jTextField1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jTextField1.setText("Departure airport");
        jTextField1.setNextFocusableComponent(jDateChooser1);
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField1MouseReleased(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(960, 200, 200, 40);

        jTextField2.setEditable(false);
        jTextField2.setText("Singapore, Changi Airport (SIN)");
        getContentPane().add(jTextField2);
        jTextField2.setBounds(960, 270, 200, 40);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/4.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153), 4));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 0, 308, 170);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);
        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 50, 1650, 90);

        jPanel1.setBackground(new java.awt.Color(153, 0, 153));
        jPanel1.setLayout(null);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("About");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel14MouseReleased(evt);
            }
        });
        jPanel1.add(jLabel14);
        jLabel14.setBounds(1230, 10, 60, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1650, 50);

        jLabel2.setBackground(new java.awt.Color(153, 0, 153));
        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Book a flight");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), null, null));
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(1130, 200, 160, 50);

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(850, 140, 440, 400);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7.png"))); // NOI18N
        jLabel8.setOpaque(true);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(60, 250, 720, 400);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8.png"))); // NOI18N
        getContentPane().add(jLabel9);
        jLabel9.setBounds(-660, 250, 720, 400);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/9.png"))); // NOI18N
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10);
        jLabel10.setBounds(-660, 250, 720, 400);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/10.png"))); // NOI18N
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(-660, 250, 720, 400);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/11.png"))); // NOI18N
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12);
        jLabel12.setBounds(-660, 250, 720, 400);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/12.png"))); // NOI18N
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13);
        jLabel13.setBounds(-660, 250, 720, 400);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setMinimumSize(new java.awt.Dimension(1650, 560));
        jPanel3.setLayout(null);
        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 140, 1650, 560);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /**
     * Function to slide down the ' Book a Flight ' option and set all
     * fields visible
     * @param evt 
     */
     public void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    jLabel3.setVisible(true);
                    AC.jLabelYDown(140, 250, 20, 2, jLabel3);
                    Thread.sleep(550);
                    jTextField1.setVisible(true);
                    jTextField2.setVisible(true);
                    jDateChooser1.setVisible(true);
                    jComboBox1.setVisible(true);
                    jButton1.setVisible(true);
                    jLabel4.setVisible(true);
                    jLabel5.setVisible(true);
                    jLabel6.setVisible(true);
                    jLabel7.setVisible(true);
                    AC.jTextFieldYDown(200, 300, 20, 1, jTextField1);
                    AC.jLabelYDown(200, 300, 20, 1, jLabel5);
                    AC.jTextFieldYDown(270, 370, 20, 1, jTextField2);
                    AC.jLabelYDown(270, 370, 20, 1, jLabel4);
                    AC.jLabelXRight(820, 920, 20, 1, jLabel6);
                    AC.jLabelXRight(820, 920, 20, 1, jLabel7);
                    AC.jButtonXRight(820, 1140, 20, 4, jButton1);
                }
                catch(Exception e)
                {
                   
                }
            }
        }.start();
    }  
    /**
     * Utility function to make the Text Field look good
     * @param evt 
     */
    private void jTextField1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseReleased
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField1.setFont(new Font("Tahoma", Font.PLAIN, 11));
    }//GEN-LAST:event_jTextField1MouseReleased
    /**
     * Event Handling - Used to move to the 'About' screen
     * @param evt 
     */
    private void jLabel14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseReleased
        mgr.displayMgr.ShowAbout();
    }//GEN-LAST:event_jLabel14MouseReleased
    /**
     * Event Handling - Used to save all the information entered and 
     * then move to Screen2
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try
        {
            StringTokenizer st = new StringTokenizer(jTextField1.getText());
            mgr.displayMgr.departureCity = (st.nextToken(","));
            mgr.displayMgr.fromCity = (st.nextToken("("));
            
        }
        catch(NoSuchElementException e)
        {
            jDialog1.setVisible(true);
        }
        StringTokenizer st = new StringTokenizer(jTextField1.getText());
        mgr.displayMgr.fromCity = (st.nextToken("("));
        mgr.displayMgr.departureCity = mgr.displayMgr.fromCity+(st.nextToken(","));
        mgr.displayMgr.depDate = jDateChooser1.getDate();
        mgr.displayMgr.passengerNum = (jComboBox1.getSelectedIndex()+1);
        mgr.myCombo = mgr.searchMgr.search(mgr.displayMgr.departureCity, mgr.displayMgr.depDate, mgr.displayMgr.passengerNum, mgr.spiceSJ, mgr.silkSA);
        mgr.displayMgr.ch = mgr.searchMgr.getCountCombo();
        mgr.displayMgr.ShowScreen2();
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * Used to suspend the warning message
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
/*
    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseReleased
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    protected javax.swing.JComboBox<String> jComboBox1;
    protected com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    protected javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}

