import java.awt.*;
import java.util.*;
import java.util.List;

public class MazeGeneration {

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(1000, 1000);
        Graphics g = panel.getGraphics();
        panel.setVisible(true);
        GenerateMaze(g);

    }


    public static void GenerateMaze(Graphics g) {
        // dimensions of generated maze
        int rows = 100, columns = 100;

        // create grid of walls
        // W = wall, P = path, S = starting point
        StringBuilder s = new StringBuilder(columns);
        for (int x = 0; x < columns; x++)
            s.append('W');
        char[][] maze = new char[rows][columns];
        for (int x = 0; x < rows; x++) maze[x] = s.toString().toCharArray();

        // select random point and open as start node
        Random r = new Random();
       Point.x = r.nextInt(100) + 1;
        Point.y = r.nextInt(100) + 1;
        maze[Point.x][Point.y ] = 'S';
        PrintCells(g, maze);
    }

    public static void ComputeFrontierCells() {  //finding all the next points

        List<Point> FrontierCells = new ArrayList<Point>() {};
            FrontierCells.add (Point.x Point.y + 1);

                    (Point.x,  Point.y + 1);


                + (Point.x,  Point.y - 1)
        +(Point.x - 1,  Point.y)
        +(Point.x - 1,  Point.y + 1)
        +(Point.x - 1,  Point.y - 1)
        +(Point.x + 1,  Point.y + 1)
        +(Point.x + 1,  Point.y - 1)
        +(Point.x + 1,  Point.y);
    }

    public static void PrintCells(Graphics g, char[][] maze) {
        for (int i = 0; i < maze.length; i++) {// iterates over the matrix and prints it
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] == 'W') {
                    g.setColor(Color.BLACK);// dead cells
                    g.fillRect(i * 10, j * 10, 10, 10);
                } else {
                    g.setColor(Color.WHITE);// living cells
                    g.fillRect(i * 10, j * 10, 10, 10);
                }
            }
        }
    }

     public static class Point {
        static int x,y;

        boolean inMaze;
    }
}