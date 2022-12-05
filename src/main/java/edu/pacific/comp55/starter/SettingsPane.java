package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private MainApplication program;
	private GButton back;
	private GButton mute;
	private boolean isMuted = false;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		
		mute = new GButton("Mute", 300, 150, 150, 75);
		mute.setFillColor(Color.WHITE);
		back = new GButton("Back", 300, 450, 150, 75);
		back.setFillColor(Color.WHITE);
	}
	
	public boolean checkIfMute() {
		return isMuted;
	}
	@Override
	public void showContents() {
		program.add(back);
		program.add(mute);
	}

	@Override
	public void hideContents() {
		program.remove(back);
		program.remove(mute);
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
			}
			else {
				isMuted = true;
				
			}
		}
	}

}
