import java.util.*;

/**
 * CellManager serves to manage cellGrid from setting up to updating states for each cell. All the methods
 * in the class is called only from Time class. cellGrid is encapsulated as its contents can be changed only
 * within the class. public getCellList method is used to get cellGrid from outside class. myPossibleRules
 * contains all the possible Rule subclasses. Depending on the user input, it applies the specific rule in
 * updateStates method.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class CellManager {
    private Cell[][] cellGrid;
    private SimulationRules[] myPossibleRules =
    { new SegregationRules(), new PredatorPreyRules(), new SpreadingFireRules(),
     new GameOfLifeRules() };
    private SimulationRules myRules;

    /**
     * setUp method is called from initSimulation method in Time class. It takes in # of rows, columns, and
     * all the relevant information extracted from XML File. By creating cellGrid larger (by 2 for both column
     * and row), we avoid the NullPointer error. Also, setting all the edge cell's current and next states to
     * BLOCKED constant (= -1) allows program to detect the four edges. This method calls setSimulationParameters
     * and fillCellgrid methods in SimulationRules class.
     *  
     * @param r
     * @param c
     * @param whichSim
     * @param entry
     * @param simParams
     */
    public void setUp (int r, int c, int whichSim, int[] entry, String[] simParams) {
        myRules = myPossibleRules[whichSim];
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
        myRules.fillCellgrid(cellGrid, entry);
    }

    public Cell[][] getCellList () {
        return cellGrid;
    }

    public void updateStates () {
        myRules.applyRule(cellGrid);
    }

    /**
     * As cellGrid is iterated, its current state is updated with next state. Then, next state is initialized
     * to OPEN_Next (= -2) to notify the program that they have not been touched.
     */
    public void moveNextToCurrentState () {
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                cellGrid[i][j].setCurrState(cellGrid[i][j].getNextState());
                cellGrid[i][j].setNextState(SimulationRules.OPEN_NEXT);

            }
        }
    }

}
