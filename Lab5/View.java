import javax.swing.*;
import java.awt.*;

/**
 *
 * @author alicekarnsund
 * heeeej!!!
 */	
public class View extends JFrame{
	private JLabel myLabel = new JLabel("Test");
	private JPanel myPanel = new JPanel();
	private JTextField myTextField = new JTextField("Enter Address");
	private JButton myExit = new JButton("Exit");
	private JEditorPane myPane = new JEditorPane();
	private JScrollPane myScroll = new JScrollPane();


	//private Container container = getContentPane();   // Needed to add thing to the frame


	public View(){
		super("The Web Browser");

		/* Set up the first panel and its components*/
		myPanel.add(myTextField);
		myPanel.add(myExit);
		myPane.add(myLabel);
		myPane.setBorder(BorderFactory.createLineBorder(Color.red));  // To test the position of panel2
		//myPane.add(myScroll);

		/* Set up the frame and its components*/
		add(myPanel, BorderLayout.NORTH);
		//add(myPane, BorderLayout.CENTER);
		add(new JScrollPane(myPane), BorderLayout.CENTER);

		/* Set up the Frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 300));
		pack();
		setVisible(true);
	}


	/** Main method, calls the constructor and runs the program
     * @param args */
	public static void main(String[] args) {
		new View();
	}

}