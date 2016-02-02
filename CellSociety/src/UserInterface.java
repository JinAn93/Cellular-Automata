import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface {
    public static final double WIDTH = 500;
    public static final double HEIGHT = 500;
    public static final double SPEED_CHANGE = 10;
    public static final double SPACING = 10;
    private Scene myScene;
    private Group root;
    private Time time;
    
    public void initStage(Stage s){
        s.setTitle("Cell Society");

        time = new Time();
        root = new Group();

        root.getChildren().add(makeButtons());
        root.getChildren().addAll(time.getCellDisplay());
        
        myScene = new Scene(root, WIDTH,HEIGHT, Color.WHITE);
        s.setScene(myScene);

    }
    
    private HBox makeButtons(){
    	Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button resume = new Button("Resume");
        Button step = new Button("Step");
        Button addspeed = new Button("Speed +");
        Button reducespeed = new Button("Speed -");
        Button loadfile = new Button("Load File");
        
        start.setOnAction(e -> time.initSimulation());
        pause.setOnAction(e -> time.pauseAnimation());
        resume.setOnAction(e -> time.resumeAnimation());
        step.setOnAction(e -> time.stepAnimation());
        addspeed.setOnAction(e -> time.setSpeed(time.getSpeed()+SPEED_CHANGE));
        reducespeed.setOnAction(e -> time.setSpeed(time.getSpeed()-SPEED_CHANGE));

        loadfile.setOnAction(e-> fileLoader());
        HBox buttonlayout = new HBox(SPACING);
        buttonlayout.setLayoutY(HEIGHT*7/8);
        buttonlayout.getChildren().addAll(start,pause, resume, step, addspeed, reducespeed, loadfile);
        return buttonlayout;
    }
    
    private void fileLoader(){
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(new Stage());
    
//    	XML use file
    }
}
