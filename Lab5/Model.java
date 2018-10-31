import java.net.URL;
import java.util.*;

/**
 *
 * @author alicekarnsund
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

	/** 
	 * @param urlIn
	 */
	public void addAddress(URL urlIn){
		while(currentIdx<addressList.size()-1 && currentIdx!=-1){
			addressList.remove(addressList.size()-1);
		}
		addressList.add(urlIn);
		forward();
	}

	public ArrayList<URL> getAddressList(){
		return addressList;
	}

	public boolean hasNext(){
		return currentIdx < addressList.size()-1;
	}

	public boolean hasPrevious(){
		return currentIdx > 0;
	}

	public URL nextAddress(){
		return addressList.get(currentIdx+1);
	}

	public URL previousAddress(){
		return addressList.get(currentIdx-1);
	}

	public void forward(){
		currentIdx++;
	}

	public void back(){
		currentIdx--;
	}

	public void indexOf(URL urlIn){
		currentIdx = addressList.indexOf(urlIn);
	}




}	