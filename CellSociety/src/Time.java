import javafx.animation.Timeline;
import javafx.scene.shape.Shape;
import javafx.util.*;
import java.util.*;
import javafx.animation.KeyFrame;

/**
 * Time is the class that controls the animation. It creates a CellManager and a Display object, and
 * steps through the simulations using a Timeline object. This class is used by UserInterface.
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class Time {

    public static final List<String> simulations = Arrays.asList("Segregation", "Predator_Prey",
                                                     "Spreading_Fire", "Game_of_Life");
    private static final Duration STEPTIME = new Duration(1000);
    private Timeline timeline;
    private CellManager Cells;
    private Display celldisplay;
    private double speed = 1;
    
    /**
     * Info is the String returned by the XML filereader (in userinterface) which contains the information about the simulation.
     * This is used to initialize the rest of the simulation. 
     * @param info
     */
    public Time () {
    }
    /**
     * Takes the string created in XMLReader and gets the information, putting it into variables that will 
     * be called when making the cellmanager and display.
     * @param info
     */
//    private void settingsFromFile (String info) {
//        String[] settings = info.split(",");
//        name = settings[0];
//        title = settings[1];
//        author = settings[2];
//        numstates = Integer.parseInt(settings[3]);
//        String[] dim = settings[4].split("x");
//        n = Integer.parseInt(dim[0]);
//        m = Integer.parseInt(dim[1]);
//
//        char[] ini = settings[5].toCharArray();
//        initial = new int[ini.length];
//        for (int i = 0; i < ini.length; i++) {
//            initial[i] = ini[i] - '0';
//        }
//        if (settings.length > 6) {
//            params = settings[6].split(" ");
//        }
//        else {
//            params = null;
//        }
//
//    }
    /**
     * creates new cellmanager, display, and timeline objects. Uses getcellList in cellmanager to pass 
     * the updated states into Display celldisplay. Makes an indefinitely long timeline that "steps".
     */
    public void initSimulation (int row, int column, int numStates, String name, int[] initial, String[] params) {
        Cells = new CellManager();
        celldisplay = new Display(row, column, numStates);
        Cells.setUp(row, column, simulations.lastIndexOf(name), initial, params);
        celldisplay.updateDisplay(Cells.getCellList());

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(STEPTIME,
                                                 e -> step()));
    }
    /**
     * This updates cells (the cellmanager) and celldisplay (the display) using their methods for this.
     */
    private void step () {
        Cells.updateStates();
        Cells.moveNextToCurrentState();
        celldisplay.updateDisplay(Cells.getCellList());
    }

    public double getSpeed () {
        return speed;
    }

    public void setSpeed (double s) {
        timeline.setRate(s);
        speed = s;
    }

    public void startAnimation () {
        timeline.playFromStart();
    }

    public void pauseAnimation () {
        timeline.pause();
    }

    public void resumeAnimation () {
        timeline.play();
    }

    public void stepAnimation () {
        timeline.pause();
        step();
    }
    /**
     * Returns a list of shapes (as opposed to a 2-D array) so that these can be added to the root group in interface 
     * @return
     */
    public List<Shape> getCellDisplay () {
        List<Shape> list = new ArrayList<Shape>();
        for (Shape[] array : celldisplay.getDisplay()) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }
}
