package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Chunk {

	private GImage chunkIMG;
	private GImage spikeIMG;
	private GRect chunkGRect;

	// Constructor
	public Chunk(String chunkName, String path, int chunkX, int chunkY, int width, int height) {

		this.chunkIMG = new GImage("ground1.png", chunkX, chunkY);
		this.chunkIMG.setSize(width, height);

		this.spikeIMG = new GImage("Spike.png", chunkX, chunkY);
		this.spikeIMG.setSize(width, height);

	}

	// Getters
	public GImage getChunkIMG() {
		return chunkIMG;
	}

	public GRect getChunkGRect() {
		return chunkGRect;
	}

	public GImage getspikeIMG() {
		return spikeIMG;
	}

}
