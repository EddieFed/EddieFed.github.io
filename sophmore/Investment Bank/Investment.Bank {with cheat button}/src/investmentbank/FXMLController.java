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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import investmentbank.Stocks.*;


//import java.util.Iterator;





/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLController implements Initializable {
    
    
    private int day = 1;
    private ObservableList<person> peopleList = FXCollections.observableArrayList();
    private person activeUser;
    private XYChart.Series appleGraph = new XYChart.Series<>();
    private XYChart.Series googleGraph = new XYChart.Series<>();
    private XYChart.Series microsoftGraph = new XYChart.Series<>();
    
    
    private StockApple apple = new StockApple();
    private StockGoogle google = new StockGoogle();
    private StockMicrosoft microsoft = new StockMicrosoft();
    
    
    @FXML private Label lblName, lblTypeName, lblBalance, lblStockValue, lblAppleBought, lblGoogleBought, lblMicrosoftBought, 
    lblAppleBought1, lblGoogleBought1, lblMicrosoftBought1, lblApple1, lblGoogle1, lblMicrosoft1, lblApple2, lblGoogle2, lblMicrosoft2, lblStocksOwnedList;
    @FXML private TextField txtName, txtAppleBuyRequest, txtGoogleBuyRequest, txtMicrosoftBuyRequest, txtAppleSellRequest, txtGoogleSellRequest, txtMicrosoftSellRequest;
    @FXML private AnchorPane registerPage, namePage, infoPage, balanceLow;
    @FXML private LineChart chartStock;
    @FXML private ListView peopleListView;
    
    
    
    
    
    //Will be used to register a new person
//    ****************************************************************************    //
    
    //Sets Stage to appropriate AnchorPane visibility
    @FXML private void handleRegister(ActionEvent event) {
        registerPage.setVisible(false);
        namePage.setVisible(true);
        infoPage.setVisible(false);
        
    }
    
    
    //Sets the name of the user as long as text has been inputed
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
            peopleList.add(new person(txtName.getText(), 500.0));
            activeUser = peopleList.get(peopleList.size() - 1);
            
            
//            lblName.setText(activeUser.getName());
//            lblBalance.setText(activeUser.getBalance() + "");
//            
            
            peopleListView.getItems().clear();
            for (person peoples : peopleList) {
                peopleListView.getItems().add(peoples.getName());

            }
            
            
            namePage.setVisible(false);
            infoPage.setVisible(true);
            lblTypeName.setText("Type your name");
            
        }
        
    }
    
    //Changes the active user deoending on who in the ListView
    @FXML private void changeUser(MouseEvent event) {

        for (person selectedPerson : peopleList) {
            if ((peopleListView.getSelectionModel().getSelectedItem().toString()).equals(selectedPerson.getName())) {
                System.out.println("\n\nActive user selected: " + activeUser.getName());
                activeUser = selectedPerson;
                lblName.setText(activeUser.getName());
                
                updateAppleLabels();
                updateGoogleLabels();
                updateMicrosoftLabels();
                
                
            }
        }
    }
    
    private void setSelectListView(int substringThing) {
        peopleListView.getSelectionModel().select(substringThing);
    }
    
//    ****************************************************************************    //
    //
    
    
    
    
    @FXML private void handleNewName(ActionEvent event) {
        txtName.setText("");
        handleRegister(null);
        
    }
    
    
    
    
    //Updates amount of stock a user has purchased when the user buys a share
