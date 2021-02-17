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

/**
 * 
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class Gameplay {
    
    // The server side socket is always player 1
    // Otherwise if no network connection is made, the user is always player 1
    private int currentPlayer = 1;
    final public int Player = 1;
    final public int Opponent = 0;
    
    
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
     */
    public Gameplay() {
        System.out.println("Game started!");
        
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
        return this.chains;
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
        this.shapeHeld = null;
        if(pile.isEmpty()) {
            return null;
        }
        
        this.shapeHeld = pile.remove(0);
        return this.shapeHeld;
    }
    
    
    /** Switches the chains of the players
     *  
     */ 
    public void switchChains() {
        List<Shape> tempChain = new ArrayList<>(this.playerChain);
        this.playerChain.clear();
        this.playerChain.addAll(this.opponentChain);
        this.opponentChain.clear();
        this.opponentChain.addAll(tempChain);
    }
    
        
    /** Randomizes the active player's shape colors
     *  
     * @param playerNum the index of chains to randomize {opponentChain, playerChain}
     */ 
    public void randomColors(int playerNum) {
        if(this.chains.get(playerNum).isEmpty()) {
            this.chains.get(currentPlayer).add(this.shapeHeld);
        }
        else {
            for (Shape s : this.chains.get(playerNum)) {
                s.newColor();
            }     
        }
    }
    
    
    /** Changes player turn
     *  
     */ 
    public void nextTurn() {
        if (this.currentPlayer == 1) {
            this.currentPlayer = 0;
        }
        else {
            this.currentPlayer = 1;
        }
    }
    
}
