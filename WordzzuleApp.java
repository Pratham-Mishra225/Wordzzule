import java.util.List;
import java.util.Scanner;

/**
 * WordzzuleApp is the main entry point for the Word Ladder Solver application.
 * This class orchestrates user interaction, coordinates the other components,
 * and presents results in a user-friendly format.
 * 
 * Application Flow:
 * 1. Welcome the user and initialize components
 * 2. Load the dictionary from file
 * 3. Get user input for start and end words
 * 4. Use the solver to find the shortest word ladder
 * 5. Display the results clearly to the user
 * 
 * @author Wordzzule Team
 * @version 1.0
 */
public class WordzzuleApp {
    
    /**
     * Main method - the entry point of the Wordzzule application.
     * Handles the complete user interaction flow from start to finish.
     * 
     * @param args command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        
        // === WELCOME MESSAGE ===
        System.out.println("===============================================");
        System.out.println("    Welcome to Wordzzule - The Word Ladder Solver!");
        System.out.println("===============================================");
        System.out.println();
        System.out.println("Find the shortest path between two words by changing");
        System.out.println("one letter at a time. Every step must be a valid word!");
        System.out.println();
        System.out.println("Example: CAT -> COT -> DOT -> DOG");
        System.out.println();
        
        // === COMPONENT SETUP ===
        
        // Create instances of our core components
        DictionaryService dictionaryService = new DictionaryService();
        WordLadderSolver wordLadderSolver = new WordLadderSolver();
        
        // Define the dictionary file path
        // This assumes wordlist.txt is in the same directory as your Java files
        String dictionaryFilePath = "wordlist.txt";
        
        // === LOAD DICTIONARY ===
        
        System.out.println("Loading dictionary from '" + dictionaryFilePath + "'...");
        
        try {
            // Attempt to load the dictionary
            dictionaryService.loadDictionary(dictionaryFilePath);
            System.out.println("Dictionary loaded successfully!");
            System.out.println();
            
        } catch (RuntimeException e) {
            // Handle dictionary loading errors gracefully
            System.out.println("Failed to load dictionary. Please check that '" + dictionaryFilePath + "' exists.");
            System.out.println("Error details: " + e.getMessage());
            return; // Exit the program if we can't load the dictionary
        }
        
        // === USER INPUT ===
        
        // Create a Scanner for reading user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the START word:");
        System.out.print("> ");
        String startWord = scanner.nextLine().trim();
        
        System.out.println();
        System.out.println("Enter the END word:");
        System.out.print("> ");
        String endWord = scanner.nextLine().trim();
        
        System.out.println();
        
        // Convert input to lowercase for consistent processing
        startWord = startWord.toLowerCase();
        endWord = endWord.toLowerCase();
        
        // Display what we're searching for
        System.out.println("Searching for word ladder from '" + startWord.toUpperCase() + 
                          "' to '" + endWord.toUpperCase() + "'...");
        System.out.println();
        
        // === SOLVE THE PUZZLE ===
        
        // Use our solver to find the word ladder
        List<String> result = wordLadderSolver.findLadder(startWord, endWord, dictionaryService);
        
        // === DISPLAY RESULTS ===
        
        if (result != null) {
            // Success! We found a word ladder
            System.out.println("🎉 SUCCESS! Word ladder found:");
            System.out.println();
            
            // Display the ladder in a beautiful format
            displayWordLadder(result);
            
            // Show some statistics
            System.out.println();
            System.out.println("Ladder length: " + result.size() + " words");
            System.out.println("Number of steps: " + (result.size() - 1));
            
        } else {
            // No path was found
            System.out.println("❌ No word ladder found between '" + startWord.toUpperCase() + 
                              "' and '" + endWord.toUpperCase() + "'.");
            System.out.println();
            System.out.println("This could happen if:");
            System.out.println("• One or both words are not in the dictionary");
            System.out.println("• The words have different lengths");
            System.out.println("• No valid path exists between the words");
        }
        
        System.out.println();
        System.out.println("Thank you for using Wordzzule!");
        
        // Close the scanner to prevent resource leak
        scanner.close();
    }
    
    /**
     * Displays a word ladder in a beautiful, easy-to-read format.
     * Shows each word in the ladder with arrows between them and
     * highlights the changed letter in each step.
     * 
     * @param wordLadder the list of words forming the ladder
     */
    private static void displayWordLadder(List<String> wordLadder) {
        
        // Handle edge case of empty or single-word ladder
        if (wordLadder == null || wordLadder.isEmpty()) {
            System.out.println("(Empty ladder)");
            return;
        }
        
        if (wordLadder.size() == 1) {
            System.out.println(wordLadder.get(0).toUpperCase());
            return;
        }
        
        // Display each step of the ladder
        for (int i = 0; i < wordLadder.size(); i++) {
            String currentWord = wordLadder.get(i).toUpperCase();
            
            // Print the current word
            System.out.print(currentWord);
            
            // Add arrow if this is not the last word
            if (i < wordLadder.size() - 1) {
                System.out.print(" -> ");
                
                // Optional: Show which letter changed (for educational purposes)
                if (i < wordLadder.size() - 1) {
                    String nextWord = wordLadder.get(i + 1);
                    char changedLetter = findChangedLetter(wordLadder.get(i), nextWord);
                    if (changedLetter != ' ') {
                        System.out.print("(" + Character.toUpperCase(changedLetter) + ") ");
                    }
                }
            }
        }
        
        System.out.println(); // End the line
    }
    
    /**
     * Helper method to find which letter changed between two words.
     * This is used for educational display purposes.
     * 
     * @param word1 the first word
     * @param word2 the second word
     * @return the new letter that was introduced, or ' ' if multiple changes
     */
    private static char findChangedLetter(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return ' '; // Invalid comparison
        }
        
        char changedLetter = ' ';
        int changesFound = 0;
        
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                changedLetter = word2.charAt(i);
                changesFound++;
            }
        }
        
        // Only return the changed letter if exactly one letter changed
        return (changesFound == 1) ? changedLetter : ' ';
    }
}

/*
 * USAGE INSTRUCTIONS:
 * 
 * 1. COMPILATION:
 *    Open a terminal/command prompt in the directory containing your Java files.
 *    Compile all three files with:
 *    javac DictionaryService.java WordLadderSolver.java WordzzuleApp.java
 * 
 * 2. DICTIONARY FILE:
 *    Create a file named "wordlist.txt" in the same directory.
 *    Add one word per line. Example content:
 *    cat
 *    cot
 *    dot
 *    dog
 *    log
 *    hog
 *    hat
 *    hot
 *    hit
 *    bit
 *    bat
 *    
 * 3. EXECUTION:
 *    Run the program with:
 *    java WordzzuleApp
 *    
 * 4. INTERACTION:
 *    - Enter your start word when prompted
 *    - Enter your end word when prompted
 *    - The program will find and display the shortest word ladder
 *    
 * 5. EXAMPLE RUN:
 *    Start word: cat
 *    End word: dog
 *    Result: CAT -> COT -> DOT -> DOG
 *    
 * 6. TROUBLESHOOTING:
 *    - Make sure wordlist.txt exists and contains the words you're testing
 *    - Ensure both start and end words are in the dictionary
 *    - Words must have the same length
 *    - Check that all Java files compiled without errors
 */