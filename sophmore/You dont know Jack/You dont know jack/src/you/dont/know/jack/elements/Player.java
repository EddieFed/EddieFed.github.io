/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package you.dont.know.jack.elements;

/**
 * 
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class Player {
    private String name;
    private int qCorrect = 0;
    private int winnings = 0;
    
    /**
     * Creates a new player
     * @param n The name to recognize the player by
     */
    public Player(String n) {
        name = n;
    }
    
    /**
     * Adds a value to the total correct questions a user has answered
     */
    public void correct() {
        qCorrect += 1;
    }
    
    /**
     * Changes the player winnings value
     * @param operation The operation to modify the winnings value (+, -)
     * @param amount The amount to change the winnings by
     */
    public void winnings(String operation, int amount) {
        if(operation.equals("+")) {
            winnings += amount;
        }
        
        if(operation.equals("-")) {
            winnings -= amount;
        }
    }
    
    /**
     * Grabs the name of the player
     * @return Name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Grabs the amount of correct answers the player has answered
     * @return Correct answers
     */
    public int get_qCorrect() {
        return qCorrect;
    }
    
    /**
     * Grabs The players total winnings
     * @return Winnings
     */
    public int getWinnings() {
        return winnings;
    }
}
