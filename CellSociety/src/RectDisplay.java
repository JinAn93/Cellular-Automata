
public class RectDisplay extends Display {
	public RectDisplay (int rows, int columns, int states) {
		super(rows, columns, states);
	}

	@Override
	protected  void makeShape(int i, int j) {
		double CELL_WIDTH = DISPLAY_WIDTH / getColumns();
		double CELL_HEIGHT = DISPLAY_HEIGHT / getRows();
		double x = CELL_WIDTH * j + X_OFFSET;
		double y = CELL_HEIGHT * i + Y_OFFSET;
		getDisplay()[i][j].getPoints()
		.addAll(new Double[] { x, y, x + CELL_WIDTH, y,
				x + CELL_WIDTH, y + CELL_HEIGHT, x,
				y + CELL_HEIGHT });


	}

}
