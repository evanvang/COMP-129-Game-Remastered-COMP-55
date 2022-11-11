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

public class MainGame extends GraphicsPane {
    private MainApplication program; // you will use program to get access to
				     // all of the GraphicsProgram calls
    private GImage img;
    private GParagraph para;
    private Player player;
    private GImage tile;
    private Enemy enemy1;
    private GImage EImg;

    private Level level1, level2;
    
    private HashMap<Integer, KeyEvent> keysPressed;
    private boolean isKeyDown;

    public MainGame(MainApplication app) {
	
	keysPressed = new HashMap<>();

	this.program = app;
	player = new Player(150, 325, new GImage("idle1.png", 300, 200));

	level1 = new Level();

	img = new GImage("idle1.png", player.getX(), player.getY());
	img.setSize(75, 75);

	// tile = new GImage("ground1.png", 0, 400);
	// tile.setSize(800, 200);

	// Commented out for hashmap testing
	// program.add(img);
	// program.add(tile);

	// Enemy
	enemy1 = new Enemy(450, 350);
	EImg = new GImage("pumpkin joe.png", enemy1.getStartX(), enemy1.getStartY());
	EImg.setSize(50, 50);
	program.add(EImg);

	// Testing, displaying 2 chunks 
	program.add(level1.getChunk("g1"));
	program.add(level1.getChunk("g2"));

	tile = new GImage("ground1.png", 0, 400);
	tile.setSize(500, 200);
	program.add(tile);

    }
    
    // TODO: Store information of every key that's pressed into a collection
    @Override
    public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	
	if (keyCode == KeyEvent.VK_RIGHT) {
	    isKeyDown = true;
	    // keysPressed.put(keyCode, KeyEvent.VK_RIGHT);
	}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
	
    }
    
    public void ActionPerformed(KeyEvent e) {
	
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