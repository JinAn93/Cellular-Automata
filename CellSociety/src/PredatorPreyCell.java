
public class PredatorPreyCell extends Cell{
    private int turnsOnState = 0;
    private int energy;
    
    public PredatorPreyCell (int curr) {
        super(curr);
        // TODO Auto-generated constructor stub
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
