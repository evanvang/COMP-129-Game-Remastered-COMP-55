package edu.pacific.comp55.starter;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplication extends GraphicsApplication {

    public static final int WINDOW_WIDTH = 1440;
    public static final int WINDOW_HEIGHT = 850;
    public static final String MUSIC_FOLDER = "sounds";
    private static final String[] SOUND_FILES = { "wesbrook.mp3" };

    private MainGame mainGame;
    private MenuPane menu;
    private SettingsPane settings;
    private Level level;
    private int count;

    public void init() {
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void run() {
	level = new Level(this, 1);
	System.out.println("Hello, world!");
	mainGame = new MainGame(this);
	menu = new MenuPane(this);
	settings = new SettingsPane(this);
	setupInteractions();
	switchToMenu();
    }

    // Start Screen
    public void switchToMenu() {
	count++;
	switchToScreen(menu);
    }

    public void switchToLevel() {
	switchToScreen(level);
	playRandomSound();
    }

    public void switchToSettings() {
	switchToScreen(settings);
    }

    private void playRandomSound() {
	AudioPlayer audio = AudioPlayer.getInstance();
	audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
    }

    public static void main(String[] args) {
	new MainApplication().start();
    }
}
