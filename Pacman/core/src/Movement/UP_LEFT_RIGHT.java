
package Movement;

import Levels_Screen.PlayScreen;
import Sprites.Blinky;
import Sprites.Inky;
import Sprites.Pacman;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class UP_LEFT_RIGHT extends Direction{
     
    public UP_LEFT_RIGHT(PlayScreen screen, Rectangle bounds) {
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
        
            if (enemyPosX < pacPosX){
            Blinky.velX = 1f;
            Blinky.velY = 0f;
            }
            else {
            Blinky.velX = -1f;
            Blinky.velY = 0f;
            }
        
    }

    @Override
    public void onInkyCollide() {
        pacPosX=Pacman.body.getPosition().x;
        pacPosY=Pacman.body.getPosition().y;
        enemyPosX =Inky.body.getPosition().x;
        enemyPosY =Inky.body.getPosition().y;
        
            if (enemyPosY < pacPosY){
            Inky.velX = 0f;
            Inky.velY = 1f;
            }
            else {
                if (enemyPosX<pacPosX){
                    Inky.velX = -1f;
                    Inky.velY = 0f;
                }
                else {
                    Inky.velX = 1f;
                    Inky.velY = 0f;
                }
            }
    }
    
}
