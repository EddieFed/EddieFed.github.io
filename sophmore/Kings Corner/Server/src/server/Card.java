package server;

/**
 *
 * @author federmeyer4208
 */
public class Card {
    
    private String cSuit;
    private int cNumber;
    private String pathName;
    
    /** creates a reusable card object with a path
     *
     * @param cName the name of the card image
     */
    public Card(String cName) {
        pathName = "cards/" + cName + ".jpg";
        cSuit = cName.substring(0); // creates the suit of a card
        cNumber = Integer.parseInt(cName.substring(2)); // creates the value of a card       
        
    }
    
    /** sends the card value when called
     *
     * @return numeric value of the card
     */
    public int getCardNumber(){
        return cNumber;
        
    }
    
    /** sends the card suit when called
     *  A - Ace
     *  H - Heart
     *  S - Spade
     *  C - Club
     * 
     * @return suit of the card
     */
    public String getCardSuit() {
        return cSuit;
        
    }
    
    /** sends card path when called (location in cards image folder)
     *
     * @return path name
     */
    public String getCardPath() {
        return pathName;
        
    }
    
}
