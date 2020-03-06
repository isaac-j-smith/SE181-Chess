import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class KnightTest {
    @Test
    public void testKnightMoves() {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // Black knight 1
        ChessPiece knight = board.getPiece(0,1);
        assertTrue(knight instanceof Knight);

        ArrayList<PieceMovement> moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(2,0), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(2,2), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(1,3), MovementCondition.None, null));

        int i = 0;
        for (PieceMovement s : knight.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        // Black knight 2
        knight = board.getPiece(0,6);
        assertTrue(knight instanceof Knight);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(2,5), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(2,7), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(1,4), MovementCondition.None, null));

        i = 0;
        for (PieceMovement s : knight.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        // White knight 1
        knight = board.getPiece(7,1);
        assertTrue(knight instanceof Knight);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(5,0), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(5,2), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(6,3), MovementCondition.None, null));

        i = 0;
        for (PieceMovement s : knight.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        // White knight 2
        knight = board.getPiece(7,6);
        assertTrue(knight instanceof Knight);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(5,5), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(5,7), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(6,4), MovementCondition.None, null));

        i = 0;
        for (PieceMovement s : knight.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }
    }
}