//    ****************************************************************************    //
    
    
    //These three functions update all Apple labels and values
    @FXML private void handleBuyApple(ActionEvent event) {
        if ((apple.getValue() * Integer.parseInt(txtAppleBuyRequest.getText())) <= activeUser.getBalance()) {
            activeUser.addStock("apple", Integer.parseInt(txtAppleBuyRequest.getText()),  apple.getValue());
            updateAppleLabels();
            
        }
        
        else {
            showBalanceLow();
        }
        
        System.out.println("bought " + txtAppleBuyRequest.getText() + " shares of Apple");
        
    }
    
    @FXML private void handleSellApple(ActionEvent event) {
        if((activeUser.getStock("apple") - Integer.parseInt(txtAppleSellRequest.getText())) > 0) {
            activeUser.sellStock("apple", Integer.parseInt(txtAppleSellRequest.getText()), apple.getValue());
            updateAppleLabels();
            
        }
        
        else {  }
        
        System.out.println("sold " + txtAppleSellRequest.getText() + " shares of Apple");
        
    }
    
    private void updateAppleLabels() {
        lblAppleBought.setText(activeUser.getStock("apple") + "");
        lblAppleBought1.setText(activeUser.getStock("apple") + "");
        lblBalance.setText(activeUser.getBalance() + "");
        totalStockValue();
    
    }
    //
    
    
    //These three functions update all Google labels and values
    @FXML private void handleBuyGoogle(ActionEvent event) {
        if((google.getValue() * Integer.parseInt(txtGoogleBuyRequest.getText())) <= activeUser.getBalance()) {
            activeUser.addStock("google", Integer.parseInt(txtGoogleBuyRequest.getText()), google.getValue());
            updateGoogleLabels();

        }
        
        else {
            showBalanceLow();
        }
        
        System.out.println("bought " + txtGoogleBuyRequest.getText() + " shares of Google");
        
    }
    
    @FXML private void handleSellGoogle(ActionEvent event) {
        if((activeUser.getStock("google") - Integer.parseInt(txtGoogleSellRequest.getText())) > 0) {
            activeUser.sellStock("google", Integer.parseInt(txtGoogleSellRequest.getText()), google.getValue());
            updateGoogleLabels();
            
        }
        
        else {  }
        
        System.out.println("sold " + txtGoogleSellRequest.getText() + " shares of Google");
        
    }
    
    private void updateGoogleLabels() {
        lblGoogleBought.setText(activeUser.getStock("google") + "");
        lblGoogleBought1.setText(activeUser.getStock("google") + "");
        lblBalance.setText(activeUser.getBalance() + "");
        totalStockValue();
        
    }
    //
    
    
    //These three functions update all Microsoft labels and values
    @FXML private void handleBuyMicrosoft(ActionEvent event) {
        if((microsoft.getValue() * Integer.parseInt(txtMicrosoftBuyRequest.getText())) <= activeUser.getBalance()) {
            activeUser.addStock("microsoft", Integer.parseInt(txtMicrosoftBuyRequest.getText()), microsoft.getValue());
            updateMicrosoftLabels();
        }
        
        else {
            showBalanceLow();
        }
        
        System.out.println("bought " + txtMicrosoftBuyRequest.getText() + " shares of Microsft");
            
    }
    
    @FXML private void handleSellMicrosoft(ActionEvent event) {
        if((microsoft.getValue() - Integer.parseInt(txtMicrosoftSellRequest.getText()))  > 0) {
            activeUser.sellStock("microsoft", Integer.parseInt(txtMicrosoftSellRequest.getText()), microsoft.getValue());
            updateMicrosoftLabels();
            
        }
        
        else {  }
        
        System.out.println("sold " + txtMicrosoftSellRequest.getText() + " shares of Microsft");
        
    }
    
    private void updateMicrosoftLabels() {
        lblMicrosoftBought.setText(activeUser.getStock("microsoft") + "");
        lblMicrosoftBought1.setText(activeUser.getStock("microsoft") + "");
        lblBalance.setText(activeUser.getBalance() + "");
        totalStockValue();
        
    }
    //
    
    
    //Updates the total value label to the total value of ALL the users stock shares
    private void totalStockValue() {
            double tempTotal = (activeUser.getStock("apple") * apple.getValue());
            tempTotal += (activeUser.getStock("google") * google.getValue());
            tempTotal += (activeUser.getStock("microsoft") * microsoft.getValue()); 
            
            if(tempTotal == 0) {
                lblStockValue.setText("_____");
                
            }
            else {
                lblStockValue.setText(tempTotal + "");
                
            }
            
            
            lblStocksOwnedList.setText("Owned:\n     Apple: " + activeUser.getStock("apple")+ "\n     Google: " + activeUser.getStock("google")+ "\n     Microsoft: " + activeUser.getStock("microsoft"));
            
            

        }
    
    
    //These two functions make a Anchorpane (My way of making an alert) appear and dissapear
    private void showBalanceLow() {
        infoPage.setVisible(false);
        balanceLow.setVisible(true);
        
    }
    @FXML private void hideBalanceLow(ActionEvent event) {
        infoPage.setVisible(true);
        balanceLow.setVisible(false);
        
    }
    //

    
