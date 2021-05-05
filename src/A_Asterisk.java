import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class A_Asterisk implements SearchAlgo {

	State start;
	State end;
	OutputFile output;
	Comparator<State> heuristic;

	Hashtable<String, State> openList = new Hashtable<String, State>(); //create open list hash table 
	Hashtable<String, State> closeList = new Hashtable<String, State>(); //create close list hash table
	PriorityQueue<State> pq;
	long startTime, endTime, totalTime; //for time keeping

	long counterNodes = 1;

	A_Asterisk(InputFile input, OutputFile output) {
		this.start = input.getStartingPosition();
		this.end = input.getSolution();
		this.output=output;
		this.heuristic = new Heuristics(input.getSolution());
		this.pq = new  PriorityQueue<State>(heuristic);//create an priority queue
	}

	@Override
	public void search() {
		startTime = System.nanoTime();

		State temp = start;
		openList.put(temp.toString(), temp);//add the start node to open list		

		while(temp != null && !temp.equals(end)) {

			this.output.printToScreen(temp.toString());

			if(!closeList.containsKey(temp.toString())){
				for (Move m : temp.getPossiblleMoves()) {
					State son = new State(temp, m);

					if(!closeList.containsKey(son.toString()) && !openList.containsKey(son.toString())) {
						pq.add(son);
						if(son!=null && !son.getParent().equals(son) && !son.equals(this.start)) {
							counterNodes++;}
						openList.put(son.toString(), son);
					}	

				}
				closeList.put(temp.toString(), temp);	
			}
			if(!(pq.isEmpty()))
				temp = pq.poll(); //pull the top of the queue

			if(temp != null)
				openList.put(temp.toString(), temp);			
		}
		if(temp.equals(end)) { // found the solution
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			output.printToFile(temp, counterNodes, totalTime);
		}	
	}

}
