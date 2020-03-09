import java.util.ArrayList;

public class Rook extends ChessPiece {
	private boolean hasMoved = false;
	
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
	
	@Override
	public void setLocation(PieceLocation destination) {
		this.location = destination;
		this.hasMoved = true;
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
}
