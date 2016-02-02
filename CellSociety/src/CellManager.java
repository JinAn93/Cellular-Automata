import java.util.*;

public class CellManager {
    private Cell[][] cellGrid;
    private SimulationRules sim1 = new SegregationRules();
    private SimulationRules sim2 = new PredatorPreyRules();
    private SimulationRules sim3 = new SpreadingFireRules();
    private SimulationRules sim4 = new GameOfLifeRules();
    
    public void generateList(int n){
        cellGrid = new Cell[n+2][n+2];
        for(int i=0; i<n+2; i++){
        	cellGrid[0][i].setCurrState(-1);
        	cellGrid[0][i].setNextState(-1);
        	cellGrid[i][0].setCurrState(-1);
        	cellGrid[i][0].setNextState(-1);
        	cellGrid[i][n+1].setCurrState(-1);
        	cellGrid[i][n+1].setNextState(-1);
        	cellGrid[n+1][i].setCurrState(-1);
        	cellGrid[n+i][i].setCurrState(-1);
        }

    public Cell[][] getCellList(){
        return cellGrid;
    }
    
    public void updateStates(int whichSim){
    	if(whichSim == 1)
    		sim1.applyRule(cellGrid);
    	if(whichSim == 2)
    		sim2.applyRule(cellGrid);
    	if(whichSim == 3)
    		sim3.applyRule(cellGrid);
    	if(whichSim == 4)
    		sim4.applyRule(cellGrid);
    }
    
    public void moveNextToCurrentState(){
    	for(int i=1; i<cellGrid.length-1; i++){
    		for(int j=1; j<cellGrid[0].length; j++){
    			cellGrid[i][j].setCurrState(cellGrid[i][j].getNextState());
    			cellGrid[i][j].setNextState(0);
    		}
    	}
    }
}
