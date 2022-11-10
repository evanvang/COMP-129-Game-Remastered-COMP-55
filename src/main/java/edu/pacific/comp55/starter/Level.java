package edu.pacific.comp55.starter;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Level {

    private Map map;
    private Player player;

    // Collection to store floors
    private ArrayList<GRect> floorList;

    // Constructor
    public Level() {
	map = new Map();
	player = new Player(50, 50, new GImage("idle1.png", 50, 50));
	floorList = new ArrayList<>();
    }

    public static void main(String[] args) {
	// TODO Auto-generated method stub

    }

}
