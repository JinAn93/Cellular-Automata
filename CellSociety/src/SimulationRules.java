import java.util.*;


public abstract class SimulationRules {
    private final int numStates;
    private Random rand = new Random();
    
    public SimulationRules(){
        
    }
    protected abstract void applyRule(List<ArrayList<Cell>> cellList);
    protected Random getRand(){
        return rand;
    }

}
