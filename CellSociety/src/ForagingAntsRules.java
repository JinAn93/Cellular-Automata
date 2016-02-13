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
    private static final int paramNeeded = 2;
    private static final int NO_FOOD_ANT = 1;
    private static final int FOOD_ANT = 2;
    private static final int FOOD_SOURCE = 3;
    private static final int NEST = 4;
    private static final int PHER = 5;
    private int homePherInd = 0;
    private int foodPherInd = 1;
    private int orientationInd = 2;
    private double maxPher;
    private double pherLost;

    public ForagingAntsRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the ForagingAnts simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        handleUserInput(curr);
        pherFade(curr, pherLost);
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
        else if (checkState(curr, PHER)) {
            if (getPher(curr, foodPherInd) > 0 || getPher(curr, homePherInd) > 0) {
                return PHER;
            }
        }
        return EMPTY;
    }

    /**
     * Sets value of both pheremones in current cell to zero
     * 
     * @param curr
     * @param pherLost
     */
    private void pherFade (Cell curr, double pherLost) {
        setPher(curr, getPher(curr, foodPherInd) - pherLost, foodPherInd);
        setPher(curr, getPher(curr, homePherInd) - pherLost, homePherInd);
    }

    /**
     * Ant must find food, calls handleDecision method with according paramters
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @return
     */
    private int findFood (Cell curr, Cell[] neighbors, Cell[][] grid) {
        return handleDecision(curr, neighbors, foodPherInd, homePherInd);
    }

    /**
     * Ant must return to Nest, calls handleDecision method with accoridng parameters
     * 
     * @param curr
     * @param neighbors
     * @param grid
     * @return
     */
    private int returnToNest (Cell curr, Cell[] neighbors, Cell[][] grid) {
        return handleDecision(curr, neighbors, homePherInd, foodPherInd);
    }

    /**
     * Drives decision making for a cell that is labeled as an ant, according to the desiredPher and
     * pherToDrop parameters passed into the method
     * 
     * @param curr
     * @param neighbors
     * @param desiredPher - pher that ant must follow
     * @param pherToDrop - pher that ant leaves behind in every cell it goes to
     * @return int representing the index of the next location for the ant to go to
     */
    private int handleDecision (Cell curr, Cell[] neighbors, int desiredPher, int pherToDrop) {
        if (isAround(neighbors, FOOD_SOURCE)) {// at food source
            setOrientation(curr, findNextOrientation(homePherInd, neighbors));
            eatFood(neighbors);
            makeMove(curr, neighbors, homePherInd, checkState(curr,NO_FOOD_ANT));
            setPher(curr, maxPher, foodPherInd);
        }
        else if (isAround(neighbors, NEST)) {// at nest
            setOrientation(curr, findNextOrientation(foodPherInd, neighbors));
            makeMove(curr, neighbors, foodPherInd, checkState(curr,FOOD_ANT));
            setPher(curr, maxPher, homePherInd);
        }
        else {// move the ant
            if (getOrientation(curr) < 0) {
                setOrientation(curr, findNextOrientation(desiredPher, neighbors));
            }
            makeMove(curr, neighbors, desiredPher, false);
            setPher(curr, maxPher, pherToDrop);
        }
        return PHER;
    }

    /**
     * called if ant is by a food source, changes state of one FOOD_SOURCE cell to the food
     * pheremones that the ant will drop in its place
     * 
     * @param neighbors
     */
    private void eatFood (Cell[] neighbors) {
        for (Cell c : neighbors) {
            if (checkState(c, FOOD_SOURCE)) {
                c.setNextState(PHER);
                setPher(c, maxPher, foodPherInd);
                return;
            }
        }
    }

    /**
     * Actually makes 'move' for a cell that is in an ant state. Picks next location according to
     * orientation and maxPheremones in neighboring cells or returns a random location if there are
     * no valid moves otherwise
     * 
     * @param curr
     * @param neighbors
     * @param pherType
     * @param change
     */
    private void makeMove (Cell curr, Cell[] neighbors, int pherType, boolean change) {
        if (getOrientation(curr) < 0) {
            int nextLoc = makeRandomMove(neighbors);
            neighbors[nextLoc].setNextState(curr.getCurrState());
            return;
        }
        int mid = (int) getOrientation(curr);
        int lower = mid - 1;
        int upper = mid + 1;
        if (lower < 0) {
            lower = neighbors.length - 1;
        }
        if (upper > neighbors.length - 1) {
            upper = 0;
        }
        int nextLoc = findMaxPher(lower, upper, mid, pherType, neighbors);
        if (nextLoc < 0) {
            nextLoc = makeRandomMove(neighbors);
        }
        if(!isValidMove(nextLoc,neighbors)){
            nextLoc = makeRandomMove(neighbors);
        }
        if (change) {
            neighbors[nextLoc].setNextState(otherAnt(curr.getCurrState()));
        }
        else {
            neighbors[nextLoc].setNextState(curr.getCurrState());
        }
        setOrientation(neighbors[nextLoc], nextLoc);
        return;
    }

    /**
     * Returns a random index from the neighbors array as the next location for a given ant, checks
     * to make sure that location is valid before returning
     * 
     * @param neighbors
     * @return
     */
    private int makeRandomMove (Cell[] neighbors) {
        int nextLoc = getRand().nextInt(neighbors.length);
        int count = 0;
        while (!isValidMove(nextLoc, neighbors) && count < 40) {// TODO make a fix here
            nextLoc = getRand().nextInt(neighbors.length);
            count++;
        }
        return nextLoc;
    }

    /**
     * Checks to make sure a given location is valid according to the requirements indicated
     * 
     * @param nextLoc
     * @param neighbors
     * @return
     */
    private boolean isValidMove (int nextLoc, Cell[] neighbors) {
        return !checkState(neighbors[nextLoc], BLOCKED) && !checkState(neighbors[nextLoc], NEST) &&
               !checkState(neighbors[nextLoc], FOOD_SOURCE) &&
               !checkNextState(neighbors[nextLoc], FOOD_ANT) &&
               !checkNextState(neighbors[nextLoc], NO_FOOD_ANT);
    }

    /**
     * Changes the state of a cell from NO_FOOD_ANT to FOOD_ANT or vice-versa
     * 
     * @param currState
     * @return
     */
    private int otherAnt (int currState) {
        if (currState == FOOD_ANT) {
            return NO_FOOD_ANT;
        }
        return FOOD_ANT;
    }

    /**
     * Given a certain three cell indices, returns the cell with the max amount of a given pheremone
     * 
     * @param lower
     * @param upper
     * @param mid
     * @param pherType - desired pheremone
     * @param neighbors
     * @return
     */
    private int findMaxPher (int lower, int upper, int mid, int pherType, Cell[] neighbors) {
        double low = getPher(neighbors[lower], pherType);
        double midd = getPher(neighbors[mid], pherType);
        double up = getPher(neighbors[upper], pherType);
        double max = Math.max(Math.max(low, midd), up);
        if (max <= 0) {
            return findNextOrientation(pherType, neighbors);
        }
        if (max == low) {
            return lower;
        }
        else if (max == midd) {
            return mid;
        }
        return upper;
    }

    /**
     * Determines next orientation of a given cell according to the pheremone levels of surrounding
     * neighbors, returns a random index if none of the neighboring cells have levels above zero of
     * a desired pheremone
     * 
     * @param pherType
     * @param neighbors
     * @return
     */
    private int findNextOrientation (int pherType, Cell[] neighbors) {
        double maxPher = 0;
        int maxCellIndex = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (getPher(neighbors[i], pherType) > maxPher) {
                maxPher = getPher(neighbors[i], pherType);
                maxCellIndex = i;
            }
        }
        if (maxPher <= 0) {// no pheremones in any neighbor
            return -1;
        }
        return maxCellIndex;
    }

    /**
     * Initializes the each cell's list of parameters to the indicated starting values
     * 
     * @param curr
     */
    @Override
    protected void initializeCellParams (Cell curr) {
        curr.getCellParamList().add((double) 0);
        curr.getCellParamList().add((double) 0);
        curr.getCellParamList().add((double) -1);
        if (checkState(curr, PHER)) {
            setPher(curr, maxPher, homePherInd);
            setPher(curr, maxPher, foodPherInd);
        }
    }

    /**
     * Updates cell's list of parameters accordingly if a cell's state is changed by user input,
     * rather than natural simulation progression
     * 
     * @param curr
     */
    @Override
    protected void handleUserInput (Cell curr) {
        if (checkPrevState(curr, BLOCKED)) {
            if (checkState(curr, PHER)) {
                setPher(curr, maxPher, foodPherInd);
                setPher(curr, maxPher, homePherInd);
            }
            else {
                pherFade(curr, maxPher);
            }
        }
    }

    /**
     * Sets the properties listed according to data read in from the XML configuration file
     * 
     * @param simParams
     */
    @Override
    protected void setSimulationParameters (String[] simParams) {
        maxPher = Double.parseDouble(simParams[0]);
        pherLost = Double.parseDouble(simParams[1]);
    }

    /**
     * Checks validity of parameter inputs, returns false if length of simParams is incorrect or if
     * any values are negative
     * 
     * @param simParams
     */
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

    private double getPher (Cell curr, int pherType) {
        return curr.getCellParamList().get(pherType);
    }

    private void setPher (Cell curr, double pherLevel, int pherType) {
        curr.getCellParamList().set(pherType, pherLevel);
    }

    private double getOrientation (Cell curr) {
        return curr.getCellParamList().get(orientationInd);
    }

    private void setOrientation (Cell curr, double orientation) {
        curr.getCellParamList().set(orientationInd, orientation);
    }
}
