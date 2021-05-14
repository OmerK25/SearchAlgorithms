import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Astar  implements SearchAlgo{

	State start;
	State end;
	OutputFile output;
	Comparator<State> heuristic;

	PriorityQueue<State> pq;
	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Hashtable<String, State> closeList = new Hashtable<String, State>(); //create close list hash table
	long startTime, endTime, totalTime; //for time keeping

	long counterNodes = 1;

	Astar(InputFile input, OutputFile output) {
		this.start = input.getStartingPosition();
		this.end = input.getSolution();
		this.output=output;
		this.heuristic = new Heuristics(input.getSolution());
		this.pq = new  PriorityQueue<State>(heuristic);//create an priority queue
	}
	@Override
	public void search() {
		startTime = System.nanoTime();
		pq.add(start);
		openList.put(start.toString(),start);
		//loop until the queue is empty, then we are done.
		while(!pq.isEmpty()) {
			if(output.getOpenList()) 
				for (State s : openList.values()) 
					System.out.println(s+" \n");			
			State n = pq.poll();
			//check if goal is achieved
			if(n.equals(end)) {
				endTime = System.nanoTime();
				totalTime = endTime - startTime;
				output.printToFile(n, counterNodes, totalTime);
				return;
			}
			openList.remove(n.toString(),n);
			closeList.put(n.toString(), n);
			//created all the possible sons and loop on them.
			for (Move m : n.getPossiblleMoves()) {
				State son = new State(n, m);
				if(!son.equals(n)) {
					if(!closeList.containsKey(son.toString()) && !openList.containsKey(son.toString())) {
						counterNodes++;
						openList.put(son.toString(), son);
						pq.add(son);
					}
					else if(openList.containsKey(son.toString()) && son.getCost() < openList.get(son.toString()).getCost()){
						openList.put(son.toString(), son);
					}
				}
			}
		}
		System.out.println("NO PATH");
		System.out.println(counterNodes);
	}
}
