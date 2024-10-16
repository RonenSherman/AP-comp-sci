import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class MasterMind {

    public static class GameData {
       public String input = "";
        public String Code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000 + 1));
        public int BlackPegs = 0;
        public int WhitePegs = 0;
    }
    public static void main(String[] args) {
        GameData g = new GameData();
     //   System.out.println(g.Code);
       GamePlay(g);
    }
    static void TakeInput(GameData g)
    {
        boolean isValidInput = false;
        while(!isValidInput) {
            System.out.println("guess a 4 digit number");
            Scanner scanner = new Scanner(System.in);
             g.input = scanner.nextLine();
            try {
                isValidInput = true;
            } catch (NumberFormatException nfe) {
                System.out.println("invalid input");
                isValidInput = false;
            }
            isValidInput = g.input.length() == 4 && isValidInput;
            }
        }
    static void GamePlay(GameData g) {
        //  TakeInput(g);
        g.Code = "6684";

        System.out.println(g.Code);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            g.input = scanner.nextLine();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < g.input.length(); i++) {
                for (int j = 0; j < g.input.length(); j++) {
                    if (g.input.charAt(i) == g.Code.charAt(j) && g.Code.charAt(j) != g.input.charAt(j) && !list.contains(j)) {
                        list.add(j);
                        g.WhitePegs++;
                        break;
                    }
                }
                if (g.input.charAt(i) == g.Code.charAt(i))
                    g.BlackPegs++;
            }
            System.out.println(g.BlackPegs);
            System.out.println(g.WhitePegs);
        }
    }
}