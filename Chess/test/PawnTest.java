import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PawnTest {
    @Test
    public void testPawnMoves() {
        Chessboard board = new Chessboard();
        board.setupBoard();


        // White pawn 3
        ChessPiece pawn = board.getPiece(1,2);
        assertTrue(pawn instanceof Pawn);

        ArrayList<PieceMovement> moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(2,2), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(2,1), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(2,3), MovementCondition.None, null));

        int i = 0;
        for (PieceMovement s : pawn.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        board.movePiece(pawn, new PieceLocation(3,2));
        pawn = board.getPiece(3,2);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(4,2), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(4,1), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(4,3), MovementCondition.None, null));
        i = 0;
        for (PieceMovement s : pawn.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        // Black pawn 6
        pawn = board.getPiece(6,5);
        assertTrue(pawn instanceof Pawn);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(5,5), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(5,4), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(5,6), MovementCondition.None, null));

        i = 0;
        for (PieceMovement s : pawn.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }

        board.movePiece(pawn, new PieceLocation(4,5));
        pawn = board.getPiece(4,5);

        moves = new ArrayList<>();
        moves.add(new PieceMovement(new PieceLocation(3,5), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(3,4), MovementCondition.None, null));
        moves.add(new PieceMovement(new PieceLocation(3,6), MovementCondition.None, null));
        i = 0;
        for (PieceMovement s : pawn.getListOfMoves()){
            assertEquals(moves.get(i).getDestination().row, s.getDestination().row);
            assertEquals(moves.get(i).getDestination().column, s.getDestination().column);
            i++;
        }
    }
}