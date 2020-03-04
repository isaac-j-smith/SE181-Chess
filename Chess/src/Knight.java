import java.util.ArrayList;

public class Knight extends ChessPiece {

	public Knight(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		super(startLocation, color, direction);
	}

	@Override
	public ArrayList<PieceMovement> getListOfMoves() {
		ArrayList<PieceMovement> moves = new ArrayList<PieceMovement>();
		int jumpAmount = 2;
		
		PieceLocation location;
		PieceMovement move;
		
		// Add moves jump up 2, side 1
		if (this.location.row + jumpAmount < Chessboard.MAX_ROW) {
			if (this.location.column - 1 >= 0) {
				location = new PieceLocation(this.location.row + jumpAmount, this.location.column - 1);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
			if (this.location.column + 1 < Chessboard.MAX_COLUMN) {
				location = new PieceLocation(this.location.row + jumpAmount, this.location.column + 1);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
		}
		
		// Add moves jump down 2, side 1
		if (this.location.row - jumpAmount >= 0) {
			if (this.location.column - 1 >= 0) {
				location = new PieceLocation(this.location.row - jumpAmount, this.location.column - 1);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
			if (this.location.column + 1 < Chessboard.MAX_COLUMN) {
				location = new PieceLocation(this.location.row - jumpAmount, this.location.column + 1);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
		}
		
		// Add moves jump left 2, side 1
		if (this.location.column - jumpAmount >= 0) {
			if (this.location.row - 1 >= 0) {
				location = new PieceLocation(this.location.row - 1, this.location.column - jumpAmount);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
			if (this.location.row + 1 < Chessboard.MAX_ROW) {
				location = new PieceLocation(this.location.row + 1, this.location.column - jumpAmount);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
		}
		
		// Add moves jump right 2, side 1
		if (this.location.column + jumpAmount < Chessboard.MAX_COLUMN) {
			if (this.location.row - 1 >= 0) {
				location = new PieceLocation(this.location.row - 1, this.location.column + jumpAmount);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
			if (this.location.row + 1 < Chessboard.MAX_ROW) {
				location = new PieceLocation(this.location.row + 1, this.location.column + jumpAmount);
				move = new PieceMovement(location, false, null);
				moves.add(move);
			}
		}
		
		return moves;
	}
}
