import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		
		moves.add(getMovementInDirection(PieceMovementDirection.LeftRow, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.RightRow, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.UpColumn, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownColumn, MovementCondition.None));
		
		return moves;
	}
}
