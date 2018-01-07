/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentbank;

import investmentbank.Stocks.*;
import java.util.ArrayList;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class person {
    
    
    private String name;
    private double balance;
    private ArrayList<Stock> stockList;
    public ArrayList<Integer> quantityOwned = new ArrayList<>();
    
    
    /** Creates a new person
     *
     * @param n The persons name
     * @param b The starting balance of the person
     * @param stocks The full list of stocks (as a reference)
     */
    public person(String n, double b, ArrayList<Stock> stocks){
        name = n;
        balance = b;
        stockList = stocks;
        
        for (Stock x : stocks) {
            quantityOwned.add(0);
        }
    }
    
    
    /** Creates a new person
     *
     * @param n The persons name
     * @param b The starting balance of the person
     * @param stocks The full list of stocks (as a reference)
     * @param amountOfStocks The amount of each stock a person has (must be the same size as the stocks list)
     */
    public person(String n, double b, ArrayList<Stock> stocks, ArrayList<Integer> amountOfStocks){
        name = n;
        balance = b;
        stockList = stocks;
        quantityOwned = amountOfStocks;

    }
    
    
    /** Get the persons name
     *
     * @return The persons name
     */
    public String getName() {
        return name;
        
    }
    
    
    /** Get the persons current balance
     *
     * @return The balance of which the person has
     */
    public double getBalance() {
        return balance;
        
    }
    
    
    /** Adds a given amount of a stocks the the users portfolio
     * 
     * @param stockName Must be a valid stock name, (E.x: stockList.get(3).getName)
     * @param amountBought The amount bought
     * @param stockValue The current value of the stock (E.x: stockList.get(3).getValue())
     * 
     */
    public void addStock(String stockName, int amountBought, double stockValue) {
        
        int indexOfStock = 0;
        for (Stock stocks : stockList) {
            if(stocks.getName().equals(stockName)) {
                indexOfStock = stockList.indexOf(stocks);
                break;
            }
        }
        
        quantityOwned.set(indexOfStock, quantityOwned.get(indexOfStock) + amountBought);
        balance -= (amountBought * stockValue);
        
        
    }
    
    
    /** Adds a given amount of a stocks the the users portfolio
     * 
     * @param stockName Must be a valid stock name, (E.x: stockList.get(3).getName)
     * @param amountSold The amount sold
     * @param stockValue The current value of the stock (E.x: stockList.get(3).getValue())
     * 
     */
    public void sellStock(String stockName, int amountSold, double stockValue) {
        
        int indexOfStock = 0;
        for (Stock stocks : stockList) {
            if(stocks.getName().equals(stockName)) {
                indexOfStock = stockList.indexOf(stocks);
                break;
            }
        }
        
        quantityOwned.set(indexOfStock, quantityOwned.get(indexOfStock) - amountSold);
        balance += (amountSold * stockValue);
        
    }
    
    
    /** The total shares owned of a specified stock
     *
     * @param stockName Must be a valid stock name, (E.x: stockList.get(3).getName)
     * @return The amount of shares a the person has of the company
     */
    public int getStock(String stockName) {
        System.out.println(quantityOwned.size());
        
        for (Stock stocks : stockList) {
            if(stocks.getName().equals(stockName)) {
                return quantityOwned.get(stockList.indexOf(stocks));
            }
        }
        
        
        return 0;
    }
    
    
}
