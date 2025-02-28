import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Ronen Sherman (had some help with the BFS from my dad)
// Implements Levenshtein distance using Wagner-Fischer and BFS
// This approach finds the shortest transformation sequence between two words
// Dictionary words are preloaded from a file for quick access



public class Levenshtein {
    private static final Set<String> dictionary = new HashSet<>();
    // HashSet for fast lookups of dictionary words

    public static void main() {

        try (Scanner scanner = new Scanner(System.in)) { // Using try to ensure scanner is closed automatically
            System.out.print("Enter the first word: ");
            String startWord = scanner.nextLine().toLowerCase(); // Convert input to lowercase to ensure case insensitivity

            System.out.print("Enter the second word: ");
            String targetWord = scanner.nextLine().toLowerCase();

            // Load dictionary from file
            try (Scanner fileScanner = new Scanner(new File("C:\\Users\\shermanr\\IdeaProjects\\Random java work\\dictionarySorted (1).txt"))) {
                while (fileScanner.hasNextLine()) {
                    dictionary.add(fileScanner.nextLine().trim().toLowerCase()); // Trim and toLower to ensure consistency
                }
            } catch (FileNotFoundException e) {
                System.out.println("Dictionary file not found. Please check the file path.");
                return; // Exit program if dictionary file is missing
            }

            // Attempt to transform the startWord to targetWord
            ArrayList<String> path = transformWord(startWord, targetWord);

            // Print the transformation path if found
            if (path != null) {
                System.out.println("Edit Distance: " + (path.size() - 1));
                System.out.println("Transformation Path:");
                for (String word : path) {
                    System.out.println(word);
                }
            } else {
                System.out.println("No valid transformation path found.");
            }
        }
    }

    /**
     * Uses BFS to find the shortest transformation path from start word to target word.
     * BFS ensures that the shortest sequence of valid transformations is found first.
     */
    private static ArrayList<String> transformWord(String start, String target) {
        // Ensure both words exist in the dictionary before proceeding
        if (!dictionary.contains(start) || !dictionary.contains(target)) {
            System.out.println("Start or target word not found in dictionary.");
            return null;
        }

        Queue<ArrayList<String>> queue = new ArrayDeque<>(); // BFS queue storing paths as lists of words
        queue.add(new ArrayList<>(Collections.singletonList(start))); // Initialize queue with start word
        Set<String> visited = new HashSet<>(); // Set to keep track of words that have already been processed
        visited.add(start); // Mark the start word as visited

        /*
          BFS PROCESS:
          --------------
          - We use a queue to explore words level by level (i.e., all words reachable in 1 step, then 2 steps, etc.).
          - Each element in the queue is a path (list of words) representing a possible transformation sequence.
          - We always explore the shortest transformation sequences first.
          - When we reach the target word, we immediately return that sequence.
         */
        while (!queue.isEmpty()) {
            ArrayList<String> path = queue.poll(); // Retrieve and remove the front path from the queue
            String current = path.getLast(); // Get the last word in the current transformation sequence

            // If we have reach the target word, return the transformation path
            if (current.equals(target)) {
                return path; // Shortest path found using bfs
            }

            // Generate all valid next transformations (neighbors)
            for (String neighbor : getValidTransformations(current)) {
                if (!visited.contains(neighbor)) { // Ensure we don't process the same word multiple times
                    ArrayList<String> newPath = new ArrayList<>(path); // Clone the current path
                    newPath.add(neighbor); // Append the new transformation
                    queue.add(newPath); // Add new path to the queue (to be explored in the next levels)
                    visited.add(neighbor); // Mark this word as visited
                }
            }
        }
        return null; // if it breaks we return null
    }

    /**
     * Generates all valid words that can be reached from the given word by:
     * - Replacing a single character
     * - Inserting a single character
     * - Deleting a single character
     * Only words present in the dictionary are considered valid transformations.
     */
    private static ArrayList<String> getValidTransformations(String word) {
        ArrayList<String> transformations = new ArrayList<>();
        char[] chars = word.toCharArray(); // Convert word into a character array for manipulation

        // Character substitution: Try replacing each character with every letter from 'a' to 'z'
        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i]; // Store original character
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) { // Ensure we are making a change
                    chars[i] = c;
                    String newWord = new String(chars); // Create new transformed word
                    if (dictionary.contains(newWord)) { // Check if it's a valid dictionary word
                        transformations.add(newWord);
                    }
                }
            }
            chars[i] = originalChar; // Restore original character before moving to the next position
        }

        // Character insertion: Insert every letter from 'a' to 'z' at every possible position
        for (int i = 0; i <= chars.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i); // Insert character at position i
                if (dictionary.contains(newWord)) { // Check if it's a valid dictionary word
                    transformations.add(newWord);
                }
            }
        }

        // Character deletion: Remove each character one at a time
        for (int i = 0; i < chars.length; i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1); // Remove character at index i
            if (dictionary.contains(newWord)) { // Check if it's a valid dictionary word
                transformations.add(newWord);
            }
        }

        // If no transformations are found, output a message for debugging purposes
        if (transformations.isEmpty()) {
            System.out.println("No transformations found");
        }

        return transformations; // Return list of valid words generated
    }
}
