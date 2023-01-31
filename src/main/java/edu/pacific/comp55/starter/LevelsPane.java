package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelsPane extends GraphicsPane {
	private MainApplication program;
	private GButton level1;
	private GButton level2;
	private GButton level3;
	private GImage levelsimg;
	private GImage joe;
	private GImage player;
	private boolean isMuted = false;

	public LevelsPane(MainApplication app) {
		this.program = app;
		levelsimg = new GImage("leveling.png", 175, -250);
		levelsimg.scale(0.4);
		level1 = new GButton("Back", 100, 700, 150, 75);
		level1.setFillColor(Color.WHITE);
		level2 = new GButton("Back", 100, 700, 150, 75);
		level2.setFillColor(Color.WHITE);
		level3 = new GButton("Back", 100, 700, 150, 75);
		level3.setFillColor(Color.WHITE);
	}

	public boolean checkIfMute() {
		return isMuted;
	}

	@Override
	public void showContents() {
		program.add(levelsimg);
		program.add(level1);
		program.add(level2);
		program.add(level3);
		program.add(player);
		program.add(joe);
	}

	@Override
	public void hideContents() {
		program.remove(levelsimg);
		program.remove(level1);
		program.remove(level2);
		program.remove(level3);
		program.remove(player);
		program.remove(joe);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == back) {
//			program.switchToMenu();
//		}

	}

}
