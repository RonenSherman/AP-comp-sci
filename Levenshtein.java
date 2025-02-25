import java.io.*;
import java.util.*;

public class Levenshtein {
    private static Set<String> dictionary = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first word: ");
        String startWord = scanner.nextLine().toLowerCase();

        System.out.print("Enter the second word: ");
        String targetWord = scanner.nextLine().toLowerCase();

     Scanner fileScanner = new Scanner(new File("C:\\Users\\shermanr\\IdeaProjects\\Random java work\\dictionarySorted (1).txt")); {
            while (fileScanner.hasNextLine()) {
                dictionary.add(fileScanner.nextLine().trim().toLowerCase());
            }
        ArrayList<String> path = transformWord(startWord, targetWord);

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

    private static ArrayList<String> transformWord(String start, String target) {
        if (!dictionary.contains(start) || !dictionary.contains(target)) {
            System.out.println("Start or target word not found in dictionary.");
            return null;
        }


        Queue<ArrayList<String>> queue = new LinkedList<>();
        queue.add(new ArrayList<>(Collections.singletonList(start)));
        Set<String> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            ArrayList<String> path = queue.poll();
            String current = path.get(path.size() - 1);

            if (current.equals(target)) {
                return path;
            }


            for (String neighbor : getValidTransformations(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    ArrayList<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        return null;
    }

    private static ArrayList<String> getValidTransformations(String word) {
        ArrayList<String> transformations = new ArrayList<>();
        char[] chars = word.toCharArray();

        // Substitutions
        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        transformations.add(newWord);
                    }
                }
            }
            chars[i] = originalChar;
        }

        // Insertions
        for (int i = 0; i <= chars.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(newWord)) {
                    transformations.add(newWord);
                }
            }
        }

        // Deletions
        for (int i = 0; i < chars.length; i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(newWord)) {
                transformations.add(newWord);
            }
        }
        if (transformations.isEmpty()) {
            System.out.println("No valid transformations found for word: " + word);
        }
        return transformations;
    }
}
