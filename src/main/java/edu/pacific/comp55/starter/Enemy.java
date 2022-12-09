package edu.pacific.comp55.starter;

import java.awt.event.ActionListener;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Enemy {

	private double startX;
	private double startY;
	private GImage enemyIMG;
	private String newPath;

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

	public GImage getImage() {
		return enemyIMG;
	}
	
	public String getPath() {
		return newPath;
	}

	public Enemy(String path, int startX, int startY) {
		this.newPath = path;
		this.startX = startX;
		this.startY = startY;
		this.enemyIMG = new GImage(path);
		this.enemyIMG.setSize(50, 50);
		this.enemyIMG.setLocation(startX, startY);

	}

//	public void move(double d) {
//		enemyIMG.move(vel, 0);
//		if(enemyIMG.getX()+enemyIMG.getWidth() >= d || enemyIMG.getX() <= startX) {
//			xVelocity *= -1;
//		}
//	}

}
