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
    private GImage tile;
    private GImage EImg;
    private Level level;
    
    
    // Collection to store key inputs
    private HashMap<MoveDirection, Integer> keysPressed;
    private boolean isKeyDown;

    // Constructor
    public MainGame(MainApplication app) {
    	keysPressed = new HashMap<>();
    	isKeyDown = false;
    	this.program = app;
    	level = new Level(app, 1);
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
    	level.showContents();
	//program.add(img);
	//ActionPerformed();
    }
    // Abstract method from GraphicsPane
    @Override
    public void hideContents() {
	program.remove(img);
    }

}