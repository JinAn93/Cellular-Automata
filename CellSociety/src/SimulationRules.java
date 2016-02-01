import java.util.*;


public abstract class SimulationRules {
    private final int numStates;
    private Random rand = new Random();
    
    public SimulationRules(int states){
        numStates = states;
    }
    protected abstract void applyRule(Cell[][] cellGrid);
    protected Random getRand(){
        return rand;
    }

}
