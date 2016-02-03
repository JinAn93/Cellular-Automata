
public class SegregationRules extends SimulationRules {
    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private double percentToSatisfy;

    public SegregationRules () {
        super();
    }

    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (isEmpty(curr)) {
            return EMPTY;
        }
        else {
            if (calculateLikeNeighbors(curr, neighbors) < percentToSatisfy) {// Not Satisfied
                pickNewLocation(grid, curr.getCurrState());
                return EMPTY;
            }
            else {// Satisfied
                return curr.getCurrState();
            }
        }
    }

    protected void pickNewLocation (Cell[][] grid, int state) {
        while (isGridOpen(grid)) {//check if this is the condition we want
            int x = getRand().nextInt(grid[0].length-3)+1;
            int y = getRand().nextInt(grid.length-3)+1;
            if (isEmpty(grid[x][y]) && isNextEmpty(grid[x][y])) {
                grid[x][y].setNextState(state);
                break;
            }

        }
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {
        percentToSatisfy = Double.parseDouble(simParams[0]);
        
    }

}
