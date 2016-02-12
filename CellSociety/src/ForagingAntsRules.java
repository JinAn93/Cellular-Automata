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
        setPher(curr,getPher(curr,foodPherInd)-pherLost,foodPherInd);
        setPher(curr,getPher(curr,homePherInd)-pherLost,homePherInd);        
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
            if (getPher(curr,foodPherInd)>0&&getPher(curr,homePherInd)>0){
                return PHER;
            }
        }
        return EMPTY;
    }

    private int findFood (Cell curr, Cell[] neighbors, Cell[][] grid) {
       return handleDecision(curr,neighbors,foodPherInd,homePherInd);        
    }

    private int returnToNest (Cell curr, Cell[] neighbors, Cell[][] grid) {
        return handleDecision(curr,neighbors,homePherInd,foodPherInd);
    }
 
    private int handleDecision (Cell curr, Cell[] neighbors, int desiredPher, int pherDrop) {
        if (atFoodSource(neighbors)) {
            setOrientation(curr, findNextOrientation(0,neighbors.length, homePherInd, neighbors));
            eatFood(neighbors);
            makeMove(curr,neighbors,homePherInd,true);
            setPher(curr,maxPher,homePherInd );
            return PHER;
        }
        else if (atNest(neighbors)) {
            setOrientation(curr, findNextOrientation(0,neighbors.length,foodPherInd, neighbors));
            makeMove(curr,neighbors,foodPherInd,true);
            setPher(curr,maxPher,homePherInd);
            return PHER;
        }
        else {// move the ant
            makeMove(curr, neighbors,desiredPher,false);
            setPher(curr, maxPher, pherDrop);
            return PHER;
        }
    }
    private void eatFood(Cell[] neighbors){
        for(Cell c:neighbors){
            if(checkState(c,FOOD_SOURCE)){
                c.setNextState(PHER);
                setPher(c, maxPher, foodPherInd);
            }
        }
    }
    private void makeMove (Cell curr, Cell[] neighbors, int pherType, boolean change) {
        if (getOrientation(curr) < 0) {
            int nextLoc = getRand().nextInt(neighbors.length);
            neighbors[nextLoc].setNextState(curr.getCurrState());
            return;
        }
        int mid = (int) getOrientation(curr);
        int lower = mid - 1;
        int upper = mid + 1;
        if (lower < 0) {
            lower =neighbors.length-1;
        }
        if (upper > neighbors.length-1) {
            upper = 0;
        }
        int nextLoc = findMaxPher(lower,  upper, mid, pherType, neighbors);
        if(change){
            neighbors[nextLoc].setNextState(otherAnt(curr.getCurrState()));
        }
        else{
            neighbors[nextLoc].setNextState(curr.getCurrState());
        }
        setOrientation(neighbors[nextLoc],getOrientation(curr));
        return;
    }
    private int otherAnt(int currState){
        if(currState==FOOD_ANT){
            return NO_FOOD_ANT;            
        }
        return FOOD_ANT;
    }
    private int findMaxPher (int lower, int upper, int mid, int pherType, Cell[] neighbors) {
        double low = getPher(neighbors[lower],pherType);
        double midd = getPher(neighbors[mid],pherType);
        double up = getPher(neighbors[upper],pherType);
        double max = Math.max(Math.max(low, midd), up);
        if(max==low){
            return lower;
        }
        else if(max==midd){
            return mid;
        }
        return upper;
    }

    private int findNextOrientation (int lower, int upper, int pherType,  Cell[] neighbors) {
        double maxPher = 0;
        int maxCellIndex = 0;
        for (int i = lower; i < upper; i++) {
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

    private boolean atFoodSource (Cell[] neighbors) {
        return countNeighborState(neighbors, FOOD_SOURCE) > 0;
    }

    private boolean atNest (Cell[] neighbors) {
        return countNeighborState(neighbors, NEST) > 0;
    }

    private double getPher (Cell curr, int pherType) {
        return curr.getSimParams().get(pherType);
    }

    private void setPher (Cell curr, double pherLevel, int pherType) {
        curr.getSimParams().set(pherType, pherLevel);
    }

    private double getOrientation (Cell curr) {
        return curr.getSimParams().get(orientationInd);
    }

    private void setOrientation (Cell curr, double orientation) {
        curr.getSimParams().set(orientationInd, orientation);
    }

    @Override
    protected void initializeCellParams (Cell curr) {
        curr.getSimParams().add((double) 0);
        curr.getSimParams().add((double) 0);
        curr.getSimParams().add((double) 0);
        if (checkState(curr, PHER)) {
            setPher(curr, maxPher, homePherInd);
            setPher(curr, maxPher, foodPherInd);
        }
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        maxPher = Double.parseDouble(simParams[0]);
        pherLost = Double.parseDouble(simParams[1]);
    }

    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        return false;
    }
}
