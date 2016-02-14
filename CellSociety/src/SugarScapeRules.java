/**
 * This class, which extends SimulationRules, controls the state updates of each cell in the
 * simulation when a SegregationRules configuration file has been loaded in the program. It is
 * dependent upon the general methods defined in SimulationRules and contains methods that run the
 * Cell updates according to the specific rules defined by the SugarScape simulation.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class SugarScapeRules extends SimulationRules {
    private static final int paramNeeded = 3;
    private static final int AGENT = 1;
    private int amountSugar = 0;
    private int maxSugarCap = 1;
    private int currSugar = 2;
    private double initSugar, sugarMetabol, vision;

    public SugarScapeRules () {
        super();
    }

    /**
     * Updates the nextState property of the cell passed into it according to the states of its
     * neighbors and the rules of the SugarScape simulation
     */
    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid, int shape) {
        handleUserInput(curr);
        if (checkState(curr, EMPTY)) {
            return EMPTY;
        }
        else if (checkState(curr, AGENT)) {
            return handleAgent(curr, neighbors, grid);
        }
        return EMPTY;
    }

    @Override
    protected void handleUserInput (Cell curr) {
        //work on this
    }

    private int handleAgent (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (isSugarAround(curr, neighbors)) {
            findMaxSugar(curr, neighbors);
            moveToSugar(curr, neighbors);
        }
        else {
            return moveRandom(neighbors);
        }
        sugarMeta(curr);
        return 0;
    }

    private void findMaxSugar (Cell curr, Cell[] neighbors) {
        
    }

    private void moveToSugar (Cell curr, Cell[] neighbors) {

    }

    private int moveRandom (Cell[] neighbors) {
        int randomLoc = getRand().nextInt(neighbors.length);
        while (!isValidMove(randomLoc, neighbors)) {
            randomLoc = (randomLoc + 1) % (neighbors.length);
        }
        return randomLoc;
    }

    private boolean isValidMove (int loc, Cell[] neighbors) {
        return !checkState(neighbors[loc], BLOCKED) && !checkState(neighbors[loc], AGENT);
    }

    private boolean isSugarAround (Cell curr, Cell[] neighbors) {

        return false;
    }

    private void sugarMeta (Cell curr) {
        curr.getCellParamList().set(currSugar,
                                    curr.getCellParamList().get(currSugar) - sugarMetabol);
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        initSugar = Double.parseDouble(simParams[0]);
        sugarMetabol = Double.parseDouble(simParams[1]);
        vision = Double.parseDouble(simParams[2]);
    }

    @Override
    protected void initializeCellParams (Cell curr) {
        curr.getCellParamList().add((double) 0); // amount sugar
        curr.getCellParamList().add((double) 0); // max sugar cap
        curr.getCellParamList().add((double) 0); // agent sugar
    }

    @Override
    protected boolean isInvalid (String[] simParams) {
        if (simParams.length != paramNeeded) {
            return true;
        }
        return false;
    }
}
