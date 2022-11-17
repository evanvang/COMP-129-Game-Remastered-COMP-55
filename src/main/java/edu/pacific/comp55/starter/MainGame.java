package edu.pacific.comp55.starter;

import java.awt.event.KeyEvent;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import javafx.scene.input.KeyCode;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GObject;

/**
 * @author Team No Focus!
 *
 *         MainGame class...
 */
public class MainGame extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GImage img;
	private GParagraph para;
	private GImage tile;
	private GImage EImg;
	private Level level;

	// Constructor
	public MainGame(MainApplication app) {

		this.program = app;
		level = new Level(app, 1);
	}

	// Abstract method from GraphicsPane
	@Override
	public void showContents() {
		level.showContents();
		// program.add(img);
		// ActionPerformed();
	}

	// Abstract method from GraphicsPane
	@Override
	public void hideContents() {
		program.remove(img);
	}

}