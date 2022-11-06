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

	
	
	public SomePane(MainApplication app) {
		this.program = app;
		player = new Player(characterType.PLAYER,0,0);
		
		img = new GImage("idle1.png", player.getCurrX(), player.getCurrY());
		img.setSize(100, 100);
		program.add(img);
		//para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		//para.setFont("Arial-24");
		
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
		if (keyCode == KeyEvent.VK_P) {
			player.getCurrX();
			System.out.println("Key 'P' has been pressed!");
			
		}
	}

	
//	public void keyPressed(int k) {
//		if (k == KeyEvent.VK_A)
//			player.setLeft(true);
//		if (k == KeyEvent.VK_D)
//			player.setRight(true);
//		if (k == KeyEvent.VK_S)
//			player.setDown(true);
//		if (k == KeyEvent.VK_W)
//			player.startJumping(true);
//		if (k == KeyEvent.VK_UP)
//			player.startJumping(true);
//		if (k == KeyEvent.VK_RIGHT)
//			player.setRight(true);
//		if (k == KeyEvent.VK_DOWN)
//			player.setDown(true);
//		if (k == KeyEvent.VK_LEFT)
//			player.setLeft(true);
//	}
//
//	public void keyReleased(int k) {
//		if (k == KeyEvent.VK_A)
//			player.setLeft(false);
//		if (k == KeyEvent.VK_D)
//			player.setRight(false);
//		if (k == KeyEvent.VK_S);
//			player.setDown(false);
//		if (k == KeyEvent.VK_W)
//			player.startJumping(false);
//		if (k == KeyEvent.VK_UP)
//			player.startJumping(false);
//		if (k == KeyEvent.VK_RIGHT)
//			player.setRight(false);
//		if (k == KeyEvent.VK_DOWN)
//			player.setDown(false);
//		if (k == KeyEvent.VK_LEFT)
//			player.setLeft(false);
//	}
}
