
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
import acm.graphics.GObject;
import acm.graphics.GRect;

/**
 * @author Team No Focus!
 * 
 *         Level class will generate the game world by integrating Map, Player,
 *         and Level
 * 
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

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
	private static final int PLAYER_DOWN_VELOCITY = 10;
	private static int jumpCounter = 0;
	private Timer jumpUpTimer = new Timer(20, this);
	private Timer downTimer = new Timer(20, this);
	private Timer leftMoveTimer = new Timer(20, this);
	private Timer rightMoveTimer = new Timer(20, this);

	// Walk friction after key released
	private Timer rightWalkFrictionTimer = new Timer(20, this);
	private Timer leftWalkFrictionTimer = new Timer(20, this);
	private double walkFriction = 10;

	// Enemy collision impact
	private Timer hitTimer = new Timer(20, this);
	private double hitImpact = 10;

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
		// newPlayer = player.getImage();
		// chunky = map.getChunks();
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

	public boolean checkGround() {
		if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1)
				.getChunkIMG()) {

		}
		return true;
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
		return;
	}

	public boolean isPlayerEnemyCollision() {

		for (Enemy e : map.getEnemies()) {

			if (player.bounds.intersects(e.getImage().getBounds())) {

				/* Test code>> */
				GRect temp = new GRect(player.bounds.getX(), player.bounds.getY(), player.bounds.getWidth(),
						player.bounds.getHeight());
				mainScreen.add(temp);

				GRect foo = new GRect(e.getImage().getX(), e.getImage().getY(), e.getImage().getWidth(),
						e.getImage().getHeight());
				foo.setColor(Color.red);
				mainScreen.add(foo);

				lastPCollision_Ref = temp.getBounds().getX() + temp.getBounds().getWidth() / 2;
				lastECollision_Ref = foo.getBounds().getX() + foo.getBounds().getWidth() / 2;

				System.out.println("Collision Detected");
				/* <<Test code */

				return true;
			}

		}

		return false;
	}

	private boolean isGround(GObject element) {
		for (Chunk c : chunky) {
			if (element == c.getChunkIMG() && c.getID() == 'g') {
				return true;
			}
		}
		return false;
	}

	public boolean isPlayerOnGround() {
		GObject obj = mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight() + 3);
		if (isGround(obj) && (jumpCounter > 1)) {
			System.out.println("JUMPING");
			System.out.println(map.getGroundChunks().size());
			return true;
		}

		return false;
	}

	public boolean isPlayerGoingOver() {
		boolean i = isGround(mainScreen.getElementAt(newPlayer.getX() + newPlayer.getWidth() / 3,
				newPlayer.getY() + newPlayer.getHeight() + 3));
		if (!i && jumpUpTimer.isRunning() == false) {
			System.out.println("FALLING");
			return true;
		}

		System.out.println("Grounded");
		return false;
	}
	/*
	 * for (int i = 0; i < map.getGroundChunks().size() ; i++) {
	 * 
	 * if (mainScreen.getElementAt(newPlayer.getX() + newPlayer.getWidth()/3,
	 * newPlayer.getY() + newPlayer.getHeight()) != map.getGroundChunks().get(i)
	 * .getChunkIMG() && jumpUpTimer.isRunning() == false ) {
	 * System.out.println("FALLING");
	 * 
	 * return true; } }
	 * 
	 */

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_RIGHT) {
			rightWalkFrictionTimer.start();
		}
	
		if (keyCode == KeyEvent.VK_LEFT){
		leftWalkFrictionTimer.start();
		rightMoveTimer.stop();
		leftMoveTimer.stop();
	}
		}

	public boolean isPlayerOnEdge() {
		if (newPlayer.getX() <= -5) {
			return true;
		}
		return false;
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

	public void setupLevel1() {
		player = new Player(50, 415);
		cloud = new Cloud(50, 25);
		map.createChunk('b', "background.png", 0, 0, 1900, 850);
		map.createChunk('g', "ground1.png", 0, 515, 650, 250);
		map.createChunk('s', "Spike.png", 650, 665, 140, 100);
		map.createChunk('g', "ground1.png", 790, 425, 650, 350);
		map.createEnemy(900, 375);
		map.createEnemy(150, 465);
		time = 30;
		lives = 3;
		map.sortGroundChunks();
		System.out.println("ffffsfs");
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
		map.createChunk('b', "backround.png", 0, 0, 1900, 850);
		map.createChunk('g', "ground1.png", 0, 300, 300, 500);
		map.createChunk('s', "Spike.png", 300, 665, 140, 100);
		map.createChunk('g', "ground1.png", 440, 425, 400, 350);
		map.createChunk('s', "Spike.png", 840, 665, 140, 100);
		map.createChunk('g', "ground1.png", 980, 300, 300, 500);
		map.createEnemy(100, 250);
		map.createEnemy(600, 375);
		time = 30;
		timeLabel.setLabel(String.valueOf(time));
		drawGoalSpace();
		goalSpace.setLocation(1150, 300 - goalSpace.getHeight());
		player.getImage().setBounds(player.getImage().getX(), player.getImage().getY(), 100, 100);

	}

	public void actionPerformed(ActionEvent e) {
		if (time == 0) {
			timeLabel = new GLabel("0", 200, 50);
			
		}
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
		//jumpCounter++;
		player.move(0, PLAYER_DOWN_VELOCITY);
		player.getImage().rotate(20);
	}
	

	if (isPlayerOnGround()) {
	    jumpUpTimer.stop();
	    jumpCounter = 0;
	}
	if (isPlayerGoingOver()) {
		downTimer.start();
		//System.out.println("Cliff");
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
	    // lives--;
	    // liveLabel.setLabel(String.valueOf(lives));
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	private boolean initAnimationState = false;
	private Timer respawnTimer = new Timer(20, this);

	/*
	 * 
	 * 
	 * 
	 * Hanna
	 * 
	 * 
	 * 
	 */

//    @Override
//    public void actionPerformed(ActionEvent e) {
//	Object source = e.getSource();
//
//	if (source == eTimer) {
//	    callEnemyCloudMovement();
//	    
//	if(lives <= 0) {
//	    //goToOrigin();
//	}
//
//	if (source == rightMoveTimer) {
//	    player.move(initSpeed, 0);
//	}
//
//	if (source == leftMoveTimer) {
//	    if (!isPlayerOnEdge()) {
//		player.move(-initSpeed, 0);
//	    }
//	}
//
//	if (source == jumpUpTimer) {
//	    jumpCounter++;
//	    player.move(0, PLAYER_UP_VELOCITY + jumpCounter);
//	}
//
//	if (isPlayerOnGround()) {
//	    jumpUpTimer.stop();
//	    jumpCounter = 0;
//	}
//
//
//	if (isPlayerEnemyCollision()) {
//	    if (source == rightMoveTimer)
//		rightMoveTimer.stop();
//
//	    if (source == leftMoveTimer)
//		leftMoveTimer.stop();
//
//	    hitTimer.start();
//	    //lives--;
//	    //liveLabel.setLabel(String.valueOf(lives));
//	}
//
//	if (source == respawnTimer) {
//	    if (player.getImage().getX() < -20) {
//		respawnPlayer();
//		respawnTimer.stop();
//	    }
//	}
//
//	if (source == hitTimer) {
//
//	    if (player.getImage().getX() < -20 || player.getImage().getX() > 1200) {
//		System.out.println("hit timer will stop");
//		respawnTimer.start();
//		hitImpact = 100;
//		hitTimer.stop();
//	    }
//
//	    // Player hit from left side of enemy
//	    if (lastPCollision_Ref < lastECollision_Ref) {
//		//player.runPlayerDeathSpinAnimation();
//		player.move(-hitImpact, -hitImpact);
//
//	    }
//
//	    // Player hit from right side of enemy
//	    if (lastPCollision_Ref > lastECollision_Ref) {
//		//player.runPlayerDeathSpinAnimation();
//		player.move(hitImpact, -hitImpact);
//	    }
//
//	    hitImpact--;
//	}
//
//	if (source == rightWalkFrictionTimer) {
//	    player.move(walkFriction, 0);
//	    walkFriction--;
//
//	    if (walkFriction == 0) {
//		walkFriction = 7;
//		rightWalkFrictionTimer.stop();
//	    }
//
//	}
//
//	if (source == leftWalkFrictionTimer && player.getImage().getX() > 1) {
//	    player.move(-walkFriction, 0);
//	    walkFriction--;
//
//	    if (walkFriction == 0) {
//		walkFriction = 7;
//		leftWalkFrictionTimer.stop();
//	    }
//
//	}
//
//	if (!(isPlayerMoving())) {
//
//	    if (initAnimationState == false) {
//		initAnimationState = true;
//		idleAnimationTimer_1.start();
//	    }
//
//	    runIdleAnimation(source);
//	}
//
//	if (lives == 0) {
//	    // mainScreen.removeAll();
//	}
//
//    }
//    }

	/*
	 * 
	 * 
	 * 
	 * Hanna
	 * 
	 * 
	 * 
	 * 
	 */

//    private void runIdleAnimation(Object source) {
//	if (source == idleAnimationTimer_1) {
//	    if (idleAnimationCount == 0) {
//		idleAnimationCount = 20;
//		idleAnimationTimer_2.start();
//		idleAnimationTimer_1.stop();
//	    }
//
//	    player.getImage().setVisible(false);
//	    player.getImage_2().setVisible(true);
//	    idleAnimationCount--;
//	}
//
//	if (source == idleAnimationTimer_2) {
//
////	    if (idleAnimationTimer_1.isRunning()) {
////		idleAnimationTimer_1.stop();
////
////	    }
//
//	    if (idleAnimationCount == 0) {
//		idleAnimationCount = 20;
//		idleAnimationTimer_1.start();
//		idleAnimationTimer_2.stop();
//
//	    }
//
//	    player.getImage_2().setVisible(false);
//	    player.getImage().setVisible(true);
//	    idleAnimationCount--;
//
//	}
//
//    }

	public void respawnPlayer() {

		player.getImage().setLocation(50, -100);
		player.getImage_2().setLocation(50, -100);
		player.updatePlayerPos();

		jumpUpTimer.start();

	}

	private boolean isPlayerMoving() {
		if (rightMoveTimer.isRunning() || leftMoveTimer.isRunning() || jumpUpTimer.isRunning()
				|| leftWalkFrictionTimer.isRunning() || rightWalkFrictionTimer.isRunning())
			return true;

		return false;
	}

}
