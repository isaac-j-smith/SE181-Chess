import java.util.ArrayList;

public class PieceMovement {
	private PieceLocation destination;
	private MovementCondition movementCondition;
	private PieceMovement nextMoveInSameDirection;
	
	public PieceMovement(PieceLocation destination, MovementCondition movementConditions, PieceMovement nextMoveInSameDirection) {
		this.destination = destination;
		this.movementCondition = movementConditions;
		this.nextMoveInSameDirection = nextMoveInSameDirection;
	}
	
	public PieceLocation getDestination() {
		return this.destination;
	}
	
	public MovementCondition getMovementCondition() {
		return this.movementCondition;
	}
	
	public PieceMovement getNextMoveInSameDirection() {
		return this.nextMoveInSameDirection;
	}
	
	public void removeNextMoveInSameDirection() {
		this.nextMoveInSameDirection = null;
	}
}
