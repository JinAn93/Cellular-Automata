
public class ToroidalBoundsManager extends CellManager {

    @Override
    protected int determineBorderState (int i, int j, int type) {
        Cell[][] cellGrid = getCellList();
        if (i == 0) {
            return (getState(cellGrid[cellGrid.length - 2][j], type));
        }
        else if (i == cellGrid.length - 1) {
            return (getState(cellGrid[1][j], type));
        }
        else if (j == 0) {
            return (getState(cellGrid[i][cellGrid[0].length - 2], type));
        }
        else if (j == cellGrid[0].length - 1) {
            return (getState(cellGrid[i][1], type));
        }
        return 0;

    }

}
