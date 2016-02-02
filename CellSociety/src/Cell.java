
public class Cell {
    private int numStates;
    private int currState;
    private int nextState;
    private int turnsOnState = 0;
    private int energy;


    public Cell (int numState, int curr) {
        numStates = numState;
        currState = curr;
        nextState = 0;
    }

    public int getCurrState () {
        return currState;
    }

    public void setCurrState (int currState) {
        this.currState = currState;
    }

    public int getNextState () {
        return nextState;
    }

    public void setNextState (int nextState) {
        this.nextState = nextState;
    }
    
    
    public int getTurnsOnState () {
        return turnsOnState;
    }

    public void setTurnsOnState (int turnsOnState) {
        this.turnsOnState = turnsOnState;
    }
    public int getEnergy () {
        return energy;
    }

    public void setEnergy (int energy) {
        this.energy = energy;
    }

}
