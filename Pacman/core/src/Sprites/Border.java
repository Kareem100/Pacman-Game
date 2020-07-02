
package Sprites;

import Tools.InteractiveObject;
import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class Border extends InteractiveObject{

    public Border(PlayScreen screen, Rectangle bounds, boolean state) {
            super(screen, bounds, state);
            fixture.setUserData(this);
            setCategoryFilter(Main.BORDER_BIT);
        }
    
     @Override
    public void onCollide() {
    }
   
}
