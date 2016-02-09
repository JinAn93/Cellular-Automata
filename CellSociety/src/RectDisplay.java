import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class RectDisplay extends Display {

    public RectDisplay (int rows, int columns, int states) {
        super(rows, columns, states);
    }

    @Override
    public void initDisplay () {
        int CELL_WIDTH = DISPLAY_WIDTH / getRows();
        int CELL_HEIGHT = DISPLAY_HEIGHT / getColumns();
        initColors();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                getDisplay()[i][j] = new Polygon();
                getDisplay()[i][j].setStroke(Color.BLACK);
                double x = CELL_WIDTH * i + X_OFFSET;
                double y = CELL_HEIGHT * j + Y_OFFSET;
                getDisplay()[i][j].getPoints()
                        .addAll(new Double[] { x, y, x + CELL_WIDTH, y,
                                               x + CELL_WIDTH, y + CELL_HEIGHT, x,
                                               y + CELL_HEIGHT });
            }
        }

    }

}
