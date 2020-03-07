import jdk.jshell.spi.SPIResolutionException;

public class PieceLocation {
	public int row;
	public int column;

	public PieceLocation(){}
	public PieceLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void SetRow(int row)
	{
		this.row = row;
	}

	public void SetColumn(int column)
	{
		this.column = column;
	}

	public int GetRow()
	{
		return this.row;
	}

	public int GetColumn()
	{
		return this.column;
	}
}
