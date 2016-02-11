import java.awt.Point;


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
    private static final int NO_FOOD_ANT = 1;
    private static final int FOOD_ANT = 2;
    private static final int FOOD_SOURCE = 3;
    private static final int NEST = 4;
    private static final int HOME_PHER = 5;
    private static final int FOOD_PHER = 6;
    private int homePherInd = 0;
    private int foodPherInd = 1;
    private double maxHomePher;
    private double maxFoodPher;
    private double homePherLost;
    private double foodPherLost;

    public ForagingAntsRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the ForagingAnts simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        if (checkState(curr, EMPTY)) {
            return EMPTY;
        }
        else if (checkState(curr, NO_FOOD_ANT)) {
            return findFood(curr, neighbors, grid);
        }
        else if (checkState(curr, FOOD_ANT)) {
            return returnToNest(curr, neighbors, grid);
        }
        else if (checkState(curr, FOOD_SOURCE)) {
            return FOOD_SOURCE;
        }
        else if (checkState(curr, NEST)) {
            return NEST;
        }
        return EMPTY;
    }

    private int findFood (Cell curr, Cell[] neighbors, Cell[][] grid) {

    }

    private int returnToNest (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (atFoodSource(neighbors)) {
            setOrientation(curr, findNextOrientation(curr, neighbors, homePherInd));
        }
        else if (atNest(neighbors)) {
            setOrientation(curr, findNextOrientation(curr, neighbors, foodPherInd));
            return NO_FOOD_ANT;
        }
        else {// move the ant
            makeMove(curr, neighbors);
            setPher(curr, maxFoodPher, foodPherInd);
            return FOOD_PHER;
        }
    }

    private void makeMove (Cell curr, Cell[] neighbors) {
        if (getOrientation(curr) < 0) {
            int nextLoc = getRand().nextInt(neighbors.length);
            neighbors[nextLoc].setNextState(curr.getCurrState());
            return;
        }
        int lower = (int) getOrientation(curr) - 1;
        int upper = (int) getOrientation(curr) + 2;
        if (lower < 0) {
            lower = upper;
            upper = neighbors.length;
        }
        if (upper > neighbors.length) {
            upper = lower;
            lower = 0;
        }
        int nextLoc = findMaxPher(lower, upper, homePherInd, neighbors);
        neighbors[nextLoc].setNextState(curr.getCurrState());
        return;
    }

    private int findMaxPher (int lower, int upper, int pherType, Cell[] neighbors) {
        double max = 0;
        int ind = 0;
        for (int i = lower; i <= upper; i++) {
            if (getPher(neighbors[i], pherType) > max) {
                max = getPher(neighbors[i], pherType);
                ind = i;
            }
        }
        return ind;
    }

    private boolean atFoodSource (Cell[] neighbors) {
        return countNeighborState(neighbors, FOOD_SOURCE) > 0;
    }

    private boolean atNest (Cell[] neighbors) {
        return countNeighborState(neighbors, NEST) > 0;
    }

    private int findNextOrientation (Cell curr, Cell[] neighbors, int pherType) {
        double maxPher = 0;
        int maxCellIndex = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (getPher(neighbors[i], pherType) > maxPher) {
                maxPher = getPher(neighbors[i], pherType);
                maxCellIndex = i;
            }
        }
        if (maxPher == 0) {// no pheremones in any neighbor
            return -1;
        }
        return maxCellIndex;
    }

    private double getPher (Cell curr, int pherType) {
        return curr.getSimParams().get(pherType);
    }

    private void setPher (Cell curr, double pherLevel, int pherType) {
        curr.getSimParams().set(pherType, pherLevel);
    }

    private double getOrientation (Cell curr) {
        return curr.getSimParams().get(2);
    }

    private void setOrientation (Cell curr, double orientation) {
        curr.getSimParams().set(2, orientation);
    }

    @Override
    protected void initializeCellParams (Cell curr) {
        setPher(curr, 0, homePherInd);
        setPher(curr, 0, foodPherInd);
        setOrientation(curr, 0);
        if (checkState(curr, HOME_PHER)) {
            setPher(curr, maxHomePher, homePherInd);
        }
        else if (checkState(curr, FOOD_PHER)) {
            setPher(curr, maxFoodPher, foodPherInd);
        }
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        maxHomePher = Double.parseDouble(simParams[0]);
        maxFoodPher = Double.parseDouble(simParams[1]);
        homePherLost = Double.parseDouble(simParams[2]);
        foodPherLost = Double.parseDouble(simParams[3]);
    }

    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        return false;
    }
}
