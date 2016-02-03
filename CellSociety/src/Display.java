
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Display {
//	TODO:check constants and decide on values
	public final static int DISPLAY_WIDTH = 400;
	public final static int DISPLAY_HEIGHT= 400;
	private int COLUMNS;
	private int ROWS;
	private int STATES;
	private Color[] colors;
	
	private Rectangle[][] Grid;
	
	public Display(int rows, int columns, int states){
		COLUMNS = columns;
		ROWS = rows;
		STATES = states;
		initDisplay();
	}
	
	public void initDisplay(){
		int CELL_WIDTH = DISPLAY_WIDTH/COLUMNS;
		int CELL_HEIGHT = DISPLAY_HEIGHT/ROWS;
		
		Grid = new Rectangle[COLUMNS][ROWS];
		for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
            	Grid[i][j] = new Rectangle();
            	Grid[i][j].setX(CELL_WIDTH*i);
            	Grid[i][j].setY(CELL_HEIGHT*j);
            	Grid[i][j].setWidth(CELL_WIDTH);
            	Grid[i][j].setHeight(CELL_HEIGHT);
            	Grid[i][j].setFill(Color.rgb(i*255/COLUMNS, j*255/ROWS, 0));
            }
		}
		Color[] colors = new Color[STATES];
		for (int k=0; k<STATES; k++){
			colors[k] = Color.hsb(k*360/STATES, 1.0, 1.0);
		}
	}

	public void updateDisplay(Cell[][] cellGrid){
        for (int i = 0; i < cellGrid.length-2; i++) {
            for (int j = 0; j < cellGrid[0].length-2; j++) {
                Grid[i][j].setFill(colors[cellGrid[i+1][j+1].getCurrState()-1]); 		
            }
        }
	}
	public Shape[][] getDisplay(){
		return Grid;
	}

}
