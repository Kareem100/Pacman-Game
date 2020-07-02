
package Movement;

import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Direction {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
     
    protected float pacPosX;
    protected float pacPosY;
    protected float enemyPosX;
    protected float enemyPosY;
    
    public Direction(PlayScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
        
         BodyDef bdef = new BodyDef();
         FixtureDef fdef = new FixtureDef();
         PolygonShape shape = new PolygonShape();
        
         bdef.type = BodyDef.BodyType.StaticBody;
         bdef.position.set((bounds.getX()+ bounds.getWidth() / 2) / Main.PPM, (bounds.getY() + bounds.getHeight() / 2) / Main.PPM );
            
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 12) / Main.PPM , (bounds.getHeight() / 12) / Main.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }
    
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    
    public abstract void onBlinkyCollide();
    public abstract void onInkyCollide();
}
