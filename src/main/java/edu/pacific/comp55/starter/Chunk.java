package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Chunk {

	private GImage backgroundIMG;
	private GImage chunkIMG;
	private GImage spikeIMG;
	private GRect chunkGRect;
	private char name;

	// Constructor
	public Chunk(char chunkName, String path, int chunkX, int chunkY, int width, int height) {
		chunkName = name;
		this.backgroundIMG = new GImage("background.png", chunkX, chunkY);
		this.backgroundIMG.setSize(width, height);

		this.chunkIMG = new GImage("ground1.png", chunkX, chunkY);
		this.chunkIMG.setSize(width, height);

		this.spikeIMG = new GImage("Spike.png", chunkX, chunkY);
		this.spikeIMG.setSize(width, height);

	}

	// Getters
	public GImage getbackgroundIMG() {
		return backgroundIMG;
	}

	public GImage getChunkIMG() {
		return chunkIMG;
	}

	public GRect getChunkGRect() {
		return chunkGRect;
	}

	public GImage getspikeIMG() {
		return spikeIMG;
	}
	
	public char getID() {
		return name;
	}
}
