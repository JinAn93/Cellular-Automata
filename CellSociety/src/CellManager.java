import java.util.*;

public class CellManager {
    private Cell[][] cellGrid;
    
    public void generateList(int n){
        cellGrid = new Cell[n+2][n+2];
    }
    public Cell[][] getCellList(){
        return cellGrid;
    }
}
