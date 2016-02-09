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
    public static final int NO_FOOD_ANT = 1;
    public static final int FOOD_ANT = 2;
    private int homePherInd = 0;
    private int foodPherInd = 1;

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
            findFood(curr,neighbors,grid);
        }
        else{
            returnToNest(curr,neighbors,grid);
        }
        return EMPTY;
    }

    private void findFood(Cell curr, Cell[] neighbors, Cell[][] grid){
        
    }
    
    private void returnToNest(Cell curr, Cell[] neighbors, Cell[][] grid){
        if(curr.isFood()){
            curr.setOrientation(findNextOrientation(curr,neighbors,homePherInd));
        }
        //TODO finish implementation
        
    }
    
    private Cell findNextOrientation(Cell curr, Cell[] neighbors, int pherType){
        int maxPher = 0;
        int maxCellIndex = 0;
        for(int i=0;i<neighbors.length;i++){
            if(neighbors[i].getPher(pherType)>maxPher){
                maxPher=neighbors[i].getPher(pherType);
                maxCellIndex = i;
            }
        }
        if(maxPher == 0){
            return null;
        }
        return neighbors[maxCellIndex];
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
