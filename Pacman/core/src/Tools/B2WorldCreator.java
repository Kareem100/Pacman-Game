
package Tools;

import Movement.*;
import Levels_Screen.PlayScreen;
import Sprites.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreator {
    
    private World world;
    private PlayScreen screen;
    private TiledMap map;
    private boolean once;
    private float timer;
    public B2WorldCreator(PlayScreen screen) {
        this.screen = screen;
        world = screen.getWorld();
        map = screen.getMap();
        // Borders 
        for(MapObject object: map.getLayers().get("BordersObj").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new Border(screen, rect, false);
        }
        // Pills 
        for(MapObject object: map.getLayers().get("Pill").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new Pill(screen, rect, true);
        }
        // Prizes 
        for(MapObject object: map.getLayers().get("Prizes").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new Prize(screen, rect, true);
        }
        /******************* START DIRECTIONS ********************/
        for(MapObject object: map.getLayers().get("LAUNCH").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new LAUNCH(screen, rect);
        }
        for(MapObject object: map.getLayers().get("LEFT-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new LEFT_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("UP-LEFT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new UP_LEFT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("UP-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new UP_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("UP-LEFT-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new UP_LEFT_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("UP-DOWN-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new UP_DOWN_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("UP-DOWN-LEFT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new UP_DOWN_LEFT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("DOWN-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new DOWN_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("DOWN-LEFT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new DOWN_LEFT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("DOWN-LEFT-RIGHT").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new DOWN_LEFT_RIGHT(screen, rect);
        }
        for(MapObject object: map.getLayers().get("ALL").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =   ((RectangleMapObject) object).getRectangle();
            new ALL(screen, rect);
        }
        /********************* END DIRECTIONS ********************/
    }
    public void showKey(float dt){
        timer += dt;
       if (timer > 30){
            if (!once){
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Freeze Key");
                layer.setVisible(true);
                
                for(MapObject object: map.getLayers().get("Key").getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rect =   ((RectangleMapObject) object).getRectangle();
                    new Key(screen, rect, true);
                }
                once =true;
            }
        }
    }
}
