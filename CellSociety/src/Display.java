import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Display {

    // TODO:check constants and decide on values
    public final static int DISPLAY_WIDTH = 400;
    public final static int DISPLAY_HEIGHT = 400;
    private int COLUMNS;
    private int ROWS;
    private int STATES;
    private Color[] colors;

    private Rectangle[][] Grid;

    public Display (int rows, int columns, int states) {
        COLUMNS = columns;
        ROWS = rows;
        STATES = states;
        // System.out.println("GUI Stuff " + COLUMNS + " " + ROWS + " " + STATES);

        initDisplay();
    }

    public void initDisplay () {
        int CELL_WIDTH = DISPLAY_WIDTH / ROWS;
        int CELL_HEIGHT = DISPLAY_HEIGHT / COLUMNS;
        initColors();
        Grid = new Rectangle[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Grid[i][j] = new Rectangle();
                Grid[i][j].setStroke(Color.BLACK);
                Grid[i][j].setX(CELL_WIDTH * i + 10);
                Grid[i][j].setY(CELL_HEIGHT * j + 10);
                Grid[i][j].setWidth(CELL_WIDTH);
                Grid[i][j].setHeight(CELL_HEIGHT);
                // Grid[i][j].setFill(colors[(i+j)%STATES]); -this is just for debugging
            }
        }

    }

    public void initColors () {
        colors = new Color[STATES];
        for (int k = 0; k < STATES; k++) {
            colors[k] = Color.hsb(k * 360 / STATES, 1.0, 1.0);
        }
    }

    public void updateDisplay (Cell[][] cellGrid) {
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                Grid[i - 1][j - 1].setFill(colors[cellGrid[i][j].getCurrState()]);
            }
        }
    }

    public Shape[][] getDisplay () {
        return Grid;
    }

}
