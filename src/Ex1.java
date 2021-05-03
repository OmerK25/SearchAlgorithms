import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Ex1 {

	public static void main(String[] args) {
		InputFile input = new InputFile();
		ArrayList<Move> m = input.getStartingPosition().getPossiblleMoves();

		System.out.println(m);
//		   for (Entry<PossibleMoves, String> me : m.entrySet()) {
//	            System.out.print(me.getKey() + ":");
//	            System.out.println(me.getValue());
//	        }
		
		
		
		
		
		SearchAlgo searchAlgorithm = null;
		switch(input.algo) {
		case "BFS":
			//			searchAlgorithm = new BFS(input.board, input.solution, output);
			break;
		default:
			break;
		}		
		//		searchAlgorithm.search();
	}
}
