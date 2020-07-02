
package Sprites;

import Tools.InteractiveObject;
import Scenes.HUD;
import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle; 

public class Pill extends InteractiveObject{
    
    private static int pills;
    private PlayScreen screen;
    public Pill(PlayScreen screen, Rectangle bounds, boolean state) {
            super(screen, bounds, state);
            this.screen = screen;
            fixture.setUserData(this);
            setCategoryFilter(Main.PILL_BIT);
            pills = 152;
        }
    
     @Override
    public void onCollide() {
      if (getCell().getTile()!=null){
          HUD.addScore(5f);
          getCell().setTile(null);
          pills--;
          if(pills==0)screen.isWon=true;
      }
    }
   
}
