import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Ex1 {

	public static void main(String[] args) {
		InputFile input = new InputFile();
		OutputFile output = new OutputFile(input.getOpenList(), input.getPrintTime());
		SearchAlgo searchAlgorithm = null;
		switch(input.algo) {
		case "BFS":
			searchAlgorithm = new BFS(input,output);
			break;
		case "A*" :
			searchAlgorithm = new Astar(input,output);
			break;
		case "IDA*":
			searchAlgorithm = new IDAstar(input,output);
			break;
		case "DFBnB" :
			searchAlgorithm = new DFBnB(input,output);
			break;
			
		default:
			break;
		}		
		searchAlgorithm.search();
	}
}
