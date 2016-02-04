import java.util.*;


public class CellManager {
    private Cell[][] cellGrid;
    private SimulationRules[] myPossibleRules =
            { new SegregationRules(), new PredatorPreyRules(), new SpreadingFireRules(),
              new GameOfLifeRules() };
    private SimulationRules myRules;

    public void setUp (int r, int c, int whichSim, int[] entry, String[] simParams) {
        myRules = myPossibleRules[whichSim];  // Possible error here, may not be -1
        cellGrid = new Cell[r + 2][c + 2];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
            	cellGrid[i][j] = new Cell();
            	if (i == 0 || i == r + 1) {
                    cellGrid[i][j].setCurrState(SimulationRules.BLOCKED);
                    cellGrid[i][j].setNextState(SimulationRules.BLOCKED);
                }
            }
            cellGrid[i][0].setCurrState(SimulationRules.BLOCKED);
            cellGrid[i][0].setNextState(SimulationRules.BLOCKED);
            cellGrid[i][c + 1].setCurrState(SimulationRules.BLOCKED);
            cellGrid[i][c + 1].setNextState(SimulationRules.BLOCKED);
        }
        myRules.setSimulationParameters(simParams);
        myRules.fillCellgrid(cellGrid,entry);        
    }

    public Cell[][] getCellList () {
        return cellGrid;
    }

    public void updateStates () {
        myRules.applyRule(cellGrid);
    }	

    public void moveNextToCurrentState () {
        for (int i = 1; i < cellGrid.length-1; i++) {
            for (int j = 1; j < cellGrid[0].length-1; j++) {
                    cellGrid[i][j].setCurrState(cellGrid[i][j].getNextState());
                    cellGrid[i][j].setNextState(SimulationRules.EMPTY);
                
            }
        }
    }

}
