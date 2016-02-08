/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the ForagingAnts simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class ForagingAntsRules extends SimulationRules {
    private static final int paramNeeded = 0;
    public static final int DEAD = 1;
    public static final int ALIVE = 2;

    public ForagingAntsRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the ForagingAnts simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
     return 0;
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        // No Parameters to Set for this Simulation
    }

    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        return false;
    }
}
