//package lab5;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The class View2 creats a frame and adds all its contents, it also contains
 * graphical functions 
 */	
public class View extends JFrame{
    private JPanel topPanel = new JPanel();
    private JTextField myTextField = new JTextField(55);
    private JButton closeButton = new JButton("CLOSE");
    private JButton backButton = new JButton("  <  ");
    private JButton forwardButton = new JButton("  >  ");
    private JEditorPane myPane = new JEditorPane();
    private JMenuBar myMenu = new JMenuBar();
    private JMenu menuText = new JMenu(" HISTORY ");
    private JMenuItem menuItem = new JMenuItem("Item");
    private Controller controller;

    /** Constructor 
     * @param controllerIn controller class to listen the interactions with View
     */ 
    public View(Controller controllerIn){
	super("The Web Browser");
	controller = controllerIn;

	/* Set up the top panel and the editor with the components*/
	backButton.setEnabled(false);
	backButton.addActionListener(controllerIn);
	forwardButton.setEnabled(false);
	forwardButton.addActionListener(controllerIn);
	myMenu.add(menuText);
	menuText.add(menuItem);
	menuText.setEnabled(false);
	myTextField.addKeyListener(controllerIn);

	topPanel.add(backButton);
	topPanel.add(forwardButton);
	topPanel.add(myMenu);
	topPanel.add(myTextField);
	topPanel.add(closeButton);

	myPane.setContentType("text/html");
	myPane.setEditable(false);
	myPane.addHyperlinkListener(controllerIn);
	controllerIn.actionClose(closeButton, this);

	/* Set up the frame and its components*/
	add(topPanel, BorderLayout.NORTH);
	add(new JScrollPane(myPane), BorderLayout.CENTER);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setSize(screenSize);
	setResizable(false);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** Returns the text in the text field as a string
     *  @return the text in the text field
     */
    public String getAddress(){
	String address = myTextField.getText();
	return address;
    }

    /** Shows the current URL in the editorPane
     * @param urlIn the url of a given website
     */
    public void setPage(URL urlIn){
    	try{
        	myPane.setPage(urlIn);
	}catch(IOException e){}
    }

    /** The function enableForward enables the forward button if the user  
     * has gone back to an earlier website either by using the back button or by
     * the history menu.
     * @param booleanIn boolean value to enable the forward button
     */
    public void enableForward(boolean booleanIn){
	forwardButton.setEnabled(booleanIn);
    }

    /** The function enableBack enables the back button if there has been 
     * one or more sites visited before the current one.
     * @param booleanIn boolean value to enable the back button
     */
    public void enableBack(boolean booleanIn){
	backButton.setEnabled(booleanIn);
    }

    /** The function enableMenu enables the history menu if there has been sites
     * visited before the current one.
     * @param booleanIn boolean value to enable the Menu
     */
    public void enableMenu(boolean booleanIn){
    	menuText.setEnabled(booleanIn);
    }

    /** The function updateMenu adds the current url list to the menu and adds a
     * separate ActionListener to each item in the menu
     * @param listIn the collected list of visited urls
     */ 
    public void updateMenu(ArrayList<URL> listIn){
    	JMenuItem item;
    	menuText.removeAll();
    	for(URL url: listIn){
    		item = new JMenuItem(url.toString());
    		item.addActionListener(controller);
    		menuText.add(item);
    	}
    }

    /** The function errorMessage shows a new window with an error message if 
     * there is something wrong with the current link or if there is no internet
     * @param messageIn to show a message in an option pane
     */
	public void errorMessage(String messageIn){
		JOptionPane.showMessageDialog(this, messageIn, "Warning", 
			JOptionPane.ERROR_MESSAGE);
	}

}