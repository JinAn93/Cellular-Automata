import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
    public static final double WIDTH = 500;
    public static final double HEIGHT = 500;
    public static final double SPEED_CHANGE = 10;
    private Scene myScene;
    private Group root;
    private Time time;
    
    public void initStage(Stage s){
        time = new Time();
        root = new Group();
        
        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button resume = new Button("Resume");
        Button addspeed = new Button("Speed +");
        Button reducespeed = new Button("Speed -");
        Button loadfile = new Button("Load File");
        
        start.setOnAction(e -> time.initSimulation());
        pause.setOnAction(e -> time.pauseAnimation());
        resume.setOnAction(e -> time.resumeAnimation());
        addspeed.setOnAction(e -> time.setSpeed(time.getSpeed()+SPEED_CHANGE));
        reducespeed.setOnAction(e -> time.setSpeed(time.getSpeed()-SPEED_CHANGE));

        loadfile.setOnAction(e-> xml);
        root.getChildren().addAll(start,pause, resume, addspeed, reducespeed);
        
        
        myScene = new Scene(root, WIDTH,HEIGHT, Color.WHITE);
        s.setScene(myScene);
        s.setTitle("Cell Society");
        
        //make timelines
    }
}
