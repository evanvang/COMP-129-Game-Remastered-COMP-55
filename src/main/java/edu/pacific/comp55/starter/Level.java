package edu.pacific.comp55.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GRect;

/**
 * @author Team No Focus!
 * 
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level
 * 
 */
public class Level implements ActionListener {

	// TODO: Integrate Player and Enemy classes with Level
	private MainApplication mainScreen;
	private Map map;
	private Player player;
	private Enemy enemy;
	private Timer timer;

	// Constructor
	public Level(MainApplication program, int levelNum) {
		this.timer = new Timer(50, this);
		// this.enemy = new Enemy (50,50);
		mainScreen = program;
		map = new Map();

		if (levelNum == 1) {
			setupLevel1();
		}
		startTimer();
	}

	public void showContents() {
		mainScreen.add(map.getChunks().get(0).getChunkIMG());
		mainScreen.add(map.getChunks().get(1).getspikeIMG());
		mainScreen.add(map.getChunks().get(2).getChunkIMG());

		mainScreen.add(player.getImage());
		mainScreen.add(enemy.getImage());

	}

	public void hideContents() {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			player.currentDirection = MoveDirection.LEFT;
			timer.start();
		}
	}

	public void keyReleased(KeyEvent e) {
		timer.stop();
	}

	// Generates every chunk of the game world
	public void generateChunks() {

	}

	public void setupLevel1() {
		player = new Player(50, 415);
		enemy = new Enemy(300, 475);
		map.createChunk("g0", "ground1.png", 0, 515, 650, 250);
		map.createChunk("g1", "Spike.png", 650, 665, 140, 100);
		map.createChunk("g2", "ground1.png", 790, 425, 650, 350);

	}

	public void startTimer() {
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("hello");
//		player.move();
		enemy.move(400);
	}

}
