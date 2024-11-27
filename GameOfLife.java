import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.Files.readString;

public class GameOfLife { // Ronen Sherman - Conway's Game of Life using  java swing
   static boolean DoGame = true;

    public static void main(String[] args) throws InterruptedException {
        int w = 500;
        int h = 500;
        int[][] Grid = new int[w][h];


        StartGameOfLife(w, h, Grid); //starts the game
        //makeGui();
    }

    static void makeGui() // creates gui
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

    static void StartGameOfLife(int x, int y, int[][] Grid) throws InterruptedException {
        int generation = 0;

        int liveCells = 0;
        JFrame frame = new JFrame("Conway's Game of Life - generation: " + generation + " Live cells: " + liveCells);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(6000, 4000);
        frame.setLayout(new FlowLayout());

        DrawingPanel panel = new DrawingPanel(6000, 4000);
        Graphics g = panel.getGraphics();
        g.setColor(Color.BLACK);
        panel.setVisible(true);
        panel.setGridLines(false);
        panel.addKeyListener(new StartsGame());
        System.out.println(x + " " + y);


        Grid[110][110] = 1;
        Grid[110][109] = 1;
        Grid[109][110] = 1;
        Grid[110][111] = 1;
        Grid[111][111] = 1;

    Boolean Done = false;//
        int size = 12;
    while(!Done) {
            while (DoGame) {
                generation++;
                for (int i = 0; i < Grid.length; i++) {// iterates over the matrix and prints it
                    for (int j = 0; j < Grid.length; j++) {
                        if (Grid[i][j] == 0) {
                            g.setColor(Color.BLACK);// dead cells
                            g.fillRect(i * size, j * size, size, size);
                        } else {
                            g.setColor(Color.WHITE);// living cells
                            g.fillRect(i * size, j * size, size, size);
                        }
                    }
                }// describes logic below.
    /* Creates a function that will check if the cells neighbor is legal to check -
        Ex. if the cell is at 100,0 then checking [i][j-1] would give an index out of bounds error, but if we check legality it would just return 0.
        use two nested for loops to iterate over this array and check each cell int[1][1] ect… -1 represents previous cell,
        1 represents next cell, zero represents current cell, we use this to find how many live neighbors each cell will have.
        iterate over the main matrix and use the data and call the above function to find out how the cell will change.
        We can save the output into an output matrix that we can send to the main matrix when we reprint the main matrix.
    */
                // Iterates over main matrix of live and dead cells
                int[][] OutputM = new int[Grid.length][Grid.length];// creates output matrix so the current changes will not affect this generation, only the next.
                for (int i = 0; i < Grid.length; i++) {
                    for (int j = 0; j < Grid[i].length; j++) {
                        int sum = // sum is the total value of all eight surrounding cells. each cell can either be zero or one, sum output range 0-8.
                                Value(i, j + 1, Grid, Grid.length)
                                        + Value(i, j - 1, Grid, Grid.length)
                                        + Value(i - 1, j, Grid, Grid.length)
                                        + Value(i - 1, j + 1, Grid, Grid.length)
                                        + Value(i - 1, j - 1, Grid, Grid.length)
                                        + Value(i + 1, j + 1, Grid, Grid.length)
                                        + Value(i + 1, j - 1, Grid, Grid.length)
                                        + Value(i + 1, j, Grid, Grid.length);
                        if ((sum == 2 || sum == 3) && Grid[i][j] == 1) { // checks sum to see how the [i][j] cell will change
                            OutputM[i][j] = 1;
                            liveCells++;
                        } else if (sum == 3 && Grid[i][j] == 0) {
                            OutputM[i][j] = 1;
                            liveCells++;
                        } else {
                            OutputM[i][j] = 0;
                        }
                    }
                }
                Grid = OutputM; // sets grid to output
            }
        }
    }

    public void onMouseClick(DrawingPanel.DPMouseEventHandler e, int[][] Grid) {


    }


    public static int Value(int x, int y, int[][] Grid, int w) {
        if (x < 0 || x >= w - 1 || y >= w - 1 || y < 0) { // checks the legality of the cell, if legal, returns cells value,
            return 0;                                    // if not legal (outside the borders of the array) returns 0.
        } else {
            return Grid[x][y];
        }
    }


    public static class StartsGame implements ActionListener, KeyListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //  StartGameOfLife();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
               DoGame = !DoGame;
            }
        }
    }
}
