import java.util.*;

public class SegregationRules extends SimulationRules {
    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private double percentToSatisfy;

    public SegregationRules () {
        super();
    }

    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if (checkState(curr,EMPTY)) {
            return EMPTY;
        }
        else { 
            if (calculateLikeNeighborsPercent(curr, neighbors) < percentToSatisfy) {// Not Satisfied
                pickNewLocation(grid, curr.getCurrState());
                return EMPTY;
            }
            else {// Satisfied
                return curr.getCurrState();
            }
        }
    }

    protected List<ArrayList<Integer>> possibleNextLoc(Cell[][] grid){
        List<ArrayList<Integer>> possLocs = new ArrayList<ArrayList<Integer>>();
        possLocs.add(new ArrayList<Integer>());
        possLocs.add(new ArrayList<Integer>());
        for(int i=1;i<grid.length-1;i++){
            for(int j=1;j<grid[0].length-1;j++){
                if((checkNextState(grid[i][j],OPEN_NEXT)&&checkState(grid[i][j],EMPTY))||(checkNextState(grid[i][j],EMPTY))){
                    possLocs.get(0).add(i);
                    possLocs.get(1).add(j);
                }
            }
            
    }
        return possLocs;
    }
        
    protected void pickNewLocation(Cell[][] grid, int state){
        List<ArrayList<Integer>> possLocs = possibleNextLoc(grid);
        int pick = getRand().nextInt(possLocs.get(0).size());
        grid[possLocs.get(0).get(pick)][possLocs.get(1).get(pick)].setNextState(state);
    }
        
        
//        while (isGridOpen(grid)) {//check if this is the condition we want
//            int x = getRand().nextInt(grid[0].length-3)+1;
//            int y = getRand().nextInt(grid.length-3)+1;
//            if (checkState(grid[x][y],EMPTY) && checkNextState(grid[x][y],EMPTY)) {
//                grid[x][y].setNextState(state);
//                break;
//            }
//
//        }
    

    @Override
    protected void setSimulationParameters (String[] simParams) {
        percentToSatisfy = Double.parseDouble(simParams[0]);
        System.out.println(percentToSatisfy);
    }

}
