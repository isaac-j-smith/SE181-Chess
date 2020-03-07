import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ChessboardTest {
    @Test
    public void testSetupBoard() {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // Black rook 1
        ChessPiece rook = board.getPiece(0,0);
        assertTrue(rook instanceof Rook);

        assertNull(rook.getListOfMoves().get(0));
        assertEquals(0, rook.getListOfMoves().get(1).getDestination().row);
        assertEquals(1, rook.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, rook.getListOfMoves().get(2).getDestination().row);
        assertEquals(0, rook.getListOfMoves().get(2).getDestination().column);
        assertNull(rook.getListOfMoves().get(3));
    }

    @Test
    public void testGetValidMoves() {
        Chessboard board = new Chessboard();
        board.setupBoard();

        ChessPiece knight = board.getPiece(0,1);
        ArrayList<PieceMovement> moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(2,0), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(2,2), MovementCondition.None, null));

        int i = 0;
        for (PieceLocation s : board.getValidMoves(knight)){
            assertEquals(moves.get(i).getDestination().row, s.row);
            assertEquals(moves.get(i).getDestination().column, s.column);
            i++;
        }
    }

    @Test
    public void testGetUnblockedMoveLocations() {
        Chessboard board = new Chessboard();
        board.setupBoard();

        ChessPiece rook = board.getPiece(0,0);
        assertEquals(0, board.getUnblockedMoveLocations(rook).size());

        ChessPiece pawn = board.getPiece(1,0);
        board.movePiece(pawn,  new PieceLocation(3,0));
        assertEquals(2, board.getUnblockedMoveLocations(rook).size());
        assertEquals(1, board.getUnblockedMoveLocations(rook).get(0).row);
        assertEquals(0, board.getUnblockedMoveLocations(rook).get(0).column);
        assertEquals(2, board.getUnblockedMoveLocations(rook).get(1).row);
        assertEquals(0, board.getUnblockedMoveLocations(rook).get(1).column);
    }

    @Test
    public void isInCheck() {
        Chessboard board = new Chessboard();
        board.setupBoard();
        assertFalse(board.isInCheckmate(PieceColor.Black));
        assertFalse(board.isInCheckmate(PieceColor.White));

        ChessPiece knight = board.getPiece(0,6);
        board.movePiece(knight, new PieceLocation(2,7));
        knight = board.getPiece(2,7);
        board.movePiece(knight, new PieceLocation(4,6));
        knight = board.getPiece(4,6);
        board.movePiece(knight, new PieceLocation(6,5));
        knight = board.getPiece(6,5);
        board.movePiece(knight, new PieceLocation(5,3));
        knight = board.getPiece(5,3);
        assertTrue(knight instanceof Knight);
        assertTrue(board.getPiece(7,4) instanceof King);
        assertFalse(board.isInCheck(PieceColor.White));
        assertTrue(board.isInCheck(PieceColor.Black));
    }

    @Test
    public void testIsInCheckmate() {
        Chessboard board = new Chessboard();
        assertFalse(board.isInCheckmate(PieceColor.Black));
        assertFalse(board.isInCheckmate(PieceColor.White));

        ArrayList<ArrayList<ChessPiece>> emptyBoard = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            // Add row
            emptyBoard.add(new ArrayList<>());
            // Add column
            for (int column = 0; column < 8; column++) {
                emptyBoard.get(row).add(null);
            }
        }
        board.setBoard(emptyBoard);

        ChessPiece whiteKing = new King(new PieceLocation(0,3), PieceColor.White, PieceMovementDirection.DownColumn);
        board.placePiece(whiteKing, whiteKing.location);

        ChessPiece blackKing = new King(new PieceLocation(7,3), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackQueen = new Queen(new PieceLocation(7,4), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackRook1 = new Rook(new PieceLocation(7,0), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackRook2 = new Rook(new PieceLocation(7,7), PieceColor.Black, PieceMovementDirection.DownColumn);
        board.placePiece(blackKing, new PieceLocation(7,3));
        board.placePiece(blackQueen, new PieceLocation(6,3));
        board.placePiece(blackRook1, new PieceLocation(7,2));
        board.placePiece(blackRook2,new PieceLocation(7,4));

        assertFalse(board.isInCheckmate(PieceColor.Black));
        assertTrue(board.isInCheckmate(PieceColor.White));
    }

    @Test
    public void testGetPieces() {
        Chessboard board = new Chessboard();
        board.setupBoard();

        ArrayList<ChessPiece> pieces = board.getPieces(PieceColor.Black);
        assertTrue(pieces.get(0) instanceof Pawn);
        assertTrue(pieces.get(11) instanceof Queen);

        pieces = board.getPieces(PieceColor.White);
        assertTrue(pieces.get(12) instanceof Pawn);
        assertTrue(pieces.get(3) instanceof Queen);
    }

    @Test
    public void testMovePiece() {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // Black rook 1
        ChessPiece knight = board.getPiece(0,1);
        board.movePiece(knight, new PieceLocation(2,0));
        knight = board.getPiece(2,0);
        assertTrue(knight instanceof Knight);

        ArrayList<PieceMovement> moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(4,1), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(0,1), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(1,2), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(3,2), MovementCondition.None, null));

        int i = 0;
        for (PieceMovement s : knight.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }
    }

    @Test
    public void testPlacePiece() {
        Chessboard board = new Chessboard();
        board.setupBoard();

        ArrayList<ArrayList<ChessPiece>> expectedBoard = board.getBoard();

        ChessPiece rook = expectedBoard.get(0).get(0);
        assertTrue(rook instanceof Rook);

        assertNull(rook.getListOfMoves().get(0));
        assertEquals(0, rook.getListOfMoves().get(1).getDestination().row);
        assertEquals(1, rook.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, rook.getListOfMoves().get(2).getDestination().row);
        assertEquals(0, rook.getListOfMoves().get(2).getDestination().column);
        assertNull(rook.getListOfMoves().get(3));
    }

    @Test
    public void testGetBoard() {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // Black rook 1
        ChessPiece rook = board.getPiece(0,0);
        assertTrue(rook instanceof Rook);

        assertNull(rook.getListOfMoves().get(0));
        assertEquals(0, rook.getListOfMoves().get(1).getDestination().row);
        assertEquals(1, rook.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, rook.getListOfMoves().get(2).getDestination().row);
        assertEquals(0, rook.getListOfMoves().get(2).getDestination().column);
        assertNull(rook.getListOfMoves().get(3));
    }

    @Test
    public void testSetBoard() {
        Chessboard board = new Chessboard();
        ArrayList<ArrayList<ChessPiece>> expectedBoard = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            // Add row
            expectedBoard.add(new ArrayList<>());
            // Add column
            for (int column = 0; column < 8; column++) {
                expectedBoard.get(row).add(null);
            }
        }

        ArrayList<ChessPiece> temp = new ArrayList<>();
        ChessPiece whiteKing = new King(new PieceLocation(0,3), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteQueen = new Queen(new PieceLocation(0,4), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteKnight1 = new Knight(new PieceLocation(0,1), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteKnight2 = new Knight(new PieceLocation(0,6), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteRook1 = new Rook(new PieceLocation(0,0), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteRook2 = new Rook(new PieceLocation(0,7), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteBishop1 = new Bishop(new PieceLocation(0,5), PieceColor.White, PieceMovementDirection.DownColumn);
        ChessPiece whiteBishop2 = new Bishop(new PieceLocation(0,2), PieceColor.White, PieceMovementDirection.DownColumn);
        expectedBoard.get(0).set(3, whiteKing);
        expectedBoard.get(0).set(4, whiteQueen);
        expectedBoard.get(0).set(1, whiteKnight1);
        expectedBoard.get(0).set(6, whiteKnight2);
        expectedBoard.get(0).set(0, whiteRook1);
        expectedBoard.get(0).set(7, whiteRook2);
        expectedBoard.get(0).set(5, whiteBishop1);
        expectedBoard.get(0).set(2, whiteBishop2);

        for (int i =0; i < 8; i++) {
            ChessPiece whitePawn = new Pawn(new PieceLocation(1,i), PieceColor.White, PieceMovementDirection.UpColumn);
            expectedBoard.get(1).set(i, whitePawn);
        }

        // Black pieces
        ChessPiece blackKing = new King(new PieceLocation(7,3), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackQueen = new Queen(new PieceLocation(7,4), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackKnight1 = new Knight(new PieceLocation(7,1), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackKnight2 = new Knight(new PieceLocation(7,6), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackRook1 = new Rook(new PieceLocation(7,0), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackRook2 = new Rook(new PieceLocation(7,7), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackBishop1 = new Bishop(new PieceLocation(7,5), PieceColor.Black, PieceMovementDirection.DownColumn);
        ChessPiece blackBishop2 = new Bishop(new PieceLocation(7,2), PieceColor.Black, PieceMovementDirection.DownColumn);
        expectedBoard.get(7).set(3, blackKing);
        expectedBoard.get(7).set(4, blackQueen);
        expectedBoard.get(7).set(1, blackKnight1);
        expectedBoard.get(7).set(6, blackKnight2);
        expectedBoard.get(7).set(0, blackRook1);
        expectedBoard.get(7).set(7, blackRook2);
        expectedBoard.get(7).set(5, blackBishop1);
        expectedBoard.get(7).set(2, blackBishop2);

        for (int i =0; i < 8; i++) {
            ChessPiece blackPawn = new Pawn(new PieceLocation(1,i), PieceColor.Black, PieceMovementDirection.UpColumn);
            expectedBoard.get(6).set(i, blackPawn);
        }
        board.setBoard(expectedBoard);

        ChessPiece rook = board.getPiece(0,0);
        assertTrue(rook instanceof Rook);

        assertNull(rook.getListOfMoves().get(0));
        assertEquals(0, rook.getListOfMoves().get(1).getDestination().row);
        assertEquals(1, rook.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, rook.getListOfMoves().get(2).getDestination().row);
        assertEquals(0, rook.getListOfMoves().get(2).getDestination().column);
        assertNull(rook.getListOfMoves().get(3));

    }
}
