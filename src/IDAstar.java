
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Stack;

public class IDAstar  implements SearchAlgo{
	State start;
	State solution;
	OutputFile output;
	Comparator<State> heuristic;

	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Stack<State> s = new Stack <State>();
	long startTime, endTime, totalTime; //for time keeping

	long counterStates = 1;

	IDAstar(InputFile input, OutputFile output) {
		this.start  = input.getStartingPosition() ;
		this.solution = input.getSolution();
		this.output=output;
		this.heuristic = new Heuristics(solution);
	}


	@Override
	public void search() {
		boolean flag = false;
		startTime = System.nanoTime();

		int threshold = this.start.getF(this.solution); // set the start threshold

		while(threshold != Integer.MAX_VALUE) {
			int minF = Integer.MAX_VALUE;
			s.push(this.start);
			start.setMark(false);
			openList.put(start.toString(), start);

			while (!s.isEmpty()) {
				State n = s.pop();
				this.output.printToScreen(n.toString());
				if(n.isMark())
					openList.remove(n.toString());
				else {
					n.setMark(true);
					s.push(n);
					for (Move m : n.getPossiblleMoves()) {
						State son = new State(n, m);
						counterStates++;
						if(son.getF(solution)>threshold) {
							minF = Math.min(minF, son.getF(solution));
							continue;
						}
						if(openList.containsKey(son.toString()) && openList.get(son.toString()).isMark()) {
							continue;
						}
						if(openList.containsKey(son.toString()) && !openList.get(son.toString()).isMark()) {
							if(openList.get((son.toString())).getF(solution) > son.getF(solution)) {
								s.removeElement(openList.get(son.toString()));
								openList.remove(son.toString());
							} 
							else {
								continue;
							}
						}
						if(son.equals(this.solution)) {
							flag=true;
							this.endTime = System.nanoTime();
							this.totalTime = this.endTime - this.startTime;
							output.printToFile(son, counterStates, this.totalTime);	
							return;
						}
						s.push(son);
						openList.put(son.toString(), son);
					}
				}
			}
			threshold = minF;
		}
		if(!flag) {
			this.endTime = System.nanoTime();
			this.totalTime = this.endTime - this.startTime;
			this.output.printToFileOnFailed(counterStates, totalTime);
		}
	}
}
