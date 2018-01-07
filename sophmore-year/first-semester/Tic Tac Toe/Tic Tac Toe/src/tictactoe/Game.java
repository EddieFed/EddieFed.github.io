/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author <Eddiefed.com>
 */
public class Game {
    
    Scanner input = new Scanner(System.in);
    private String board[][] = new String[3][5];
    private String pName[] = new String[2];
    private int pTurn;
    private int turns;
    private int choice;
    boolean over = false;
    
    
    
    public Game() {
        set();
        
    }
    
    
    public void play() {
        
        // While no winner is found 
        while(!over) {
            printScreen("valid");
            
//            while((turn(Integer.parseInt(input.next())))) {    } // Runs a players turn
            
            int p_Input = 0;
            while(turn(p_Input)) {
                p_Input = Integer.parseInt(input.next());
                
            }
                
            if(pTurn == 1) {pTurn = 0;}      // Switches player
            else if(pTurn == 0) {pTurn = 1;} //
            
            if(getWinner().equals(pName[0]) || getWinner().equals(pName[1])) {
                over = true;
            }
            
        }
        
        printScreen("valid");
        
    }
    
    
    private void set() {
        System.out.println("New Game...");
        System.out.println("Simply play tic-tac-toe.");
        System.out.println("If you input a bad space,\nit will ask for a new one until your input is valid.");
        
        turns = 0; // No turns have been taken
        over = false;
        
//        System.out.println(board.length);
//        System.out.println(board[0].length);

        // Sets all the spaces to their correct numbers.
        board[0][0]="1";
        board[0][1]="2";
        board[0][2]="3";
        board[1][0]="4";
        board[1][1]="5";
        board[1][2]="6";
        board[2][0]="7";
        board[2][1]="8";
        board[2][2]="9";
        
        
        // Gets the player name from the console
        System.out.println("X enter your name: ");
        pName[0] = input.next();
        
        System.out.println("O enter your name: ");
        pName[1] = input.next();
        
        pTurn = (int)(Math.random()*2); // Chooses a random player to go first
        System.out.println(pName[pTurn] + " has been chosen to go first.");
        
        play();
    }
    
    
    // Fills in the array with the appropriate stuff
    public boolean fillSpace(int x, int y) {
        
        if(board[x][y].equals("X") || board[x][y].equals("O")) {
            printScreen("invalid");
            
            return true;
            
        }
        
        else {
            if(pTurn == 0) {
                board[x][y] = "X";
            }
            if(pTurn == 1) {
                board[x][y] = "O";
            }
            
            return false;
            
        }
    }
    
    
    public boolean turn(int in) {
        System.out.println(in);
        boolean response;
        
        switch(in) {
            
            // Looks at which number = which space,
            // then runs the input method for each space.
            case 1: {response = fillSpace(0,0); break;}
            case 2: {response = fillSpace(0,1); break;}
            case 3: {response = fillSpace(0,2); break;}
            case 4: {response = fillSpace(1,0); break;}
            case 5: {response = fillSpace(1,1); break;}
            case 6: {response = fillSpace(1,2); break;}
            case 7: {response = fillSpace(2,0); break;}
            case 8: {response = fillSpace(2,1); break;}
            case 9: {response = fillSpace(2,2); break;}
            default: {response = true; break;}
            
        }
        
        //Tells the play() method if the turn was valid or not
        return response;
        
    }
    
    // Check up/down with loop
    public String getWinner() {
        
        for(int i = 0; i < 3; i++) {
            if((board[i][0].equals("X") &&  board[i][1].equals("X") && board[i][2].equals("X")) || (board[0][i].equals("X") &&  board[1][i].equals("X") && board[2][i].equals("X"))) {
                return pName[0];
            }
            if((board[i][0].equals("O") &&  board[i][1].equals("O") && board[i][2].equals("O")) || (board[0][i].equals("O") &&  board[1][i].equals("O") && board[2][i].equals("O"))) {
                return pName[1];
            }
        }
        
        if((board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X"))  || (board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X"))) {
            return pName[0];
        }
        if((board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O"))  || (board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O"))) {
            return pName[1];
        }
        
        
        
        
        return "temp"; // Temporary code
      
    }
    
    
    //Prints the board
    public void boardOut() {
        
        for(int i=0;i<3;i++) {
            System.out.println();
            
            for(int j=0;j<3;j++) {
                System.out.print("|");
                System.out.print(board[i][j]);
                System.out.print("|");
                
            }
        }
    }
    
    // Prints lines
    public void printScreen(String y) {
        for(int i = 0; i < 30; i++) {System.out.print("\n");}
            
        if(!over) {
            System.out.print(pName[pTurn] + ", your move!");

            if(y.equals("invalid")) {
                System.out.println("\nNot a valid play");
            }
            else {
                System.out.println("\n");
            }
        }
        
        else {
            System.out.println(getWinner() + " is the winner!");
        }
        boardOut();
        
        for(int i = 0; i < 5; i++) {System.out.print("\n");}

    }
}
