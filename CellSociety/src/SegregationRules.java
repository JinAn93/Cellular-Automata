import java.util.*;


/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the segregation simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class SegregationRules extends SimulationRules {
    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private static final int paramNeeded = 1;
    private double percentToSatisfy;

    public SegregationRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the segregation simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        if (checkState(curr, EMPTY)) {
            return EMPTY;
        }
        else {
            if (calculateLikeNeighborsPercent(curr, neighbors) < percentToSatisfy) {// Not Satisfied
                pickNewLocation(grid, curr.getCurrState());
                return EMPTY;
            }
            else {// Satisfied
                return curr.getCurrState();
            }
        }
    }

    /**
     * Returns a list of the x locs, and corresponding y locs of all available locations on the grid
     * that an unsatisfied cell can 'move' to
     * 
     * @param grid
     * @return
     */
    private List<ArrayList<Integer>> possibleNextLoc (Cell[][] grid) {
        List<ArrayList<Integer>> possLocs = new ArrayList<ArrayList<Integer>>();
        possLocs.add(new ArrayList<Integer>());
        possLocs.add(new ArrayList<Integer>());
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if ((checkNextState(grid[i][j], OPEN_NEXT) && checkState(grid[i][j], EMPTY)) ||
                    (checkNextState(grid[i][j], EMPTY))) {
                    possLocs.get(0).add(i);
                    possLocs.get(1).add(j);
                }
            }

        }
        return possLocs;
    }

    /**
     * Randomly chooses an open cell location on the grid from the list of possible locations and
     * sets that cell's next state to whichever state argument is passed into the method
     * 
     * @param grid
     * @param state
     */
    private void pickNewLocation (Cell[][] grid, int state) {
        List<ArrayList<Integer>> possLocs = possibleNextLoc(grid);
        int pick = getRand().nextInt(possLocs.get(0).size());
        grid[possLocs.get(0).get(pick)][possLocs.get(1).get(pick)].setNextState(state);
    }

    /**
     * Sets the percentToSatisfy property of the simulation according to data passed in that was
     * read from the XML configuration file
     */
    @Override
    protected void setSimulationParameters (String[] simParams) {
        percentToSatisfy = Double.parseDouble(simParams[0]);
    }
    
    /**
     * Checks to make sure that amount of params in XML file is equal to amount needed and that
     * percentToSatisfy is between 0 and 1
     */
    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        if (Double.parseDouble(simParams[0]) > 1 || Double.parseDouble(simParams[0]) < 0) {
            return true;
        }
        return false;
    }
}
