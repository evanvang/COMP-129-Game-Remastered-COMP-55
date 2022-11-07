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
import java.awt.*;
import java.awt.event.*;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
									// all of the GraphicsProgram calls
	private GImage img;
	private GParagraph para;
	private Player player;
	private GImage tile;
	
	private Map foo;
	
	
	public SomePane(MainApplication app) {
		this.program = app;
		player = new Player(characterType.PLAYER,0,0);
		foo = new Map();
		
		img = new GImage("idle1.png", player.getCurrX(), player.getCurrY());
		img.setSize(100, 100);
		
		tile = new GImage("robot head.jpg", foo.getFloorX(), foo.getFloorY());
		//tile.setSize(100, 100):
		program.add(img);
		program.add(tile);
		
	}
	
	
	
	
//	public SomePane(MainApplication app) {
//		this.program = app;
//		img = new GImage("idle1.png", 100, 100);
//		para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
//		para.setFont("Arial-24");
//		
//	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(foo.getFloor());
		//program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(img);
	//	program.remove(para);
	}

	//@Override
//	public void mousePressed(MouseEvent e) {
//		para.setText("you need\nto click\non the eyes\nto go back");
//		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == img) {
//			program.switchToMenu();
//		}
//	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		boolean right = false;
		boolean left = false;

		if (keyCode == KeyEvent.VK_RIGHT) { // move the player right
			right = true;
			player.move(right);
			
			img.setLocation(player.getCurrX(), player.getCurrY());
			
			System.out.println("Key 'Right Arrow' has been pressed!");
		}
		
		if (keyCode == KeyEvent.VK_LEFT) { // move the player right
			left = false;
			player.move(left);
			
			
			img.setLocation(player.getCurrX(), player.getCurrY());
				
			System.out.println("Key 'Left Arrow' has been pressed!");
		}
	}

}
