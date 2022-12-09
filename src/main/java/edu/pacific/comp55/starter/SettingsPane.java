package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private MainApplication program;
	private GButton back;
	private GButton mute;
	private GImage settingsimg;
	private GImage joe;
	private GImage player;
	private boolean isMuted = false;

	public SettingsPane(MainApplication app) {
		this.program = app;
		settingsimg = new GImage("settings img.png", 175, -250);
		settingsimg.scale(0.4);
		player = new GImage("think.png", 500, 385);
		player.scale(0.7);
		joe = new GImage("joe mama.png", 975, 600);
		joe.scale(0.7);
		mute = new GButton("Mute", 550, 300, 150, 75);
		mute.setFillColor(Color.WHITE);
		back = new GButton("Back", 100, 700, 150, 75);
		back.setFillColor(Color.WHITE);
	}

	public boolean checkIfMute() {
		return isMuted;
	}

	@Override
	public void showContents() {
		program.add(settingsimg);
		program.add(back);
		program.add(mute);
		program.add(player);
		program.add(joe);
	}

	@Override
	public void hideContents() {
		program.remove(settingsimg);
		program.remove(back);
		program.remove(mute);
		program.remove(player);
		program.remove(joe);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.switchToMenu();
		}
		if (obj == mute) {
			if (isMuted) {
				isMuted = false;
			} else {
				isMuted = true;

			}
		}
	}

}
