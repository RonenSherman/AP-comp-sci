import java.util.Arrays;
import java.util.Scanner;

public class Levenshtein {// Ronen Sherman

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter two words\n");
        String word1 = " " + scanner.nextLine().trim().toLowerCase();
        String word2 = " " + scanner.nextLine().trim().toLowerCase();
        scanner.close();
        int x = word1.length();
        int y = word2.length();

        int[][] Matrix = new int[x + 1][y + 1];

        for (int i = 0; i <= x; i++) {
            Matrix[i][0] = i; // Deletion cost
        }
        for (int j = 0; j <= y; j++) {
            Matrix[0][j] = j; // Insertion cost
        }
        System.out.println(Arrays.deepToString((Matrix)));



        }
    }
