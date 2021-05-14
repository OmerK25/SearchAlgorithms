import java.util.Hashtable;

public class DFID implements SearchAlgo{

	State start;
	State solution;
	int limit;
	boolean flag;
	OutputFile output;
	long counterStates = 1;
	State DFSres;
	long startTime, endTime, totalTime; //for time keeping
	Hashtable<String, State> Path = new Hashtable<String, State>();

	DFID(InputFile input,	OutputFile output) {
		this.start = input.getStartingPosition();
		this.solution = input.getSolution();
		this.output = output;
	}
	/*
	 * Limited DFS function, the main search function will use it.
	 */
	public String Limited_DFS(State n, State end, int limit, Hashtable<String, State> Path) {
		if(n.equals(end)) {
			this.DFSres = n;
			return "end" ;
		}
		else if(limit == 0) {
			return "cutOff";
		}
		else {
			Path.put(n.toString(), n);
			flag = false;
			//created all the possible sons and loop on them.
			for(Move m : n.getPossiblleMoves()) {
				State son = new State(n, m);
				if (!Path.containsKey(son.toString())) {
					if(son!=null && !son.getParent().equals(son) && !son.equals(this.start)) {
						counterStates++;
					}}

				if(!Path.containsKey(son.toString())){
					String result = Limited_DFS(son, end, limit-1, Path);
					if (result.equals("cutOff")){
						flag = true;		
					}
					else if(!result.equals("failed")) {
						return "end";
					}
				}
			}
			Path.remove(n.toString());
			if(flag)
				return "cutOff";
			else
				return "failed";
		}	
	}
	@Override
	public void search() {
		for(int deep=1; deep < Integer.MAX_VALUE; deep++) {
			startTime = System.nanoTime();
			String result = Limited_DFS(start,solution,deep,Path);
			if(!result.equals("cutOff")){ 
				if(result.equals("failed")) {
					output.printToFileOnFailed(counterStates, totalTime);
					break;
				}
				if(result.equals("end")) {
					endTime = System.nanoTime();
					totalTime = endTime - startTime;
					output.printToFile(DFSres,counterStates, this.totalTime);
					break;
				}
			}		
		}
	}
}
