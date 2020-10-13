/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class computerPlay {
    
    /** Gives the next computer play as an integer
     *
     * @param difficulty The computer difficulty
     * @param history The past game history of the player
     * @return The computers next play
     */
    public static int computerThrow(String difficulty, List<Integer> history) {
        switch (difficulty) {
            case "easy":
                return easy();
            case "normal":
                return normal(history);
            default:
                break;
        }
        
        return 0;
        
    }
    
    //Computers move, during easy mode
    private static int easy() {
        System.out.println("\nEasy"); 
        int choiceNum = (int) ((Math.random() * 3) + 1);

        return choiceNum;
    }
    
    //Computers move, during hard mode
    private static int normal(List<Integer> history) {
        System.out.println("\nNormal"); 
        int choiceNum;
        
        // en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm //
        if (history.size() > 8) {
            
            String historyString = "";
            for (int num : history) {
                historyString += num;
                
            }
            
                                           // Rock, Paper, Scissors  
            int[] playOccurences = new int[] {0, 0, 0}; 
            
            //Checks for a match and stops at the biggest size it finds a match at
            for(int j = history.size() - 5; j >= 2; j--){
                
                boolean matched = false;
                for(int k = 0; k < (historyString.length() - j); k++) {
                    System.out.println("\n" + historyString.substring(k, k + j));
                    System.out.println(historyString.substring(historyString.length() - j, historyString.length()));
                    if(historyString.substring(k, k + j).equals(historyString.substring(historyString.length() - j, historyString.length()))) {
                        matched = true;
                        System.out.println("Match");
                        System.out.println("Nest move guess: " + historyString.substring(k + j, k + j + 1));

                        switch (Integer.parseInt(historyString.substring(k + j, k + j + 1))) {
                            case 1:
                                playOccurences[0] += 1;
                                break;
                            case 2:
                                playOccurences[1] += 1;
                                break;
                            case 3:
                                playOccurences[2] += 1;
                                break;
                            default:
                                break;

                        }
                    }        
                }
                
                if(matched == true) {
                    break;
                    
                }
            }
            
            choiceNum = mostCommonCase(playOccurences);
            System.out.println(Arrays.toString(playOccurences));
        }
        
        else {
            choiceNum = (int) ((Math.random() * 3) + 1);
        }
        
        return choiceNum;
        
    }
    
    //Returns the winning move when the players mode is guessed
    private static int getBestMove(int throwChoice) {
        switch (throwChoice) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 1;
            default:
                return 0;
        }
        
        
    }
    
    //Returns the most common of the players guessed next move
    private static int mostCommonCase(int[] moveProbability) {
        System.out.println("Probabilities: " + Arrays.toString(moveProbability));
        if(moveProbability[0] > moveProbability[1] && moveProbability[0] > moveProbability[2]) {
            return getBestMove(1);
        }
        else if(moveProbability[1] > moveProbability[2] && moveProbability[1] > moveProbability[0]) {
            return getBestMove(2);
        }
        else if(moveProbability[2] > moveProbability[0] && moveProbability[2] > moveProbability[1]) {
            return getBestMove(3);
        }
        else{
            return (int) ((Math.random() * 3) + 1);
        }
    }
}
