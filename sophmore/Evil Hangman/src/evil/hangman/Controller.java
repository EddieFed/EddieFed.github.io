/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 *
 * @author rcortez
 */
public class Controller implements Initializable {
    // TO DO
    // [ ] Make 10 hangman variations
    // [ ] Loop the game (ask to play again)
    // [ ] Add scoreboard
    // 
    
    GamePlay game = new GamePlay();
    List<Image> pictures = new ArrayList<>();
    
    @FXML private Button btnGuess, btnStart;
    @FXML private ImageView imgHang;
    @FXML private Label lblWord, lblGuess;
    
    
    
    @FXML private void handleStart(ActionEvent event)  {
        game.setLength(Integer.parseInt(JOptionPane.showInputDialog(null, "Lenght of the word?", "")));
        
        btnStart.setVisible(false);
        btnGuess.setVisible(true); 
    }
    
    
    @FXML private void handleGuess(ActionEvent event) {
        game.addGuess(JOptionPane.showInputDialog(null, "Guess a letter!", "Guess"));
        lblGuess.setText(game.getGuesses().substring(0, game.getGuesses().length() - 1));
        
        
        System.out.println(game.getDictionary().getArrayNoMatch(game.getGuesses().toUpperCase(), game.getLength()));
        game.checkForWord();
        lblWord.setText(game.getFinalWordLabel());
        
        if (game.checkWin().equals("You Won!")) {
            
        }
        
        
        imgHang.setImage(pictures.get(game.checkForWrong()));
        
    }
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        
        lblWord.setText("");
        lblGuess.setText("");
        pictures.clear();
        for (int p = 1; p <= 7; p++) {  pictures.add(new Image("resources/hangman" + p + ".png"));  }
        imgHang.setImage(pictures.get(0));
        
        btnStart.setVisible(true);
        btnGuess.setVisible(false);
    }    
}
