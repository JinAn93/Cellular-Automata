import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexDisplay {

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
				Grid[i][j].getPoints().addAll(new Double[]{
						(CELL_WIDTH * i + X_OFFSET), (CELL_HEIGHT * (j+.25) + Y_OFFSET),
						(CELL_WIDTH * (i+.5) + X_OFFSET), (CELL_HEIGHT * (j) + Y_OFFSET),
						(CELL_WIDTH * (i+1) + X_OFFSET), (CELL_HEIGHT * (j+.25) + Y_OFFSET) 
						(CELL_WIDTH * i + X_OFFSET), (CELL_HEIGHT * (j+.75) + Y_OFFSET),
						(CELL_WIDTH * (i+.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
						(CELL_WIDTH * (i+1) + X_OFFSET), (CELL_HEIGHT * (j+.75) + Y_OFFSET)  });

			}
		}

	}

}
