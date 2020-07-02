
package Movement;

import Movement.Direction;
import Levels_Screen.PlayScreen;
import Sprites.Blinky;
import Sprites.Inky;
import Sprites.Pacman;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class UP_LEFT extends Direction{
    
    public UP_LEFT(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.DIRECTION_BIT);
    }

    @Override
    public void onBlinkyCollide() {
        
        pacPosY=Pacman.body.getPosition().y;
        enemyPosY =Blinky.body.getPosition().y;
        
                if (pacPosY>enemyPosY){
                    Blinky.velX = 0f;
                    Blinky.velY=  1f;
                }
                else {
                    Blinky.velX = -1f;
                    Blinky.velY=  0f;
                }
    }

    @Override
    public void onInkyCollide() {
        pacPosX=Pacman.body.getPosition().x;
        enemyPosX =Inky.body.getPosition().x;
        
                if (enemyPosX>pacPosX){
                    Inky.velX = -1f;
                    Inky.velY=  0f;
                }
                else {
                    Inky.velX = 0f;
                    Inky.velY=  1f;
                }
    }
    
}
