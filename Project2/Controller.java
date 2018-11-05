//package projectfinal;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The controller class, which handles all communication between the model and 
 * the view 
 */
public class Controller implements ActionListener, Runnable {
    private View theView;
    private Model theModel;

    private ServerSocket server;
    private Socket client;
    private boolean connected = false;
    
    private BufferedReader streamIn;
    private PrintStream streamOut; 

    /**
     * The constructor
     */
    public Controller(){
    	theView = new View(this);
	theModel = new Model();
    }

    /**
     * An action listener on three buttons in the view. 
     * If the connectButton is pressed, the server/client tries to connect to a 
     * client/server. If so, a thread is started. 
     * If the sendButton is pressed, a message is displayed in the own view, and 
     * sent as an XML on an output stream.
     * If the shutDownButton is pressed, a log out message is sent and the 
     * program shuts down. 
     * @param ae event when a button is klicked
     */
    public void actionPerformed(ActionEvent ae){
	if (ae.getActionCommand() == "CONNECT"){
            // Create a connection
            if (connected == false){
		// If we are a server
		if (theView.isServer()){
                    try{
			server = new ServerSocket(theView.getPort());
                        client = server.accept();

			streamIn =  new BufferedReader(new InputStreamReader
                            (client.getInputStream()));
			streamOut = new PrintStream(client.getOutputStream());
                        
			connected = true;
        		theView.showMessage("A client successfully connected to"
                                + " you", "#000000", false, false);
			theView.setConnect();
			new Thread(this).start();
                        
			}catch(Exception e){
                            System.out.println("Sorry, we could not find any "
                                    + "clients");
                            System.err.println(e);
			}
		}
		// If we are a client
		else if (theView.isClient()){
                    try{
			client = new Socket(theView.getIP(), theView.getPort());

			streamIn =  new BufferedReader(new InputStreamReader
                            (client.getInputStream()));
                        streamOut = new PrintStream(client.getOutputStream());
                        
			connected = true;
			theView.showMessage("You successfully connected to a "
                                + "server", "#000000", false, false);
			theView.setConnect();

			new Thread(this).start();

			}catch(Exception e){
				theView.showMessage("Sorry, you could not "
                                        + "connect to the server", "#000000", 
                                        false, false);
			}
		}
            }
	}
        
	// Send a message
	else if (ae.getActionCommand() == "SEND"){
            try{     
                String text = theView.getMessage();
                String name = theView.getName();
                String color = theView.getColor();
                String colorVal = theModel.validateColor(color);
                if (!colorVal.equals(color)){
                    theView.showMessage("Invalid color!", "#000000", false, 
                            false);	
                }
                boolean bold = theView.wantBold();
                boolean italic = theView.wantItalic();

                String messageOut = name + ": " + text;
                theView.showMessage(messageOut, colorVal, bold, italic);

                if (connected){
                    String xml = theModel.stringToXml(text, name, colorVal, 
                            bold, italic);
                    streamOut.println(xml);
                } else {
                    theView.showMessage("You are not connected to anyone", 
                            "#000000", false, false);
                }
            }catch(Exception e){
                System.out.println("Error when sending a message");
                System.err.println(e);
            }
	}
        
	// Shut down
	if (ae.getActionCommand() =="SHUT DOWN"){
		try{     
                    if (connected){
                       	String xml = theModel.stringToXml("Logged out!", 
                                theView.getName(), "#000000", false, false);
			streamOut.println(xml);
                    }
            	System.exit(0);
		}catch(Exception e){
                    //System.out.println("Error when disconnecting");	
                    //System.err.println(e);
                }
        }   
    } 	

    /**
     * The run method, which constantly listens to an incoming stream of 
     * messages and displays them in the view
     */
    public void run(){
        // Read and display incoming messages
        if (connected){
            try {
                while (connected ==true) {
                    String line = streamIn.readLine();
                        if (line!=null){
                            Info info = theModel.xmlToString(line);
                            if (info.getOK()){
                                theView.showMessage(info.getText(), 
                                        info.getColor(), info.getBold(), 
                                        info.getItalics());
                            }
                        }
                    }
                } catch (Exception e) {
                            System.err.println("Exception:  " + e);
                }
        }
    }

    public static void main(String[] args) {
            Controller myController = new Controller();
    }
}


