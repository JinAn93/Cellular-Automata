import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


/**
 * Display is the class that deals with the visual representation of the cells. It is 
 * called by the Time class (with the updateDisplay method).
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public abstract class Display {

    private static final int TOTAL_HUE_DEGREES = 360;
	private static final double SATURATION = 0.7;
	private static final double BRIGHTNESS = 1.0;
	protected static final double X_OFFSET = 10;
	protected static final double Y_OFFSET = 210;
	public final static int DISPLAY_WIDTH = 400;
    public final static int DISPLAY_HEIGHT = 400;
    private int columns;
    private int rows;
    private int states;
    private Color[] colors;


    private Polygon[][] Grid; 
    /**
     * This class is initialized with a specific number of rows, columns and states so 
     * that it will be flexible and create different sized shapes each time. 
     * @param rows
     * @param columns
     * @param states
     */
    public Display (int rows, int columns, int states) {
        this.columns = columns;
        this.rows = rows;
        this.states = states;
        Grid = new Polygon[rows][columns];
        initDisplay();
    }
    /**
     * This creates the color array and the shape array, setting up their location as well as shape.
     */
    public void initDisplay (){
    	initColors();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Grid[i][j] = new Polygon();
                Grid[i][j].setStroke(Color.BLACK);
                makeShape(i,j);
            }
        }
    }
    /**
     * this is the method that creates the individual shapes, which are different for each type of display.
     * @param i
     * @param j
     */
    protected abstract void makeShape(int i, int j);
            
            
    /**
     * Makes the same number of colors as states, so that they will be evenly spaced out on the range of hues
     * (and thus distinguishable). This is to allow for more states to be displayed.
     */
    public void initColors () {
        colors = new Color[states];
        for (int k = 0; k < states; k++) {
            colors[k] = Color.hsb(k * TOTAL_HUE_DEGREES / states, SATURATION, BRIGHTNESS);
        }
    }
    /**
     * Checks the state of each cell in the array, and sets the corresponding polygon's color to match.
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

    public Polygon[][] getDisplay () {
        return Grid;
    }
    public int getColumns () {
        return columns;
    }
    public void setColumns (int columns) {
        this.columns = columns;
    }
    public int getRows () {
        return rows;
    }
    public void setRows (int rows) {
        this.rows = rows;
    }
    public int getStates () {
        return states;
    }
    public void setStates (int states) {
        this.states = states;
    }
    public Color[] getColors(){
    	return colors;
    }
    
}
