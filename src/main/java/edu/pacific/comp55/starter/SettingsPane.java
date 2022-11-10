package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private MainApplication program;
	private GButton back;
	
	public SettingsPane(MainApplication app) {
		this.program = app;
		back = new GButton("Back", 300, 450, 150, 75);
		back.setFillColor(Color.WHITE);
	}
	
	@Override
	public void showContents() {
		program.add(back);
		
	}

	@Override
	public void hideContents() {
		program.remove(back);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.switchToMenu();
		}
	}

}
