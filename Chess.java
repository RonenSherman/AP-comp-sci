import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.*;


 // Shift + Alt + (+/-) to zoom in/ out

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
                gameBoard[6][col] = new Pawn(true);  // White pawns
                gameBoard[1][col] = new Pawn(false); // Black pawns
            }

            // Place major pieces
            placeMajorPieces(7, true);  // Black pieces on row 0
            placeMajorPieces(0, false); // white pieces on row 7
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

        private boolean isWhiteTurn = true; // true = white's turn, false = black's turn

        private void handleMove(int x, int y) throws InterruptedException {
            // Check if there's a piece at the clicked position
            if (gameBoard[y][x] != null) {
                // If the same piece is clicked again, deselect it
                if (Selected == gameBoard[y][x]) {
                    Selected = null;
                }
                // If no piece is currently selected, select the clicked piece
                else if (Selected == null && gameBoard[y][x].iswhite() == isWhiteTurn) {
                    Selected = gameBoard[y][x];
                    pieceX = x;
                    pieceY = y;
                }
                // If a piece is already selected, check if it's a valid capture
                else if (Selected != null && Selected.isValidMove(pieceX, pieceY, x, y)
                        && gameBoard[y][x].iswhite() != Selected.iswhite()) {
                    gameBoard[y][x] = Selected;
                    gameBoard[pieceY][pieceX] = null;
                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected = null;
                    isWhiteTurn = !isWhiteTurn; // Toggle turn
                }
            }
            // If an empty space is clicked and a piece is selected, attempt to move
            else if (Selected != null) {
                if (Selected.isValidMove(pieceX, pieceY, x, y)) {
                    gameBoard[y][x] = Selected;
                    gameBoard[pieceY][pieceX] = null;
                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected = null;
                    isWhiteTurn = !isWhiteTurn; // Toggle turn
                } else if (CanCastle(pieceX, pieceY, x, y)) {
                    performCastling(pieceX, pieceY, x, y);
                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected = null;
                    isWhiteTurn = !isWhiteTurn; // Toggle turn
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

        // Handles castling movement
        private void performCastling(int kingX, int kingY, int rookX, int rookY) {
            // Determine direction of castling: left or right (based on rook's x position relative to king)
            int step = (rookX > kingX) ? 1 : -1; // Right if rookX > kingX, left if rookX < kingX

            King king = (King) Selected;
            Rook rook = (Rook) gameBoard[rookY][rookX + step];

            // Move the King to its new position (2 squares towards the Rook)
            gameBoard[kingY][kingX + 2 * step] = king; // Place the King next to the Rook
            gameBoard[kingY][kingX] = null; // Remove King from its original position

            // Move the Rook to its new position (right next to the King)
            gameBoard[kingY][kingX + step] = rook; // Place the Rook next to the King
            gameBoard[rookY][rookX + step] = null; // Remove Rook from its original position

            // Mark the King and Rook as having moved
            king.move();
            rook.move();
        }

        //Checks if castling is legal
        private boolean CanCastle(int kingX, int kingY, int rookX, int rookY) {
            int step = (rookX > kingX) ? 1 : -1; // Right if rookX > kingX, left if rookX < kingX
            if (!(Selected instanceof King king)) {
                return false; // Only the King can castle
            }

            if (king.hasMoved()) {
                return false; // King has already moved
            }

            if (!(gameBoard[rookY][rookX + step] instanceof Rook rook)) {
                return false; // Must be a Rook
            }

            if (rook.hasMoved()) {
                return false; // Rook has already moved
            }

            // Check spaces between King and Rook for blocking pieces
            for (int x = kingX + step; x != rookX; x += step) {
                if (gameBoard[kingY][x] != null) {
                    return false; // There's a piece blocking castling
                }
            }

            // Check if King is in check, moves through check, or ends in check
            if (King.isInCheck(kingY, kingX)) { // King is currently in check
                return false;
            }
            if (King.isInCheck(kingY, kingX + step)) { // King would pass through check
                return false;
            }
            if (King.isInCheck(kingY, kingX + 2 * step)) { // King would land in check
                return false;
            }

            return true; // All checks passed, can castle
        }

        private void HandleCheck(boolean selectedColor) {
            boolean whiteKingInCheck = false;
            boolean blackKingInCheck = false;

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = gameBoard[row][col];
                    if (piece != null && "King".equals(piece.getName())) {
                        boolean kingIsWhite = piece.iswhite();
                        if (King.isInCheck(row, col)) {
                            if (kingIsWhite) {
                                whiteKingInCheck = true;
                                JOptionPane.showMessageDialog(null,"White is in check!");
                            } else {
                                blackKingInCheck = true;
                                JOptionPane.showMessageDialog(null,"Black is in check!");

                            }
                        }
                    }
                }
            }

            if (whiteKingInCheck && !hasAnyLegalMoves(true)) {
                System.out.println("White is checkmated! Black wins!");
                // You can add a game-ending function here
            }
            if (blackKingInCheck && !hasAnyLegalMoves(false)) {
                System.out.println("Black is checkmated! White wins!");
                // You can add a game-ending function here
            }
        }

        private boolean hasAnyLegalMoves(boolean isWhite) {
            for (int startRow = 0; startRow < 8; startRow++) {
                for (int startCol = 0; startCol < 8; startCol++) {
                    ChessPiece piece = gameBoard[startRow][startCol];
                    if (piece != null && piece.iswhite() == isWhite) {
                        for (int endRow = 0; endRow < 8; endRow++) {
                            for (int endCol = 0; endCol < 8; endCol++) {
                                if (piece.isValidMove(startRow, startCol, endRow, endCol)) {
                                    // Simulate the move
                                    ChessPiece temp = gameBoard[endRow][endCol];
                                    gameBoard[endRow][endCol] = piece;
                                    gameBoard[startRow][startCol] = null;

                                    boolean kingStillInCheck = false;
                                    for (int r = 0; r < 8; r++) {
                                        for (int c = 0; c < 8; c++) {
                                            ChessPiece p = gameBoard[r][c];
                                            if (p != null && "King".equals(p.getName()) && p.iswhite() == isWhite) {
                                                if (King.isInCheck(r, c)) {
                                                    kingStillInCheck = true;
                                                }
                                            }
                                        }
                                    }

                                    // Undo move
                                    gameBoard[startRow][startCol] = piece;
                                    gameBoard[endRow][endCol] = temp;

                                    if (!kingStillInCheck) {
                                        return true; // Found a move that gets out of check
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false; // No legal moves found
        }





        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX() / 75;
            int y = e.getY() / 75;

            // Ensure the coordinates are within the board boundaries
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                return;  // Ignore clicks outside the board
            }
            try {
                handleMove(x, y);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
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
                    gameBoard[6][col] = new Pawn(true);  // White pawns
                    gameBoard[1][col] = new Pawn(false); // Black pawns
                }

                // Place major pieces
                placeMajorPieces(7, true);  // White pieces on row 7
                placeMajorPieces(0, false); // Black pieces on row 0
                redrawBoard();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
