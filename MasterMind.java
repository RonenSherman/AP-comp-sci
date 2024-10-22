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
        g.input = "4567";
        int b = 0;
        int w = 0;
        int used = 0;

        Boolean[] CheckedAns = { false, false, false, false };
        Boolean[] CheckedInput = { false, false, false, false };
        //   List<Boolean> list = CheckedInput.asList(CheckedInput);

        for (int i = 0; i < 4; i++)
        {
            if ( g.Code.charAt(i) == g.input.charAt(i))
                g.BlackPegs+=1;  CheckedInput[i] = true;CheckedAns[i] = true;
        }
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if ((g.Code.charAt(j) == g.input.charAt(i)) && !CheckedAns[j] && !CheckedInput[i] && (i!=j)) {
                    w+=1;
                    CheckedInput[i] = true; CheckedAns[j] = true;
                }
            }
        }
        // Return the hit counts in the order required!

            System.out.println(g.BlackPegs);
            System.out.println(g.WhitePegs);
        }
    }
