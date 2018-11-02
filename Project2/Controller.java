import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class Controller implements ActionListener, Runnable {
	View theView;
	private ServerSocket server;
    private Socket client;
    private boolean connected = false;
    
    private BufferedReader streamIn;
    private PrintStream streamOut; 

	public Controller(){
		//theView = new View(this);
		//xmlToString("<message sender="+"B"+"><text color="+"#999999"+"><fetstil>bara bold<\\fetstil><\\text><\\message>");
		xmlToString("<message sender="+"B"+"><text color="+"#999999"+"><fetstil><kursiv>bold?<\\kursiv><\\fetstil><\\text><\\message>");
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
				String xml = stringToXml(text, name, color, bold, italic);
				//String textMessage = xmlToString(xml);
				//streamOut.println(name + ": "+textMessage);
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

    private String stringToXml(String textIn, String nameIn, String colorIn, boolean boldIn, boolean italicIn){
		String theXml = "<message sender=\"" + nameIn + "\"><text color=\"" + colorIn + "\">";
		if (boldIn){
			if (italicIn && boldIn){
				theXml += "<fetstil><kursiv>" + modifyText(textIn) + "<\\kursiv><\\fetstil>";
			}else if(boldIn){
				theXml += "<fetstil>" + modifyText(textIn) + "<\\fetstil>";	
			} else {
				theXml += "<kursiv>" + modifyText(textIn) + "<\\kursiv>";
			}
		}else{
			theXml += modifyText(textIn);
		}
		theXml += "<\\text><\\message>";
		return theXml;
	}

    private String modifyText(String aText){
		String modText = aText.replace(">", "&gt");
		modText = modText.replace(">", "&lt");
		return modText;
	}

	private List<String> getCleanString(String[] stringIn){
		List<String> allParts = Arrays.asList(stringIn);
		int nParts = allParts.size();
		List<String> cleanString = new ArrayList<String>();
		System.out.println(allParts);
		for(String s:allParts){
			if(s.equals("")){
			}else{
				System.out.println(s);
				cleanString.add(s);
			}
		}
		return cleanString;
	}

	private String xmlToString(String xmlIn){
		// <message sender="A"><text color="#999999">Hej, jag vill vet hur detta ska redigeras<\text><\message>
		//<message sender="B"><text color="#999999">kul, jag vill ha italic<\text><\message>
		//<message sender="B"><text color="#999999">halå<\text><\message>
		//<message sender="B"><text color="#999999"><fetstil><kursiv>bold?<\kursiv><\fetstil><\text><\message>
		//<message sender="B"><text color="#999999"><fetstil>bara bold<\fetstil><\text><\message>
		String text = new String();
		String color = new String();
		String type1 = new String();
		String type2 = new String();
		List<String> info = new ArrayList<String>();
		try{
			String[] splitParts = xmlIn.split(">|<");
		
			List<String> parts = getCleanString(splitParts);
			int nParts = parts.size()-1;
			//String part = Arrays.toString(parts);
			
			System.out.println(parts);
			System.out.println(nParts);
			//String[] clean = Arrays.stream(parts).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
			
			
			String mess1 = parts.get(0);
			String mess2 = parts.get(nParts);
			String txt1 = parts.get(1);
			String txt2 = parts.get(nParts-1);
			
			System.out.println(txt1.substring(12, txt1.length()));
			
			if(mess1.substring(0,7).equals(mess2.substring(mess2.length()-7,mess2.length()))){
				System.out.println("JAA");
				if(txt1.substring(0,4).equals(txt2.substring(txt2.length()-4,txt2.length()))){
					System.out.println("mm");
					if(nParts == 4){
						text = parts.get(2);
						color = txt1.substring(12, txt1.length());
						info.add(text);
						info.add(color);
					}else if(nParts == 6){
						text = parts.get(3);
					    color = txt1.substring(12, txt1.length());
						type1 = parts.get(2);
						info.add(text);
						info.add(color);
						info.add(type1);
					}else if(nParts == 8){
						text = parts.get(4);
						color = txt1.substring(12, txt1.length());
						type1 = parts.get(2);
						type2 = parts.get(3);
						info.add(text);
						info.add(color);
						info.add(type1);
						info.add(type2);
						System.out.println(color);
				
					}else{
						System.out.println("There is something wrong with the XML or an empty"
							+"string was sent");
					}

				}
			}
		}catch(Exception e){
			System.out.println("There is something wrong with the XML");
		}

		

		
		

		System.out.println(info);

		
		return text;
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


