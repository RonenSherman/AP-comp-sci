import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class GameOfLife {

    public static void main(String[] args)
    {
        GameOfLife();
        // makeGui();
    }

    static void makeGui()
    {
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 250);
        frame.setLayout(new FlowLayout());


        JButton button1 = new JButton();
        button1.setText("New game of life");
        button1.setPreferredSize(new Dimension(300, 100));
        frame.add(button1);
        frame.setVisible(true);

    }
    static void GameOfLife()
    {

        int rows = 10;
        int columns = 10;
        boolean Matrix[][] = new boolean[rows][columns];
        java.util.Arrays.fill(Matrix[0], false);
        java.util.Arrays.fill(Matrix[1], false);



        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Matrix[i][j]) {
                    System.out.print(" ■ "); // if true
                } else {
                    System.out.print(" ▢ "); // if false
                }
            }
            System.out.println();
        }
    }

    public static class SetLiveCells implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}