
public class FirebaseData {
    public Boolean isWhiteTurn;
    public MovementMade movementMade;
    public Boolean player1Connected;
    public Boolean player2Connected;

    public FirebaseData() {}
    public FirebaseData(Boolean isWhiteTurn, MovementMade movementMade, Boolean player1Connected, Boolean player2Connected){
        this.isWhiteTurn = isWhiteTurn;
        this.movementMade = movementMade;
        this.player1Connected = player1Connected;
        this.player2Connected = player2Connected;
    }

    public Boolean GetIsWhiteTurn(){
        return this.isWhiteTurn;
    }

    public MovementMade getMovementMade(){
        return this.movementMade;
    }

    public void SetIsWhiteTurn(Boolean isWhiteTurn){
        this.isWhiteTurn = isWhiteTurn;
    }

    public void SetMovementMade(MovementMade movementMade){
        this.movementMade = movementMade;
    }
}
