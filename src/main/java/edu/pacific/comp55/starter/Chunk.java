package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Chunk {

    private GImage chunkIMG;
    private GRect chunkGRect;

    // Constructor
    public Chunk(String imgName, int chunkX, int chunkY, int width, int height) {
	this.chunkGRect = new GRoundRect(chunkX, chunkY, width, height);

	this.chunkIMG = new GImage("ground1.png");
    }

    // Getters
    public GImage getChunkIMG() {
	return chunkIMG;
    }

    public GRect getChunkGRect() {
	return chunkGRect;
    }

}
