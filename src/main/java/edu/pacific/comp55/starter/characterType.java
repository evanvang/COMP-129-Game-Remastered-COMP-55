package edu.pacific.comp55.starter;

public enum characterType {
PLAYER, ENEMY, BOSS;
	
	public String toString() {
		switch(this) {
			case PLAYER: return "player";
			case ENEMY: return "enemy";
			case BOSS: return "boss";
		}
		return "n/a";
	}
}
