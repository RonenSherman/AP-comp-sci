import java.util.Scanner;
import java.io.*;
public class TextEditor
{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Text");
        String input = myObj.nextLine();
        filereader(input);
    }

    public static void filereader(String input)
            throws FileNotFoundException
    {
        PrintStream output = new PrintStream("Text.txt");
        output.println(input);
    }


}
