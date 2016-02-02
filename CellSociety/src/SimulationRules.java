import java.util.*;


public abstract class SimulationRules {
    
    protected static final int EMPTY = 0;
    private final int numStates;
    private Random rand = new Random();    

    public SimulationRules (int states) {
        numStates = states;
    }

    protected void applyRule (Cell[][] cellGrid) {
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                Cell[] neighbors = setNeighbors(cellGrid, i, j);
                int nextState = findNextState(cellGrid[i][j], neighbors,cellGrid);
                cellGrid[i][j].setNextState(nextState);
            }
        }
    }

    protected abstract int findNextState (Cell curr, Cell[] neighbors,Cell[][] grid);

    protected Random getRand () {
        return rand;
    }
    

    protected boolean isEmpty (Cell currCell) {
        return (currCell.getCurrState() == EMPTY);
    }
    protected boolean isNextEmpty(Cell currCell){
        return (currCell.getNextState()==EMPTY);
    }
    protected boolean checkState(Cell curr, int state){
        return (curr.getCurrState()==state);
    }
    protected boolean checkNextState(Cell curr, int state){
        return (curr.getNextState()==state);
    }
    protected double calculateLikeNeighbors(Cell curr,Cell[] neighbors){
        double likeCount = countNeighborState(neighbors,curr.getCurrState());
        double occupiedNeighbors = neighbors.length-countNeighborState(neighbors,EMPTY);
        return likeCount/occupiedNeighbors;
    }
    
    protected double countNeighborState(Cell[] neighbors, int state){
        double count = 0;
        for(int i=0;i<neighbors.length;i++){
            if(neighbors[i].getCurrState()==state){
                count++;
            }
        }
        return count;
    }
    protected boolean isGridOpen(Cell[][] grid){
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if(isEmpty(grid[i][j])&& isNextEmpty(grid[i][j])){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Order of Array: {North, South, East, West, NorthEast, NorthWest, SouthWest, SouthEast}
     *          Index:    0      1      2     3     4             5         6         7
     * @param cellGrid
     * @param i
     * @param j
     * @return
     */
    public Cell[] setNeighbors (Cell[][] cellGrid, int i, int j) {
        Cell[] neighbors = {cellGrid[i - 1][j], cellGrid[i + 1][j],
                            cellGrid[i][j + 1], cellGrid[i][j - 1],
                            cellGrid[i-1][j+1], cellGrid[i-1][j-1],
                            cellGrid[i+1][j-1], cellGrid[i+1][j+1]};
        return neighbors;
    }

}
