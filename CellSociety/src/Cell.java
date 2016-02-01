
public class Cell {
    private int numStates;
    private int currState;
    private int nextState;
    
    public Cell(int numState, int curr){
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
}
