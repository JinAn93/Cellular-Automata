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
	
	private double speed;
	
	
	private String name;
	private String title;
	private String author;
	private double settings;
	private int n;
	private int m;
	private String initial;
	
	public Time (List<String> info){
		settingsFromFile(info);
		//break up string w/method
		initSimulation();
	}
	
	private void settingsFromFile(List<String> info){
		name = info.get(1);
		title = info.get(2);
		author= info.get(3);
		settings = Integer.parseInt(info.get(4));
		String[] dim = info.get(5).split("x");
		n = Integer.parseInt(dim[0]);
		m = Integer.parseInt(dim[1]);
		initial = info.get(6);
	}
	
	private void checkConditions(){
		if (info.isEmpty()){
//			error
		}
	}
	
	
	
	
	public void initSimulation(){
		checkConditions();
		CellManager Cells = new CellManager();
		Display celldisplay = new Display(n, m, );
		
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
