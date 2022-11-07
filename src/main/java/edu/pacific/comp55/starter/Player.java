package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.console.Console;
import acm.graphics.GRect;


public class Player extends Character {
	
	//players current position within window. NOT IMAGE
	private int currX;

	private int currY;
	
	//Amount player will move by when keyevent occurs
	public static final int velocityX = 50;
	public static final int velocityY = 10;
	
	public GRect temp;
	
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
	//GETTERS and SETTERS ^

	//Player Constructor
	public Player (characterType type, int startRow, int startCol) {
		super(type, startRow, startCol);
		currX = 100;
		currY = 300;
		
	temp = new GRect(currX, currY);
		
	}
	
	public void move(boolean direction) {
		
		if (direction)
			currX += velocityX;
		if (direction == false)
			currX -= velocityX;

		System.out.println(currX);
	}
	
	
}
