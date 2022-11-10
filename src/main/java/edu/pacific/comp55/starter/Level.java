package edu.pacific.comp55.starter;

import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Level {

    private static Map map;
    private Player player;
    private Enemy enemy;

    // Collection to store chunks
    private ArrayList<GRect> chunkList;
    
    // Int array to store (x,y) position of a chunk
    int[] chunkPos;

    // Constructor
    public Level() {
	map = new Map();
	player = new Player(50, 50, new GImage("idle1.png", 50, 50));
	chunkList = new ArrayList<>();
	chunkPos = new int[2];

	generateChunks();
    }

    // Generates every chunk of the game world
    // TODO: fill this out
    public void generateChunks() {
	map.createChunk("chunk1", 0, 400, 500, 200);
	map.createChunk("chunk2", 600, 450, 500, 200);
    }

    // Return collection of GRects
    public HashMap<String, GRect> getChunkToGRect() {
	return map.getChunkToGRect();
    }
    
    // Returns an array(x,y) position of a specified chunk
    public int[] getChunkPos(String key) {
	chunkPos[0] = (int)getChunkToGRect().get(key).getX();
	chunkPos[1] = (int)getChunkToGRect().get(key).getY();
	
	return chunkPos;
    }

}
