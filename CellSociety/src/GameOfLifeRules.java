
public class GameOfLifeRules extends SimulationRules {
    public static final int DEAD = 1;
    public static final int ALIVE = 2;

    public GameOfLifeRules () {
        super();
    }

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
        else {
            if (countNeighborState(neighbors, ALIVE) == 3) {
                return ALIVE;
            }
            else {
                return DEAD;
            }
        }
    }

    protected boolean isDead (Cell curr) {
        return (curr.getCurrState() == DEAD);
    }

    protected boolean isAlive (Cell curr) {
        return (curr.getCurrState() == ALIVE);
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        // No Parameters to Set for this Simulation
    }

}
