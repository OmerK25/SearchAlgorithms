import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

/*
 * BFS algorithm, Breadth first search on the board.
 */
public class BFS implements SearchAlgo {
	
	State start;
	State end;
	OutputFile output;
	Queue<State> front = new LinkedList<State>(); //create openList Queue
	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Hashtable<String, State> visitedList = new Hashtable<String, State>(); //create close list hash table
	long startTime, endTime, totalTime; //for time keeping

	State result = null;
	long count = 1;

	BFS(State start, State solution, OutputFile output) {
		this.start = start;
		this.end = solution;
		this.output = output;
	}

	@Override
	public void search() {
		this.front.add(this.start); //add the start State to the Queue
		this.openList.put(this.start.boardString(), this.start);
		this.visitedList.put(this.start.boardString(), this.start);

		this.startTime = System.nanoTime();

		while(!this.front.isEmpty()) {
			State validateState = this.front.poll(); //remove the top State from the Queue
			this.visitedList.put(validateState.boardString(), validateState);

//			this.output.printToScreen(validateState.toString());

			for (Direction direction : Direction.values()) { //create all directions States
				State son = new State(validateState, direction); //create son State for some direction				
				if (!son.equals(validateState) && 
					!this.visitedList.containsKey(son.boardString()) &&
					!this.openList.containsKey(son.boardString())) {
					count++;
					if (son.equals(this.end)) {
						this.endTime = System.nanoTime();
						this.totalTime = this.endTime - this.startTime;
						this.output.printToFile(son, count, totalTime);
						return;
					}
					this.front.add(son); //insert the son State to the  open list Queue
					this.openList.put(son.boardString(), son);
				}
			}
		}
		
		this.endTime = System.nanoTime();
		this.totalTime = this.endTime - this.startTime;
//		this.output.printToFileOnFailed(count, totalTime);
	}
}
