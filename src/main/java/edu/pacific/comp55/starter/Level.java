package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * @author Team No Focus!
 * 
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level aasd
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

	private static final int PLAYER_UP_VELOCITY = -20;
	private static int jumpCounter = 0;
	private Timer jumpUpTimer = new Timer(50, this);
	private Timer leftMoveTimer = new Timer(20, this);
	private Timer rightMoveTimer = new Timer(20, this);

	public double initSpeed = 10;

	private MainApplication mainScreen;
	private Map map;
	private Player player;
	private Timer eTimer = new Timer(50, this);
	private Cloud cloud;
	private double enemyVel = 3;
	private int time;
	private GLabel timeLabel;
	private GLabel liveLabel;
	private int count = 0;
	private GImage liveIMG;
	private GImage clockIMG;
	private boolean playerOnEdge = false;

	private GImage newPlayer;
	private ArrayList<Chunk> chunky;
	private GImage goalSpace;
	private int levelNum;

	// Constructor
	public Level(MainApplication program, int levelNum) {

		mainScreen = program;
		map = new Map();
		this.levelNum = levelNum;
		drawTimeLabel();
		drawLiveLabel();
		if (levelNum == 1) {
			setupLevel1();
		}
		newPlayer = player.getImage();
		chunky = map.getChunks();
	}

	public GLabel getTimeLabel() {
		mainScreen.setupInteractions();
		newPlayer = player.getImage();
		chunky = map.getChunks();
		return timeLabel;
	}

	public void setTimeLabel(GLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public boolean isPlayerOnGround() {
		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1)
				.getChunkIMG() && (jumpCounter > 1)) {
			return true;
		}

		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(3)
				.getChunkIMG() && (jumpCounter > 1)) {
			return true;

		}
//		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) != chunky.get(3)
//				.getChunkIMG() && (jumpCounter > 1)) {
//			return true;
//			
//		}

		return false;
	}

	void callEnemyCLoudMovement() {
		count++;
		for (Enemy ene : map.getEnemies()) {
			ene.getImage().move(enemyVel, 0);
			if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 200
					|| ene.getImage().getX() <= ene.getStartX()) {
				enemyVel *= -1;
				ene.getImage().move(enemyVel, 0);

			}
		}

		if (count % 15 == 0) {
			time--;
			timeLabel.setLabel(String.valueOf(time));
		}

		cloud.move(1325);
	}

	public void drawTimeLabel() {
		timeLabel = new GLabel("30", 200, 50);
		timeLabel.setColor(Color.WHITE);
		timeLabel.setFont("Arial-Bold-30");
		clockIMG = new GImage("clock guy.png", 145, 15);
		clockIMG.setSize(45, 45);
	}

	public void drawLiveLabel() {
		liveLabel = new GLabel("3", 95, 50);
		liveLabel.setColor(Color.WHITE);
		liveLabel.setFont("Arial-Bold-30");
		liveIMG = new GImage("liveshead.png", 20, 8);
		liveIMG.setSize(65, 65);
	}

	public void drawGoalSpace() {
		goalSpace = new GImage("redflag.png");
		goalSpace.setSize(70, 70);
	}

	public void callEnemyMovement() {
		for (Enemy ene : map.getEnemies()) {
			ene.getImage().move(enemyVel, 0);
			if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 200
					|| ene.getImage().getX() <= ene.getStartX()) {
				enemyVel *= -1;
				ene.getImage().move(enemyVel, 0);

			}
		}
	}

	public void startTimer() {
		eTimer.start();
	}

	public void stopTimer() {
		eTimer.stop();
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setupLevel1() {
		player = new Player(50, 415);
		cloud = new Cloud(50, 25);
		map.createChunk("g0", "background.png", 0, 0, 1900, 850);
		map.createChunk("g1", "ground1.png", 0, 515, 650, 250);
		map.createChunk("g2", "Spike.png", 650, 665, 140, 100);
		map.createChunk("g3", "ground1.png", 790, 425, 650, 350);
		map.createEnemy(900, 375);
		map.createEnemy(150, 465);
		time = 30;

		drawGoalSpace();
		goalSpace.setLocation(1150, 425 - goalSpace.getHeight());

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
		mainScreen.add(timeLabel);
		mainScreen.add(liveIMG);
		mainScreen.add(liveLabel);
		mainScreen.add(clockIMG);
		mainScreen.add(goalSpace);
		startTimer();

	}

	public void hideContents() {

	}

	public boolean checkGround() {
		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1)
				.getChunkIMG()) {

		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		/*
		 * Player key movements
		 */
		switch (keyCode) {
		case KeyEvent.VK_RIGHT:
			// rightMoveTimer = new Timer(20, this);
			rightMoveTimer.start();
			break;
		case KeyEvent.VK_LEFT:
			if (isPlayerOnEdge()) {
				break;
			}
			// leftMoveTimer = new Timer(20, this);
			leftMoveTimer.start();
			break;
		case KeyEvent.VK_SPACE:
			jumpUpTimer = new Timer(20, this);
			jumpUpTimer.start();
			break;
		case KeyEvent.VK_P:
			mainScreen.switchToPause();
			eTimer.stop();
		default:
			throw new IllegalArgumentException("Unexpected value: " + keyCode);
		}

	}

	public boolean isPlayerOnEdge() {
		if (newPlayer.getX() <= -5) {
			return true;
		}
		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {

		rightMoveTimer.stop();
		leftMoveTimer.stop();
		System.out.println("key is released");
	}

	boolean passedLevel() {
		if (player.getImage().getX() + 50 == goalSpace.getX()) {

			System.out.println("win");
			return true;
		}
		System.out.println("lose");
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == eTimer) {
			callEnemyCLoudMovement();
		}

		if (source == rightMoveTimer) {

			player.move(initSpeed, 0);

		}
		if (source == leftMoveTimer) {
			if (!isPlayerOnEdge()) {
				player.move(-initSpeed, 0);
			}

		}
		if (source == jumpUpTimer) {
			jumpCounter++;
			player.move(0, PLAYER_UP_VELOCITY + jumpCounter);
		}

		if (isPlayerOnGround()) {
			jumpUpTimer.stop();
			jumpCounter = 0;
		}

// OLD CODE FOR REFERENCE
//		if (player.moveState != null) {
//
//			if (source == rightMoveTimer) {
////				if (keyList.contains(KeyEvent.VK_RIGHT) && keyList.contains(KeyEvent.VK_LEFT)) {
////					return;
////				}
////
////				if (keyList.contains(KeyEvent.VK_RIGHT)) {
//					// player.setRightStep(true);
//
////		    initSpeed += 0.45;
////		    if (initSpeed >= PLAYER_WALK_VELOCITY) {
////			player.move(PLAYER_WALK_VELOCITY, 0);
////		    } else {
//					player.move(initSpeed, 0);
////		    }
//
//				}
//
//				if (keyList.contains(KeyEvent.VK_LEFT)) {
//					// player.setLeftStep(true);
//
//					initSpeed += 0.45;
//					if (initSpeed >= PLAYER_WALK_VELOCITY) {
//						player.move(PLAYER_WALK_VELOCITY, 0);
//					} else {
//						player.move(initSpeed, 0);
//					}
//
//				}
//
//			}

//		if (source == jumpUpTimer) {
//			jumpCounter++;
//
//			player.move(0, PLAYER_UP_VELOCITY + jumpCounter);
//
////			jumpStep += 20;
//		if (jumpStep >= jumpHeight) {
//		    jumpUpTimer.stop();
		// jumpDnTimer.start();
//		}
//		}

	}

}
