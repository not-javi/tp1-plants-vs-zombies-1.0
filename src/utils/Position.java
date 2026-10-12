package utils;

public class Position {
	
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Position(Position pos) {
		this.row = pos.row;
		this.col = pos.col;
	}
	
	public Position left() {
		Position pos = new Position(this.row, this.col - 1);
		if (pos.inBoard()) {
			return pos;
		}
		return null;
	}
	
	public Position right() {
		Position pos = new Position(this.row, this.col + 1);
		if (pos.inBoard()) {
			return pos;
		}
		return null;
	}

	public boolean inBoard() {
		return ( 0 <= this.col && this.col < NUM_COLS && 0 <= this.row && this.row < NUM_ROWS);
	}
	
	public boolean equals(Position pos) {
		return (this.row == pos.row && this.col == pos.col);
	}
	
	public String positionToString() {
	    return "(" + this.col + ", " + this.row + ")";
	}
	
	
	
	
	
	
}
	
