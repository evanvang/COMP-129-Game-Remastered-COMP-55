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
 *         and Level
 * 
 */
public class Level extends GraphicsPane implements KeyListener, ActionListener {

    private final int jumpHeight = 150;
    private int jumpStep = 0;
    private Timer jumpUp = new Timer(2, this);
    private Timer jumpDn = new Timer(2, this);

    private final int PLAYER_VELOCITY_WALK = 5;

    private MainApplication mainScreen;
    private Map map;
    private Player player;
    private Timer eTimer;
    private Cloud cloud;
    private double enemyVel = 3;
    // private double cloudVel = 3;
    private int time = 30;
    private GLabel timeLabel;
    private GLabel liveLabel;
    private int count = 0;
    private GImage liveIMG;

    private Timer playerTimer = new Timer(2, this);

    // Constructor
    public Level(MainApplication program, int levelNum) {

	this.eTimer = new Timer(50, this);
	mainScreen = program;
	map = new Map();
	drawTimeLabel();
	drawLiveLabel();
	if (levelNum == 1) {
	    setupLevel1();
	}
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
	mainScreen.add(timeLabel);
	mainScreen.add(liveIMG);
	mainScreen.add(liveLabel);
	startTimer();
    }

    public void hideContents() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

	int keyCode = e.getKeyCode();

	if (keyCode == KeyEvent.VK_RIGHT) {
	    System.out.println("right");
	    player.moveState = MoveDirection.RIGHT;
	    playerTimer.start();
	}

	if (keyCode == KeyEvent.VK_LEFT) {

	    player.moveState = MoveDirection.LEFT;
	    playerTimer.start();

	}

	if (keyCode == KeyEvent.VK_SPACE) {

	    player.moveState = MoveDirection.SPACE;
	    jumpStep = 0;
	    jumpUp.start();

	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	playerTimer.stop();
	if (player.moveState == MoveDirection.LEFT || player.moveState == MoveDirection.RIGHT) {

	}
	if (player.moveState != MoveDirection.SPACE) {
	    player.moveState = null;
	}

    }

    void moveEandC() {
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
    public void actionPerformed(ActionEvent e) {
	Object source = e.getSource();
	count++;

	if (source == eTimer) {
	    moveEandC();
	}

	if (source == playerTimer && playerTimer.isRunning()) {
	    // horizontal motion
	    if (player.moveState == MoveDirection.RIGHT || player.moveState == MoveDirection.LEFT) {
		player.move(PLAYER_VELOCITY_WALK, 0);
	    }
	}

	if (source == jumpUp) {
	    System.out.println("go up");
	    player.move(0, 2);
	    System.out.println("after up");
	    jumpStep += 2;
	    if (jumpStep >= jumpHeight) {
		jumpUp.stop();
		jumpDn.start();
	    }
	}

	if (source == jumpDn) {
	    player.move(0, -2);
	    jumpStep -= 2;
	    if (jumpStep <= 0) {
		jumpDn.stop();
		player.moveState = null;
	    }
	}

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

    public void drawTimeLabel() {
	timeLabel = new GLabel("30", 200, 50);
	timeLabel.setColor(Color.WHITE);
	timeLabel.setFont("Arial-Bold-30");
    }

    public void drawLiveLabel() {
	liveLabel = new GLabel("3", 95, 50);
	liveLabel.setColor(Color.WHITE);
	liveLabel.setFont("Arial-Bold-30");
	liveIMG = new GImage("liveshead.png", 20, 8);
	liveIMG.setSize(65, 65);

    }

    public void startTimer() {
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
}
