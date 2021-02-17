/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */
package shaper.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import shaper.SceneController;

/**
 * 
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class Gameplay {
    
    
    // The server side socket is always player 1
    // Otherwise if no network connection is made, the user is always player 1
    private int currentPlayer;
    public int Player = 1;
    public int Opponent = 0;
    
    
    // The list of all shapes in the draw pile
    private List<Shape> pile = new ArrayList<>();
    
    
    // The chains of the two players
    private List<Shape> playerChain = new ArrayList<>();
    private List<Shape> opponentChain = new ArrayList<>();
    
    // Chain manager
    private List<List<Shape>> chains = new ArrayList<>(Arrays.asList(opponentChain, playerChain));
    
    
    // The shape currently held by the active player
    private Shape shapeHeld = null;
    
    
    
    /** Creates a Gameplay object to store all game data
     *  
     *  @param isNetworked true = Networked, false = Single player
     */
    public Gameplay(boolean isNetworked) {
        if (isNetworked) {
            System.out.println("Multiplayer");
            System.out.println(SceneController.TargetAddressIP + ":");
            /** Network setup
             *  ~ Tell seed
             *  
             */ 
            this.currentPlayer = 1;  // This is the server {dont forget to change on client}
        }
        else {
            System.out.println("Single player");
            this.currentPlayer = 1;
        }
        
        // Possibilities for shapes
        String[] shapes = {"TRIANGLE", "DIAMOND", "SQUARE"};
        String[] effects = {"R", "SW", "S", "RAND", "GF", "GE", "GM", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        String[] colors = {"RED", "BLUE", "GREEN"};
        Random randomPileSeed = new Random((long) (Math.random() * 150));
        
        // Creates pile of shapes
        for(int s = 0; s < 20; s++) {
            pile.add(new Shape(shapes[randomPileSeed.nextInt(3)], effects[randomPileSeed.nextInt(15)], colors[randomPileSeed.nextInt(3)]));
        }    
    }
    
    
    
    /** Gets all chains
     *  
     *  @return A list of chains {opponentChain, playerChain}
     */
    public List<List<Shape>> getChains() {
        return chains;
    }
    
    
    
    /** Gets the player number
     *  
     *  @return The active player number
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    
    
    /** Grabs a shape from the draw pile
     *  If it has an immediate effect, it will trigger that event immediately
     * 
     * @return The top shape of the pile, null if pile is empty
     */
    public Shape grabShape() {
        shapeHeld = null;
        if(pile.isEmpty()) {
            return null;
        }
        
        shapeHeld = pile.remove(0);
        return shapeHeld;
    }
    
    
    
    /** Switches the chains of the players
     *  
     */ 
    public void switchChains() {
        List<Shape> tempChain = new ArrayList<>(playerChain);
        playerChain.clear();
        playerChain.addAll(opponentChain);
        opponentChain.clear();
        opponentChain.addAll(tempChain);
    }
    
    
    
    /** Randomizes the active player's shape colors
     *  
     * @param playerNum the index of chains to randomize {opponentChain, playerChain}
     */ 
    public void randomColors(int playerNum) {
        if(chains.get(playerNum).isEmpty()) {
//            System.out.println("empty");
            chains.get(currentPlayer).add(shapeHeld);
        }
        else {
            for (Shape s : chains.get(playerNum)) {
                s.newColor();
            }     
        }
    }
    
    
    
    /** Changes player turn
     *  
     */ 
    public void nextTurn() {
        if (currentPlayer == 1) {
            currentPlayer = 0;
        }
        else {
            currentPlayer = 1;
        }
    }
    
    
    
}
