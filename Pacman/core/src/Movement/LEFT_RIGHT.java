
package Movement;

import Movement.Direction;
import Levels_Screen.PlayScreen;
import Sprites.Blinky;
import Sprites.Inky;
import Sprites.Pacman;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class LEFT_RIGHT extends Direction{
    int cnt;
    public LEFT_RIGHT(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.DIRECTION_BIT);
    }

    @Override
    public void onBlinkyCollide() {
        pacPosX=Pacman.body.getPosition().x;
        enemyPosX =Blinky.body.getPosition().x;
        if (enemyPosX < pacPosX){
            Blinky.velX = 1f;
            Blinky.velY=0f;
        }
        else {
            Blinky.velX = -1f;
            Blinky.velY=0f;
        }
    }

    @Override
    public void onInkyCollide() {
        if (cnt<2){
            pacPosX=Pacman.body.getPosition().x;
            enemyPosX =Inky.body.getPosition().x;
            if (enemyPosX > pacPosX){
                Inky.velX = -1f;
                Inky.velY=0f;
            }
            else {
                Inky.velX = 1f;
                Inky.velY=0f;
            }
            cnt++;
        }
    }
    
}
