import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * This class represents the input file that given to the program before it starts.
 */
public class InputFile {
	String fileName;
	String algo;
	Boolean printTime = false;
	Boolean openList = false;
	Integer n, m;
	State startingPosition;
	State solution;
	Integer firstSpaceLocation = -1;
	Integer spaces = 0;
	ArrayList<String> spacesLocation = new ArrayList<>();

	/*
	 *  constructor, reads the data from the command line and fill the parameters.
	 */

	public InputFile() {
		ArrayList<String> commands = new ArrayList<>();
		try {
			File myObj = new File("input2.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				commands.add(myReader.nextLine());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		fileName = "input.txt";
		algo = commands.get(0);
		if( commands.get(1).contains("with"))
			printTime = true;

		if( commands.get(2).contains("with"))
			openList = true;

		String splNM[] = commands.get(3).split("");
		n = Integer.parseInt(splNM[0]);
		m = Integer.parseInt(splNM[2]);
		Integer[][]  startPositionBoard = new Integer[n][m];

		int pos = 0;
		spaces = 0;
		spacesLocation.clear();
		for(int i = 4; i < 4+n; i++) {
			String boardLine[] = commands.get(i).split(",");
			for(int j = 0; j< boardLine.length; j++) {
				if(boardLine[j].equals("_")) {
					startPositionBoard[pos][j] = -1;
					spacesLocation.add(i-4+""+ j+"");
					spaces++;
				}
				else {
					startPositionBoard[pos][j] = Integer.parseInt(boardLine[j]);
				}
			}
			pos++;
		}
		startingPosition = new State(startPositionBoard, spaces, spacesLocation);
		
		Integer[][] SolutionPositionBoard = new Integer[n][m];
		spaces = 0;
		pos = 0;
		spacesLocation.clear();
		for(int i = 5+n; i < 5+n+n; i++) {
			String boardLine[] = commands.get(i).split(",");
			for(int j = 0; j< boardLine.length; j++) {
				if(boardLine[j].equals("_")) {
					SolutionPositionBoard[pos][j] = -1;
				spacesLocation.add(i-(5+n)+""+ j+"");
				spaces++;
				}
				else {
					SolutionPositionBoard[pos][j] = Integer.parseInt(boardLine[j]);
				}
			}
			pos++;
		}
		solution = new State(SolutionPositionBoard, spaces, spacesLocation);
	}
	/*
	 * Getters Setters
	 */
	@Override
	public String toString() {
		return "TBA";
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	public Boolean getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Boolean printTime) {
		this.printTime = printTime;
	}
	public Boolean getOpenList() {
		return openList;
	}
	public void setOpenList(Boolean openList) {
		this.openList = openList;
	}
	public Integer getN() {
		return n;
	}
	public void setN(Integer n) {
		this.n = n;
	}
	public Integer getM() {
		return m;
	}
	public void setM(Integer m) {
		this.m = m;
	}
	public State getStartingPosition() {
		return startingPosition;
	}
	public void setStartingPosition(State startingPosition) {
		this.startingPosition = startingPosition;
	}
	public State getSolution() {
		return solution;
	}
	public void setSolution(State solution) {
		this.solution = solution;
	}
	public Integer getFirstSpaceLocation() {
		return firstSpaceLocation;
	}
	public void setFirstSpaceLocation(Integer firstSpaceLocation) {
		this.firstSpaceLocation = firstSpaceLocation;
	}

}
