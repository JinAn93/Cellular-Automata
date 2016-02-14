import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Config {
	private String initConfig;
	private int myGridSize;
	private int myNumStates;

	public Config(String initConfig, int myGridSize, int myNumStates){
		this.initConfig =initConfig;
		this.myGridSize =myGridSize;
		this.myNumStates =myNumStates;
	}

	public int[] considerInitConfig () {
		int[] init = new int[myGridSize];
		if (isRandom()) {
			init = setRandomConfig();
		}
		else if (isProbRandom()) {
			init = setProbRandomConfig();
		}
		else {
			char[] ini = initConfig.toCharArray();
			init = new int[ini.length];
			for (int i = 0; i < ini.length; i++) {
				init[i] = ini[i] - '0';
			}
		}
		return init;
	}

	private int[] setProbRandomConfig () {
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
		int[] randomConfig = new int[myGridSize];
		for (int i = 0; i < randomConfig.length; i++) {
			randomConfig[i] = new Random().nextInt(myNumStates - 1);
		}
		return randomConfig;
	}

	private boolean isRandom () {
		return initConfig.equals("random");
	}


	private boolean isProbRandom () {
		return initConfig.contains(" ");
	}
}
