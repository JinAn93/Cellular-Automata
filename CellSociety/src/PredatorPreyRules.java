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
    private int turnsOnStateInd = 0;
    private int energyInd = 1;

    public PredatorPreyRules () {
        super();

    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the PredatorPrey simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        if (shape == 0 || shape == 1) {
            neighbors = shortenNeighbors(neighbors);
        }
        setTurnsOnState(curr,getTurnsOnState(curr) + 1);
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
            int rand = getRand().nextInt(neighbors.length);
            if ((checkState(neighbors[rand], EMPTY) &&
                 checkNextState(neighbors[rand], OPEN_NEXT)) ||
                checkNextState(neighbors[rand], EMPTY)) {
                neighbors[rand].setNextState(state);
                if (getTurnsOnState(curr) > reproductionTime) {
                    setTurnsOnState(curr,0);
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
        setEnergy(curr,getEnergy(curr) - 1);
        if (getEnergy(curr) <= 0) {
            return EMPTY;
        }
        while (isAround(curr, neighbors, grid, FISH)) {
            int rand = getRand().nextInt(neighbors.length);
            if (checkState(neighbors[rand], FISH)) {
                neighbors[rand].setNextState(SHARK);
                setEnergy(neighbors[rand],getEnergy(curr) + fishEnergy);
                if (getTurnsOnState(curr) > reproductionTime) {
                    setTurnsOnState(curr,(double) 0);
                    setEnergy(curr,startEnergy);
                    return SHARK;
                }
                else {
                    return EMPTY;
                }
            }

        }
        return handleMove(curr, neighbors, grid, SHARK);
    }
    
    @Override
    protected void initializeCellParams(Cell curr){
        curr.getCellParamList().add((double)0);
        curr.getCellParamList().add((double)0);        
        if (checkState(curr,SHARK)) {
            curr.getCellParamList().set(energyInd,startEnergy);
        }
    }

    private double getTurnsOnState (Cell curr) {
        
        return curr.getCellParamList().get(turnsOnStateInd );
    }

    private void setTurnsOnState (Cell curr, double turns) {
        curr.getCellParamList().set(turnsOnStateInd, turns);
    }

    private double getEnergy (Cell curr) {
        return curr.getCellParamList().get(energyInd);
    }

    private void setEnergy (Cell curr, double energy) {
        curr.getCellParamList().set(energyInd, energy);
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
        for (int i = 0; i < neighbors.length; i++) {
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
        for (int i = 0; i < neighbors.length; i++) {
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
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        for (int i = 0; i < paramNeeded; i++) {
            if (Double.parseDouble(simParams[i]) <= 0) {
                return true;
            }
        }
        return false;
    }

}
