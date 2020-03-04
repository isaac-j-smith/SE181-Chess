import java.util.ArrayList;

public class PieceMovement {
	private PieceLocation destination;
	private boolean onlyAttackMovement;
	private PieceMovement nextMoveInSameDirection;
	
	public PieceMovement(PieceLocation destination, boolean onlyAttackMovement, PieceMovement nextMoveInSameDirection) {
		this.destination = destination;
		this.onlyAttackMovement = onlyAttackMovement;
		this.nextMoveInSameDirection = nextMoveInSameDirection;
	}
	
	public PieceLocation getDestination() {
		return this.destination;
	}
	
	public boolean isOnlyAttackMovement() {
		return this.onlyAttackMovement;
	}
	
	public PieceMovement getNextMoveInSameDirection() {
		return this.nextMoveInSameDirection;
	}
	
	public void removeNextMoveInSameDirection() {
		this.nextMoveInSameDirection = null;
	}
}
