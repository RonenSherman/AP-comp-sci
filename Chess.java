import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Chess { // Ronen Sherman - chess and chess bot

    public void main() {

        Board.panel.setVisible(true);
        Board.panel.addKeyListener(new TakeInputs());
        Board.panel.addMouseListener(new TakeInputs());


        Board.PrintBoard(75);
    }


    public static class Board { // Need to make code of ints for pieces


        public static int[][] board = new int[8][8];
        public static DrawingPanel panel = new DrawingPanel(600, 650);
        public static Graphics g = panel.getGraphics();

        public Board() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    board[row][col] = (row + col) % 2;  // Alternates between 0 and 1
                }
            }

        }
        boolean isOccupied(int x, int y) {
            return board[x][y] != 1; // If the square is not null, it's occupied
        }

        public static void PrintBoard( int size)
        {
            for (int i = 0; i < board.length; i++) {// iterates over the board and prints it
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == 0) {
                        g.setColor(Color.WHITE);//
                        g.fillRect(i * size, j * size, size, size);
                    } else {
                        g.setColor(Color.BLACK);//
                        g.fillRect(i * size, j * size, size, size);
                    }
                }
            }
        }
    }


    class TakeInputs extends Chess.Board implements MouseListener, KeyListener {
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
