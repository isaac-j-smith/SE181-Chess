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
	
	public abstract ArrayList<PieceMovement> getListOfMoves();
	
	
	protected PieceMovement getMovementInDirection(PieceMovementDirection direction, boolean onlyAttackMovement) {
		ArrayList<PieceLocation> locations = new ArrayList<PieceLocation>();
		int row;
		int column;
		
		// Get list of locations in a direction
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
		
		// Convert the locations into movements and link them together if there are moves
		PieceMovement movements = null;
		if (locations.size() > 0) {
			Collections.reverse(locations);
			movements = new PieceMovement(locations.get(0), onlyAttackMovement, null);
			for (int index = 1; index < locations.size(); index++) {
				movements = new PieceMovement(locations.get(index), onlyAttackMovement, movements);
			}
		}
		
		return movements;
	}
	
	
	public void move(PieceLocation destination) {
		this.location = destination;
	}
}
