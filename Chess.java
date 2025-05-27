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
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.BLACK); // Or white depending on background
            String turnText = isWhiteTurn ? "White's Turn" : "Black's Turn";
            g.drawString(turnText, 20, 30); // X, Y position at top-left
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

        public boolean isWhiteTurn = true; // true = white's turn, false = black's turn

        public static int[] enPassantTargetSquare = null;

        private boolean leavesKingInCheck(int fromX, int fromY, int toX, int toY) {
            ChessPiece movingPiece = gameBoard[fromY][fromX];
            ChessPiece targetPiece = gameBoard[toY][toX];

            gameBoard[toY][toX] = movingPiece;
            gameBoard[fromY][fromX] = null;

            boolean inCheck = false;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = gameBoard[row][col];
                    if (piece != null && piece.getName().equals("King") && piece.iswhite() == movingPiece.iswhite()) {
                        if (King.isInCheck(row, col)) {
                            inCheck = true;
                            break;
                        }
                    }
                }
            }

            // Undo move
            gameBoard[fromY][fromX] = movingPiece;
            gameBoard[toY][toX] = targetPiece;

            return !inCheck;
        }


        private void handleMove(int x, int y) throws InterruptedException {
            // Clicked on a piece
            if (gameBoard[y][x] != null) {
                if (Selected == gameBoard[y][x]) {
                    Selected = null;
                } else if (Selected == null && gameBoard[y][x].iswhite() == isWhiteTurn) {
                    Selected = gameBoard[y][x];
                    pieceX   = x;
                    pieceY   = y;
                } else if (Selected != null
                        && Selected.isValidMove(pieceX, pieceY, x, y)
                        && gameBoard[y][x].iswhite() != Selected.iswhite()
                        && leavesKingInCheck(pieceX, pieceY, x, y)) {

                    // en passant first
                    if (handleEnPassant(pieceX, pieceY, x, y)) {
                        return;
                    }

                    // normal capture
                    gameBoard[y][x]         = Selected;
                    gameBoard[pieceY][pieceX] = null;

                    // promotion
                    if (Selected instanceof Pawn && (y == 0 || y == 7)) {
                        gameBoard[y][x] = promotePawn(Selected.iswhite());
                    }

                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected     = null;
                    isWhiteTurn = !isWhiteTurn;
                    repaintTurnLabel(g);

                    enPassantTargetSquare = null;
                }
            }
            // Moving to empty square
            else if (Selected != null) {
                // en passant first
                if (handleEnPassant(pieceX, pieceY, x, y)) {
                    return;
                }

                // standard move (guarded against leaving king in check)
                if (Selected.isValidMove(pieceX, pieceY, x, y)
                        && leavesKingInCheck(pieceX, pieceY, x, y)) {

                    gameBoard[y][x]         = Selected;
                    gameBoard[pieceY][pieceX] = null;

                    // set en passant target
                    if (Selected instanceof Pawn && Math.abs(y - pieceY) == 2) {
                        int enRow = (y + pieceY) / 2;
                        enPassantTargetSquare = new int[] { enRow, x };
                    } else {
                        enPassantTargetSquare = null;
                    }

                    // promotion
                    if (Selected instanceof Pawn && (y == 0 || y == 7)) {
                        gameBoard[y][x] = promotePawn(Selected.iswhite());
                    }

                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected     = null;
                    isWhiteTurn = !isWhiteTurn;
                    repaintTurnLabel(g);
                }
                // castling
                else if (CanCastle(pieceX, pieceY, x, y)) {
                    performCastling(pieceX, pieceY, x, y);
                    redrawBoard();
                    Thread.sleep(100);
                    HandleCheck(Selected.iswhite());
                    Selected     = null;
                    isWhiteTurn = !isWhiteTurn;
                    repaintTurnLabel(g);
                }
                // invalid
                else {
                    Selected = null;
                }
            }
        }

        private boolean handleEnPassant(int fromX, int fromY, int toX, int toY) throws InterruptedException {
            if (Selected instanceof Pawn && enPassantTargetSquare != null) {
                if (toX == enPassantTargetSquare[1] && toY == enPassantTargetSquare[0]) {
                    Pawn pawn = (Pawn) Selected;
                    if (pawn.canEnPassant(fromX, fromY, toX, toY)) {
                        // Capture the pawn en passant
                        gameBoard[toY][toX] = Selected;
                        gameBoard[fromY][fromX] = null;

                        // Remove the pawn being captured en passant
                        int direction = pawn.iswhite() ? 1 : -1;
                        gameBoard[toY + direction][toX] = null;

                        redrawBoard();
                        Thread.sleep(100);
                        HandleCheck(pawn.iswhite());
                        Selected = null;
                        isWhiteTurn = !isWhiteTurn;
                        repaintTurnLabel(g);
                        enPassantTargetSquare = null;
                        return true;
                    }
                }
            }
            return false;
        }



        public void repaintTurnLabel(Graphics g) {
            // Clear previous label area (optional, if needed)
            g.setColor(Color.LIGHT_GRAY); // Background color of label area
            g.fillRect(0, 0, 200, 40); // Adjust width/height as needed

            // Draw the turn label
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            String text = isWhiteTurn ? "White's Turn" : "Black's Turn";
            g.drawString(text, 20, 30); // Adjust position to where you want it
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

            gameBoard[kingY][kingX+step] =  gameBoard[kingY][kingX];
            if (King.isInCheck(kingY, kingX + step)) { // King would pass through check
                gameBoard[kingY][kingX+step] =  null;
                return false;
            }

            gameBoard[kingY][kingX+step] =  null;
            // King would land in check
            gameBoard[kingY][kingX + 2 * step] =  gameBoard[kingY][kingX];
            if (King.isInCheck(kingY, kingX + 2 * step)) { // If it would land in check
                gameBoard[kingY][kingX + 2 * step] =  null;
                return false;
            } else{
                gameBoard[kingY][kingX + 2 * step] =  null;
                return true;
            }


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
                                JOptionPane.showMessageDialog(null, "White is in check!");
                            } else {
                                blackKingInCheck = true;
                                JOptionPane.showMessageDialog(null, "Black is in check!");

                            }
                        }
                    }
                }
            }

            if (whiteKingInCheck && !hasAnyLegalMoves(true)) {
                JOptionPane.showMessageDialog(null,"White is checkmated! Black wins!");
            }
            if (blackKingInCheck && !hasAnyLegalMoves(false)) {
                JOptionPane.showMessageDialog(null,"Black is checkmated! White wins!");
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

        private ChessPiece promotePawn(boolean isWhite) {
            String[] options = {"Queen", "Rook", "Bishop", "Knight"};
            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose promotion piece:",
                    "Pawn Promotion",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    "Queen"
            );

            if (choice == null) return new Queen(isWhite); // Default if canceled

            return switch (choice) {
                case "Rook" -> new Rook(isWhite);
                case "Bishop" -> new Bishop(isWhite);
                case "Knight" -> new Knight(isWhite);
                default -> new Queen(isWhite);
            };
        }



        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX() / 75;
            int y = e.getY() / 75;

            // Ensure the coordinates are within the board boundaries // 
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
                //isWhiteTurn = true;
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
