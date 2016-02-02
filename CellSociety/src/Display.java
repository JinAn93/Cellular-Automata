
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Display {
	private int DISPLAY_WIDTH = 300;
	private int DISPLAY_HEIGHT= 300;
	private Color[] colors;
	
	private Shape[][] Grid;
	
	
	public void initDisplay(int rows, int columns, int states){
		int CELL_WIDTH = DISPLAY_WIDTH/columns;
		int CELL_HEIGHT = DISPLAY_HEIGHT/rows;
		
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
		Color[] colors = new Color[states];
		for (int k=0; k<states; k++){
			colors[k] = Color.hsb(k*360/states, 1.0, 1.0);
		}
	}

	public void updateDisplay(Cell[][] cellGrid){
        for (int i = 0; i < cellGrid.length-2; i++) {
            for (int j = 0; j < cellGrid[0].length-2; j++) {
                Grid[i][j].setFill(colors[cellGrid[i+1][j+1].getCurrState()]); 		
            }
        }
	}

}
