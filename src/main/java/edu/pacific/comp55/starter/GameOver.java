package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class GameOver extends GraphicsPane {
	private MainApplication program;
	private GButton retry;
	private GButton quit;
	private Level level;
	private GLabel prompt;
    private int numLevel;
	public GameOver(MainApplication app, Level l) {
		program = app;
		level = l;
		prompt = new GLabel("Game Over", 580, 200);
		retry = new GButton("Retry", 580, 375, 150, 75);
		quit = new GButton("Quit", 580, 550, 150, 75);
		
	}
	
	@Override
	public void showContents() {
		program.add(prompt);
		program.add(quit);
		program.add(retry);
	}
	
	@Override
	public void hideContents() {
		program.remove(prompt);
		program.remove(quit);
		program.remove(retry);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		hideContents();
		numLevel = level.getLevelNum();
		if (obj == quit) {
			 program.removeAll();
	         program.switchToMenu();
			
		}
		
		if (obj == retry) {			
			System.out.println("back to level: " + numLevel);
			program.removeAll();
			level = new Level(program,numLevel);
			program.switchToScreen(level);
		}
		
	}
}
