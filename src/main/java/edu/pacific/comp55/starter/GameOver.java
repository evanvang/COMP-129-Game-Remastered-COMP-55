package edu.pacific.comp55.starter;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class GameOver extends GraphicsPane {
	
	private MainApplication program;
	private GButton retry2;
	private GButton quit2;
	private GImage gOver;
	private Level level2;
	private int numLevel;
	
	public GameOver(MainApplication app, Level l) {
		program = app;
		level2 = l;
		gOver = new GImage("Game Over.png", 500, 150);
		retry2 = new GButton("Retry", 580, 375, 150, 75);
		quit2 = new GButton("Quit", 580, 500, 150, 75);
	}
	
	@Override
	public void showContents() {
		program.add(gOver);
		program.add(quit2);
		program.add(retry2);
	}
	
	@Override
	public void hideContents() {
		program.remove(gOver);
		program.remove(quit2);
		program.remove(retry2);
		level2.setGameOverToNull();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		numLevel = level2.getLevelNum();
		level2.setLives(3);
		if (obj == quit2) {
			level2.stopAllTimers();
			 program.removeAll();
			 level2.setLevelNum(1);
	         program.switchToMenu();
	  
		}
		if (obj == retry2) {	
			level2.stopAllTimers();
			program.removeAll();
			level2 = new Level(program,numLevel);
			program.switchToScreen(level2);
		}
	}

}
