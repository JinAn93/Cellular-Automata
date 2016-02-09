import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriDisplay extends Display{
	public TriDisplay(int rows, int columns, int states) {
		super(rows, columns, states);
		// TODO Auto-generated constructor stub
	}

	 public void initDisplay () {
	        int CELL_WIDTH = DISPLAY_WIDTH / ROWS;
	        int CELL_HEIGHT = DISPLAY_HEIGHT / COLUMNS;
	        initColors();
	        Grid = new Polygon[ROWS][COLUMNS];
	        for (int i = 0; i < ROWS; i++) {
	            for (int j = 0; j < COLUMNS; j++) {
	                Grid[i][j] = new Polygon();
	                Grid[i][j].setStroke(Color.BLACK);
	                double k = i+.5*j%2;
	                if(i%2==0){
	                Grid[i][j].getPoints().addAll(new Double[]{
	                		(CELL_WIDTH * k + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET),
	                		(CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
	                	    (CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET) });
	                }
	                else{
	                	Grid[i][j].getPoints().addAll(new Double[]{
		                		(CELL_WIDTH * (k-.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
		                		(CELL_WIDTH * (k) + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET),
		                	    (CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET) });
	                }
	            }
	        }

	    }
	
	

}
