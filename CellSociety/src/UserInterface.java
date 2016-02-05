import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class UserInterface {
    public static final double WIDTH = 530;
    public static final double HEIGHT = 500;
    public static final double SPEED_CHANGE = 0.3;
    public static final double SPACING = 5;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String STYLESHEET = "custom.css";
    private String RESOURCE_PACKAGE_BUTTONS = "Resources/ButtonLabels";
    private Scene myScene;
    private Group root;
    private Time time;
    private String info = null;

    public void initStage (Stage s) {
        s.setTitle("Cell Society");
        s.setResizable(false);
        root = new Group();
        root.getChildren().add(makeButtons());
        myScene = new Scene(root, WIDTH, HEIGHT, Color.SKYBLUE);
        myScene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
        s.setScene(myScene);
    }

    private void makeTime () {

        time = new Time(info);
        Node n = root.getChildren().get(0);
        root.getChildren().clear();
        root.getChildren().add(n);
        root.getChildren().addAll(time.getCellDisplay());

    }

    private Button makeButton (String name, EventHandler<ActionEvent> e) {
        ResourceBundle myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE_BUTTONS);

        Button b = new Button(myResources.getString(name));
        b.setOnAction(e);
        return b;
    }

    private HBox makeButtons () {

        // TODO: make a resources file for buttons
        Button start = makeButton("start", e -> {
            if (time != null) {
                time.startAnimation();
            }
        });
        Button pause = makeButton("pause", e -> {
            if (time != null) {
                time.pauseAnimation();
            }
        });
        Button resume = makeButton("resume", e -> {
            if (time != null) {
                time.resumeAnimation();
            }
        });
        Button step = makeButton("step", e -> {
            if (time != null) {
                time.stepAnimation();
            }
        });
        Button addspeed = makeButton("addspeed", e -> {
            if (time != null) {
                time.setSpeed(time.getSpeed() + SPEED_CHANGE);
            }
        });
        Button reducespeed = makeButton("reducespeed", e -> {
            if (time != null && time.getSpeed() - SPEED_CHANGE > 0) {
                time.setSpeed(time.getSpeed() - SPEED_CHANGE);
            }
        });
        Button reset = makeButton("reset", e -> {
            if (time != null) {
                time.pauseAnimation();
                makeTime();
            }
        });
        Button loadfile = makeButton("loadfile", e -> fileLoader());

        HBox buttonlayout = new HBox(SPACING);

        buttonlayout.getChildren().addAll(start, pause, resume, step, addspeed, reducespeed, reset,
                                          loadfile);
        // TODO: figure out layout
        buttonlayout.setLayoutY(HEIGHT * 7 / 8);
        buttonlayout.setLayoutX(SPACING);
        return buttonlayout;
    }

    private void fileLoader () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            XMLReader readfile = new XMLReader();
            info = readfile.readXMLFile(file);
            makeTime();
        }
    }

}
