package edu.pacific.comp55.starter;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;



public class Enemy  {
	

	private double startX;
    private double startY;
	private GImage enemyIMG;
//	public static final int velocityX = 5;
//	public static final int INIT_X_VELOCITY = 5;
//	public static final int BREAK_MS = 30;
    private int xVelocity=1;
//	Timer t;

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

	
	 public GObject getImage() {
	    	return enemyIMG;
	    }
	
	public Enemy(int startX, int startY) {
	
		this.startX = startX;
		this.startY = startY;
		this.enemyIMG = new GImage("pumpkin joe.png");
		this.enemyIMG.setSize(50,50);
		this.enemyIMG.setLocation(startX,startY);
		
	
	}
	
	public void move(int finalX) {
		enemyIMG.move(xVelocity, 0);
		if(enemyIMG.getX()+enemyIMG.getWidth() >= finalX || enemyIMG.getX() <= startX) {
			xVelocity *= -1;
		}
	}

	public void run() {
			
	}
	

	public static void main(String[] args) { 
		  //Enemy e = new Enemy(100,100);
		 
	}

	
}

