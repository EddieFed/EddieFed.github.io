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
public class switchPlayType {
    
    /** Converts between number and move
     *
     * @param num The number which is to be converted to a move
     * @return The String value of the move [rock, paper, scissors] or null if not a valid number
     */
    public static String numToString(int num) {
        switch (num) {
            case 1:
                return "rock";
            case 2:
                return "paper";
            case 3:
                return "scissors";
            default:
                return null;
        }
        
    }
    
    /** Converts between number and move
     *
     * @param str The String value to be converted to a number [rock, paper, scissors]
     * @return The number which represents the string, otherwise 0 is returned
     */
    public static int stringToNum(String str) {
        switch (str) {
            case "rock":
                return 1;
            case "paper":
                return 2;
            case "scissors":
                return 3;
            default:
                return 0;
        }                
    }
}
