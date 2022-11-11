package edu.pacific.comp55.starter;

import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GRect;

/**
 * @author Team No Focus! Level class will generate the game world by
 *         integrating Map, Player, and Level
 */
public class Level {

    // TODO: Integrate Player and Enemy classes with Level
    private static Map map;
    private Player player;
    private Enemy enemy;

  
    // Constructor
    public Level() {
	map = new Map();
	player = new Player(50, 50, new GImage("idle1.png", 50, 50));

	generateChunks();
    }

    // Generates every chunk of the game world
    // TODO: fill this out
    public void generateChunks() {
	map.createChunk("g1", 0, 400, 500, 200);
	map.createChunk("g2", 600, 450, 500, 200);
    }

    // Returns the HashMap of GRects
    public HashMap<String, GRect> getChunkToGRect() {
	return map.getChunkToGRect();
    }

    // Returns a specified chunk within the HashMap
    public GRect getChunk(String key) {
	return getChunkToGRect().get(key);
    }

    // Returns an array{x,y} position of a specified chunk
    public int[] getChunkPos(String key) {
	return map.getChunkPos(key);
    }

}
