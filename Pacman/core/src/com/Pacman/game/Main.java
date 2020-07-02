package com.Pacman.game;

import Levels_Screen.PlayScreen;
import Scenes.GameOver;
import StartUp.Instruction;
import StartUp.Intro;
import StartUp.MainMenu;
import StartUp.Scoreboard;
import StartUp.Setting;
import StartUp.Username;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    
    public static SpriteBatch batch;
    public static AssetManager manager;
    
    public static int width = 600;
    public static int height = 600;
    public static int highscore=0;
    public static String username = "user";
    public static boolean mute; // DON'T FORGET SOUNDS OPTIONS
    public static String mapTheme;
    
    public static final int V_WIDTH = 19*16;
    public static final int V_HEIGHT = 26*16;
    public static final float PPM = 100;
    public static final short EAT_AREA_BIT = 1;
    public static final short PACMAN_BIT = 2;
    public static final short ENEMY_BIT = 4;
    public static final short ENEMY_CENTER_BIT = 8;
    public static final short BORDER_BIT = 16;
    public static final short PILL_BIT = 32;
    public static final short PRIZE_BIT = 64;
    public static final short KEY_BIT = 128;
    public static final short DIRECTION_BIT = 256;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("Sounds//Death.wav", Music.class);
        manager.finishLoading();
        
        setScreen(new Intro(this));
        //setScreen(new Username(this));
        //setScreen(new MainMenu(this));
        //setScreen(new Instruction(this));
        //setScreen(new Scoreboard(this));
        //setScreen(new Setting(this));
        //setScreen(new PlayScreen(this));
        //setScreen(new GameOver(this));
    }
    
    @Override
    public void render () {
            super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        manager.dispose();
    }
	
}
