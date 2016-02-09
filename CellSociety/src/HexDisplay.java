import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexDisplay extends Display{

    public HexDisplay (int rows, int columns, int states) {
        super(rows, columns, states);
    }

    @Override
    public void initDisplay () {
    	double CELL_WIDTH = DISPLAY_WIDTH / getRows()*4/3;
		double CELL_HEIGHT = DISPLAY_HEIGHT / (getColumns()+.5);
		initColors();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				getDisplay()[i][j] = new Polygon();
				getDisplay()[i][j].setStroke(Color.BLACK);
				double k = i*.75;
				double l = j+.5*(i%2);
				
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
    }


}
