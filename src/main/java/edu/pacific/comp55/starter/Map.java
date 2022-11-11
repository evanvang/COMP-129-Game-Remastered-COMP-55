package edu.pacific.comp55.starter;

import java.awt.Color;
import java.util.HashMap;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Map extends GraphicsPane {

    // HashMap declaration, key values are names of chunks, actual values are GRect objects
    private HashMap<String, GRect> chunkToGRect;

    // Constructor
    public Map() {
	this.chunkToGRect = new HashMap<>();
    }

    // Method to create a chunk, adds to the HashMap "chunkToGRect"
    public void createChunk(String name, int chunkX, int chunkY, int width, int height) {
	GRect chunk = new GRoundRect(chunkX, chunkY, width, height);
	chunkToGRect.put(name, chunk);
    }

    // Returns the HashMap
    public HashMap<String, GRect> getChunkToGRect() {
	return chunkToGRect;
    }
    
    // Returns an array(x,y) position of a specified chunk
    public int[] getChunkPos(String key) {
	int[] xy = new int[2];
	xy[0] = (int)chunkToGRect.get(key).getX();
	xy[1] = (int)chunkToGRect.get(key).getX();
	return xy;
    }

    // Abstract method from GraphicsPane
    @Override
    public void showContents() {

    }

    // Abstract method from GraphicsPane
    @Override
    public void hideContents() {

    }

}


