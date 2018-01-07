



//This function will make the button put a number into the input box
function inputNumber(num) {


    var x = document.getElementById('input').value;     //Gets the input value
    document.getElementById('input').value = x + num;   //Adds button number to existing input number

}

//This function activates on the equals button
function solveProblem() {
    var num1 = document.getElementById("input").value.substr(0,1);      //finds first number in the first character slot of input box
    var numSign = document.getElementById("input").value.substr(1,1);   //finds the operator in the middle of the two numbers
    var num2 = document.getElementById("input").value.substr(2,1);      //finds second number in the first character slot of input box
    var num3 = document.getElementById("input").value.substr(3,1);      //finds any character after the 3 characters before it

    //if a number is put in the middle slot, the following will occur
    if (numSign > 0){

        var answer = "Please follow the instructions and use an equation like,   (Single digit number)(Operator)(single digit number)"; //state message with instructions

    }

    //if anything is in 4 or farther spot, the following will occur
    else if (num3 > 0 || num3 === "÷" || num3 === "x" || num3 === "+" || num3 === "-"){

        var answer = "You should really reread the instructions...";    //state message with instructions

    }

    //if a operator is put in the first slot, the following will occur
    else if (num1 === "÷" || num1 === "x" || num1 === "+" || num1 === "-"){

        var answer = "Please follow the instructions and use an equation like,   (Single digit number)(Operator)(single digit number)"; //state message with instructions

    }

    //if a operator is put in the second number slot, the following will occur
    else if (num2 === "÷" || num2 === "x" || num2 === "+" || num2 === "-"){

        var answer = "Please follow the instructions and use an equation like,   (Single digit number)(Operator)(single digit number)"; //state message with instructions

    }

    //if the operator is division, the following will occur
    else if (numSign === "÷"){

        var answer = +num1 / +num2; //divide the numbers

    }

    //if the operator is multiplication, the following will occur
    else if (numSign === "x"){

        var answer = +num1 * +num2; //multiply the numbers

    }

    //if the operator is addition, the following will occur
    else if (numSign === "+"){

        var answer = +num1 + +num2; //add the numbers

    }

    //if the operator is subtraction, the following will occur
    else if (numSign === "-"){

        var answer = +num1 - +num2; //subtract the numbers

    }

    alert(answer); //Send the answer through an alert

}

//The clear button will clear the input box
function clearNumbers() {
    document.getElementById('input').value = "";    //replaced all of the input box with nothing
}
