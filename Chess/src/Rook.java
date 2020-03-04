import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		moves.add(getMovementInDirection(PieceMovementDirection.LeftRow, false));
		moves.add(getMovementInDirection(PieceMovementDirection.RightRow, false));
		moves.add(getMovementInDirection(PieceMovementDirection.UpColumn, false));
		moves.add(getMovementInDirection(PieceMovementDirection.DownColumn, false));	
		return moves;
	}

}
