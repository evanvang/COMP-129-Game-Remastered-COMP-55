package edu.pacific.comp55.starter;

/*import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage img;
	private GParagraph para;

	public SomePane(MainApplication app) {
		this.program = app;
		img = new GImage("robot head.jpg", 100, 100);
		para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(img);
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == img) {
			program.switchToMenu();
		}
	}
}*/
import java.awt.event.KeyEvent;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.*;

import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane {
    private MainApplication program; // you will use program to get access to
				     // all of the GraphicsProgram calls
    private GImage img;
    private GParagraph para;
    private Player player;
    private GImage tile;
    private Enemy enemy1;
    private GImage EImg;

    private Level level;

    public SomePane(MainApplication app) {

	this.program = app;
	player = new Player(300, 200, new GImage("idle1.png", 300, 200));

	level = new Level();

	img = new GImage("idle1.png", player.getX(), player.getY());
	img.setSize(100, 100);

	tile = new GImage("Ground1.png", foo.getFloorX(), foo.getFloorY());
	tile.setSize(800, 200);
	program.add(img);
	program.add(tile);

	// Enemy
	enemy1 = new Enemy(200, 150);
	EImg = new GImage("pumpkin joe.png", enemy1.getStartX(), enemy1.getStartY());
	EImg.setSize(50, 50);
	program.add(EImg);
    }

    @Override
    public void showContents() {
	program.add(img);
    }

    @Override
    public void hideContents() {
	program.remove(img);
    }

}