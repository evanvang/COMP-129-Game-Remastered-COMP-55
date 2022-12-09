package edu.pacific.comp55.starter;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
public class PausePane extends GraphicsPane {
	private MainApplication program;
	//private GButton resume;
	private GButton retry;
	private GButton quit;
	private Level level;
    private int numLevel;
	public PausePane(MainApplication app, Level l) {
		program = app;
		level = l;
		//resume = new GButton("Resume", 580, 300, 150, 75);
		retry = new GButton("Retry", 580, 375, 150, 75);
		quit = new GButton("Quit", 580, 550, 150, 75);
	}
	
	@Override
	public void showContents() {
		program.add(quit);
		//program.add(resume);
		program.add(retry);
	}
	
	@Override
	public void hideContents() {
		program.remove(quit);
		//program.remove(resume);
		program.remove(retry);
		level.setPauseToNull();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		numLevel = level.getLevelNum();
		if (obj == quit) {
			level.stopAllTimers();
			 program.removeAll();
			 level.setLevelNum(1);
	         program.switchToMenu();
	  
		}
		//if (obj == resume) {
			//program.switchToScreen(level);
			
		//}
		if (obj == retry) {	
			level.stopAllTimers();
			program.removeAll();
			level = new Level(program,numLevel);
			program.switchToScreen(level);
		}
	}

}
