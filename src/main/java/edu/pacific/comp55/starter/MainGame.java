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
 * MainGame class...
 */
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
    
    // Collection to store key inputs
    private HashMap<MoveDirection, Integer> keysPressed;
    private boolean isKeyDown;

    // Constructor
    public MainGame(MainApplication app) {
	keysPressed = new HashMap<>();
	isKeyDown = false;

	this.program = app;

	img = new GImage("idle1.png", player.getX(), player.getY());
	img.setSize(75, 75);

	// Testing, displaying 2 chunks 
	program.add(level1.getChunk("g1"));
	program.add(level1.getChunk("g2"));

//	tile = new GImage("ground1.png", 0, 400);
//	tile.setSize(500, 200);
//	program.add(tile);

    }
    
    // TODO: Store information of every key that's pressed into a collection
    @Override
    public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	
	if (keyCode == KeyEvent.VK_RIGHT) {
	    isKeyDown = true;
	    keysPressed.put(MoveDirection.RIGHT, keyCode);
	    System.out.println("Key pressed");
	    System.out.println("   " + keysPressed.toString() + "\n");
	}
    }
     
    @Override
    public void keyReleased(KeyEvent e) {
	
	// Empty the HashMap after key release
	keysPressed.clear();
	System.out.println("Key released");
	System.out.println("   " + keysPressed.toString() + "\n");

    }
    
    public void ActionPerformed() {
	if (keysPressed.containsKey(MoveDirection.RIGHT)) {
	    // Do something
	    System.out.println("'Right Key' down");
	}
    }
    
    // Abstract method from GraphicsPane
    @Override
    public void showContents() {
	program.add(img);
	ActionPerformed();
    }
    // Abstract method from GraphicsPane
    @Override
    public void hideContents() {
	program.remove(img);
    }

}