//package lab5;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.event.*;

/**
 *
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The controller class implements 3 types of listeners that are necessary for
 * interactions with the program. It contains the functions that translates the
 * users interactions with myView into actions 
 */	
public class Controller implements HyperlinkListener, ActionListener, KeyListener{
    private View myView = new View(this);
    private Model myModel = new Model();

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
     * @param closeIn the close button 
     * @param frameIn the frame where all is set up
     */
    public void actionClose(JButton closeIn, JFrame frameIn){
	closeIn.addActionListener(e -> frameIn.dispose());
    }

    /** hyperlinkUpdate is a function which goes to the URL corresponding to the 
     * link clicked inside the editorPane
     * @param e event when a hyperlink is klicked
     */
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e){
    	if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            myModel.addAddress(e.getURL());
            myView.setPage(e.getURL());
            updateButtons();
    	}
    }	

    /** keyTyped is an auto generated method which comes with keyListener
     * @param e key event when a key on the keyboard is typed 
     */
    @Override
    public void keyTyped(KeyEvent e){
    }
     
    /** keyPressed is an auto generated method which comes with keyListener 
     * @param e key event when a key on the keyboard is pressed
     */
    @Override
    public void keyPressed(KeyEvent e){
    }
     
    /** The function keyReleased converts the string in the textfield to an URL
     * when "enter" is pressed and then the URL is being entered with the goTo 
     * method in View2 if the response code == 200. If the URL is invalid, 
     * an error message is shown.
     * @param e key event when a key on the keyboard is released
     */
    @Override
    public void keyReleased(KeyEvent e){
    	if (e.getKeyCode() == KeyEvent.VK_ENTER){
    		String address = myView.getAddress();
    		try{
    			URL url = new URL(address);
    			int code = myModel.checkResponseCode(url);
    			if(code == 200){
    				myModel.addAddress(url);
    				myView.setPage(url);
    				updateButtons();
    			}
    			else{
    				myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    			}
    		}catch(MalformedURLException ex){
    			myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    		}catch(IOException ie){  
    			myView.errorMessage("The URL you have entered is invalid! Please "
                    + "try again");
    		}		
	}
    }
     
    /** The function actionPerformed notices if one has clicked the back or 
     * forward button and then enters the matching site, or else it enters the
     * url choosen in the history list.
     * @param e an action is made, i.e a button is clicked
     */
    @Override
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
     * @param args to run the program
     */
	public static void main(String[] args) {
		new Controller();
	}


}