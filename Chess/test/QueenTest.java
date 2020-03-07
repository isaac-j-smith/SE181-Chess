import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QueenTest 
{
    @Test
    public void testQueenMoves() 
    {
        Chessboard board = new Chessboard();
        board.setupBoard();

        // White Queen 
        ChessPiece queen = board.getPiece(7,3);
        assertTrue(queen instanceof Queen);

        assertEquals(7, queen.getListOfMoves().get(0).getDestination().row);
        assertEquals(2, queen.getListOfMoves().get(0).getDestination().column);
        assertEquals(7, queen.getListOfMoves().get(1).getDestination().row);
        assertEquals(4, queen.getListOfMoves().get(1).getDestination().column);
        assertNull(queen.getListOfMoves().get(2));
        assertEquals(6, queen.getListOfMoves().get(3).getDestination().row);
        assertEquals(3, queen.getListOfMoves().get(3).getDestination().column);
        assertNull(queen.getListOfMoves().get(4));
        assertNull(queen.getListOfMoves().get(5));
        assertEquals(6, queen.getListOfMoves().get(6).getDestination().row);
        assertEquals(2, queen.getListOfMoves().get(6).getDestination().column);
        assertEquals(6, queen.getListOfMoves().get(7).getDestination().row);
        assertEquals(4, queen.getListOfMoves().get(7).getDestination().column);

        // Black Queen 
        queen = board.getPiece(0,3);
        assertTrue(queen instanceof Queen);

        assertEquals(0, queen.getListOfMoves().get(0).getDestination().row);
        assertEquals(2, queen.getListOfMoves().get(0).getDestination().column);
        assertEquals(0, queen.getListOfMoves().get(1).getDestination().row);
        assertEquals(4, queen.getListOfMoves().get(1).getDestination().column);
        assertEquals(1, queen.getListOfMoves().get(2).getDestination().row);
        assertEquals(3, queen.getListOfMoves().get(2).getDestination().column);
        assertNull(queen.getListOfMoves().get(3));
        assertEquals(1, queen.getListOfMoves().get(4).getDestination().row);
        assertEquals(2, queen.getListOfMoves().get(4).getDestination().column);
        assertEquals(1, queen.getListOfMoves().get(5).getDestination().row);
        assertEquals(4, queen.getListOfMoves().get(5).getDestination().column);
        assertNull(queen.getListOfMoves().get(6));
        assertNull(queen.getListOfMoves().get(7));


    }
}