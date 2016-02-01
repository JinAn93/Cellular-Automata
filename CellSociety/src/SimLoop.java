import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class SimLoop {
    private Timeline simLoop;
    private Duration stepTime;

    public SimLoop (double timeInMillis) {
        setStepTime(timeInMillis);
        buildAndSetLoop();
    }

    protected void buildAndSetLoop () {
        KeyFrame frame = new KeyFrame(stepTime, new EventHandler<ActionEvent>() {

            @Override
            public void handle (ActionEvent event) {
                update();
            }
        });

        simLoop = new Timeline();
        simLoop.setCycleCount(Timeline.INDEFINITE);
        simLoop.getKeyFrames().add(frame);
    }

    protected void update () {
        // THIS WILL BE CALLED EVERY STEP OF SIMULATION
        
    }

    protected void setStepTime (double time) {
        stepTime = new Duration(time);
    }
}
