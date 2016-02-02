import javafx.animation.Timeline;
import javafx.scene.shape.Shape;
import javafx.util.*;
import java.util.*;

import javafx.animation.KeyFrame;

public class Time {
	private Timeline timeline;
	private Duration stepTime;
	private CellManager Cells;
	private Display celldisplay;
	private List<String> info;
	
	private double speed;
	public Time (List<String> inf){
		info = inf;
		initSimulation();
	}
	private void checkConditions(){
		if (info.isEmpty()){
//			error
		}
	}
	public void initSimulation(){
		checkConditions();
		CellManager Cells = new CellManager();
		Display celldisplay = new Display(info.get());
		
		Cells.setUp(rows);
		celldisplay.initDisplay();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(stepTime,
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
		stepTime = new Duration(speed);
	}
	
	public void pauseAnimation(){
		timeline.pause();
	}
	
	public void resumeAnimation(){
		timeline.play();
	}
	public void stepAnimation(){
		timeline.pause();
		step();
	}
	public List<Shape> getCellDisplay(){
		List<Shape> list = new ArrayList<Shape>();
		for (Shape[] array: celldisplay.getDisplay()){
			list.addAll(Arrays.asList(array));
		}
		return list;
	}
}
