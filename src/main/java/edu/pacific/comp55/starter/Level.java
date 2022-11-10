package edu.pacific.comp55.starter;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Level {
    
    private static Map map;
    private Player player;
    
    static GRect mainFloor;

    // Collection to store floors
    private ArrayList<GRect> floorList;

    // Constructor
    public Level() {
	map = new Map();
	player = new Player(50, 50, new GImage("idle1.png", 50, 50));
	floorList = new ArrayList<>();
	
	mainFloor = map.createFloor(50, 50, 50, 50);
    }

    public static void main(String[] args) {
    }
    
    public GRect getGRect() {
	return mainFloor;
    }

}
