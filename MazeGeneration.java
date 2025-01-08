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

        // select random point and make it start node
        Random r = new Random();
        Point starting = new Point();
        starting.x = r.nextInt(100) + 1;
        starting.y = r.nextInt(100) + 1;
        maze[starting.x][starting.y] = 'S';
        ComputeFrontierCells(maze, starting);


         PrintCells(g,maze);
    }

    public static List<Point> ComputeFrontierCells(char[][] maze, Point starting) {  //finding all the next points
        List<Point> FrontierCells = new ArrayList<>() {
        };
        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                try {
                    if (maze[starting.x + x][starting.y + y] != 'W') continue;
                } catch (Exception e) { // make sure it is in bounds
                    continue;
                }
                // add legal points to frontier of next points
                FrontierCells.add(new Point(starting.x + x, starting.y + y, starting));
            }
        return FrontierCells;
    }


    public void IDontKnow( List<Point> FrontierCells,char[][] maze){
          Point last = null;
        while (!FrontierCells.isEmpty()) {

            // pick current node at random
            Point current = FrontierCells.remove((int) (Math.random() * FrontierCells.size()));
            Point opposite = current.opposite(current);
            try {
                if (maze[current.x][current.y] == 'W') {
                    if (maze[opposite.x][opposite.y] == 'W') {


                        maze[current.x][current.y] = 'P';
                        maze[opposite.x][opposite.y] = 'P';


                        last = opposite;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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

    public static class Point { // original point class Eric's is a copy
        int x, y;

        public Point(int x, int y, Point starting) {
        }

        public Point() {

        }

        public Point opposite( Point current) {
         //   Point opposite = current.x
            return null;
        }
    }
}



