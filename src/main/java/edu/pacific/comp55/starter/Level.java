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

    private MainApplication mainScreen;
    private Map map;
    private Player player;
    private Map ground;
    private Timer timer;
    private Cloud cloud;
    private double enemyVel = 3;
//	private double cloudVel = 3;
    private int time = 30;
    private GLabel timeLabel;
    private int count = 0;

    private Enemy enemy;
    private Timer eTimer;

    private Timer playerTimer;
    private GImage newPlayer;
    private ArrayList<Chunk> chunky;
    

    // Constructor
    public Level(MainApplication program, int levelNum) {
	this.timer = new Timer(50, this);
	mainScreen = program;
	map = new Map();
	drawTimeLabel();
	if (levelNum == 1) {
	    setupLevel1();
	}
	mainScreen.setupInteractions();
	playerTimer = new Timer(2, this);
	newPlayer = player.getplayerIMG();
	chunky = ground.getChunks();
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
	    playerTimer.start();

	}
    }

    public boolean checkGround() {
    	if (mainScreen.getElementAt(newPlayer.getX() ,newPlayer.getY() + newPlayer.getHeight()) == chunky.get(1).getChunkIMG() ) {
    		
    	}
    	return true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
	playerTimer.stop();
	if (player.moveState == MoveDirection.LEFT || player.moveState == MoveDirection.RIGHT) {

	}
	player.moveState = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object source = e.getSource();
	count++;

	if (source == timer) {
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

	if (source == playerTimer) {
	    if (playerTimer.isRunning() && player.moveState != MoveDirection.SPACE) {
		player.move(3, 0);
	    } else if (player.moveState == MoveDirection.SPACE) {
		int jumpTime = 10;
		System.out.println(jumpTime);

		while (jumpTime != 0) {
		    if (!(jumpTime < 5)) {
			player.move(0, 1);
		    } else {
			player.move(0, -1);
		    }
		    jumpTime--;

		}
	    }
	}
    }

    public void drawTimeLabel() {
	timeLabel = new GLabel("30", 50, 50);
	timeLabel.setLocation(200, 50);
	timeLabel.setColor(Color.WHITE);
	timeLabel.setFont("Arial-Bold-30");
    }

    public void startTimer() {
	timer.start();
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
