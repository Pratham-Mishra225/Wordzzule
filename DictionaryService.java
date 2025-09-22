import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * DictionaryService handles all dictionary-related operations for the Word Ladder solver.
 * This class is responsible for loading words from a file and providing fast word validation.
 * 
 * The core design uses a HashSet for dictionary storage because:
 * - HashSet provides O(1) average time complexity for word lookups
 * - We need to frequently check if words are valid during the BFS algorithm
 * - Memory usage is reasonable for typical dictionary sizes
 * 
 * @author Wordzzule Team
 * @version 1.0
 */
public class DictionaryService {
    
    /**
     * The dictionary of valid words stored as a HashSet.
     * HashSet is chosen because:
     * - Fast O(1) average lookup time for contains() operations
     * - No duplicate words (Set property)
     * - Perfect for our "is this word valid?" use case
     */
    private Set<String> dictionary;
    
    /**
     * Constructor initializes an empty dictionary.
     * The HashSet is created ready to hold dictionary words.
     */
    public DictionaryService() {
        // Initialize the dictionary as an empty HashSet
        this.dictionary = new HashSet<String>();
    }
    
    /**
     * Loads a dictionary from a text file where each line contains one word.
     * All words are converted to lowercase for consistent comparison.
     * 
     * @param filePath the path to the dictionary file
     * @throws FileNotFoundException if the dictionary file cannot be found
     */
    public void loadDictionary(String filePath) {
        BufferedReader reader = null;
        
        try {
            // Create a BufferedReader to efficiently read the file line by line
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Convert to lowercase for consistent word comparison
                // Trim whitespace in case the file has extra spaces
                String word = line.trim().toLowerCase();
                
                // Only add non-empty words to the dictionary
                if (!word.isEmpty()) {
                    dictionary.add(word);
                }
            }
            
            System.out.println("Dictionary loaded successfully! " + dictionary.size() + " words available.");
            
        } catch (FileNotFoundException e) {
            // Handle the case where the dictionary file doesn't exist
            System.err.println("Error: Dictionary file not found at '" + filePath + "'");
            System.err.println("Please make sure the file exists and the path is correct.");
            throw new RuntimeException("Dictionary file not found: " + filePath, e);
            
        } catch (IOException e) {
            // Handle any other file reading errors
            System.err.println("Error reading dictionary file: " + e.getMessage());
            throw new RuntimeException("Error reading dictionary file", e);
            
        } finally {
            // Always close the file reader to prevent resource leaks
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Warning: Could not close dictionary file properly.");
                }
            }
        }
    }
    
    /**
     * Checks if a given word exists in the loaded dictionary.
     * The word is converted to lowercase before checking for consistency.
     * 
     * Why this method is essential:
     * - During BFS, we generate many potential words by changing letters
     * - We need to quickly validate which of these potential words are real
     * - HashSet.contains() gives us O(1) average time complexity
     * 
     * @param word the word to check (will be converted to lowercase)
     * @return true if the word exists in the dictionary, false otherwise
     */
    public boolean isValidWord(String word) {
        // Convert to lowercase to match how words were stored in the dictionary
        String lowerCaseWord = word.toLowerCase();
        
        // Use HashSet's fast contains() method - this is why we chose HashSet!
        return dictionary.contains(lowerCaseWord);
    }
    
    /**
     * Returns the number of words currently in the dictionary.
     * Useful for debugging and displaying statistics to the user.
     * 
     * @return the number of words in the dictionary
     */
    public int getDictionarySize() {
        return dictionary.size();
    }
}