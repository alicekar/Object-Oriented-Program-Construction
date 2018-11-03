import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class Controller implements ActionListener, Runnable {
	View theView;
	Model theModel;
	private ServerSocket server;
    private Socket client;
    private boolean connected = false;
    
    private BufferedReader streamIn;
    private PrintStream streamOut; 

	public Controller(){
		theView = new View(this);
		theModel = new Model();
		//theModel.xmlToString("<message sender="+"B"+"><text color="+"#999999"+"><fetstil>bara bold<\\fetstil><\\text><\\message>");
		//theModel.xmlToString("<message sender="+"A"+"><text color="+"#999999"+">hoho&gt&lthaha&lt&lthehe&gt&gt<\\text><\\message>");
		//List<Object> info =theModel.xmlToString("<message sender="+"B"+"><text color="+"#999999"+"><fetstil><kursiv>bold?<\\kursiv><\\fetstil><\\text><\\message>");
		//theModel.getTypeAndColor(info);
	}


    public void actionPerformed(ActionEvent ae){
        if (ae.getActionCommand() == "CONNECT"){
			// Create a connection
            if (connected == false){
				// If we are a server
                if (theView.isServer()){
                    try{
                        server = new ServerSocket(theView.getPort());
                        client = server.accept();

						streamIn =  new BufferedReader(new InputStreamReader(client.getInputStream()));
						streamOut = new PrintStream(client.getOutputStream());
                        
                        connected = true;
                        theView.setConnectUnable();
                        theView.showMessage("A client successfully connected to you", "#000000", false, false);
                        theView.setConnect();

						new Thread(this).start();

                    }catch(Exception e){
                        System.out.println("Sorry, we could not find any clients");
                        System.out.println(e);
                    }
                }
				// If we are a client
                else if (theView.isClient()){
                    try{
                        client = new Socket(theView.getIP(), theView.getPort());

						streamIn =  new BufferedReader(new InputStreamReader(client.getInputStream()));
						streamOut = new PrintStream(client.getOutputStream());
                        
                        connected = true;
                        theView.setConnectUnable();
                        theView.showMessage("You successfully connected to a server", "#000000", false, false);
                        theView.setConnect();

						new Thread(this).start();

                    }catch(Exception e){
                        theView.showMessage("Sorry, you could not connect to the server", "#000000", false, false);
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
				boolean bold = theView.wantBold();
				boolean italic = theView.wantItalic();

                String messageOut = name + ": " + text;
                theView.showMessage(messageOut, color, bold, italic);
				String xml = theModel.stringToXml(text, name, color, bold, italic);
				List<Object> info = theModel.xmlToString(xml);
				Object textObject = info.get(0);
				Object colorObject = info.get(1);
				Object boldObject = info.get(2);
				Object italicObject = info.get(3);
				String senderText = textObject.toString();
				String senderColor = colorObject.toString();
				Boolean senderBold = (Boolean) boldObject;
				Boolean senderItalic = (Boolean) italicObject;
				System.out.println(senderColor);
				//theView.showMessage(senderText, senderColor, senderBold, senderItalic);
				streamOut.println(name + ": "+senderText);
            }catch(Exception e){
                System.out.println("Error when sending a message");
            }
        }
        
		// Shut down
        if (ae.getActionCommand() =="SHUT DOWN"){
			try{     
				if (connected){
		            String disconnectMessage = theView.getName() + " logged out";
					streamOut.println(disconnectMessage);
				}
				System.exit(0);
            }catch(Exception e){
                System.out.println("Error when sending a message");
            }
        }
    } 	


    public void run(){
    	// Read and display incoming messages
		if (connected){
			try {
				while (connected ==true) {
					String line = streamIn.readLine();
					if (line!=null){
						theView.showMessage(line, "#000000", true, true);
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


