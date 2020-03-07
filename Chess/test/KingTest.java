import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class KingTest 
{
    @Test
    public void testKingMoves() 
    {
        Chessboard board = new Chessboard();
        board.setupBoard();

        // White king 
        ChessPiece king = board.getPiece(7,4);
        assertTrue(king instanceof King);

        assertEquals(7, king.getListOfMoves().get(0).getDestination().row);
        assertEquals(3, king.getListOfMoves().get(0).getDestination().column);
        assertEquals(7, king.getListOfMoves().get(1).getDestination().row);
        assertEquals(5, king.getListOfMoves().get(1).getDestination().column);
        assertNull(king.getListOfMoves().get(2));
        assertEquals(6, king.getListOfMoves().get(3).getDestination().row);
        assertEquals(4, king.getListOfMoves().get(3).getDestination().column);
        assertNull(king.getListOfMoves().get(4));
        assertNull(king.getListOfMoves().get(5));
        assertEquals(6, king.getListOfMoves().get(6).getDestination().row);
        assertEquals(3, king.getListOfMoves().get(6).getDestination().column);
        assertEquals(6, king.getListOfMoves().get(7).getDestination().row);
        assertEquals(5, king.getListOfMoves().get(7).getDestination().column);

        // Black King 
        king = board.getPiece(0,4);
        assertTrue(king instanceof King);

        assertEquals(0, king.getListOfMoves().get(0).getDestination().row);
        assertEquals(3, king.getListOfMoves().get(0).getDestination().column);
        assertEquals(0, king.getListOfMoves().get(1).getDestination().row);
        assertEquals(5, king.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, king.getListOfMoves().get(2).getDestination().row);
        assertEquals(4, king.getListOfMoves().get(2).getDestination().column);
        assertNull(king.getListOfMoves().get(3));
        assertEquals(1, king.getListOfMoves().get(4).getDestination().row);
        assertEquals(3, king.getListOfMoves().get(4).getDestination().column);
        assertEquals(1, king.getListOfMoves().get(5).getDestination().row);
        assertEquals(5, king.getListOfMoves().get(5).getDestination().column);
        assertNull(king.getListOfMoves().get(6));
        assertNull(king.getListOfMoves().get(7));


    }
}