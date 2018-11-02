import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class Model{

	public Model(){

	}


	public String stringToXml(String textIn, String nameIn, String colorIn, boolean boldIn, boolean italicIn){
		String theXml = "<message sender=\"" + nameIn + "\"><text color=\"" + colorIn + "\">";
		if (boldIn){
			if (italicIn && boldIn){
				theXml += "<fetstil><kursiv>" + modifyText(textIn) + "<\\kursiv><\\fetstil>";
			}else if(boldIn){
				theXml += "<fetstil>" + modifyText(textIn) + "<\\fetstil>";	
			} else {
				theXml += "<kursiv>" + modifyText(textIn) + "<\\kursiv>";
			}
		}else{
			theXml += modifyText(textIn);
		}
		theXml += "<\\text><\\message>";
		return theXml;
	}

    private String modifyText(String aText){
		String modText = aText.replace(">", "&gt");
		modText = modText.replace(">", "&lt");
		return modText;
	}


	public List<String> getCleanString(String[] stringIn){
		List<String> allParts = Arrays.asList(stringIn);
		int nParts = allParts.size();
		List<String> cleanString = new ArrayList<String>();
		System.out.println(allParts);
		for(String s:allParts){
			if(s.equals("")){
			}else{
				System.out.println(s);
				cleanString.add(s);
			}
		}
		return cleanString;
	}

	public List<String> xmlToString(String xmlIn){
		// <message sender="A"><text color="#999999">Hej, jag vill vet hur detta ska redigeras<\text><\message>
		//<message sender="B"><text color="#999999">kul, jag vill ha italic<\text><\message>
		//<message sender="B"><text color="#999999">halå<\text><\message>
		//<message sender="B"><text color="#999999"><fetstil><kursiv>bold?<\kursiv><\fetstil><\text><\message>
		//<message sender="B"><text color="#999999"><fetstil>bara bold<\fetstil><\text><\message>
		String text = new String();
		String color = new String();
		String type1 = new String();
		String type2 = new String();
		List<String> info = new ArrayList<String>();
		try{
			String[] splitParts = xmlIn.split(">|<");
		
			List<String> parts = getCleanString(splitParts);
			int nParts = parts.size()-1;
			//String part = Arrays.toString(parts);
			
			System.out.println(parts);
			System.out.println(nParts);
			//String[] clean = Arrays.stream(parts).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
			
			
			String mess1 = parts.get(0);
			String mess2 = parts.get(nParts);
			String txt1 = parts.get(1);
			String txt2 = parts.get(nParts-1);
			
			System.out.println(txt1.substring(12, txt1.length()));
			
			if(mess1.substring(0,7).equals(mess2.substring(mess2.length()-7,mess2.length()))){
				System.out.println("JAA");
				if(txt1.substring(0,4).equals(txt2.substring(txt2.length()-4,txt2.length()))){
					System.out.println("mm");
					if(nParts == 4){
						text = parts.get(2);
						color = txt1.substring(12, txt1.length());
						info.add(text);
						info.add(color);
					}else if(nParts == 6){
						text = parts.get(3);
					    color = txt1.substring(12, txt1.length());
						type1 = parts.get(2);
						info.add(text);
						info.add(color);
						info.add(type1);
					}else if(nParts == 8){
						text = parts.get(4);
						color = txt1.substring(12, txt1.length());
						type1 = parts.get(2);
						type2 = parts.get(3);
						info.add(text);
						info.add(color);
						info.add(type1);
						info.add(type2);
						System.out.println(color);
				
					}else{
						System.out.println("There is something wrong with the XML or an empty"
							+"string was sent");
					}

				}
			}
		}catch(Exception e){
			System.out.println("There is something wrong with the XML");
		}

		System.out.println(info);
		
		return info;
	}



}