import java.util.ArrayList;

public class Chess {

	public static void main(String[] args) {
		PieceLocation loc = new PieceLocation(7,3);
		ChessPiece p = new Knight(loc, PieceColor.Black, PieceMovementDirection.UpColumn);
		//p.move(new PieceLocation(5,4));
		//print(p.getListOfMoves());
		
		
		Chessboard board = new Chessboard();
		
		
		
		
		
		
		
		
	}
	
	public static void print(ArrayList<PieceMovement> movements) {
		for (int i = 0; i < movements.size(); i++) {
			if (movements.get(i) != null) {
				innterPrint(movements.get(i));
				System.out.println("");
			}
		}
	}
	
	public static void innterPrint(PieceMovement move) {
		PieceLocation loc = move.getDestination();
		String l = loc.row + "," + loc.column;
		System.out.println(l);
		if (move.getNextMoveInSameDirection() != null) {
			innterPrint(move.getNextMoveInSameDirection());
		}
	}

}
