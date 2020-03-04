import java.util.ArrayList;

public class Queen extends ChessPiece {
	public Queen(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		moves.add(getMovementInDirection(PieceMovementDirection.LeftRow, false));
		moves.add(getMovementInDirection(PieceMovementDirection.RightRow, false));
		moves.add(getMovementInDirection(PieceMovementDirection.UpColumn, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownColumn, false));
		moves.add(getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.UpRightDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownRightDiagonal, false));
		return moves;
	}
}
