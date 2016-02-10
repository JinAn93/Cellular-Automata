import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * UserInterface is the class that displays the grid and the buttons necessary for the user to
 * interact
 * with the program. It also handles said input. It creates an instance of the Time class, and uses
 * its
 * public methods. It implements a resources package to make the buttons that control the animation
 * (with a
 * properties file) and to format these (with a css file).
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class UserInterface {
    public static final double WIDTH = 580;
    public static final double HEIGHT = 640;
    public static final double SPEED_CHANGE = 0.3;
    public static final double BUTTON_SPACING = 5;
    public static final double BUTTON_HEIGHT = HEIGHT - 15;
    public static final double GRAPH_HEIGHT = 125;
    private static final int SETTINGINDEX = 7;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String STYLESHEET = "custom.css";
    private static final String BUTTONLABELS = "ButtonLabels";
    private String myName, myTitle, myAuthor;
    private int myNumStates, myRow, myColumn, myShape, myGridSize;
    private int[] myInitial;
    private String[] myParams;
    private Scene myScene;
    private Group root;
    private Time time = null;
    private String info = null;

    private Button start;
    private Button pause;
    private Button resume;
    private Button step;
    private Button addspeed;
    private Button reducespeed;
    private Button reset;

    /**
     * Called by main in order to set the stage and the scene.
     * 
     * @param s
     */
    public void initStage (Stage s) {
        s.setTitle("Cell Society");
        s.setResizable(false);
        root = new Group();
        root.getChildren().add(makeButtons());
        myScene = new Scene(root, WIDTH, HEIGHT, Color.SKYBLUE);
        myScene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
        s.setScene(myScene);
    }

    /**
     * Makes a new Time object, enabling the animation control buttons. Clears the
     * previous displayed grid (if there is one) and puts in a new one.
     */
    private void makeTime () {
        time = new Time();
        time.initSimulation(myRow, myColumn, myNumStates, myName, myInitial, myParams, myShape);
        enableButtons();
        Node n = root.getChildren().get(0);
        root.getChildren().clear();
        root.getChildren().add(n);
        Polygon[][] displayArray = time.getCellDisplay();
        for (int i=0; i<displayArray.length;i++){
                for (int j=0; j<displayArray[0].length;j++){
                        final int k = i;
                        final int l =j;
                        displayArray[i][j].setOnMouseClicked(e->time.changeState(k,l));
                        root.getChildren().add(displayArray[i][j]);
                }
        }

        LineChart<Number, Number> c = time.getCellGraph();
        c.setMaxHeight(GRAPH_HEIGHT);
        root.getChildren().add(c);
}

    /**
     * Returns a button, getting its label from the resource file. Also sets its action.
     * 
     * @param name -of the button (for resource file)
     * @param e -event when clicked)
     * @return
     */
    private Button makeButton (String name, EventHandler<ActionEvent> e) {
        ResourceBundle myResources =
                ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + BUTTONLABELS);
        Button b = new Button(myResources.getString(name));
        b.setOnAction(e);
        return b;
    }

    /**
     * disables the control buttons if a file has not been loaded
     */
    private void enableButtons () {
        start.setDisable(time == (null));
        pause.setDisable(time == null);
        resume.setDisable(time == null);
        step.setDisable(time == null);
        addspeed.setDisable(time == null);
        reducespeed.setDisable(time == null);
        reset.setDisable(time == null);
    }

    /**
     * Returns an HBox that contains all buttons. Uses makeButton to make these.
     * 
     * @return
     */
    private HBox makeButtons () {
        start = makeButton("start", e -> {
            time.startAnimation();
        });

        pause = makeButton("pause", e -> {
            time.pauseAnimation();
        });
        resume = makeButton("resume", e -> {
            time.resumeAnimation();
        });
        step = makeButton("step", e -> {
            time.stepAnimation();
        });
        addspeed = makeButton("addspeed", e -> {
            time.setSpeed(time.getSpeed() + SPEED_CHANGE);
        });
        reducespeed = makeButton("reducespeed", e -> {
            if (time.getSpeed() - SPEED_CHANGE > 0) {
                time.setSpeed(time.getSpeed() - SPEED_CHANGE);
            }
        });
        reset = makeButton("reset", e -> {
            time.pauseAnimation();
            makeTime();
        });
        Button loadfile = makeButton("loadfile", e -> fileLoader());
        enableButtons();

        HBox buttonlayout = new HBox(BUTTON_SPACING);

        buttonlayout.getChildren().addAll(start, pause, resume, step, addspeed, reducespeed, reset,
                                          loadfile);
        buttonlayout.setLayoutY(BUTTON_HEIGHT);
        buttonlayout.setLayoutX(BUTTON_SPACING);
        return buttonlayout;
    }

    /**
     * Opens a new window for choosing XML file to start animation
     */
    private void fileLoader () {
        boolean isFileReady = false;
        while (!isFileReady) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                XMLReader readfile = new XMLReader();
                info = readfile.readXMLFile(file);
                extractFile(info);
                String errorCheck =
                        readfile.checkError(myRow, myColumn, myName, myInitial, myParams);
                if (XMLReader.errorTypes.get(XMLReader.NO_ERROR) == errorCheck) {
                    isFileReady = true;
                }
                else {
                    showAlertMessage(errorCheck);
                }
            }
        }
        makeTime();
    }

    private void showAlertMessage (String errorType) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(errorType);
        alert.showAndWait();
    }

    private void extractFile (String info) {
        String[] settings = info.split(",");
        myName = settings[0];
        myTitle = settings[1];
        myAuthor = settings[2];
        myShape = Integer.parseInt(settings[3]);
        myNumStates = Integer.parseInt(settings[4]);
        String[] dim = settings[5].split("x");
        myRow = Integer.parseInt(dim[0]);
        myColumn = Integer.parseInt(dim[1]);
        myGridSize = myRow * myColumn;
        myInitial = considerInitConfig(settings[6]);

        if (settings.length > SETTINGINDEX) {
            myParams = settings[7].split(" ");
        }
        else {
            myParams = null;
        }
    }

    private int[] considerInitConfig (String initConfig) {
        int[] init = new int[myGridSize];
        System.out.println(initConfig);
        if (isRandom(initConfig)) {
            System.out.println("random!");
            init = setRandomConfig();
        }
        else if (isProbRandom(initConfig)) {
            System.out.println("random probability!");
            init = setProbRandomConfig(initConfig);
        }
        else {
            System.out.println("initial config is given!");
            char[] ini = initConfig.toCharArray();
            init = new int[ini.length];
            for (int i = 0; i < ini.length; i++) {
                init[i] = ini[i] - '0';
            }
        }
        return init;
    }

    private int[] setProbRandomConfig (String initConfig) {
        List<Integer> stateList = new ArrayList<Integer>();
        int[] probRandomConfig = new int[myGridSize];
        double[] randomEachState = new double[myNumStates];
        String[] probs = initConfig.split(" ");
        int setState = 0, countGrid = 0;

        for (int i = 0; i < probs.length; i++) {
            randomEachState[i] = Double.parseDouble(probs[i]) * myGridSize;
        }

        for (int i = 0; i < myGridSize; i++) {
            if (Math.round(randomEachState[setState]) == countGrid && (setState != myNumStates - 1)) {
                setState++;
                countGrid = 0;
            }
            stateList.add(setState);
            countGrid++;
        }
        Collections.shuffle(stateList);
        for (int i = 0; i < stateList.size(); i++) {
            probRandomConfig[i] = stateList.get(i);
        }
        return probRandomConfig;
    }

    private int[] setRandomConfig () {
        int[] randomConfig = new int[myRow * myColumn];
        for (int i = 0; i < randomConfig.length; i++) {
            randomConfig[i] = new Random().nextInt(myNumStates - 1);
        }
        return randomConfig;
    }

    private boolean isRandom (String initConfig) {
        if (initConfig.equals("random")) { // Use Resource Bundle
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isProbRandom (String initConfig) {
        if (initConfig.contains(" "))
            return true;
        else {
            return false;
        }
    }
}
