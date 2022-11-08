package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Map extends GraphicsPane{
	
	
	public double floorX = 0;
	public double getFloorX() {
		return floorX;
	}

	public double getFloorY() {
		return floorY;
	}

	public double floorY = 400;
	
	public static double width = 300;
	public static double height = 300;
	
	public GRect r;
	private MainApplication floor;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Map(MainApplication app) {
		r = new GRect(floorX, floorY, 800, 200);
	}
	
	

	public GRect getFloor() {
		return r;
	}
	

	@Override
	public void showContents() {
		//floor.add(r);
	}

	@Override
	public void hideContents() {
	
	}
	
//	public Map() {
//		GRect r = new GRect(floorX, floorY, 100, 100);
//		r.setColor(Color.DARK_GRAY);
//		r.setFillColor(Color.DARK_GRAY);
//		r.setFilled(true);
//		r.setLocation(200, 200);
//	}
	
	

}
