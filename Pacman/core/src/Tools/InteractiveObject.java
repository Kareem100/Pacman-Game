
package Tools;

import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
     
    public InteractiveObject(PlayScreen screen, Rectangle bounds, boolean state){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
        
         BodyDef bdef = new BodyDef();
         FixtureDef fdef = new FixtureDef();
         PolygonShape shape = new PolygonShape();
        
         bdef.type = BodyDef.BodyType.StaticBody;
         bdef.position.set((bounds.getX()+ bounds.getWidth() / 2) / Main.PPM, (bounds.getY() + bounds.getHeight() / 2) / Main.PPM );
            
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / Main.PPM , (bounds.getHeight() / 2) / Main.PPM);
        fdef.shape = shape;
        fdef.isSensor = state;
        fixture = body.createFixture(fdef);
    }
    
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    
    public abstract void onCollide();
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Small Dots");
        return layer.getCell( (int)(body.getPosition().x  * Main.PPM / 16), (int)(body.getPosition().y * Main.PPM / 16) );
    }
    public TiledMapTileLayer.Cell getPrizeCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Fruit Prizes");
        return layer.getCell( (int)(body.getPosition().x  * Main.PPM / 16), (int)(body.getPosition().y * Main.PPM / 16) );
    }
    public TiledMapTileLayer.Cell getKeyCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Freeze Key");
        return layer.getCell( (int)(body.getPosition().x  * Main.PPM / 16), (int)(body.getPosition().y * Main.PPM / 16) );
    } 
}
