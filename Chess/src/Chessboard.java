import java.util.ArrayList;

public class Chessboard {
	public static int MAX_ROW = 8;
	public static int MAX_COLUMN = 8;
	private ArrayList<ArrayList<ChessPiece>> board = new ArrayList<ArrayList<ChessPiece>>();
	private ChessPiece lastMovedPiece = null;
	private PieceLocation lastMovedPiecePreviousLocation = null;
	
	public Chessboard() {
		// Create empty board
		for (int row = 0; row < MAX_ROW; row++) {
			// Add row
			this.board.add(new ArrayList<ChessPiece>());
			// Add column
			for (int column = 0; column < MAX_COLUMN; column++) {
				this.board.get(row).add(null);
			}
		}
		
		setupBoard();
	}
	
	/**
	 * Setup the board with the standard 16 chess pieces for each color
	 */
	public void setupBoard() {
		// White pieces
		ChessPiece whiteKing = new King(new PieceLocation(0,4), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteQueen = new Queen(new PieceLocation(0,3), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteKnight1 = new Knight(new PieceLocation(0,1), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteKnight2 = new Knight(new PieceLocation(0,6), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteRook1 = new Rook(new PieceLocation(0,0), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteRook2 = new Rook(new PieceLocation(0,7), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteBishop1 = new Bishop(new PieceLocation(0,5), PieceColor.White, PieceMovementDirection.UpColumn);
		ChessPiece whiteBishop2 = new Bishop(new PieceLocation(0,2), PieceColor.White, PieceMovementDirection.UpColumn);
		
		placePiece(whiteKing, whiteKing.location);
		placePiece(whiteQueen,whiteQueen.location);
		placePiece(whiteKnight1,whiteKnight1.location);
		placePiece(whiteKnight2, whiteKnight2.location);
		placePiece(whiteRook1,whiteRook1.location);
		placePiece(whiteRook2,whiteRook2.location);
		placePiece(whiteBishop1, whiteBishop1.location);
		placePiece(whiteBishop2, whiteBishop2.location);
		
		for (int i =0; i < MAX_COLUMN; i++) {
			ChessPiece whitePawn = new Pawn(new PieceLocation(1,i), PieceColor.White, PieceMovementDirection.UpColumn);
			placePiece(whitePawn, whitePawn.location);
		}
		
		// Black pieces
		ChessPiece blackKing = new King(new PieceLocation(7,4), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackQueen = new Queen(new PieceLocation(7,3), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackKnight1 = new Knight(new PieceLocation(7,1), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackKnight2 = new Knight(new PieceLocation(7,6), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackRook1 = new Rook(new PieceLocation(7,0), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackRook2 = new Rook(new PieceLocation(7,7), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackBishop1 = new Bishop(new PieceLocation(7,5), PieceColor.Black, PieceMovementDirection.DownColumn);
		ChessPiece blackBishop2 = new Bishop(new PieceLocation(7,2), PieceColor.Black, PieceMovementDirection.DownColumn);
		
		placePiece(blackKing, blackKing.location);
		placePiece(blackQueen,blackQueen.location);
		placePiece(blackKnight1,blackKnight1.location);
		placePiece(blackKnight2, blackKnight2.location);
		placePiece(blackRook1,blackRook1.location);
		placePiece(blackRook2,blackRook2.location);
		placePiece(blackBishop1, blackBishop1.location);
		placePiece(blackBishop2, blackBishop2.location);
		
		for (int i =0; i < MAX_COLUMN; i++) {
			ChessPiece blackPawn = new Pawn(new PieceLocation(6,i), PieceColor.Black, PieceMovementDirection.DownColumn);
			placePiece(blackPawn, blackPawn.location);
		}
	}
	
	/**
	 * Gets a list of locations that a piece can move to without getting into check
	 * @param ChessPiece - the piece to get moves for
	 * @return ArrayList<PieceLocation> - list of locations the piece can go to that is not blocked nor result in check
	 */
	public ArrayList<PieceLocation> getValidMoves(ChessPiece piece) {
		ArrayList<PieceLocation> validMoves = new ArrayList<PieceLocation>();
		ArrayList<PieceLocation> unblockedMoves = getUnblockedMoveLocations(piece);
		
		// For each collision free move, check if it won't result in the player in check
		for (PieceLocation unblockedMove: unblockedMoves) {
			// Save info of current state to restore back after checking
			ChessPiece pieceAtDestination = getPiece(unblockedMove);
			PieceLocation savePieceOriginalLocation = piece.location;
			
			// Temporarily move piece
			placePiece(piece, unblockedMove);
			
			// If the move will not result in the player in check then it is valid
			if (isInCheck(piece.color) == false) {
				validMoves.add(unblockedMove);
			}
			
			// Move pieces back to original locations
			placePiece(piece, savePieceOriginalLocation);
			placePiece(pieceAtDestination, unblockedMove);
		}
		
		return validMoves;
	}
	
	/**
	 * Get the locations of moves that a piece can go to without running into another piece along the way
	 * @param ChessPiece - the piece to get the list for
	 * @return ArrayList<PieceLocation> - the list of locations the piece can go to unblocked
	 */
	public ArrayList<PieceLocation> getUnblockedMoveLocations(ChessPiece piece) {
		ArrayList<PieceMovement> movements = piece.getListOfMoves();
		ArrayList<PieceLocation> locations = new ArrayList<PieceLocation>();
		
		// Check each movements
		for (PieceMovement movement: movements) {
			// Check through the movement link list
			while (movement != null) {
				// Check if there is already a piece there
				ChessPiece pieceAtDestination = getPiece(movement.getDestination());
				// If there isn't a piece and the move is valid even when not capturing then the move is valid
				if (pieceAtDestination == null && (movement.getMovementCondition() == MovementCondition.OnlyMove || movement.getMovementCondition() == MovementCondition.None)) {
					locations.add(movement.getDestination());
					movement = movement.getNextMoveInSameDirection();
				}
				// Else if there is a piece, check if it is the same player's or opponent's and if the piece can capture it
				else if (pieceAtDestination != null && (movement.getMovementCondition() == MovementCondition.OnlyCapture || movement.getMovementCondition() == MovementCondition.None)) {
					// If it is the same color then the move is not valid
					if (pieceAtDestination.color == piece.color) {
						movement = null;
					}
					// Else the move is capturing the opponent's piece and is valid but the rest are blocked
					else {
						locations.add(movement.getDestination());
						movement = null;
					}
				}
				// Else handle the pawn En Passant move
				else if (movement.getMovementCondition() == MovementCondition.enPassant && pieceAtDestination == null) {
					// Check if the last movement was of a pawn of the opposite color
					if (this.lastMovedPiece instanceof Pawn) {
						if (this.lastMovedPiece.color != piece.color) {
							int lastMovedPawnMovementDistance = Math.abs(this.lastMovedPiecePreviousLocation.row - this.lastMovedPiece.location.row);
							// if the pawn moved twice (which means it was its first move)
							if (lastMovedPawnMovementDistance == 2) {
								int pawnGhostRow;
								int pawnGhostColumn;
								
								// Find the ghost location for the last moved pawn
								if (this.lastMovedPiece.direction == PieceMovementDirection.UpColumn) {
									pawnGhostRow = this.lastMovedPiece.location.row - 1;
									pawnGhostColumn = this.lastMovedPiecePreviousLocation.column;
								}
								else {
									pawnGhostRow = this.lastMovedPiece.location.row + 1;
									pawnGhostColumn = this.lastMovedPiecePreviousLocation.column;
								}
								
								// See if the pawn can capture the ghost opponent pawn
								if (movement.getDestination().row == pawnGhostRow && movement.getDestination().column == pawnGhostColumn) {
									locations.add(movement.getDestination());
									placePiece(null, this.lastMovedPiece.location);
								}
							}
						}
					}
					
					movement = null;
				}
				// Else handle Castling
				else if (movement.getMovementCondition() == MovementCondition.Castling) {
					// Check if the spaces between King and Rook are free
					if (getPiece(movement.getDestination()) == null) {
						PieceMovement secondMovement = movement.getNextMoveInSameDirection();
						PieceMovement thirdMovement = secondMovement.getNextMoveInSameDirection();
						PieceMovement fourthMovement = thirdMovement.getNextMoveInSameDirection();
						
						boolean longerCastling = false;
						boolean isSpaceBetweenKingRookOpen = true;
						ChessPiece pieceAtEnd;
						
						// Check if each space in between King and Rook are free
						if (getPiece(movement.getDestination()) != null) {
							isSpaceBetweenKingRookOpen = false;
						}
						if (getPiece(secondMovement.getDestination()) != null) {
							isSpaceBetweenKingRookOpen = false;
						}
						
						// Check for longer distance Castling
						if (fourthMovement != null) {
							longerCastling = true;
							
							if (getPiece(thirdMovement.getDestination()) != null) {
								isSpaceBetweenKingRookOpen = false;
							}
						}
						
						// Get the piece at the end
						if (longerCastling == false) {
							pieceAtEnd = getPiece(thirdMovement.getDestination());
						}
				else {
							pieceAtEnd = getPiece(fourthMovement.getDestination());
						}
						
						// Check if the piece at the end is a Rook
						if (pieceAtEnd instanceof Rook) {
							Rook rook = (Rook) pieceAtEnd;
							ChessPiece whiteRook2 = new Rook(new PieceLocation(0,7), PieceColor.White, PieceMovementDirection.UpColumn);
							placePiece(whiteRook2,whiteRook2.location);
							// Check if the Rook is the same color as the King and the Rook has no moved
							if (rook.color == piece.color && rook.hasMoved() == false && isSpaceBetweenKingRookOpen == true) {
								locations.add(secondMovement.getDestination());
							}
						}
					}
					
					movement = null;
				}
				else {
					movement = null;
			}
		}
		}
		
		return locations;
	}
	
	/**
	 * Sees if a color set of pieces is in check
	 * @param PieceColor - the color set to check
	 * @return boolean - if it is in check
	 */
	public boolean isInCheck(PieceColor color) {
		// Get each of the opponent's pieces
		ArrayList<ChessPiece> opponentPieces = null;
		if (color == PieceColor.Black) {
			opponentPieces = getPieces(PieceColor.White);
		}
		else {
			opponentPieces = getPieces(PieceColor.Black);
		}
		
		// For each enemy piece on the board, check if it can hit the king in a turn
		for (int i = 0; i < opponentPieces.size(); i++) {
			ChessPiece piece = opponentPieces.get(i);
			ArrayList<PieceLocation> locations = getUnblockedMoveLocations(piece);

			// Check if any location the enemy piece can move contains the king
			for (PieceLocation location: locations) {
				ChessPiece pieceAtLocation = getPiece(location);
				if (pieceAtLocation instanceof King) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Sees if a color set of pieces is in checkmate
	 * @param PieceColor - the color set to check
	 * @return boolean - if it is in check
	 */
	public boolean isInCheckmate(PieceColor color) {
		// Get all of the current color's pieces
		ArrayList<ChessPiece> pieces = null;
		if (color == PieceColor.Black) {
			pieces = getPieces(PieceColor.Black);
		}
		else {
			pieces = getPieces(PieceColor.White);
		}

		// For any piece check if there is at least one move that is valid
		for (ChessPiece piece: pieces) {
			ArrayList<PieceLocation> moveableLocations = getValidMoves(piece);
			if (moveableLocations.size() > 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public ChessPiece getPiece(PieceLocation location) {
		return this.board.get(location.row).get(location.column);
	}
	
	public ChessPiece getPiece(int row, int column) {
		return this.board.get(row).get(column);
	}
	
	/**
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
	
	/**
	 * Moves a piece to a location
	 * @param ChessPiece - the piece to move
	 * @param PieceLocation - the location to move the piece
	 */
	public void movePiece(ChessPiece piece, PieceLocation destination) {
		// Handle opponent's En Passant move
		if (getPiece(destination) == null && piece instanceof Pawn && this.lastMovedPiece != null) {
			// If the pawn moved to the ghost of the last move pawn
			if (this.lastMovedPiece.direction == PieceMovementDirection.UpColumn) {
				if (destination.column == this.lastMovedPiece.location.column && destination.row == this.lastMovedPiece.location.row - 1) {
					placePiece(null, this.lastMovedPiece.location);
				}
			}
			else if (this.lastMovedPiece.direction == PieceMovementDirection.DownColumn) {
				if (destination.column == this.lastMovedPiece.location.column && destination.row == this.lastMovedPiece.location.row + 1) {
					placePiece(null, this.lastMovedPiece.location);
				}
			}
		}
		else if (piece instanceof King) {
			PieceLocation kingStartLocation = piece.location;
			int spacesMoved = Math.abs(destination.column - kingStartLocation.column);
			
			// Check if the King is Castling
			if (spacesMoved == 2) {
				spacesMoved = destination.column - kingStartLocation.column;
				// Check which side the King went
				if (spacesMoved > 0) {
					System.out.println(">>>");
					Rook rook = (Rook) this.board.get(piece.location.row).get(this.MAX_COLUMN - 1);
					PieceLocation castledRookLocation = new PieceLocation();
					castledRookLocation.row = piece.location.row;			
					castledRookLocation.column = destination.column - 1;
					
					movePiece(rook, castledRookLocation);
				}
				else {
					System.out.println("<<<<");
					Rook rook = (Rook) this.board.get(piece.location.row).get(0);
					PieceLocation castledRookLocation = new PieceLocation();
					castledRookLocation.row = piece.location.row;			
					castledRookLocation.column = destination.column + 1;
					
					movePiece(rook, castledRookLocation);
				}
			}
		}
		
		// Move piece
		this.lastMovedPiece = piece;
		this.lastMovedPiecePreviousLocation = piece.location;
		placePiece(piece, destination);
		piece.setLocation(destination);
	}

	/**
	 * Puts a piece at a location without moving the piece functionally
	 * @param ChessPiece - the piece to move
	 * @param PieceLocation - the location to move the piece
	*/
	public void placePiece(ChessPiece piece, PieceLocation destination) {
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

	public void promotePawn(Pawn pawn, String choice){
		if (choice.compareTo("Bishop") == 0){
			ChessPiece bishop = new Bishop(pawn.location, pawn.color, pawn.direction);
			placePiece(bishop, bishop.location);
		}
		else if (choice.compareTo("Knight") == 0){
			ChessPiece knight = new Knight(pawn.location, pawn.color, pawn.direction);
			placePiece(knight, knight.location);
		}
		else if (choice.compareTo("Rook") == 0){
			ChessPiece rook = new Rook(pawn.location, pawn.color, pawn.direction);
			placePiece(rook, rook.location);
		}
		else if (choice.compareTo("Queen") == 0){
			ChessPiece queen = new Queen(pawn.location, pawn.color, pawn.direction);
			placePiece(queen, queen.location);
		}
	}

	public ArrayList<ArrayList<ChessPiece>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<ChessPiece>> board){
		this.board = board;
	}
}
