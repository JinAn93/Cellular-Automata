import java.util.*;


/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the SpreadingFire simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class SpreadingFireRules extends SimulationRules {
    private static final int TREE = 1;
    private static final int BURNING = 2;
    private static final int paramNeeded = 1;
    private double probCatch;

    public SpreadingFireRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the SpreadingFire simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        if(shape==0||shape==1){
            neighbors = shortenNeighbors(neighbors);
        }
        if (checkState(curr, BURNING)) {
            return EMPTY;
        }
        else if (checkState(curr, TREE)) {            
            if (checkBurning(neighbors) &&
                getRand().nextDouble() < probCatch) {
                return BURNING;
            }
            else {
                return TREE;
            }
        }
        return EMPTY;
    }

    /**
     * Returns true if any of the neighboring cells are currently burning
     * 
     * @param North
     * @param South
     * @param East
     * @param West
     * @return
     */
    private boolean checkBurning (Cell[] neighbors) {
        for(int i=0;i<neighbors.length;i++){
            if(checkState(neighbors[i],BURNING)){
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the probCatch property of the simulation according to the data read in from the XML
     * configuration file
     */
    @Override
    protected void setSimulationParameters (String[] simParams) {
        probCatch = Double.parseDouble(simParams[0]);

    }
    
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
