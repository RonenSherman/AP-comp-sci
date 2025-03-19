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

        public static void BackGroundDraw(int size) {
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

    public static class GameBoard {
        private ChessPiece[][] gameBoard = new ChessPiece[8][8];

        public GameBoard() {
            // Place Pawns
            for (int col = 0; col < 8; col++) {
                gameBoard[1][col] = new Pawn(true);  // White pawns
                gameBoard[6][col] = new Pawn(false); // Black pawns
            }

            // Place major pieces
            placeMajorPieces(gameBoard, 0, true);  // White pieces on row 0
            placeMajorPieces(gameBoard, 7, false); // Black pieces on row 7
            PrintGame();
        }

        private static void placeMajorPieces(ChessPiece[][] board, int row, boolean isWhite) {
            board[row][0] = new Rook(isWhite);
            board[row][7] = new Rook(isWhite);
            board[row][1] = new Knight(isWhite);
            board[row][6] = new Knight(isWhite);
            board[row][2] = new Bishop(isWhite);
            board[row][5] = new Bishop(isWhite);
            board[row][3] = new Queen(isWhite);
            board[row][4] = new King(isWhite);
        }

        public void PrintGame() {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] != null) {
                        char pieceSymbol;
                        switch (gameBoard[i][j].getName()) {
                            case "Pawn" -> pieceSymbol = 'P';
                            case "Rook" -> pieceSymbol = 'R';
                            case "Knight" -> pieceSymbol = 'N';
                            case "Bishop" -> pieceSymbol = 'B';
                            case "Queen" -> pieceSymbol = 'Q';
                            case "King" -> pieceSymbol = 'K';
                            default -> pieceSymbol = '?'; // Should never happen
                        }
                        System.out.print((gameBoard[i][j].iswhite() ? "W-" : "B-") + pieceSymbol + " ");
                    } else {
                        System.out.print("-- "); // Empty space
                    }
                }
                System.out.println();
            }
        }
   }



    class TakeInputs extends GameBoard implements MouseListener {

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
