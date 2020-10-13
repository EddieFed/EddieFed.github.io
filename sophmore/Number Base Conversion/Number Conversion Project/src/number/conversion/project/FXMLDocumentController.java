/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package number.conversion.project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label result;
    @FXML private TextField txtNum, txtStartingBase, txtEndingBase;
    private boolean isPossible;
    
            
    @FXML private void handleBase(ActionEvent event) {
        
//        int num = Integer.parseInt(txtNum.getText());
        String numString = (txtNum.getText());
        int startBase = Integer.parseInt(txtStartingBase.getText());
        int endBase = Integer.parseInt(txtEndingBase.getText());
        isPossible = true;
        
        
        /*
        Pattern --> Multiply the number in the position by its index (substring).
        
        113 - Base 10
        1*(10^2)
        1*(10^1)
        3*(10^0)
        
        
        1010 (10) - Binary
        1*(2^3)
        0*(2^2)
        1*(2^1)
        0*(2^0)
        
        
        110 (12) - Base 3
        1*(3^2)
        1*(3^1)
        0*(3^0)
        
        
        /\/\/\/\/\/\
        
        
        11 (Base 10 --> Binary)
        11%8 --> R3, 1
        3%4  --> R3, 0
        3%2  --> R1, 1
        1%1  --> R0, 1
        
        
        
        
        
        
        
        
        
        */
        
        
        
        
        
        
        
        
        
        String base10 = toBase10(numString, startBase) + "";
        if(isPossible){
            System.out.println("Base 10: " + toBase10(numString, startBase));
            result.setText(from10ToBaseAnything(base10, endBase));
            
        }
        else {
            System.out.println("Not Possible");
            result.setText("Not possible");
            
        }
        
    }
    
    
    
    private int toBase10(String numString, int startBase) {
        int num = 0;
        
        for(int i = 0; i < numString.length(); i++){
            num += (Character.getNumericValue(numString.charAt(numString.length() - (i + 1)))) * (Math.pow(startBase, i));
            
            if(Character.getNumericValue(numString.charAt(numString.length() - (i + 1))) >= startBase) {
                isPossible = false;
                break;
            }
            
        }
        
//        System.out.println("Base 10: " + num);
        return num;
    }
    
    
    
    private String from10ToBaseAnything(String numString, int endBase) {
        String endResult = "";
        int num = Integer.parseInt(numString);
        int baseDivisor = 0;
        
        for(int j = 1; baseDivisor < num; j++) {
            baseDivisor = (int) (Math.pow(endBase, j));
            
        }
        
        // Corrects value
        baseDivisor /= endBase;
        
        System.out.println("Highest multiple of base " + baseDivisor + "\n\n");
        
        while(baseDivisor > 0) {
            String digitVal = (num / baseDivisor) + "";
            if(Integer.parseInt(digitVal) >= 10) {

                digitVal = ((char)(Integer.parseInt(digitVal) + 55)) + "";
                
            }
            
            
            endResult += (digitVal + "");
            System.out.println("EndResult --> " + endResult);
            
            num %= baseDivisor;
            System.out.println("Num --> " + num);
                        
            baseDivisor /= endBase;
            System.out.println("BaseDivisor --> " + baseDivisor + "\n");
        
        }
        
        
        
        System.out.println("Result --> " + endResult);
        
        
        
        
        return ("Result --> " + endResult);
    }
    
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
//        txtNum.setText("17");
//        txtStartingBase.setText("10");
//        txtEndingBase.setText("3");
        
        
    }    
    
}
