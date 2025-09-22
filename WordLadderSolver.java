import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * WordLadderSolver implements the core Breadth-First Search (BFS) algorithm
 * to find the shortest word ladder between two words.
 * 
 * Why BFS for Word Ladder:
 * - BFS explores all paths of length N before exploring paths of length N+1
 * - This guarantees that the first solution found is the shortest possible
 * - Word Ladder is an unweighted graph problem (each step has equal cost)
 * - BFS is optimal for finding shortest paths in unweighted graphs
 * 
 * Algorithm Overview:
 * 1. Start with the initial word
 * 2. Generate all valid one-letter variations (neighbors)
 * 3. For each neighbor, continue building the path
 * 4. Track visited words to avoid cycles
 * 5. Return the first complete path found (guaranteed shortest)
 * 
 * @author Wordzzule Team
 * @version 1.0
 */
public class WordLadderSolver {
    
    /**
     * Finds the shortest word ladder from startWord to endWord using BFS algorithm.
     * 
     * The algorithm works by:
     * 1. Validating input words
     * 2. Using a queue to store complete paths (not just individual words)
     * 3. For each path, generating all possible next steps
     * 4. Tracking visited words to prevent infinite loops
     * 5. Returning the first complete path found (guaranteed shortest due to BFS)
     * 
     * @param startWord the word to start the ladder from
     * @param endWord the target word to reach
     * @param dictionaryService the service providing word validation
     * @return List of words representing the shortest ladder, or null if no path exists
     */
    public List<String> findLadder(String startWord, String endWord, DictionaryService dictionaryService) {
        
        // Convert inputs to lowercase for consistent comparison
        startWord = startWord.toLowerCase();
        endWord = endWord.toLowerCase();
        
        // === INITIAL VALIDATION ===
        
        // Check if words have the same length - required for word ladder rules
        if (startWord.length() != endWord.length()) {
            System.out.println("Error: Start word and end word must have the same length.");
            return null;
        }
        
        // Check if both words exist in the dictionary
        if (!dictionaryService.isValidWord(startWord)) {
            System.out.println("Error: Start word '" + startWord + "' is not in the dictionary.");
            return null;
        }
        
        if (!dictionaryService.isValidWord(endWord)) {
            System.out.println("Error: End word '" + endWord + "' is not in the dictionary.");
            return null;
        }
        
        // Special case: if start and end are the same word
        if (startWord.equals(endWord)) {
            List<String> singleWordPath = new ArrayList<String>();
            singleWordPath.add(startWord);
            return singleWordPath;
        }
        
        // === BFS INITIALIZATION ===
        
        /*
         * The queue stores complete paths (List<String>), not just individual words.
         * This is crucial because:
         * - We need to reconstruct the entire ladder when we find the solution
         * - Each queue entry represents a partial path from start word
         * - When we reach the end word, we immediately have the complete path
         */
        Queue<List<String>> pathQueue = new LinkedList<List<String>>();
        
        /*
         * visitedWords prevents us from revisiting the same word in different paths.
         * This is essential because:
         * - Prevents infinite loops
         * - Ensures we don't waste time on longer paths to the same word
         * - BFS guarantees the first time we visit a word is via the shortest path
         */
        Set<String> visitedWords = new HashSet<String>();
        
        // Create the initial path containing only the start word
        List<String> initialPath = new ArrayList<String>();
        initialPath.add(startWord);
        
        // Add the initial path to our queue and mark start word as visited
        pathQueue.add(initialPath);
        visitedWords.add(startWord);
        
        // === MAIN BFS LOOP ===
        
        while (!pathQueue.isEmpty()) {
            // Get the next path to explore
            List<String> currentPath = pathQueue.poll();
            
            // The last word in the current path is what we'll generate neighbors from
            String lastWord = currentPath.get(currentPath.size() - 1);
            
            // === GOAL CHECK ===
            // If we've reached the target word, we've found the shortest path!
            if (lastWord.equals(endWord)) {
                return currentPath;
            }
            
            // === FIND AND PROCESS NEIGHBORS ===
            
            // Get all valid neighboring words (one letter different)
            List<String> neighbors = getNeighbors(lastWord, dictionaryService);
            
            // For each valid neighbor, create a new path
            for (String neighbor : neighbors) {
                
                // Only process unvisited words
                if (!visitedWords.contains(neighbor)) {
                    
                    // Mark this neighbor as visited
                    visitedWords.add(neighbor);
                    
                    // Create a new path by copying the current path and adding the neighbor
                    List<String> newPath = new ArrayList<String>(currentPath);
                    newPath.add(neighbor);
                    
                    // Add this new path to the queue for future exploration
                    pathQueue.add(newPath);
                }
            }
        }
        
        // If we exit the loop without finding a path, no solution exists
        return null;
    }
    
    /**
     * Generates all valid neighboring words by changing exactly one letter.
     * A neighbor is valid if it exists in the dictionary and differs by exactly one letter.
     * 
     * Algorithm:
     * 1. For each position in the word
     * 2. Try replacing that position with each letter a-z
     * 3. Check if the resulting word is in the dictionary
     * 4. Collect all valid words found this way
     * 
     * @param word the word to find neighbors for
     * @param dictionaryService the service to validate words
     * @return List of all valid neighboring words
     */
    private List<String> getNeighbors(String word, DictionaryService dictionaryService) {
        List<String> neighbors = new ArrayList<String>();
        
        // Convert the word to a character array so we can modify individual letters
        char[] wordArray = word.toCharArray();
        
        // Try changing each position in the word
        for (int position = 0; position < wordArray.length; position++) {
            
            // Remember the original character at this position
            char originalChar = wordArray[position];
            
            // Try every letter of the alphabet at this position
            for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                
                // Skip if this would result in the same word
                if (newChar == originalChar) {
                    continue;
                }
                
                // Change the character at the current position
                wordArray[position] = newChar;
                
                // Create a new word from the modified character array
                String newWord = new String(wordArray);
                
                // Check if this new word is valid (exists in dictionary)
                if (dictionaryService.isValidWord(newWord)) {
                    neighbors.add(newWord);
                }
            }
            
            // CRITICAL: Restore the original character before moving to next position
            // This ensures we only change one letter at a time
            wordArray[position] = originalChar;
        }
        
        return neighbors;
    }
}