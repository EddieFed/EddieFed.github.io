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
public class Stock {
    
    
    private double value;
    private double growthVal;
    private String stockName;
    
    
    /** Creates a stock
     * 
     * @param startVal The starting value
     * @param growth The growth percentage (70% --> 0.7)
     * @param name The Stock Name
     */
    public Stock(int startVal, double growth, String name ){
        stockName = name;
        growthVal = growth;
        value = startVal;
        
    }
    
    
    /** Generates a random number, if it's greater than the growth percentage, it goes up by a random number value between 1 and 5
     * 
     */
    public void nextDay() {
        if(Math.random() < growthVal) {
            value += ((int) Math.floor((Math.random() * 6) + 1));
        }
        
        else {
            value -= ((int) Math.floor((Math.random() * 6) + 1));
        }
        
    }
    
    
    /** Gets the current value of the stock specified
     *
     * @return The value of the stock
     */
    public double getValue() {
        return (value);
        
    }
    
    
    /** Gets the name of the stock specified
     *
     * @return The name of the stock
     */
    public String getName() {
        return (stockName);
        
    }
    
    
    /** Gets the growth percentage of the stock specified
     *
     * @return The growth percentage of the stock
     */
    public double getGrowth() {
        return (growthVal);
        
    }
    
    
}
