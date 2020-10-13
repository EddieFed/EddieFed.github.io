/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebook.project;

import java.net.URL;
//import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
//import javafx.scene.control.SplitMenuButton;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLDocumentController implements Initializable {
    
    
    
    //var[0] is total points, var[1] is points given
    public int[] homeworkScores = {0, 0};
    public int[] quizScores = {0, 0};
    public int[] testScores = {0, 0};
    
    
    
    //These variables represent the assignment weights
    public double hww = 0.2;
    public double qw = 0.3;
    public double tw = 0.5;
    
      
    
    @FXML
    private Label lblHomework, lblQuiz, lblTest, lblTotalGrade, lblWeight;
    
    
    
    @FXML   //Adds a grade under the homework catagory after total points and points given are determined
    private void handleGradeHomework(ActionEvent event) {
        int tempAnswerTotal = Integer.parseInt(JOptionPane.showInputDialog("How many points was this assignment out of?"));
        int tempAnswerPoints = Integer.parseInt(JOptionPane.showInputDialog("How many points were given?"));
        
        addGrade("Homework", tempAnswerTotal, tempAnswerPoints);
        
    }
    
    
    
    @FXML   //Adds a grade under the Quiz catagory after total points and points given are determined
    private void handleGradeQuiz(ActionEvent event) {
        int tempAnswerTotal = Integer.parseInt(JOptionPane.showInputDialog("How many points was this quiz out of?"));
        int tempAnswerPoints = Integer.parseInt(JOptionPane.showInputDialog("How many points were given?"));
        
        addGrade("Quiz", tempAnswerTotal, tempAnswerPoints);
        
    }
    
    
    
    @FXML   //Adds a grade under the Test catagory after total points and points given are determined
    private void handleGradeTest(ActionEvent event) {
        int tempAnswerTotal = Integer.parseInt(JOptionPane.showInputDialog("How many points was this test out of?"));
        int tempAnswerPoints = Integer.parseInt(JOptionPane.showInputDialog("How many points were given?"));
        
        addGrade("Test", tempAnswerTotal, tempAnswerPoints);
        
    }
    
    
    
            //Called by the three functions and finds percents and changes labels acordingly
    private void addGrade(String type, int total, int points) {
        
        
        if (type.equals("Homework")) {
            homeworkScores[0] += total;
            homeworkScores[1] += points;
            double tempHW = ( (homeworkScores[1] + 0.0) / (homeworkScores[0] + 0.0) );
//            System.out.println(tempHW);

            lblHomework.setText("Homework: " + homeworkScores[1] + "/" + homeworkScores[0] + " or " + ((int)Math.round(tempHW * 100)) + "%");
            
        }
        
        if (type.equals("Quiz")) {
            quizScores[0] += total;
            quizScores[1] += points;
            double tempQuiz = ( (quizScores[1] + 0.0) / (quizScores[0] + 0.0) );
//            System.out.println(tempHW);

            lblQuiz.setText("Quiz: " + quizScores[1] + "/" + quizScores[0] + " or " + ((int)Math.round(tempQuiz * 100)) + "%");
            
        }
        
        if (type.equals("Test")) {
            testScores[0] += total;
            testScores[1] += points;
            double tempTest = ( (testScores[1] + 0.0) / (testScores[0] + 0.0) );
//            System.out.println(tempHW);

            lblTest.setText("Test: " + testScores[1] + "/" + testScores[0] + " or " + ((int)Math.round(tempTest * 100)) + "%");
            
        }
        
        updateTotalLabel();
        
        
    }
    
    
    
            //Self explanitory
    private void updateTotalLabel() {
        double gradePercent = totalPercentgrade();
        lblTotalGrade.setText("Total: " + ((int)Math.round(gradePercent * 100)) + "% or " + letterGrade(gradePercent));
        
    }
    
    
    
            //Will return the overall grade, weighted according to the weight variables
    private double totalPercentgrade() {
        
        @SuppressWarnings("UnusedAssignment")
        double hwTotal = 0;
        
        @SuppressWarnings("UnusedAssignment")
        double qzTotal = 0;
        
        @SuppressWarnings("UnusedAssignment")
        double tsTotal = 0;
        
        if (homeworkScores[0] > 0) {
            hwTotal = ((double)homeworkScores[1]) / homeworkScores[0];
            
        }
        
        else {
            hwTotal = 1.0;
        }
        
        if (quizScores[0] > 0) {
            qzTotal = ((double)quizScores[1]) / quizScores[0];
        
        }
        
        else {
            qzTotal = 1.0;
        }
        
        if (testScores[0] > 0) {
            tsTotal = ((double)testScores[1]) / testScores[0];
        
        }
        
        else {
            tsTotal = 1.0;
        }
        
        return ((qw * qzTotal) + (hww * hwTotal) + (tw *tsTotal));

    }
    
    
    
            //Will return the Letter grade of a given decimal percent
    private String letterGrade(double grade) {
        
        if (grade >= 0.85) {
            return "A";
            
        }
        
        else if (grade >= 0.75) {
            return "B";
            
        }
        
        else if (grade >= 0.65) {
            return "C";
            
        }
        
        else if (grade >= 0.55) {
            return "D";
            
        }
        
        else {
            return "F";
        }
        
    }
    
    
    
    @FXML   //Allows the user to set the weight of grades, it will not accept the inputs if they do not equal 100
    private void handleSetWeight(ActionEvent event) {
        
        boolean equals100 = false;
        while(equals100 == false) {
            hww = (Double.parseDouble(JOptionPane.showInputDialog("What is the weight percent of Homework?")) / 100.0);
            qw = (Double.parseDouble(JOptionPane.showInputDialog("What is the weight percent of Homework?")) / 100.0);
            tw = (Double.parseDouble(JOptionPane.showInputDialog("What is the weight percent of Homework?")) / 100.0);
            
            if (hww + qw + tw != 1) {
                System.out.println("That does not equal 100");
                
            }
            
            else {
                equals100 = true;
                lblWeight.setText("Homework: " + ((int)(hww * 100)) + "%, Quizzes: " + ((int)(qw * 100)) + "%, Tests: " + ((int)(tw * 100)) + "%");
                updateTotalLabel();
                
            }
           
        }
         
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    }    
    
}
