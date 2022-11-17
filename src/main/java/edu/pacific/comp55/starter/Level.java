package edu.pacific.comp55.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * @author Team No Focus!
 * 
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level
 * 
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

	private MainApplication mainScreen;
	private Map map;
	private Player player;
	private Enemy enemy;
	private Timer timer;
	private Cloud cloud;
	private double enemyVel = 3;
	private double cloudVel = 3;

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

		mainScreen.setupInteractions();
	}

	public void showContents() {
		mainScreen.add(map.getChunks().get(0).getbackgroundIMG());
		mainScreen.add(map.getChunks().get(1).getChunkIMG());
		mainScreen.add(map.getChunks().get(2).getspikeIMG());
		mainScreen.add(map.getChunks().get(3).getChunkIMG());
		mainScreen.add(player.getImage());
		mainScreen.add(map.getEnemies().get(0).getImage());
		mainScreen.add(map.getEnemies().get(1).getImage());
		mainScreen.add(cloud.getImage());

	}

	public void hideContents() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_RIGHT) {
			System.out.println("Level Key pressed");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Level Key released");

	}

	public void ActionPerformed() {

	}

	public void startTimer() {
		timer.start();
	}

	public void setupLevel1() {
		player = new Player(50, 415);
		enemy = new Enemy(300, 475);
		cloud = new Cloud(100, 25);

		map.createChunk("g0", "background.png", 0, 0, 1900, 850);
		map.createChunk("g1", "ground1.png", 0, 515, 650, 250);
		map.createChunk("g2", "Spike.png", 650, 665, 140, 100);
		map.createChunk("g3", "ground1.png", 790, 425, 650, 350);

		map.createEnemy(900, 375);
		map.createEnemy(150, 465);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Enemy ene : map.getEnemies()) {
			// vel *= -1;
			ene.getImage().move(enemyVel, 0);
			if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 200
					|| ene.getImage().getX() <= ene.getStartX()) {
				enemyVel *= -1;

				// cloud.move(1350);

			}
		}
		cloud.getImage().move(cloudVel, 0);
		if (cloud.getImage().getX() + cloud.getImage().getWidth() >= cloud.getStartX() + 1325
				|| cloud.getImage().getX() <= cloud.getStartX()) {
			cloudVel *= -1;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
