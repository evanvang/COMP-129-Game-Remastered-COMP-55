package edu.pacific.comp55.starter;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import javafx.beans.binding.When;

import java.awt.*;
import java.awt.event.*;

import org.apache.commons.math3.random.ISAACRandom;

public class MainApplication extends GraphicsApplication {

	public static final int WINDOW_WIDTH = 1440;
	public static final int WINDOW_HEIGHT = 850;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "wesbrook.mp3" };

	private MainGame mainGame;
	private MenuPane menu;
	private SettingsPane settings;
	private Level level = new Level(this,1);
	private boolean isMuted;
	private WinScreen win;

	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		level = new Level(this, 1);

		mainGame = new MainGame(this);
		menu = new MenuPane(this);
		settings = new SettingsPane(this);
		win = new WinScreen(this);
		//	pause = new PausePane(this, level);
		setupInteractions();
		switchToMenu();
	}

	// Start Screen
	public void switchToMenu() {
		count++;
		switchToScreen(menu);
		stopRandomSound();
	}

	public void switchToLevel() {
		int levelNum = level.getLevelNum();
		level = new Level(this, levelNum);
		switchToScreen(level);
		playRandomSound();	
	}
	
	public void switchToWinScreen() {
		switchToPause(win);
		
	}
	
	public void switchToNewLevel() {
		level = new Level(this, 1);
		switchToScreen(level);
		playRandomSound();	
	}
	
	//    public void switchToCurrLevel() {
	//    	switchToScreen(level);
	//    	playRandomSound();
	//    }

	public void switchToSettings() {
		switchToScreen(settings);
	}

	public void switchPause(PausePane p) {
		switchToPause(p);

	}
	public void switchLose(GameOver o) {
		switchToLose(o);

	}

	private void playRandomSound() {
		isMuted = settings.checkIfMute();
		AudioPlayer audio = AudioPlayer.getInstance();
		if (!isMuted) {

			audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
		}

	}

	public void stopRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

	public static void main(String[] args) {
		new MainApplication().start();
	}
}
