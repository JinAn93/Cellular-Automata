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
    // PredatorPrey Properties
    private double turnsOnState = 0;
    private double energy;
    // ForagingAnts Properties
    private Cell orientation;
    private boolean isNest;
    private boolean isFood;
    private Integer[] pher = new Integer[2]; //pher[0] = homePher, pher[1] = foodPher

    public Cell getOrientation () {
        return orientation;
    }

    public void setOrientation (Cell orientation) {
        this.orientation = orientation;
    }

    public int getPher (int type) {
        return pher[type];
    }

    public void setHomePher (int pher, int type) {
        this.pher[type] = pher;
    }

    public boolean isNest () {
        return isNest;
    }

    public void setNest (boolean isNest) {
        this.isNest = isNest;
    }

    public boolean isFood () {
        return isFood;
    }

    public void setFood (boolean isFood) {
        this.isFood = isFood;
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
