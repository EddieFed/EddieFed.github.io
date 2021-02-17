/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class ControllerClient implements Initializable {
    
    
    private static final int PORT = 2048;
    private static final String ADDRESS = "localhost";
    private Socket clientService = null;
    private PrintWriter output;
    private BufferedReader input;
    
    private long seed;
    private Random rand = new Random(6000);
    
    @FXML private ImageView imageDown1 , imageDown2 , imageDown3 , imageDown4 , imageDown5 , imageDown6 , imageDown7 , imageDown8 , imageDown9 , imageDown10 , imageDown11 , imageDown12 , imageDown13 ,
                            imageLeft1 , imageLeft2 , imageLeft3 , imageLeft4 , imageLeft5 , imageLeft6 , imageLeft7 , imageLeft8 , imageLeft9 , imageLeft10 , imageLeft11 , imageLeft12 , imageLeft13 ,
                            imageUp1   , imageUp2   , imageUp3   , imageUp4   , imageUp5   , imageUp6   , imageUp7   , imageUp8   , imageUp9   , imageUp10   , imageUp11   , imageUp12   , imageUp13   ,
                            imageRight1, imageRight2, imageRight3, imageRight4, imageRight5, imageRight6, imageRight7, imageRight8, imageRight9, imageRight10, imageRight11, imageRight12, imageRight13;
    @FXML private ImageView imageTopLeft, imageTopRight, imageBottomLeft, imageBottomRight,
                            selectedCard;
    
    //My hand2 info
    @FXML private GridPane  hand2;
    private List<ImageView> handImages2 = new ArrayList<>();
    private List<Card> handCards2 = new ArrayList<>();
    
    //Opponants
    @FXML private GridPane  hand1;
    private List<ImageView> handImages1 = new ArrayList<>();
    private List<Card> handCards1 = new ArrayList<>();
    
    
    //Image Managers
    private List<ImageView> topPile;
    private List<ImageView> leftPile;
    private List<ImageView> bottomPile;
    private List<ImageView> rightPile;
    private List<ImageView> corners;
    private List<List<ImageView>> allPiles;
    
    
    //Card Managers
    private List<Card> topPileCards = new ArrayList<>();
    private List<Card> leftPileCards = new ArrayList<>();
    private List<Card> bottomPileCards = new ArrayList<>();
    private List<Card> rightPileCards = new ArrayList<>();
    private List<Card> cornersCards = new ArrayList<>();
    private List<List<Card>> allPilesCards;
    
    
    //All cards to start with
    private List<Card> deck = new ArrayList<>();
    
    
    //Actively selected card info
    private Card selected;
    private List<Card> selectedPile;
    
    
    //Currentl player info
    private int activePlayer = 1;   //The server is player 1, the client is player 2 
    @FXML private AnchorPane disableScreen;
    
    
    public ControllerClient() {
        new Thread(() -> {
            while (clientService == null) {
                try {
                    System.out.println("Connecting to server...");
                    clientService = new Socket();
                    clientService.connect(new InetSocketAddress(ADDRESS, PORT));
                    
                    output = new PrintWriter(clientService.getOutputStream(), true);
                    input = new BufferedReader(new InputStreamReader(clientService.getInputStream()));
                    listener();
                }
                catch (IOException err) {
                    System.out.println("Unable to connect, retrying in 60 seconds");
                    clientService = null;   // Resets server to nothing if it fails to connect
                    try {
                        Thread.sleep(6000);
                    }
                    catch (InterruptedException ex) {
                        System.out.println("Something went wrong while sleeping");
                    }
                }
            }
        }).start();
        
    }
    
    
    private void listener() {
        new Thread(() -> {
            if (clientService.isBound()) {
                System.out.println("Server bound at: " + clientService.getInetAddress().getHostAddress() + ":" + clientService.getLocalPort());
                
                new Thread(() -> {
                    System.out.println("Listener Thread started");
                    
                    try {
                        System.out.println("Listening");
                        while (true) {
                            String args = input.readLine();
                            try {
                                parseInput(args);
                                System.out.println(args);
                            } catch (Exception ex1) {
                                System.out.println(ex1);
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Error reading line");
                    }
                    
                    
                }).start();
            }
        }).start();
    }
    
    
    private void parseInput(String s) {
        switch(s.substring(0, s.indexOf("."))) {
            case ("start"): {
                seed = Long.parseLong(s.substring(s.indexOf(".") + 1));
                startGame(null);
                break;
            }
            case ("nextTurn"): {
                endTurn();
                break;
            }
            case ("moveCard"): {
                int a = Integer.parseInt(s.substring(s.indexOf(".") + 1, s.indexOf(",")));
                int b = Integer.parseInt(s.substring(s.indexOf(",") + 1, s.indexOf("_")));
                int c = Integer.parseInt(s.substring(s.indexOf("_") + 1));
                
                moveCard(a, b, c);
                break;
            }
            default: {
                System.out.println("Something was sent, but it didnt match any arguments");
                break;
            }
        }
    }
    
    
    private void syncCardData() {
        hand2.getChildren().clear();
        handImages2.clear();
        
        //Refreshes hand2
        for (Card c : handCards2) {
            
            ImageView x = new ImageView(new Image(c.getCardPath()));
            
            
            x.setOnMouseClicked((event) -> {
                for (ImageView im : handImages2) {
                    if(im.equals(event.getSource())) {
                        selected = handCards2.get(handImages2.indexOf(im));
                        selectedCard.setImage(new Image(selected.getCardPath()));
                        selectedPile = null;
                    }
                }
                updateScreen(event);
            });
            
            handImages2.add(x);
            hand2.addRow(0, x); 
        }
        
        
        //                                        Remove -1 once you add corners into play
        //Syncs all the card pile data           ↙ 
        for(int l = 0; l < allPiles.size() /**- 1**/; l++) {
            
            
            //If card pile is corners
            if (l == 4) {
                for (int c = 0; c < allPilesCards.get(4).size(); c++) {
                    
                    // Stupid comparison, keep it, you need to check if null otherwise you get an error 
                    // --> You can make into try catch later, keep if-else for now
                    if (cornersCards.get(c) == null) {
                        corners.get(c).setImage(null);  // <-- FIX THIS SOMETHING IS WRONG HERE
                    }
                    else {
                        corners.get(c).setImage(new Image(cornersCards.get(c).getCardPath()));
                    }
                }
                
                break; // Skip the rest of the stuffs because thats only for the other card checks, not the corners
            }
            
            
            for(int i = 0; i < allPiles.get(l).size(); i++) {
                
                //If there IS a card in that location, make the thingy VISABLE and CLICKABLE
                //If there is NO card in that location, make the thingy INVISABLE and UNCLICKABLE
                try {
                   allPiles.get(l).get(i).setImage(new Image(allPilesCards.get(l).get(i).getCardPath()));
                   allPiles.get(l).get(i).setVisible(true);
                }catch(IndexOutOfBoundsException e) {
                   allPiles.get(l).get(i).setImage(null);
                   allPiles.get(l).get(i).setVisible(false);
                } 
            }
        }
        
        for (int i = 0; i < 4; i++) {
            if (allPilesCards.get(i).isEmpty()) {
                allPiles.get(i).get(0).setImage(null);
                allPiles.get(i).get(0).setVisible(true);
                
                allPiles.get(i).get(0).setOnMouseClicked((event) -> {
                    for (List<ImageView> l : allPiles) {
                        for (ImageView c : l) {
                            if (c.equals(event.getSource())) {
                                allPilesCards.get(allPiles.indexOf(l)).add(selected);
                                handCards2.remove(selected);
                                selected = null;
                                selectedPile = null;
                                syncCardData();
                                
                            }
                        }
                    }
                });
            }
            
            else {
//                allPiles.get(i).get(0).setImage(null);
//                allPiles.get(i).get(0).setVisible(true);
                
                allPiles.get(i).get(0).setOnMouseClicked((event) -> {
                    for (ImageView im : handImages2) {
                        if(im.equals(event.getSource())) {
                            selected = handCards2.get(handImages2.indexOf(im));
                            selectedCard.setImage(new Image(selected.getCardPath()));
                            selectedPile = null;
                        }
                    }
                    updateScreen(event);
                });
            }
            
        }
        
        
    }
    
    
    @FXML private void updateScreen(MouseEvent event) {
        
        syncCardData();
        
        if (event != null) {
            
            //Gets card selected
            ImageView selectedImage = (ImageView) event.getSource();
            
            
            
            
            for (List<ImageView> l : allPiles) {
                for (ImageView i : l) {
                    if (i.equals(selectedImage)) {

                        //Run if a card is currently held
                        if (selected != null) {

                            //Run if a corner is clicked
                            if (allPiles.indexOf(l) == 4) {
                                System.out.println("");
                                System.out.println("Corner Clicked");

                                //Runs if no card is in the corner clicked
                                if (cornersCards.get(l.indexOf(i)) == null) {

                                    //run if card is a King
                                    if (selected.getCardNumber() == 13) {
                                        cornersCards.set(l.indexOf(i), selected);
                                        String out = "";
                                        out += "moveCard.5" + l.indexOf(i) + ",";
                                        
                                        try {
                                                selectedPile.remove(selected);
                                                out += allPilesCards.indexOf(selectedPile) + "_" + l.indexOf(selectedImage);
                                            }
                                        catch (NullPointerException err) {
                                            System.out.println("Card is from hand");
                                            out += "4400_" + handCards2.indexOf(selected);
                                            handCards2.remove(selected);
                                            hand2.getChildren().remove(selectedImage);
                                        }
                                        
                                        output.println(out);
                                        output.flush();
                                        selected = null;
                                        syncCardData();
                                        System.out.println("Placed a king in the corner");
                                        
                                    }
                                    else {
                                        System.out.println("Card is not a king");
                                    }
                                }


                                //Runs if there IS a card in the corner clicked
                                else if (cornersCards.get(l.indexOf(i)) != null) {

                                    //Runs if the card held is one number smaller than the number placed currently
                                    if ((Integer.parseInt(String.valueOf(selected.getCardSuit().substring(2))) + 1) == (Integer.parseInt(String.valueOf(cornersCards.get(l.indexOf(i)).getCardSuit().substring(2))))) {
                                        System.out.println("Card is the right number");
                                        String out = "";
                                        out += "moveCard." + allPiles.indexOf(l) + ",";

                                        //Runs if card held is the opposite color
                                        if (!(String.valueOf(selected.getCardSuit().charAt(0)).equals(String.valueOf(cornersCards.get(l.indexOf(i)).getCardSuit().charAt(0))))) {
                                            cornersCards.set(l.indexOf(i), selected);
                                            
                                            try {
                                                selectedPile.remove(selected);
                                                out += allPilesCards.indexOf(selectedPile) + "_" + l.indexOf(selectedImage);
                                            }
                                            catch (NullPointerException err) {
                                                System.out.println("Card is from hand");
                                                out += "4400_" + handCards2.indexOf(selected);
                                                handCards2.remove(selected);
                                                hand2.getChildren().remove(selectedImage); 
                                            }
                                            
                                            output.println(out);
                                            output.flush();
                                            selected = null;
                                            syncCardData();
                                            System.out.println("Card is the correct color");
                                            return;

                                        }
                                        else {
                                            System.out.println("Card is not the right color");
                                        }
                                    }
                                    else {
                                        System.out.println("Card is not the right number");
                                    }
                                }
                            }

                            //Runs if a regular pile is clicked
                            else {
                                Card cardToMoveOn = allPilesCards.get(allPiles.indexOf(l)).get(l.indexOf(i));
                                System.out.println("Move --> " + selected.getCardSuit() + " onto " + cardToMoveOn.getCardSuit());

                                //Run if the Value is one less then the card moving on top of, as well as opposite color
                                if ((Integer.parseInt(String.valueOf(selected.getCardSuit().substring(2))) + 1) == Integer.parseInt(String.valueOf(cardToMoveOn.getCardSuit().substring(2)))) {
                                    System.out.println("Card is the right number");
                                    String out = "";
                                    out += "moveCard." + allPiles.indexOf(l) + ",";
                                    
                                    if (!((String.valueOf(selected.getCardSuit().charAt(0))).equals(String.valueOf(cardToMoveOn.getCardSuit().charAt(0))))) {
                                        allPilesCards.get(allPiles.indexOf(l)).add(selected);
                                        
                                        
                                        try {
                                            out += allPilesCards.indexOf(selectedPile) + "_" + selectedPile.indexOf(selected);
                                            selectedPile.remove(selected);
                                            
                                        }
                                        catch (NullPointerException err) {
                                            System.out.println("Card is from hand");
                                            out += "4400_" + handCards2.indexOf(selected);
                                            handCards2.remove(selected);
                                            hand2.getChildren().clear();
                                        }
                                        
                                        output.println(out);
                                        output.flush();
                                        selected = null;
                                        selectedCard.setImage(null);
                                        syncCardData();
                                        return;
                                    }
                                }
                                //Run if the move is invalid, will reselect card
                                else {
                                    selected = null;
                                    selectedCard.setImage(null);
                                }
                            }   
                        }

                        // Runs if no card is currently selected
                        if ((selected == null) && ((allPiles.indexOf(l)) < 4)) {

                            //Sets selected card
                            try {
                                selected = allPilesCards.get(allPiles.indexOf(l)).get(l.indexOf(i));
                                selectedPile = allPilesCards.get(allPiles.indexOf(l));
                                selectedCard.setImage(selectedImage.getImage());
                            } catch(ArrayIndexOutOfBoundsException e) {
                                System.out.println("Stack clicked is empty --> Choose again");
                                selected = null;
                            }
                        }
                    }
                }
            }   
        }
        
        syncCardData();
        System.out.println("\nEnd move\n");
    }
    
    
    @FXML private void deselectCard(ActionEvent event) {
        selected = null;
        selectedCard.setImage(null);
    }
    
    
    @FXML private void startGame(ActionEvent event) {
        
        Platform.runLater(() -> {
            
            
            if (clientService != null) {
                if (clientService.isBound()) {
                    restartGame(null);  
                    updateScreen(null);
                }
            }
        });
    }
    
    
    @FXML private void restartGame(ActionEvent event) {
        rand = new Random(seed);
        
        //Populates Deck
        deck.clear();
        for (int i = 01; i < 14; i++){
            deck.add(new Card("BC" + Integer.toString(i)));
            deck.add(new Card("BS" + Integer.toString(i)));
            deck.add(new Card("RH" + Integer.toString(i)));
            deck.add(new Card("RD" + Integer.toString(i)));
        }
        
        //Populate Lists with ImageView info
        topPile = Arrays.asList(imageDown1, imageDown2, imageDown3, imageDown4, imageDown5, imageDown6, imageDown7, imageDown8, imageDown9, imageDown10, imageDown11, imageDown12, imageDown13);
        leftPile = Arrays.asList(imageLeft1, imageLeft2, imageLeft3, imageLeft4, imageLeft5, imageLeft6, imageLeft7, imageLeft8, imageLeft9, imageLeft10, imageLeft11, imageLeft12, imageLeft13);
        bottomPile = Arrays.asList(imageUp1, imageUp2, imageUp3, imageUp4, imageUp5, imageUp6, imageUp7, imageUp8, imageUp9, imageUp10, imageUp11, imageUp12, imageUp13);
        rightPile = Arrays.asList(imageRight1, imageRight2, imageRight3, imageRight4, imageRight5, imageRight6, imageRight7, imageRight8, imageRight9, imageRight10, imageRight11, imageRight12, imageRight13);
        corners = Arrays.asList(imageTopLeft, imageTopRight, imageBottomLeft, imageBottomRight);
        allPiles = Arrays.asList(topPile, leftPile, bottomPile, rightPile, corners);
        
        //Sets card manager
        allPilesCards = Arrays.asList(topPileCards, leftPileCards, bottomPileCards, rightPileCards, cornersCards);
        cornersCards = Arrays.asList(null, null, null, null);
        
        //Wipes images and clears card data
        for(int l = 0; l < allPiles.size(); l++) {
            if (l < 4) { allPilesCards.get(l).clear(); }
            
            for(int i = 0; i < allPiles.get(l).size(); i++) {
                allPiles.get(l).get(i).setImage(null);
                allPiles.get(l).get(i).setOnMouseClicked((MouseEvent e) -> {
                    updateScreen(e);
                });
            }
        }
        
        //Deal cards
        for (int i = 0; i < 4; i++) {
            int random = (int)(rand.nextInt(deck.size()));
            System.out.println(random);
            Card temp = deck.get(random);
            allPilesCards.get(i).add(0, temp);
            deck.remove(random);
        }
        
        handImages2.clear();
        handCards2.clear();
        hand2.getChildren().clear();
        
        handImages1.clear();
        handCards1.clear();
        hand1.getChildren().clear();
        
        
        //Server Draws first
        for (int i = 0; i < 7; i++) {
            opponentDrawCard();
        }
        
        //Then other
        for (int i = 0; i < 7; i++) {
            drawCard();
        }

        
        //Resets selections
        selected = null;
        selectedCard.setImage(null);
        selectedPile = null;
    }
    
    
    private void drawCard() {
        int random = (int) (rand.nextInt(deck.size()));
        Card temp = deck.get(random);
        handCards2.add(temp);
        deck.remove(temp);
        ImageView x = new ImageView(new Image(temp.getCardPath()));
        handImages2.add(x);

        x.setOnMouseClicked((event) -> {
            for (ImageView i : handImages2) {
                if (i.equals((ImageView) event.getSource())) {
                    selected = handCards2.get(handImages2.indexOf(i));
                    selectedCard.setImage(new Image(selected.getCardPath()));

                    updateScreen(event);
                }
            }
        });

        hand2.addRow(0, x);
    }
    
    
    private void opponentDrawCard() {
        int random = (int) (rand.nextInt(deck.size()));
        Card temp = deck.get(random);
        handCards1.add(temp);
        deck.remove(temp);
        
//        hand1.addRow(0, new ImageView(new Image("/cards/BACK-3.jpg")));
        hand1.addRow(0, new ImageView(new Image(temp.getCardPath())));     
    }
    
    
    @FXML private void endTurn() {

        Platform.runLater(() -> {        
            selected = null;
            selectedCard.setImage(null);
            selectedPile = null;

            
            //My turn
            if (activePlayer == 1) {
                activePlayer = 2;
                disableScreen.setVisible(false);
                drawCard();
            }
            //Not my turn
            else if (activePlayer == 2) {
                activePlayer = 1;
                disableScreen.setVisible(true);
                opponentDrawCard();
                
                output.println("nextTurn.");
                output.flush();
            }    
        });
    }
    
    
    private void moveCard(int NewAllArrayPos, int allArrayPos, int arrayPos) {
        Platform.runLater(() -> {
            
            //From the hand
            if (allArrayPos == 4400) {
                allPilesCards.get(NewAllArrayPos).add(handCards1.get(arrayPos));
                hand1.getChildren().remove(arrayPos);
                handCards1.remove(arrayPos);
            }
            else if (allArrayPos/10 == 5) {
                allPilesCards.get(4).set((allArrayPos % 10), selected);
                allPilesCards.get(4).remove(arrayPos);
            }
            else {
                allPilesCards.get(NewAllArrayPos).add(allPilesCards.get(allArrayPos).get(arrayPos));
                allPilesCards.get(allArrayPos).remove(arrayPos);
                System.out.println("Move");
            }
            
                syncCardData();
        });
    }
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        disableScreen.setVisible(true);
    }    
    
}
