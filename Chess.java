import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Chess { // Ronen Sherman - chess and chess bot

    public void main() {

    BackgroundBoard backgroundBoard = new BackgroundBoard();

        BackgroundBoard.panel.setVisible(true);
        BackgroundBoard.panel.addMouseListener(new TakeInputs());


        BackgroundBoard.BackGroundDraw(75);
    }//


    public static class BackgroundBoard { // background board for color

        public static int[][] BackgroundBoard = new int[8][8];
        public static DrawingPanel panel = new DrawingPanel(600, 650);
        public static Graphics g = panel.getGraphics();

        public BackgroundBoard() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    BackgroundBoard[row][col] = (row + col) % 2;  // Alternates between 0 and 1
                }
            }
        }

        public static void BackGroundDraw( int size)
        {
            for (int i = 0; i < BackgroundBoard.length; i++) {// iterates over the board and prints it
                for (int j = 0; j < BackgroundBoard.length; j++) {
                    if (BackgroundBoard[i][j] == 0) {
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

    public static class GameBoard
    {
        public GameBoard()
        {
            ChessPiece[][] gameBoard = new ChessPiece[8][8];

            // Place Pawns
            for (int col = 0; col < 8; col++) {
                gameBoard[1][col] = new Pawn("White");
                gameBoard[6][col] = new Pawn("Black");
            }

            // Place major pieces
            placeMajorPieces(gameBoard, 0, "White"); // White pieces on row 0
            placeMajorPieces(gameBoard, 7, "Black"); // Black pieces on row 7
        }

        private static void placeMajorPieces(ChessPiece[][] board, int row, String color) {
            board[row][0] = new Rook(color);
            board[row][7] = new Rook(color);
            board[row][1] = new Knight(color);
            board[row][6] = new Knight(color);
            board[row][2] = new Bishop(color);
            board[row][5] = new Bishop(color);
            board[row][3] = new Queen(color);
            board[row][4] = new King(color);
        }

    }


    class TakeInputs extends Chess.GameBoard implements MouseListener {

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



