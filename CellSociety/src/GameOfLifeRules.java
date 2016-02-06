/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the GameOfLife simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class GameOfLifeRules extends SimulationRules {
    public static final int DEAD = 1;
    public static final int ALIVE = 2;

    public GameOfLifeRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the GameOfLife simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (curr.getCurrState() == ALIVE) {
            if (countNeighborState(neighbors, ALIVE) < 2) {
                return DEAD;
            }
            else if (countNeighborState(neighbors, ALIVE) < 4) {
                return ALIVE;
            }
            else {
                return DEAD;
            }
        }
        else {// current cell is dead
            if (countNeighborState(neighbors, ALIVE) == 3) {
                return ALIVE;
            }
            else {
                return DEAD;
            }
        }
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        // No Parameters to Set for this Simulation
    }

}
