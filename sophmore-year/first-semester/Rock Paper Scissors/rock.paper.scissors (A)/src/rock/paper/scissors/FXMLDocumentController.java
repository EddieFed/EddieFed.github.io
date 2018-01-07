/*
 */
package rock.paper.scissors;


import functions.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
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
    @FXML private AnchorPane gamePane;
    @FXML private ToggleGroup clickyButton, difficultyClicky;
    @FXML private Toggle easy, normal;
    @FXML private ImageView playerImage, computerImage;
    private List<Integer> throwHistory = new ArrayList<>();
    
    
    //On the play button click execute
    @FXML private void throwPlay(ActionEvent event) {
        play(false, 0);
        
        
        //Adds dummy data
        if (throwHistory.size() == 14) {
            for(int i = 0; i < 3; i++){
                play(true, (int) ((Math.random() * 3) + 1));
            }
        }
        
    }
    
    //The general calls to functions so that a round is run
    private void play(boolean override, int overrideValue) {
        int player = playerPlay(override, overrideValue);
        int computer = computerPlay();
        throwHistory.add(player);
        
        winLoss.setText(checkGame.winLose(player, computer));
        System.out.println(Arrays.toString(throwHistory.toArray()));
        
    }
    
    //Returns the players play value as an int
    private int playerPlay(boolean override, int overrideValue) {
        int selectedPlay;
        String selectedPlayString; 
        
        if (override) {
            selectedPlay = overrideValue;
            selectedPlayString = switchPlayType.numToString(overrideValue);
              
        }
        
        else {
            selectedPlayString = clickyButton.getSelectedToggle().toString().substring((clickyButton.getSelectedToggle().toString().indexOf("=") + 1), clickyButton.getSelectedToggle().toString().indexOf(","));
            selectedPlay = switchPlayType.stringToNum(selectedPlayString);
            
        }
        
        playerImage.setImage(new Image("/resources/" + selectedPlayString + ".png"));
        return selectedPlay;
       
    }
    
    //Returns the computers play value as an int
    private int computerPlay() {
        int compThrow = computerPlay.computerThrow(difficultyClicky.getSelectedToggle().toString().substring((difficultyClicky.getSelectedToggle().toString().indexOf("=") + 1), difficultyClicky.getSelectedToggle().toString().indexOf(",")), throwHistory);
        String compThrowString = switchPlayType.numToString(compThrow);
        computerImage.setImage(new Image("/resources/" + compThrowString + ".png"));
        return compThrow;

    }
    
    //Adds dummy data and defaults the game to Hard mode
    @Override public void initialize(URL url, ResourceBundle rb) {
        difficultyClicky.selectToggle(normal);
        for(int i = 0; i < 15; i++) {
            play(true, (int) ((Math.random() * 3) + 1));
        }
        
    }     
}
