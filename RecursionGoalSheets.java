import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RecursionGoalSheets {

    public static void main(String[] args) {
        readDictionaryAndInput();

    }

    public static String StarString(int n) {
        if (n == 0) {
            return "*";
        } else {
            String output = StarString(n - 1);
            return output + output;
        }
    }

    public static String WriteNums(int n) {
        if (n == 0) {
            return "1";
        } else {
            String output = (WriteNums(n - 1));

            return output + "," + (n + 1);

        }
    }

    public static String writeSequence(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be at least 1");
        }
        if (n == 1) {
            return "1";
        } else if (n == 2) {
            return "1 1";
        } else {
            int num = (n + 1) / 2; // Determine the outermost number
            return num + " " + writeSequence(n - 2) + " " + num;
        }
    }

    public static double sumTo(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Input must be at least 1.");
        else if (n == 0) {
            return 0.0;
        } else {
            return 1 / (double) n + sumTo((n - 1));
        }

    }

    public static int Fibonacci(int n) {
        if (n <= 1) return n;
        int prev1 = 0, prev2 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return prev2;
    }

    public static String writeSquares(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Input must be at least 1.");
        }
        if (n == 1) {
            return "1";
        }

        String result;
        if (n % 2 == 1) {
            result = n * n + ", " + writeSquares(n - 1);
        } else {
            result = writeSquares(n - 1) + ", " + (n * n);
        }
        return result;
    }

    public static void MergeSortInput(int n) {
        /*Divide the unsorted list into n sub-lists, each containing one element
        (a list of one element is considered sorted).
        Repeatedly merge sublists to produce new sorted sublists until
         there is only one sublist remaining. This will be the sorted list.
        */

        List<String> unsortedWords = new ArrayList<>();
        System.out.println("Enter a list of eight words");
        Scanner stringScanner = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            String input = stringScanner.next();
            unsortedWords.add(input.toLowerCase());
        }
        List<String> sorted = mergeSort(unsortedWords);
        System.out.println(sorted);
    }

    public static List<String> mergeSort(List<String> list) {
        {
            if (list.size() <= 1) return list; // Base case

            int mid = list.size() / 2;
            List<String> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
            List<String> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

            // Merging step
            List<String> sorted = new ArrayList<>();
            int i = 0, j = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i).compareTo(right.get(j)) <= 0) {
                    sorted.add(left.get(i++));
                } else {//hk
                    sorted.add(right.get(j++));
                }
            }
            while (i < left.size()) sorted.add(left.get(i++));
            while (j < right.size()) sorted.add(right.get(j++));

            return sorted;

        }
    }

    private static void readDictionaryAndInput() {
        List<String> dictionary = readDictionary();

        

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first word: ");
        String word1 = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter the second word: ");
        String word2 = scanner.nextLine().trim().toLowerCase();
        scanner.close();

      
        // Find indices using binary search
        int index1 = binarySearch(dictionary, word1, 0, dictionary.size() - 1);
        int index2 = binarySearch(dictionary, word2, 0, dictionary.size() - 1);

        int countBetween = index2 - index1 - 1;
        System.out.println("Number of words between '" + word1 + "' and '" + word2 + "': " + countBetween);
    }

    private static List<String> readDictionary() {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
            Collections.sort(words); // Ensure dictionary is sorted
        } catch (IOException e) {
            System.out.println("Error reading dictionary file.");
        }
        return words;
    }

    private static int binarySearch(List<String> words, String target, int left, int right) {
        if (left > right) {
            return left; // If not found, return the insertion point
        }

        int mid = left + (right - left) / 2;
        int cmp = words.get(mid).compareTo(target);

        if (cmp == 0) return mid; // Word found
        if (cmp < 0) return binarySearch(words, target, mid + 1, right); // Search right
        return binarySearch(words, target, left, mid - 1); // Search left
    }
}
