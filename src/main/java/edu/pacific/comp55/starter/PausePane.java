package edu.pacific.comp55.starter;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
public class PausePane extends GraphicsPane {
	private MainApplication program;
	private GButton resume;
	private GButton retry;
	private GButton quit;
	private Level level;
    private int numLevel;
	public PausePane(MainApplication app, Level l) {
		program = app;
		level = l;
		resume = new GButton("Resume", 580, 300, 150, 75);
		retry = new GButton("Retry", 580, 375, 150, 75);
		quit = new GButton("Quit", 580, 450, 150, 75);
	}
	
	@Override
	public void showContents() {
		program.add(quit);
		program.add(resume);
		program.add(retry);
	}
	
	@Override
	public void hideContents() {
		program.remove(quit);
		program.remove(resume);
		program.remove(retry);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		hideContents();
		//numLevel = level.getLevelNum();
		if (obj == quit) {
//            level.stopTimer();
//			program.switchToMenu();
		}
		if (obj == resume) {
			program.switchToLevel();
		}
		if (obj == retry) {
			numLevel = level.getLevelNum();
			level = new Level(program,numLevel);
			program.switchToScreen(level);
		}
	}

}
