/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package you.dont.know.jack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import you.dont.know.jack.elements.*;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private AnchorPane splashScreen, mainPane, playerSelectScreen, scoreboardScreen, mainScreen, questionPane, namePane, playerInfoScreen, questionTypeScreen, thisOrThatPane, anagramPane, multipleChoicePane, fillInTheBlankPane, timePane, coverQuestionsPane, wrongAnswerPane, finishPane;
    @FXML private Label lbl_PlayerName, lbl_Correct, lbl_Winnings, lbl_Question, lbl_activePlayer, lbl_time, lbl_FinishMessage;
    @FXML private ListView scoreView;
    @FXML private TextField txtName, txtAnagram, txtFill;
    @FXML private ToggleGroup playerCount, questionTypeChoice, thisOrThatButtons, multipleChoiceButtons;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private Player activeplayer, playerQuestionClicker;
    private Question activeQuestion;
    private int questionsAskedCount = 0;
    private boolean questionsShown = false;
    private boolean questionClicerSelected = false;
    
    
//  To do
//  
//  = Add timer for questions  
//  = Make a keybaord shorcut as a buzzer - then make a player connected to buzzer
//  = Give points to player when a player gets it right
//  = Add game ending + final round after 7 questions
//  = Add CSS Style 
//  + Add music & sound
//  + (MAYBE) Add possible global leaderboards (hosted via eddiefed.com)
//    
    
    
    
    // Called for every player that is playing and asks for a name
    @FXML private void handleNewName(ActionEvent event) {
        playerSelectScreen.setVisible(false);
        namePane.setVisible(true);
        System.out.println(((RadioButton) playerCount.getSelectedToggle()).getText());
        
    }
    
    // Used to setup the players list and update ListView
    @FXML private void nameEntered(ActionEvent event) {
        
        
        if (players.size() < Integer.parseInt(((RadioButton) playerCount.getSelectedToggle()).getText())) {
            
            players.add(new Player(txtName.getText()));
            txtName.clear();
            
            System.out.println("Player " + players.size() + ": " + players.get(players.size() - 1).getName());
        }
        
        if (players.size() == Integer.parseInt(((RadioButton) playerCount.getSelectedToggle()).getText())){
            namePane.setVisible(false);
            
            if(players.size() == 1) {
                mainScreen.setLayoutX(150);
                 
            }
            else {
                scoreboardScreen.setVisible(true);
            }
            
            questionTypeScreen.setVisible(true);
            mainScreen.setVisible(true);

            
            
            
            List<String> names = new ArrayList<>();
            for(Player p : players) {
                names.add(players.get(players.indexOf(p)).getName());
            }
            scoreView.setItems(FXCollections.observableArrayList(names));
            
        }
        
        activeplayer = players.get(0);
        lbl_activePlayer.setText("Category Chooser: " + activeplayer.getName());
        
        scoreView.getSelectionModel().select(0);
        
    }
    
    private void nextActivePlayer() {
        if(players.indexOf(activeplayer) < players.size() - 1) {
            activeplayer = players.get(players.indexOf(activeplayer) + 1);
        }
        else if(players.indexOf(activeplayer) == players.size()) {
            activeplayer = players.get(0);
        }
        
//        lbl_activePlayer.setText("Category Chooser: " + activeplayer.getName());
    }
    
    // Updates the player info log on the right AnchorPane
    @FXML private void updateSelectedPlayerInfo(MouseEvent event) {
        for (Player p : players) {
            if(p.getName().equals(scoreView.getSelectionModel().getSelectedItem().toString())){
//                System.out.println(p.getName());
                
                playerInfoScreen.setVisible(true);
                lbl_PlayerName.setText("Name: " + p.getName());
                lbl_Correct.setText("Correct: " + p.get_qCorrect());
                lbl_Winnings.setText("Winnnings: " + p.getWinnings());
                        
            }
        }
    }
    
    // Called when a question type is clicked
    @FXML private void handleChooseType(ActionEvent event) {
        questionTypeScreen.setVisible(false);
        
        for(Question q : questions) {
            if((q.getType().toLowerCase()).equals((((RadioButton) questionTypeChoice.getSelectedToggle()).getText()).toLowerCase())) {
//                System.out.println(q.getQuestion());
                
                activeQuestion = q;
            }
        }
        
        handleShowQuestion();
        
        pastTime = LocalTime.now();
        timer.start();
        
        
    }
    
    // Updates the question text
    private void handleShowQuestion() {
        if(players.size() == 1) {
            playerQuestionClicker = players.get(0);
        }
        
        
        // Shows Question in question box       
        questionPane.setVisible(true);
        lbl_Question.setText(activeQuestion.getQuestion());
        
        handleShowQuestionChoices();
    }
    
    // Shows the answer selection info in the appropriate AnchorPane
    private void handleShowQuestionChoices() {
        questionsAskedCount++;
        questionsShown = true;
        coverQuestionsPane.setVisible(true);
        lbl_activePlayer.setText("Click the buzzer!");
        
        switch(activeQuestion.getType()) {
            case ("This or that"): {
                System.out.println("This or that");
                thisOrThatPane.setVisible(true);
                
                ArrayList<RadioButton> buttons = new ArrayList<>();
                for(Toggle r : thisOrThatButtons.getToggles()){
                    ((RadioButton) r).setText(activeQuestion.getChoices().get(thisOrThatButtons.getToggles().indexOf(r)));
                    
                }
                
                return;
                
            }
                
            case ("Anagram"): {
                System.out.println("Anagram");
                anagramPane.setVisible(true);
                
                return;
                
            }
                
            case ("Multiple choice"): {
                System.out.println("Multiple choice");
                multipleChoicePane.setVisible(true);
                
                ArrayList<RadioButton> buttons = new ArrayList<>();
                for(Toggle r : multipleChoiceButtons.getToggles()){
                    ((RadioButton) r).setText(activeQuestion.getChoices().get(multipleChoiceButtons.getToggles().indexOf(r)));
                    
                }
                return;
            }
                
            case ("Fill in the blank"): {
                System.out.println("Fill in the blank");
                fillInTheBlankPane.setVisible(true);
                
                return;
            }
                
            default: {
                System.out.println("Question type not Valid: " + activeQuestion.getType());
                
            }
        }              
    }
    
    // Grabs player number from button pressed
    @FXML private void getPlayerQuestionClicker(KeyEvent event) {
//        System.out.println(event.getCode().toString());
        if (questionsShown == true) {
            if((event.getCode() == KeyCode.DIGIT1) && (players.size() >= 1) && (questionClicerSelected == false)) {
                playerQuestionClicker = players.get(0);
            }
            else if((event.getCode() == KeyCode.DIGIT2) && (players.size() >= 2) && (questionClicerSelected == false)) {
                playerQuestionClicker = players.get(1);
            }
            else if((event.getCode() == KeyCode.DIGIT3) && (players.size() == 3) && (questionClicerSelected == false)) {
                playerQuestionClicker = players.get(2);
            }
            else {
                return;
            }
            coverQuestionsPane.setVisible(false);
            questionClicerSelected = true;
            lbl_activePlayer.setText("Clicker person: " + playerQuestionClicker.getName());
            System.out.println("Clicker person: " + playerQuestionClicker.getName());
        }
        
    }
    
    

    //
    //// --- These are called when answers to a question are submitted --- ////
    @FXML private void handleAnswerSelectThisOrThat(ActionEvent event){
        System.out.println(((RadioButton) thisOrThatButtons.getSelectedToggle()).getText());
        
        if(((((RadioButton) thisOrThatButtons.getSelectedToggle()).getText()).toLowerCase()).equals((activeQuestion.getAnswer()).toLowerCase())) {
            System.out.println("Correct! :)");
            playerQuestionClicker.correct();
            playerQuestionClicker.winnings("+", activeQuestion.getWinnings());
            
        }
        
        else {
            System.out.println("Incorrect! :(");
            playerQuestionClicker.winnings("-", activeQuestion.getWinnings());
            wrongAnswer();
            return;
            
        }
        resetOptions();
        
        
    }
    
    @FXML private void handleAnswerSelectAnagram(ActionEvent event) {
        System.out.println(txtAnagram.getText());
        
        if(((txtAnagram.getText()).toLowerCase()).equals((activeQuestion.getAnswer()).toLowerCase())) {
            System.out.println("Correct! :)");
            playerQuestionClicker.correct();
            playerQuestionClicker.winnings("+", activeQuestion.getWinnings());
        
        }
        else{
            System.out.println("Incorrect :(");
            playerQuestionClicker.winnings("-", activeQuestion.getWinnings());
            wrongAnswer();
            return;
            
        }
        resetOptions();
        
    }
    
    @FXML private void handleAnswerSelectMultipleChoice(ActionEvent event) {
        if(((((RadioButton) multipleChoiceButtons.getSelectedToggle()).getText()).toLowerCase()).equals((activeQuestion.getAnswer()).toLowerCase())) {
            System.out.println("Correct! :)");
            playerQuestionClicker.correct();
            playerQuestionClicker.winnings("+", activeQuestion.getWinnings());
            
        }
        
        else {
            System.out.println("Incorrect! :)");
            playerQuestionClicker.winnings("-", activeQuestion.getWinnings());
            wrongAnswer();
            return;
            
        }
        resetOptions();
        
    }
    
    @FXML private void handleAnswerSelectFillInTheBlank(ActionEvent event) {
        System.out.println(txtFill.getText());

        if(((txtFill.getText()).toLowerCase()).equals((activeQuestion.getAnswer()).toLowerCase())) {
            System.out.println("Correct! :)");
            playerQuestionClicker.correct();
            playerQuestionClicker.winnings("+", activeQuestion.getWinnings());
            
        }
        else{
            System.out.println("Incorrect :(");
            playerQuestionClicker.winnings("-", activeQuestion.getWinnings());
            wrongAnswer();
            return;

        }
        resetOptions();
    }
    
        // Resets everything for a new question
        private void resetOptions() {
        questionsShown = false;
        questionPane.setVisible(false);
        thisOrThatPane.setVisible(false);
        anagramPane.setVisible(false);
        multipleChoicePane.setVisible(false);
        fillInTheBlankPane.setVisible(false); 
        questionTypeScreen.setVisible(true);
        
        
        questionTypeChoice.selectToggle(null);
        thisOrThatButtons.selectToggle(null);
        txtAnagram.clear();
        multipleChoiceButtons.selectToggle(null);
        txtFill.clear();
        
        
        lbl_time.setText("");
        timer.stop();
        
        
        questionClicerSelected = false;
        coverQuestionsPane.setVisible(false);
        playerQuestionClicker = null;
        
        
        updateQuestionList();
        updateSelectedPlayerInfo(null);
        
        nextActivePlayer();
        lbl_activePlayer.setText("Category Chooser: " + activeplayer.getName());
        
        gameDone();
        
    }
        
        // Removes the question answered from the pool of questions
        private void updateQuestionList() {
        questions.remove(questions.indexOf(activeQuestion));
        
        for(Question q :questions) {
            if(q.getType().equals(activeQuestion.getType())){
                System.out.println("Another question exists of the same type!");
                
                return;
            }
        }
        
        for(int k =0; k < 4; k++) {
            if((((RadioButton) ((questionTypeChoice.getToggles()).get(k))).getText()).equals(activeQuestion.getType())) {
                ((RadioButton) ((questionTypeChoice.getToggles()).get(k))).setVisible(false);
            }
        }
    }
        
        // Called when the timer hits 0
        private void outOfTime() {
            questionTypeScreen.setVisible(false);
            timePane.setVisible(true);
        }
        
        // Called when the answer is incorrect
        private void wrongAnswer() {
            questionPane.setVisible(false);
            questionTypeScreen.setVisible(false);
            wrongAnswerPane.setVisible(true);
        }
        
        // Checks if game is over
        private void gameDone() {
            if(questionsAskedCount == 7) {
                
                Player highestScorer = null;
                for (Player p : players) {
                    if (players.indexOf(p) == 0 ) {
                        highestScorer = p;
                    }
                    else {
                        if (p.getWinnings() > highestScorer.getWinnings()) {
                            highestScorer = p;
                        }
                    }
                }
                
                if(players.size() == 1) {
                    lbl_FinishMessage.setText("You finished the game " + highestScorer.getName() + " with $" + highestScorer.getWinnings());
                }
                else {
                    lbl_FinishMessage.setText(highestScorer.getName() + " won the game with $" + highestScorer.getWinnings());
                }
                
                finishPane.setVisible(true);
                mainPane.setVisible(false);
            }
        }
        
        
    //// --- --------------------------------------------------------- --- ////
    //
    
    
        
    // When the "okay" button is pressed
    @FXML private void hideTimePane(ActionEvent event) {
        questionTypeScreen.setVisible(true);
        timePane.setVisible(false);
    }  
    
    // When the other "okay" button is pressed
    @FXML private void hideWrongAnswerPane(ActionEvent event) {
        questionTypeScreen.setVisible(true);
        wrongAnswerPane.setVisible(false);
        resetOptions();
    }
    
    // Closes game
    @FXML private void handleGoodbye(ActionEvent event) {
        Platform.exit();
    }
        
    //Timer resources
    private AnimationTimer timer, splashTime;
    private LocalTime pastTime;
    public FXMLDocumentController() {
        pastTime = LocalTime.now();
        timer = new AnimationTimer() {
            
            @Override public void handle(long now) {
                
                lbl_time.setText((10 - (LocalTime.now().toSecondOfDay() - pastTime.toSecondOfDay())) + "");
                
                if(LocalTime.now().toSecondOfDay() - pastTime.toSecondOfDay() > 10) {
                   lbl_time.setText("0");
                   
                    
                    
                    
                    timer.stop();
                    lbl_time.setText("");
                    
                    // Call "out of time function"
                    resetOptions();
                    outOfTime();





                                 
                }
            }
        };
        
        splashTime = new AnimationTimer() {
            @Override public void handle(long now) {
                
                if(LocalTime.now().toSecondOfDay() - pastTime.toSecondOfDay() == 5) {
                    splashScreen.setVisible(false);
                    mainPane.setVisible(true);
                    splashTime.stop();
                }
                
                
            }
        };
        splashTime.start();
    }
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        questionTypeScreen.setVisible(false);
        playerSelectScreen.setVisible(true);
        mainScreen.setVisible(false);
        scoreboardScreen.setVisible(false);
        namePane.setVisible(false);
        playerInfoScreen.setVisible(false);
        questionPane.setVisible(false);
        timePane.setVisible(false);
        wrongAnswerPane.setVisible(false);
        
        mainPane.setVisible(false);
        splashScreen.setVisible(true);
        
        

        try{
            FileReader reader = new FileReader("src\\you\\dont\\know\\jack\\rescources\\load\\QuestionLoader.txt");
            Scanner in = new Scanner(reader);
            while(in.hasNextLine()) {
                String temp_Type = in.nextLine();
                String temp_Question = in.nextLine();
                
                String super_temp_ListChoices = in.nextLine();
                ArrayList<String> temp_ListChoices = new ArrayList<>();
                
                if(!(super_temp_ListChoices.contains(","))) {
                    temp_ListChoices.add("");
                }
                
                while(super_temp_ListChoices.contains(",")) {
                    temp_ListChoices.add(super_temp_ListChoices.substring(0, super_temp_ListChoices.indexOf(",")));

                    super_temp_ListChoices = super_temp_ListChoices.substring(super_temp_ListChoices.indexOf(",") + 1);
                }
                
                String temp_Answer = in.nextLine();
                
//                System.out.println("Type: " + temp_Type);
//                System.out.println("Question: " + temp_Question);
//                System.out.println("Choices: " + temp_ListChoices);
//                System.out.println("Answer: " + temp_Answer + "\n");
                
                questions.add(new Question(temp_Type, temp_Question, temp_ListChoices, temp_Answer));
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading file!\nFile not found or missing");
        }
    }
}
