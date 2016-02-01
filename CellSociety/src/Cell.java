
public class Cell {
    private int numStates;
    private int currState;
    private int nextState;
    
    public Cell(int numState, int curr){
        numStates = numState;
        currState = curr;
        nextState = 0;
    }
}
