public static class MazeSolver {
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
            Thread.sleep(15);
            PrintCells(g,maze); // Printing the process of generating the solved route.
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
