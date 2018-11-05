//package projectfinal;

/**
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The model class, which handles all encoding and decoding of chat messages 
 */
public class Model{

    /**
     * The constructor
     */
    public Model(){
    }

    /**
     * Returns the color as a string if it is in the right format, otherwise 
     * returns "#000000", which corresponds to the color black. 
     *
     * @param  colorIn the input color as a string
     * @return color a valid color as a string
     */
    public String validateColor(String colorIn){

        String color = colorIn;

        // Check if valid color 
        if (color.length()!=7){
            color = "#000000";
        }else if(!color.substring(0, 1).equals("#")){
            color = "#000000";
        }else{
            for (int i=1; i<=6; i++){
                if(!(color.substring(i, i+1).equals("0") || 
                    color.substring(i, i+1).equals("1") || 
                    color.substring(i, i+1).equals("2") || 
                    color.substring(i, i+1).equals("3") || 
                    color.substring(i, i+1).equals("4") || 
                    color.substring(i, i+1).equals("5") || 
                    color.substring(i, i+1).equals("6") || 
                    color.substring(i, i+1).equals("7") || 
                    color.substring(i, i+1).equals("8") || 
                    color.substring(i, i+1).equals("9") || 
                    color.substring(i, i+1).equals("A") || 
                    color.substring(i, i+1).equals("B") || 
                    color.substring(i, i+1).equals("C") || 
                    color.substring(i, i+1).equals("D") || 
                    color.substring(i, i+1).equals("E") || 
                    color.substring(i, i+1).equals("F"))){

                    color = "#000000";
                }
            }
        }

        return color;
    }

    /**
     * Modification of a message, so that we will not mess up the decoding 
     * from XML to message
     *
     * @param  textIn the input string
     * @return modText the modified string
     */
    public String modifyText(String textIn){
        String modText = textIn.replace(">", "&gt");
        modText = modText.replace("<", "&lt");
        return modText;
    }

    /**
     * Reverse modification of a message, to be able to display "less" and 
     * "greater than" in the 
     * chat
     *
     * @param  textIn the previously modified input string
     * @return modText the reversely modified (i.e. original) string
     */
    public String reverseModifyText(String textIn){
        String revText = textIn.replace("&gt", ">");
        revText = revText.replace("&lt", "<");
        return revText;
    }

    /**
     * Turns the message information (text, name, color, bold, italics) into an 
     * XML encoded string
     *
     * @param  textIn the message as a string
     * @param  nameIn the name as a string
     * @param  colorIn the color as a string
     * @param  boldIn a boolean indicating if the message is bold or not
     * @param  italicsIn a boolean indicating if the message is in italics or not
     * @return theXml a summarizing XML encoded string
     */
    public String stringToXml(String textIn, String nameIn, String colorIn, 
            boolean boldIn, boolean italicsIn){
        String theXml = "<message sender=\"" + nameIn + "\"><text color=\"" + 
                colorIn + "\">";
        if (boldIn && italicsIn){
                theXml += "<fetstil><kursiv>" + modifyText(textIn) + 
                        "<\\kursiv><\\fetstil>";
        } else if (italicsIn){
                theXml += "<kursiv>" + modifyText(textIn) + "<\\kursiv>";
        } else if (boldIn){
                theXml += "<fetstil>" + modifyText(textIn) + "<\\fetstil>";
        }else{
                theXml += modifyText(textIn);
        }
        theXml += "<\\text><\\message>";
        return theXml;
    }

    /**
     * Extracts relevant information from an XML encoded string and returns an 
     * Info object, with the fields text, color, bold, italics, XMLok
     *
     * @param  xmlIn a summarizing XML coded string
     * @return info an Info object containing all information about how to 
     * display this message
     */
    public Info xmlToString(String xmlIn){
        Info info = new Info();
        try{
            String[] parts = xmlIn.split(">|<");
            int n = parts.length;

            String[] part1 = parts[1].split(" |=");
            String[] part3 = parts[3].split(" |=");
            if(part1[0].equals("message") && parts[n-1].equals("\\message")){
                if (part1.length > 1){
                    info.setText(part1[2].replace("\"", ""));
                }
                info.addText(": ");
                if(part3[0].equals("text") && parts[n-3].equals("\\text")){
                    if (part3.length > 1){
                        String colorStr = part3[2].replace("\"", "");
                        info.setColor(validateColor(colorStr));
                    }
                    if (n == 8){
                        info.addText(reverseModifyText(parts[4]));
                        info.setOK(true);
                    } else if (n == 12){
                        if(parts[5].equals("kursiv") && parts[n-5].equals
                            ("\\kursiv")){
                                info.addText(reverseModifyText(parts[6]));
                                info.setItalics(true);
                                info.setOK(true);
                        }else if(parts[5].equals("fetstil") && parts[n-5].equals
                            ("\\fetstil")){
                                info.addText(reverseModifyText(parts[6]));
                                info.setBold(true);
                                info.setOK(true);
                        }
                    } else if (n == 16){
                        if(parts[5].equals("fetstil") && parts[n-5].equals
                            ("\\fetstil") && parts[7].equals("kursiv") && 
                            parts[n-7].equals("\\kursiv")){
                                info.addText(reverseModifyText(parts[8]));
                                info.setItalics(true);
                                info.setBold(true);
                                info.setOK(true);
                        }
                    }
                }
            }
        }catch(Exception e){
                System.out.println("Something went wrong when decoding the XML");
        }

        return info;
    }
}
