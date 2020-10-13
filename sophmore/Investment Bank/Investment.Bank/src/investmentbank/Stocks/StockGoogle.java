/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentbank.Stocks;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class StockGoogle {
    
    private double value = 15; 
    
    /**
     * Creates a google stock with base value 15
     */
    public StockGoogle(){
        
    }
    
    /**
     * Adds a random int between 1 and 5 to the stock value
     */
    public void nextDay() {
        if(Math.random() < 0.75) {
            value += ((int) Math.floor((Math.random() * 6) + 1));
        }
        
        else {
            value -= ((int) Math.floor((Math.random() * 6) + 1));
        }
        
    }
    
    /**
     *
     * @return The value of the stock
     */
    public double getValue() {
        return (value);
    }
    
}
