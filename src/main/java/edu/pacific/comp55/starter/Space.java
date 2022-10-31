package edu.pacific.comp55.starter;

public class Space {
	
	public String toString() {
		return "r" + this.row + "c" + this.col;
	}

	private int row;
	private int col;

	public Space(int row, int col) {
		this.row = row;
		this.col = col;
	}

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

//	public Space() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
