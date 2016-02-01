import java.util.*;

public class CellManager {
    private List<ArrayList<Cell>> cellList;
    
    public void generateList(){
        cellList = new ArrayList<ArrayList<Cell>>();
    }
    public List<ArrayList<Cell>> getCellList(){
        return cellList;
    }
}
