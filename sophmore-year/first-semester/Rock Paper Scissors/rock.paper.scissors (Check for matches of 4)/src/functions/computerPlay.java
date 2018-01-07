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
            // 2332
            // 132323321223322
            
            String historyString = "";
            for (int num : history) {
                historyString += num;
            }
            
//            System.out.println(historyString.substring(historyString.length() - 5, historyString.length() - 1));
//            System.out.println(historyString.substring(0, 0 + 4));
            
                                           // Rock, Paper, Scissors  
            int[] playOccurences = new int[] {0, 0, 0}; 
            for(int k = 0; k < (historyString.length() - 4); k++) {
                System.out.println("\n" + historyString.substring(k, k + 4));
                System.out.println(historyString.substring(historyString.length() - 4, historyString.length()));
                if(historyString.substring(k, k + 4).equals(historyString.substring(historyString.length() - 4, historyString.length()))) {
                    System.out.println("Match");
                    System.out.println("Nest move guess: " + historyString.substring(k + 4, k + 5));
                    
                    
                    
                    switch (Integer.parseInt(historyString.substring(k + 4, k + 5))) {
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
        if(moveProbability[0] > moveProbability[1]) {
            return getBestMove(moveProbability[0]);
        }
        else if(moveProbability[1] > moveProbability[2]) {
            return getBestMove(moveProbability[1]);
        }
        else if(moveProbability[2] > moveProbability[0]) {
            return getBestMove(moveProbability[2]);
        }
        else{
            return (int) ((Math.random() * 3) + 1);
        }
    }
}
