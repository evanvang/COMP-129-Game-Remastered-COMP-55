package edu.pacific.comp55.starter;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplication extends GraphicsApplication {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String MUSIC_FOLDER = "sounds";
    private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

    private MainGame mainGame;
    private MenuPane menu;
    private SettingsPane settings;
    private int count;

    public void init() {
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void run() {
	System.out.println("Hello, world!");
	mainGame = new MainGame(this);
	menu = new MenuPane(this);
	//settings = new SettingsPane(this);
	setupInteractions();
	switchToMenu();
    }

    //Start Screen
    public void switchToMenu() {
	count++;
	switchToScreen(menu);
    }

    public void switchToLevel() {
	switchToScreen(mainGame);
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
