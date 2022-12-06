
package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

/**
 * @author Team No Focus!
 * 
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level
 * 
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

	// Animation
	private Timer idleAnimationTimer_1 = new Timer(20, this);
	private Timer idleAnimationTimer_2 = new Timer(20, this);
	private int idleAnimationCount = 100;

	// Walk friction after key released
	private Timer rightWalkFrictionTimer = new Timer(20, this);
	private Timer leftWalkFrictionTimer = new Timer(20, this);
	private double walkFriction = 10;

	// Enemy collision impact
	private Timer hitTimer = new Timer(20, this);
	private double hitImpact = 10;

	// Player movement
	private static final int PLAYER_UP_VELOCITY = -20;
	private static int jumpCounter = 0;
	private Timer jumpUpTimer = new Timer(10, this);
	private Timer leftMoveTimer = new Timer(10, this);
	private Timer rightMoveTimer = new Timer(10, this);

	public double initSpeed = 10;

	private MainApplication mainScreen;
	private Map map;
	private Player player;
	private Timer eTimer = new Timer(50, this);
	private Cloud cloud;
	private double enemyVel = 3;
	private int time;
	private int startX;
	private int startY;
	private GLabel timeLabel;
	private GLabel liveLabel;
	private int count = 0;
	private GImage liveIMG;
	private GImage clockIMG;

	private GImage newPlayer;
	private ArrayList<Chunk> chunky;
	private GImage goalSpace;
	private int levelNum;
	private int lives;
	private PausePane pause;

	// Constructor
	public Level(MainApplication program, int levelNum) {

		mainScreen = program;
		map = new Map();
		this.levelNum = levelNum;
		drawTimeLabel();
		drawLiveLabel();
		if (levelNum == 1) {
			setupLevel1();
		} else if (levelNum == 2) {
			System.out.println("retry");
			setupLevel2();
		}
//		newPlayer = player.getImage();
//		chunky = map.getChunks();
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

	public void showDetails() {
		mainScreen.add(timeLabel);
		mainScreen.add(liveIMG);
		mainScreen.add(liveLabel);
		mainScreen.add(clockIMG);
		mainScreen.add(goalSpace);
	}

	public void showContents() {
		if (levelNum == 1) {
			mainScreen.add(map.getChunks().get(0).getbackgroundIMG());
			mainScreen.add(map.getChunks().get(1).getChunkIMG());
			mainScreen.add(map.getChunks().get(2).getspikeIMG());
			mainScreen.add(map.getChunks().get(3).getChunkIMG());
			mainScreen.add(player.getImage());
			mainScreen.add(player.getImage_2());
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(map.getEnemies().get(1).getImage());
			mainScreen.add(cloud.getImage());
		} else {
			// System.out.println("add level 2");
			mainScreen.add(map.getChunks().get(0).getbackgroundIMG());
			mainScreen.add(map.getChunks().get(1).getChunkIMG());
			mainScreen.add(map.getChunks().get(2).getspikeIMG());
			mainScreen.add(map.getChunks().get(3).getChunkIMG());
			mainScreen.add(map.getChunks().get(4).getspikeIMG());
			mainScreen.add(map.getChunks().get(5).getChunkIMG());
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(map.getEnemies().get(1).getImage());
			mainScreen.add(player.getImage());
			mainScreen.add(player.getImage_2());
		}
		newPlayer = player.getImage();
		chunky = map.getChunks();
		showDetails();
		startTimer();

	}

	public void hideContents() {

		mainScreen.removeAll();
		map.removeChunks(chunky);
		map.removeEnemies(map.getEnemies());
		System.out.println("hide");
	}

	public void setupLevel1() {
		startX = 50;
		startY = 415;
		player = new Player(startX, startY);
		cloud = new Cloud(50, 25);
		map.createChunk("g0", "background.png", 0, 0, 1900, 850);
		map.createChunk("g1", "ground1.png", 0, 515, 650, 250);
		map.createChunk("g2", "Spike.png", 650, 665, 140, 100);
		map.createChunk("g3", "ground1.png", 790, 425, 650, 350);
		map.createEnemy(900, 375);
		map.createEnemy(150, 465);
		time = 30;
		lives = 3;
		drawGoalSpace();
		goalSpace.setLocation(1150, 425 - goalSpace.getHeight());

		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);
	}

	public void setupLevel2() {
		System.out.println("setupLevel2");
		startX = 30;
		startY = 200;
		player = new Player(startX, startY);
		cloud = new Cloud(50, 25);
		map.createChunk("g0", "backround.png", 0, 0, 1900, 850);
		map.createChunk("g1", "ground1.png", 0, 300, 300, 500);
		map.createChunk("g2", "Spike.png", 300, 665, 140, 100);
		map.createChunk("g3", "ground1.png", 440, 425, 400, 350);
		map.createChunk("g4", "Spike.png", 840, 665, 140, 100);
		map.createChunk("g5", "ground1.png", 980, 300, 300, 500);
		map.createEnemy(100, 250);
		map.createEnemy(600, 375);
		time = 30;
		timeLabel.setLabel(String.valueOf(time));
		drawGoalSpace();
		goalSpace.setLocation(1150, 300 - goalSpace.getHeight());
		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);


	}

	// boolean passedLevel() {
	// if (player.getImage().getBounds().intersects(goalSpace.getBounds())) {
	// System.out.println("win");
	// eTimer.stop();
	//// map.removeChunks(chunky);
	//// map.removeEnemies(map.getEnemies());
	// return true;
	// }
	// // System.out.println("lose");
	// return false;
	// }

	void passedLevel() {
		if (player.getImage().getBounds().intersects(goalSpace.getBounds())) {
			System.out.println("win");
			eTimer.stop();
			if (levelNum == 1) {
				hideContents();
				levelNum = 2;
				setupLevel2();
				showContents();
			}

		}
		// System.out.println("lose");

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
			rightMoveTimer.start();

			break;
		case KeyEvent.VK_LEFT:
			if (isPlayerOnEdge()) {
				break;
			}
			leftMoveTimer.start();

			break;
		case KeyEvent.VK_SPACE:
			jumpUpTimer.start();

			break;
		case KeyEvent.VK_P:
			System.out.println("in level: " + levelNum);
			pause = new PausePane(mainScreen, this);
			mainScreen.switchPause(pause);
			eTimer.stop();
			break;
		case KeyEvent.VK_T:
			System.out.println("P: " + player.getImage().getX());
			System.out.println("E: " + map.getEnemies().get(1).getImage().getX() + "\n");
			break;

		default:

		}

	}

	private int lastPCollision_Ref;
	private int lastECollision_Ref;

	public boolean isPlayerEnemyCollision() {

		for (Enemy e : map.getEnemies()) {

			if (player.bounds.intersects(e.getImage().getBounds())) {

				/* Test code>> */
