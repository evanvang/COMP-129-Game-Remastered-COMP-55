package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GButton rect;
	private GImage menu;
	
	public MenuPane(MainApplication app) {
		super();
		program = app;
		rect = new GButton("Play", 300, 450, 200, 100);
		rect.setFillColor(Color.WHITE);
		menu = new GImage("Intro.png", -12, 0);
		rect = new GButton("Next", 200, 200, 200, 200);
		rect.setFillColor(Color.RED); 
	}

	@Override
	public void showContents() {
		program.add(menu);
		program.add(rect);
	}

	@Override
	public void hideContents() {
		program.remove(menu);
		program.remove(rect);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
	}
}