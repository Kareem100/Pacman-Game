
package Sprites;

import Levels_Screen.PlayScreen;
import Scenes.HUD;
import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Pacman extends Sprite {
    
    public World world;
    public static Body body;
    
    public enum State {EATING, DIEING};
    public static State currentState;
    public static State previousState;
    private Animation eating;
    private Animation dieing;
    private float stateTimer;
    private static boolean dead, once;
    
    private final int PacmanInitPosX=152;
    private final int PacmanInitPosY= 56; 
    private final float PacmanMaxSpeed = 1.6f;
    private static PlayScreen screen;
    
    public Pacman (PlayScreen screen){
        super(screen.getAtlas().findRegion("Pacman"));
        this.world = screen.getWorld();
        this.screen = screen;
        
        currentState = State.EATING;
        previousState = State.EATING;
        stateTimer = 0;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0 ; i  < 3; ++i)
            frames.add(new TextureRegion(getTexture(), i*16+1, 15, 16, 16));
        eating = new Animation (0.1f, frames);
        eating.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        frames.clear();
        
        for(int i = 3; i< 14; ++i)
            frames.add(new TextureRegion(getTexture(), i*16+1, 15, 16, 16));
        dieing = new Animation(0.3f, frames);
        dieing.setPlayMode(Animation.PlayMode.NORMAL);
        
        setBounds(0, 0, 16/Main.PPM, 16/Main.PPM);
        definePacman();
        dead = once =false;
    }
    
    public void definePacman(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(PacmanInitPosX / Main.PPM, PacmanInitPosY / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        
        FixtureDef  fdef = new FixtureDef();
        fdef.filter.categoryBits = Main.PACMAN_BIT;
        fdef.filter.maskBits = Main.BORDER_BIT | Main.PILL_BIT | Main.PRIZE_BIT |Main.KEY_BIT |Main.ENEMY_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(7f / Main.PPM);
        
        fdef.shape = shape;
        body.createFixture(fdef);
        
        EdgeShape center = new EdgeShape();
        center.set(new Vector2(-2/Main.PPM, 0/Main.PPM), new Vector2(2/Main.PPM, 0/Main.PPM));
        fdef.filter.categoryBits = Main.EAT_AREA_BIT;
        fdef.shape = center;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("Center");
    }
    
    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            setRotation(0);
            body.setLinearVelocity(PacmanMaxSpeed, 0);
        }
          
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
             setRotation(180);
             body.setLinearVelocity(-PacmanMaxSpeed, 0);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP) ){
            setRotation(90);
            body.setLinearVelocity(0, PacmanMaxSpeed);
        }
            
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ){
            setRotation(270);
            body.setLinearVelocity(0, -PacmanMaxSpeed);
        }
        
        // else if (Gdx.input.isKeyPressed(131))           // Dynamic Buttons for Settings         (It Works)
    }
    
    public void update(float dt){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
        setOrigin(8/Main.PPM, 8/Main.PPM);
        if (body.getPosition().x + getWidth()/2 < 0)
            body.setTransform(304/Main.PPM, body.getPosition().y, 0);
        else if (body.getPosition().x - getWidth()/2 > 304/Main.PPM)
            body.setTransform(0, body.getPosition().y, 0);
        
        if (!screen.isPaused)
             handleInput();
        
        if (dead && !once){
            HUD.lossesLife();
            once = true;
        }
        if (!screen.death.isPlaying()){
            once = false;
            dead=false;
        }
    }
    
    public TextureRegion getFrame(float dt){
        //currentState = getState();
        TextureRegion region= (TextureRegion) eating.getKeyFrame(stateTimer, true);
        
        switch(currentState){
            case DIEING:
                region = (TextureRegion) dieing.getKeyFrame(stateTimer, true);
                setRotation(90);
                    break;
            case EATING:
            default:
                region = (TextureRegion) eating.getKeyFrame(stateTimer, true);
                break;        
        }

        stateTimer = currentState == previousState? stateTimer + dt : 0;
        previousState = currentState;
        
        return region;
    }
    
    public static void dead(){
        screen.isDead = true;
        screen.death.play();
        currentState = State.DIEING;
        dead =true;
    }
}
