import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexDisplay extends Display{

    public HexDisplay (int rows, int columns, int states) {
        super(rows, columns, states);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initDisplay () {
        // TODO Auto-generated method stub
    	int CELL_WIDTH = DISPLAY_WIDTH / getRows();
		int CELL_HEIGHT = DISPLAY_HEIGHT / getColumns();
		initColors();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				getDisplay()[i][j] = new Polygon();
				getDisplay()[i][j].setStroke(Color.BLACK);
				double k = i+.5*j%2;
				getDisplay()[i][j].getPoints().addAll(new Double[]{
						(CELL_WIDTH * i + X_OFFSET), (CELL_HEIGHT * (j+.25) + Y_OFFSET),
						(CELL_WIDTH * (i+.5) + X_OFFSET), (CELL_HEIGHT * (j) + Y_OFFSET),
						(CELL_WIDTH * (i+1) + X_OFFSET), (CELL_HEIGHT * (j+.25) + Y_OFFSET), 
						(CELL_WIDTH * i + X_OFFSET), (CELL_HEIGHT * (j+.75) + Y_OFFSET),
						(CELL_WIDTH * (i+.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
						(CELL_WIDTH * (i+1) + X_OFFSET), (CELL_HEIGHT * (j+.75) + Y_OFFSET)  });

			}
		}
    }


}
