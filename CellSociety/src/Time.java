import javafx.animation.Timeline;
import javafx.scene.shape.Shape;
import javafx.util.*;
import java.util.*;

import javafx.animation.KeyFrame;

public class Time {

	public static final double INITIAL_SPEED = 1;// address this: put in xml? what is the "correct" time?
	private List<String> simulations =Arrays.asList("Segregation", "Predator_Prey", "Spreading_Fire","Game_of_Life");

	private Timeline timeline;
	private Duration stepTime;
	private CellManager Cells;
	private Display celldisplay;

	private double speed;

//TODO: figure out what to do with these
	private String name;
	private String title;
	private String author;
	private int numstates;
	private int n;
	private int m;
	private int[] initial;
	private String[] params;

	public Time (String info){
		settingsFromFile(info);
		//break up string w/method
		setSpeed(INITIAL_SPEED);
		initSimulation();

	}

	private void settingsFromFile(String info){
		System.out.println(info);
		String[] settings = info.split(",");
		name = settings[0];
		title = settings[1];
		author= settings[2];
		numstates = Integer.parseInt(settings[3]);
		n =  settings[4].charAt(0)-'0';
		m =  settings[4].charAt(2)-'0';

		char[] ini = settings[5].toCharArray();
		initial = new int[ini.length];
		for(int i=0; i<ini.length;i++ ){
			initial[i] = ini[i]-'0';
		}
		if (settings.length>4){
			params = Arrays.copyOfRange(settings, 5, settings.length);
		}
		else{
			params = null;
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
		celldisplay = new Display(n, m, numstates);

		Cells.setUp(n, m, simulations.lastIndexOf(name), initial, params);
		System.out.println("Grid Stuff "+n+" "+m);

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(stepTime,
				e -> step()));

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
	public void startAnimation(){
		timeline.playFromStart();
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
