import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
	private State grandParent = null;
	private Integer n, m , cost = 0;
	private String roadTo = "";
	private Double f = 0.0;
	private PossibleMoves pm;
	boolean mark = false;
	int creationTime = 0;

	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
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
	 * get a father state and a Move, and change the board of the son accordingly
	 */
	State(State father, Move move) {
		this.board = new Integer[father.n][father.m];
		for(int i=0; i<father.board.length; i++)
			for(int j=0; j<father.board[i].length; j++)
				this.board[i][j]=father.board[i][j];

		this.creationTime = father.creationTime+1;
		this.pm = move.getP();
		this.parent = father;
		this.grandParent = father.parent;
		this.cost = father.cost;
		this.roadTo = father.roadTo;
		this.n = father.n;
		this.m = father.m;
		this.spacesAmount = father.spacesAmount;
		int i1= move.getI1();
		int j1 = move.getJ1();
		int i2 = move.getI2();
		int j2 = move.getJ2();
		boolean sameAsGrand = true;
		switch (this.pm){
		case LEFT :
			this.roadTo += "-"+this.board[i1][j1]+"L";
			this.board[i1][j1-1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			if(spacesAmount == 1) 
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+i1+(j1-1))) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+j1);
				}
				fixSpacesLocation(spacesLocation);
			}
			this.cost += 5;
			break;

		case RIGHT :
			this.roadTo += "-"+this.board[i1][j1]+"R";
			this.board[i1][j1+1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+i1+(j1+1))) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+j1);
				}
				fixSpacesLocation(spacesLocation);
			}
			this.cost += 5;
			break;

		case UP :
			this.roadTo += "-"+this.board[i1][j1]+"U";
			this.board[i1-1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+(i1-1)+j1)) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+j1);
				}
				fixSpacesLocation(spacesLocation);
			}
			this.cost += 5;
			break;

		case DOWN :

			this.roadTo += "-"+this.board[i1][j1]+"D";
			this.board[i1+1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			if(spacesAmount == 1)
				spacesLocation.add(""+i1+j1);
			if(spacesAmount == 2) {
				if(father.spacesLocation.get(0).equals(""+(i1+1)+j1)) {
					spacesLocation.add(""+i1+j1);
					spacesLocation.add(father.spacesLocation.get(1));
				}
				else {
					spacesLocation.add(father.spacesLocation.get(0));
					spacesLocation.add(""+i1+j1);
				}
				fixSpacesLocation(spacesLocation);
			}
			this.cost += 5;
			break;

		case DOUBLELEFT :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"L";
			this.board[i1][j1-1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2][j2-1] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			fixSpacesLocation(spacesLocation);
			this.cost += 6;
			break;

		case DOUBLETRIGHT :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"R";
			this.board[i1][j1+1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2][j2+1] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			fixSpacesLocation(spacesLocation);
			this.cost += 6;
			break;

		case DOUBLEUP :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"U";
			this.board[i1-1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2-1][j2] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			fixSpacesLocation(spacesLocation);
			this.cost += 7;
			break;

		case DOUBLEDOWN :
			this.roadTo += "-"+this.board[i1][j1]+"&"+this.board[i2][j2]+"D";
			this.board[i1+1][j1] = this.board[i1][j1];
			this.board[i1][j1] = -1;
			this.board[i2+1][j2] = this.board[i2][j2];
			this.board[i2][j2] = -1;
			if(grandParent != null) {
				for(int i=0; i<this.board.length; i++) {
					for(int j=0; j<this.board[i].length; j++) {
						if(this.board[i][j] != grandParent.getBoard()[i][j])
							sameAsGrand = false;
					}
				}
				if(sameAsGrand)
					return;
			}
			spacesLocation.add(""+i1+j1);
			spacesLocation.add(""+i2+j2);
			fixSpacesLocation(spacesLocation);

			this.cost += 7;
			break;
		}
	}

	public int getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(int creationTime) {
		this.creationTime = creationTime;
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
		int r2 = -2;
		int c2 = -2;
		// if it has two spaces squares, it can go LEFT RIGHT UP DOWN, each with up to 2 squares, and also DOUBLE move.
		if(spacesAmount == 2) {
			String splt2[] = spacesLocation.get(1).split("");
			r2 = Integer.parseInt(splt2[0]);
			c2 = Integer.parseInt(splt2[1]);
			// Now check for DOUBLE moves:
			//Adjust in the same row.
			if(Math.abs(r1-r2) == 0 && Math.abs(c1-c2) == 1) {
				if(pm!=PossibleMoves.DOUBLEDOWN && pm!=PossibleMoves.DOWN)
					if(r1 != n-1)
						move.add(new Move(PossibleMoves.DOUBLEUP, (r1+1), Math.min(c1, c2),(r1+1),  Math.max(c1, c2)));
				if(pm!=PossibleMoves.DOUBLEUP && pm!=PossibleMoves.UP)
					if(r1 != 0) 
						move.add(new Move(PossibleMoves.DOUBLEDOWN, (r1-1), Math.min(c1, c2),(r1-1), Math.max(c1, c2)));
			}
			//Adjust in the same column.
			if(Math.abs(r1-r2) == 1 && Math.abs(c1-c2) == 0) {
				if(pm!=PossibleMoves.DOUBLETRIGHT && pm!=PossibleMoves.RIGHT)
					if(c1 != m-1)
						move.add(new Move(PossibleMoves.DOUBLELEFT, Math.min(r1,r2),(c1+1), Math.max(r1,r2),(c1+1)));
				if(pm!=PossibleMoves.DOUBLELEFT && pm!=PossibleMoves.LEFT)
					if(c1 != 0) 
						move.add(new Move(PossibleMoves.DOUBLETRIGHT, Math.min(r1,r2), (c1-1),  Math.max(r1,r2),(c1-1)));		
			}
		}
		// if it has only one space square, only LEFT RIGHT UP DOWN possible moves:
		//first space
		if(pm!=PossibleMoves.DOUBLETRIGHT)
			if(c1 != (m-1) && board[r1][c1+1] != -1) 
				move.add(new Move(PossibleMoves.LEFT, r1, (c1+1)));
		if(pm!=PossibleMoves.DOUBLEDOWN)
			if(r1 != (n-1) && board[r1+1][c1] != -1) 
				move.add(new Move(PossibleMoves.UP, (r1+1), c1));
		if(pm!=PossibleMoves.DOUBLELEFT)
			if(c1 != 0 && board[r1][c1-1] != -1 ) 
				move.add(new Move(PossibleMoves.RIGHT, r1, (c1-1)));
		if(pm!=PossibleMoves.DOUBLEUP)
			if(r1 != 0 && board[r1-1][c1] != -1 )
				move.add(new Move(PossibleMoves.DOWN, (r1-1), c1));

		//second space
		if(c2 != -2 && r2 != -2) {
			if(pm!=PossibleMoves.DOUBLETRIGHT)
				if(c2 != (m-1) && board[r2][c2+1] != -1)
					move.add(new Move(PossibleMoves.LEFT, r2, (c2+1)));
			if(pm!=PossibleMoves.DOUBLEDOWN)
				if(r2 != (n-1) && board[r2+1][c2] != -1) 
					move.add(new Move(PossibleMoves.UP, (r2+1), c2));
			if(pm!=PossibleMoves.DOUBLELEFT)
				if(c2 != 0 &&board[r2][c2-1] != -1)
					move.add(new Move(PossibleMoves.RIGHT, r2, (c2-1)));
			if(pm!=PossibleMoves.DOUBLEUP)
				if(r2 != 0 && board[r2-1][c2] != -1 )
					move.add(new Move(PossibleMoves.DOWN, (r2-1), c2));
		}
		return move;
	}

	private Double ManDist(State sol) {
		Double sumDistance=0.0;
		for(int i=0; i<this.getBoard().length; i++) {
			for(int j=0; j<this.getBoard()[i].length; j++) {
				int block = this.getBoard()[i][j];
				if(block == -1)
					continue;
				for(int k=0; k<sol.getBoard().length; k++) {
					for(int w=0; w<sol.getBoard()[k].length; w++) {
						if(sol.getBoard()[k][w] == block) {
							sumDistance+=Math.abs(i-k)+Math.abs(j-w);
						}
					}
				}
			}
		}
		return sumDistance;
	}

	private int TileMiss(State sol){ 
		int distance=0;
		for(int i=0; i<this.getBoard().length; i++) {
			for(int j=0; j<this.getBoard()[i].length; j++) {
				int block = this.getBoard()[i][j];
				if(block == -1)
					continue;
				for(int k=0; k<sol.getBoard().length; k++) {
					for(int w=0; w<sol.getBoard()[k].length; w++) {
						if(sol.getBoard()[k][w] == block) {
							if(i != k) 
								distance++;		
							if(j != w) 
								distance++;	
						}
					}
				}
			}
		}
		return distance;
	}
	public void fixSpacesLocation(ArrayList<String> spacesLoc) {
		if(spacesLoc.get(1).charAt(0) < spacesLoc.get(0).charAt(0)) 
			Collections.swap(spacesLoc, 0, 1);
		if(spacesLoc.get(1).charAt(0) == spacesLoc.get(0).charAt(0))
			if(spacesLoc.get(1).charAt(1) == spacesLoc.get(0).charAt(1))
				Collections.swap(spacesLoc, 0, 1);
	}

	private double multiplie(){
		if(spacesAmount == 1) return 5; // one block
		if(spacesStatus().equals("Horizon")) return 3;
		else return 3.5;
	}

	public String spacesStatus(){
		int x1 =Integer.parseInt(String.valueOf(spacesLocation.get(0).charAt(0)));
		int y1 = Integer.parseInt(String.valueOf(spacesLocation.get(0).charAt(1)));
		int x2 = Integer.parseInt(String.valueOf(spacesLocation.get(1).charAt(0)));
		int y2 = Integer.parseInt(String.valueOf(spacesLocation.get(1).charAt(1)));
		if(Math.abs(x1-x2) == 0 && Math.abs(y1-y2) == 1)
			return "Horizon";
		if(Math.abs(x1-x2) == 1 && Math.abs(y1-y2) == 0)
			return "Vertical";
		else
			return "not connected";
	}
	// Getters Setters
	public Double getF(State sol) {
		if(this.f != 0)
			return this.f;
		setF( this.getCost()+ ManDist(sol)*multiplie());
		return this.f;
	}

	public void setF(Double f) {
		this.f = f;
	}
	public PossibleMoves getPm() {
		return pm;
	}
	public void setPm(PossibleMoves pm) {
		this.pm = pm;
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
		String s = "";
		for (Integer[] row : this.board) {
			for(Integer h : row)
			{
				s+=h+",";
			}
			s+="\n";
		}
		return s;
	}
}
