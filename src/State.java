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
	private ArrayList<String> spacesLocation = new ArrayList<>();
	private Integer[][] board;
	private State parent = null;
	private Integer n, m , cost = 0;
	private String roadTo = "";
	private Integer f;

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
	/*
	 * Son constructor
	 * get a father state and a Move, and change the board of the son acordinally
	 */
	State(State father, Move move) {
		this.board = new Integer[father.n][father.m];
		for(int i=0; i<father.board.length; i++)
			for(int j=0; j<father.board[i].length; j++)
				this.board[i][j]=father.board[i][j];

		this.parent = father;
		this.cost = father.cost;
		this.roadTo = father.roadTo;
		this.n = father.n;
		this.m = father.m;
		this.spacesAmount = father.spacesAmount;
		int i1= move.getI1();
		int j1 = move.getJ1();
		int i2 = move.getI2();
		int j2 = move.getJ2();

		PossibleMoves a = move.getP();
		switch (a){
		case LEFT :
			this.roadTo += "-"+this.board[i1][j1]+"L";
			this.board[i1][j1-1] = this.board[i1][j1];
			this.board[i1][j1] = -1;

			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+i1+(j1-1))) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+(j1-1));
				}
			}
			this.cost += 5;
			break;

		case RIGHT :
			this.roadTo += "-"+this.board[i1][j1]+"R";
			this.board[i1][j1+1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+i1+(j1+1))) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+(j1+1));
				}
			}
			this.cost += 5;
			break;

		case UP :
			this.roadTo += "-"+this.board[i1][j1]+"U";
			this.board[i1-1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+(i1-1)+j1)) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+(i1-1)+j1);
				}
			}
			this.cost += 5;
			break;

		case DOWN :
			this.roadTo += "-"+this.board[i1][j1]+"D";
			this.board[i1+1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+(i1+1)+j1)) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+(i1+1)+j1);
				}
			}
			this.cost += 5;
			break;

		case DOUBLELEFT :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"L";
			this.board[i1][j1-1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2][j2-1] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			if(i1 == i2)
				this.cost += 6;
			if(j1 == j2)
				this.cost += 7;
			break;

		case DOUBLETRIGHT :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"R";
			this.board[i1][j1+1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2][j2+1] = this.board[i2][j2];
			this.board[i2][j2] = -1;

			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			if(i1 == i2)
				this.cost += 6;
			if(j1 == j2)
				this.cost += 7;
			break;

		case DOUBLEUP :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"U";
			this.board[i1-1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2-1][j2] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			if(i1 == i2)
				this.cost += 6;
			if(j1 == j2)
				this.cost += 7;
			break;

		case DOUBLEDOWN :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"D";
			this.board[i1+1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2+1][j2] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			if(i1 == i2)
				this.cost += 6;
			if(j1 == j2)
				this.cost += 7;
			break;
		}
	}

	@Override
	//this function check if the states are equals.
	public boolean equals(Object obj){
		if (obj == null) {
			return false;
		}
		State StateToEqual = (State) obj;
		for(int i=0; i<this.board.length; i++)
			for(int j=0; j<this.board[i].length; j++)
				if(this.board[i][j] != StateToEqual.board[i][j])
					return false;

		return true;
	}
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
		// if it has two spaces squares, it can go LEFT RIGHT UP DOWN, each with up to 2 squares, and also DOUBLE move.
		if(spacesAmount == 2) {
			String splt2[] = spacesLocation.get(1).split("");
			r2 = Integer.parseInt(splt2[0]);
			c2 = Integer.parseInt(splt2[1]);
			// Now check for DOUBLE moves:
			//Adjust in the same row.
			if(Math.abs(r1-r2) == 0 && Math.abs(c1-c2) == 1) {
				if(r1 != n-1)
					move.add(new Move(PossibleMoves.DOUBLEUP, (r1+1), Math.min(c1, c2),(r1+1),  Math.max(c1, c2)));
				if(r1 != 0)
					move.add(new Move(PossibleMoves.DOUBLEDOWN, (r1-1), Math.min(c1, c2),(r1-1), Math.max(c1, c2)));
			}
			//Adjust in the same column.
			if(Math.abs(r1-r2) == 1 && Math.abs(c1-c2) == 0) {
				if(c1 != m-1)
					move.add(new Move(PossibleMoves.DOUBLELEFT, Math.min(r1,r2),(c1+1), Math.max(r1,r2),(c1+1)));
				if(c1 != 0) 
					move.add(new Move(PossibleMoves.DOUBLETRIGHT, Math.min(r1,r2), (c1-1),  Math.max(r1,r2),(c1-1)));		
			}
		}
		// if it has only one space square, only LEFT RIGHT UP DOWN possible moves:
		//first space
		if(c1 != (m-1) && board[r1][c1+1] != -1)
			move.add(new Move(PossibleMoves.LEFT, r1, (c1+1)));
		if(r1 != (n-1) && board[r1+1][c1] != -1) 
			move.add(new Move(PossibleMoves.UP, (r1+1), c1));
		if(c1 != 0 && board[r1][c1-1] != -1 ) 
			move.add(new Move(PossibleMoves.RIGHT, r1, (c1-1)));
		if(r1 != 0 && board[r1-1][c1] != -1 )
			move.add(new Move(PossibleMoves.DOWN, (r1-1), c1));

		//second space
		if(c2 != -1 && r2 != -1) {
			if(c2 != (m-1) && board[r2][c2+1] != -1)
				move.add(new Move(PossibleMoves.LEFT, r2, (c2+1)));
			if(r2 != (n-1) && board[r2+1][c2] != -1) 
				move.add(new Move(PossibleMoves.UP, (r2+1), c2));
			if(c2 != 0 &&board[r2][c2-1] != -1)
				move.add(new Move(PossibleMoves.RIGHT, r2, (c2-1)));
			if(r2 != 0 && board[r2-1][c2] != -1 )
				move.add(new Move(PossibleMoves.DOWN, (r2-1), r1));
		}
		return move;
	}

	// Getters Setters

	public Integer getF(State solution) {
		int counter = 0;
		for(int i=0; i<this.board.length; i++)
			for(int j=0; j<this.board[i].length; j++)
				if(this.board[i][j] != solution.board[i][j])
					counter++;

		return counter;
	}

	public void setF(Integer f) {
		this.f = f;
	}
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

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getRoadTo() {
		return roadTo;
	}

	public void setRoadTo(String roadTo) {
		this.roadTo = roadTo;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(this.board);
	}
}
