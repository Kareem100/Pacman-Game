package Scenes;

import Levels_Screen.PlayScreen;
import StartUp.MainMenu;
import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PopUpDialogBox extends Dialog{
    
    private PlayScreen screen;
    private Main game;
    
    public PopUpDialogBox(String title, Skin skin, PlayScreen screen) {
        super(title, skin);
        this.screen = screen;
        this.game = screen.getGame();
        text("Back To Main Menu?");
        button("YES", "yes");
        button("NO", "no");
    }
    
    @Override
    public void result(Object object) {
        if (object.equals("yes")){
              screen.once=false;
              screen.isPaused=false;
              MainMenu.fromScreen = true;
             game.setScreen (new MainMenu(game));
             screen.dispose();
        }
        else if (object.equals("no")){
            screen.once=false;
            screen.isPaused=false;
        }
    }
}
