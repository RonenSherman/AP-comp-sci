import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GUI {

public static void main(String[] args) throws IOException {
    String input = JOptionPane.showInputDialog(null, "Which file would you like to open?");

    Path filePath = Path.of("C:\\Users\\roshe\\IdeaProjects\\AP-comp-sci\\" + input + ".txt");
    String content = Files.readString(filePath);
    JFrame frame = new JFrame();
    JTextField field = new JTextField();
    frame.add(field);
    frame.setPreferredSize(new Dimension(500,500));
    frame.pack();
    frame.setVisible(true);
     field.setText(content);
}
}
