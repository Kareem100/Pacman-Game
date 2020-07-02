
package Sprites;

import Tools.InteractiveObject;
import Scenes.HUD;
import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class Prize extends InteractiveObject{
    
    public Prize(PlayScreen screen, Rectangle bounds, boolean state) {
            super(screen, bounds, state);
            fixture.setUserData(this);
            setCategoryFilter(Main.PRIZE_BIT);
        }
    
     @Override
    public void onCollide() {
      if (getPrizeCell().getTile()!=null){
          HUD.addScore(10f);
          getPrizeCell().setTile(null);
      }
    }
   
}
