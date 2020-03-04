import java.util.ArrayList;
import java.util.Scanner;

public class Chessboard {
	public static int MAX_ROW = 8;
	public static int MAX_COLUMN = 8;
	private ArrayList<ArrayList<ChessPiece>> board = new ArrayList<ArrayList<ChessPiece>>();
	
	public Chessboard() {
		// Create empty board
		for (int row = 0; row < this.MAX_ROW; row++) {
			// Add row
			this.board.add(new ArrayList<ChessPiece>());
			// Add column
			for (int column = 0; column < this.MAX_COLUMN; column++) {
				this.board.get(row).add(null);
			}
		}
		
		
		// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// FILL BOARD WITH THE SET OF PIECES
		////// BELOW IS TESTING OF CHESSBOARD
//		PieceLocation loc1 = new PieceLocation(1,1);
//		Rook rk = new Rook(loc1, PieceColor.White, PieceMovementDirection.UpColumn);
//		movePiece(rk, loc1);
//		PieceLocation loc2 = new PieceLocation(1,4);
//		Rook rk2 = new Rook(loc2, PieceColor.Black, PieceMovementDirection.DownColumn);
//		
//		movePiece(rk2, loc2);
//		PieceLocation loc3 = new PieceLocation(1,0);
//		King rk3 = new King(loc3, PieceColor.White, PieceMovementDirection.DownColumn);
//		movePiece(rk3, loc3);
		
		
		/////////////////////////////////////
		ChessPiece wKing = new King(new PieceLocation(0,3), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wQueen = new Queen(new PieceLocation(0,4), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wKnight1 = new Knight(new PieceLocation(0,1), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wKnight2 = new Knight(new PieceLocation(0,6), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wRook1 = new Rook(new PieceLocation(0,0), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wRook2 = new Rook(new PieceLocation(0,7), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wBishop1 = new Bishop(new PieceLocation(0,5), PieceColor.White, PieceMovementDirection.DownColumn);
		ChessPiece wBishop2 = new Bishop(new PieceLocation(0,2), PieceColor.White, PieceMovementDirection.DownColumn);
		for (int i =0; i < this.MAX_COLUMN; i++) {
			ChessPiece pawn = new Pawn(new PieceLocation(1,i), PieceColor.White, PieceMovementDirection.UpColumn);
			movePiece(pawn, pawn.location);
		}
		movePiece(wKing, wKing.location);
		movePiece(wQueen,wQueen.location);
		movePiece(wKnight1,wKnight1.location);
		movePiece(wKnight2, wKnight2.location);
		movePiece(wRook1,wRook1.location);
		movePiece(wRook2,wRook2.location);
		movePiece(wBishop1, wBishop1.location);
		movePiece(wBishop2, wBishop2.location);
		
		ChessPiece bKing = new King(new PieceLocation(7,3), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bQueen = new Queen(new PieceLocation(7,4), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bKnight1 = new Knight(new PieceLocation(7,1), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bKnight2 = new Knight(new PieceLocation(7,6), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bRook1 = new Rook(new PieceLocation(7,0), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bRook2 = new Rook(new PieceLocation(7,7), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bBishop1 = new Bishop(new PieceLocation(7,5), PieceColor.Black, PieceMovementDirection.UpColumn);
		ChessPiece bBishop2 = new Bishop(new PieceLocation(7,2), PieceColor.Black, PieceMovementDirection.UpColumn);
		
		movePiece(bKing, bKing.location);
		movePiece(bQueen,bQueen.location);
		movePiece(bKnight1,bKnight1.location);
		movePiece(bKnight2, bKnight2.location);
		movePiece(bRook1,bRook1.location);
		movePiece(bRook2,bRook2.location);
		movePiece(bBishop1, bBishop1.location);
		movePiece(bBishop2, bBishop2.location);
		for (int i =0; i < this.MAX_COLUMN; i++) {
			ChessPiece pawn = new Pawn(new PieceLocation(6,i), PieceColor.Black, PieceMovementDirection.DownColumn);
			movePiece(pawn, pawn.location);
		}
		////////////////////
		
		
//		ArrayList<PieceLocation> a = getValidMoves(rk);
//		printer(a);
//		Pawn p = new Pawn(new PieceLocation(1,0), PieceColor.White, PieceMovementDirection.UpColumn);
//		movePiece(p,p.location);
		printBoard();
//		printer(this.getCollisionFreeMoves(this.getPiece(0, 0)));
//		printer(this.getCollisionFreeMoves(p));
		game();
		System.out.println("END");
	}
	
	public void game() {
		while (true) {
			printBoard();
			Scanner in = new Scanner(System.in);
			String line = in.nextLine();
			String[] s = line.split("");
			int startRow = Integer.parseInt(s[0]);
			int startColumn = Integer.parseInt(s[1]);
			int endRow = Integer.parseInt(s[3]);
			int endColumn = Integer.parseInt(s[4]);
			PieceLocation start = new PieceLocation(startRow, startColumn);
			PieceLocation dest = new PieceLocation(endRow, endColumn);
			ChessPiece piece = this.getPiece(start);
			if (piece == null) {
				System.out.println("NULL SQUARE");
			}
			else {
				ArrayList<PieceLocation> moves = this.getValidMoves(piece);
				boolean isValid = false;
				for (PieceLocation move: moves) {
					if (move.row == dest.row) {
						if (move.column == dest.column) {
							isValid = true;
						}
					}
				}
				if (isValid) {
					movePiece(piece, dest);
					piece.setLocation(dest);
				}
				else {
					System.out.println("invalid");
				}
			}
		}
	}
	
	public ChessPiece getPiece(int row, int column) {
		return this.board.get(row).get(column);
	}
	
	//////////////// FOR TESTING PRINTER
	////////////////////////////////////
	public void printer(ArrayList<PieceLocation> a) {
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).row + "," + a.get(i).column);
		}
	}
	
	public void printBoard() {
		System.out.println("" + "\t" + "0" + "\t" + "1" + "\t" + "2" + "\t" + "3" + "\t" + "4" + "\t" + "5" + "\t" + "6" + "\t" + "7" + "\t");
		int rowNum = 0;
		for (ArrayList<ChessPiece> row: board) {
			String s = rowNum + "\t";
			rowNum++;
			for (ChessPiece column: row) {
				if (column != null) {
					s += column.color.toString().substring(0,1) + column.toString().split("@")[0] + "\t";
				}
				else {
					s += "NULL" + "\t";
				}
			}
			System.out.println(s);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<PieceLocation> getValidMoves(ChessPiece piece) {
		ArrayList<PieceLocation> validMoves = new ArrayList<PieceLocation>();
		ArrayList<PieceLocation> collisionFreeMoves = getUnblockedMoveLocations(piece);
		
		// For each collision free move, check if it won't result in the player in check
		for (PieceLocation collisionFreeMove: collisionFreeMoves) {
			// Save info of current state to restore back
			ChessPiece pieceAtDestination = getPiece(collisionFreeMove);
			PieceLocation savePieceOriginalLocation = piece.location;
			
			// Temporarily move piece
			movePiece(piece, collisionFreeMove);
			
			// If the move will not result in the player in check then it is valid
			if (checkIfColorInCheck(piece.color) == false) {
				validMoves.add(collisionFreeMove);
			}
			
			// Move pieces back to original locations
			movePiece(piece, savePieceOriginalLocation);
			movePiece(pieceAtDestination, collisionFreeMove);
		}
		return validMoves;
	}
	
	/*
	 * Get the locations of moves that a piece can go to without running into another piece
	 * 
	 */
	public ArrayList<PieceLocation> getUnblockedMoveLocations(ChessPiece piece) {
		ArrayList<PieceMovement> movements = piece.getListOfMoves();
		ArrayList<PieceLocation> locations = new ArrayList<PieceLocation>();
		
		for (int i = 0; i < movements.size(); i++) {
			PieceMovement move = movements.get(i);
			
			while (move != null) {
				// Check if there is already a piece there
				ChessPiece pieceAtDestination = getPiece(move.getDestination());
				// If there isn't a piece and the move is valid even when not capturing then the move is valid
				if (pieceAtDestination == null && move.getMovementCondition() != MovementCondition.OnlyCapture) {
					locations.add(move.getDestination());
					move = move.getNextMoveInSameDirection();
				}
				// Else if there is a piece, check if it is the same player's or opponent's and if the piece can capture it
				else if (pieceAtDestination != null && move.getMovementCondition() != MovementCondition.OnlyMove) {
					// If it is the same color then the move is not valid
					if (pieceAtDestination.color == piece.color) {
						move = null;
					}
					// Else the move is capturing the opponent's piece and is valid
					else {
						locations.add(move.getDestination());
						move = null;
					}
				}
				else {
					move = null;
				}
			}
		}
		return locations;
	}
	
	public boolean checkIfColorInCheck(PieceColor color) {
		ArrayList<ChessPiece> opponentPieces = null;
		if (color == PieceColor.Black) {
			opponentPieces = getPieces(PieceColor.White);
		}
		else {
			opponentPieces = getPieces(PieceColor.Black);
		}
		
		// For each enemy piece check if it can hit the king in a turn
		for (int i = 0; i < opponentPieces.size(); i++) {
			ChessPiece piece = opponentPieces.get(i);
			ArrayList<PieceLocation> locations = getUnblockedMoveLocations(piece);

			// Check if any location contains the king
			for (PieceLocation location: locations) {
				ChessPiece pieceAtLocation = getPiece(location);
				if (pieceAtLocation instanceof King) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ChessPiece getPiece(PieceLocation location) {
		return this.board.get(location.row).get(location.column);
	}
	
	/*
	 * Gets all the pieces on the board of a given color
	 * @param PieceColor - the color pieces to get
	 * @return ArrayList<ChessPiece> - the list of pieces on the board with the same color as the given color
	 */
	public ArrayList<ChessPiece> getPieces(PieceColor color) {
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		// Go through the board to get all the pieces of the color
		for (ArrayList<ChessPiece> row: this.board) {
			for (ChessPiece column: row) {
				if (column != null) {
					if (column.color == color) {
						pieces.add(column);
					}
				}
			}
		}
		
		return pieces;
	}
	
	/*
	 * Moves a piece to a location
	 * @param ChessPiece - the piece to move
	 * @param PieceLocation - the location to move the piece
	 */
	public void movePiece (ChessPiece piece, PieceLocation destination) {
		// Remove piece at old location
		if (piece != null)
		{
			this.board.get(piece.location.row).remove(piece.location.column);
			this.board.get(piece.location.row).add(piece.location.column, null);
			
			piece.location = destination;
		}
		
		// Move piece to new location
		this.board.get(destination.row).remove(destination.column);
		this.board.get(destination.row).add(destination.column, piece);
	}
}
