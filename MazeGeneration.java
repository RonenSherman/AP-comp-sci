import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeGeneration{

public static void main(String[] args) throws InterruptedException {
        // Create a drawing panel for visualization
        DrawingPanel panel = new DrawingPanel(1000, 600);
        Graphics g = panel.getGraphics();
        panel.setVisible(true);

        // Generate and render the maze
        GenerateMaze(g);
        }

public static void GenerateMaze(Graphics g) throws InterruptedException {
        // Define maze dimensions (rows and columns)
        int rows = 60, columns = 100;

        // Create an initial grid filled with walls
        // 'W' represents walls, 'P' represents paths, 'S' is the start, and 'E' is the endpoint
        StringBuilder s = new StringBuilder(columns);
        s.append("W".repeat(columns)); // Fill each row with 'W'
        char[][] maze = new char[rows][columns];
        for (int x = 0; x < rows; x++) maze[x] = s.toString().toCharArray();

        // Randomly select a starting point and mark it with 'S'
        Point starting = new Point((int) (Math.random() * rows), (int) (Math.random() * columns), null);
        maze[starting.x][starting.y] = 'S';

        // Begin generating the maze starting from the chosen point
        ComputeFrontierCells(maze, starting, g);
        MazeSolver.solveMaze(maze,g);
        maze[starting.x][starting.y] = 'S';
        PrintCells(g,maze);

        }

public static void ComputeFrontierCells(char[][] maze, Point starting, Graphics g) {
        // List to track frontier cells (potential cells to carve paths to)
        List<Point> FrontierCells = new ArrayList<>();

        // Add initial frontier cells around the starting point
        for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
        // Skip the current cell and diagonal neighbors
        if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;

        // Calculate neighbor coordinates
        int nx = starting.x + x;
        int ny = starting.y + y;

        // Add neighbors that are within bounds and are walls
        if (isInBounds(maze, nx, ny) && maze[nx][ny] == 'W') {
        FrontierCells.add(new Point(nx, ny, starting));
        }
        }
        }

        Point last = null; // Keep track of the last processed cell

        // Process all frontier cells
        while (!FrontierCells.isEmpty()) {
        // Randomly select a frontier cell
        Point current = FrontierCells.remove((int) (Math.random() * FrontierCells.size()));

        // Determine the opposite cell in the direction from the parent
        Point opposite = current.opposite();

        // Check if the opposite cell is valid and carve the path
        if (isInBounds(maze, opposite.x, opposite.y) && maze[current.x][current.y] == 'W' && maze[opposite.x][opposite.y] == 'W') {
        // Mark the current and opposite cells as part of the path
        maze[current.x][current.y] = 'P';
        maze[opposite.x][opposite.y] = 'P';

        // Update the last processed cell
        last = opposite;

        // Add new frontier cells around the opposite cell
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
        // Render the maze after processing each step
        PrintCells(g, maze);
        }

        // Set the endpoint as the last processed cell, if available
        if (last != null) {
        maze[last.x][last.y] = 'E'; // Mark the endpoint
        PrintCells(g, maze); // Render the maze with the endpoint
        }
        }

// Utility function to check if a given cell is within the maze bounds
private static boolean isInBounds(char[][] maze, int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
        }

// Function to render the maze on the panel
public static void PrintCells(Graphics g, char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
        for (int j = 0; j < maze[i].length; j++) {
        // Set the color based on the cell type
        if (maze[i][j] == 'W') {
        g.setColor(Color.BLACK); // Walls
        } else if (maze[i][j] == 'S') {
        g.setColor(Color.GREEN); // Start point

        } else if (maze[i][j] == 'R') {
        g.setColor(Color.YELLOW); // final solved route
        } else if (maze[i][j] == 'V') {
        g.setColor(Color.BLUE); // Temp route for vis display

        } else if (maze[i][j] == 'E') {
        g.setColor(Color.RED); // Endpoint
        } else {
        g.setColor(Color.WHITE); // Path
        }
        // Draw the cell as a rectangle
        g.fillRect(j * 10, i * 10, 10, 10);
        // (j, i) ensures correct location in reference with DrawingPanel (the graphic display)
        }
        }
        }
        }
// Class representing a point in the maze
 class Point {
    Integer x, y; // Coordinates of the point
    Point parent; // Reference to the parent point (used to calculate opposite points)

    public Point(int r, int c, Point p) {
        parent = p; // Set the parent point
        this.x = r; // Row
        this.y = c; // Column
    }

    // Compute the opposite cell relative to the parent
    public Point opposite() {
        if (this.x.compareTo(parent.x) != 0) {
            return new Point(this.x + this.x.compareTo(parent.x), this.y, this);
        }
        if (this.y.compareTo(parent.y) != 0) {
            return new Point(this.x, this.y + this.y.compareTo(parent.y), this);
        }
        return null; // Return null if there is no valid opposite
    }
}


 class MazeSolver {
    //Uses recursive depth first search
    //DFS is where the algorithm goes as deep as possible before hitting a dead end, hence the name "Depth" first.
    //This is the best for solving mazes since it follows each branch of the maze all the way.
    //DFS is not good for shortest path however (which would be Dijkstra's, A-star, or breadth first search)
    //We are searching for the only path rather than the shortest path so DFS is the best algorithm choice now.
    //Otherwise known as recursive backtracking.

    // Directions for moving up, down, left, right
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // Up
            {1, 0},  // Down
            {0, -1}, // Left
            {0, 1}   // Right
    };

    public static boolean dfs(char[][] maze, int x, int y, Graphics g) throws InterruptedException {
        // Check if out of bounds or hitting a wall
        if (x < 0 || x >= maze.length || y < 0 || y >= maze[0].length || maze[x][y] == 'W') {
            return false;
        }

        // Check if it's the end
        if (maze[x][y] == 'E') {
            return true;
        }

        // Check if already visited (to prevent cycles)
        if (maze[x][y] == 'R' || maze[x][y] == 'V') {
            return false;
        }

        // Mark the current cell as visited
        maze[x][y] = 'V'; // 'v' stands for already visited
      Thread.sleep(7);
        MazeGeneration.PrintCells(g,maze); // Printing the process of generating the solved route.
        // Explore all possible directions
        for (int[] dir : DIRECTIONS) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (dfs(maze, newX, newY, g)) {
                maze[x][y] = 'R'; // Mark the route
                return true;
            }
        }

        // Backtrack if no path is found from here
        maze[x][y] = 'P'; // Restore to open path
        return false;
    }

    public static void solveMaze(char[][] maze, Graphics g) throws InterruptedException {
        // Find the start point
        int startX = -1, startY = -1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'S') {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }

        if (startX == -1) {
            System.out.println("Start point 's' not found in the maze");
            return;
        }

        // Perform DFS to solve the maze
        if (dfs(maze, startX, startY,g)) {
            System.out.println("Path found");
        } else {
            System.out.println("No existing path");
        }
    }
}
