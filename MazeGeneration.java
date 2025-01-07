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


    public static void GenerateMaze(Graphics g) {//
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
        Point st = new Point();
       st.x = r.nextInt(100) + 1;
        st.y = r.nextInt(100) + 1;
        maze[st.x][st.y ] = 'S';
        List<Point> FrontierCells = new ArrayList<>() {};
        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                try {
                    if (maze[st.x + x][st.y + y] == '.') continue;
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // add eligible points to frontier
                FrontierCells.add(new Point(st.x + x, st.y + y, st));
            }
        PrintCells(g,maze);



    }

    public static void ComputeFrontierCells() {  //finding all the next points


         //   FrontierCells.add




    }

    public static void PrintCells(Graphics g, char[][] maze) {
        for (int i = 0; i < maze.length; i++) {// iterates over the maze and prints it
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] == 'W') {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * 10, j * 10, 10, 10);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(i * 10, j * 10, 10, 10);
                }
            }
        }
    }

      public static class Point {
         int x,y;

        boolean inMaze;

          public Point(int x, int y, Point st) {
          }

          public Point() {

          }
      }
}