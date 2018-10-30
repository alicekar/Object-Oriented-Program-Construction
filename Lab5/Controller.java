import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.event.*;

/**
 *
 * @author alicekarnsund
 * 
 */	
public class Controller implements HyperlinkListener, ActionListener, KeyListener{

	View myView = new View(this);


	public Controller(){
        try{
            hyperlinkUpdate(new HyperlinkEvent(this, 
                    HyperlinkEvent.EventType.ACTIVATED, new URL("http://kth.se")));
		}catch (MalformedURLException e){
			myView.errorMessage("There is something wrong with the home page");
        } 
	}

 	/** The close-button closes when being clicked
     * @param closeIn
     * @param frameIn 
     */
	public void actionClose(JButton closeIn, JFrame frameIn){
		closeIn.addActionListener(e -> frameIn.dispose());
	}

	/** hyperlinkUpdate is a function which goes to the URL corresponding to the 
     * link clicked inside the editorPane
     * @param e 
     * @Override
     */
    public void hyperlinkUpdate(HyperlinkEvent e){
    	if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
    		myView.setPage(e.getURL());
    	}
    }	

	/** keyTyped is an auto generated method which comes with keyListener
	 * @param e 
	 * @Override
	 */
    public void keyTyped(KeyEvent e){
    }
     
    /** keyPressed is an auto generated method which comes with keyListener 
     * @param e 
	 * @Override
     */
    public void keyPressed(KeyEvent e){
    }
     
    /** Handle the key released event from the text field. 
     * @param e 
	 * @Override
     */
    public void keyReleased(KeyEvent e){
    	if (e.getKeyCode() == KeyEvent.VK_ENTER){
    		String address = myView.getAddress();
    		try{
    			URL url = new URL(address);
    			myView.setPage(url);
    		}catch(MalformedURLException ex){
    			myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    		}		
		}	
    }
     
    /** Handle the button click. */
    public void actionPerformed(ActionEvent e){
    }


	/** Main method, calls the constructor and runs the program
     * @param args 
     */
	public static void main(String[] args) {
		new Controller();
	}


}