package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		return new Position(this.row, this.col - 1);
	}

	public Position right() {
		return new Position(this.row, this.col + 1);
	}

	public Position up() {
		return new Position(this.row - 1, this.col);
	}

	public Position down() {
		return new Position(this.row + 1, this.col);
	}

	public Position upRight() {
		return new Position(this.row - 1, this.col + 1);
	}

	public Position downRight() {
		return new Position(this.row + 1, this.col + 1);
	}

	public Position upLeft() {
		return new Position(this.row - 1, this.col - 1);
	}

	public Position downLeft() {
		return new Position(this.row + 1, this.col - 1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(col), Integer.valueOf(row));
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
		return this.col == col;
	}

	public boolean isInside(int maxCols, int maxRows) {
		return this.col >= 0 && this.col < maxCols && this.row >= 0 && this.row < maxRows;
	}
	
	public List<Position> areaRad1() {
		List<Position> aux = new ArrayList<>(Arrays.asList(this.up(), this.down(), this.left(),
				this.right(), this.upLeft(), this.upRight(), this.downLeft(), this.downRight()));
		return aux;
	}
	
	public int column() {
		return col;
	}
	
	public int row() {
		return row;
	}
}
