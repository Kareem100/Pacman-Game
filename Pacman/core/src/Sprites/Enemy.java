package Sprites;

import Levels_Screen.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemy extends Sprite{
    
    protected World world;
    protected Map map;
    protected PlayScreen screen;
    public  float velX;
    public  float velY;
    
    public Enemy(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        setPosition(x, y);
        
        defineEnemy();
        handleMovement();
    }
    protected abstract void defineEnemy();
    protected abstract void handleMovement();
    
    public abstract void update(float dt);
}