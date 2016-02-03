
public abstract class Cell {
    private int currState;
    private int nextState;
    


    public Cell (int curr) {
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
