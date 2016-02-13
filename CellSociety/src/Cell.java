import java.awt.Point;
import java.util.*;


/**
 * Cell serves to contain all the relevant information for each cell. They have been encapsulated by
 * declaring
 * as private variables and using getter and setter methods for each information.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */

public class Cell {
    private int currState = 0;
    private int nextState = 0;
    private int prevState = 0;
    private List<Double> cellParamList = new ArrayList<Double>();

    public int getPrevState () {
        return prevState;
    }

    public void setPrevState (int prevState) {
        this.prevState = prevState;
    }

    public List<Double> getCellParamList () {
        return cellParamList;
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
