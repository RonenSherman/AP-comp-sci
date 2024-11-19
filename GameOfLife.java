import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

public class GameOfLife { // Ronen Sherman - Conway's Game of Life using  java swing

    public static void main(String[] args)
    {




        GameOfLife();
       //  makeGui();
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
        button1.addActionListener(new StartsGame());
        frame.add(button1);
        frame.setVisible(true);

    }
    static void GameOfLife()
    {

        DrawingPanel panel = new DrawingPanel(600, 400);
        Graphics g = panel.getGraphics();
        panel.addMouseListener(new SetLiveCells());
        g.setColor(Color.BLACK);
        panel.setVisible(true);

            int rows = 50;
        int columns = 50;
        boolean Grid[][] = new boolean[rows][columns];


       /* for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Grid[i][j]) {
                 //   textArea.append(" ■ "); // if true
                } else {
                   // textArea.append(" ▢ "); // if false
                    g.fillRect(i,j,15,15);
                }
            }
          //  g.fillRect(i,j,15,15);
        }*/
        int size = 12;
        for (int i = 0; i < Grid.length ;i ++) {
            for (int j = 0; j < Grid[i].length; j ++) {
                if(!Grid[i][j]) {
                    g.fillRect(i*size, j*size, size - 1, size - 1);
                }else {
                    g.drawRect(i*size, j*size, size, size);
                }
            }
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
    public static class StartsGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameOfLife();
        }
    }
}
