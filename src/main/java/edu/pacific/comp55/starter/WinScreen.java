package edu.pacific.comp55.starter;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class WinScreen extends GraphicsPane {

	private MainApplication program;
	private GButton goBack;
	private GImage winText;
	
	public WinScreen(MainApplication app) {
		program = app;
		goBack = new GButton("Go Back", 580, 375, 150, 75);
		winText = new GImage("You Win!.png", 500, 100);

	}
	
	@Override
	public void showContents() {
		program.add(goBack);
		program.add(winText);
	}
	
	@Override
	public void hideContents() {
		program.remove(goBack);
		program.add(winText);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == goBack) {
			 program.removeAll();
	         program.switchToMenu();
	  
		}
	}
}
