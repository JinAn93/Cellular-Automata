
public class GameOfLifeRules extends SimulationRules {
    public static final int ALIVE = 1;
    public static final int DEAD = 2;
    
    public GameOfLifeRules (int states) {
        super(states);
        // TODO Auto-generated constructor stub
    }


    @Override
    protected int findNextState (Cell curr, Cell[] neighbors, Cell[][] grid) {
        if(isAlive(curr)){
            if(countNeighborState(neighbors,ALIVE)<2){
                return DEAD;
            }
            else if(countNeighborState(neighbors,ALIVE)<4){
                return ALIVE;
            }
            else{
                return DEAD;
            }
        }
        else{
            if(countNeighborState(neighbors,DEAD)==3){
                return ALIVE;
            }
            else{
                return DEAD;
            }
        }
    }
    
    protected boolean isDead(Cell curr){
        return(curr.getCurrState()==DEAD);
    }
    
    protected boolean isAlive(Cell curr){
        return(curr.getCurrState()==ALIVE);
    }

}
