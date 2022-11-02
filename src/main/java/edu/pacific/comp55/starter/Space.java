package edu.pacific.comp55.starter;

public class Space {
	private int row;
	private int col;
	
	public String toString() {
		return "r" + this.row + "c" + this.col;
	}

	// Constructor
	public Space(int row, int col) {
		this.row = row;
		this.col = col;
	}

	// Setters & Getters
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	//test
}
