/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package you.dont.know.jack.elements;

import java.util.ArrayList;

/**
 *
 * @author Lilly
 */
public class Question {
    private String q_type;
    private String question;
    private ArrayList<String> a_Choices = new ArrayList<>();
    private String answer;
    
    /**
     * Sets up a question and its values
     * @param t Question type
     * @param q Question
     * @param c List of choices
     * @param a Answer
     */
    public Question(String t, String q, ArrayList<String> c, String a) {
        q_type = t;
        question = q;
        answer = a;
        
        if(!("".equals(c.get(0)))) {
            for (String s : c) {
                a_Choices.add(s);
                
            }
        }
    }
    
    /**
     * Gets the type of question
     * @return Type
     */
    public String getType() {
        return q_type;
    }
    
    /**
     * Gets the question
     * @return Question
     */
    public String getQuestion() {
        switch(q_type) {
            case("This or that"):      
                return ("Is " + question + " a " + a_Choices.get(0) + " or a " + a_Choices.get(1));
            case("Anagram"):      
                return ("\"" + question + "\" is an anagram for");
            case("Multiple choice"):      
                return (question);
            case("Fill in the blank"):
                return (question);
            default:
                return ("Type not found");
        }
    }
    
    /**
     * Gets the list of choices
     * @return Choice List
     */
    public ArrayList<String> getChoices() {
        return a_Choices; 
    }
    
    /**
     * Gets the answer
     * @return Answer
     */
    public String getAnswer() {
        return answer;
    }
    
    
    public int getWinnings() {
        switch(q_type) {
            case ("This or that"): {
                return 2000;
            }            
            case ("Anagram"): {
                return 3000;
            }
            case ("Multiple choice"): {
                return 1000;
            }
            case ("Fill in the blank"): {
                return 2000;
            }
            default: {
                System.out.println("Question type not Valid: " + q_type);
                return 0;
            }
        }
    }
    
}