import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;



public class MasterMind {

    public static class GameData {
        public String input = "";
        public String Code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000 + 1));
        public boolean[] blackPegLocation = {false,false,false,false};
        public boolean[] WhitePegLocation = {false,false,false,false};
        public char[] values = {'1','2','3','4'};
        public char[] WhitePegValues = {'1','2','3','4'} ;
        public int BlackPegs = 0;
        public int guesses = 0;
        public int WhitePegs = 0;
    }

    public static void main(String[] args) {
        GameData g = new GameData();
        while(!Objects.equals(g.input, g.Code))
        {
            GetGuess(g);
        }
    }

    static void GetGuess(GameData g) {
        g.guesses++;
        if(g.BlackPegs == 0)
        {
            g.input = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000 + 1));
            ScoringFunction(g);

        } else {
            g.values = g.input.toCharArray();
            for (int i = 0; i < 4; i++) {
                if (!g.blackPegLocation[i]) {
                    char newguess = (char)ThreadLocalRandom.current().nextInt('0', '9' + 1);
                    g.values[i] = newguess;
                }
            }
            g.input = String.valueOf((g.values));
            ScoringFunction(g);
        }
    }

    static int[] ScoringFunction(GameData g) {
        g.BlackPegs = 0;
        g.WhitePegs = 0;
        List<Integer> list = new ArrayList<>(); // code for - 100% success - it finally works
        for (int i = 0; i < 4; i++) {
            if (g.input.charAt(i) == g.Code.charAt(i)) {
                g.BlackPegs++;
                g.values[i] = g.Code.charAt(i);
                g.blackPegLocation[i] = true;
            }
            for (int j = 0; j < 4; j++) {
                if ((g.input.charAt(i) == g.Code.charAt(j)) && !list.contains(j) && g.Code.charAt(j) != g.input.charAt(j) && g.Code.charAt(i) != g.input.charAt(i)) {
                    list.add(j);
                    g.WhitePegLocation[j] = true;
                    g.WhitePegValues[j] = g.input.charAt(j);
                    g.WhitePegs++;
                    break;
                }
            }
        }
        // Return the hit counts in the order required!
        System.out.println("Code is: " + g.Code + " input is: " + g.input);
        System.out.println("Guesses is: " + g.guesses);
        System.out.println(g.BlackPegs);
        System.out.println(g.WhitePegs);
        return new int[]{g.BlackPegs, g.WhitePegs};
    }
}