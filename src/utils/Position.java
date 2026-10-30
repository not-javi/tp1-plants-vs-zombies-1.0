package utils;

import java.util.Objects;

public class Position {
	
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
		return pos;
	}
	
	public Position right() {
		Position pos = new Position(this.row, this.col + 1);
		return pos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return col == other.col && row == other.row;
	}

	public String positionToString() {
	    return "(" + this.col + ", " + this.row + ")";
	}
	
	public boolean isInCol(int col) {
		return this.col ==col;
	}
	
	public boolean isInside(int maxCols, int maxRows) {
		return this.col >= 0 && this.col < maxCols && this.row >= 0 && this.row < maxRows;
	}
}
	
