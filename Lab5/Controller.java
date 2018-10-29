import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author alicekarnsund
 * 
 */	
public class Controller{

	View myView = new View(this);


	public Controller(){
		try{
			myView.myPane.setPage(new URL("https://www.kth.se"));
		}
		catch(java.io.IOException ioe){
			System.out.println("IOException while loading KTH homepage");
		}

	}

 	/** The close-button closes when being clicked
     * @param closeIn
     * @param frameIn */
	public void actionClose(JButton closeIn, JFrame frameIn){
		closeIn.addActionListener(e -> frameIn.dispose());
	}

	/** Main method, calls the constructor and runs the program
     * @param args */
	public static void main(String[] args) {
		new Controller();
	}


}