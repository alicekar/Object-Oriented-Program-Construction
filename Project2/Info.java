//package projectfinal;

/**
 * @author alicekarnsund
 * @author elinsamuelsson
 * 
 * The info class, which stores information about a message and its format
 */
public class Info{

    private String text;
    private String color;
    private boolean bold;
    private boolean italics;
    private boolean XMLok;

    /**
     * The constructor
     */
    public Info(){
        text = "";
        color = "#000000";
        bold = false;
        italics = false;
        XMLok = false;
    }

    /**
     * Adds a String textIn to the already existing String text
     * @param textIn some text as a String
     */
    public void addText(String textIn){
        text += textIn;
    }

    /**
     * Returns a boolean indicating if the format is in bold or not
     * @return true if bold, else false
     */
    public boolean getBold(){
        return bold;
    }

    /**
     * Returns the color as a String
     * @return the color as a String
     */
    public String getColor(){
        return color;
    }

    /**
     * Returns a boolean indicating if the format is in italics or not
     * @return true if italics, else false
     */
    public boolean getItalics(){
        return italics;
    }

    /**
     * Returns a boolean indicating if the XML was valid or not
     * @return true if XML ok, else false
     */
    public boolean getOK(){
        return XMLok;
    }

    /**
     * Returns what to output in the chat (name: message) as a String
     * @return the output String
     */
    public String getText(){
        return text;
    }

    /**
     * Assign a boolean indicating if the format is in bold or not
     * @param boldIn true if bold, else false
     */
    public void setBold(boolean boldIn){
        bold = boldIn;
    }

    /**
     * Assign a String colorIn to the field color
     * @param colorIn the color as a String
     */
    public void setColor(String colorIn){
        color = colorIn;
    }

    /**
     * Assign a boolean indicating if the format is in italics or not
     * @param italicsIn true if italics, else false
     */
    public void setItalics(boolean italicsIn){
        italics = italicsIn;
    }

    /**
     * Assign a boolean indicating if the XML was valid or not
     * @param okIn true if XML ok, else false
     */
    public void setOK(boolean okIn){
        XMLok = okIn;
    }	

    /**
     * Assign a String textIn to the field text
     * @param textIn some text as a String
     */
    public void setText(String textIn){
        text = textIn;
    }

}

