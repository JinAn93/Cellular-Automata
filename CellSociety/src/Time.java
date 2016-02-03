import javafx.animation.Timeline;
import javafx.scene.shape.Shape;
import javafx.util.*;
import java.util.*;

import javafx.animation.KeyFrame;

public class Time {
	private List<String> simulations =Arrays.asList("Segregation", "Predator_Prey", "Spreading_Fire","Game_of_Life");
	
	private Timeline timeline;
	private Duration stepTime;
	private CellManager Cells;
	private Display celldisplay;
	
	private double speed;
	
	
	private String name;
	private String title;
	private String author;
	private String settings;
	private int numstates;
	private int n;
	private int m;
	private int[] initial;
	
	public Time (List<String> info){
		settingsFromFile(info);
		//break up string w/method
		initSimulation();
	}
	
	private void settingsFromFile(List<String> info){
		
		name = info.get(0);
		title = info.get(1);
		author= info.get(2);
		settings = info.get(3);
		String[] dim = info.get(4).split("x");
		n = Integer.parseInt(dim[0]);
		m = Integer.parseInt(dim[1]);
		char[] ini = info.get(5).toCharArray();
		initial = new int[ini.length];
		for(int i=0; i<ini.length;i++ ){
			initial[i] = ini[i]-'0';
		}
	}
	
//	private void checkConditions(){
//		if (info.isEmpty()){
////			error
//		}
//	}
	
	
	
	
	public void initSimulation(){
//		checkConditions();
		 Cells = new CellManager();
		 celldisplay = new Display(n, m, numstates );
		
		Cells.setUp(n, m, simulations.lastIndexOf(name), initial);
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
