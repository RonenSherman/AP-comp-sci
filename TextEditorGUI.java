import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class TextEditorGUI { //       Ronen Sherman File text editor

   static ArrayList<String> FileNames = new ArrayList<String>();
    public static void main(String[] args) {
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
    public static class EditFilePressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(null, "Which file would you like to open?");
            JFrame frame = new JFrame("File input ");
            String filenames = input + ".txt";
            Path fileName = Path.of("/Users/shermanr/IdeaProjects/AP-comp-sci/" + filenames);
           JTextField field = new JTextField();
            frame.add(field);
            frame.setVisible(true);

            String text = field.getText();
         //field.write(new Writer(FileNames));


            try {
                Files.writeString(fileName, text);
                String fileContent = Files.readString(fileName);
                System.out.println(fileContent);
            } catch (IOException f) {

                System.err.println("An error occurred: " + f.getMessage());
            }

                }
            }
    }