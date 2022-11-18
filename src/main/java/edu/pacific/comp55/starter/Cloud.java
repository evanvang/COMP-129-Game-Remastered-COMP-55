package edu.pacific.comp55.starter;

import java.awt.event.ActionListener;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Cloud {

	private double startX;
	private double startY;
	private GImage cloudIMG;
	private double cloudVel = 3;

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
		return cloudIMG;
	}

	public Cloud(int startX, int startY) {

		this.startX = startX;
		this.startY = startY;
		this.cloudIMG = new GImage("cloud.png");
		this.cloudIMG.setSize(235, 150);
		this.cloudIMG.setLocation(startX, startY);

	}

//	public void move(int finalX) {
//		cloudIMG.move(xVelocity, 0);
//		if (cloudIMG.getX() + cloudIMG.getWidth() >= finalX || cloudIMG.getX() <= startX) {
//			xVelocity *= -1;
//		}
//	}

	public void move(int finalX) {
		getImage().move(cloudVel, 0);
		if (getImage().getX() + getImage().getWidth() >= getStartX() + 1325 || getImage().getX() <= getStartX()) {
			cloudVel *= -1;
		}
	}

}