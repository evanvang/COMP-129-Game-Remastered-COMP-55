package edu.pacific.comp55.starter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Map {

	// Array list holds GRect objects for our game world
	private ArrayList<Chunk> chunks;
	private ArrayList<Enemy> enemies;
    private ArrayList<Chunk> groundPieces;
	public ArrayList<Chunk> getChunks() {
		return chunks;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	public ArrayList<Chunk> getChunk() {
		return chunks;
	}

	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	// Constructor
	public Map() {
		this.chunks = new ArrayList<>();
		this.enemies = new ArrayList<Enemy>();
		this.groundPieces = new ArrayList<Chunk>();
	}

	// Method to create a chunk, adds to the ArrayList "chunkToGRect"
	public void createChunk(char chunkName, String path, int chunkX, int chunkY, int width, int height) {
		Chunk chunk = new Chunk(chunkName, path, chunkX, chunkY, width, height);

		chunks.add(chunk);
	}

	public void createEnemy(int startX, int startY) {
		Enemy enemy = new Enemy(startX, startY);
		enemies.add(enemy);
	}
	
	public void removeChunks(ArrayList<Chunk> chunks) {
		chunks.clear();
	}
	public void removeEnemies(ArrayList<Enemy> enemies) {
		enemies.clear();
	}

	public ArrayList<Chunk> getGroundChunks() {
		return groundPieces; 
	}
	
}
