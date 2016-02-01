import java.util.*;


public class SpreadingFireRules extends SimulationRules {
    private static final int EMPTY = 0;
    private static final int TREE = 1;
    private static final int BURNING = 2;
    private double probCatch;
    
    public SpreadingFireRules(double prob){
        probCatch = prob;
    }

    @Override
    protected void applyRule (List<ArrayList<Cell>> cellList) {
        for (int i = 0; i < cellList.size(); i++) {
            for (int j = 0; j < cellList.get(0).size(); j++) {
                int nextState = findNextState();
                cellList.get(i).get(j).setNextState(nextState);
            }
        }
    }

    protected int findNextState (Cell curr, Cell North, Cell South, Cell East, Cell West) {
        if (isEmpty(curr)) {
            return EMPTY;
        }
        else if (isBurning(curr)) {
            return EMPTY;
        }
        else if (isTree(curr) && checkBurning(North,South,East,West)) {
            if(getRand().nextDouble()<probCatch){
                return TREE;
            }
            else{
                return BURNING;
            }
        }
        return 0;
    }

    private boolean checkBurning (Cell North, Cell South, Cell East, Cell West) {
        return isBurning(North) || isBurning(South) || isBurning(East) || isBurning(West);
    }

    private boolean isBurning (Cell currCell) {
        return (currCell.getCurrState() == BURNING);
    }

    private boolean isTree (Cell currCell) {
        return (currCell.getCurrState() == TREE);
    }

    private boolean isEmpty (Cell currCell) {
        return (currCell.getCurrState() == EMPTY);
    }

}
