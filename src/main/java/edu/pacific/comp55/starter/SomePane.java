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
		
		//enemy
				enemy1 = new Enemy( 200, 150);
				EImg = new GImage("pumpkin joe.jpg",enemy1.getStartX(), enemy1.getStartY());
				EImg.setSize(50, 50);
				program.add(EImg);
				
		
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

	// This method is the user's controller
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		// Move the player right
		if (keyCode == KeyEvent.VK_RIGHT) {
			player.move(MoveDirection.RIGHT);
			img.move(player.getVelocityX(), 0);
			
			System.out.println("'RIGHT ARROW' key has been pressed.");
		}
		
		// Move the player left
		if (keyCode == KeyEvent.VK_LEFT) {
			player.move(MoveDirection.LEFT);
			img.move((-1)*player.getVelocityX(), 0);
			
			System.out.println("'LEFT ARROW' key has been pressed.");
		}
		
		// TODO: Implement the player jump mechanic
		if (keyCode == KeyEvent.VK_SPACE) {
			player.move(MoveDirection.SPACE);
			img.move(0, (-1)*player.getVelocityY());
			
			System.out.println("'SPACE' key has been pressed.");
		}
	}

}