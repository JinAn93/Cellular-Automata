
public class TriDisplay extends Display{
	public TriDisplay (int rows, int columns, int states) {
		super(rows, columns, states);
		CELL_WIDTH = DISPLAY_WIDTH /Math.ceil(getColumns()/2.) ; 
		CELL_HEIGHT = DISPLAY_HEIGHT / getRows();
	}

	@Override
	public void makeShape(int i, int j) {


		double k = (j)/2.;
		//System.out.println(k);
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
