import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;



import static java.nio.file.Files.readString;


public class TextEditorGUI { //       Ronen Sherman File text editor

    static ArrayList<String> FileNames = new ArrayList<String>();
    public static void main(String[] args)throws FileNotFoundException {
        JFrame frame = new JFrame("Text Editor");
        frame.setForeground(Color.CYAN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.YELLOW);
        frame.setSize(600, 250);
        frame.setLayout(new FlowLayout());



        JButton button1 = new JButton();
        button1.setText("Create new File");
        button1.setPreferredSize(new Dimension(300,100));
        button1.addActionListener(new FileCreatePressed());
        frame.add(button1);
        JButton button2 = new JButton();
        button2.setText("Edit existing File");
        button2.setPreferredSize(new Dimension(300,100));
        button2.addActionListener(new EditFilePressed());
        frame.add(button2);
        // frame.pack();
        frame.setVisible(true);
    }
    public static class FileCreatePressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Filename = JOptionPane.showInputDialog(null, "How would you like to name this file?");
            try {
                File myObj = new File(Filename + ".txt");
                FileNames.add(Filename);
                if (myObj.createNewFile()) {
                    JOptionPane.showMessageDialog(null,"File created: " + myObj.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "File already exists.");

                }
            } catch (IOException h) {
                JOptionPane.showMessageDialog(null, "damn you really screwed this up");
                h.printStackTrace();
            }
        }
    }
    public static class EditFilePressed implements ActionListener { // file stuff
        @Override
        public void actionPerformed(ActionEvent e)  {



            String input = JOptionPane.showInputDialog(null, "Which file would you like to open?");

            Path filePath = Path.of("C:\\Users\\roshe\\IdeaProjects\\AP-comp-sci\\" + input + ".txt");
            String content = null;
            try {
                content = Files.readString(filePath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JFrame frame = new JFrame();
            JTextField field = new JTextField();
            frame.add(field);
            frame.setPreferredSize(new Dimension(500,500));
            frame.pack();
            frame.setVisible(true);
            field.setText(content);

            //field.write(new Writer(FileNames));


            String text = field.getText();
          /*  try {
                Files.writeString(filePath, text);
                String fileContent = readString(filePath);
                System.out.println(fileContent);
            } catch (IOException f) {

                System.err.println("An error occurred: " + f.getMessage());
            }*/

        }
    }


    public static class EnterKeyPressed implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {


        }
    }
}