//package lab5;

import java.net.URL;
import java.util.*;
import java.net.*;
import java.io.IOException;

/**
 *
 * @author alicekarnsund
 * @author elinsamuelsson
 *
 * The Model class contains the hidden functions, such as keeping tranck of number
 * of websites visited and at which website in the addressList we are at the moment
 */
public class Model{
    ArrayList<URL> addressList;
    int currentIdx = -1;

    /** Constructor
     * Creates a new ArrayList where all the visited websites will be stored
     */
    public Model(){
	addressList = new ArrayList<URL>();
    }

    /** The function addAdress adds a new address to the addressList and calls the 
     * forward function to increase the index by one
     * @param urlIn url of a given new website
     */
    public void addAddress(URL urlIn){
        while(currentIdx<addressList.size()-1 && currentIdx!=-1){
            addressList.remove(addressList.size()-1);
	}
        addressList.add(urlIn);
	forward();
    }

    /** The function getAddressList, returns the whole list of visited addresses
     * @return the whole address list
     */
    public ArrayList<URL> getAddressList(){
	return addressList;
    }

    /** The function hasNext, returns true if one has visited and address 
     * after the current one
     * @return true if there is a website that has been visited after this one
     */
    public boolean hasNext(){
	return currentIdx < addressList.size()-1;
    }

    /** The function hasPrevious, returns true if one has visited and address 
     * prior to the current one
     * @return true if there is a website that has been visited prior this one
     */
    public boolean hasPrevious(){
	return currentIdx > 0;
    }

    /** The function nextAddress, returns the address visited after 
     * @return the next url in the address list
     */
    public URL nextAddress(){
	return addressList.get(currentIdx+1);
    }

    /** The function previousAddress, returns the address visited earlier
     * @return the previous url in the address list
     */
    public URL previousAddress(){
	return addressList.get(currentIdx-1);
    }

    /** The forward-function, increases the current index in the addressList
     */
    public void forward(){
	currentIdx++;
    }

    /** The back-function, decreases the current index in the addressList
     */
    public void back(){
	currentIdx--;
    }

    /** The function indexOf, finds the index in the addressList of an URL
     * @param urlIn any url one wish to find the position of in the address list
     */
    public void indexOf(URL urlIn){
	currentIdx = addressList.indexOf(urlIn);
    }

    /** The checkResponseCode function checks the response code of the url
     * to make sure it is valid, ie == 200. Otherwise an exception uis thrown
     * and an error message is shown.
     * @param urlIn url to see if it is valid
     * @return the response code
     * @throws IOException caught in controller when testing a bad url
     */
    public static int checkResponseCode(URL urlIn) throws IOException{
       	HttpURLConnection httpCon = (HttpURLConnection)urlIn.openConnection();
	System.out.println("Response code is " + httpCon.getResponseCode());
	return httpCon.getResponseCode();
    }



}	