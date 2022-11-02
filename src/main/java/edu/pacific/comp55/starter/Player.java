package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.graphics.GRect;


public class Player extends Character {
	
	//player's current position
	private int currX;
	private int currY;
	
	//the direction player should be going
	private int velocityX;
	private int velocityY;
	
	//checks current movement
	private boolean goLeft;
	private boolean goRight;
	private boolean isJumping;
	private boolean goDown;
	
	//checks to see if player can still jump/is touching ground
	private boolean isInAir;
	private boolean isOnGround;
	
	public Player() {
		currX = 100;
		currY = 100;
		velocityX = 0;
		velocityY = 0;
	}

	public void setGraphics() {
		GRect rect = new GRect(300, 300, currX, currY);
		rect.setFilled(true);
		rect.setColor(Color.RED);
	}
	public void setX(int num) {
		currX = num;
	}
	public void setY(int num) {
		currY = num;
	}
	
	public int getX() {
		return currX;
	}
	
	public int getY() {
		return currY;
	}
	
	public void setLeft(boolean updateDir) {
		goLeft = updateDir;
		
	}
	
	public void setRight(boolean updateDir) {
		goRight = updateDir;
	}
	
	public void startJumping(boolean updateDir) {
		isJumping = updateDir;
	}
	
	public void setDown(boolean updateDir) {
		goDown = updateDir;
	}
	
	public int getVelocityX() {
		return velocityX;
	}
	
	public int getVelocityY() {
		return velocityY;
	}
	
	public void changeVelocityX(int num) {
		velocityX += num;
	}
	
	public void changeVelocityY(int num) {
		velocityY += num;
	}
	
	
	public void updateDirection() {
		if (goLeft == true) {
			changeVelocityX(-2);
		}
		
		if (goRight == true) {
			changeVelocityX(2);
		}
		
		currX += velocityX;
		
	}
	
}
