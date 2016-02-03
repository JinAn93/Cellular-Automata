
public class Cell {
    private int currState = 0;
    private int nextState = 0;
    private int turnsOnState = 0;
    private int energy;


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
