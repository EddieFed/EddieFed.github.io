/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello.game;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLDocumentController implements Initializable {
    
    
    // [x] up is broken (might be all vertical) 
    // [ ] make diagonal checks
    // [ ] determine winner 
    // [ ] make dumb AI
    
    
    
    public int gridSize = 8;
    
    private String aiColor = "w"; 
    private String turnColor = "";
    @FXML private ImageView turnImage;
    
    @FXML private AnchorPane pane;
    @FXML private GridPane grid;
    private String[][] chips = new String[gridSize][gridSize];
    
    private boolean checkMove(int x, int y, boolean override) {
        
        // check to see if there is a chip there already
        if (chips[x][y].equals("b") || chips[x][y].equals("w")) {
            return false;
            
        }
        
        boolean up = placeChipColumn(x, y, override);
        boolean side = placeChipRow(x, y, override);
        boolean diagonalsUp = placeChipDiagonalUp(x, y, override);
        boolean diagonalsDown = placeChipDiagonalDown(x, y, override);
        return up || side || diagonalsUp || diagonalsDown;
        
    }
    
    private boolean placeChipDiagonalUp(int x, int y, boolean override) {
//        System.out.println("Diagonal Up");
        List<Integer> yListUpLeft = new ArrayList<>();
        List<Integer> xListUpLeft = new ArrayList<>();
        List<Integer> yListUpRight = new ArrayList<>();
        List<Integer> xListUpRight = new ArrayList<>();
        
        
        // up to the left [ ↖ ]
        int xLoc = x; // <--- Normally this should be one less, but that happens the first time the loop runs, leave it be
        for (int yLoc = (y - 1); ((yLoc >= 0) && (xLoc >= 1)); yLoc--) {
            xLoc--;
            
            if (!(chips[xLoc][yLoc].equals(turnColor)) && !(chips[xLoc][yLoc].equals("_"))) {
//                System.out.println("opposite to the ↖");
                yListUpLeft.add(yLoc);
                xListUpLeft.add(xLoc);
                
            }
            else if (chips[xLoc][yLoc].equals("_")) {
                yListUpLeft.clear();
                xListUpLeft.clear();
                break;
                
            }
            else if (chips[xLoc][yLoc].equals(turnColor)) {
                break;
                
            }
            if (((xLoc == 0) || (yLoc == 0)) && !(chips[xLoc][yLoc].equals(turnColor))) {
                yListUpLeft.clear();
                xListUpLeft.clear();
                break;
                
            }
        }
        
        
        // up to the right [ ↗ ]
        xLoc = x; // <--- Normally this should be one more, but that happens the first time the loop runs, leave it be
        for (int yLoc = (y - 1); ((yLoc >= 0) && (xLoc < (gridSize - 1))); yLoc--) {
            xLoc++;
            
            if (!(chips[xLoc][yLoc].equals(turnColor)) && !(chips[xLoc][yLoc].equals("_"))) {
                System.out.println("opposite to the ↗");
                yListUpRight.add(yLoc);
                xListUpRight.add(xLoc);
                
            }
            else if (chips[xLoc][yLoc].equals("_")) {
                yListUpRight.clear();
                xListUpRight.clear();
                break;
                
            }
            else if (chips[xLoc][yLoc].equals(turnColor)){
                break;
            
            }
            if (((xLoc == (gridSize - 1)) || (yLoc == 0)) && !(chips[xLoc][yLoc].equals(turnColor))) {
                yListUpRight.clear();
                xListUpRight.clear();
                break;
                
            }
        }
        
        
        yListUpLeft.addAll(yListUpRight);
        xListUpLeft.addAll(xListUpRight);
        
        if (override == false) {
            for(int spot = 0; spot < yListUpLeft.size(); spot++) {
                System.out.println("loop");
                chips[xListUpLeft.get(spot)][yListUpLeft.get(spot)] = turnColor;
            }
        }
        
        
        return (yListUpLeft.size() > 0);
    }
    
    private boolean placeChipDiagonalDown(int x, int y, boolean override) {
//        System.out.println("Diagonal Down");
        List<Integer> yListDownLeft = new ArrayList<>();
        List<Integer> xListDownLeft = new ArrayList<>();
        List<Integer> yListDownRight = new ArrayList<>();
        List<Integer> xListDownRight = new ArrayList<>();
        
        
        // down to the left [ ↙ ]
        int xLoc = x; // <--- Normally this should be one less, but that happens the first time the loop runs, leave it be
        for (int yLoc = (y + 1); ((yLoc < gridSize) && (xLoc >= 1)); yLoc++) {
            xLoc--;
            
            System.out.println(chips[xLoc][yLoc]);
            
            if (!(chips[xLoc][yLoc].equals(turnColor)) && !(chips[xLoc][yLoc].equals("_"))) {
//                System.out.println("opposite to the ↙");
                yListDownLeft.add(yLoc);
                xListDownLeft.add(xLoc);
                
            }
            else if (chips[xLoc][yLoc].equals("_")) {
                yListDownLeft.clear();
                xListDownLeft.clear();
                break;
                
            }
            else if (chips[xLoc][yLoc].equals(turnColor)) {
                break;
                
            }
            if (((xLoc == 0) || (yLoc == (gridSize - 1))) && !(chips[xLoc][yLoc].equals(turnColor))) {
                yListDownLeft.clear();
                xListDownLeft.clear();
                break;
                
            }
        }
        
        
        // down to the right [ ↘ ]
        xLoc = x; // <--- Normally this should be one more, but that happens the first time the loop runs, leave it be
        for (int yLoc = (y + 1); ((yLoc < gridSize) && (xLoc < (gridSize - 1))); yLoc++) {
            xLoc++;
            
            if (!(chips[xLoc][yLoc].equals(turnColor)) && !(chips[xLoc][yLoc].equals("_"))) {
                System.out.println("opposite to the ↘");
                yListDownRight.add(yLoc);
                xListDownRight.add(xLoc);
                
            }
            else if (chips[xLoc][yLoc].equals("_")) {
                yListDownRight.clear();
                xListDownRight.clear();
                break;
                
            }
            else if (chips[xLoc][yLoc].equals(turnColor)) {
                break;
            
            }
            if (((xLoc == (gridSize - 1)) || (yLoc == (gridSize - 1))) && !(chips[xLoc][yLoc].equals(turnColor))) {
                yListDownRight.clear();
                xListDownRight.clear();
                break;
                
            }
        }
        
        
        yListDownLeft.addAll(yListDownRight);
        xListDownLeft.addAll(xListDownRight);
        
        
        if (override == false) {
            for(int spot = 0; spot < yListDownLeft.size(); spot++) {
                chips[xListDownLeft.get(spot)][yListDownLeft.get(spot)] = turnColor;

            }
        }
        
        
        return (yListDownLeft.size() > 0);
    }

    private boolean placeChipColumn(int x, int y, boolean override) {
//        System.out.println("up");
        List<Integer> yUp = new ArrayList<>();
        List<Integer> yDown = new ArrayList<>();
        
        
        // checks up
        for (int locY = (y - 1); locY >= 0; locY--) {
            if (!(chips[x][locY].equals(turnColor)) && !(chips[x][locY].equals("_"))) {
//                System.out.println("Opposite chip to the up");
                yUp.add(locY);
                
            }
            else if (chips[x][locY].equals("_")) {
                yUp.clear();
                break;
                
            }
            else if (chips[x][locY].equals(turnColor)) {
                break;
                
            }
            if ((locY == 0) && !(chips[x][locY].equals(turnColor))) {
                yUp.clear();
                break;
                
            }
        }
        
        
        // checks down
        for (int locY = (y + 1); locY < gridSize ; locY++) {
            if (!(chips[x][locY].equals(turnColor)) && !(chips[x][locY].equals("_"))) {
//                System.out.println("Opposite chip to the down");
                yDown.add(locY);
                
            }
            else if (chips[x][locY].equals("_")) {
                yDown.clear();
                break;
                
            }
            else if (chips[x][locY].equals(turnColor)) {
                break;
                
            }
            if ((locY == (gridSize - 1)) && !(chips[x][locY].equals(turnColor))) {
                yDown.clear();
                break;
                
            }
        }
        
        
        // yUp becomes the overlord of the y locations
        yUp.addAll(yDown);

        
        // captures chips
        if (override == false) {
            for (int spot : yUp) {
                chips[x][spot] = turnColor;
            }
        }
        
        
        // if move was valid or not
        return yUp.size() > 0;
    }
    
    private boolean placeChipRow(int x, int y, boolean override) {
//        System.out.println("Side");
        List<Integer> xLeft = new ArrayList<>();
        List<Integer> xRight = new ArrayList<>();
        
        
        // checks left
        for (int locX = (x - 1); locX >= 0; locX--) {
            if (!(chips[locX][y].equals(turnColor)) && !(chips[locX][y].equals("_"))) {
//                System.out.println("Opposite chip to the left");
                xLeft.add(locX);
                
            }
            else if (chips[locX][y].equals("_")) {
                xLeft.clear();
                break;
                
            }
            else if (chips[locX][y].equals(turnColor)) {
                break;
                
            }
            if ((locX == 0) && !(chips[locX][y].equals(turnColor))) {
                xLeft.clear();
                break;
                
            }
        }
        
        
        // checks right
        for (int locX = (x + 1); locX < gridSize ; locX++) {
            if (!(chips[locX][y].equals(turnColor)) && !(chips[locX][y].equals("_"))) {
//                System.out.println("Opposite chip to the right");
                xRight.add(locX);
                
            }
            else if (chips[locX][y].equals("_")) {
                xRight.clear();
                break;
                
            }
            else if (chips[locX][y].equals(turnColor)) {
                break;
                
            }
            if ((locX == (gridSize - 1)) && !(chips[locX][y].equals(turnColor))) {
                xRight.clear();
                break;
                
            }
        }
        
        // xLeft becomes the overlord of the x locations
        xLeft.addAll(xRight);
        
        if (override == false) {
            for (int spot : xLeft) {
                chips[spot][y] = turnColor;

            }
        }
        
        
        // if move was valid or not
        return xLeft.size() > 0;
    }
    
    public void update() {
        // places chips on the board
        for (int k = 0; k < gridSize; k++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView image = new ImageView("chips/" + (chips[k][j]) + ".png");
                image.setOnMouseClicked(eventOnClick);
                image.setPickOnBounds(true);
                
                grid.add(image, k, j);
                
            }
        }
        
        //Switches turn
        if (turnColor.equals("b")) {
            turnColor = "w";  
        }
        else {
            turnColor = "b";
        }
        turnImage.setImage(new Image("chips/" + turnColor + ".png"));
    }
    
    public boolean checkWinner() {
        boolean isWinner = true;
        
        loop: {
        for (int k = 0; k < gridSize; k++) {
            for (int j = 0; j < gridSize; j++) {
                if((checkMove(k, j, true)) == true) {
                    System.out.println("Game is still going");
                    isWinner = false;
                    break loop;     
                }
            }
        }}
        
        if (isWinner == true) {
            JOptionPane.showInputDialog("Game end!");
            return true;
            
        }
        
        return false;
    }

    public EventHandler eventOnClick = (EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
        
        @Override public void handle(MouseEvent event) {
            int x;
            int y;
            if (event == null) {
                x = (int)(Math.random() * gridSize);
                y = (int)(Math.random() * gridSize);
            }
            else {
                x = GridPane.getColumnIndex(((ImageView) event.getSource()));
                y = GridPane.getRowIndex(((ImageView) event.getSource()));
            }
            
            
            
            System.out.println("Turn: " + turnColor);
            System.out.println("Y:    " + y);
            System.out.println("X:    " + x);
            
            
            boolean valid = checkMove(x, y, false);
            if (valid) {
                System.out.println("\ngood move");
                chips[x][y] = turnColor;
                update();
                
            }
            else {
                System.out.println("\nInvalid move - try again");
                
            }
            
            
            // prints out boatd to console
            for (int k = 0; k < gridSize; k++) {
                for (int j = 0; j < gridSize; j++) {
                    System.out.print(chips[j][k] + " ");
                }
                System.out.println();
            }
            
            
            
            if (checkWinner() == false) {
                
            /** //     Enable for AI auto opponent     **/ 
            /****/                                  /****/
            /****/  if (turnColor.equals(aiColor)) {    /****/
            /****/      handle(null);               /****/
            /****/  }                               /****/
            /****/                                  /****/
            /** //     Enable for AI auto opponent     **/ 
            
            }
            
            
        }
    };
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        pane.setStyle("-fx-background-color: grey");
        grid.setStyle("-fx-grid-lines-visible: true");
        
        // sets the "turn" counter to black (the starting color)
        turnImage.setImage(new Image("chips/b.png"));
        turnColor = "w"; // <-- Staring color is black, but trust me, it becomes black during setup
        
        // starting positions
        chips[3][3] = "w";
        chips[4][4] = "w";
        chips[3][4] = "b";
        chips[4][3] = "b";
        
        // tests
//        chips[2][2] = "b";
        
        
        // sets all 'null' values to "_"
        for (int k = 0; k < gridSize; k++) {
            for (int j = 0; j < gridSize; j++) {
                if((chips[k][j]) == null){
                    chips[k][j] = "_";
                    
                }
            }
        }
        
        update();
        
    }
}
