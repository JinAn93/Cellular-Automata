
public class PredatorPreyRules extends SimulationRules {
    private static final int FISH = 1;
    private static final int SHARK = 2;

    private double reproductionTime;
    private double startEnergy;
    private double fishEnergy;

    public PredatorPreyRules () {
        super();

    }

    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        curr.setTurnsOnState(curr.getTurnsOnState() + 1); // TODO Check if this is in the correct
                                                          // place
        if (checkState(curr, FISH)) {
            return handleMove(curr, neighbors, grid, FISH);
        }
        else if (checkState(curr, SHARK)) {
            return handleShark(curr, neighbors, grid);
        }
        else {
            return EMPTY;
        }
    }
    
    @Override
    public void fillCellgrid (Cell[][] cellGrid, int[] entry) {
        int count = 0;
        for (int i = 1; i < cellGrid.length - 1; i++) {
            for (int j = 1; j < cellGrid[0].length - 1; j++) {
                cellGrid[i][j].setCurrState(entry[count]);
                if(entry[count]==SHARK){
                    cellGrid[i][j].setEnergy(startEnergy);
                }
                count++;
            }
        }
    }

    protected int handleMove (Cell curr, Cell[] neighbors, Cell[][] grid, int state) {
        while (isNeighborState(curr, neighbors, grid, EMPTY)) {
            int rand = getRand().nextInt(4);
            if (isEmpty(neighbors[rand]) && isNextEmpty(neighbors[rand])) {
                neighbors[rand].setNextState(state);
                if (curr.getTurnsOnState() > reproductionTime) {
                    curr.setTurnsOnState(0);
                    ;
                    return state;
                }
                else {
                    return EMPTY;
                }
            }
        }
        return state;
    }

    protected int handleShark (Cell curr, Cell[] neighbors, Cell[][] grid) {
        curr.setEnergy(curr.getEnergy() - 1);
        if (curr.getEnergy() == 0) {
            return EMPTY;
        }
        while (isNeighborState(curr, neighbors, grid, FISH)) {
            int rand = getRand().nextInt(4);
            if (checkState(neighbors[rand], FISH) && !checkNextState(neighbors[rand], SHARK)) {
                neighbors[rand].setNextState(SHARK);
                neighbors[rand].setEnergy(neighbors[rand].getEnergy() + fishEnergy);
                if (curr.getTurnsOnState() > reproductionTime) {
                    curr.setTurnsOnState(0);
                    curr.setEnergy(startEnergy);
                    return SHARK;
                }
                else {
                    return EMPTY;
                }
            }

        }
        return handleMove(curr, neighbors, grid, SHARK);
    }

    protected boolean isNeighborState (Cell curr, Cell[] neighbors, Cell[][] grid, int state) {
        for (int i = 0; i < 4; i++) {
            if (isEmpty(neighbors[i]) && neighbors[i].getNextState() == state) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void setSimulationParameters (String[] simParams) {

        reproductionTime = Double.parseDouble(simParams[0]);
        startEnergy = Double.parseDouble(simParams[1]);
        fishEnergy = Double.parseDouble(simParams[2]);
    }

}
