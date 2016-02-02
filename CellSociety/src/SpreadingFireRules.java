import java.util.*;


public class SpreadingFireRules extends SimulationRules {
    private static final int TREE = 1;
    private static final int BURNING = 2;
    private double probCatch;

    public SpreadingFireRules (int states, double prob) {
        super(states);
        probCatch = prob;
    }

    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (isEmpty(curr)) {
            return EMPTY;
        }
        else if (checkState(curr, BURNING)) {
            return EMPTY;
        }
        else if (checkState(curr, TREE) &&
                 checkBurning(neighbors[0], neighbors[1], neighbors[2], neighbors[3])) {
            if (getRand().nextDouble() < probCatch) {
                return TREE;
            }
            else {
                return BURNING;
            }
        }
        return 0;
    }

    private boolean checkBurning (Cell North, Cell South, Cell East, Cell West) {
        return checkState(North, BURNING) || checkState(South, BURNING) ||
               checkState(East, BURNING) || checkState(West, BURNING);
    }

}
