import java.util.ArrayList;

public class Bishop extends ChessPiece {
	public Bishop(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		moves.add(getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.UpRightDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownRightDiagonal, false));
		return moves;
	}
}
