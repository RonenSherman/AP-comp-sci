import javax.swing.*;
import java.awt.*;

public class GUI {

public static void main(String[] args)
{
//JOptionPane.showMessageDialog(null, "Hello , World!");

    JFrame frame = new JFrame("Text Editor");
    frame.setForeground(Color.WHITE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBackground(Color.WHITE);
    frame.setSize(600, 250);
    frame.setLayout(new FlowLayout());
    String[] data = {"one", "two", "three", "four"};
    JList<String> myList = new JList<String>(data);
myList.setBackground(Color.WHITE);
    frame.add(myList);
    frame.setVisible(true);
}
}
