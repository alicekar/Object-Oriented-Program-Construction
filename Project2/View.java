//package projectfinal;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;


/**
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The view class, which displays a GUI for the user to interact with
 */
public class View extends JFrame{
    private JPanel topPanel = new JPanel();
    private JTextPane chatArea = new JTextPane();
    private StyledDocument doc;
    private JTextField nameField = new JTextField();
    private JButton sendButton = new JButton("SEND");
    private JLabel nameLabel = new JLabel("  What's your name? ");

    private JPanel choicePanel = new JPanel();
    private JTextField portField = new JTextField("50000");
    private JTextField IPField = new JTextField(); 
    private JRadioButton serverButton = new JRadioButton("Server");
    private JRadioButton clientButton = new JRadioButton("Client");
    private JButton connectButton = new JButton("CONNECT");
    private JButton shutDownButton = new JButton("SHUT DOWN");

    private JPanel formatPanel = new JPanel();
    private JRadioButton boldButton = new JRadioButton("Bold");
    private JRadioButton italicButton = new JRadioButton("Italic");
    private JTextField colorField = new JTextField("#FF0000");

    private JTextField writeField = new JTextField();

    /**
     * The constructor
     * @param  controllerIn the controller, which listens to the connectButton, 
     * the shutDownButton and the sendButton
     */
    public View(Controller controllerIn){
    	super("CHAT PROGRAM");
    	doc = chatArea.getStyledDocument();

	connectButton.addActionListener(controllerIn);
	shutDownButton.addActionListener(controllerIn);
	sendButton.addActionListener(controllerIn);

	topPanel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.fill = GridBagConstraints.HORIZONTAL;

	chatArea.setBackground(Color.LIGHT_GRAY);
	JScrollPane chatScroll = new JScrollPane(chatArea);
	chatScroll.setPreferredSize(new Dimension(500,300));
	c.gridwidth = 4;
	c.gridheight = 1;
	topPanel.add(chatScroll, c);

	//Choose name
	c.gridy = c.gridy + c.gridheight;
	c.gridwidth = 1;
	c.gridheight = 1;
	topPanel.add(nameLabel, c);
	nameField.setPreferredSize(new Dimension(200,20));
	c.gridx ++;
	topPanel.add(nameField, c);

	//Write Message
	c.gridx --;
	c.gridy ++;
	topPanel.add(new JLabel("  Write your message here:   "), c);
	writeField.setPreferredSize(new Dimension(500,50));
	c.gridy ++;
	c.gridwidth = 2;
	c.gridheight = 2;
	topPanel.add(writeField, c);
	sendButton.setPreferredSize(new Dimension(100,50));
	c.gridx = 3;
	c.gridwidth = 1;
	topPanel.add(sendButton,c);
		
	createFormatPanel();
	c.gridx = 5;
	topPanel.add(formatPanel, c);
        
	createChoicePanel();
	c.gridx = 5;
	c.gridy = 0;
	topPanel.add(choicePanel, c);
        
	this.add(topPanel, BorderLayout.CENTER);
	this.pack();
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Builds the formatPanel, to be added to the topPanel 
     */
    private void createFormatPanel(){
	formatPanel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.fill = GridBagConstraints.HORIZONTAL;

	c.gridwidth = 2;
	formatPanel.add(new JLabel("   Color RRGGBB   "), c);
	c.gridy ++;
	formatPanel.add(colorField,c);

	c.gridx += 2;
	c.gridy = 0;
	formatPanel.add(boldButton, c);
	c.gridy ++;
	formatPanel.add(italicButton, c);
    }

    /**
     * Builds the choicePanel, to be added to the topPanel 
     */
    private void createChoicePanel(){    
	choicePanel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.fill = GridBagConstraints.HORIZONTAL;
        
	choicePanel.add(new JLabel("    What are you?   "), c);
	ButtonGroup choiceGroup = new ButtonGroup();
	choiceGroup.add(serverButton);
	choiceGroup.add(clientButton);
	c.gridx ++;
	choicePanel.add(serverButton, c);
	c.gridy ++;
	choicePanel.add(clientButton, c);
        
	c.gridx = 0;
	c.gridy = 2;
	c.gridwidth = 2;
	choicePanel.add(new JLabel("   Which port do you want to connect to?"
                + "   "), c);
	c.gridy ++;
	choicePanel.add(portField,c);
        
	c.gridy ++;
	choicePanel.add(new JLabel("   If Server: Which IP-address do you want"
                + " to connect to?   "), c);
	c.gridy ++;
	choicePanel.add(IPField,c);
        
	c.gridy ++;
	choicePanel.add(connectButton, c);

	c.gridy ++;
	choicePanel.add(shutDownButton, c);    
    }

    /**
     * Returns whatever is written in the colorField as a String
     * @return the color as a String
     */
    public String getColor(){
    	String color = colorField.getText();
    	return color;
    }

    /**
     * Returns whatever is written in the IPField as a String
     * @return the IP address as a String
     */
    public String getIP(){
    	String IPAddress = IPField.getText();
	return IPAddress;
    }  

    /**
     * Returns whatever is written in the writeField as a String
     * @return the message as a String
     */
    public String getMessage(){
    	String message = writeField.getText();
	writeField.setText("");
	return message;
    }
    
    /**
     * Returns whatever is written in the nameField as a String
     * @return the name as a String
     */
    public String getName(){
	return nameField.getText();
    }

    /**
     * Returns whatever is written in the portField as an Int
     * @return the port as an Int
     */
    public int getPort(){
    	String port = portField.getText();
	int intPort = Integer.parseInt(port);
	return intPort;
    }

    /**
     * Returns a boolean indicating if the client button is marked or not
     * @return true if the client button is marked, else false
     */
    public boolean isClient(){
	return clientButton.isSelected();
    }

    /**
     * Returns a boolean indicating if the server button is marked or not
     * @return true if the server button is marked, else false
     */
    public boolean isServer(){
	return serverButton.isSelected();
    }

    /**
     * Disables the connect button
     */
    public void setConnect(){
	connectButton.setText("CONNECTED");
	chatArea.setBackground(Color.WHITE);
	connectButton.setEnabled(false);
    }
    
    /**
     * Displays a message (textIn) in the chatArea in the given format (colorIn,
     * boldIn, italicIn)
     * @param  textIn the message as a string
     * @param  colorIn the color as a string
     * @param  boldIn a boolean indicating if the message is bold or not
     * @param  italicIn boolean indicating if the message is in italics or not
     */
    public void showMessage(String textIn, String colorIn, boolean boldIn, 
            boolean italicIn){
    	try{
            SimpleAttributeSet keyWord = new SimpleAttributeSet();
            StyleConstants.setForeground(keyWord, Color.decode(colorIn));
            StyleConstants.setBold(keyWord, boldIn);
            StyleConstants.setItalic(keyWord, italicIn);
            StyleConstants.setFontSize(keyWord, 14);

            doc.insertString(doc.getLength(), textIn + "\n", keyWord);
	}catch(Exception e){
		System.out.println("Error in show message");
	}
    } 

    /**
     * Returns a boolean indicating if the bold button is marked or not
     * @return true if the bold button is marked, else false
     */
    public boolean wantBold(){
	return boldButton.isSelected();
    }

    /**
     * Returns a boolean indicating if the italics button is marked or not
     * @return true if the italics button is marked, else false
     */
    public boolean wantItalic(){
    	return italicButton.isSelected();
    }
}









