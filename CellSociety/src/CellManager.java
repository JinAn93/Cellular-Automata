import java.util.*;

public class CellManager {
    private Cell[][] cellGrid;
    private SimulationRules sim1 = new SegregationRules();
    private SimulationRules sim2 = new PredatorPreyRules();
    private SimulationRules sim3 = new SpreadingFireRules();
    private SimulationRules sim4 = new GameOfLifeRules();
    private int myWhichsim;
    
    public void setUp(int r, int c, int whichsim){
        cellGrid = new Cell[r+2][c+2];
        this.myWhichsim = whichsim;
        for(int i=0; i<r+2; i++){
        	for(int j=0; j<c+2; j++){
        		if(i==0 || i == r+1){
        			cellGrid[i][j].setCurrState(-1);
            		cellGrid[i][j].setNextState(-1);
        		}
        		else{
        			cellGrid[i][0].setCurrState(-1);
        			cellGrid[i][0].setNextState(-1);
        			cellGrid[i][c+1].setCurrState(-1);
        			cellGrid[i][c+1].setNextState(-1);
        		}		
        	}
        }
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
