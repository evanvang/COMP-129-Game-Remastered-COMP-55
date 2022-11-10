package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.console.Console;
import acm.graphics.GImage;
import acm.graphics.GRect;

public class Player {

    // For the players image
    private GImage playerIMG;
    
    // Players current position within window
    private int x, y;

    // Amount player will move by when key event occurs
    public static final int velocityX = 10;
    public static final int velocityY = 10;

    // Player Constructor
    public Player(int x, int y, GImage playerIMG) {
	this.x = x;
	this.y = y;
	this.playerIMG = playerIMG;
    }

    // Updates Player (x,y)
    public void move(MoveDirection direction) {

	switch (direction) {
	case RIGHT:
	    x += velocityX;
	    break;
	case LEFT:
	    x -= velocityX;
	    break;
	case SPACE:
	    y -= (-1) * velocityY;
	    break;

	default:
	    System.out.println("Switch case failed");
	}
    }    
    
    // Setters & Getters
    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getVelocityX() {
	return velocityX;
    }

    public int getVelocityY() {
	return velocityY;
    }

}