import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.IOException;
import javax.swing.JTextField;


import static java.nio.file.Files.readString;


public class TextEditorGUI { //       Ronen Sherman File text editor,   esc key is save button

    static ArrayList<String> FileNames = new ArrayList<String>();

    public static void main(String[] args) throws FileNotFoundException { // sets up the gui
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 250);
        frame.setLayout(new FlowLayout());


        JButton button1 = new JButton();
        button1.setText("Create new File");
        button1.setPreferredSize(new Dimension(300, 100));
        button1.addActionListener(new FileCreatePressed());
        frame.add(button1);
        JButton button2 = new JButton();
        button2.setText("Edit existing File");
        button2.setPreferredSize(new Dimension(300, 100));
        button2.addActionListener(new EditFilePressed());
        frame.add(button2);
        // frame.pack();
        frame.setVisible(true);
    }

    public static class FileCreatePressed implements ActionListener {// creates a new .txt file
        @Override
        public void actionPerformed(ActionEvent e) {
            String Filename = JOptionPane.showInputDialog(null, "How would you like to name this file?");
            try {
                File myObj = new File(Filename + ".txt");
                FileNames.add(Filename);
                if (myObj.createNewFile()) {
                    JOptionPane.showMessageDialog(null, "File created: " + myObj.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "File already exists.");
                }
            } catch (IOException h) {
                JOptionPane.showMessageDialog(null, "damn you really screwed this up");
                h.printStackTrace();
            }
        }
    }
    public static class EditFilePressed implements ActionListener, KeyListener { //opens a file for you to edit
        String input;
        Path filePath;

        private final JTextArea field = new JTextArea();

        @Override

        public void actionPerformed(ActionEvent e) {
            input = JOptionPane.showInputDialog(null, "Which file would you like to open?");
             filePath = Path.of("/Users/shermanr/IdeaProjects/AP-comp-sci/" + input + ".txt");
            JFrame frame = new JFrame();
            String content = null;
            try {
                content = Files.readString(filePath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.add(field);
            field.setEditable(true);
            frame.setPreferredSize(new Dimension(500, 500));
            frame.pack();
            frame.setVisible(true);
            field.addKeyListener(this);
            field.setText(content);
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e){}

        @Override
        public void keyReleased(KeyEvent e) {//

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                String text = field.getText();
                try {
                    Files.writeString(filePath, text);
                    String fileContent = readString(filePath);
                    System.out.println(fileContent);
                } catch (IOException f) {
                    System.out.println("oops...");
                }
            }
        }
    }
}