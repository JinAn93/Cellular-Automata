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

        root = new Group();

        root.getChildren().add(makeButtons());
        
        myScene = new Scene(root, WIDTH,HEIGHT, Color.WHITE);
        s.setScene(myScene);

    }
    
    private void makeTime(){
      time = new Time(info);
      root.getChildren().addAll(time.getCellDisplay());

    }
    private Button makeButton(String name, EventHandler<ActionEvent> e){
    	Button b = new Button(name);
    	b.setOnAction(e);
    	return b;
    }
    private HBox makeButtons(){
    	
    	Button start = makeButton("start", e -> time.initSimulation());
    	Button pause = makeButton("pause", e -> time.pauseAnimation());
        Button resume = makeButton("resume", e -> time.resumeAnimation());
        Button step = makeButton("step", e -> time.stepAnimation());
        Button addspeed = makeButton("speed+", e -> time.setSpeed(time.getSpeed()+SPEED_CHANGE));
        Button reducespeed = makeButton("speed-", e -> time.setSpeed(time.getSpeed()-SPEED_CHANGE));
        Button loadfile = makeButton("load file", e-> fileLoader());
      

        HBox buttonlayout = new HBox(SPACING);
        
        buttonlayout.getChildren().addAll(start,pause, resume, step, addspeed, reducespeed, loadfile);
        buttonlayout.setLayoutY(HEIGHT*7/8);
        return buttonlayout;
    }
    
    private void fileLoader(){
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(new Stage());
    	XMLReader readfile = new XMLReader();
    	info = readfile.readXMLFile(file);
    	for (String e: info){
    	System.out.println("-"+e+"-");
    	}
    }
}
