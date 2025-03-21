import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Chess { // Ronen Sherman - chess and chess bot
    public static DrawingPanel panel = new DrawingPanel(600, 650);
    public static Graphics g = panel.getGraphics();

    public void main() {
        panel.setVisible(true);
        panel.addMouseListener(new GameBoard());

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

    public static class GameBoard extends Chess.BackgroundBoard implements MouseListener {
        private static final ChessPiece[][] gameBoard = new ChessPiece[8][8];
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

     /*  public static void Showmoves()
        {
            for(int i =0; i <= gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                if( gameBoard[i][j].getName() != null)
                {
                   String name =  gameBoard[i][j].getName();

                }
                }
            }

        }*/

        public static void PrintGame() {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] != null) {
                        char pieceSymbol;
                        if (gameBoard[i][j].iswhite()) {
                            switch (gameBoard[i][j].getName()) {
                                case "Pawn" -> pieceSymbol = '♙';
                                case "Rook" -> pieceSymbol = '♖';
                                case "Knight" -> pieceSymbol = '♘';
                                case "Bishop" -> pieceSymbol = '♗';
                                case "Queen" -> pieceSymbol = '♕';
                                case "King" -> pieceSymbol = '♔';
                                default -> pieceSymbol = '?'; // Should never happen
                            }
                        } else {
                            switch (gameBoard[i][j].getName()) {
                                case "Pawn" -> pieceSymbol = '♟';
                                case "Rook" -> pieceSymbol = '♜';
                                case "Knight" -> pieceSymbol = '♞';
                                case "Bishop" -> pieceSymbol = '♝';
                                case "Queen" -> pieceSymbol = '♛';
                                case "King" -> pieceSymbol = '♚';
                                default -> pieceSymbol = '?'; // Should never happen
                            }
                        }
                        if (Chess.BackgroundBoard.BackgroundBoard[i][j] == 0)
                            g.setColor(Color.BLACK);
                        g.setFont(new Font("piece", Font.PLAIN, 65));
                        g.drawString(String.valueOf(pieceSymbol), j * 75, (i + 1) * 75);
                        g.setColor(Color.WHITE);
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX()/75;
            int y = e.getY()/75;
            if (gameBoard[y][x] != null && Selected == null)
            {
                x = pieceX;
                y = pieceY;
                Selected = gameBoard[y][x];
            } else
            {
               if( Selected.isValidMove(pieceX,pieceY, x,y))
               {
                   gameBoard[y][x] = Selected;
                   gameBoard[pieceX][pieceY] = null;
                   Selected = null;
                   PrintGame();
               }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

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




