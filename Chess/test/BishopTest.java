import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BishopTest 
{
    @Test
    public void testBishopMoves() 
    {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // black bishop 1 
        ChessPiece bishop = board.getPiece(0,2);
        assertTrue(bishop instanceof Bishop);

        
        assertEquals(1, bishop.getListOfMoves().get(0).getDestination().row);
        assertEquals(1, bishop.getListOfMoves().get(0).getDestination().column);
        assertEquals(1, bishop.getListOfMoves().get(1).getDestination().row);
        assertEquals(3, bishop.getListOfMoves().get(1).getDestination().column);
        assertNull(bishop.getListOfMoves().get(2));
        assertNull(bishop.getListOfMoves().get(3));
        
        // black bishop 2 
        bishop = board.getPiece(0,5);
        assertTrue(bishop instanceof Bishop);
        
        assertEquals(1, bishop.getListOfMoves().get(0).getDestination().row);
        assertEquals(4, bishop.getListOfMoves().get(0).getDestination().column);
        assertEquals(1, bishop.getListOfMoves().get(1).getDestination().row);
        assertEquals(6, bishop.getListOfMoves().get(1).getDestination().column);
        assertNull(bishop.getListOfMoves().get(2));
        assertNull(bishop.getListOfMoves().get(3));
        
        // white bishop 1 
        bishop = board.getPiece(7,2);
        assertTrue(bishop instanceof Bishop);
        
        assertNull(bishop.getListOfMoves().get(0));
        assertNull(bishop.getListOfMoves().get(1));
        assertEquals(6, bishop.getListOfMoves().get(2).getDestination().row);
        assertEquals(1, bishop.getListOfMoves().get(2).getDestination().column);
        assertEquals(6, bishop.getListOfMoves().get(3).getDestination().row);
        assertEquals(3, bishop.getListOfMoves().get(3).getDestination().column);
        
        // white bishop 2
        bishop = board.getPiece(7,5);
        assertTrue(bishop instanceof Bishop);
        assertNull(bishop.getListOfMoves().get(0));
        assertNull(bishop.getListOfMoves().get(1));
        assertEquals(6, bishop.getListOfMoves().get(2).getDestination().row);
        assertEquals(4, bishop.getListOfMoves().get(2).getDestination().column);
        assertEquals(6, bishop.getListOfMoves().get(3).getDestination().row);
        assertEquals(6, bishop.getListOfMoves().get(3).getDestination().column);
        
    }
}