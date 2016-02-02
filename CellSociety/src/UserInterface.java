import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private List<String> info = null;
    
    public void initStage(Stage s){
        s.setTitle("Cell Society");

        time = new Time(info);
        root = new Group();

        root.getChildren().add(makeButtons());
        root.getChildren().addAll(time.getCellDisplay());
        
        myScene = new Scene(root, WIDTH,HEIGHT, Color.WHITE);
        s.setScene(myScene);

    }
    
    
    private Button makeButton(String name, EventHandler<ActionEvent> e){
    	Button b = new Button(name);
    	b.setOnAction(e);
    	return b;
    }
    private HBox makeButtons(){
    	
    	Button start = makeButton(???, e -> time.initSimulation());
    	Button pause = makeButton(???, e -> time.pauseAnimation());
        Button resume = makeButton(???, e -> time.resumeAnimation());
        Button step = makeButton(???, e -> time.stepAnimation());
        Button addspeed = makeButton(???, e -> time.setSpeed(time.getSpeed()+SPEED_CHANGE));
        Button reducespeed = makeButton(???, e -> time.setSpeed(time.getSpeed()-SPEED_CHANGE));
        Button loadfile = makeButton(???, e-> fileLoader());
      

        HBox buttonlayout = new HBox(SPACING);
        
        buttonlayout.getChildren().addAll(start,pause, resume, step, addspeed, reducespeed, loadfile);
        buttonlayout.setLayoutY(HEIGHT-buttonlayout.getHeight());
        return buttonlayout;
    }
    
    private void fileLoader(){
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(new Stage());
    	XMLReader readfile = new XMLReader();
    	info = readfile.readXMLFile(file);

    }
}
