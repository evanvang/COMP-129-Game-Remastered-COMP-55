package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GButton playButton;
	private GButton settingsButton;
	private GButton levelsButton;
	private GImage menuBackground;
	

	public MenuPane(MainApplication app) {
		super();
		program = app;

		playButton = new GButton("Play", 400, 600, 150, 75);
		playButton.setFillColor(Color.WHITE);
		

		settingsButton = new GButton("Settings", 850, 600, 150, 75);
		settingsButton.setFillColor(Color.WHITE);
		
		menuBackground = new GImage("Intro.png", 0, 0);
		menuBackground.scale(1.95);

	}

	@Override
	public void showContents() {
		program.add(menuBackground);
		program.add(playButton);
		program.add(settingsButton);
		
	}

	@Override
	public void hideContents() {
		program.remove(menuBackground);
		program.remove(playButton);
		program.remove(settingsButton);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == playButton) {
			program.switchToNewLevel();
		}
		if (obj == settingsButton) {
			program.switchToSettings();
		}
		if (obj == levelsButton) {
			program.switchToLevels();
		}
	}
}