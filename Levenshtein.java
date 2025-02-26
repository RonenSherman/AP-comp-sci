import java.io.*;
import java.util.*;

public class Levenshtein {
    // Ronen Sherman Levenshtein using Wagner-Fischer and BFS.
    private static final Set<String> dictionary = new HashSet<>();
    // Set to store dictionary words for quick lookup

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // Using try-with-resources for safe handling
            System.out.print("Enter the first word: ");
            String startWord = scanner.nextLine().toLowerCase(); // Convert input to lowercase for consistency

            System.out.print("Enter the second word: ");
            String targetWord = scanner.nextLine().toLowerCase();

            // Load dictionary from file
            try (Scanner fileScanner = new Scanner(new File("C:\\Users\\shermanr\\IdeaProjects\\Random java work\\dictionarySorted (1).txt"))) {
                while (fileScanner.hasNextLine()) {
                    dictionary.add(fileScanner.nextLine().trim().toLowerCase()); // Trim and store words in lowercase
                }
            } catch (FileNotFoundException e) {
                System.out.println("Dictionary file not found."); // Handle missing file scenario
                return;
            }

            // Find the transformation path from startWord to targetWord
            ArrayList<String> path = transformWord(startWord, targetWord);

            // Print the transformation path if found, otherwise indicate failure
            if (path != null) {
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
     * uses BFS to find the shortest path from start to target.
     * BFS (breadth first search) is different from DFS (which I used in a previous program).
     * This works by first spreading out, exploring all the sides first (hence the "Breadth" in the name) And then moving forwards level by level.
     * BFS is widely accepted as one of the best "shortest path" algorithms (A* and Dijkstra's are improvements on BFS)
     */
    private static ArrayList<String> transformWord(String start, String target) {
        // Check if both words exist in the dictionary
        if (!dictionary.contains(start) || !dictionary.contains(target)) {
            System.out.println("Start or target word not found in dictionary.");
            return null;
        }

        Queue<ArrayList<String>> queue = new ArrayDeque<>(); // BFS queue to track transformation paths
        queue.add(new ArrayList<>(Collections.singletonList(start))); // Initialize with start word
        Set<String> visited = new HashSet<>(); // Set to keep track of visited words
        visited.add(start);

        while (!queue.isEmpty()) {
            ArrayList<String> path = queue.poll(); // Retrieve and remove the front path in queue
            String current = path.getLast(); // Get the last word in the current transformation path

            // If we reach the target word, return the transformation path
            if (current.equals(target)) {
                return path;
            }

            // Explore all valid transformations
            for (String neighbor : getValidTransformations(current)) {
                if (!visited.contains(neighbor)) {
                    ArrayList<String> newPath = new ArrayList<>(path); // Copy current path
                    newPath.add(neighbor); // Add new word to the path
                    queue.add(newPath); // Enqueue the new path for further exploration
                    visited.add(neighbor); // Mark word as visited
                }
            }
        }
        return null; // No valid transformation path found
    }

    /**
     * Generates all valid transformations of a given word by performing:
     * - Single character substitutions
     * - Single character insertions
     * - Single character deletions
     */
    private static ArrayList<String> getValidTransformations(String word) {
        ArrayList<String> transformations = new ArrayList<>();
        char[] chars = word.toCharArray();

        // Substitutions: Replace each character with 'a' to 'z' and check validity
        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) { // Avoid replacing a character with itself
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) { // Check if it's a valid word
                        transformations.add(newWord);
                    }
                }
            }
            chars[i] = originalChar; // Restore original character before next iteration
        }

        // Insertions: Insert 'a' to 'z' at every possible position in the word
        for (int i = 0; i <= chars.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(newWord)) { // Check if it's a valid word
                    transformations.add(newWord);
                }
            }
        }

        // Deletions: Remove one character at a time
        for (int i = 0; i < chars.length; i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(newWord)) { // Check if it's a valid word
                transformations.add(newWord);
            }
        }

        // message if there are no transformations found
        if (transformations.isEmpty()) {
            System.out.println("No valid transformations found for word: " + word);
        }

        return transformations; // Return all valid transformed words
    }
}
