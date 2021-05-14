import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

public class DFBnB implements SearchAlgo{
	State start;
	State solution;
	OutputFile output;
	Comparator<State> heuristic;
	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Stack<State> st = new Stack <State>();
	long startTime, endTime, totalTime; //for time keeping
	int counterStates = 1;

	DFBnB(InputFile input, OutputFile output) {
		this.start = input.getStartingPosition();
		this.solution = input.getSolution();
		this.output = output;
		this.heuristic = new Heuristics(solution);
	}
	@Override
	public void search() {
		ArrayList<State> sons = new ArrayList<State>();
		Double threshold = Double.MAX_VALUE;
		State result = null;
		this.openList.put(this.start.toString(), this.start);
		this.st.push(this.start);
		if(output.getOpenList()) 
			for (State s : openList.values()) 
				System.out.println(s+" \n");		
		this.startTime = System.nanoTime();
		//loop until the stack is empty.
		while(!st.isEmpty()) {
			State n = st.pop();
			if(n.isMark()) {
				this.openList.remove(n.toString());
			} 
			else {
				n.setMark(true);
				st.push(n);
				//created all the possible sons and loop on them.
				for (Move m : n.getPossiblleMoves()) {
					State son = new State(n, m);
					if (!openList.containsKey(son.toString()) && son!=null && !son.getParent().equals(son) && !son.equals(this.start)) {
						sons.add(son);
						counterStates++;
					}
				}
				//sort all the sons by the F function.
				sons.sort(heuristic);
				Iterator<State> it = sons.iterator();
				while (it.hasNext()) {
					State g = it.next();
					if(g.getF(solution) >= threshold) {
						while(it.hasNext()) {
							it.next();
							it.remove();
						}
					}
					else if(openList.containsKey(g.toString()) && openList.get(g.toString()).isMark())
						it.remove();
					else if(openList.containsKey(g.toString()) && !openList.get(g.toString()).isMark()) {
						if(openList.get(g.toString()).getF(solution) <= g.getF(solution)) {
							it.remove();
						}
						else {
							openList.remove(g.toString());
							st.remove(g);
						}
					}
					//check if goal achieved.
					else if(g.equals(solution)) {
						threshold = g.getF(solution);
						result = g;
						while(it.hasNext()) {
							it.next();
							it.remove();
						}
					}
				}
				Collections.reverse(sons);
				for(State rev : sons)	{
					st.push(rev);
					openList.put(rev.toString(), rev);
				}
			}
		}
		this.endTime = System.nanoTime();
		this.totalTime = this.endTime - this.startTime;

		if(result == null) {
			System.out.println("NO PATH");
			System.out.println("NUM : "+ counterStates);
		}
		else
			output.printToFile(result, this.counterStates, this.totalTime);
	}
}
