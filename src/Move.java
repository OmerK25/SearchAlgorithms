/*
 * This class represents a possible move on the board in a given state.
 */

public class Move {

	private PossibleMoves p;
	int i1,j1,i2,j2;

	Move(PossibleMoves p, int i, int j){
		this.p = p;
		this.i1 = i;
		this.j1 = j;
		this.i2 = -1;
		this.j2 = -1;
	}

	@Override
	public String toString() {
		if(i2 == -1)
			return "Move "+ this.p +" index ["+i1+j1+"]" ;
		return "Move "+ this.p +" index ["+i1+j1+"] and index ["+i2+j2+"]" ;
	}

	public PossibleMoves getP() {
		return p;
	}

	public void setP(PossibleMoves p) {
		this.p = p;
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public int getJ1() {
		return j1;
	}

	public void setJ1(int j1) {
		this.j1 = j1;
	}

	public int getI2() {
		return i2;
	}

	public void setI2(int i2) {
		this.i2 = i2;
	}

	public int getJ2() {
		return j2;
	}

	public void setJ2(int j2) {
		this.j2 = j2;
	}

	Move(PossibleMoves p, int i1, int j1, int i2, int j2){
		this.p = p;
		this.i1 = i1;
		this.j1 = j1;
		this.i2 = i2;
		this.j2 = j2;
	}

}
