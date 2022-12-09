	
package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import javafx.scene.transform.Rotate;

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
	private static final int PLAYER_UP_VELOCITY = -18;
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
	private double shurikenVel = 7;
	private int time;
	private int startX;
	private int startY;
	private GLabel timeLabel;
	private GLabel liveLabel;
	private int count = 0;
	private GImage liveIMG;
	private GImage clockIMG;
	private boolean timerActivated = false;
	public double initSpeed = 10;
	private GImage newPlayer;
	private ArrayList<Chunk> chunky;
	private GImage goalSpace;
	private int levelNum;
	private int downVelocity = 8;
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
		else if (levelNum == 3) {
			System.out.println("retry");
			setupLevel3();
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

		} else if (levelNum == 2){

			addChunks();
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(player.getImage());
			mainScreen.add(player.getImage_2());
			
		}
		else {
			addChunks();
			mainScreen.add(map.getEnemies().get(0).getImage());
			mainScreen.add(map.getEnemies().get(1).getImage());
			mainScreen.add(map.getEnemies().get(2).getImage());
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
				hideContents();
				levelNum = 3;
				setupLevel3();
				showContents();
				deadEnemies.clear();
			}
			else if (levelNum == 3) {
				mainScreen.switchToWinScreen();
				deadEnemies.clear();
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
			player.getImage().setVisible(true);
			player.getImage_2().setVisible(false);
			
			break;
		case KeyEvent.VK_LEFT:
			if (isPlayerOnEdge()) {
				break;
			}
			player.getImage().setVisible(true);
			player.getImage_2().setVisible(false);
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
	
	/*private boolean isTouchWall() {
		for (int i = 0; i < map.getChunks().size(); i++) {
			if ((((newPlayer.getX() + initSpeed >= map.getChunks().get(i).getRight() - 50 || newPlayer.getX() >= map.getChunks().get(i).getRight() + 20) && (newPlayer.getY() == map.getChunks().get(i).getUp()))) {
				return true;
			}
		}
		return false;
	}*/
	

	public boolean isPlayerOnGround() {
		GObject obj = mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight() + 3);
		if (isGround(obj) && (jumpCounter > 1)) {
			System.out.println("JUMPING");
			// System.out.println(map.getGroundChunks().size());
			return true;
		}
		else if (isGround(obj) && downTimer.isRunning()) {
			return true;
		}

		return false;
	}

	public boolean isPlayerGoingOver() {
		boolean i = isGround(mainScreen.getElementAt((newPlayer.getX() + newPlayer.getWidth() / 4),
				newPlayer.getY() + newPlayer.getHeight() + PLAYER_DOWN_VELOCITY));
		if (!i && jumpUpTimer.isRunning() == false && downTimer.isRunning() == false) {
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
		map.createChunk('s', "upspike.png", 550, 785, 100, 22);
		map.createChunk('s', "upspike.png", 650, 785, 100, 22);
		map.createChunk('s', "upspike.png", 750, 785, 100, 22);
		map.createChunk('s', "upspike.png", 850, 785, 100, 22);
		map.createChunk('s', "rightspike.png", 600, 700, 22, 100);
		map.createChunk('s', "rightspike.png", 600, 600, 22, 100);
		map.createChunk('s', "leftspike.png", 878, 500, 22, 100);
		map.createChunk('s', "leftspike.png", 878, 600, 22, 100);
		map.createChunk('s', "leftspike.png", 878, 700, 22, 100);
		map.createChunk('s', "leftspike.png", 878, 800, 22, 100);
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

		map.createEnemy("pumpkin joe.png", 400, 470);
		map.createEnemy("pumpkin joe.png", 900, 370);
		
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
		
		map.createChunk('g', "ground1.png", 0, 300, 100, 100);
		map.createChunk('g', "ground2.png", 100, 300, 100, 100);
		map.createChunk('g', "ground4.png", 200, 300, 100, 100);
		map.createChunk('g', "ground8.png", 200, 400, 100, 100);
		map.createChunk('g', "ground9.png", 200, 500, 100, 100);
		map.createChunk('g', "ground8.png", 200, 600, 100, 100);
		map.createChunk('g', "ground8.png", 200, 700, 100, 100);
		map.createChunk('g', "ground8.png", 200, 800, 100, 100);
		map.createChunk('g', "ground11.png", 0, 400, 100, 100);
		map.createChunk('g', "ground12.png", 100, 400, 100, 100);
		map.createChunk('g', "ground10.png", 0, 500, 100, 100);
		map.createChunk('g', "ground11.png", 100, 500, 100, 100);
		map.createChunk('g', "ground10.png", 0, 600, 100, 100);
		map.createChunk('g', "ground12.png", 100, 600, 100, 100);
		map.createChunk('g', "ground10.png", 0, 700, 100, 100);
		map.createChunk('g', "ground11.png", 100, 700, 100, 100);
		map.createChunk('g', "ground12.png", 0, 800, 100, 100);
		map.createChunk('g', "ground10.png", 100, 800, 100, 100);
		
		map.createChunk('s', "rightspike.png", 300, 400, 22, 100);
		map.createChunk('s', "rightspike.png", 300, 500, 22, 100);
		map.createChunk('s', "rightspike.png", 300, 600, 22, 100);
		map.createChunk('s', "rightspike.png", 300, 700, 22, 100);
		map.createChunk('s', "rightspike.png", 300, 800, 22, 100);
		
		map.createChunk('s', "leftspike.png", 503, 0, 22, 100);
		map.createChunk('s', "leftspike.png", 503, 100, 22, 100);
		map.createChunk('s', "leftspike.png", 503, 200, 22, 100);
		map.createChunk('s', "leftspike.png", 503, 300, 22, 100);
		map.createChunk('s', "leftspike.png", 503, 400, 22, 100);
		
		map.createChunk('g', "ground7.png", 525, 0, 100, 100);
		map.createChunk('g', "ground6.png", 525, 100, 100, 100);
		map.createChunk('g', "ground6.png", 525, 200, 100, 100);
		map.createChunk('g', "ground7.png", 525, 300, 100, 100);
		map.createChunk('g', "ground16.png", 525, 400, 100, 100);
		map.createChunk('g', "ground18.png", 625, 400, 100, 100);
		map.createChunk('g', "ground18.png", 725, 400, 100, 100);
		map.createChunk('g', "ground20.png", 625, 300, 100, 100);
		map.createChunk('g', "ground9.png", 625, 200, 100, 100);
		map.createChunk('g', "ground8.png", 625, 100, 100, 100);
		map.createChunk('g', "ground9.png", 625, 0, 100, 100);
		map.createChunk('g', "ground1.png", 725, 300, 100, 100);
		map.createChunk('g', "ground4.png", 825, 300, 100, 100);
		map.createChunk('g', "ground17.png", 825, 400, 100, 100);
		
		map.createChunk('s', "downspike.png", 525, 500, 100, 22);
		map.createChunk('s', "downspike.png", 625, 500, 100, 22);
		map.createChunk('s', "downspike.png", 725, 500, 100, 22);
		map.createChunk('s', "downspike.png", 825, 500, 100, 22);
		map.createChunk('s', "rightspike.png", 925, 300, 22, 100);
		map.createChunk('s', "rightspike.png", 925, 400, 22, 100);
		
		map.createChunk('s', "leftspike.png", 678, 750, 22, 100);
		map.createChunk('g', "ground5.png", 700, 750, 100, 100);
		map.createChunk('g', "ground1.png", 800, 750, 100, 100);
		map.createChunk('g', "ground1.png", 900, 750, 100, 100);
		map.createChunk('g', "ground1.png", 1000, 750, 100, 100);
		map.createChunk('g', "ground2.png", 1100, 750, 100, 100);
		map.createChunk('g', "ground1.png", 1200, 750, 100, 100);
		map.createChunk('g', "ground2.png", 1300, 750, 100, 100);
		map.createChunk('g', "ground1.png", 1400, 750, 100, 100);
		map.createChunk('g', "ground21.png", 1500, 750, 100, 100);
		map.createChunk('g', "ground10.png", 0, 850, 100, 100);
		map.createChunk('g', "ground10.png", 100, 850, 100, 100);
		map.createChunk('g', "ground10.png", 200, 850, 100, 100);
		map.createChunk('g', "ground5.png", 700, 850, 100, 100);
		map.createChunk('g', "ground10.png", 800, 850, 100, 100);
		map.createChunk('g', "ground10.png", 900, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1000, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1100, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1200, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1300, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1400, 850, 100, 100);
		map.createChunk('g', "ground10.png", 1500, 850, 100, 100);
		
		map.createChunk('g', "ground7.png", 1500, 650, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 550, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 450, 100, 100);
		map.createChunk('g', "ground7.png", 1500, 350, 100, 100);
		map.createChunk('g', "ground7.png", 1500, 250, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 150, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 50, 100, 100);
		map.createChunk('g', "ground7.png", 1500, -50, 100, 100);
		
		map.createChunk('g', "ground15.png", 1200, 625, 100, 50);
		map.createChunk('g', "ground14.png", 1100, 625, 100, 50);
		
		map.createChunk('g', "ground15.png", 1350, 500, 100, 50);
		map.createChunk('g', "ground14.png", 1250, 500, 100, 50);
		
		map.createChunk('g', "ground15.png", 1100, 390, 100, 50);
		map.createChunk('g', "ground14.png", 1000, 390, 100, 50);
		
		
		
		
		map.createChunk('s', "leftspike.png", 1478, -50, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 50, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 150, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 250, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 350, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 450, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 550, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 650, 22, 100);
		
		map.createEnemy("pumpkin joe.png", 1000, 700);
		time = 30;
		lives = 3;
		timeLabel.setLabel(String.valueOf(time));
		drawGoalSpace();
		goalSpace.setLocation(800, 300 - goalSpace.getHeight());
		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);

	}
	
	public void setupLevel3() {
		System.out.println("setupLevel3");
		startX = 0;
		startY = 550;
		player = new Player(startX, startY);
		cloud = new Cloud(50, 25);
		map.createChunk('b', "background.png", 0, 0, 1900, 850);
		map.createChunk('g', "ground2.png", 0, 700, 100, 100);
		map.createChunk('g', "ground1.png", 100, 700, 100, 100);
		map.createChunk('g', "ground1.png", 200, 700, 100, 100);
		map.createChunk('g', "ground2.png", 300, 700, 100, 100);
		map.createChunk('g', "ground1.png", 400, 700, 100, 100);
		map.createChunk('g', "ground1.png", 500, 700, 100, 100);
		map.createChunk('g', "ground2.png", 600, 700, 100, 100);
		map.createChunk('g', "ground2.png", 700, 700, 100, 100);
		map.createChunk('g', "ground1.png", 800, 700, 100, 100);
		map.createChunk('g', "ground2.png", 900, 700, 100, 100);
		map.createChunk('g', "ground1.png", 1000, 700, 100, 100);
		map.createChunk('g', "ground2.png", 1100, 700, 100, 100);
		map.createChunk('g', "ground2.png", 1200, 700, 100, 100);
		map.createChunk('g', "ground1.png", 1300, 700, 100, 100);
		map.createChunk('g', "ground1.png", 1400, 700, 100, 100);
		map.createChunk('g', "ground21.png", 1500, 700, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 600, 100, 100);
		map.createChunk('g', "ground7.png", 1500, 500, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 400, 100, 100);
		map.createChunk('g', "ground6.png", 1500, 300, 100, 100);
		map.createChunk('g', "ground22.png", 1500, 200, 100, 100);
		
		map.createChunk('g', "ground18.png", 1400, 200, 100, 100);
		map.createChunk('g', "ground19.png", 1300, 200, 100, 100);
		map.createChunk('g', "ground16.png", 1200, 200, 100, 100);
		map.createChunk('g', "ground5.png", 1200, 100, 100, 100);
		map.createChunk('g', "ground2.png", 1300, 100, 100, 100);
		map.createChunk('g', "ground1.png", 1400, 100, 100, 100);
		map.createChunk('g', "ground1.png", 1500, 100, 100, 100);
		
		map.createChunk('g', "ground15.png", 1200, 460, 100, 50);
		map.createChunk('g', "ground14.png", 1100, 460, 100, 50);
		
		map.createChunk('g', "ground15.png", 825, 360, 100, 50);
		map.createChunk('g', "ground13.png", 725, 360, 100, 50);
		map.createChunk('g', "ground14.png", 625, 360, 100, 50);
		
		map.createChunk('g', "ground15.png", 950, 575, 100, 50);
		map.createChunk('g', "ground14.png", 850, 575, 100, 50);
		
		map.createChunk('g', "ground14.png", 200, 475, 100, 50);
		map.createChunk('g', "ground15.png", 300, 475, 100, 50);
		
		map.createChunk('s', "leftspike.png", 1478, 600, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 500, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 400, 22, 100);
		map.createChunk('s', "leftspike.png", 1478, 300, 22, 100);
		
		map.createChunk('g', "ground4.png", 425, 100, 100, 100);
		map.createChunk('g', "ground5.png", 325, 100, 100, 100);
		map.createChunk('g', "ground16.png", 325, 200, 100, 100);
		map.createChunk('g', "ground17.png", 425, 200, 100, 100);
		
		map.createChunk('s', "leftspike.png", 303, 200, 22, 100);
		map.createChunk('s', "leftspike.png", 303, 100, 22, 100);
		map.createChunk('s', "downspike.png", 325, 300, 100, 22);
		map.createChunk('s', "downspike.png", 425, 300, 100, 22);
		map.createChunk('s', "rightspike.png", 522, 200, 22, 100);
		map.createChunk('s', "rightspike.png", 522, 100, 22, 100);
		
		map.createChunk('g', "ladderend.png", 125, 100, 100, 100);
		map.createChunk('g', "ladder.png", 125, 200, 100, 100);
		map.createChunk('g', "ladderstart.png", 125, 300, 100, 100);
		
		map.createChunk('g', "ground14.png", 600, 120, 100, 50);
		map.createChunk('g', "ground13.png", 700, 120, 100, 50);
		map.createChunk('g', "ground13.png", 800, 120, 100, 50);
		map.createChunk('g', "ground13.png", 900, 120, 100, 50);
		map.createChunk('g', "ground15.png", 1000, 120, 100, 50);
		
		map.createEnemy("pumpkin joe.png", 325, 50);
		map.createEnemy("shuriken.png", 600, 290);
		map.createEnemy("shuriken.png", 550, 40);
		time = 30;
		lives = 3;
		timeLabel.setLabel(String.valueOf(time));
		drawGoalSpace();
		goalSpace.setLocation(1250, 100 - goalSpace.getHeight());
		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);

	}

	private void decrementLive() {
		lives--;
		liveLabel.setLabel(String.valueOf(lives));
		time = 31;
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
			downVelocity = 4;
		}
		if (source == downTimer) {
			// jumpCounter++;
			downVelocity += 1.25;
			player.move(0, downVelocity);
			// player.getImage().rotate(10);
		}

		if (isPlayerOnGround()) {
			downVelocity = 5;
			downTimer.stop();
			timerActivated = false;
			jumpUpTimer.stop();
			jumpCounter = 0;
		}
		if (isPlayerGoingOver()) {
			if (!timerActivated) {
				System.out.println("WENT OVER");
				timerActivated = true;
				downTimer.start();
			}
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
		if (newPlayer.getY() > 950) {
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

				if (intersectionHeight < deltaHeight && collideEnemy.getPath() == "pumpkin joe.png") {
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
			if (ene.getPath() == "pumpkin joe.png") {
				ene.getImage().move(enemyVel, 0);
				if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 150
					|| ene.getImage().getX() <= ene.getStartX()) {
					enemyVel *= -1;
					ene.getImage().move(enemyVel, 0);
				}
			}
			else if (ene.getPath() == "shuriken.png") {
				ene.getImage().move(shurikenVel, 0);
				if (ene.getImage().getX() + ene.getImage().getWidth() >= ene.getStartX() + 500
					|| ene.getImage().getX() <= ene.getStartX()) {
					shurikenVel *= -1;
					ene.getImage().move(shurikenVel, 0);
				}
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
		downVelocity = 8;
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
