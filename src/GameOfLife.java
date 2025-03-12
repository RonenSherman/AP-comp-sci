import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


// Had to use global variables to pass them to eventListeners without too much difficulty
public static boolean DoGame = false;
public static int[][] Grid = new int[250][250];
public static DrawingPanel panel = new DrawingPanel(3000, 2000);
public static Graphics g = panel.getGraphics();

public static void main(String[] args) throws InterruptedException {
    StartGameOfLife(Grid); //starts the game
}


static void StartGameOfLife(int[][] Grid) throws InterruptedException {
    g.setColor(Color.BLACK);
    panel.setVisible(true);
    panel.setGridLines(true);
    panel.addKeyListener(new TakeInputs());
    panel.addMouseListener(new TakeInputs());


  //  GosperGliderGun(Grid);


    int size = 10;
    PrintVis(Grid, size, g);// --> initial print to start the game.
    final boolean isEricPaceLate = true;
    while (isEricPaceLate) {
        Thread.sleep(50);
        if (DoGame) {

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
                g.setColor(Color.WHITE);// living cells
                g.fillRect(i * size, j * size, size, size);
            }
        }
    }
}

public static void GosperGliderGun(int[][] Grid)// set coordinates to create a gospers glider gun
{
    Grid[101][100] = 1;
    Grid[101][101] = 1;
    Grid[102][100] = 1;
    Grid[102][101] = 1;
    Grid[111][100] = 1;
    Grid[111][101] = 1;
    Grid[111][102] = 1;
    Grid[112][99] = 1;
    Grid[113][98] = 1;
    Grid[114][98] = 1;
    Grid[112][103] = 1;
    Grid[113][104] = 1;
    Grid[114][104] = 1;

    Grid[115][101] = 1;
    Grid[117][101] = 1;
    Grid[117][102] = 1;
    Grid[117][100] = 1;
    Grid[116][99] = 1;
    Grid[116][103] = 1;
    Grid[118][101] = 1;
    Grid[121][100] = 1;
    Grid[122][100] = 1;
    Grid[122][99] = 1;
    Grid[121][99] = 1;
    Grid[121][98] = 1;
    Grid[122][98] = 1;
    Grid[123][97] = 1;
    Grid[123][101] = 1;
    Grid[125][101] = 1;
    Grid[125][102] = 1;
    Grid[125][97] = 1;
    Grid[125][96] = 1;
    Grid[135][98] = 1;
    Grid[135][99] = 1;
    Grid[136][99] = 1;
    Grid[136][98] = 1;
}

public static void SpaceShipSpaceShipSpaceShip(int[][] Grid) {
    Grid[100][100] = 1; // need to switch everything around
    Grid[103][100] = 1;
    Grid[104][101] = 1;
    Grid[104][102] = 1;
    Grid[104][103] = 1;
    Grid[104][104] = 1;
    Grid[104][105] = 1;
    Grid[104][106] = 1;
    Grid[103][106] = 1;
    Grid[102][106] = 1;
    Grid[101][105] = 1;
    Grid[99][102] = 1;
    Grid[99][103] = 1;

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
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if the key pressed is space, it flips DoGame, and prints some numbers.
            System.exit(0);

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) { // this function is a work in progress. will most likely not work, unfortunately.
        int x = e.getX() / 10;
        int y = e.getY() / 10;
        if (Grid[x][y] == 0) Grid[x][y] = 1;
        else Grid[x][y] = 0;
        PrintVis(Grid, 10, g);

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