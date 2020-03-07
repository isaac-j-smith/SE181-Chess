public class MovementMade {

    public PieceLocation from;
    public PieceLocation destination;

    public MovementMade(){}
    public MovementMade(PieceLocation from, PieceLocation destination){
        this.from = from;
        this.destination = destination;
    }

    public void SetFrom(PieceLocation from)
    {
        this.from = from;
    }

    public void SetDestination(PieceLocation destination)
    {
        this.destination = destination;
    }

    public PieceLocation GetDestination()
    {
        return this.destination;
    }

    public PieceLocation GetFrom()
    {
        return this.from;
    }




}
