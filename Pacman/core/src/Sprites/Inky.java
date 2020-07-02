
package Sprites;

import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

public class Inky extends Enemy{
    private TextureRegion region;
    
    private Animation searching;
    private float stateTimer;
    private boolean in;
    public static float velX;
    public static float velY;
    public static Body body;
    
    public Inky(PlayScreen screen, float x, float y){
        super(screen, x, y);
        in = false;
        stateTimer = 0;
        velX = 0f;
        velY = 0f;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0 ; i  < 8; ++i)
            frames.add(new TextureRegion((screen.getAtlas().findRegion("Inky")), i*16, 0, 16, 16));
         
        searching = new Animation (0.1f, frames);
        searching.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        frames.clear();
        
        setBounds(0, 0, 16/Main.PPM, 16/Main.PPM);
    }
    
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() / Main.PPM, getY() / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.DIRECTION_BIT | Main.BORDER_BIT | Main.PACMAN_BIT | Main.ENEMY_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(7f / Main.PPM);
        
        fdef.shape = shape;
        body.createFixture(fdef);
        
        PolygonShape center = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-0.2f, -0.2f).scl(1 / Main.PPM);
        vertice[1] = new Vector2(-0.2f, 0.2f).scl(1 / Main.PPM);
        vertice[2] = new Vector2(0.2f, 0.2f).scl(1 / Main.PPM);
        vertice[3] = new Vector2(0.2f, -0.2f).scl(1 / Main.PPM);
        center.set(vertice);
        fdef.filter.categoryBits = Main.ENEMY_CENTER_BIT;
        fdef.shape = center;
        fdef.isSensor = true;
        
        body.createFixture(fdef).setUserData("Inky");
    }
    @Override
    protected void handleMovement(){
            body.setLinearVelocity(velX, velY);
    }
    
    @Override
     public void update(float dt){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        
        region= (TextureRegion) searching.getKeyFrame(stateTimer, true);
        setRegion(region);
        setOrigin(8/Main.PPM, 8/Main.PPM);
        stateTimer += dt;

        if (stateTimer > 10 && !in){
            velX = 1f;
            velY = 0f;
            in = true;
        }
        
        if (!screen.isPaused)
            handleMovement();
    }
}
