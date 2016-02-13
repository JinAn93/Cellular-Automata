import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;

/**
 * Graph is the class that graphs the values of each type of cell. This class is used by Time, and is passed on to the interface.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class Graph {

	private List<XYChart.Series<Number, Number> > values;
	private int numStates;
	private int count;
	private Color[] colors;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String STATES = "States";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STATES);
	private String myName;
	
	public Graph(Color[] colors, String name){
		this.colors = colors;
		numStates = colors.length;
		myName=name;
		init();
	}

	/**
	 * Sets up the graph by making the same number of XYCharts as states. Also sets the x values to 0 (count)
	 */
	private void init(){
		values = new ArrayList<XYChart.Series<Number, Number>>();
		for (int i = 0; i<numStates; i++){
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();

			series.setName(myResources.getString(myName+i));


			values.add(series);
		}
		count =0;
	}
	/**
	 * Takes in a cell array and counts the number of instances of each type of state. 
	 * Adds these values to the line, with count as x, and updates count.
	 * @param cellGrid
	 */
	public void updateValues(Cell[][] cellGrid){
		int[] stateTotals = new int[numStates];
		for (int i = 1; i < cellGrid.length - 1; i++) {
			for (int j = 1; j < cellGrid[0].length - 1; j++) {
				stateTotals[cellGrid[i][j].getCurrState()]+=1;
			}
		}
		double total = (cellGrid.length - 1)*(cellGrid[0].length - 1);
		for (int i = 0; i<numStates; i++){
			values.get(i).getData().add(new Data<Number, Number>(count, stateTotals[i]/total));
		}
		count +=1;
	}

	/**
	 * makes the actual linechart. This is used by the time class which passes it to the interface.
	 * @return
	 */
	public LineChart<Number, Number> getGraph(){
		LineChart<Number, Number> lineChart = 
				new LineChart<Number,Number>(new NumberAxis(),new NumberAxis());
		lineChart.getData().addAll(values);
		lineChart.setCreateSymbols(false);
		Set<Node> symbolNode = lineChart.lookupAll(".chart-legend-item");		
		Iterator<Node> iter = symbolNode.iterator();
		for (int i = 0; i<numStates; i++){
			Node lineNode = lineChart.lookup(".series"+i);
			lineNode.setStyle("-fx-stroke: "+getHex(colors[i])+";");
			Node c = iter.next();
			c.setStyle("-fx-background-color: "+getHex(colors[i])+";");
			c.setStyle("-fx-background-color: "+getHex(colors[i])+";");
		}

		return lineChart;
	}

	private String getHex(Color c) {
		String hex = String.format( "#%02X%02X%02X",
				(int)( c.getRed() * 255 ),
				(int)( c.getGreen() * 255 ),
				(int)( c.getBlue() * 255 ) );
		return hex;
	}

}



