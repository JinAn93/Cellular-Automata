import java.util.*;


public abstract class SimulationRules {
    private final int numStates;
    private Random rand = new Random();
    
    public SimulationRules(int states){
        numStates = states;
    }
    protected void applyRule (Cell[][] cellGrid) {
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                int nextState =
                        findNextState(cellGrid[i][j], cellGrid[i - 1][j], cellGrid[i + 1][j],
                                      cellGrid[i][j + 1], cellGrid[i][j - 1]);
                cellGrid[i][j].setNextState(nextState);
            }
        }
    }
    protected abstract int findNextState(Cell curr, Cell North, Cell South, Cell East, Cell West);
    protected Random getRand(){
        return rand;
    }

}
