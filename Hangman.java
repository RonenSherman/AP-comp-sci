import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

    public class Hangman {
    public static class Playerdata {
        public List<String> Words;
        public List<Character> CorrectChars;
        public List<Character> Incorrectwords;
        public String word;
        public boolean isCorrect;
        public int guess;
        public int Correctguess;
        public int mistakes;
        public boolean isIncorrect;
    }

    public static void main(String[] args) {
        Playerdata p = new Playerdata();
        SetupGame(p);
    }

    static void SetupGame(Playerdata p) {
        p.mistakes = 0;
        p.isIncorrect = false;
        p.Correctguess = 0;
        p.isCorrect = false;
      Path filePath = Path.of("/Users/shermanr/IdeaProjects/AP-comp-sci/Hangman.txt");
        String content = null;
        try {
            content = Files.readAllLines(filePath).toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        p.Words = new ArrayList<>(Arrays.asList(content));
        p.CorrectChars = new ArrayList<>(Arrays.asList('_', '_', '_', '_', '_'));
        p.Incorrectwords = new ArrayList<>(Arrays.asList(' ', ' ', ' ', ' ', ' '));
        int RandomWord = new Random().nextInt(p.Words.size());
        p.word = p.Words.get(RandomWord);
        UpDateGame(p);
        DoGameLoop(p);
    }

    static void UpDateGame(Playerdata p) {
        switch (p.mistakes) {
            case 0:
                System.out.println("______\n|    |\n|\n|\n|\n|    " + String.join("", p.Incorrectwords.toString()) + "\n\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 1:
                System.out.println("______\n|    |\n|    O\n|\n|\n|    " + String.join(" ", p.Incorrectwords.toString()) + "\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 2:
                System.out.println("______\n|    |\n|    O\n|    |\n|\n|    " + String.join(" ", p.Incorrectwords.toString()) + "\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 3:
                System.out.println("______\n|    |\n|    O\n|    |\\\n|\n|    " + String.join(" ", p.Incorrectwords.toString()) + "\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 4:
                System.out.println("______\n|    |\n|    O\n|   /|\\\n|\n|    " + String.join(" ", p.Incorrectwords.toString()) + "\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 5:
                System.out.println("______\n|    |\n|    O\n|   /|\\\n|     \\\n|    " + String.join(" ", p.Incorrectwords.toString()) + "\n\n\n" + String.join(" ", p.CorrectChars.toString()));
                break;
            case 6:
                break;
        }
    }

    static void DoGameLoop(Playerdata p) {
        Scanner scanner = new Scanner(System.in);
        while (!p.isCorrect && !p.isIncorrect) {
            boolean Correct = false;
            char key = scanner.next().charAt(0);
            for (int i = 0; i < p.word.length(); i++) {
                if (key == p.word.charAt(i)) {
                    Correct = true;
                    p.Correctguess++;
                    p.CorrectChars.set(i, p.word.charAt(i));
                    UpDateGame(p);
                }
            }
            if (!Correct) {
                p.mistakes++;
                p.Incorrectwords.add(key);
                UpDateGame(p);
            }
            if (p.mistakes == 6) {
                p.isIncorrect = true;
            }
            if (p.Correctguess == 5) {
                p.isCorrect = true;
            }
        }
        GameEnd(p);
    }

    static void Gamechoice(Playerdata p) {
        Scanner scanner = new Scanner(System.in);
        String GameChoice = scanner.nextLine();
        switch (GameChoice) {
            case "yes":
                SetupGame(p);
                break;
            case "no":
                break;
            default:
                break;
        }
    }

    static void GameEnd(Playerdata p) {
        if (p.isCorrect) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Congratulations! you guessed the right word! The word was " + p.word + " \nDo you wish to play again? yes/no");
            Gamechoice(p);
        } else if (p.isIncorrect) {
            System.out.println("______\n|    |\n|    O\n|   /|\\\n|   / \\\n|    ");
            System.out.println("you failed, the word was " + p.word + "\nDo you wish to play again? yes/no");
            Gamechoice(p);
        }
    }
}

