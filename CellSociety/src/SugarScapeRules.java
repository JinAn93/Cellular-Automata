/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a patch configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the SugarScape simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class SugarScapeRules extends SimulationRules {
    private static final int paramNeeded = 5;
    private static final int AGENT_STATE = 1;
    private int amountSugarIndex = 0;
    private int maxSugarCapIndex = 1;
    private int sugarGrowBackCountIndex = 2;
    private int agentSugarIndex = 3;
    private int initSugar, sugarMetabol, vision, sugarGrowBackInterval, sugarGrowBackRate;

    public SugarScapeRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the SugarScape simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        handleUserInput(curr);
        if (checkState(curr, AGENT_STATE)) {
            return handleAgent(curr, neighbors, grid);
        }
        else {
            if (isSugarMax(curr)) {
                // return the max sugar
            }
            else {
                // check the sugarGrowBack interval, if it's time, increase the sugar and return new
                // sugar or not return its sugar amount. If new sugar amount is greater than max,
                // set it to max
            }
        }
        return EMPTY;
    }

    @Override
    protected void handleUserInput (Cell curr) {
        // This method allows users to allocate the agents
    }

    private int handleAgent (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (isSugarAround(curr, neighbors)) {
            int maxSugarIndex = findMaxSugar(curr, neighbors);
            moveToSugar(curr, neighbors, maxSugarIndex);
        }
        else {
            return moveRandom(neighbors);
        }
        sugarMeta(curr);
        return 0;
    }

    /**
     * findMaxSugar iterates through neighbors to find out the max sugar location. If there are more
     * than one, use the smallest index using the characteristic of how neighbor is set up. (The
     * greater the index, the farther it is from the cell)
     * 
     * @param curr
     * @param neighbors
     */
    private int findMaxSugar (Cell curr, Cell[] neighbors) {
        return 0;
    }

    /**
     * Set the neighbors[loc]'s nextstate to agent and reduce its sugar amount by 1
     * 
     * @param curr
     * @param neighbors
     * @param loc
     */
    private void moveToSugar (Cell curr, Cell[] neighbors, int loc) {
        // change the nextstate of neighbors[loc] to agent and pass the agentSugar parameter to new
        // location
    }

    private int moveRandom (Cell[] neighbors) {
        int randomLoc = getRand().nextInt(neighbors.length);
        while (!isValidMove(randomLoc, neighbors)) {
            randomLoc = (randomLoc + 1) % (neighbors.length);
        }
        return randomLoc;
    }

    private boolean isSugarMax (Cell curr) {
        return (curr.getCellParamList().get(amountSugarIndex) != curr.getCellParamList()
                .get(maxSugarCapIndex));
    }

    private boolean isValidMove (int loc, Cell[] neighbors) {
        return !checkState(neighbors[loc], BLOCKED) && !checkState(neighbors[loc], AGENT_STATE);
    }

    /**
     * returns boolean to check whether there is sugar within the cell's neighbors.
     * 
     * @param curr
     * @param neighbors
     * @return
     */
    private boolean isSugarAround (Cell curr, Cell[] neighbors) {
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i].getCellParamList().get(amountSugarIndex) > 0) {
                return true;
            }
        }
        return false;
    }

    private void sugarMeta (Cell curr) {
        // if its agentSugar is greater than sugarMeta, return the remaining
        // if it's less than sugarMeta, return empty because it dies
    }

    /**
     * This method overrides existing setNeighbors method in super class to apply vision to increase
     * the capacity of neighbor. We have to consider whether the CellGrid will have OutofBounds
     * Exception if vision is greater than 1. The order would be: {North+1, West+1, South+1, East+1,
     * North+2...}
     * 
     * @param cellGrid
     * @param i
     * @param j
     * @param shape
     * @return
     */
    @Override
    public Cell[] setNeighbors (Cell[][] cellGrid, int i, int j, int shape) {
        return new Cell[] { cellGrid[i][j] };
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        initSugar = Integer.parseInt(simParams[0]);
        sugarMetabol = Integer.parseInt(simParams[1]);
        vision = Integer.parseInt(simParams[2]);
        sugarGrowBackInterval = Integer.parseInt(simParams[3]);
        sugarGrowBackRate = Integer.parseInt(simParams[4]);
    }

    @Override
    protected void initializeCellParams (Cell curr) {
        curr.getCellParamList().add((double) 0); // amount sugar for patch
        curr.getCellParamList().add((double) 0); // max sugar cap for patch
        curr.getCellParamList().add((double) 0); // sugarGrowBackInterval
        curr.getCellParamList().add((double) 0); // agent sugar (if there exists agent in the cell)
    }

    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        return false;
    }
}
