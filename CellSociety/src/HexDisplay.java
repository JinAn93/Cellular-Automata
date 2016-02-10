
public class HexDisplay extends Display{

	public HexDisplay (int rows, int columns, int states) {
		super(rows, columns, states);
		CELL_WIDTH = DISPLAY_WIDTH / (getColumns())*4/3;
		CELL_HEIGHT = DISPLAY_HEIGHT / (getRows()+.5);
	}

	@Override
	public void makeShape(int i, int j) {
		double k = j*.75;
		double l = i+.5*(j%2);

		getDisplay()[i][j].getPoints().addAll(new Double[]{
				(CELL_WIDTH * (k) + X_OFFSET), (CELL_HEIGHT * (l+.5) + Y_OFFSET),
				(CELL_WIDTH * (k+.25) + X_OFFSET), (CELL_HEIGHT * (l) + Y_OFFSET),
				(CELL_WIDTH * (k+.75) + X_OFFSET), (CELL_HEIGHT * (l) + Y_OFFSET), 
				(CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * (l+.5) + Y_OFFSET),
				(CELL_WIDTH * (k+.75) + X_OFFSET), (CELL_HEIGHT * (l+1) + Y_OFFSET),
				(CELL_WIDTH * (k+.25) + X_OFFSET), (CELL_HEIGHT * (l+1) + Y_OFFSET)

		});

	}


}
