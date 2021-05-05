
import java.util.Comparator;

public class Heuristics implements Comparator<State> {
	private State solution;

	/** Constructor
	 * @param eState the end state used to calculate heuristics.**/
	public Heuristics(State end) {
		solution = end;
	}

	public int compare(State State1, State State2) {
		if( State1.getF(solution) > State2.getF(solution)) return 1;
		else if (State1.getF(solution) < State2.getF(solution)) return -1;
		else if( State1.getF(solution) == State2.getF(solution)) {
			if(State1.getCost() > State2.getCost()) return 1;
			else return -1;
		}
		return 0;
	}
}