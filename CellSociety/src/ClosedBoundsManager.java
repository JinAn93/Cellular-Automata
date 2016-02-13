
public class ClosedBoundsManager extends CellManager{

    @Override
    protected int determineBorderState (int i, int j, int type) {
        return SimulationRules.BLOCKED;
    }

}
