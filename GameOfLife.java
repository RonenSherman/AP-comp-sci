import java.awt.*;
import java.awt.event.*;


public class GameOfLife {// Ronen Sherman - Conway's Game of Life using  java swing
    // Had to use global variables to pass them to eventListeners without too much difficulty
    public static boolean DoGame = false;
    public static int generation = 0;
    public static int liveCells = 0;
    public static int[][] Grid = new int[500][500];
    public static DrawingPanel panel = new DrawingPanel(3000, 2000);
    public static Graphics g = panel.getGraphics();
    public static void main(String[] args) throws InterruptedException {
        StartGameOfLife(Grid); //starts the game
    }

    static void StartGameOfLife(int[][] Grid) throws InterruptedException {
        g.setColor(Color.BLACK);
        panel.setVisible(true);
        panel.setGridLines(false);
        panel.addKeyListener(new TakeInputs());
        panel.addMouseListener(new TakeInputs());

        Grid[110][110] = 1;
        Grid[110][109] = 1;
        Grid[109][110] = 1;
        Grid[110][111] = 1;
        Grid[111][111] = 1;


        int size = 12;
        PrintVis(Grid, size, g);// --> initial print to start the game.
        final boolean isEricPaceLate = true;
        while (isEricPaceLate) {
            Thread.sleep(50);
            if (DoGame) {
                liveCells = 0;// helps keep track of living cells during the game, outputted during pause using system.out.PrintLn
                generation++;
                PrintVis(Grid, size, g); // main printing call that is called every time to reprint the game.
                // describes logic below.
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
                        } else if (sum == 3 && Grid[i][j] == 0) {
                            OutputM[i][j] = 1;
                        } else {
                            OutputM[i][j] = 0;
                        }
                    }
                }

                Grid = OutputM; // sets grid to output
            }
        }
    }
    public static void PrintVis(int[][] Grid, int size, Graphics g)// takes the grid, the size, and the graphics.
    {
        for (int i = 0; i < Grid.length; i++) {// iterates over the matrix and prints it
            for (int j = 0; j < Grid.length; j++) {
                if (Grid[i][j] == 0) {
                    g.setColor(Color.BLACK);// dead cells
                    g.fillRect(i * size, j * size, size, size);
                } else {
                    liveCells++;
                    g.setColor(Color.WHITE);// living cells
                    g.fillRect(i * size, j * size, size, size);
                }
            }
        }
    }

    public static int Value(int x, int y, int[][] Grid, int w) {
        if (x < 0 || x >= w - 1 || y >= w - 1 || y < 0) { // checks the legality of the cell, if legal, returns cells value,
            return 0;                                    // if not legal (outside the borders of the array) returns 0.
        } else {
            return Grid[x][y];
        }
    }

    public static class TakeInputs implements MouseListener, KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {

        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) { // if the key pressed is space, it flips DoGame, and prints some numbers.
               DoGame = !DoGame;
                if(!DoGame)
                    System.out.println("Conway's Game of Life - generation: " + generation + " Live cells: " + liveCells);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) { // this function is a work in progress. will most likely not work, unfortunately.
            int x = e.getX()/12;
            int y = e.getY()/12;
            if(Grid[x][y] == 0) Grid[x][y] = 1;
            else Grid[x][y] = 0;
            PrintVis(Grid,12, g);

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
