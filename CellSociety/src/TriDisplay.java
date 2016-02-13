
public class TriDisplay extends Display{
	public TriDisplay (int rows, int columns, int states) {
		super(rows, columns, states);

	}
	/**
	 * cell width is half of columns (and rounded up) because 2n+1 triangles occupy the same space as n rectangles.
	 * The triangles' orientation is determined by its i,j
	 */
	@Override
	protected void makeShape(int i, int j) {
		double CELL_WIDTH = DISPLAY_WIDTH /Math.ceil(getColumns()/2.) ; 
		double CELL_HEIGHT = DISPLAY_HEIGHT / getRows();

		double k = (j)/2.;
		if((i+j)%2==0){
			getDisplay()[i][j].getPoints().addAll(new Double[]{
					(CELL_WIDTH * k + X_OFFSET), (CELL_HEIGHT * i + Y_OFFSET),
					(CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * (i+1) + Y_OFFSET),
					(CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * i + Y_OFFSET) });
		}
		else{
			getDisplay()[i][j].getPoints().addAll(new Double[]{
					(CELL_WIDTH * (k) + X_OFFSET), (CELL_HEIGHT * (i+1) + Y_OFFSET),
					(CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * i + Y_OFFSET),
					(CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * (i+1) + Y_OFFSET) });
		}
	}
}
