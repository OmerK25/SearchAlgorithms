import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements SearchAlgo {

	private State start;
	private State end;
	private OutputFile output;

	Queue<State> front = new LinkedList<State>(); //create openList Queue
	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Hashtable<String, State> closeList = new Hashtable<String, State>(); //create close list hash table
	long startTime, endTime, totalTime; //for time keeping
	int count = 1;

	BFS(InputFile input, OutputFile output) {
		this.start = input.getStartingPosition();
		this.end = input.getSolution();
		this.output = output;
	}

	@Override
	public void search() {
		this.front.add(this.start); //add the start State to the Queue
		this.startTime = System.nanoTime();
		while(!front.isEmpty()) {
			State n = front.poll();
			closeList.put(n.toString(), n);
			openList.remove(n.toString(),n);
			//print the open list if needed
			if(output.getOpenList()) 
				for (State s : openList.values()) 
					System.out.println(s+" \n");		
			this.output.printToScreen(n.toString());
			//created all the possible sons and loop on them.
			for (Move m : n.getPossiblleMoves()) {
				State son = new State(n, m);
				if(!closeList.containsKey(son.toString()) && !openList.containsKey(son.toString())) {
					count++;
					//check if goal achieved.
					if(son.equals(end)) {
						endTime = System.nanoTime();
						totalTime = endTime - startTime;
						output.printToFile(son, count, totalTime);
						return;
					}
					front.add(son);
					openList.put(son.toString(), son);
				}
			}
		}
		this.endTime = System.nanoTime();
		this.totalTime = this.endTime - this.startTime;
		this.output.printToFileOnFailed(count, totalTime);	}
}
