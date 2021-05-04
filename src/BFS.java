import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

/*
 * BFS algorithm, Breadth first search on the board.
 */
public class BFS implements SearchAlgo {

	private State start;
	private State end;
	private OutputFile output;

	Queue<State> front = new LinkedList<State>(); //create openList Queue
	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Hashtable<String, State> visitedList = new Hashtable<String, State>(); //create close list hash table
	long startTime, endTime, totalTime; //for time keeping

	State result = null;
	long count = 1;

	BFS(InputFile input, OutputFile output) {
		this.start = input.getStartingPosition();
		this.end = input.getSolution();
		this.output = output;
	}

	@Override
	public void search() {
		this.front.add(this.start); //add the start State to the Queue
		this.openList.put(this.start.toString(), this.start);
		this.visitedList.put(this.start.toString(), this.start);

		this.startTime = System.nanoTime();

		while(!this.front.isEmpty()) {
			State validateState = this.front.poll(); //remove the top State from the Queue
			this.visitedList.put(validateState.toString(), validateState);

			this.output.printToScreen(validateState.toString());
			for(Move m : validateState.getPossiblleMoves()) {
				State son = new State(validateState, m); //create son State for some direction		
				System.out.println(m+ " -----> move");
				System.out.println(son.getSpacesLocation() + " ----> locLoc");

				if (!son.equals(validateState) && 
						!this.visitedList.containsKey(son.toString()) &&
						!this.openList.containsKey(son.toString())) {
					count++;
					if (son.equals(this.end)) {
						this.endTime = System.nanoTime();
						this.totalTime = this.endTime - this.startTime;
						this.output.printToFile(son, count, totalTime);
						return;
					}
					this.front.add(son); //insert the son State to the  open list Queue
					this.openList.put(son.toString(), son);
				}
			}
		}

		this.endTime = System.nanoTime();
		this.totalTime = this.endTime - this.startTime;
		this.output.printToFileOnFailed(count, totalTime);
	}
}
