/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */
package shaper; 

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import shaper.objects.Gameplay;
import shaper.objects.Shape;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class SceneController implements Initializable {
    
    final private int playerNumber = 1;
    final private int opponentNumber = 0;
    private int[] scores = {0, 0};  // {Opponent, Player}
    
    private Gameplay game;
    private Shape globalHeldShape;
    private boolean forceOpponentThink = false;
    
    
    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML private AnchorPane pane_Connect, pane_main, pane_playerlock, pane_clickStuff, pane_rules, pane_score;
    @FXML private CheckBox   chk_overrideMenu;
    @FXML private GridPane   grid_opponent, grid_player, grid_opponentSelector, grid_playerSelector;
    @FXML private ImageView  imgv_debug, imgv_opponentSelector, imgv_playerSelector;
    @FXML private Label      lbl_turn, lbl_debug, lbl_score;
    @FXML private ToolBar    tool_override;
    //</editor-fold>
    
    
    /** Starts the game
     *
     * @param event the Object clicked (not really important)
     */
    @FXML public void action_StartGame(ActionEvent event) {
        game = new Gameplay();
        SetVisibilityStart();
        
        imgv_debug.setImage(null);
        lbl_debug.setText("");
    }
    
    
    /** Sets the visibility for the start of the game
     *
     */
    private void SetVisibilityStart() {
        pane_Connect.setVisible(false);
        pane_main.setVisible(true);
        pane_playerlock.setVisible(false);
        pane_clickStuff.setVisible(false);
        pane_rules.setVisible(false);
        pane_score.setVisible(false);
        
        imgv_playerSelector.setVisible(false);
        imgv_opponentSelector.setVisible(false);
    }
    
    
    /** Draws a shape for the player
     *
     * @param event the Object clicked by the mouse (not really important)
     */
    @FXML public void action_DrawShape(MouseEvent event) {
        Platform.runLater(() -> { 
            
            
            
            // If not opponent's think is not forced, draw for active player
            if (forceOpponentThink == false) {
                globalHeldShape = null;
                globalHeldShape = game.grabShape();
                
                if(globalHeldShape == null) {
                    tallyUpScore();
                    return;
                }
                else {
                    imgv_debug.setImage(new Image(globalHeldShape.getPath()));
                    lbl_debug.setText(globalHeldShape.getEffect());
                }
            }
            
            //<editor-fold defaultstate="collapsed" desc="Player turn">
                if (game.getCurrentPlayer() == playerNumber) {
                    System.out.println("\n\nPlayer's Turn:");
                    
                    switch(globalHeldShape.getEffect()) {
                        case("GF"): {
                            System.out.println("    ~ Shape was force placed at front of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add(0, globalHeldShape);
                            break;
                        }
                        case("GE"): {
                            System.out.println("    ~ Shape was force placed at end of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add(globalHeldShape);
                            break;
                        }
                        case("GM"): {
                            System.out.println("    ~ Shape was force placed at the middle of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add((game.getChains().get(game.getCurrentPlayer()).size() / 2), globalHeldShape);
                            break;
                        }
                        case("SW"): {
                            System.out.println("    ~ Chains were forcefully switched");
                            game.switchChains();
                            break;
                        }
                        case("R"): {
                            System.out.println("    ~ Reversed chain {later make it selectable}");
                            Collections.reverse(game.getChains().get(game.getCurrentPlayer()));
                            break;
                        }
                        case("S"): {
                            if(game.getChains().get(opponentNumber).isEmpty()) {
                                game.getChains().get(playerNumber).add(globalHeldShape);
                                System.out.println("    ~ No shapes to steal, immediately added");
                            }
                            else {
                                activateSelectors("S");
                            }
                            return;
                        }
                        case("NULL"): {
                            if(game.getChains().get(playerNumber).isEmpty()) {
                                game.getChains().get(playerNumber).add(globalHeldShape);
                                System.out.println("    ~ No shapes in chain, immediately added");
                                break;
                            }
                            else {
                                activateSelectors("NULL");
                                return;
                            }
                        }
                        case("RAND"): {
                            activateSelectors("RAND");
                            return;
                        }   
                    }

                    updateChains();
                    game.nextTurn();
                }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Computer Sleep Cycle">
                if ((game.getCurrentPlayer() == opponentNumber && event != null) || forceOpponentThink == true) {
                    pane_playerlock.setVisible(true);
                    lbl_turn.setText("Opponent's Turn:");
                    System.out.println("\n\nOpponent's Thinking:");
                    
                    // Computer "Thinks for 3 seconds"
                    new Thread(() -> {
                        try {
                            System.out.println("    ~ 3..");
                            Thread.sleep(1000);
                            System.out.println("    ~ 2..");
                            Thread.sleep(1000);
                            System.out.println("    ~ 1.");
                            Thread.sleep(1000);
                            System.out.println("    ~ Opponent move made");

                            forceOpponentThink = false;
                            action_DrawShape(null);
                        } catch (InterruptedException ex) {
                            System.out.println("    ~ Oops");
                        }
                    }).start();
                    return;
                }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Computer Turn">
                if (game.getCurrentPlayer() == opponentNumber && event == null) {
                    switch (globalHeldShape.getEffect()) {
                        case("GF"): {
                            System.out.println("    ~ Shape was force placed at front of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add(0, globalHeldShape);
                            break;
                        }
                        case("GE"): {
                            System.out.println("    ~ Shape was force placed at end of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add(globalHeldShape);
                            break;
                        }
                        case("GM"): {
                            System.out.println("    ~ Shape was force placed at the middle of the chain");
                            game.getChains().get(game.getCurrentPlayer()).add((game.getChains().get(game.getCurrentPlayer()).size() / 2), globalHeldShape);
                            break;
                        }
                        case("SW"): {
                            System.out.println("    ~ Chains were forcefully switched");
                            game.switchChains();
                            break;
                        }
                        case("R"): {
                            System.out.println("    ~ Reversed chain {later make it selectable}");
                            Collections.reverse(game.getChains().get(game.getCurrentPlayer()));
                            break;
                        }
                        case "S": {
                            if (game.getChains().get(playerNumber).isEmpty()) {
                                game.getChains().get(opponentNumber).add(globalHeldShape);
                                System.out.println("    ~ Nothing to steal, moved to chain");
                            }
                            else {
                                int stealIndex = (int) (Math.random() * game.getChains().get(playerNumber).size());
                                globalHeldShape = game.getChains().get(playerNumber).remove(stealIndex);
                                if((int)(Math.random() * 2) == 0) {
                                    game.getChains().get(opponentNumber).add(0, globalHeldShape);
                                    System.out.println("    ~ Shape stolen from index " + stealIndex + ". Moved to front of chain");
                                }
                                else {
                                    game.getChains().get(opponentNumber).add(globalHeldShape);
                                    System.out.println("    ~ Shape stolen from index " + stealIndex + ". Moved to back of chain");
                                }
                            }   
                            break;
                        }
                        case "NULL": {
                            if((int)(Math.random() * 2) == 0) {
                                game.getChains().get(opponentNumber).add(0, globalHeldShape);
                                System.out.println("    ~ Shape placed at front of chain");
                            }
                            else {
                                game.getChains().get(opponentNumber).add(globalHeldShape);
                                System.out.println("    ~ Shape placed at back of chain");
                            }   
                            break; 
                        }
                        case "RAND": {
                            int playerToRandomize = (int)(Math.random() * 2);
                            game.randomColors(playerToRandomize);
                            System.out.println("    ~ Randomized player number " + playerToRandomize);
                            break;
                        }  
                    }

                    updateChains();
                    game.nextTurn();
                    
                    lbl_turn.setText("Your turn");
                    pane_playerlock.setVisible(false);
                }
            //</editor-fold>
            
        });
    }
    
    
    /** Shows the panes to click according to the effect attached to a shape
     *
     * @param effect the String effect notation of the effect attached to the shape
     */
    private void activateSelectors(String effect) {
        pane_clickStuff.setVisible(true);
        grid_playerSelector.getChildren().clear();
        grid_opponentSelector.getChildren().clear();
        
        
        switch(effect) {
            case("S"): {
                System.out.println("    ~~> Steal");
                
                //<editor-fold defaultstate="collapsed" desc=""S(teal)" event">
                    for (Shape s : game.getChains().get(opponentNumber)) {
                        ImageView newI = new ImageView(new Image("resources/select.png"));
                        newI.setFitHeight(64.0);
                        newI.setFitWidth(64.0);
                        newI.setOpacity(0.25);
                        newI.setOnMouseClicked((event) -> {
                            //<editor-fold defaultstate="collapsed" desc="Onclick for "S(teal)" event">
                                int index = grid_opponentSelector.getChildren().indexOf(event.getSource());
                                globalHeldShape = game.getChains().get(opponentNumber).remove(index);
                                grid_opponentSelector.getChildren().clear();
                                System.out.println("    ~ Shape at index " + index + " was stolen");
                                activateSelectors("NULL");
                            //</editor-fold>
                        });

                        grid_opponentSelector.add(newI, grid_opponentSelector.getChildren().size(), 0);
                    }
                //</editor-fold>
                
                break;
            }
            case("NULL"): {
                System.out.println("    ~~> Place shape");
                
                //<editor-fold defaultstate="collapsed" desc=""NULL" event">
                    for (int x = 0; x < game.getChains().get(playerNumber).size() + 2; x++) {
                        if (x == 0 || x == game.getChains().get(playerNumber).size() + 1) {
                            ImageView newI = new ImageView(new Image("resources/select.png"));
                            newI.setFitHeight(64.0);
                            newI.setFitWidth(64.0);
                            newI.setOpacity(0.25);
                            newI.setOnMouseClicked((event) -> {
                                //<editor-fold defaultstate="collapsed" desc="Onclick for "NULL" event">
                                    int index = grid_playerSelector.getChildren().indexOf(event.getSource());

                                    if (index == 0) {
                                        game.getChains().get(playerNumber).add(0, globalHeldShape);
                                        System.out.println("    ~ Shape added to front of hand");
                                    }
                                    else {
                                        game.getChains().get(playerNumber).add(globalHeldShape);
                                        System.out.println("    ~ Shape added to back of hand");
                                    }

                                    grid_playerSelector.getChildren().clear();
                                    pane_clickStuff.setVisible(false);
                                    forceOpponentThink = true;
                                    game.nextTurn();
                                    action_DrawShape(null);
                                    updateChains();
                                //</editor-fold>
                            });

                            grid_playerSelector.add(newI, x, 0);
                        }
                        else {
                            ImageView newI = new ImageView(new Image("resources/noselect.png"));
                            newI.setFitHeight(64.0);
                            newI.setFitWidth(64.0);
                            newI.setOpacity(0.10);
                            grid_playerSelector.add(newI, x, 0);
                        }
                    }
                //</editor-fold>
                
                break;
            }
            
            case("RAND"): {
                System.out.println("    ~~> Choose who to randomize colors");
                
                pane_playerlock.setVisible(false);
                imgv_playerSelector.setVisible(true);
                imgv_opponentSelector.setVisible(true);
                
                //<editor-fold defaultstate="collapsed" desc=""RAND(om)" event">
                    // ImageView if player's Chain
                    imgv_playerSelector.setOnMouseClicked((event) -> {
                        game.randomColors(playerNumber);
                        imgv_playerSelector.setVisible(false);
                        imgv_opponentSelector.setVisible(false);
                        pane_clickStuff.setVisible(false);
                        System.out.println("    ~ Randomized player's colors");

                        updateChains();
                        game.nextTurn();

                        forceOpponentThink = true;
                        action_DrawShape(null);
                    });
                    
                    // ImageView of opponent's Chain
                    imgv_opponentSelector.setOnMouseClicked((event) -> {
                        game.randomColors(opponentNumber);
                        imgv_playerSelector.setVisible(false);
                        imgv_opponentSelector.setVisible(false);
                        pane_clickStuff.setVisible(false);
                        System.out.println("    ~ Randomized opponent's colors");

                        updateChains();
                        game.nextTurn();

                        forceOpponentThink = true;
                        action_DrawShape(null);
                    }); 
                //</editor-fold>
                
                break;
            }
        }
    }
    
    
    /** Updates all shapes based on the chain arrayLists in game
     *
     */
    private void updateChains() {
        List<GridPane> grids = Arrays.asList(grid_opponent, grid_player);
        
        for(int i = 0; i <= 1; i++) {
            grids.get(i).getChildren().clear();
            
            for(Shape s : game.getChains().get(i)) {
                ImageView newImageView = new ImageView(new Image(s.getPath()));
                newImageView.setFitHeight(64.0);
                newImageView.setFitWidth(64.0);
                grids.get(i).add(newImageView, (grids.get(i).getChildren().size()), 0);
            }
        }
    }
    
    
    /** Adds up all scores
     *
     */
    private void tallyUpScore() {
        System.out.println("\n\nGame over:");
        System.out.println("    ~ Score tallied");
        
        // Base score counter
        for (List<Shape> a : game.getChains()) {
            int playerScoreIndex = game.getChains().indexOf(a);
            
            int index = 0;
            while (index < a.size()) {
                int inARow = 1;
                boolean isEnded = false;
                
                while (index < a.size() && isEnded == false) {

                    if (index + 1 == a.size()) {
                        scoreTally(inARow, playerScoreIndex);
                        isEnded = true;
                    } 
                    else if (a.get(index).getShape().equals(a.get(index + 1).getShape())) {
                        inARow++;
                    }
                    else {
                        scoreTally(inARow, playerScoreIndex);
                        inARow = 1;
                        isEnded = true;
                    }
                    index++;
                }
            }
        }
        
        // Score multiplyer
        for (List<Shape> a : game.getChains()) {
            int playerScoreIndex = game.getChains().indexOf(a);
            
            int index = 0;
            while (index < a.size()) {
                int inARow = 1;
                boolean isEnded = false;
                
                while (index < a.size() && isEnded == false) {

                    if (index + 1 == a.size()) {
                        scoreMult(inARow, playerScoreIndex);
                        isEnded = true;
                    } 
                    else if (a.get(index).getColor().equals(a.get(index + 1).getColor())) {
                        inARow++;
                    }
                    else {
                        scoreMult(inARow, playerScoreIndex);
                        inARow = 1;
                        isEnded = true;
                    }
                    index++;
                }
            }
        }
        
        pane_main.setVisible(false);
        pane_score.setVisible(true);
        System.out.println("\n\nPlayer: " + scores[1] + ", Opponent: " + scores[0]);
        lbl_score.setText("Player: " + scores[1] + ", Opponent: " + scores[0]);
    }
    
    
    /** Add up base scores depending on the amount of same shapes in a row
     *
     * @param inAR the amount of same shapes in a row
     * @param player the player index (Player: 1, Opponent: 0)
     */
    private void scoreTally(int inAR, int player) {
        switch(inAR) {
            // ▽ 1 Score
            case(1): {
                scores[player] += 1;
                break;
            }
            // ▽ 2 Score
            case(2): {
                scores[player] += 3;
                break;
            }
            // ▽ 3+ Score
            default: {
                scores[player] += 5;
                break;
            }
        }
    }
    
    
    /** Multiplies the base scores depending on the amount of same colors in a row
     *
     * @param inAR the amount of same colors in a row
     * @param player the player index (Player: 1, Opponent: 0)
     */
    private void scoreMult(int inAR, int player) {
        switch(inAR) {
            case(1): {
                scores[player] *= 1;
                break;
            }
            case(2): {
                scores[player] *= 2;
                break;
            }
            case(3): {
                scores[player] *= 2;
                break;
            }
            default: {
                scores[player] *= 3;
                break;
            }
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Developer Menu stuffs">
    
        /** Shows/Hides the "developer" menu
         *
         *  @param event the Object clicked (not really important)
         */
        @FXML public void toggle_OverrideMenu(ActionEvent event) {
            tool_override.setVisible(chk_overrideMenu.isSelected());
        }


        /** Restarts the game to the default state as if it you just opened the program
         *
         *  @param event the Object clicked (not really important)
         */
        @FXML public void handleEndGame(ActionEvent event) {
            System.out.println("Game reset");
            SetVisibilityStart();
            pane_main.setVisible(false);
            pane_Connect.setVisible(true);
            grid_player.getChildren().clear();
            grid_opponent.getChildren().clear();
            chk_overrideMenu.setSelected(false);
            toggle_OverrideMenu(null);
            game = null;
        }


        /** Randomizes a defined player's hand colors
         *
         *  @param event the Object clicked (not really important)
         */
        @FXML public void handleRandom(ActionEvent event) {
            System.out.println("Colors randomized");
            int input = Integer.parseInt(JOptionPane.showInputDialog(null, "Randomize color: {opponentChain, playerChain}", "Which player?", JOptionPane.PLAIN_MESSAGE));
            game.randomColors(input);
            updateChains();
        }


        /** Switches both players hands immediately
         *
         *  @param event the Object clicked (not really important)
         */
        @FXML public void handleSwitch(ActionEvent event) {
            System.out.println("Chains switched");
            game.switchChains();
            updateChains();
        }
        
        
        /** Closes the window and ends the game
         *
         *  @param event the Object clicked (not really important)
         */
        @FXML public void handleClose(ActionEvent event) {
            System.exit(1);
        }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Show/Hide Rules">
    
        /** Hides the rules
         *
         * @param event the Object clicked by the mouse (not really important)
         */
        @FXML public void hideRules(ActionEvent event) {
            pane_Connect.setVisible(true);
            pane_rules.setVisible(false);
        }


        /** Shows the rules
         *
         * @param event the Object clicked by the mouse (not really important)
         */
        @FXML public void showRules(ActionEvent event) {
            pane_Connect.setVisible(false);
            pane_rules.setVisible(true);
        }
    
    //</editor-fold>
    
        
    @Override public void initialize(URL url, ResourceBundle rb) {
        SetVisibilityStart();
        pane_main.setVisible(false);
        pane_Connect.setVisible(true);
    }
    
}
