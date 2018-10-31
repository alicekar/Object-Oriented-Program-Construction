import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.event.*;
import java.util.*;
import java.net.HttpURLConnection;

/**
 *
 * @author alicekarnsund
 * 
 */	
public class Controller implements HyperlinkListener, ActionListener, KeyListener{
	View myView = new View(this);
	Model myModel = new Model();

	/** Constructor */
	public Controller(){
        try{
            hyperlinkUpdate(new HyperlinkEvent(this, 
                    HyperlinkEvent.EventType.ACTIVATED, new URL("http://kth.se")));
		}catch (MalformedURLException e){
			myView.errorMessage("There is something wrong with the home page");
        } 
	}

 	/** The close-button closes the frame when being clicked
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
    		myModel.addAddress(e.getURL());
    		myView.setPage(e.getURL());
    		updateButtons();
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
     
    /** The function keyReleased converts the string in the textfield to an URL
     * when "enter" is pressed and then the URL is being entered with the goTo 
     * method in View2 if the response code == 200. If the URL is invalid, 
     * an error message is shown.
     * @param e 
     * @Override
     */
    public void keyReleased(KeyEvent e){
    	if (e.getKeyCode() == KeyEvent.VK_ENTER){
    		String address = myView.getAddress();
    		try{
    			URL url = new URL(address);
    			int code = myModel.checkResponseCode(url);
    			// if code == 200 below happens 
    			myModel.addAddress(url);
    			myView.setPage(url);
    			updateButtons();
    			
    		}catch(MalformedURLException ex){
    			myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    		}catch(IOException ie){  // If code /= 200 this happens
    			myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    		}		
		}
    }
     
    /** The function actionPerformed notices if one has clicked the back or 
     * forward button and then enters the matching site, or else it enters the
     * url choosen in the history list.
     * @param e 
     */
    public void actionPerformed(ActionEvent e){
    	if(e.getActionCommand()=="  <  "){
    		URL previous = myModel.previousAddress();
    		myView.setPage(previous);
    		myModel.back();
    		updateButtons();
    	}
    	if(e.getActionCommand()=="  >  "){
    		URL next = myModel.nextAddress();
    		myView.setPage(next);
    		myModel.forward();
    		updateButtons();
    	}
    	else{
    		try{URL url = new URL(e.getActionCommand());
    			myModel.indexOf(url);
    			myView.setPage(url);
    			updateButtons();
    		}catch(MalformedURLException uex){}
    	}
    }

    /** The function updateButtons enables the back button if there has been 
     * websites visited before, enables the forward button if one is visting a 
     * previous website and enables the menu button if more than one website 
     * (the home page) has been visted.
     */
    public void updateButtons(){
    	myView.enableForward(myModel.hasNext());
    	myView.enableBack(myModel.hasPrevious());
    	myView.enableMenu(myModel.hasPrevious() || myModel.hasNext());
    	if(myModel.hasPrevious() || myModel.hasNext()){
    		myView.updateMenu(myModel.getAddressList());
    	}
    }

	/** Main method, calls the constructor and runs the program
     * @param args 
     */
	public static void main(String[] args) {
		new Controller();
	}


}