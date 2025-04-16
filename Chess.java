import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Objects;


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
            // Check if there's a piece at the clicked position
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
                    redrawBoard();
                }
            }
            // If an empty space is clicked and a piece is selected, attempt to move
            else if (Selected != null) {
                // Check for valid move or castling
                if (Selected.isValidMove(pieceX, pieceY, x, y)) {
                    // Regular move
                    gameBoard[y][x] = Selected;
                    gameBoard[pieceY][pieceX] = null;

                    if (Selected instanceof King) {
                        ((King) Selected).move(); // Mark King as having moved
                    }

                    Selected = null; // Deselect after move
                    redrawBoard();
                } else if (CanCastle(pieceX, pieceY, x, y)) {
                    // If castling is possible, perform castling
                    performCastling(pieceX, pieceY, x, y);
                    redrawBoard();
                } else {
                    Selected = null; // Reset selection if the move is invalid
                }
            }
        }

        // Helper method to redraw the board
        private void redrawBoard() {
            panel.clear();
            Chess.BackgroundBoard.BackGroundDraw(75);
            PrintGame();
        }

        private void performCastling(int kingX, int kingY, int rookX, int rookY) {
            // Determine direction of castling: left or right (based on rook's x position relative to king)
            int step = (rookX > kingX) ? 1 : -1; // Right if rookX > kingX, left if rookX < kingX

            King king = (King) Selected;
            Rook rook = (Rook) gameBoard[rookY][rookX];

            // Move the King to its new position (2 squares towards the Rook)
            gameBoard[kingY][kingX + 2 * step] = king; // Place the King next to the Rook
            gameBoard[kingY][kingX] = null; // Remove King from its original position

            // Move the Rook to its new position (right next to the King)
            gameBoard[kingY][kingX + step] = rook; // Place the Rook next to the King
            gameBoard[rookY][rookX] = null; // Remove Rook from its original position

            // Mark the King and Rook as having moved
            king.move();
            rook.move();
        }

        private boolean CanCastle(int kingX, int kingY, int rookX, int rookY) {
            if (!(Selected instanceof King)) {
                return false; // Only the King can castle
            }

            King king = (King) Selected;
            if (king.hasMoved()) {
                return false; // King has already moved
            }

            if (!(gameBoard[rookY][rookX] instanceof Rook)) {
                return false; // The piece must be a Rook
            }

            Rook rook = (Rook) gameBoard[rookY][rookX];
            if (rook.hasMoved()) {
                return false; // Rook has already moved
            }

            // Ensure there are no pieces between the King and the Rook
            int step = (rookX > kingX) ? 1 : -1; // Determine direction of castling
            for (int x = kingX + step; x != rookX; x += step) {
                if (gameBoard[kingY][x] != null) {
                    return false; // There's a piece blocking castling
                }
            }

            // Ensure King does not pass through check
            if (King.isInCheck(kingX, kingY) || King.isInCheck(kingX + step, kingY) || King.isInCheck(kingX + 2 * step, kingY)) {
                return false;
            }

            // Perform castling
            gameBoard[kingY][kingX + 2 * step] = king; // Move King two squares
            gameBoard[kingY][rookX] = null; // Remove Rook from its original place
            gameBoard[kingY][kingX + step] = rook; // Move Rook next to King
            gameBoard[kingY][kingX] = null; // Remove King from its original position

            king.move(); // Set the King as having moved
            rook.move(); // Mark Rook as moved

            return true; // Castling successful
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
