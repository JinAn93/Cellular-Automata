import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Display is the class that deals with the visual representation of the cells. It is 
 * called by the Time class (with the updateDisplay method).
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class Display {

    private static final int TOTAL_HUE_DEGREES = 360;
	private static final double SATURATION = 0.7;
	private static final double BRIGHTNESS = 1.0;
	private static final int BORDER_SIZE = 10;
	public final static int DISPLAY_WIDTH = 400;
    public final static int DISPLAY_HEIGHT = 400;
    private int COLUMNS;
    private int ROWS;
    private int STATES;
    private Color[] colors;

    private Rectangle[][] Grid;
    /**
     * This class is initialized with a specific number of rows, columns and states so 
     * that it will be flexible and create different sized rectangles each time. 
     * @param rows
     * @param columns
     * @param states
     */
    public Display (int rows, int columns, int states) {
        COLUMNS = columns;
        ROWS = rows;
        STATES = states;
        initDisplay();
    }
    /**
     * This uses the number of rows and columns to calculate each cell's individual dimensions. It 
     * then creates the color array and the rectangle array, setting up their location as well as shape.
     */
    public void initDisplay () {
        int CELL_WIDTH = DISPLAY_WIDTH / ROWS;
        int CELL_HEIGHT = DISPLAY_HEIGHT / COLUMNS;
        initColors();
        Grid = new Rectangle[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Grid[i][j] = new Rectangle();
                Grid[i][j].setStroke(Color.BLACK);
                Grid[i][j].setX(CELL_WIDTH * i + BORDER_SIZE);
                Grid[i][j].setY(CELL_HEIGHT * j + BORDER_SIZE);
                Grid[i][j].setWidth(CELL_WIDTH);
                Grid[i][j].setHeight(CELL_HEIGHT);
            }
        }

    }
    /**
     * Makes the same number of colors as states, so that they will be evenly spaced out on the range of hues
     * (and thus distinguishable). This is to allow for more states to be displayed.
     */
    public void initColors () {
        colors = new Color[STATES];
        for (int k = 0; k < STATES; k++) {
            colors[k] = Color.hsb(k * TOTAL_HUE_DEGREES / STATES, SATURATION, BRIGHTNESS);
        }
    }
    /**
     * Checks the state of each cell in the array, and sets the corresponding rectangle's color to match.
     * Note that i,j go from 1 to length-1 in order to evade the border cells.
     * @param cellGrid
     */
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
