package edu.pacific.comp55.starter;

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
public class Level {

    // TODO: Integrate Player and Enemy classes with Level
    private MainApplication mainScreen;
    private Map map;
    private Player player;
    private Enemy enemy;
    
    private Timer timer;

    // Constructor
    public Level(MainApplication program, int levelNum) {
    	this.timer = new Timer(1000, program);
    	mainScreen = program;
    	map = new Map();
    	if (levelNum == 1) {
    		setupLevel1();
    	}
    }

    public void showContents() {
    mainScreen.add(map.getChunks().get(0).getChunkGRect());
    mainScreen.add(map.getChunks().get(0).getChunkIMG());
    
    mainScreen.add(map.getChunks().get(1).getChunkGRect());
    mainScreen.add(map.getChunks().get(1).getChunkIMG());
    
//    mainScreen.add(getChunk("ground1.png"));
//    mainScreen.add(getChunk("g2"));
	mainScreen.add(player.getImage());
	mainScreen.add(enemy.getImage());
	//mainScreen.add(enemy.);
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

    public void actionPerformed(){
		player.move();
	    }
    // Generates every chunk of the game world
    // TODO: fill this out
    public void generateChunks() {
	
    }
    
    public void setupLevel1() {
    	player = new Player(50, 415);
   
    	enemy = new Enemy(300, 475);
    	map.createChunk("g1", 0, 515, 650, 250);
    	map.createChunk("g2", 790, 425, 650, 350);
    }
    

}
