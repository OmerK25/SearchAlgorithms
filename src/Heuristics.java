
import java.util.ArrayList;
import java.util.Comparator;

public class Heuristics implements Comparator<State> {
	private State solution;

	/** Constructor
	 * @param eState the end state used to calculate heuristics.**/
	public Heuristics(State end) {
		solution = end;
	}
	public int compare(State s1, State s2) {
		Double f1 = s1.getF(solution);
		Double f2 = s2.getF(solution);
		int pm1 = PossibleMoves.valueOf(s1.getPm()+"").ordinal();
		int pm2 = PossibleMoves.valueOf(s2.getPm()+"").ordinal();
		if(f1 > f2) return 1;
		else if(f1 < f2)
			return -1;
		else if(s1.getCreationTime() < s2.getCreationTime())
			return -1;
		else if(s1.getCreationTime() > s2.getCreationTime())
			return 1;
		else 	if(pm1 < pm2) 
			return -1;
		else 	if(pm1 > pm2)
			return 1;
		else
			return 0;
	}
}
