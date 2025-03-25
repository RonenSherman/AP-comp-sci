import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;


public class Chess { // Ronen Sherman - chess and chess bot
    public static DrawingPanel panel = new DrawingPanel(600, 650);
    public static Graphics g = panel.getGraphics();

    public void main() {
        panel.setVisible(true);
        panel.addMouseListener(new GameBoard());
        panel.addKeyListener(new GameBoard());

        BackgroundBoard.BackGroundDraw(75);
        GameBoard.PrintGame();
    }


    public static class BackgroundBoard { // background board for color

        public static int[][] BackgroundBoard = new int[8][8];

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
                        g.setColor(Color.LIGHT_GRAY);//
                        g.fillRect(i * size, j * size, size, size);
                    } else {
                        g.setColor(Color.DARK_GRAY);//
                        g.fillRect(i * size, j * size, size, size);
                    }
                }
            }
        }
    }

    public static class GameBoard extends Chess.BackgroundBoard implements MouseListener, KeyListener {
        static final ChessPiece[][] gameBoard = new ChessPiece[8][8];
        ChessPiece Selected;
        int pieceX;
        int pieceY;

        public GameBoard() {
            // Place Pawns
            for (int col = 0; col < 8; col++) {
                gameBoard[1][col] = new Pawn(true);  // White pawns
                gameBoard[6][col] = new Pawn(false); // Black pawns
            }

            // Place major pieces
            placeMajorPieces(0, true);  // White pieces on row 0
            placeMajorPieces(7, false); // Black pieces on row 7
        }

        private static void placeMajorPieces(int row, boolean isWhite) {
            GameBoard.gameBoard[row][0] = new Rook(isWhite);
            GameBoard.gameBoard[row][7] = new Rook(isWhite);
            GameBoard.gameBoard[row][1] = new Knight(isWhite);
            GameBoard.gameBoard[row][6] = new Knight(isWhite);
            GameBoard.gameBoard[row][2] = new Bishop(isWhite);
            GameBoard.gameBoard[row][5] = new Bishop(isWhite);
            GameBoard.gameBoard[row][3] = new Queen(isWhite);
            GameBoard.gameBoard[row][4] = new King(isWhite);
        }

        public static void PrintGame() {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {

                    if (gameBoard[i][j] != null) {
                        char pieceSymbol;
                        switch (gameBoard[i][j].getName()) {
                            case "Pawn" -> pieceSymbol = '♟';
                            case "Rook" -> pieceSymbol = '♜';
                            case "Knight" -> pieceSymbol = '♞';
                            case "Bishop" -> pieceSymbol = '♝';
                            case "Queen" -> pieceSymbol = '♛';
                            case "King" -> pieceSymbol = '♚';
                            default -> pieceSymbol = '?'; // Should never happen
                        }
                        if (gameBoard[i][j].iswhite()) {
                            g.setColor(Color.WHITE);
                        } else {
                            g.setColor(Color.BLACK);
                        }

                        g.setFont(new Font("piece", Font.PLAIN, 65));
                        g.drawString(String.valueOf(pieceSymbol), j * 75, (i + 1) * 75);
                    }
                }
            }
        }

        private void handleMove(int x, int y) {
            boolean turn = true;
            // Check if there's a piece at the clicked position
            if(turn) {
                if (gameBoard[y][x] != null) {
                    // If the same piece is clicked again, deselect it
                    if (Selected == gameBoard[y][x]) {
                        Selected = null;
                    }
                    // If no piece is currently selected, select the clicked piece
                    else if (Selected == null) {
                        Selected = gameBoard[y][x];
                        pieceX = x;
                        pieceY = y;
                    }
                    // If a piece is already selected, check if it's a valid capture
                    else if (Selected.isValidMove(pieceX, pieceY, x, y) && gameBoard[y][x].iswhite() != Selected.iswhite()) {
                        // Capture the piece (replace the enemy piece with the selected piece)
                        gameBoard[y][x] = Selected;
                        gameBoard[pieceY][pieceX] = null;
                        Selected = null;

                        // Redraw the board after capturing
                        panel.clear(); // Custom method to clear the board
                        Chess.BackgroundBoard.BackGroundDraw(75);
                        PrintGame();
                    }
                }
                // If an empty space is clicked and a piece is selected, attempt to move
                else if (Selected != null) {
                    if (Selected.isValidMove(pieceX, pieceY, x, y)) {
                        // Move the selected piece
                        gameBoard[y][x] = Selected;
                        gameBoard[pieceY][pieceX] = null;
                        Selected = null;

                        // Redraw the board after moving
                        panel.clear(); // Custom method to clear the board
                        Chess.BackgroundBoard.BackGroundDraw(75);
                        PrintGame();
                    } else {
                        Selected = null; // Reset selection if the move is invalid
                    }
                }
                turn = !turn;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX() / 75;
            int y = e.getY() / 75;

            // Ensure the coordinates are within the board boundaries
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                return;  // Ignore clicks outside the board
            }
            handleMove(x, y);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_R) {

                for (ChessPiece[] chessPieces : gameBoard) {
                    Arrays.fill(chessPieces, null);
                }

                for (int col = 0; col < 8; col++) {
                    gameBoard[1][col] = new Pawn(true);  // White pawns
                    gameBoard[6][col] = new Pawn(false); // Black pawns
                }

                // Place major pieces
                placeMajorPieces(0, true);  // White pieces on row 0
                placeMajorPieces(7, false); // Black pieces on row 7
                panel.clear(); // Custom method to clear the board
                Chess.BackgroundBoard.BackGroundDraw(75);
                PrintGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
