public class MovementMade {

    public PieceLocation from;
    public PieceLocation destination;

    public MovementMade(){}
    public MovementMade(PieceLocation from, PieceLocation destination){
        this.from = from;
        this.destination = destination;
    }
}
