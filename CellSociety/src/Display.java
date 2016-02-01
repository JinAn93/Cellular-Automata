
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Display {
	private int DISPLAY_WIDTH = 300;
	private int DISPLAY_HEIGHT= 300;
	private int CELL_WIDTH;
	private int CELL_HEIGHT;
	private Color[] colors;
	
	private Rectangle[][] Grid;
	
	
	public void initDisplay(int rows, int columns){
		CELL_WIDTH = DISPLAY_WIDTH/columns;
		CELL_HEIGHT = DISPLAY_HEIGHT/rows;
		
		Rectangle[][] Grid = new Rectangle[columns][rows];
		for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
            	Grid[i][j] = new Rectangle();
            	Grid[i][j].setX(CELL_WIDTH*i);
            	Grid[i][j].setY(CELL_HEIGHT*j);
            	Grid[i][j].setWidth(CELL_WIDTH);
            	Grid[i][j].setHeight(CELL_HEIGHT);
            }
		}	
	}

	public void updateDisplay(Cell[][] cellGrid){
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                Grid[i][j].setFill(colors[cellGrid[i][j].getCurrState()]); 		
            }
        }
	}

}
