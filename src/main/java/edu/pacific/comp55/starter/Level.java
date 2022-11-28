package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GLabel;
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

    private static final int PLAYER_DN_VELOCITY = -5;
    private static final int PLAYER_UP_VELOCITY = 50;
    private final int jumpHeight = 70;
    private int jumpStep = 0;
    private Timer jumpUpTimer = new Timer(2, this);
    private Timer jumpDnTimer = new Timer(2, this);

    private final double PLAYER_WALK_VELOCITY = 10;

    public double initSpeed = 0;

    /* List to store keys that are pressed, stores no duplicates */
    private Set<Integer> keyList = new HashSet<>();

    private MainApplication mainScreen;
    private Map map;
    private Player player;
    private Map ground;
    private Timer timer;
    private Timer eTimer = new Timer(50, this);
    private Cloud cloud;
    private double enemyVel = 3;
    private int time = 30;
    private GLabel timeLabel;
    private GLabel liveLabel;
    private int count = 0;
    private GImage liveIMG;
    private GImage clockIMG;

    private GImage newPlayer;
    private ArrayList<Chunk> chunky;

    private Timer playerMoveTimer = new Timer(2, this);

    // Constructor
    public Level(MainApplication program, int levelNum) {

	mainScreen = program;
	map = new Map();
	drawTimeLabel();
	drawLiveLabel();
	if (levelNum == 1) {
	    setupLevel1();
	}
	newPlayer = player.getplayerIMG();
	chunky = map.getChunks();
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
	startEnemyTimer();
    }

    public void hideContents() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

	int keyCode = e.getKeyCode();

	/*
	 * Player key movements
	 */
	switch (keyCode) {

	case KeyEvent.VK_RIGHT:
	    keyList.add(KeyEvent.VK_RIGHT);

	    player.setMoveState(MoveState.RIGHT);
	    playerMoveTimer.start();
	    break;

	case KeyEvent.VK_LEFT:
	    keyList.add(KeyEvent.VK_LEFT);

	    player.setMoveState(MoveState.LEFT);
	    playerMoveTimer.start();
	    break;

	case KeyEvent.VK_SPACE:
	    keyList.add(KeyEvent.VK_SPACE);

	    player.setJumping(true);
	    jumpStep = 0;
	    player.setMoveState(MoveState.SPACE);
	    jumpUpTimer.start();
	    break;

	default:
	    break;
	}
    }

    public boolean checkGround() {
	if (mainScreen.getElementAt(newPlayer.getX(), newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1)
		.getChunkIMG()) {

	}
	return true;
    }

    public void releasePlayerFlags() {
	playerMoveTimer.stop();
	player.releaseKeyFlags();

	if (player.moveState != MoveState.SPACE) {
	    player.moveState = null;
	}

	initSpeed = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
	releasePlayerFlags();

//	if (keyList.contains(KeyEvent.VK_RIGHT)) {
//	    player.setMoveState(MoveState.RIGHT_STOP);
//	    Double temp = PLAYER_WALK_VELOCITY;
//	    temp -= 0.45;
//	    if (initSpeed >= 0) {
//		player.move(PLAYER_WALK_VELOCITY, 0);
//	    } else {
//		player.move(initSpeed, 0);
//	    }
//	}
	
	keyList.clear();
    }

    void callEnemyCLoudMovement() {
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

    public void startEnemyTimer() {
	eTimer.start();
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
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object source = e.getSource();
	count++;

	if (source == eTimer) {
	    callEnemyCLoudMovement();
	}

	if (player.moveState != null) {

	    if (source == playerMoveTimer) {
		if (keyList.contains(KeyEvent.VK_RIGHT) && keyList.contains(KeyEvent.VK_LEFT)) {
		    return;
		}

		if (keyList.contains(KeyEvent.VK_RIGHT)) {
		    // player.setRightStep(true);

		    initSpeed += 0.45;
		    if (initSpeed >= PLAYER_WALK_VELOCITY) {
			player.move(PLAYER_WALK_VELOCITY, 0);
		    } else {
			player.move(initSpeed, 0);
		    }

		}

		if (keyList.contains(KeyEvent.VK_LEFT)) {
		    // player.setLeftStep(true);

		    initSpeed += 0.45;
		    if (initSpeed >= PLAYER_WALK_VELOCITY) {
			player.move(PLAYER_WALK_VELOCITY, 0);
		    } else {
			player.move(initSpeed, 0);
		    }

		}

	    }

	    if (source == jumpUpTimer) {

		player.move(0, PLAYER_UP_VELOCITY);

		jumpStep += 30;
		if (jumpStep >= jumpHeight) {
		    jumpUpTimer.stop();
		    jumpDnTimer.start();
		}
	    }

	    if (source == jumpDnTimer) {

		player.move(0, PLAYER_DN_VELOCITY);

		jumpStep -= 3;
		if (jumpStep <= 0) {
		    jumpDnTimer.stop();
		    player.moveState = null;
		    player.setJumping(false);
		}
	    }

	} else {
	    // player.callIdleAnimation();
	}

    }
}
