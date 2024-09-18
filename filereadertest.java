// Importing required classes
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Main class
public class filereadertest {

    // Main driver method
    public static void main(String[] args) {

        String text = "hi there this is silly weehee";
        String filenames = "Eric.txt";

        Path fileName = Path.of("/Users/shermanr/IdeaProjects/AP-comp-sci/" + filenames);

        try {
            Files.writeString(fileName, text);
            String fileContent = Files.readString(fileName);
            System.out.println(fileContent);
        } catch (IOException e) {

            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
