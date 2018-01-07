package cash.register;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class RegisterController implements Initializable {
    
    //Array of Amounts of foods purchased
        //[0] Burgers {$6.25}
        //[1] Shake   {$8.50}
        //[2] Beef    {$6.00}
        //[3] Fries   {$4.75}
    public int[] food = {0, 0, 0, 0};
    public double total;
    public double amountMoney;
    
    
    @FXML
    private Label lblTotal, lblChange;
    
    @FXML
    private TextField cashInput;
    
    @FXML
    private Button button;
    
    
    @FXML
    private void handlePay(ActionEvent event) { 
        button.setVisible(false);
        
        total = 0.0;
        amountMoney = 0.0;
        
        
        if ((cashInput.getText()).equals("")) {
            cashInput.setText("0");
            
        }
        
        double moneyPayAmount = Double.parseDouble(cashInput.getText());
//        double moneyToReturn = getPayment(moneyPayAmount);
        
        calcTotal();
        
        int twenties;
        int tens;
        int fives;
        int singles;
        int quarters;
        int dimes;
        int nickles;
        int pennies;
        
        //Check if order is larger than $1000, then if it is, resets it
        if (total >= 100) {
            lblTotal.setText("I'm sorry, we dont take orders that large");
            
            int i = 0;
            while(i < 3) {
                food[i] = 0;
                i += 1;
            }
            
        }
        
        
        
        
        
        boolean validPayment = false;
        if (total <= moneyPayAmount) {
            validPayment = true;
            
        }
 
        
        total = (getPayment(moneyPayAmount) * -1);
        
        System.out.println(total);
        
        
//        System.out.println("\nTotal Before change " + total);
//        total = ((Math.round(total * 100.0)) / 100.0);
//        System.out.println("after " + total);
//        
//        System.out.println(total + " Start");
//        
//
//        twenties = ((int) total) / 20;
//        total -= (20.0 * twenties);
//        System.out.println(total + " 20");
//        
//        tens = ((int)total) / 10;
//        total -= (10.0 * tens);
//        System.out.println(total + " 10");
//        
//        fives = ((int)total) / 5;
//        total -= (5.0 * fives);
//        System.out.println(total + " 5");
//                
//        singles = ((int)total);
//        total -= (1 * singles);
//        System.out.println(total + " 1");
//        
//        quarters = (int) (total / 0.25);
//        total -= (0.25 * quarters);
//        System.out.println(total + " .25");
//        
//        dimes = (int) (total / 0.10);
//        total -= (0.10 * dimes);
//        System.out.println(total + " .10");
//        
//        nickles = (int) (total / 0.05);
//        total -= (0.05 * nickles);
//        System.out.println(total + " .05");
//        
//        pennies = (int)(total * 100);
//        System.out.println(total + " .01");
        
        

        total = (Math.round(total * 100.0));
        
        int intTotal = (int) total;
        
        twenties = intTotal / 2000;
        intTotal -= 2000 * twenties;
        
        tens = intTotal / 1000;
        intTotal -= 1000 * tens;

        fives = intTotal / 500;
        intTotal -= 500 * fives;

        singles = intTotal / 100;
        intTotal -= 100 * singles;

        quarters = intTotal / 25;
        intTotal -= 25 * quarters;
        
        dimes = intTotal / 10;
        intTotal -= 10 * dimes;
        
        nickles = intTotal / 5;
        intTotal -= 5 * nickles;
        
        pennies = intTotal / 1;
        intTotal -= 1 * pennies;
        
        
        
        
        
//        System.out.println("Twenties: " + twenties + ", Tens: " + tens + ", Singles: " + singles + 
//                ", \nQuarters: " + quarters + ", Dimes: " + dimes + ", Nickles: " + nickles +
//                ", Pennies: " + pennies);
        
        lblChange.setText("Twenties: " + twenties + ", Tens: " + tens + ", Fives: " + fives + ", Singles: " + singles + 
                ", \nQuarters: " + quarters + ", Dimes: " + dimes + ", Nickles: " + nickles +
                ", Pennies: " + pennies);
        
        if (!validPayment) {
            lblChange.setText("Sorry, that's not enough");
            
        }
        
        total = 0;
        
        
        
        
        
    }
    
    
    @FXML
    private void handleBurger(ActionEvent event) { 
        button.setVisible(true);
        System.out.println("Add Burger");
        food[0] += 1;
        calcTotal();
        lblChange.setText("");
        
    }
    
    
    @FXML
    private void handleShake(ActionEvent event) { 
        button.setVisible(true);
        System.out.println("Add Shake");
        food[1] += 1;
        calcTotal();
        lblChange.setText("");
        
    }
    
    
    @FXML
    private void handleBeef(ActionEvent event) { 
        button.setVisible(true);
        System.out.println("Add Beef");
        food[2] += 1;
        calcTotal();
        lblChange.setText("");
        
    }
    
    
    @FXML
    private void handleFries(ActionEvent event) { 
        button.setVisible(true);
        System.out.println("Add Fries");
        food[3] += 1;
        calcTotal();
        lblChange.setText("");
        
    }
    
    
    private void calcTotal() {
        total = 0.0;
        total = ((food[0] * 6.25) + (food[1] * 8.50) + (food[2] * 6.00) + (food[3] * 4.75));
        total *= 1.0625; //Adds tax
        total = ((Math.round(total * 100.0)) / 100.0); //rounds to cent
        
        lblTotal.setText("Total: $" + total);
//        System.out.println(total);
    }
    
    public double getPayment(double amount) {

        
        if (amount < total) {
            lblChange.setText("Sorry, that's not enough");
            return 0.0;
            
        }
        
        else {
            return (total - amount);
            
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    }    
    
}
