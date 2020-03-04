import java.util.ArrayList;

public class Pawn extends ChessPiece {
	private boolean hasMoved = false;
	
	public Pawn(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		PieceMovement move = getMovementInDirection(this.direction, MovementCondition.OnlyMove);
		
		// If it has moved then only allow moving one space else let it move 2 from its starting location
		if (move != null) {
			if (this.hasMoved == true) {
				if (move.getNextMoveInSameDirection() != null) {
					move.removeNextMoveInSameDirection();
				}
			}
			else {
				if (move.getNextMoveInSameDirection() != null) {
					move.getNextMoveInSameDirection().removeNextMoveInSameDirection();
				}
			}
		}
		
		moves.add(move);
		
		// Add possible diagonal attack moves
		if (this.direction == PieceMovementDirection.UpColumn) {
			move = getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, MovementCondition.OnlyCapture);
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
			}
			
			move = getMovementInDirection(PieceMovementDirection.UpRightDiagonal, MovementCondition.OnlyCapture);
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
			}
			
		}
		else if (this.direction == PieceMovementDirection.DownColumn) {
			move = getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, MovementCondition.OnlyCapture);
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
			}
			
			move = getMovementInDirection(PieceMovementDirection.DownRightDiagonal, MovementCondition.OnlyCapture);
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
			}
		}
		
		return moves;
	}
	
	@Override
	public void setLocation(PieceLocation destination) {
		this.location = destination;
		this.hasMoved = true;
	}
}
