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
		PieceMovement enPassantMove;
		if (this.direction == PieceMovementDirection.UpColumn) {
			move = getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, MovementCondition.OnlyCapture);
			enPassantMove = getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, MovementCondition.enPassant);
			
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
				enPassantMove.removeNextMoveInSameDirection();
				moves.add(enPassantMove);
			}
			
			move = getMovementInDirection(PieceMovementDirection.UpRightDiagonal, MovementCondition.OnlyCapture);
			enPassantMove = getMovementInDirection(PieceMovementDirection.UpRightDiagonal, MovementCondition.enPassant);
			
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
				enPassantMove.removeNextMoveInSameDirection();
				moves.add(enPassantMove);
			}
			
		}
		else if (this.direction == PieceMovementDirection.DownColumn) {
			move = getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, MovementCondition.OnlyCapture);
			enPassantMove = getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, MovementCondition.enPassant);
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
				enPassantMove.removeNextMoveInSameDirection();
				moves.add(enPassantMove);
			}
			
			move = getMovementInDirection(PieceMovementDirection.DownRightDiagonal, MovementCondition.OnlyCapture);
			enPassantMove = getMovementInDirection(PieceMovementDirection.DownRightDiagonal, MovementCondition.enPassant);
			
			if (move != null) {
				move.removeNextMoveInSameDirection();
				moves.add(move);
				enPassantMove.removeNextMoveInSameDirection();
				moves.add(enPassantMove);
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
