import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriDisplay extends Display{
    public TriDisplay (int rows, int columns, int states) {
        super(rows, columns, states);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initDisplay () {
        // TODO Auto-generated method stub
    	double CELL_WIDTH = DISPLAY_WIDTH / Math.ceil(getRows()/2.); 
        double CELL_HEIGHT = DISPLAY_HEIGHT / getColumns();
        initColors();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                getDisplay()[i][j] = new Polygon();
                getDisplay()[i][j].setStroke(Color.BLACK);
                double k = (i)/2.;
                //System.out.println(k);
                if((i+j)%2==0){
                	getDisplay()[i][j].getPoints().addAll(new Double[]{
                		(CELL_WIDTH * k + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET),
                		(CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
                	    (CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET) });
                }
                else{
                	getDisplay()[i][j].getPoints().addAll(new Double[]{
	                		(CELL_WIDTH * (k) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET),
	                		(CELL_WIDTH * (k+.5) + X_OFFSET), (CELL_HEIGHT * j + Y_OFFSET),
	                	    (CELL_WIDTH * (k+1) + X_OFFSET), (CELL_HEIGHT * (j+1) + Y_OFFSET) });
                }
            }
        }
        
    }

}
