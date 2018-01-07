/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentbank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import investmentbank.Stocks.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLController implements Initializable {
    
    // Initialization of variables
    private int day = 1;
    private person activeUser;
    boolean overrideNames = false;
    
    private ObservableList<person> peopleList = FXCollections.observableArrayList();
    private ArrayList<Stock> stocksList = new ArrayList<>();
    
    @FXML private Label lblName, lblTypeName, lblBalance, lblStockValue, lblSelectStockValue, lblSelectStockOwned;
    @FXML private TextField txtName, txtBuyStock, txtSellStock, txtNewName, txtNewGrowth, txtNewValue;
    @FXML private AnchorPane registerPage, namePage, infoPage, balanceLow, newStock;
    @FXML private ListView peopleListView, stocksListView;
    
    
    // FXML Functions:
    // Shows a register page
    @FXML private void handleRegister(ActionEvent event) {
        registerPage.setVisible(false);
        namePage.setVisible(true);
        infoPage.setVisible(false);
        
    }
    
    
    // Asks for a name to be inputed (must not be taken)
    @FXML private void setName(ActionEvent event) {
            ObservableList<String> names = FXCollections.observableArrayList();
            for (person peoples : peopleList) {
                names.add(peoples.getName());

            }

            if ((txtName.getText()).isEmpty()) {
                lblTypeName.setText("Please type a name");

            }

            else if (names.contains(txtName.getText())) {
                lblTypeName.setText("That name is taken, try again");

            }

            else {
                peopleList.add(new person(txtName.getText(), 500.0, stocksList));
                activeUser = peopleList.get(peopleList.size() - 1);

                peopleListView.getItems().clear();
                for (person peoples : peopleList) {

                    peopleListView.getItems().add(peoples.getName());

                }


                namePage.setVisible(false);
                infoPage.setVisible(true);
                lblTypeName.setText("Type your name");

            }
        
        
        
        
        
        
    }
    
    
    // Changes the active user when the ListView is clicked
    @FXML private void changeUser(MouseEvent event) {

        for (person selectedPerson : peopleList) {
            if ((peopleListView.getSelectionModel().getSelectedItem().toString()).equals(selectedPerson.getName())) {
                activeUser = selectedPerson;
                System.out.println("Active user selected: " + activeUser.getName());
                lblName.setText(activeUser.getName());
                
                updateLabels();
                
                
            }
        }
    }
    
    
    // Changes the viewed stock when the ListView is clicked
    @FXML private void changeStockSelect(MouseEvent event) {

        for (Stock selectedStock : stocksList) {
            if ((stocksListView.getSelectionModel().getSelectedItem().toString()).equals(selectedStock.getName())) {
                System.out.println(selectedStock.getName() + "'s info is being viewed");
                lblSelectStockOwned.setText(activeUser.getStock(selectedStock.getName()) + "");
                lblSelectStockValue.setText(selectedStock.getValue() + "");
                
            }
        }
    }
    
    
    // Clears name data and calls for handleRegister()
    @FXML private void handleNewName(ActionEvent event) {
        txtName.setText("");
        handleRegister(null);
        
    }
    
    
    // Adds a new stock toe the stockList
    @FXML private void handleNewStock(ActionEvent event) {
        infoPage.setVisible(false);
        newStock.setVisible(true);
        
    }
    
    
    // Handles when a stock is bought
    @FXML private void handleBuy(ActionEvent event) {
        if ((stocksList.get(indexOfSelected()).getValue() * Integer.parseInt(txtBuyStock.getText())) <= activeUser.getBalance()) {
            activeUser.addStock(stocksListView.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(txtBuyStock.getText()),  stocksList.get(indexOfSelected()).getValue());
            updateLabels();
            
        }
        
        else {
            showBalanceLow();
        }
        
    }
    
    
    // Handles when a stock is sold
    @FXML private void handleSell(ActionEvent event) {
        if((activeUser.getStock(stocksList.get(indexOfSelected()).getName()) - Integer.parseInt(txtSellStock.getText())) > 0) {
            activeUser.sellStock(stocksListView.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(txtSellStock.getText()), stocksList.get(indexOfSelected()).getValue());
            updateLabels();
            
        }
        
        else {
            
        }
        
    }
    
    
    // Hides the balance low popup
    @FXML private void hideBalanceLow(ActionEvent event) {
        infoPage.setVisible(true);
        balanceLow.setVisible(false);
        
    }
    
    
    // Hides the new stock popup
    @FXML private void hideNewStock(ActionEvent event) {
        infoPage.setVisible(true);
        newStock.setVisible(false);
        
        stocksList.add(new Stock(Integer.parseInt(txtNewValue.getText()), Double.parseDouble(txtNewGrowth.getText()), txtNewName.getText()));
        stocksListView.getItems().add(txtNewName.getText());
        
        for(person persons : peopleList) {
            peopleList.get(peopleList.indexOf(persons)).quantityOwned.add(0);
        }
        
        txtNewName.setText("");
        txtNewGrowth.setText("");
        txtNewValue.setText("");
        
    }
    
    
    // Runs a day cycle and updates stock values
    @FXML private void handleRunDayCycle(ActionEvent event) {
        for(Stock x : stocksList) {
            stocksList.get(stocksList.indexOf(x)).nextDay();
            
        }
        
        totalStockValue();
        System.out.println("Day Advanced");
        
    }
    
    
    @FXML private void saveToFile(ActionEvent event) {
        
        try {
            try (PrintWriter out = new PrintWriter("src\\investmentbank\\files\\profiles.txt")) {
                for(person persons : peopleList) {
                    out.println(persons.getName());
                    out.println(persons.getBalance());
                    
                    String tempStockList = "";
                    for(Stock stocks : stocksList) {
                        tempStockList += (persons.getStock(stocks.getName()) + ",");
                    }
                    out.println(tempStockList);  
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong! \\\\ Writing to profiles");
            
        }
        
        
        try {
            try (PrintWriter out = new PrintWriter("src\\investmentbank\\files\\profiles.txt")) {
                for(Stock stocks : stocksList) {
                    out.println(stocks.getName());
                    out.println(stocks.getValue());
                    out.println(stocks.getGrowth());
                    
                }
                
                
                
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong! \\\\ Writing to stocks");
            
        }
        
        
    }
    
    
    // Functions:
    // Gets the index of the selected Stock
    private int indexOfSelected() {
        for (Stock x : stocksList) {
            if (x.getName().equals(stocksListView.getSelectionModel().getSelectedItem().toString())) {
                return stocksList.indexOf(x);
            } 
        } 
        return -1;
    }
    
    
    // Updates stock information labels
    private void updateLabels() {
        lblSelectStockValue.setText(stocksList.get(indexOfSelected()).getValue() + "");
        lblSelectStockOwned.setText(activeUser.getStock(stocksListView.getSelectionModel().getSelectedItem().toString()) + "");
        lblBalance.setText(activeUser.getBalance() + "");
        totalStockValue();
    
    }
    
    
    // Updates the value of all the users owned stocks
    private void totalStockValue() {
        double tempTotal = 0;
        for (Stock y : stocksList) {
            tempTotal += y.getValue() * activeUser.getStock(y.getName());
        }

        if(tempTotal == 0) {
            lblStockValue.setText("_____");

        }
        else {
            lblStockValue.setText(tempTotal + "");

        }

    }
    
    
    // Shows when the users balance is too low
    private void showBalanceLow() {
        infoPage.setVisible(false);
        balanceLow.setVisible(true);
        
    }
    
    
    // Overrides the FXML and checks to see if there is a file
    @Override public void initialize(URL url, ResourceBundle rb) {
        
        int load = JOptionPane.showConfirmDialog(null, "Load from the file?", "Load", JOptionPane.YES_NO_OPTION);
        
        
        //Check for a file
        if(load == 0) {
            System.out.println("yes");
            
            try{
                FileReader reader = new FileReader("src\\investmentbank\\files\\stocks.txt");
                Scanner in = new Scanner(reader);
                while(in.hasNextLine()) {
                    String tempName = in.nextLine();
                    String tempValue = in.nextLine();
                    String tempGrowth = in.nextLine();
                    
                    stocksList.add(new Stock(Integer.parseInt(tempValue), Double.parseDouble(tempGrowth), tempName));
                    
                    
                    
                }
            } catch (FileNotFoundException ex) {
                System.out.println("SOMETHING HAS GONE HORRIBLY WRONG WE'RE ALL GONNA DIE!");
            }
            
            
            
            
            
            try{
                FileReader reader = new FileReader("src\\investmentbank\\files\\profiles.txt");
                Scanner in = new Scanner(reader);
                while(in.hasNextLine()) {
                    String tempName = in.nextLine();
                    Double tempBalance = Double.parseDouble(in.nextLine());
                    String tempAmounts = in.nextLine();
                    
                    ArrayList<Integer> tempAmountsList = new ArrayList<>();
                    
                    while(tempAmounts.contains(",")) {
                        tempAmountsList.add(Integer.parseInt(tempAmounts.substring(0, tempAmounts.indexOf(","))));
                        
                        tempAmounts = tempAmounts.substring(tempAmounts.indexOf(",") + 1);
                        System.out.println(tempAmounts);
                        System.out.println(tempAmountsList);
                    }
                    
                    peopleList.add(new person(tempName, tempBalance, stocksList, tempAmountsList));
                    System.out.println(peopleList.get(0).getName());
                    
                    
                }
            } catch (FileNotFoundException ex) {
                System.out.println("SOMETHING HAS GONE HORRIBLY WRONG WE'RE ALL GONNA DIE!");
            }
            overrideNames = true;
            
        }
        
        
        
        
        //If there is no file to load from, or no is choosen, create a new dataset
        if(load == 1) {
            System.out.println("No");
            
            stocksList.add(new Stock(20, .7, "Apple"));
            stocksList.add(new Stock(25, .75, "Google"));
            stocksList.add(new Stock(30, .80, "Microsoft"));
            stocksList.add(new Stock(30, .80, "LG"));
            peopleList.add(new person("Jake", 550.0, stocksList));
            
            registerPage.setVisible(true);
            namePage.setVisible(false);
            infoPage.setVisible(false);
            balanceLow.setVisible(false);
        }
        
        
        peopleListView.getItems().clear();
        for (person persons : peopleList) {

            peopleListView.getItems().add(persons.getName());

        }
        peopleListView.getSelectionModel().select(0);
        
        stocksListView.getItems().clear();
        for (Stock stocks : stocksList) {

            stocksListView.getItems().add(stocks.getName());

        }
        stocksListView.getSelectionModel().select(0);
        
        
    }
    
    
}
