package edu.pacific.comp55.starter;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;



public class Enemy  {

	public double getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public static int getVelocityx() {
		return velocityX;
	}
	
	 public GObject getImage() {
	    	return enemyIMG;
	    }

	private double startX;
    private double startY;
	public static final int velocityX = 5;
	public static final int INIT_X_VELOCITY = 5;
	public static final int BREAK_MS = 30;
	private int xVelocity=5;
	Timer t;
	private GImage enemyIMG;
	
	
	public Enemy(int startX, int startY) {
	
		this.startX = startX;
		this.startY = startY;
		this.enemyIMG = new GImage("pumpkin joe.png");
		this.enemyIMG.setSize(50,50);
		this.enemyIMG.setLocation(startX,startY);
	
	}
	
	public void run() {
			
	}
	
	public void moveEnemy() {
		double saveIX = startX;
		startX += xVelocity;
		if (startX > 300) {
			startX -= xVelocity;
		}
		if (startX < saveIX) {
			startX += xVelocity;
		}
		}

	public static void main(String[] args) { 
		  
	}

	
}

