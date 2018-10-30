import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;


/**
 *
 * @author alicekarnsund
 * BRANCH L5
 * The class View2 creats a frame and adds all its contents, it also contains
 * the most basic functions for the browser
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
	//private Container container = getContentPane();   // Needed to add thing to the frame

	/** Constructor */ 
	public View(Controller controllerIn){
		super("The Web Browser");

		/* Set up the top panel and the editor with the components*/
		backButton.setEnabled(false);
		forwardButton.setEnabled(false);
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
	 *  @return
	 */
	public String getAddress(){
		String address = myTextField.getText();
		return address;
	}

	/** Shows the current URL in the editorPane
	 *
	 */
	public void setPage(URL urlIn){
		try{
			myPane.setPage(urlIn);
		}catch(IOException e){}
	}

	/** The function enableForward enables the forward button if the user  
     * has gone back to an earlier website either by using the back button or by
     * the history menu.
     * @param booleanIn 
	 */
	public void enableForward(boolean booleanIn){
		forwardButton.setEnabled(booleanIn);
	}

    /** The function enableBack enables the back button if there has been 
     * one or more sites visited before the current one.
     * @param booleanIn 
     */
	public void enableBack(boolean booleanIn){
		backButton.setEnabled(booleanIn);
	}

    /** The function enableMenu enables the history menu if there has been sites
     * visited before the current one.
     * @param booleanIn 
     */
    public void enableMenu(boolean booleanIn){
    	menuText.setEnabled(booleanIn);
    }


	public void errorMessage(String messageIn){
		JOptionPane.showMessageDialog(this, messageIn, "Warning", 
			JOptionPane.ERROR_MESSAGE);
	}

}