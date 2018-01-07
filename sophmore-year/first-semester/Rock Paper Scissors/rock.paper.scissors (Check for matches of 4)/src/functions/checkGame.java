/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class checkGame {
    
    /** Will return the status of the game
     *
     * @param player The play the player makes
     * @param computer The play the computer makes
     * @return Returns the value, "Tie" "Win" or "Loss" depending on the result
     */
    public static String winLose(int player, int computer) {
        if(player == computer) {
            System.out.println("Tie. -->    Player: " + switchPlayType.numToString(player) + "  Computer: " + switchPlayType.numToString(computer));
            return "Tie";
        }
        
        else if((player == 1 && computer == 3) || (player == 2 && computer == 1) || (player == 3 && computer == 2)) {
            System.out.println("Win. -->    Player: "  + switchPlayType.numToString(player) + "  Computer: " + switchPlayType.numToString(computer));
            return "Win";
        }
        
        else {
            System.out.println("Loss. -->    Player: " + switchPlayType.numToString(player) + "  Computer: " + switchPlayType.numToString(computer));
            return "Loss";
        }
        
    }
      
}
