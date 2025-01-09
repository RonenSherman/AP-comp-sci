import java.awt.*;
import java.util.*;
import java.util.List;

public class MazeGeneration {// Ronen Sherman - maze generator using iterative prims algorithm without recursion

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(1000, 1000);
        Graphics g = panel.getGraphics();
        panel.setVisible(true);
        GenerateMaze(g);
    }


    public static void GenerateMaze(Graphics g) {//
        // dimensions of generated maze
        int rows = 60, columns = 100;

        // create grid of walls
        // W = wall, P = path, S = starting point
        StringBuilder s = new StringBuilder(columns);
        for (int x = 0; x < columns; x++)
            s.append('W');
        char[][] maze = new char[rows][columns];
        for (int x = 0; x < rows; x++) maze[x] = s.toString().toCharArray();

        // select random point and make it start node
        Point starting = new Point((int)(Math.random() * rows), (int)(Math.random() * columns), null);
        maze[starting.x][starting.y] = 'S';
        ComputeFrontierCells(maze, starting, g);


    }

        public static void ComputeFrontierCells ( char[][] maze, Point starting, Graphics g){
            List<Point> FrontierCells = new ArrayList<>();

            // Add initial frontier cells
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                    int nx = starting.x + x;
                    int ny = starting.y + y;
                    if (isInBounds(maze, nx, ny) && maze[nx][ny] == 'W') {
                        FrontierCells.add(new Point(nx, ny, starting));
                    }
                }
            }

            Point last = null;

            // Process the frontier cells
            while (!FrontierCells.isEmpty()) {
                Point current = FrontierCells.remove((int) (Math.random() * FrontierCells.size()));
                Point opposite = current.opposite();

                if (isInBounds(maze, opposite.x, opposite.y) && maze[current.x][current.y] == 'W' && maze[opposite.x][opposite.y] == 'W') {
                    maze[current.x][current.y] = 'P';
                    maze[opposite.x][opposite.y] = 'P';
                    last = opposite;

                    // Add new frontier cells
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                            int nx = opposite.x + x;
                            int ny = opposite.y + y;
                            if (isInBounds(maze, nx, ny) && maze[nx][ny] == 'W') {
                                FrontierCells.add(new Point(nx, ny, opposite));
                            }
                        }
                    }
                }

                // Print the maze at each step
                PrintCells(g, maze);
            }

            // Set the endpoint
            if (last != null) {
                maze[last.x][last.y] = 'E';
                PrintCells(g, maze);
            }

        }


        // Utility function to check if a point is within bounds
        private static boolean isInBounds(char[][] maze, int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }


    public static void PrintCells(Graphics g, char[][] maze) { // iterates over the maze and prints it, same as in game of life
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'W') {
                    g.setColor(Color.BLACK); // Walls
                } else if (maze[i][j] == 'S') {
                    g.setColor(Color.GREEN); // Start
                } else if (maze[i][j] == 'E') {
                    g.setColor(Color.RED); // Endpoint
                } else {
                    g.setColor(Color.WHITE); // Path
                }
                g.fillRect(j * 10, i * 10, 10, 10); // Corrected to (j, i)
            }
        }
    }



    public static class Point { // original point class Eric's is a copy
        Integer  x, y;// x and y of each point
        Point parent;

        public Point(int r, int c, Point p) {
            parent = p;
            this.x = r;
            this.y = c;

        }

        public Point() {

        }

        // compute opposite node given that it is in the other direction from the parent
        public Point opposite() {// used to tunnel and build the maze
            if (this.x.compareTo(parent.x) != 0)
                return new Point(this.x + this.x.compareTo(parent.x), this.y, this);
            if (this.y.compareTo(parent.y) != 0)
                return new Point(this.x, this.y + this.y.compareTo(parent.y), this);
            return null;
        }
    }
}
