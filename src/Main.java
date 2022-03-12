import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String word;

    public static void main(String[] args) throws IOException {

        // Load properties file for words
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\sarat\\IdeaProjects\\Jwordle\\src\\wordsFile.properties"));
        String[] words = prop.get("theWords").toString().split(","); // Get words from file and store in array

        //Instructions
        System.out.println("B* O R^ E^ D*\n<letter>* => letter is in word and in correct position\n<letter>^=> letter is in word but in wrong position\n<just a letter> => letter is not in word\n");
        System.out.println("B* O R^ E^ D*\nB* R^ E^ A^ D*\nB* E* A* R* D*\n");
        System.out.println("^<letter>^ => More than one occurrence");
        System.out.println("C* R* ^E^ A K*\nC* R* E* E* K*");

        //Generate random five letter word
        Random rand = new Random();
        boolean flag = false;
        while (!flag) {
            int random = rand.nextInt(words.length);
            word = words[random].toUpperCase();
            if (word.length() == 5)
                flag = true;
        }

        //Input
        Scanner sc = new Scanner(System.in);

        //Gameplay
        boolean won = false;
        flag = false;
        int _try = 0; //Which try
        String guesses[] = {"_  _  _  _  _  ", "_  _  _  _  _  ", "_  _  _  _  _  ", "_  _  _  _  _  ", "_  _  _  _  _  ", "_  _  _  _  _  "}; // Stores the guesses
        while (!flag) { // While the game is not over
            for (int i = 0; i < guesses.length; i++)// Print the guesses
                System.out.println(guesses[i]);
            System.out.println("\nEnter guess: ");
            String guess = sc.nextLine().toUpperCase();
            if (guess.length() == 5) { // If the guess is 5 letters long
                String res = "";
                for (int i = 0; i < 5; i++) { // Check if each letter in the guess is correct
                    if (guess.charAt(i) == word.charAt(i))
                        res += guess.charAt(i) + "* ";
                    else if (word.contains(guess.charAt(i) + "")) {
                        String temp = word; // Temporary String to count the number of occurrences of a character
                        int occ = temp.length() - temp.replace(guess.charAt(i) + "", "").length(); // Number of occurences of the letter in the word
                        if (occ == 1)
                            res += guess.charAt(i) + "^ "; // If there is only one occurence
                        else
                            res += "^" + guess.charAt(i) + "^ "; // If there are more than one occurences
                    } else
                        res += guess.charAt(i) + " ";
                }

                // Update the guesses
                guesses[_try] = res;
                _try++;

                if (guess.equals(word)) { // If the guess is correct
                    flag = true; // Game is over
                    won = true; // Player won
                    System.out.println("You won!");
                    exit(); // Exit or new wordle
                } else if (_try == 6) {  // If the player has used all his tries
                    flag = true; // Game is over, player lost
                    System.out.println("You lost :(\nThe word was: " + word);
                    exit(); // Exit or new wordle
                }
            } else System.out.println("Enter five letter word");
        }
    }

    // Mutual recursive function to exit or new game
    public static void exit() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDo you want to play again? (y/n)");
        String ans = sc.nextLine();
        if (ans.equals("y")) main(null); // If yes, start a new game
        else if (ans.equals("n"))
            System.out.println("Goodbye!"); // If no, exit
        else {
            System.out.println("Invalid input"); // If invalid input
            exit(); // Ask again
        }
    }
}
