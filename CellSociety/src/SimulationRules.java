import java.util.*;


public abstract class SimulationRules {

    public static final int OPEN_NEXT = -2;
	public static final int BLOCKED = -1;
	public static final int EMPTY = 0;
	
	public SimulationRules () {
	}

	protected void applyRule (Cell[][] cellGrid) {
		for (int i = 1; i < cellGrid.length - 1; i++) {
			for (int j = 1; j < cellGrid[0].length - 1; j++) {
			    if(checkNextState(cellGrid[i][j],OPEN_NEXT)){
				Cell[] neighbors = setNeighbors(cellGrid, i, j);

				int nextState = findNextState(cellGrid[i][j], neighbors,
						cellGrid);
				cellGrid[i][j].setNextState(nextState);
			    }
			}
		}
	}

	public void fillCellgrid (Cell[][] cellGrid, int[] entry) {
		int count = 0;
		for (int j = 1; j < cellGrid[0].length - 1; j++) {
			for (int i = 1; i < cellGrid.length - 1; i++) {
				cellGrid[i][j].setCurrState(entry[count]);
				cellGrid[i][j].setNextState(OPEN_NEXT);
				count++;
			}
		}
	}

	protected abstract int findNextState (Cell curr,
			Cell[] neighbors,
			Cell[][] grid);

	protected abstract void setSimulationParameters (String[] simParams);

	protected Random getRand () {
		return new Random();
	}

	protected boolean checkState (Cell curr, int state) {
		return (curr.getCurrState() == state);
	}

	protected boolean checkNextState (Cell curr, int state) {
		return (curr.getNextState() == state);
	}

	protected double calculateLikeNeighborsPercent (Cell curr, Cell[] neighbors) {
		double likeCount = countNeighborState(neighbors, curr.getCurrState());
		double occupiedNeighbors = neighbors.length - countNeighborState(neighbors, EMPTY);
		return likeCount / occupiedNeighbors;
	}

	protected double countNeighborState (Cell[] neighbors, int state) {
		double count = 0;
		for (int i = 0; i < neighbors.length; i++) {
			if (checkState(neighbors[i],state)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Order of Array: {North, South, East, West, NorthEast, NorthWest,
	 * SouthWest, SouthEast} Index: 0 1 2 3 4 5 6 7
	 * 
	 * @param cellGrid
	 * @param i
	 * @param j
	 * @return
	 */
	public Cell[] setNeighbors (Cell[][] cellGrid, int i, int j) {
		Cell[] neighbors = { cellGrid[i - 1][j], cellGrid[i + 1][j],
				cellGrid[i][j + 1], cellGrid[i][j - 1], cellGrid[i - 1][j + 1],
				cellGrid[i - 1][j - 1], cellGrid[i + 1][j - 1],
				cellGrid[i + 1][j + 1] };
		return neighbors;
	}
}
