import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*public  interface Piece{

    public static int xpos = 0;
    int ypos = 0;

    String name = "";
    char piece = ' ';
}

public static class Pawn implements Piece
{
    xpos = 6;
} */
public class Chess { // Ronen Sherman - chess and chess bot


    public static void main()
    {
        Board.g.setColor(Color.BLACK);
        Board.panel.setVisible(true);
        //Board.panel.setGridLines(true);
        Board.panel.addKeyListener(new TakeInputs());
        Board.panel.addMouseListener(new TakeInputs());
        for (int i = 0; i < Board.Board.length; i++) {// iterates over the board and prints it
            for (int j = 0; j < Board.Board.length; j++) {
                if ((Board.Board[i][j] % 2) == 0) {
                    Board.g.setColor(Color.BLACK);//
                    Board.g.fillRect(i * 100, j * 75, 100, 75);
                } else {
                    Board.g.setColor(Color.WHITE);//
                    Board.g.fillRect(i * 100, j * 75, 100, 75);
                }
            }
        }
    }





    public static class Board
    {
        public static int[][] Board = new int[8][8];
        public static DrawingPanel panel = new DrawingPanel(850, 750);
        public static Graphics g = panel.getGraphics();

        public static void PrintBoard(int[][] Grid, int size, Graphics g)// takes the grid, the size, and the graphics.
        {
            for (int i = 0; i < Grid.length; i++) {// iterates over the matrix and prints it
                for (int j = 0; j < Grid.length; j++) {
                    if (Grid[i][j] == 0) {
                        g.setColor(Color.BLACK);//
                        g.fillRect(i * size, j * size, size, size);
                    } else {
                        g.setColor(Color.WHITE);//
                        g.fillRect(i * size, j * size, size, size);
                    }
                }
            }
        }
    }
    




    public static class TakeInputs extends Chess.Board implements MouseListener, KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) { // if the key pressed is space, it flips DoGame, and prints some numbers.
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if the key pressed is space, it flips DoGame, and prints some numbers.
                System.exit(0);

            }

        }

        @Override
        public void mouseClicked(MouseEvent e) { // this function is a work in progress. will most likely not work, unfortunately.
            int x = e.getX() / 10;
            int y = e.getY() / 10;
            if (Board[x][y] == 0) Board[x][y] = 1;
            else Board[x][y] = 0;
       //     PrintVis(Grid, 10, g);

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
}
