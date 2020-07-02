
package Movement;

import Levels_Screen.PlayScreen;
import Sprites.Blinky;
import Sprites.Inky;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class LAUNCH extends Direction{

    public LAUNCH(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.DIRECTION_BIT);
    }

    @Override
    public void onBlinkyCollide() {
        Blinky.velX = 0f;
        Blinky.velY = 1f;
    }

    @Override
    public void onInkyCollide() {
        Inky.velX = 0f;
        Inky.velY = 1f;
    }
    
}
