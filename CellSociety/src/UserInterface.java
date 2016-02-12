import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private static final int SETTINGINDEX = 8;
    private static final int STARTINDEX = 1;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String STYLESHEET = "custom.css";
    private static final String BUTTONLABELS = "ButtonLabels";
	private static final String SLIDERLABELS = "Sliders";
    private String myName, myTitle, myAuthor, mySetting, myConfig;
    private int myNumStates, myRow, myColumn, myShape, myGridSize, myEdge;
    private int[] myInitial;
    private String[] myParams;
    private Scene myScene;
    private XMLManager myXMLManager;
    private Group root;
    private Time time = null;
    private String info = null;

    private BorderPane border;

    private Button start;
    private Button pause;
    private Button resume;
    private Button step;
    private Button addspeed;
    private Button reducespeed;
    private Button reset;
    private Button save;

    /**
     * Called by main in order to set the stage and the scene.
     * 
     * @param s
     */
    public void initStage (Stage s) {
        s.setTitle("Cell Society");
        s.setResizable(false);
        root = new Group();
        border = new BorderPane();
        border.setBottom(makeButtons());
        border.setPrefSize(WIDTH, HEIGHT);
        
        root.getChildren().add(border);
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
        time.initSimulation(myRow, myColumn, myNumStates, myName, myInitial, myParams, myShape, myEdge);
        enableButtons();
        root.getChildren().retainAll(border);
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
        border.setTop(c);
        if(myParams!=null){
            border.setRight(makeSliders());
        }               
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
        save.setDisable(time == null);
    }

    /**
     * Returns an HBox that contains all buttons. Uses makeButton to make these.
     * 
     * @return
     */
    private HBox makeButtons () {
        start = makeButton("start", e -> time.startAnimation());
        pause = makeButton("pause", e -> time.pauseAnimation());
        resume = makeButton("resume", e -> time.resumeAnimation());
        step = makeButton("step", e -> time.stepAnimation());
        addspeed = makeButton("addspeed", e -> time.setSpeed(time.getSpeed() + SPEED_CHANGE));
        reducespeed = makeButton("reducespeed", e -> {
            if (time.getSpeed() - SPEED_CHANGE > 0) {
                time.setSpeed(time.getSpeed() - SPEED_CHANGE);
            }
        });
        reset = makeButton("reset", e -> {
            time.pauseAnimation();
            makeTime();
        });
        save = makeButton("save", e-> {
            time.pauseAnimation();
            saveFile();
        });
        Button loadfile = makeButton("loadfile", e -> fileLoader());
        enableButtons();

        HBox buttonlayout = new HBox(BUTTON_SPACING);

        buttonlayout.getChildren().addAll(start, pause, resume, step, addspeed, reducespeed, reset,
                                          loadfile, save);

        return buttonlayout;
    }
    
    private void saveFile(){
        myXMLManager.writeXMLFile(myName, myTitle, myAuthor, myShape, myEdge, myNumStates, updateSetting(), myRow, myColumn, updateConfig());
    }
    
    private String updateConfig(){
        String updatedConfig = new String();
        Cell[][] recentConfig = time.getUpdatedConfig();
        for(int i=0; i<recentConfig.length; i++){
            for(int j=0; j<recentConfig[0].length; j++){
                updatedConfig += recentConfig[i][j].getCurrState();
            }
        }
        return updatedConfig;
    }
    
    private String updateSetting(){
        String updatedSetting = new String();
        if(myParams == null)
            return updatedSetting;
        else{
            for(int i=0; i<myParams.length; i++){
                updatedSetting += (" "+myParams[i]);
            }
            updatedSetting = updatedSetting.substring(STARTINDEX);
        }
        return updatedSetting;
    }
    
    private VBox makeSliders(){
    	VBox sliderlayout = new VBox(BUTTON_SPACING);
    	for (int i= 0; i<myParams.length; i++){
    		double curr = Double.parseDouble(myParams[i]);
    		double max = 10; //TODO:figure this out
    		if (curr<1){
    			max = 1.0;
    		}
    	    sliderlayout.getChildren().add(makeSlider(i, curr, max));
    	    sliderlayout.getChildren().add(makeLabel(i));
            
    	}

    	return sliderlayout;
    }

	private Slider makeSlider(int i, double curr, double max) {
		Slider slider = new Slider(0, max, curr );
		
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			 myParams[i]= String.valueOf(newValue.intValue());
			 time.updateParams(myParams);
		
		});
		return slider;
	}
	private Label makeLabel(int i){
		ResourceBundle myResources =
                ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SLIDERLABELS);
        String name = (myResources.getString(myName+i));
        Label label = new Label(name);
        return label;
	}
    
    
    

    /**
     * Opens a new window for choosing XML file to start animation
     */
    private void fileLoader () {
        boolean isFileReady = false;
        while (!isFileReady) {
            myXMLManager = new XMLManager();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                info = myXMLManager.readXMLFile(file);
                extractFile(info);
                String errorCheck =
                        myXMLManager.checkError(myRow, myColumn, myName, myInitial, myParams);
                if (XMLManager.errorTypes.get(XMLManager.NO_ERROR) == errorCheck) {
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
        myEdge = Integer.parseInt(settings[4]);
        myNumStates = Integer.parseInt(settings[5]);
        String[] dim = settings[6].split("x");
        myRow = Integer.parseInt(dim[0]);
        myColumn = Integer.parseInt(dim[1]);
        myGridSize = myRow * myColumn;
//        myInitial = considerInitConfig(settings[7]);
        myConfig = settings[7];
        myInitial = new Config(myConfig,  myGridSize,  myNumStates).considerInitConfig();
        
        if (settings.length > SETTINGINDEX) {
            myParams = settings[8].split(" ");
        }
        else {
            myParams = null;
        }
    }

    //TODO: delete this
//    private int[] considerInitConfig (String initConfig) {
//        int[] init = new int[myGridSize];
//        if (isRandom(initConfig)) {
//            init = setRandomConfig();
//        }
//        else if (isProbRandom(initConfig)) {
//            init = setProbRandomConfig(initConfig);
//        }
//        else {
//            char[] ini = initConfig.toCharArray();
//            init = new int[ini.length];
//            for (int i = 0; i < ini.length; i++) {
//                init[i] = ini[i] - '0';
//            }
//        }
//        return init;
//    }
//
//    private int[] setProbRandomConfig (String initConfig) {
//        List<Integer> stateList = new ArrayList<Integer>();
//        int[] probRandomConfig = new int[myGridSize];
//        double[] randomEachState = new double[myNumStates];
//        String[] probs = initConfig.split(" ");
//        int setState = 0, countGrid = 0;
//
//        for (int i = 0; i < probs.length; i++) {
//            randomEachState[i] = Double.parseDouble(probs[i]) * myGridSize;
//        }
//
//        for (int i = 0; i < myGridSize; i++) {
//            if (Math.round(randomEachState[setState]) == countGrid && (setState != myNumStates - 1)) {
//                setState++;
//                countGrid = 0;
//            }
//            stateList.add(setState);
//            countGrid++;
//        }
//        Collections.shuffle(stateList);
//        for (int i = 0; i < stateList.size(); i++) {
//            probRandomConfig[i] = stateList.get(i);
//        }
//        return probRandomConfig;
//    }
//
//    private int[] setRandomConfig () {
//        int[] randomConfig = new int[myRow * myColumn];
//        for (int i = 0; i < randomConfig.length; i++) {
//            randomConfig[i] = new Random().nextInt(myNumStates - 1);
//        }
//        return randomConfig;
//    }
//
//    private boolean isRandom (String initConfig) {
//        if (initConfig.equals("random")) { // Use Resource Bundle
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    private boolean isProbRandom (String initConfig) {
//        if (initConfig.contains(" "))
//            return true;
//        else {
//            return false;
//        }
//    }
}
