/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentbank;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class person {
    
    private String name;
    private double balance;
    private int[] stocks = new int[] {0, 0, 0};
    
    
    public person(String n, double b){
        name = n;
        balance = b;
        
    }
    
    
    /**
     *
     * @return The persons name
     */
    public String getName() {
        return name;
        
    }
    
    
    /**
     *
     * @return The balance of which the person has
     */
    public double getBalance() {
        return balance;
        
    }
    
    
    /**
     * Method will add 1 to the total of the company stocks bought.
     * @param stockName must be either "apple", "google", or "microsoft"
     * @param amountBought the amount of shares bought
     * @param stockValue the value of the stock (e.x: google.getValue())
     * 
     */
    public void addStock(String stockName, int amountBought, double stockValue) {
        
        switch (stockName) {
            
            case "apple":
                balance -= (stockValue * amountBought);
                stocks[0] += (1 * amountBought);
                break;
                
            case "google":
                balance -= (stockValue * amountBought);
                stocks[1] += (1 * amountBought);
                break;
                
            case "microsoft":
                balance -= (stockValue * amountBought);
                stocks[2] += (1 * amountBought);
                break;
                
            default:
                break;
        }
        
    }
    
    
    /**
     * Method will add 1 to the total of the company stocks bought.
     * @param stockName must be either "apple", "google", or "microsoft"
     * @param amountSold the amount of shares sold
     * @param stockValue the value of the stock (e.x: google.getValue())
     * 
     */
    public void sellStock(String stockName, int amountSold, double stockValue) {
        
        switch (stockName) {
            
            case "apple":
                balance += (stockValue * amountSold);
                stocks[0] -= (1 * amountSold);
                break;
                
            case "google":
                balance += (stockValue * amountSold);
                stocks[1] -= (1 * amountSold);
                break;
                
            case "microsoft":
                balance += (stockValue * amountSold);
                stocks[2] -= (1 * amountSold);
                break;
                
            default:
                break;
        }
        
    }
    
    
    /**
     *
     * @param stockName must be either "apple", "google", or "microsoft"
     * @return The amount of stock the person has with that company
     */
    public int getStock(String stockName) {
        
        switch (stockName) {
            
            case "apple":
                return stocks[0];
                
            case "google":
                return stocks[1];
                
            case "microsoft":
                return stocks[2];
                
            default:
                return 0;
        }
        
    }
    
}
