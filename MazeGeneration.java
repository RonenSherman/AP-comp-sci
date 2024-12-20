import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MazeGeneration
{
    public static void main(String[] args) {
         DrawingPanel panel = new DrawingPanel(3000, 2000);
         Graphics g = panel.getGraphics();

        panel.setVisible(true);
        panel.addKeyListener(new GameOfLife.TakeInputs());
        panel.addMouseListener(new GameOfLife.TakeInputs());

    }




    public static class TakeInputs implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }




}
/*
import java.util.*;

public class Prim {

 public static void main(String[] args) {
  // dimensions of generated maze
  int r = 10, c = 10;

  // build maze and initialize with only walls
  StringBuilder s = new StringBuilder(c);
  for (int x = 0; x < c; x++)
   s.append('*');
  char[][] maz = new char[r][c];
  for (int x = 0; x < r; x++) maz[x] = s.toString().toCharArray();

  // select random point and open as start node
  Point st = new Point((int)(Math.random() * r), (int)(Math.random() * c), null);
  maz[st.r][st.c] = 'S';

  // iterate through direct neighbors of node
  ArrayList < Point > frontier = new ArrayList < Point > ();
  for (int x = -1; x <= 1; x++)
   for (int y = -1; y <= 1; y++) {
    if (x == 0 && y == 0 || x != 0 && y != 0)
     continue;
    try {
     if (maz[st.r + x][st.c + y] == '.') continue;
    } catch (Exception e) { // ignore ArrayIndexOutOfBounds
     continue;
    }
    // add eligible points to frontier
    frontier.add(new Point(st.r + x, st.c + y, st));
   }

  Point last = null;
  while (!frontier.isEmpty()) {

   // pick current node at random
   Point cu = frontier.remove((int)(Math.random() * frontier.size()));
   Point op = cu.opposite();
   try {
    // if both node and its opposite are walls
    if (maz[cu.r][cu.c] == '*') {
     if (maz[op.r][op.c] == '*') {

      // open path between the nodes
      maz[cu.r][cu.c] = '.';
      maz[op.r][op.c] = '.';

      // store last node in order to mark it later
      last = op;

      // iterate through direct neighbors of node, same as earlier
      for (int x = -1; x <= 1; x++)
       for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0 || x != 0 && y != 0)
         continue;
        try {
         if (maz[op.r + x][op.c + y] == '.') continue;
        } catch (Exception e) {
         continue;
        }
        frontier.add(new Point(op.r + x, op.c + y, op));
       }
     }
    }
   } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
   }

   // if algorithm has resolved, mark end node
   if (frontier.isEmpty())
    maz[last.r][last.c] = 'E';
  }

  // print final maze
  for (int i = 0; i < r; i++) {
   for (int j = 0; j < c; j++)
    System.out.print(maz[i][j]);
   System.out.println();
  }
 }

 static class Point {
  Integer r;
  Integer c;
  Point parent;
  public Point(int x, int y, Point p) {
    r = x;
    c = y;
    parent = p;
   }
   // compute opposite node given that it is in the other direction from the parent
  public Point opposite() {
   if (this.r.compareTo(parent.r) != 0)
    return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
   if (this.c.compareTo(parent.c) != 0)
    return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
   return null;
  }
 }
}
 */