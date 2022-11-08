package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.console.Console;
import acm.graphics.GRect;


public class Player {
	
	// Players current position within window. NOT IMAGE
	private int currX, currY;
	
	// Amount player will move by when keyevent occurs
	public static final int velocityX = 10;
	public static final int velocityY = 10;
		
	// Player Constructor
		public Player (int currX, int currY) {
			this.currX = currX;
			this.currY = currY;		
		}

	// Updates Player (x,y)
		public void move(MoveDirection direction) {
		
		switch(direction) {
		case RIGHT:
			currX += velocityX;
			break;
		case LEFT: 
			currX -= velocityX;
			break;
		case SPACE:
			currY -= (-1)*velocityY;
			break;
			
		default:
			System.out.println("Switch case failed");
		}
	}
	
		// Setters/Getters
		public int getCurrX() {
			return currX;
		}

		public void setCurrX(int currX) {
			this.currX = currX;
		}

		public int getCurrY() {
			return currY;
		}

		public void setCurrY(int currY) {
			this.currY = currY;
		}
		
		public int getVelocityX() {
			return velocityX;
		}
		
		public int getVelocityY() {
			return velocityY;
		}
	
}