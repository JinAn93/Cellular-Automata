import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

public class Time {
	private Timeline timeline;
	private CellManager Cells;
	private Display celldisplay;
	
	private double speed;
	
	private void checkConditions(){
		
	}
	public void initSimulation(){
		CellManager Cells = new CellManager();
		Display celldisplay = new Display(rows, columns, states);
		
		Cells.setUp();
		celldisplay.initDisplay();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
				   e -> step()));
				 timeline.playFromStart();
	}

	private void step(){
		Cells.updateStates();
		Cells.moveNextToCurrentState();
		
		celldisplay.updateDisplay(Cells.getCellList());
	}
	
	
	public double getSpeed(){
		return speed;
	}

	public void setSpeed(double s ){
		speed = s;
	}
	
	public void pauseAnimation(){
		timeline.pause();
	}
	
	public void resumeAnimation(){
		timeline.play();
	}

}
