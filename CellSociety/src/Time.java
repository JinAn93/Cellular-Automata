import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.shape.Polygon;
import javafx.util.*;
import java.util.*;
import javafx.animation.KeyFrame;

/**
 * Time is the class that controls the animation. It creates a CellManager and a Display object, and
 * steps through the simulations using a Timeline object. This class is used by UserInterface.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class Time {

	public static final List<String> simulations = Arrays.asList("Segregation", "Predator_Prey",
			"Spreading_Fire", "Game_of_Life");
	private static final Duration STEPTIME = new Duration(1000);
	private Timeline timeline;
	private CellManager Cells;
	private Display cellDisplay;
	private double speed = 1;
	private Display[] cellDisplayArray;
	private Graph cellGraph; 	
	private int numStates;
	
	/**
	 * creates new cellmanager, display, and timeline objects. Uses getcellList in cellmanager to pass 
	 * the updated states into Display celldisplay. Makes an indefinitely long timeline that "steps".
	 */

	public void initSimulation (int row, int column, int numStates, String name, int[] initial, String[] params, int shape, int edge) {
		this.numStates = numStates;
		
		Cells = new CellManager();
		cellDisplayArray = new Display[]{new RectDisplay(row,column,numStates), new TriDisplay(row,column,numStates), new HexDisplay(row,column,numStates)};
		cellDisplay = cellDisplayArray[shape];
		Cells.setUp(row, column, simulations.lastIndexOf(name), initial, params, shape, edge);
		cellDisplay.updateDisplay(Cells.getCellList());
		
		cellGraph = new Graph(numStates);
		cellGraph.updateValues(Cells.getCellList());
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(STEPTIME,
				e -> step()));
	}
	/**
	 * This updates cells (the cellmanager) and celldisplay (the display) using their methods for this.
	 */
	private void step () {
		Cells.updateStates();
		Cells.moveNextToCurrentState();
		cellDisplay.updateDisplay(Cells.getCellList());

		cellGraph.updateValues(Cells.getCellList());

	}

	
	
	public double getSpeed () {
		return speed;
	}

	public void setSpeed (double s) {
		timeline.setRate(s);
		speed = s;
	}

	public void startAnimation () {
		timeline.playFromStart();
	}

	public void pauseAnimation () {
		timeline.pause();
	}

	public void resumeAnimation () {
		timeline.play();
	}

	public void stepAnimation () {
		timeline.pause();
		step();
	}
	/**
	 * Returns a list of shapes (as opposed to a 2-D array) so that these can be added to the root group in interface 
	 * @return
	 */
	public Polygon[][] getCellDisplay () {
		return cellDisplay.getDisplay();
	}

	public LineChart<Number, Number> getCellGraph(){
		return cellGraph.getGraph();
	}
	
	public void changeState(int i, int j) {
		Cell c = Cells.getCellList()[i+1][j+1];
		c.setCurrState((c.getCurrState()+1)%numStates);
		cellDisplay.updateDisplay(Cells.getCellList());

	}
	public void updateParams(String[] myParams) {
		Cells.setParams(myParams);
		
	}

}
