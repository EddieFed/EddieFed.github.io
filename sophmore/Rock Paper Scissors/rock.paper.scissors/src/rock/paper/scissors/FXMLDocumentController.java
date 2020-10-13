/*
 */
package rock.paper.scissors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML private Label winLoss;
    @FXML private AnchorPane startPane, gamePane;
    @FXML private ToggleGroup clickyButton, difficultyClicky;
    @FXML private ImageView playerImage, computerImage;
    private List<Integer> throwHistory = new ArrayList<>();
    
    
    @FXML private void handleStart(ActionEvent event) {
        startPane.setVisible(false);
        gamePane.setVisible(true);
    }
    
    
        //After player selects play, throw it against a play by the computer
    @FXML private void throwPlay(ActionEvent event) {
        if(clickyButton.getSelectedToggle() != null) {clickyButton.getSelectedToggle().toString().substring((clickyButton.getSelectedToggle().toString().indexOf("=") + 1), clickyButton.getSelectedToggle().toString().indexOf(","));}
        String selectedButton;
        int selectedButtonNum = Integer.parseInt(numToThrow(0, selectedButton));
        //        System.out.println("Computer: " + selectedButton);
        
        
        playerImage.setImage(new Image("/resources/" + selectedButton + ".png"));
        
        
        
        //Starts computer Throw
        int compSelect = computerThrow(difficultyClicky.getSelectedToggle().toString().substring((difficultyClicky.getSelectedToggle().toString().indexOf("=") + 1), difficultyClicky.getSelectedToggle().toString().indexOf(",")));
//        System.out.println("Computer: " + numToThrow(compSelect, null));
        
        
        /* Debugging */ if(compSelect == 0) {return;}
        throwHistory.add(selectedButtonNum);
        System.out.println("player: " + Arrays.toString(throwHistory.toArray()));
        winLoss.setText(winLose(selectedButtonNum, compSelect));
    }
    
    
        //Throws The computers move, 
    private int computerThrow(String difficulty) {
        switch (difficulty) {
            case "easy":
                System.out.println("\nEasy\n");
                
                int choiceNum = (int) ((Math.random() * 3) + 1);
                String choice = numToThrow(choiceNum, null);
                computerImage.setImage(new Image("/resources/" + choice + ".png"));
                
                return choiceNum;
                
                
                
            case "normal":
                System.out.println("\nNormal\n");
                
                if(throwHistory.size() > 5) {
                    System.out.println("Smart");
                    
                    for(int i = 0; i < 5; i++) {
                        
                    }
                    
                }
                
                else {
                    System.out.println("Not so smart");
                    return computerThrow("easy");
                }
                
                
                
                
                
                
                
                
                
                
                
                break;
            case "hard":
                System.out.println("\nHard\n");
                break;
            default:
                break;
        }
        
        return 0;
    }
    
        //Translates the corresponding number to the throw (Rock, paper, scissors)
    private String numToThrow(int num, String name) {
        switch (num) {
            case 1:
                return "rock";
            case 2:
                return "paper";
            case 3:
                return "scissors";
            default:
                break;
        }
        
        switch (name) {
            case "rock":
                return "1";
            case "paper":
                return "2";
            case "scissors":
                return "3";
            default:
                return null;
        }
        
    }
    
    
    private String winLose(int player, int computer) {
        if(player == computer) {
            System.out.println("Tie. -->    Player: " + numToThrow(player, null) + "  Computer: " + numToThrow(computer, null));
            return "Tie";
        }
        
        else if((player == 1 && computer == 3) || (player == 2 && computer == 1) || (player == 3 && computer == 2)) {
            System.out.println("Win. -->    Player: " + numToThrow(player, null) + "  Computer: " + numToThrow(computer, null));
            return "Win";
        }
        
        else {
            System.out.println("Loss. -->    Player: " + numToThrow(player, null) + "  Computer: " + numToThrow(computer, null));
            return "Loss";
        }
    }
    
    
    @FXML private void refreshImage(ActionEvent event) {}
    @Override public void initialize(URL url, ResourceBundle rb) {
        winLoss.setText("");
        startPane.setVisible(false);
        gamePane.setVisible(false);  
        
        override();
    }    
    
    public String override;
    public void override() {
        
    }
    
    public void overrideVars(String play) {
        override = play;
        
        
    }
    
}
