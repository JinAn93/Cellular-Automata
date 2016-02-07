/**
 * Cell serves to contain all the relevant information for each cell. They have been encapsulated by declaring
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
    private double turnsOnState = 0;
    private double energy;

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

    public double getTurnsOnState () {
        return turnsOnState;
    }

    public void setTurnsOnState (double turnsOnState) {
        this.turnsOnState = turnsOnState;
    }

    public double getEnergy () {
        return energy;
    }

    public void setEnergy (double energy) {
        this.energy = energy;
    }

}
