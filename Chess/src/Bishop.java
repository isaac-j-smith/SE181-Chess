import java.util.ArrayList;

public class Bishop extends ChessPiece {
	
	public Bishop(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		
		moves.add(getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.UpRightDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownRightDiagonal, MovementCondition.None));
		
		return moves;
	}
}
