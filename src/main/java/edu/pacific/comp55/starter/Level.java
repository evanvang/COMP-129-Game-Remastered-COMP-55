	
package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;

/**
 * @author Team No Focus!
 *
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level
 *
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

	private boolean hasCollided = false;
	private List<Enemy> deadEnemies;

	private boolean initAnimationState = false;
	private Timer respawnTimer = new Timer(20, this);

	private double lastPCollision_Ref;
	private double lastECollision_Ref;

	private Timer hitBufferTimer = new Timer(20, this);
	private int hitBufferCount = 100;

	// Animation
	private Timer idleAnimationTimer_1 = new Timer(20, this);
	private Timer idleAnimationTimer_2 = new Timer(20, this);
	private int idleAnimationCount = 20;

	// Player movement
	private static final int PLAYER_UP_VELOCITY = -20;
	private static final int PLAYER_DOWN_VELOCITY = 5;
	private static int jumpCounter = 0;
	private Timer jumpUpTimer = new Timer(20, this);
	private Timer downTimer = new Timer(25, this);
	private Timer leftMoveTimer = new Timer(20, this);
	private Timer rightMoveTimer = new Timer(20, this);

	// Walk friction after key released
	private Timer rightWalkFrictionTimer = new Timer(20, this);
	private Timer leftWalkFrictionTimer = new Timer(20, this);
	private double walkFriction = 10;

	// Enemy collision impact
	private Timer hitTimer = new Timer(20, this);
	private double hitImpact = 100;

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

	public double initSpeed = 10;
	private GImage newPlayer;
	private ArrayList<Chunk> chunky;
	private GImage goalSpace;
	private int levelNum;
	private int lives;
	private PausePane pause;
	private GameOver lose;

	// Constructor
	public Level(MainApplication program, int levelNum) {
		deadEnemies = new LinkedList<>();
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
	}

	public void setLives(int live) {
		lives = live;
	}

	public GLabel getTimeLabel() {
		mainScreen.setupInteractions();
		newPlayer = player.getImage();
		chunky = map.getChunks();
		return timeLabel;
	}

	public void showDetails() {
		mainScreen.add(timeLabel);
		mainScreen.add(liveIMG);
		mainScreen.add(liveLabel);
		mainScreen.add(clockIMG);
		mainScreen.add(goalSpace);
	}

	public void setTimeLabel(GLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public void showContents() {
		if (levelNum == 1) {

			addChunks();
			mainScreen.add(player.getImage());
			mainScreen.add(player.getImage_2());
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(map.getEnemies().get(1).getImage());

		} else {

			addChunks();
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(map.getEnemies().get(1).getImage());
			mainScreen.add(player.getImage());
			mainScreen.add(player.getImage_2());
		}
		newPlayer = player.getImage();
		chunky = map.getChunks();
		showDetails();
		eTimer.start();
	}

	public int getLevelNum() {
		return levelNum;
	}

	public boolean checkGround() {
		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1)
				.getChunkIMG()) {

			return true;
		}
		return false;
	}

	public void hideContents() {
		mainScreen.removeAll();
		map.removeChunks(chunky);
		map.removeEnemies(map.getEnemies());
	}

	void passedLevel() {
		if (player.getImage().getBounds().intersects(goalSpace.getBounds())) {
			stopAllTimers();
			if (levelNum == 1) {
				hideContents();
				levelNum = 2;
				setupLevel2();
				showContents();
				deadEnemies.clear();
			}
			else if (levelNum == 2) {
				mainScreen.switchToWinScreen();
			}
		}
	}

	void GameOverCheck() {
		if (lives == 0) {
			stopAllTimers();
			lose = new GameOver(mainScreen, this);
			mainScreen.switchLose(lose);
		}
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
			stopAllTimers();
			break;
		case KeyEvent.VK_T:
			System.out.println("P: " + player.getImage().getX());
			System.out.println("E: " + map.getEnemies().get(1).getImage().getX() + "\n");
			break;

		default:

		}
		return;
	}

	public Enemy collideWithEnemy() {
		for (Enemy enemy : map.getEnemies()) {
			if (player.getImage().getBounds().intersects(enemy.getImage().getBounds())) {
				return enemy;
			}
		}
		return null;
	}

	public boolean isPlayerEnemyCollision() {
		for (Enemy enemy : map.getEnemies()) {
			if (player.getImage().getBounds().intersects(enemy.getImage().getBounds())) {
				return true;
			}
		}

		return false;
	}

	private boolean isSpike(GObject spike) {
		for (Chunk c : chunky) {
			if (spike == c.getspikeIMG() && c.getID() == 's') {
				return true;
			}
		}
		return false;
	}

	public boolean isPlayerOnSpike() {
		GObject obj = mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight() + 3);
		if (isSpike(obj)) {
			System.out.println("SPIKED");
			return true;
		}

		return false;
	}

	private boolean isGround(GObject ground) {
		for (Chunk c : chunky) {
			if (ground == c.getChunkIMG() && c.getID() == 'g') {
				return true;
			}
		}
		return false;
	}
	

	public boolean isPlayerOnGround() {
		GObject obj = mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight() + 3);
		if (isGround(obj) && (jumpCounter > 1)) {
			System.out.println("JUMPING");
			// System.out.println(map.getGroundChunks().size());
			return true;
		}

		return false;
	}

	public boolean isPlayerGoingOver() {
		boolean i = isGround(mainScreen.getElementAt(newPlayer.getX() + newPlayer.getWidth() / 4,
				newPlayer.getY() + newPlayer.getHeight() + 3));
		if (!i && jumpUpTimer.isRunning() == false) {
			System.out.println("FALLING");
			return true;
		}

		// System.out.println("Grounded");
		return false;
	}

	public boolean isPlayerOnEdge() {
		if (newPlayer.getX() <= -5) {
			return true;
		}
		return false;
	}

	public boolean isPlayerClipping() {
		if (newPlayer.getX() + newPlayer.getWidth() == 10000) {
			return true;
		}

		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_RIGHT) {
			rightWalkFrictionTimer.start();
			rightMoveTimer.stop();
			leftMoveTimer.stop();
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			leftWalkFrictionTimer.start();
			rightMoveTimer.stop();
			leftMoveTimer.stop();
		}
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

	public void addChunks() {
		for (int i = 0; i < map.getChunks().size(); i++) {
			if (map.getChunks().get(i).getID() == 'b') {
				mainScreen.add(map.getChunks().get(i).getbackgroundIMG());
			}
			if (map.getChunks().get(i).getID() == 'g') {
				mainScreen.add(map.getChunks().get(i).getChunkIMG());
			}
			if (map.getChunks().get(i).getID() == 's') {
				mainScreen.add(map.getChunks().get(i).getspikeIMG());
			}
		}
	}

	public void setupLevel1() {
		player = new Player(50, 415);
		cloud = new Cloud(50, 25);
		map.createChunk('b', "background.png", 0, 0, 1900, 850);
		map.createChunk('g', "ground1.png", 0, 515, 100, 100);
		map.createChunk('s', "Spike.png", 550, 725, 100, 100);
		map.createChunk('s', "Spike.png", 650, 725, 100, 100);
		map.createChunk('s', "Spike.png", 750, 725, 100, 100);
		map.createChunk('s', "Spike.png", 850, 725, 100, 100);
		map.createChunk('g', "ground1.png", 100, 515, 100, 100);
		map.createChunk('g', "ground1.png", 200, 515, 100, 100);
		map.createChunk('g', "ground1.png", 300, 515, 100, 100);
		map.createChunk('g', "ground1.png", 400, 515, 100, 100);
		map.createChunk('g', "ground4.png", 500, 515, 100, 100);
		map.createChunk('g', "ground8.png", 500, 615, 100, 100);
		map.createChunk('g', "ground9.png", 500, 715, 100, 100);
		map.createChunk('g', "ground8.png", 500, 815, 100, 100);
		map.createChunk('g', "Ground10.png", 0, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 100, 615, 100, 100);
		map.createChunk('g', "Ground11.png", 200, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 300, 615, 100, 100);
		map.createChunk('g', "Ground12.png", 400, 615, 100, 100);
		map.createChunk('g', "Ground11.png", 0, 715, 100, 100);
		map.createChunk('g', "Ground12.png", 100, 715, 100, 100);
		map.createChunk('g', "Ground10.png", 200, 715, 100, 100);
		map.createChunk('g', "Ground12.png", 300, 715, 100, 100);
		map.createChunk('g', "Ground11.png", 400, 715, 100, 100);
		map.createChunk('g', "Ground14.png", 650, 480, 100, 50);
		map.createChunk('g', "Ground15.png", 750, 480, 100, 50);
		map.createChunk('g', "Ground6.png", 900, 715, 100, 100);
		map.createChunk('g', "Ground7.png", 900, 615, 100, 100);
		map.createChunk('g', "Ground6.png", 900, 515, 100, 100);
		map.createChunk('g', "Ground5.png", 900, 415, 100, 100);
		map.createChunk('g', "Ground1.png", 1000, 415, 100, 100);
		map.createChunk('g', "Ground11.png", 1000, 515, 100, 100);
		map.createChunk('g', "Ground10.png", 1000, 615, 100, 100);
		map.createChunk('g', "Ground2.png", 1100, 415, 100, 100);
		map.createChunk('g', "Ground11.png", 1100, 515, 100, 100);
		map.createChunk('g', "Ground11.png", 1000, 715, 100, 100);
		map.createChunk('g', "Ground10.png", 1100, 515, 100, 100);
		map.createChunk('g', "Ground11.png", 1100, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 1100, 715, 100, 100);
		map.createChunk('g', "Ground3.png", 1200, 415, 100, 100);
		map.createChunk('g', "Ground10.png", 1200, 515, 100, 100);
		map.createChunk('g', "Ground10.png", 1200, 615, 100, 100);
		map.createChunk('g', "Ground11.png", 1200, 715, 100, 100);
		map.createChunk('g', "Ground12.png", 1300, 515, 100, 100);
		map.createChunk('g', "Ground10.png", 1300, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 1300, 715, 100, 100);
		map.createChunk('g', "Ground10.png", 1400, 515, 100, 100);
		map.createChunk('g', "Ground2.png", 1300, 415, 100, 100);
		map.createChunk('g', "Ground3.png", 1400, 415, 100, 100);
		map.createChunk('g', "Ground11.png", 1400, 515, 100, 100);
		map.createChunk('g', "Ground11.png", 1400, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 1400, 715, 100, 100);
		map.createChunk('g', "Ground1.png", 1500, 415, 100, 100);
		map.createChunk('g', "Ground12.png", 1500, 515, 100, 100);
		map.createChunk('g', "Ground12.png", 1500, 615, 100, 100);
		map.createChunk('g', "Ground10.png", 1500, 715, 100, 100);

		map.createEnemy(400, 470);
		map.createEnemy(900, 370);
		
		time = 30;
		lives = 3;
		drawGoalSpace();
		goalSpace.setLocation(1300, 420 - goalSpace.getHeight());

		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);
	}

	public void setupLevel2() {
		System.out.println("setupLevel2");
		startX = 0;
		startY = 200;
		player = new Player(startX, startY);
		cloud = new Cloud(50, 25);
		map.createChunk('b', "background.png", 0, 0, 1900, 850);
		map.createChunk('g', "ground1.png", 0, 300, 300, 500);
		map.createChunk('s', "Spike.png", 300, 665, 140, 100);
		map.createChunk('g', "ground1.png", 440, 425, 400, 350);
		map.createChunk('s', "Spike.png", 840, 665, 140, 100);
		map.createChunk('g', "ground1.png", 980, 300, 300, 500);
		map.createEnemy(150, 250);
		map.createEnemy(600, 375);
		time = 30;
		lives = 3;
		timeLabel.setLabel(String.valueOf(time));
		drawGoalSpace();
		goalSpace.setLocation(1150, 300 - goalSpace.getHeight());
		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);

	}

	private void decrementLive() {
		lives--;
		liveLabel.setLabel(String.valueOf(lives));
	}

	public void actionPerformed(ActionEvent e) {
		GameOverCheck();
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
		if (source == downTimer) {
			// jumpCounter++;
			player.move(0, PLAYER_DOWN_VELOCITY);
			// player.getImage().rotate(10);
		}

		if (isPlayerOnGround()) {
			downTimer.stop();
			jumpUpTimer.stop();
			jumpCounter = 0;
		}
		if (isPlayerGoingOver()) {
			downTimer.start();
		}

		if (isPlayerOnSpike()) {
			respawnPlayer();
			decrementLive();

			for (Enemy enemy : deadEnemies) {
				mainScreen.add(enemy.getImage());
				map.getEnemies().add(enemy);
			}
			deadEnemies.clear();
		}

		if (time == 0) {
			time = 31;
			decrementLive();
		}

		Enemy collideEnemy = collideWithEnemy();
		if (collideEnemy == null) {
			hasCollided = false;

		} else {
			if (!hasCollided) {
				hasCollided = true;
				double playerHeight = player.getImage().getHeight();
				double enemyHeight = collideEnemy.getImage().getHeight();
				double deltaHeight = Math.abs(playerHeight - enemyHeight);

				GRectangle intersectionRect = player.getImage().getBounds()
						.intersection(collideEnemy.getImage().getBounds());
				double intersectionHeight = intersectionRect.getHeight();

				if (intersectionHeight < deltaHeight) {
					mainScreen.remove(collideEnemy.getImage());
					map.getEnemies().remove(collideEnemy);
					deadEnemies.add(collideEnemy);

				} else {
					decrementLive();
					respawnPlayer();
				}
			}

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
				walkFriction = 5;
				rightWalkFrictionTimer.stop();
			}

		}

		if (source == leftWalkFrictionTimer && player.getImage().getX() > 1) {
			player.move(-walkFriction, 0);
			walkFriction--;

			if (walkFriction == 0) {
				walkFriction = 5;
				leftWalkFrictionTimer.stop();
			}

		}

		if (!(isPlayerMoving())) {
			idleAnimationTimer_1.start();
			runIdleAnimation(source);
		}
		passedLevel();
	}

	void setPauseToNull() {
		pause = null;
	}

	void setGameOverToNull() {
		lose = null;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	void callEnemyCloudMovement() {
		count++;
		for (Enemy ene : map.getEnemies()) {
			ene.getImage().move(enemyVel, 0);
			if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 150
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

	void stopAllTimers() {
		eTimer.stop();
		rightMoveTimer.stop();
		leftMoveTimer.stop();
		jumpUpTimer.stop();
		downTimer.stop();
		hitTimer.stop();
		rightWalkFrictionTimer.stop();
		leftWalkFrictionTimer.stop();
		idleAnimationTimer_1.stop();
		idleAnimationTimer_2.stop();
	}

	private void runIdleAnimation(Object source) {
		if (source == idleAnimationTimer_1) {
			if (idleAnimationCount == 0) {
				idleAnimationCount = 20;
				idleAnimationTimer_2.start();

			}

			player.getImage().setVisible(false);
			player.getImage_2().setVisible(true);
			idleAnimationCount--;
		}

		if (source == idleAnimationTimer_2) {

			if (idleAnimationTimer_1.isRunning()) {
				idleAnimationTimer_1.stop();
			}

			if (idleAnimationCount == 0) {
				idleAnimationCount = 20;
				idleAnimationTimer_1.start();
				idleAnimationTimer_2.stop();

			}

			player.getImage_2().setVisible(false);
			player.getImage().setVisible(true);
			idleAnimationCount--;

		}

	}

	public void respawnPlayer() {

		player.getImage().setLocation(0, -100);
		player.getImage_2().setLocation(0, -100);
		player.updatePlayerPos();

		jumpUpTimer.start();

	}

	private boolean isPlayerMoving() {
		if (rightMoveTimer.isRunning() || leftMoveTimer.isRunning() || jumpUpTimer.isRunning()
				|| leftWalkFrictionTimer.isRunning() || rightWalkFrictionTimer.isRunning()) {
			return true;
		}

		return false;
	}

}
