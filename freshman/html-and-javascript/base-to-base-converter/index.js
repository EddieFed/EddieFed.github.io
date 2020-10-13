function makeBase10() {
    var binaryInput = document.getElementById("Binary").value;      //Gets input value.
    var lengthInput = binaryInput.length;     //Gets Length of input number.
    var showNum = 0;

    for(i=0 ;i < lengthInput ;i++) {      //Increase "i" by one very repeat, until it is greater or equal to one less then the length.

        var baseTwoValue = binaryInput.substr(lengthInput -i -1, 1);                    //get the individual value of the character that is in use
        var baseTenNumberValue =  Math.pow(2, i)* (baseTwoValue) ;      //multiply the value of the substring by 2^i (i increases every loop)
        showNum = showNum + baseTenNumberValue;                         //add the new value to the display

    }

    alert(showNum);     //alert the answer in Base ten
}





function makeBase2() {
    var decimalInput = document.getElementById("Base10").value;      //Gets input value.
    var powerOfTwo = 1;                 //starts the powerOfTwo at one
    var decimalTemp = decimalInput;     //Creates a temp of the input
    var outputString = "";              //starts the output string blank
    var count = 0;              //This is to keep track of the amount of digits in the binary number

while (powerOfTwo <= decimalTemp) {     //Loops to find one power of 2 OVER the number imputed

        powerOfTwo *=2;     //Every loop it squares two.

}
    powerOfTwo /=2;     //Now that the power of two is one OVER the input number, divide by 2 to find one square less.
    powerOfTwo =  Math.floor(powerOfTwo);


    while (powerOfTwo > 0) {       //Keep doing this loops till powerOfTwo is less then 0
    //alert(powerOfTwo + "," + decimalTemp + "," + outputString);


        if (powerOfTwo > decimalTemp) {
            outputString += "0";    //adds a 0 to the string
        }

    else {
            outputString += "1";
            decimalTemp -= powerOfTwo;      //adds a 1 to the string AND subtracts powerOfTwo from the temp version of the input
        }

        powerOfTwo /=2;     // bring the power of two one down
        powerOfTwo =  Math.floor(powerOfTwo);

        count+= 1;      //This is to keep track of the amount of digits in the binary number
    }



    if (outputString === "") {      //If the number entered is 0, it ignores everything and outputs 0
        outputString = "0";
        count = 1;      //This is to keep track of the amount of digits in the binary number
    }
    alert(outputString + " is a " + count + " digit binary number");       // final alert telling answer
}

//Everything Below is a W.I.P

/**


function makeBaseX(x) {

    var decimalInput = document.getElementById("Basex").value;      //Gets input value.
    var powerOfX = 1;
    var decimalTemp = decimalInput;
    var outputString = "";
    var count = 0;

    while (powerOfX <= decimalTemp) {

        powerOfX *=x;

    }
    powerOfX /=x;
    powerOfX =  Math.floor(powerOfX);
   // alert(powerOfX);
    while (powerOfX > 0) {
        //alert(powerOfTwo + "," + decimalTemp + "," + outputString);

        var index = 0;
        var indexx = powerOfX;

        while (indexx <= decimalTemp){

               indexx*= x;
               index++;

        }

        powerOfX /=x;
        powerOfX =  Math.floor(powerOfX);
        indexx--;
        //alert(powerOfX + " , " + index);

        alert(index + "," + indexx);

        // if (powerOfX > decimalTemp) {
        //     outputString += "0";
        // }
        // else {
        //     outputString+= index.toString();
        //     decimalTemp-= (powerOfX* index);
        // }
        outputString+= index.toString();
        decimalTemp-= (powerOfX* index);

      //  alert(decimalTemp + " , "  + outputString);




        powerOfX /=x;
        powerOfX =  Math.floor(powerOfX);
      //  alert(powerOfX + " " + decimalTemp );
        count+= 1;
    }



    if (outputString === "") {
        outputString = "0";
        count = 1;
    }

  alert(outputString);
}



**/
