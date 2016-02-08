/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the PredatorPrey simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class PredatorPreyRules extends SimulationRules {
    private static final int FISH = 1;
    private static final int SHARK = 2;
    private static final int paramNeeded = 3;
    private double reproductionTime;
    private double startEnergy;
    private double fishEnergy;

    public PredatorPreyRules () {
        super();

    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the PredatorPrey simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        curr.setTurnsOnState(curr.getTurnsOnState() + 1);
        if (checkState(curr, FISH)) {
            return handleMove(curr, neighbors, grid, FISH);
        }
        else if (checkState(curr, SHARK)) {
            return handleShark(curr, neighbors, grid);
        }
        else {
            return EMPTY;
        }
    }

    /**
     * Overrides fillCellGrid method of Simulation Rules. Initializes the cell grid of the
     * simulation according to the XML configuration file and sets the energy level of a Shark cell
     * to the initial shark energy, as defined in the configuration file
     */
    @Override
    public void fillCellgrid (Cell[][] cellGrid, int[] entry) {
        int count = 0;
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                cellGrid[i][j].setCurrState(entry[count]);
                cellGrid[i][j].setNextState(OPEN_NEXT);
                if (entry[count] == SHARK) {
                    cellGrid[i][j].setEnergy(startEnergy);
                }
                count++;
            }
        }
    }

    /**
     * Handles move for a given Cell. Chooses random new location from each of the cell's neighbors
     * that are available and sets that cells next state to the state argument passed into the
     * method
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @param state
     * @return
     */
    private int handleMove (Cell curr, Cell[] neighbors, Cell[][] grid, int state) {
        while (isNeighborState(curr, neighbors, grid, OPEN_NEXT)) {
            int rand = getRand().nextInt(4);
            if ((checkState(neighbors[rand], EMPTY) &&
                 checkNextState(neighbors[rand], OPEN_NEXT)) ||
                checkNextState(neighbors[rand], EMPTY)) {
                neighbors[rand].setNextState(state);
                if (curr.getTurnsOnState() > reproductionTime) {
                    curr.setTurnsOnState(0);
                    return state;
                }
                else {
                    return EMPTY;
                }
            }
        }
        return state;
    }

    /**
     * Handles 'hunting' movement for a Shark cell. Chooses a random neighboring cell that is
     * currently a fish and sets that cells next state to shark. If no neighbors are fish, calls
     * handleMove() to make the shark move to a random new location.
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @return
     */
    private int handleShark (Cell curr, Cell[] neighbors, Cell[][] grid) {
        curr.setEnergy(curr.getEnergy() - 1);
        if (curr.getEnergy() <= 0) {
            return EMPTY;
        }
        while (isAround(curr, neighbors, grid, FISH)) {
            int rand = getRand().nextInt(4);
            if (checkState(neighbors[rand], FISH)) {
                neighbors[rand].setNextState(SHARK);
                neighbors[rand].setEnergy(curr.getEnergy() + fishEnergy);
                if (curr.getTurnsOnState() > reproductionTime) {
                    curr.setTurnsOnState(0);
                    curr.setEnergy(startEnergy);
                    return SHARK;
                }
                else {
                    return EMPTY;
                }
            }

        }
        return handleMove(curr, neighbors, grid, SHARK);
    }

    /**
     * Returns true if any of a cells neighbors are equal to the state argument passed in. Otherwise
     * returns false
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @param state
     * @return
     */
    private boolean isAround (Cell curr, Cell[] neighbors, Cell[][] grid, int state) {
        for (int i = 0; i < 4; i++) {
            if (checkState(neighbors[i], state)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any neighbors of a cell are empty currently and have next state equal to the
     * state argument passed in
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @param state
     * @return
     */
    private boolean isNeighborState (Cell curr, Cell[] neighbors, Cell[][] grid, int state) {
        for (int i = 0; i < 4; i++) {
            if (checkState(neighbors[i], EMPTY) && checkNextState(neighbors[i], state)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the properties listed according to data read in from the XML configuration file
     */
    @Override
    protected void setSimulationParameters (String[] simParams) {
        reproductionTime = Double.parseDouble(simParams[0]);
        startEnergy = Double.parseDouble(simParams[1]);
        fishEnergy = Double.parseDouble(simParams[2]);
    }
    
    @Override
    protected boolean isInvalid (String[] simParams){
        if(simParams.length != paramNeeded){
            return true;
        }
        for(int i=0; i<paramNeeded; i++){
            if(Double.parseDouble(simParams[i]) <= 0){
                return true;
            }
        }
        return false;
    }

}
