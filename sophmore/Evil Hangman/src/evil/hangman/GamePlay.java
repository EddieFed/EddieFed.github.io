package evil.hangman;

import java.util.ArrayList;


public class GamePlay {
    private Dictionary dict = new Dictionary();
    private String guesses = "";
    private String finalWord;
    
    private int length = 0;
    private int wrongGuesses = 0;
    
//    private String guesses = "A,E,I,O,U,Y,";
    
    /** Creates a blank GamePlay object
     *
     */
    public GamePlay() {
        
    }
    
    /** Sets the length
     *
     * @param length any integer
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     *
     * @return the value of length
     */
    public int getLength() {
        return length;
    }
    
    /** Adds a letter to the list of guesses
     *
     * @param letter any string value
     */
    public void addGuess(String letter) {
        guesses += letter.toUpperCase() + ",";
    }
    
    /**
     *
     * @return the value of guesses
     */
    public String getGuesses() {
        return guesses;
    }
    
    /**
     *
     * @return the full dictionary
     */
    public Dictionary getDictionary() {
        return dict;
    }
    
    /** Sets the final word to a random word of the remaining words left when the dict.getArrayNoMatch is empty
     *
     */
    public void checkForWord() {
        if (finalWord == null) {
            if (dict.getArrayNoMatch(guesses, length).isEmpty()) {
                ArrayList past = dict.getArrayNoMatch(guesses.substring(0, guesses.length() - 3), length);
                finalWord = (String) past.get((int) (Math.random() * past.size()));

                
            }
        }
        
        else {
            System.out.println("Final word: " + finalWord);
        }
    }
    
    public int checkForWrong() {
        
        // This is just to get around an error when testing, leave it
        if (wrongGuesses >= 6) {
            return 6;
        }
        
        if (finalWord == null) {
            wrongGuesses += 1;
        }
        else {
            if (!(finalWord.contains(guesses.substring(guesses.length() - 1, guesses.length())))) {
                wrongGuesses += 1;
            }
        }
        
        
        return wrongGuesses; 
    }
    
    public String getFinalWordLabel() {
        String labelString = "";
        if (finalWord != null) {
            for (int i = 0; i < finalWord.length(); i++) {
                if (guesses.contains(finalWord.substring(i, i + 1))) {
                    labelString += finalWord.substring(i, i + 1);
                }

                else {
                    labelString += "_";
                }
            }
        }
        
        else {
            for (int i = 0; i < length; i++) {
                labelString += "_";
            }
        }
        return labelString;
    }
    
    public String checkWin() {
        if (getFinalWordLabel().equals(finalWord)) {
            return "You Won!";
        }
        else if (guesses.length() >= 14) {
            
            
            return "Game over! You lost";
        }
        return "Guess again!";
    }
}
