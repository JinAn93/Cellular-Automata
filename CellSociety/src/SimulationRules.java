import java.util.*;


/**
 * SimulationRules serves to provide the basic methods and functionality for the implementation
 * of each simulation. Many of its methods interact with CellManager's grid of cells that
 * represents the state of every active cell in the simulation at any given point. This class
 * is to be inherited by the individual rules subclasses, one for each type of simulation that the
 * program can run, where more specific methods and properties are defined.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public abstract class SimulationRules {

    public static final int OPEN_NEXT = -2;
    public static final int BLOCKED = -1;
    public static final int EMPTY = 0;

    public SimulationRules () {
    }

    /**
     * Called at every step of the simulation's Timeline. This method iterates through CellManager's
     * grid of cells to determine and set the next state of each cell in the simulation of every
     * active cell.
     * 
     * @param cellGrid
     */
    protected void applyRule (Cell[][] cellGrid, int shape) {
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                if (checkNextState(cellGrid[i][j], OPEN_NEXT)) {
                    System.out.println(cellGrid[i][j].getCurrState());
                    Cell[] neighbors = setNeighbors(cellGrid, i, j,shape);


                    int nextState = findNextState(cellGrid[i][j], neighbors,
                                                  cellGrid,shape);
                    cellGrid[i][j].setNextState(nextState);
                }
            }
        }
    }

    /**
     * Initializes CellManager's cell grid with the initial states of every cell in the simulation,
     * as defined by the XML configuration file. Also sets the next state property of each cell to
     * OPEN_NEXT as an initial value.
     * 
     * @param cellGrid
     * @param entry
     */
    public void fillCellgrid (Cell[][] cellGrid, int[] entry) {
        int count = 0;
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                System.out.println(entry[count]);
                cellGrid[i][j].setCurrState(entry[count]);
                cellGrid[i][j].setNextState(OPEN_NEXT);
                count++;
            }
        }
    }

    /**
     * Abstract method to be implemented in each SimulationRules subclass. Specific implementation
     * represents the way that updates are handled according to each simulation's set of rules.
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @return -int representing next state of cell
     */
    protected abstract int findNextState (Cell curr,
                                          Cell[] neighbors,
                                          Cell[][] grid, int shape);

    /**
     * Abstract method to be implemented by each specific SimulationRules subclass. Sets constants
     * of simulation based on simParams, a string array read in from the XML configuration file.
     * 
     * @param simParams
     */
    protected abstract void setSimulationParameters (String[] simParams);

    protected abstract boolean isInvalid (String[] simParams);

    protected Random getRand () {
        return new Random();
    }

    /**
     * Returns true if the current state of the cell is the same as the int state argument passed in
     * 
     * @param curr
     * @param state
     * @return
     */
    protected boolean checkState (Cell curr, int state) {
        return (curr.getCurrState() == state);
    }

    /**
     * Returns true if the next state of the cell is the same as the int state argument passed in
     * 
     * @param curr
     * @param state
     * @return
     */
    protected boolean checkNextState (Cell curr, int state) {
        return (curr.getNextState() == state);
    }

    /**
     * Returns the percent of like neighbors. This is determined as the number of similar neighbors
     * divided by the number of total neighbor states that are not defined as EMPTY
     * 
     * @param curr
     * @param neighbors
     * @return
     */
    protected double calculateLikeNeighborsPercent (Cell curr, Cell[] neighbors) {
        double likeCount = countNeighborState(neighbors, curr.getCurrState());
        double occupiedNeighbors = neighbors.length - countNeighborState(neighbors, EMPTY);
        return likeCount / occupiedNeighbors;
    }

    /**
     * Returns a double equal to the number of neighbors of a certain cell that are the same as the
     * given state that is passed into the method.
     * 
     * @param neighbors
     * @param state
     * @return
     */
    protected double countNeighborState (Cell[] neighbors, int state) {
        double count = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (checkState(neighbors[i], state)) {
                count++;
            }
        }
       // System.out.println(count);
        return count;
    }

    /**
     * Rectangular and Triangular:
     * Order of Array: {North, South, East, West, NorthEast, NorthWest,
     * SouthWest, SouthEast} Index: 0 1 2 3 4 5 6 7
     * 
     * Hexagonal:
     * Order of Array: {North, South, SouthEast, SouthWest,
     * NorthWest, NorthEast} Index: 0 1 2 3 4 5
     * 
     * @param cellGrid
     * @param i
     * @param j
     * @return
     */
    public Cell[] setNeighbors (Cell[][] cellGrid, int i, int j, int shape) {
        if (shape == 0 || shape == 1) {
            Cell[] neighbors = { cellGrid[i - 1][j], cellGrid[i + 1][j],
                                 cellGrid[i][j + 1], cellGrid[i][j - 1], cellGrid[i - 1][j + 1],
                                 cellGrid[i - 1][j - 1], cellGrid[i + 1][j - 1],
                                 cellGrid[i + 1][j + 1] };
            return neighbors;
        }
        else {
            Cell[] neighbors =
                    { cellGrid[i - 1][j], cellGrid[i + 1][j], cellGrid[i][j-1],
                      cellGrid[i][j+1], cellGrid[i + 1][j-1], cellGrid[i+1][j+1] };
            return neighbors;
        }
    }
    
    public Cell[] shortenNeighbors(Cell[] neighbors){
        return new Cell[]{neighbors[0],neighbors[1],neighbors[2],neighbors[3]};
    }
}
