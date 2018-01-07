



var playerNames = [];
var Score = 0;
var pushToName = "";

//Action to start the game by asking for player name. Also displays word.

function startGame(playerCount) {
    var q = 0;
    if (playerCount === 1) {
        for (q=0;q === 2; q++){
            pushToName = "";
            askName();
        }
    }

}




function askName() {
        pushToName = prompt("What Is Your Name?");
        playerNames.push();
        alert("Welcome " + playerNames);
        document.getElementById('playerNameDisplay').innerHTML = "Name: " + playerNames;
        alert('Now Spin the Wheel and Guess a letter!');
        pushLetter();


}




var spinWheel = true;
var chosenValues = 0;




//Gets The amount of money given through the wheel
function getValues() {
    spinWheel = true;

    //Triggers a slide of images of wheels
    displayPics();

    var values = [100, 200, 300, 500, 700, 400, 150, 450, 250, 600, 350, "bankrupt"];


    var randomNumber = Math.trunc(Math.random() * values.length);  //Generates a number 1 - (the Length of the list)
    chosenValues = values[randomNumber];

    //If bankrupt is spun, player is alerted and looses all their earnings. (Must spin again)
    if (chosenValues === "bankrupt") {
        Score = 0;
        alert("You Spun a Bankrupt... Sorry...");
        alert("Spin again");
        spinWheel = false;
    }

    document.getElementById('amountOfMoney').innerHTML = "$" + chosenValues;

}




var picSource = ["wheel01.jpg", "wheel02.jpg", "wheel03.jpg", "wheel04.jpg", "wheel05.jpg", "wheel06.jpg", "wheel07.jpg", "wheel08.jpg", "wheel09.jpg", "wheel10.jpg", "wheel11.jpg", "wheel12.jpg", "wheel13.jpg", "wheel14.jpg", "wheel15.jpg", "wheel16.jpg", "wheel17.jpg", "wheel18.jpg", "wheel19.jpg", "wheel20.jpg", "wheel21.jpg", "wheel22.jpg", "wheel23.jpg", "wheel24.jpg"];
var picNum = 1;

//Used to spin the picture of the wheel.
function displayPics() {
    var interval = setInterval(function() {myTimer()}, 50);
    var spinNum = 0;

    //Branch of displayPics
    function myTimer() {

        document.getElementById('wheelPic').src = "Wheel%20Images/" + picSource[picNum];
        picNum++;
        if(picNum ===picSource.length){
            picNum = 0;
        }

        spinNum++;


        if(spinNum === 24){
            clearInterval(interval);

        }

    }

}




var wordArray = ["psyched", "hello", "science", "computers", "fortune", "wheel"];
var guessLetter = [];
var displayWord = "";

var x = 0;




//Checks if there is a duplicate entry to the input box
function isDouble() {
    var inputValue = document.getElementById('letterInput').value.toLowerCase();
    var placeFound = guessLetter.indexOf(inputValue);

    if (placeFound < 0) {
        pushLetter();

    }

    else if (placeFound >= 0){
        alert("You have already guessed that");
    }
}




//Branch Of isDouble ---> Only if it passes...
function pushLetter() {
    var inputValue = document.getElementById('letterInput').value.toLowerCase();


    //Checks for a vowel in the input box, if there is one, it charges you $250
    if (inputValue === "a" || inputValue === "e" || inputValue === "i" || inputValue === "o" || inputValue === "u") {

        if (Score > 250) {
            Score = Score - 250;
            pushLetter2();
        }

            else {
                alert("You Need More Money to buy a vowel");
            }

    }

    else {
        pushLetter2();
    }

}




//The second branch of isDouble
function pushLetter2() {

var inputValue = document.getElementById('letterInput').value.toLowerCase();

//Used only for the moment when the start button is pressed, this allows the word to be formed but no letter guessed
if(x === 0) {

    var randomWord = Math.trunc(Math.random() * wordArray.length);  //Generates a number 1 - (the Length of the list)
    word = wordArray[randomWord];
    //alert(word);
    x++;
}


    //Defaults th value of "amountOfMoney" to remind you to re-Spin
    document.getElementById('amountOfMoney').innerHTML = "(Spin the Wheel)";

    //If the wheel is spun, Continue...
    if (spinWheel === true) {


        //Pushes the new value from the input box into the Array
        guessLetter.push(inputValue);

        displayWord = "";

        //Checks to see if a guessed letter is in the word
        for (i = 0; i < word.length; i++) {
            var correctLetter = false;

            for (k = 0; k < guessLetter.length; k++) {

                if (word.substr(i, 1) === guessLetter[k]) {
                    displayWord = displayWord + word.substr(i, 1);
                    correctLetter = true;
                    Score = Score + chosenValues;
                    chosenValues = 0;
                    spinWheel = false;

                }
            }

            if (correctLetter === false) {
                displayWord = displayWord + " _ ";
                spinWheel = false;

            }
        }

        document.getElementById('scoreCount').innerHTML = "Winnings: $" + Score;
        document.getElementById('showWord').innerHTML = displayWord;
        //alert(guessLetter);
        document.getElementById('letterInput').value = "";
        setTimeout(checkWord, 300);

    }


    else if (spinWheel === false) {
    alert("Spin The Wheel First!");

    }
}




//This function Checks to see if the work is correctly completed
function checkWord() {

    if (word === displayWord) {
        alert("Congrats! You Won!");
        alert("Winnings: $" + Score);
    }


}




//Function Resets every paragraph and variable used to its starting value
function refreshGame() {
    playerNames = "";
    spinWheel = true;
    document.getElementById('playerNameDisplay').innerHTML = "Name: ";
    chosenValues = 0;
    document.getElementById('amountOfMoney').innerHTML = "";
    guessLetter = [" "];
    document.getElementById('scoreCount').innerHTML = "Winnings: $0";
    Score = 0;
    x = 0;
    document.getElementById('showWord').innerHTML = "";
    alert("Refreshed, Please Click start again")
}



































































