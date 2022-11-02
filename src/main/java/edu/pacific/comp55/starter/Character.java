package edu.pacific.comp55.starter;

public class Character {
	private characterType charType;
	private Space startPos;
	
	// Constructor
	public Character(characterType type, int startRow, int startCol) {
		charType = type;
		startPos = new Space (startRow, startCol);
	}

	// Setters & Getters
	public characterType getCharType() {
		return charType;
	}

	public void setCharType(characterType charType) {
		this.charType = charType;
	}

	public Space getStartPos() {
		return startPos;
	}

	public void setStartPos(Space startPos) {
		this.startPos = startPos;
		
	}
	
	public static void main(String[] args) {

	}

	@Override
	public String toString() {
		return "Character [charType=" + charType + ", startPos=" + startPos + "]";
	}


}
