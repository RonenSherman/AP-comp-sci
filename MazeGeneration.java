import java.awt.*;
import java.util.*;

public class MazeGeneration {

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(3000, 2000);
        Graphics g = panel.getGraphics();
        panel.setVisible(true);
            GenerateMaze(g);

    }


    public static void GenerateMaze( Graphics g) {
        // dimensions of generated maze
        int rows = 50, columns = 50;

        // create grid of walls
        // W = wall, P = path, S = starting point
        StringBuilder s = new StringBuilder(columns);
        for (int x = 0; x < columns; x++)
            s.append('W');
        char[][] maze = new char[rows][columns];
        for (int x = 0; x < rows; x++) maze[x] = s.toString().toCharArray();

        // select random point and open as start node
        Random r = new Random();
        int startrows = r.nextInt(100) + 1;
        int startcolumns = r.nextInt(100) + 1;
        maze[startrows][startcolumns] = 'S';

    }

    public void ComputeFrontierCells()
    {

    }
}
