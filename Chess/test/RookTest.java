import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RookTest {
    @Test
    public void testRookMoves() {
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

        // Black rook 2
        rook = board.getPiece(0,7);
        assertTrue(rook instanceof Rook);

        assertEquals(0, rook.getListOfMoves().get(0).getDestination().row);
        assertEquals(6, rook.getListOfMoves().get(0).getDestination().column);
        assertNull(rook.getListOfMoves().get(1));
        assertEquals(1, rook.getListOfMoves().get(2).getDestination().row);
        assertEquals(7, rook.getListOfMoves().get(2).getDestination().column);
        assertNull(rook.getListOfMoves().get(3));

        // White rook 1
        rook = board.getPiece(7,0);
        assertTrue(rook instanceof Rook);

        assertNull(rook.getListOfMoves().get(0));
        assertEquals(7, rook.getListOfMoves().get(1).getDestination().row);
        assertEquals(1, rook.getListOfMoves().get(1).getDestination().column);
        assertNull(rook.getListOfMoves().get(2));
        assertEquals(6, rook.getListOfMoves().get(3).getDestination().row);
        assertEquals(0, rook.getListOfMoves().get(3).getDestination().column);

        // White rook 2
        rook = board.getPiece(7,7);
        assertTrue(rook instanceof Rook);

        assertEquals(7, rook.getListOfMoves().get(0).getDestination().row);
        assertEquals(6, rook.getListOfMoves().get(0).getDestination().column);
        assertNull(rook.getListOfMoves().get(1));
        assertNull(rook.getListOfMoves().get(2));
        assertEquals(6, rook.getListOfMoves().get(3).getDestination().row);
        assertEquals(7, rook.getListOfMoves().get(3).getDestination().column);
    }
}