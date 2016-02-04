import java.util.*;


public class SpreadingFireRules extends SimulationRules {
	private static final int TREE = 1;
	private static final int BURNING = 2;
	private double probCatch;

	public SpreadingFireRules () {
		super();
	}

	@Override
	protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
//		if (isEmpty(curr)) {
//			return EMPTY;
//		}
//		else 
			if (checkState(curr, BURNING)) {
				System.out.println("+");
			return EMPTY;
		}
		else if (checkState(curr, TREE)) {			
			if (checkBurning(neighbors[0], neighbors[1], neighbors[2], neighbors[3]) && getRand().nextDouble() > probCatch) {
				return BURNING;
			}
			else {
				return TREE;
			}
		}
		return 0;
	}

	private boolean checkBurning (Cell North, Cell South, Cell East, Cell West) {
		return checkState(North, BURNING) || checkState(South, BURNING) ||
				checkState(East, BURNING) || checkState(West, BURNING);
	}

	@Override
	protected void setSimulationParameters (String[] simParams) {
		probCatch = Double.parseDouble(simParams[0]);     
		System.out.println(probCatch);

	}

}
