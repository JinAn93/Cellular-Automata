/**
 * This subclass of CellManager sets the states of border cells as BLOCKED 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 */

public class ClosedBoundsManager extends CellManager{

    @Override
    protected int determineBorderState (int i, int j, int type) {
        return SimulationRules.BLOCKED;
    }

}
