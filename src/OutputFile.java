/*
 * This class represents the out file file that would be the result of the program. 
 */
import java.io.FileWriter;
import java.io.IOException;

public class OutputFile {

	Boolean openList;
	Boolean printTime;
	String pathOutputFile;

	public OutputFile(Boolean openList, Boolean printTime) {
		this.openList = openList;
		this.printTime = printTime;
		this.pathOutputFile = "output.txt";
	}

	//prints to the screen the open list every step
	void printToScreen(String dataToPrint) {
		if (this.openList)
			System.out.println(dataToPrint);
	}
	void fail(long counterNodes) throws IOException {
		FileWriter myWriter = new FileWriter(this.pathOutputFile);
		myWriter.write("no path");
		myWriter.write("Num: " + counterNodes + "\n");
		myWriter.close();
	}

	void printToFileOnFailed(long counterNodes, long totalTime) {
		try {
			FileWriter myWriter = new FileWriter(this.pathOutputFile);
			myWriter.write("no path\n");
			myWriter.write("Num: " + counterNodes + "\n");
			if (printTime) { myWriter.write("Time: " + (double)totalTime/1_000_000_000.0 + "\n"); }
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	void printToFile(State result, long counterNodes, long totalTime) {
		try {
			FileWriter myWriter = new FileWriter(this.pathOutputFile);
			myWriter.write(result.getRoadTo().substring(1) + "\n");
			myWriter.write("Num: " + counterNodes + "\n");
			myWriter.write("Cost: " + result.getCost().toString() + "\n");
			if (printTime) { myWriter.write((double)totalTime/1_000_000_000.0 + " seconds\n"); }
			myWriter.close();
			System.out.println("Successfully wrote the results to the file.");
			System.out.println("num:"+counterNodes);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	String actions(State result) {
		if (result == null || result.getRoadTo() == "")
			return "";

		return actions(result.getParent()) + result.getRoadTo();
	}

	Integer price(State result) {
		if (result == null)
			return 0;

		return price(result.getParent()) + result.getCost();
	}
}

