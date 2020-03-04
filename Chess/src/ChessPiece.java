import java.util.ArrayList;
import java.util.Collections;

public abstract class ChessPiece {
	protected PieceLocation location;
	protected PieceColor color;
	protected PieceMovementDirection direction;
	
	public ChessPiece(PieceLocation startLocation, PieceColor color, PieceMovementDirection direction) {
		this.location = startLocation;
		this.color = color;
		this.direction = direction;
	}
	
	/*
	 * Get list of moves the piece could do on a chess board at its location (as if there was no other piece on the board)
	 * @return ArrayList<PieceMovement> - All the movements the piece can do
	 */
	public abstract ArrayList<PieceMovement> getListOfMoves();
	
	public void setLocation(PieceLocation destination) {
		this.location = destination;
	}
	
	/*
	 * Helper function used to generate movements to all locations in a direction
	 * @param PieceMovementDirection - direction of movements
	 * @param boolean - if the move is only valid as an attack
	 * @return PieceMovement - Link list of PieceMovement in the given direction 
	*/
	protected PieceMovement getMovementInDirection(PieceMovementDirection direction, MovementCondition movementCondition) {
		ArrayList<PieceLocation> locations = new ArrayList<PieceLocation>();
		int row;
		int column;
		
		// Generate list of locations in a direction until it reaches the end of the board
		switch (direction) {
			case UpColumn:
				for (row = this.location.row + 1; row < Chessboard.MAX_ROW; row++) {
					PieceLocation location = new PieceLocation(row, this.location.column);
					locations.add(location);
				}
				break;
			case DownColumn:
				for (row = this.location.row - 1; row >= 0; row--) {
					PieceLocation location = new PieceLocation(row, this.location.column);
					locations.add(location);
				}
				break;
			case LeftRow:
				for (column = this.location.column - 1; column >= 0; column--) {
					PieceLocation location = new PieceLocation(this.location.row, column);
					locations.add(location);
				}
				break;
			case RightRow:
				for (column = this.location.column + 1; column < Chessboard.MAX_COLUMN; column++) {
					PieceLocation location = new PieceLocation(this.location.row, column);
					locations.add(location);
				}
				break;
			case UpLeftDiagonal:
				row = this.location.row + 1;
				for (column = this.location.column - 1; column >= 0; column--) {
					if (row < Chessboard.MAX_ROW) {
						PieceLocation location = new PieceLocation(row, column);
						locations.add(location);
					}
					row++;
				}
				break;
			case UpRightDiagonal:
				row = this.location.row + 1;
				for (column = this.location.column + 1; column < Chessboard.MAX_COLUMN; column++) {
					if (row < Chessboard.MAX_ROW) {
						PieceLocation location = new PieceLocation(row, column);
						locations.add(location);
					}
					row++;
				}
				break;
			case DownLeftDiagonal:
				row = this.location.row - 1;
				for (column = this.location.column - 1; column >= 0; column--) {
					if (row >= 0) {
						PieceLocation location = new PieceLocation(row, column);
						locations.add(location);
					}
					row--;
				}
				break;
			case DownRightDiagonal:
				row = this.location.row - 1;
				for (column = this.location.column + 1; column < Chessboard.MAX_COLUMN; column++) {
					if (row >= 0) {
						PieceLocation location = new PieceLocation(row, column);
						locations.add(location);
					}
					row--;
				}
				break;
		}
		
		// Convert each locations into movements and link them together
		PieceMovement movements = null;
		// If there are locations then link it in the order that the parent link is closer the piece than the child link
		if (locations.size() > 0) {
			Collections.reverse(locations);
			movements = new PieceMovement(locations.get(0), movementCondition, null);
			for (int index = 1; index < locations.size(); index++) {
				movements = new PieceMovement(locations.get(index), movementCondition, movements);
			}
		}
		
		return movements;
	}
}
