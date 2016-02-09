import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class Graph {

	private List<XYChart.Series<Number, Number> > values;
	private int numStates;
	private int count;

	public Graph(int numStates){
		this.numStates = numStates;
		init();
	}
	private void init(){
		values = new ArrayList<XYChart.Series<Number, Number>>();
		for (int i = 0; i<numStates; i++){
			values.add(new XYChart.Series<Number, Number>());
		}
		count =0;
	}
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
	
	public LineChart<Number, Number> getGraph(){
		LineChart<Number, Number> lineChart = 
                new LineChart<Number,Number>(new NumberAxis(),new NumberAxis());
        lineChart.getData().addAll(values);
        return lineChart;
	}
}



