package evil.hangman;



import java.util.ArrayList;     // Used to create ArrayLists dictionary use
import java.util.Scanner;
import java.io.*;               // Used for IOException, File


public class Dictionary {

    // Declare a dynamically allocated ArrayList of Strings for the dictionary.
    // The dictionary can hold any number of words.
    ArrayList<String> dictionary;
    private File dictionaryFile;

	// Constructor
	Dictionary() {
        // Define the instance of the dictionary ArrayList
        dictionary = new ArrayList<>();
        // Now fill the dictionary array list with words from the dictionary file
        readInDictionaryWords();
	}//end Constructor
	

	//---------------------------------------------------------------------------------
    // Read in the words to create the dictionary.
        
        
    public void readInDictionaryWords() {
               
        FileReader r = null;
        try {
            r = new FileReader("src/resources/Dictionary.txt");
            
        } 
        catch (FileNotFoundException ex) {

                System.out.println("*** Error *** \n" +
                                   "Your dictionary file has the wrong name or is " +
                                   "in the wrong directory.  \n" +
                                   "Aborting program...\n\n");
                System.exit( -1);    // Terminate the program

        }
     Scanner f = new Scanner(r);

     while(f.hasNextLine()) {
        dictionary.add(f.nextLine());
     }

    }
    
    
    //---------------------------------------------------------------------------------
    // Allow looking up a word in dictionary, returning a value of true or false
    public boolean wordExists(String wordToLookup) {
        if ( dictionary.contains( wordToLookup.toUpperCase())) {
            return true;    // words was found in dictionary
        }
        else {
            return false;   // word was not found in dictionary    
        }
    }
    
    
    /**
     *
     * @return number of words in dictionary
     */
    public int numberOfWordsInDictionary() {
    	return dictionary.size();
    }
    
    /** return word at a particular position in dictionary
     *
     * @param index position
     * @return word at a particular position in dictionary
     */
    public String wordAtIndex( int index) {
    	return dictionary.get( index);
    }
     
    
    public int indexOfWord(String wordToLookup) {
        for(int i = 0;i<dictionary.size();i++)
        {
            if (dictionary.get(i).equalsIgnoreCase(wordToLookup))
                return i;
        }
        return -1;
    }
    
    
    public ArrayList getDictionary() {
        return dictionary;
    }
    
    
    /** returns an array of words that do not contain the letters
     *  the words will be of a certain length only
     * @param letters letters to be removed - sequence :{letter, letter, letter}
     * @param len length of words wanted
     * @return array of words that do not contain the letters
     */
    public ArrayList getArrayNoMatch(String letters, int len) {
        ArrayList<String> newList = new ArrayList<>();
        
//            for (int w = 0; w < dictionary.size(); w++) {
//                boolean thisWordStays = true;
//                for (int i = 0; i < letters.length(); i+=2) {
//                    if (dictionary.get(w).contains(letters.substring(i, i + 1)) || (dictionary.get(w).length()) != len) {
//                        thisWordStays = false;
//                    }
//                }
//                if (thisWordStays) {
//                    newList.add(dictionary.get(w));
//                }
//            }
            
            
            for (String w : dictionary) {
                boolean thisWordStays = true;
                for (int i = 0; i < letters.length(); i+=2) {
                    if (w.contains(letters.substring(i, i + 1)) || (w.length()) != len) {
                        thisWordStays = false;
                    }
                }
                if (thisWordStays) {
                    newList.add(w);
                }
            }
            
            
            
        return newList;
    }

}
//end class Dictionary