package edu.pacific.comp55.starter;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Level {
    
    private static Map map;
    private Player player;
    private Enemy enemy;
    
    static GRect chunk1, chunk2, chunk3, chunk4, chunk5, chunk6;

    // Collection to store floors
    private ArrayList<GRect> chunkList;

    // Constructor
    public Level() {
	map = new Map();
	player = new Player(50, 50, new GImage("idle1.png", 50, 50));
	chunkList = new ArrayList<>();
	
	generateChunks();
    }
    
    // Generates every chunk of the game world
    // TODO: fill this out
    public void generateChunks() {
	chunk1 = map.createFloor(50, 50, 50, 50);
	chunkList.add(chunk1);
	
	chunk2 = map.createFloor(75, 75, 75, 75);
	chunkList.add(chunk2);
    }
    
    // Return the collection of GRects
    public ArrayList<GRect> getChunkList() {
	return this.chunkList;
    }
    
}
