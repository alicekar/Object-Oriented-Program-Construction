import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author alicekarnsund
 * BRANCH L5
 * The class View2 creats a frame and adds all its contents, it also contains
 * the most basic functions for the browser
 */	
public class View extends JFrame{
	//private JLabel myLabel = new JLabel("Test");
	private JPanel topPanel = new JPanel();
	private JTextField myTextField = new JTextField("Enter Address");
	private JButton myClose = new JButton("Close");
	public JEditorPane myPane = new JEditorPane();
	//private Container container = getContentPane();   // Needed to add thing to the frame
	//Controller myController = new Controller();

	/** Constructor */ 
	public View(Controller controllerIn){
		super("The Web Browser");

		controllerIn.actionClose(myClose, this);

		/* Set up the top panel and the editor with the components*/
	    //topPanel.add(myLabel);
		topPanel.add(myTextField);
		topPanel.add(myClose);
		myPane.setContentType("text/html");
		myPane.setEditable(false);
		//myPane.setBorder(BorderFactory.createLineBorder(Color.red));  // To test the position of panel2

		/* Set up the frame and its components*/
		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(myPane), BorderLayout.CENTER);

		/* Set up the Frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 300));
		pack();
		setVisible(true);
	}


}