
package Sprites;

import Tools.InteractiveObject;
import Scenes.HUD;
import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.math.Rectangle; 

public class Key extends InteractiveObject{
    private PlayScreen screen;
    
    public Key(PlayScreen screen, Rectangle bounds, boolean state) {
            super(screen, bounds, state);
            this.screen = screen;
            fixture.setUserData(this);
            setCategoryFilter(Main.KEY_BIT);
        }
    
     @Override
    public void onCollide() { 
      if (getKeyCell().getTile()!=null){
          getKeyCell().setTile(null);
          screen.isKey = true;
      }
    }
}