//    ****************************************************************************    //
    //
    
    
    
    
    //Runs a day cycle, and refreshes all stock values
    @FXML private void handleRunDayCycle(ActionEvent event) {
        day += 1;
        apple.nextDay();
        google.nextDay();
        microsoft.nextDay();  
        
        totalStockValue();
        graphStocks();
        
        lblApple1.setText(apple.getValue() + "");
        lblApple2.setText(apple.getValue() + "");
        lblGoogle1.setText(google.getValue() + "");
        lblGoogle2.setText(google.getValue() + "");
        lblMicrosoft1.setText(microsoft.getValue() + "");
        lblMicrosoft2.setText(microsoft.getValue() + "");
        
        System.out.println("Day Advanced");
        
    } 
    //
    
    
    
    
    //Creates and/or updates the graph with values (day, value of said share)
    private void graphStocks() {
        appleGraph.getData().add(new XYChart.Data<>((day + ""), apple.getValue()));
        googleGraph.getData().add(new XYChart.Data<>((day + ""), google.getValue()));
        microsoftGraph.getData().add(new XYChart.Data<>((day + ""), microsoft.getValue()));

        
        chartStock.getData().removeAll(appleGraph, googleGraph, microsoftGraph);
        chartStock.getData().addAll(appleGraph, googleGraph, microsoftGraph);
        
    }
    //
    
    
    
    
    //
    @FXML private void cheatButton(ActionEvent event) {
        
        
        if(peopleList.size() >= 1) {
            System.out.println("\n\nActive user selected: " + activeUser.getName());
            lblName.setText(activeUser.getName());
            setSelectListView(0);
            activeUser = peopleList.get(0);
            
            if(activeUser.getBalance() > (apple.getValue() * 4)) {
                txtAppleBuyRequest.setText("4");
                handleBuyApple(null);
                
            }
            
            if(activeUser.getBalance() > (microsoft.getValue() * 2)) {
                txtMicrosoftBuyRequest.setText("2");
                handleBuyMicrosoft(null);
                
            }
            
        }
        
        
        if(peopleList.size() >= 2) {
            System.out.println("\n\nActive user selected: " + activeUser.getName());
            lblName.setText(activeUser.getName());
            setSelectListView(1);
            activeUser = peopleList.get(1);
            
            if(activeUser.getBalance() > (google.getValue() * 3)) {
                txtGoogleBuyRequest.setText("3");
                handleBuyGoogle(null);
                
            }
            
            if(activeUser.getBalance() > (apple.getValue() * 5)) {
                txtAppleBuyRequest.setText("5");
                handleBuyApple(null);
                
            }
            
        }
        
        handleRunDayCycle(null);
        handleRunDayCycle(null);
        
    }
    //
    
    
    
    
    //Sets AchorPanes to appropriate visibilities
    @Override public void initialize(URL url, ResourceBundle rb) {
        registerPage.setVisible(true);
        namePage.setVisible(false);
        infoPage.setVisible(false);
        balanceLow.setVisible(false);
//        invalidNumber.setVisible(false);
        
        appleGraph.setName("Apple");
        googleGraph.setName("Google");
        microsoftGraph.setName("Microsoft");
        
        graphStocks();
        
        
    }    
    //
    
}
