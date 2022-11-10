package edu.pacific.comp55.starter;

import java.awt.Color;
import java.util.HashMap;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class Map extends GraphicsPane {

    // Chunks position
    public int chunkX, chunkY;

    // Size of chunk
    public int width, height;
    
    // HashMap declaration, key values are names of chunks, actual values are GRect objects
    private HashMap<String, GRect> chunkToGRect;

    // Constructor
    public Map() {
	this.chunkX = 0;
	this.chunkX = 0;
	this.width = 0;
	this.height = 0;
	this.chunkToGRect = new HashMap<>();
    }
    
    // Method to create a chunk, adds to the HashMap "chunkToGRect"
    public void createChunk(String name, int chunkX, int chunkY, int width, int height) {
	GRect chunk = new GRoundRect(chunkX, chunkY, width, height);
	chunkToGRect.put(name, chunk);
    }
    
    public HashMap<String, GRect> getChunkToGRect() {
	return chunkToGRect;
    }

    // Abstract method from GraphicsPane
    @Override
    public void showContents() {

    }

    // Abstract method from GraphicsPane
    @Override
    public void hideContents() {

    }

    // Setters/Getters
    public double getFloorX() {
	return chunkX;
    }

    public double getFloorY() {
	return chunkY;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