//				//GRect temp = new GRect(player.bounds.getX(), player.bounds.getY(), player.bounds.getWidth(),
//						player.bounds.getHeight());
//				mainScreen.add(temp);
//
//				//GRect foo = new GRect(e.getImage().getX(), e.getImage().getY(), e.getImage().getWidth(),
//						e.getImage().getHeight());
//				foo.setColor(Color.red);
//				mainScreen.add(foo);
				// System.out.println("Collision Detected");
				/* <<Test code */

				return true;
			}

		}

		return false;
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

		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_RIGHT) {
			rightWalkFrictionTimer.start();
		}

		if (keyCode == KeyEvent.VK_LEFT) {
			leftWalkFrictionTimer.start();
		}

		rightMoveTimer.stop();
		leftMoveTimer.stop();
	}

	public boolean isPlayerOnEdge() {
		if (newPlayer.getX() <= -5) {
			return true;
		}
		return false;
	}

	void callEnemyCloudMovement() {
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

	public void goToOrigin() {
		player = new Player(startX, startY);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == eTimer) {
			callEnemyCloudMovement();
		}

		if (lives <= 0) {
			// goToOrigin();
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
		if (getTime() <= 0) {
			lives--;
			liveLabel.setLabel(String.valueOf(lives));
			time = 30;

		}

		if (isPlayerEnemyCollision()) {
			if (source == rightMoveTimer)
				rightMoveTimer.stop();

			if (source == leftMoveTimer)
				leftMoveTimer.stop();

			hitTimer.start();
//			lives--;
//			liveLabel.setLabel(String.valueOf(lives));
		}

		if (source == hitTimer) {
			if (hitImpact < 0) {
				hitImpact = 10;
				hitTimer.stop();
			}

			// Player hit from left side of enemy
			if (lastPCollision_Ref < lastECollision_Ref) {
				player.move(-hitImpact, 0);
			}

			// Player hit from right side of enemy
			if (lastPCollision_Ref > lastECollision_Ref) {
				player.move(hitImpact, 0);
			}

			hitImpact--;
		}

		if (source == rightWalkFrictionTimer) {
			player.move(walkFriction, 0);
			walkFriction--;

			if (walkFriction == 0) {
				walkFriction = 10;
				rightWalkFrictionTimer.stop();
			}

		}

		if (source == leftWalkFrictionTimer && player.getImage().getX() > 1) {
			player.move(-walkFriction, 0);
			walkFriction--;

			if (walkFriction == 0) {
				walkFriction = 10;
				leftWalkFrictionTimer.stop();
			}

		}

		if (!(isPlayerMoving())) {

			idleAnimationTimer_1.start();
		}

		if (source == idleAnimationTimer_1) {
			player.getImage().setVisible(false);
			player.getImage_2().setVisible(true);
			idleAnimationCount--;

			if (idleAnimationCount == 0) {
				idleAnimationCount = 100;
				idleAnimationTimer_1.stop();
				idleAnimationTimer_2.start();
			}
		}

		if (source == idleAnimationTimer_2) {
			player.getImage_2().setVisible(false);
			player.getImage().setVisible(true);
			idleAnimationCount--;

			if (idleAnimationCount == 0) {
				idleAnimationCount = 100;
				idleAnimationTimer_2.stop();
				idleAnimationTimer_1.start();
			}
		}
		passedLevel();

		// if(passedLevel() && levelNum ==1) {
		// hideContents();
		// levelNum = 2;
		// setupLevel2();
		// showContents();
		//
		// }

	}

	void setPauseToNull() {
		pause = null;
	}

	private boolean isPlayerMoving() {
		if (rightMoveTimer.isRunning() || leftMoveTimer.isRunning() || jumpUpTimer.isRunning()
				|| leftWalkFrictionTimer.isRunning() || rightWalkFrictionTimer.isRunning())
			return true;

		return false;
	}

}
