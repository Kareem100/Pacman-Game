
package Movement;

import Levels_Screen.PlayScreen;
import Sprites.Blinky;
import Sprites.Inky;
import Sprites.Pacman;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class DOWN_LEFT_RIGHT extends Direction{
    public DOWN_LEFT_RIGHT(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.DIRECTION_BIT);
    }
    @Override
    public void onBlinkyCollide() {
        
        pacPosX=Pacman.body.getPosition().x;
        pacPosY=Pacman.body.getPosition().y;
        enemyPosX =Blinky.body.getPosition().x;
        enemyPosY =Blinky.body.getPosition().y;
        
        if (enemyPosY< pacPosY){ 
            if (enemyPosX < pacPosX){
            Blinky.velX = 1f;
            Blinky.velY=0f;
            }
            else {
                Blinky.velX = -1f;
                Blinky.velY=0f;
            }
        }
        else {
            Blinky.velX = 0f;
            Blinky.velY=-1f;
        }
    }

    @Override
    public void onInkyCollide() {
         pacPosX=Pacman.body.getPosition().x;
        pacPosY=Pacman.body.getPosition().y;
        enemyPosX =Inky.body.getPosition().x;
        enemyPosY =Inky.body.getPosition().y;
        
        
            if (enemyPosX < pacPosX){
            Inky.velX = 1f;
            Inky.velY=0f;
            }
            else  if (enemyPosY< pacPosY){ 
                Inky.velX = -1f;
            Inky.velY=0f;
            }
            else {
                Inky.velX = 0f;
                Inky.velY= -1f;
            }
    }
    
}
