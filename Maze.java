import java.awt.*;
import java.util.ArrayList;

public class Maze {
    //                                                              Ronen Sherman Maze Generation

    public Maze() {

        Cells = new int[r][c];
    }
  int r = 10, c = 10;
    //Point st = new Point((int)(Math.random() * r), (int)(Math.random() * c), null);
    int[][] Cells;
    int StartingX;
    int StartingY;
    //  Grid of integers.
    // 0 represents walls/blocked cells. 1 represents passageways/ empty cells
    /*
    A Grid consists of a 2 dimensional array of cells.
    A Cell has 2 states: Blocked or Passage.
    Start with a Grid full of Cells in state Blocked.
    Pick a random Cell, set it to state Passage and Compute its frontier cells. A frontier cell of a Cell is a cell with distance 2 in state Blocked and within the grid.
    While the list of frontier cells is not empty:
    Pick a random frontier cell from the list of frontier cells.
    Let neighbors(frontierCell) = All cells in distance 2 in state Passage.
    Pick a random neighbor and connect the frontier cell with the neighbor by setting the cell in-between to state Passage.
    Compute the frontier cells of the chosen frontier cell and add them to the frontier list. Remove the chosen frontier cell from the list of frontier cells.
     */

    void GenerateMaze() {
        StartingX = 1;
        StartingY = 1;
        Cells[StartingX][StartingY] = 1;
        FrontierCells(r,c);
    }

    void FrontierCells(int r, int c) {
        ArrayList<Point> frontier = new ArrayList < Point > ();
        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                try {
                    if (Cells[StartingX + x][StartingY + y] == '.') continue;
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // add eligible points to frontier
                frontier.add(new Point(st.r + x, st.c + y, st));

            }
    }
}

