/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */
package shaper; 

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import shaper.objects.Gameplay;
import shaper.objects.Shape;
//</editor-fold>

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class SceneController implements Initializable {
    
    
    public static String TargetAddressIP = null;
    public static int PlayerNumber = 1;
    public static int OpponentNumber = 0;
    private Gameplay game;
    private Shape globalHeldShape;
    boolean forceOpponentTurn = false;
    
    
    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML private AnchorPane pane_Connect, pane_main, pane_playerlock, pane_clickStuff, pane_rules;
    @FXML private Button     btn_single, btn_double;
    @FXML private CheckBox   chk_overrideMenu;
    @FXML private GridPane   grid_opponent, grid_player, grid_opponentSelector, grid_playerSelector;
    @FXML private ImageView  imgv_drawPile, imgv_debug, imgv_opponentSelector, imgv_playerSelector;
    @FXML private Label      lbl_turn, debug_lbl;
    @FXML private TextField  txt_ip;
    @FXML private ToolBar    tool_override;
    //</editor-fold>
    
    // TO DO
    // [ ] - Add Game over screen
    // [ ] - Add Endgame {scoring}
    // [ ] - Add networking
    
    
    /** Starts the game
     *
     * @param event the Object clicked (not really important)
     */
    @FXML public void action_StartGame(ActionEvent event) {
        TargetAddressIP = txt_ip.getText();
        game = new Gameplay(((Button) event.getSource()).equals(btn_double));
        pane_Connect.setVisible(false);
        pane_main.setVisible(true);
        pane_clickStuff.setVisible(false);
        pane_playerlock.setVisible(false);

        imgv_playerSelector.setVisible(false);
        imgv_opponentSelector.setVisible(false);
    }
    
    
    /** Draws a shape for the player
     *
     * @param event the Object clicked by the mouse (not really important)
     */
    @FXML public void action_DrawShape(MouseEvent event) {
        Platform.runLater(() -> {     
            if (game.getCurrentPlayer() == PlayerNumber) {
                System.out.println("\n\nPlayer's Turn:");
            }
            
            
//            System.out.println(game.getCurrentPlayer());
            if (forceOpponentTurn == false) {
                globalHeldShape = null;
                globalHeldShape = game.grabShape();
            }
            if(globalHeldShape == null) {
                System.out.println("\n\nGame over");
                return;
            }
                
                
//            System.out.println(globalHeldShape.getDebugData() + "\n" + globalHeldShape.getPath());
            imgv_debug.setImage(new Image(globalHeldShape.getPath()));
            debug_lbl.setText(globalHeldShape.getEffect());
            
//            System.out.println("Move to be made by: " + game.getCurrentPlayer());
            //<editor-fold defaultstate="collapsed" desc="Player turn">
            if (game.getCurrentPlayer() == PlayerNumber) {
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
                        if(game.getChains().get(OpponentNumber).isEmpty()) {
                            game.getChains().get(PlayerNumber).add(globalHeldShape);
                            System.out.println("    ~ No shapes to steal, immediately added");
                        }
                        else {
                            activateSelectors("S");
                        }
                        return;
                    }
                    case("NULL"): {
                        if(game.getChains().get(PlayerNumber).isEmpty()) {
                            game.getChains().get(PlayerNumber).add(globalHeldShape);
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
            if ((game.getCurrentPlayer() == OpponentNumber && event != null) || forceOpponentTurn == true) {
                pane_playerlock.setVisible(true);
                lbl_turn.setText("Opponent's Thinking:");
                System.out.println("\n\nOpponent's Thinking:");
                new Thread(() -> {
                    try {
                        System.out.println("    ~ 3..");
                        Thread.sleep(1000);
                        System.out.println("    ~ 2..");
                        Thread.sleep(1000);
                        System.out.println("    ~ 1.");
                        Thread.sleep(1000);
                        System.out.println("    ~ Opponent move made");
                        
                        forceOpponentTurn = false;
                        action_DrawShape(null);
                    } catch (InterruptedException ex) {
                        System.out.println("    ~ Oops");
                    }
                }).start();
                return;
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Computer Turn">
            if (game.getCurrentPlayer() == OpponentNumber && event == null) {
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
                        if (game.getChains().get(PlayerNumber).isEmpty()) {
                            game.getChains().get(OpponentNumber).add(globalHeldShape);
                            System.out.println("    ~ Nothing to steal, moved to chain");
                        }
                        else {
                            int stealIndex = (int) (Math.random() * game.getChains().get(PlayerNumber).size());
                            globalHeldShape = game.getChains().get(PlayerNumber).remove(stealIndex);
                            if((int)(Math.random() * 2) == 0) {
                                game.getChains().get(OpponentNumber).add(0, globalHeldShape);
                                System.out.println("    ~ Shape stolen from index " + stealIndex + ". Moved to front of chain");
                            }
                            else {
                                game.getChains().get(OpponentNumber).add(globalHeldShape);
                                System.out.println("    ~ Shape stolen from index " + stealIndex + ". Moved to back of chain");
                            }
                        }   
                        break;
                    }
                    case "NULL": {
                        if((int)(Math.random() * 2) == 0) {
                            game.getChains().get(OpponentNumber).add(0, globalHeldShape);
                            System.out.println("    ~ Shape placed at front of chain");
                        }
                        else {
                            game.getChains().get(OpponentNumber).add(globalHeldShape);
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
                
                pane_playerlock.setVisible(false);
                lbl_turn.setText("Your turn");
            }
            //</editor-fold>
            
            
        });
    }
    
    
    private void activateSelectors(String effect) {
        pane_clickStuff.setVisible(true);
        grid_playerSelector.getChildren().clear();
        grid_opponentSelector.getChildren().clear();
        
        switch(effect) {
            case("S"): {
                System.out.println("    ~~> Steal");
                
                //<editor-fold defaultstate="collapsed" desc=""S(teal)" event">
                for (Shape s : game.getChains().get(OpponentNumber)) {
                   
//                    System.out.println("Endpoints");
                    ImageView newI = new ImageView(new Image("resources/select.png"));
                    newI.setFitHeight(64.0);
                    newI.setFitWidth(64.0);
                    newI.setOpacity(0.25);
                    newI.setOnMouseClicked((event) -> {
                        //<editor-fold defaultstate="collapsed" desc="Onclick for "S(teal)" event">
                        int index = grid_opponentSelector.getChildren().indexOf(event.getSource());
//                        System.out.println(index);
                        globalHeldShape = game.getChains().get(OpponentNumber).remove(index);
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
                for (int x = 0; x < game.getChains().get(PlayerNumber).size() + 2; x++) {
                    if (x == 0 || x == game.getChains().get(PlayerNumber).size() + 1) {
//                        System.out.println("Endpoints");
                        ImageView newI = new ImageView(new Image("resources/select.png"));
                        newI.setFitHeight(64.0);
                        newI.setFitWidth(64.0);
                        newI.setOpacity(0.25);
                        newI.setOnMouseClicked((event) -> {
                            //<editor-fold defaultstate="collapsed" desc="Onclick for "NULL" event">
                            int index = grid_playerSelector.getChildren().indexOf(event.getSource());
//                            System.out.println(index);
                            
                            if (index == 0) {
                                game.getChains().get(PlayerNumber).add(0, globalHeldShape);
                                System.out.println("    ~ Shape added to front of hand");
                            }
                            else {
                                game.getChains().get(PlayerNumber).add(globalHeldShape);
                                System.out.println("    ~ Shape added to back of hand");
                            }
                            
                            grid_playerSelector.getChildren().clear();
                            pane_clickStuff.setVisible(false);
                            forceOpponentTurn = true;
                            game.nextTurn();
                            action_DrawShape(null);
                            updateChains();
                            //</editor-fold>
                        });
                        
                        grid_playerSelector.add(newI, x, 0);
                    }
                    else {
//                        System.out.println("Middle points");
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
                imgv_playerSelector.setOnMouseClicked((event) -> {
                    game.randomColors(PlayerNumber);
                    imgv_playerSelector.setVisible(false);
                    imgv_opponentSelector.setVisible(false);
                    pane_clickStuff.setVisible(false);
                    System.out.println("    ~ Randomized player's colors");
                    
                    updateChains();
                    game.nextTurn();
                    
                    forceOpponentTurn = true;
                    action_DrawShape(null);
                    
                });
                imgv_opponentSelector.setOnMouseClicked((event) -> {
                    game.randomColors(OpponentNumber);
                    imgv_playerSelector.setVisible(false);
                    imgv_opponentSelector.setVisible(false);
                    pane_clickStuff.setVisible(false);
                    System.out.println("    ~ Randomized opponent's colors");
                    
                    updateChains();
                    game.nextTurn();
                    
                    forceOpponentTurn = true;
                    action_DrawShape(null);
                }); 
                //</editor-fold>
                
                break;
            }
        }
        
        
    }
    
    
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
    
    
    //<editor-fold defaultstate="collapsed" desc="Developer Menu stuffs">
    // XXXXXXXXXXXXXX - DEV TOOLS - XXXXXXXXXXXXXXXX \\
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \\

    
    /** Shows/Hides the "developer" menu
     *
     *  @param event the Object clicked (not really important)
     */
    @FXML public void toggle_OverrideMenu(ActionEvent event) {
        tool_override.setVisible(chk_overrideMenu.isSelected());
    }
    
    @FXML public void handleEndGame(ActionEvent event) {
        System.out.println("Game reset");
        pane_main.setVisible(false);
        pane_Connect.setVisible(true);
        grid_player.getChildren().clear();
        grid_opponent.getChildren().clear();
        game = null;
    }
    
    @FXML public void handleRandom(ActionEvent event) {
        System.out.println("Colors randomized");
        int input = Integer.parseInt(JOptionPane.showInputDialog(null, "Randomize color: {opponentChain, playerChain}", "Which player?", JOptionPane.PLAIN_MESSAGE));
        game.randomColors(input);
        updateChains();
    }
    
    @FXML public void handleSwitch(ActionEvent event) {
        System.out.println("Chains switched");
        game.switchChains();
        updateChains();
    }
    

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \\
    //</editor-fold>
    
    @FXML public void hideRules(ActionEvent event) {
        pane_Connect.setVisible(true);
        pane_rules.setVisible(false);
    }
    @FXML public void showRules(ActionEvent event) {
        pane_Connect.setVisible(false);
        pane_rules.setVisible(true);
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        pane_main.setVisible(false);
        pane_Connect.setVisible(true);
        pane_playerlock.setVisible(false);
        pane_clickStuff.setVisible(false);
        
        imgv_playerSelector.setVisible(false);
        imgv_opponentSelector.setVisible(false);
    }
    
}
