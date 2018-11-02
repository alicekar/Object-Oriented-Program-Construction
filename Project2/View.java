import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;


public class View extends JFrame{
	private JPanel topPanel = new JPanel();
	private JTextPane chatArea = new JTextPane();
	private StyledDocument doc;
    private JTextField nameField = new JTextField();
    private JButton sendButton = new JButton("SEND");
    private JLabel chatLabel = new JLabel("CHAT");
    private JLabel nameLabel = new JLabel("  What's your name? ");

    private JPanel choicePanel = new JPanel();
    private JTextField portField = new JTextField("50000");
    private JTextField IPField = new JTextField("192.168.0.15");
    private JRadioButton serverButton = new JRadioButton("Server");
    private JRadioButton clientButton = new JRadioButton("Client");
	private JButton connectButton = new JButton("CONNECT");
	private JButton shutDownButton = new JButton("SHUT DOWN");

	private JPanel formatPanel = new JPanel();
	private JRadioButton boldButton = new JRadioButton("Bold");
	private JRadioButton italicButton = new JRadioButton("Italic");
	private JTextField colorField = new JTextField("#999999");

	public JTextField writeField = new JTextField();

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
        choicePanel.add(new JLabel("   Which port do you want to connect to?   "), c);
        c.gridy ++;
        choicePanel.add(portField,c);
        
        c.gridy ++;
        choicePanel.add(new JLabel("   If Server: Which IP-address do you want to connect to?   "), c);
        c.gridy ++;
        choicePanel.add(IPField,c);
        
        c.gridy ++;
        choicePanel.add(connectButton, c);

        c.gridy ++;
        choicePanel.add(shutDownButton, c);
        
    }

    public String getName(){
        return nameField.getText();
    }
    
    public int getPort(){
        String port = portField.getText();
        int intPort = Integer.parseInt(port);
        return intPort;
    }

    public String getIP(){
        String IPAddress = IPField.getText();
        return IPAddress;
    }

    public void setConnect(){
        connectButton.setText("CONNECTED");
        chatArea.setBackground(Color.WHITE);
    }

    public void setConnectUnable(){
        connectButton.setEnabled(false);
    }
    
    public void showMessage(String text, String color, boolean bold, boolean italic){
        try{
            SimpleAttributeSet keyWord = new SimpleAttributeSet();
            StyleConstants.setForeground(keyWord, Color.decode(color));
            StyleConstants.setBold(keyWord, bold);
            StyleConstants.setItalic(keyWord, italic);
            StyleConstants.setFontSize(keyWord, 14);

            doc.insertString(doc.getLength(), text + "\n", keyWord);
        }catch(Exception e){
            System.out.println("Error in show message");
        }
    }    


    public String getMessage(){
        String message = writeField.getText();
        writeField.setText("");
        return message;
    }
    
    public boolean isServer(){
        return serverButton.isSelected();
    }
    
    public boolean isClient(){
        return clientButton.isSelected();
    }

    public boolean wantBold(){
        return boldButton.isSelected();
    }

    public boolean wantItalic(){
        return italicButton.isSelected();
    }


    public String getColor(){
        String color = colorField.getText();
        return color;
    }

}









