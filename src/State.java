import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * This class represent a given state of the board, meaning the order of the numbers in it.
 */

public class State {
	private Integer spacesAmount;
	ArrayList<String> spacesLocation = new ArrayList<>();
	private Integer[][] board;
	private State parent = null;
	private Integer n, m , price = 0;
	String roadTo = "";

	/*
	 * Constructor.
	 */

	//Constructor from scratch
	State(Integer[][] board, int spaces, ArrayList<String> spacesLocation){
		this.n = board.length;
		this.m = board[0].length;
		this.board = board;
		this.spacesAmount = spaces;
		this.spacesLocation = spacesLocation;
	}

	//Constructor for sons.
	//	State(State father, PossibleMoves move) {
	//		this.board = father.board.clone();
	//		for (int i = 0; i < this.board.length; i++){
	//			this.board[i] = new Cube(copyNode.board[i].getColor(), copyNode.board[i].getNumber());
	//		}
	//		
	//		this.spacePosition = copyNode.spacePosition;
	//		this.depth = copyNode.depth + 1;
	//		this.parent = copyNode;
	//		this.n = copyNode.n;
	//		this.m = copyNode.m;
	//		this.moveSpace(direction);
	//	}

	/*
	 * This function check all the possible moves and return them to the algorithm.
	 */
	ArrayList<Move> getPossiblleMoves(){
		ArrayList<Move>	move = new ArrayList<>();
		String splt1[] = spacesLocation.get(0).split("");
		int r1 = Integer.parseInt(splt1[0]);
		int c1 = Integer.parseInt(splt1[1]);
		int r2 = -1;
		int c2 = -1;

		/*
		 * if it has two spaces squares, it can go LEFT RIGHT UP DOWN, each with up to 2 squares, and also DOUBLE move.
		 */
		if(spacesAmount == 2) {
			String splt2[] = spacesLocation.get(1).split("");
			r2 = Integer.parseInt(splt2[0]);
			c2 = Integer.parseInt(splt2[1]);

			// Now check for DOUBLE moves.

			//Adjust in the same row.
			if(Math.abs(r1-r2) == 0 && Math.abs(c1-c2) == 1) {

				if(c1 != 1 && c2 != 1)
					move.add(new Move(PossibleMoves.DOUBLETRIGHT, r1, (Math.min(c1, c2)-1),r1, (Math.min(c1, c2)-2)));
				if(c1 != (m-2) && c2 != (m-2))
					move.add(new Move(PossibleMoves.DOUBLELEFT, r1, (Math.max(c1, c2)+1),r1,  (Math.max(c1, c2)+2)));
				if(r1 != 0)
					move.add(new Move(PossibleMoves.DOUBLEDOWN, (r1-1), Math.min(c1, c2),(r1-1), Math.max(c1, c2)));
				if(r1 != n-1)
					move.add(new Move(PossibleMoves.DOUBLEUP, (r1+1), Math.min(c1, c2),(r1+1),  Math.max(c1, c2)));
			}
			//Adjust in the same column.
			if(Math.abs(r1-r2) == 1 && Math.abs(c1-c2) == 0) {
				if(c1 != 0)
					move.add(new Move(PossibleMoves.DOUBLETRIGHT, (c1-1), Math.min(r1,r2),(c1-1),  Math.max(r1,r2)));
				if(c1 != m-1)
					move.add(new Move(PossibleMoves.DOUBLELEFT, (c1+1), Math.min(r1,r2),(c1+1),  Math.max(r1,r2)));
				if(r1 != (n-2) && r2 != (n-2))
					move.add(new Move(PossibleMoves.DOUBLEDOWN, c1,(Math.min(r1,r2)-1),c1,  (Math.min(r1,r2)-2)));
				if(r1 != 1 && r2 != 1)
					move.add(new Move(PossibleMoves.DOUBLEUP, c1,  (Math.max(r1,r2)+1),c1, (Math.max(r1,r2)+2)));
			}
		}
		/*
		 * if it has only one space square, only LEFT RIGHT UP DOWN possible moves
		 */

		//first space
		if(r1 != (n-1) && r2 != (r1+1)) 
			move.add(new Move(PossibleMoves.UP, (r1+1), c1));
		if(r1 != 0 && r2 != (r1-1) )
			move.add(new Move(PossibleMoves.DOWN, (r1-1), c1));
		if(c1 != 0 && c2 != (c1-1))
			move.add(new Move(PossibleMoves.RIGHT, r1, (c1-1)));
		if(c1 != (m-1) && c2 != c1+1)
			move.add(new Move(PossibleMoves.LEFT, r1, (c1+1)));

//		second space
		if(r2 != (n-1) && r1 != (r2+1)) 
			move.add(new Move(PossibleMoves.UP, (r2+1), c2));
		if(r2 != 0 && r1 != (r2-1) )
			move.add(new Move(PossibleMoves.DOWN, (r2-1), r1));
		if(c2 != 0 && c1 != (c2-1))
			move.add(new Move(PossibleMoves.RIGHT, r2, (c2-1)));
		if(c2 != (m-1) && c1 != c2+1)
			move.add(new Move(PossibleMoves.LEFT, r2, (c2+1)));
		
		return move;
	}

	/*
	 * Getters Setters
	 */

	public Integer[][] getBoard() {
		return board;
	}


	public Integer getSpacesAmount() {
		return spacesAmount;
	}

	public void setSpacesAmount(Integer spacesAmount) {
		this.spacesAmount = spacesAmount;
	}

	public ArrayList<String> getSpacesLocation() {
		return spacesLocation;
	}

	public void setSpacesLocation(ArrayList<String> spacesLocation) {
		this.spacesLocation = spacesLocation;
	}

	public void setBoard(Integer[][] board) {
		this.board = board;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getRoadTo() {
		return roadTo;
	}

	public void setRoadTo(String roadTo) {
		this.roadTo = roadTo;
	}

	@Override
	public String toString() {
		return "TBA";
	}
}
