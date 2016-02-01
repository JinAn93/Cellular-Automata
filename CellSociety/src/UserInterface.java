import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
    public static final double WIDTH = 500;
    public static final double HEIGHT = 500;
    private Scene myScene;
    private Group root;
    
    public void initStage(Stage s){
        root = new Group();
        myScene = new Scene(root, WIDTH,HEIGHT, Color.WHITE);
        s.setScene(myScene);
        s.setTitle("Cell Society");
    }
}
