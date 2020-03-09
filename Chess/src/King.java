import java.util.ArrayList;

public class King extends ChessPiece {
	private boolean hasMoved = false;
	
	public King(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		
		moves.add(getMovementInDirection(PieceMovementDirection.LeftRow, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.RightRow, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.UpColumn, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownColumn, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.UpLeftDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.UpRightDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownLeftDiagonal, MovementCondition.None));
		moves.add(getMovementInDirection(PieceMovementDirection.DownRightDiagonal, MovementCondition.None));
		
		// Since king can only move 1 in each direction, unlink each movement so it is only 1 in each direction
		for (PieceMovement move: moves) {
			// Ignores moves in directions that have no moves
			if (move != null) {
				move.removeNextMoveInSameDirection();
			}
		}
		
		// Castling
		if (hasMoved == false) {
			moves.add(getMovementInDirection(PieceMovementDirection.LeftRow, MovementCondition.Castling));
			moves.add(getMovementInDirection(PieceMovementDirection.RightRow, MovementCondition.Castling));
		}		
		
		return moves;
	}
	
	@Override
	public void setLocation(PieceLocation destination) {
		this.location = destination;
		this.hasMoved = true;
	}
}
