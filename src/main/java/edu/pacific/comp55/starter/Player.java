package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import acm.console.Console;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Player {

    // For the players image
    private GImage playerIMG;
    private GImage playerIMG_idle2;

    // Players current position within window
    private double x, y;

    // the active pressed key
    public MoveState moveState = null;

    // Amount player will move by when key event occurs
    public static final int velocityX = 10;
    public static final int velocityY = 10;

    public GRectangle bounds;

    // Player Constructor
    public Player(double x, double y) {
	this.x = x;
	this.y = y;
	playerIMG = new GImage("idle1.png");
	playerIMG.setSize(100, 100);
	playerIMG.setLocation(x, y);

	playerIMG_idle2 = new GImage("idle2.png");
	playerIMG_idle2.setSize(100, 100);
	playerIMG_idle2.setLocation(x, y);
	playerIMG_idle2.setVisible(false);

	bounds = new GRectangle(x + 10, y, getImage().getWidth() - 50, getImage().getHeight());
    }

    public void move(double x1, double y1) {

	playerIMG.setLocation(x + x1, y + y1);
	playerIMG_idle2.setLocation(x + x1, y + y1);

	updatePlayerPos();

	
    }

    
    public void runPlayerDeathSpinAnimation() {
	playerIMG.rotate(50);
	playerIMG_idle2.rotate(50);
    }
    

    public void updatePlayerPos() {
	x = playerIMG.getX();
	y = playerIMG.getY();

	bounds.setLocation(x + 10, y);
    }

    /*
     * 
     * Setters & Getters
     * 
     */
//	public boolean isJumping() {
//		return isJumping;
//	}
//
//	public void setJumping(boolean isJumping) {
//		this.isJumping = isJumping;
//	}
//
//	public boolean isRightStep() {
//		return isRightStep;
//	}
//
//	public void setRightStep(boolean isRightStep) {
//		this.isRightStep = isRightStep;
//	}
//
//	public boolean isLeftStep() {
//		return isLeftStep;
//	}
//
//	public void setLeftStep(boolean isLeftStep) {
//		this.isLeftStep = isLeftStep;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public int getVelocityX() {
//		return velocityX;
//	}
//
//	public int getVelocityY() {
//		return velocityY;
//	}

    public GImage getImage() {
	return playerIMG;
    }

    public GImage getImage_2() {
	return playerIMG_idle2;
    }

//    public GImage getplayerIMG() {
//	return playerIMG;
//    }

    public void setPlayerImage(String img) {
	GImage newIMGImage = new GImage(img);
	this.playerIMG = newIMGImage;
    }

}