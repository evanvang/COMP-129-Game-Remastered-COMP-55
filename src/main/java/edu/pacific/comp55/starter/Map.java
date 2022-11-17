package edu.pacific.comp55.starter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Map {

	// Array list holds GRect objects for our game world
	private ArrayList<Chunk> chunks;

	public ArrayList<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	// Constructor
	public Map() {
		this.chunks = new ArrayList<>();
	}

	// Method to create a chunk, adds to the ArrayList "chunkToGRect"
	public void createChunk(String chunkName, String path, int chunkX, int chunkY, int width, int height) {
		Chunk chunk = new Chunk(chunkName, path, chunkX, chunkY, width, height);

		chunks.add(chunk);
	}

}
