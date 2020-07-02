
package Scenes;

import StartUp.MainMenu;
import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class GameOver implements Screen{
    
    private Main game;
    private Texture background;
    
    private int cnt =0;
    private int Score[];
    private String Name[];
    
    public GameOver(Main game){
        this.game = game;
        background = new Texture("Sprites\\Game Over Scene.jpg");
        if(HUD.getScore() > Main.highscore)Main.highscore=HUD.getScore();
        takeData();
        saveScore();
    }
    
    private void takeData(){
        String name="", score="";
        Score = new int[10];
        Name = new String[10];
        boolean sizeReaded=false, nameReaded=false, scoreReaded=false, once = false;
        
        try {
            FileReader reader = new FileReader("..\\core\\assets\\scoresheet.txt");
                int c;
                while ( (c = reader.read())!= -1 ){
                    if(!sizeReaded){
                    sizeReaded=true;
                    continue;
                }
                if ((char) c == '-' && !nameReaded){
                    nameReaded = true;
                    scoreReaded = false;
                }
                else if (!nameReaded){
                    if (once)
                        name+=(char) c;
                    once=true;
                }
                else if ((char) c == '-' && !scoreReaded){
                    scoreReaded = true;
                    nameReaded = false;
                    once = false;
                    Name[cnt] = name;
                    Score[cnt] = Integer.valueOf(score);
                    name ="";
                    score ="";
                    cnt++;
                }    
                else if (!scoreReaded)
                    score+=(char) c;
            }
         reader.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void saveScore(){
        Name[cnt] = game.username;
        Score[cnt] = HUD.getScore();
        cnt++;  //The New Player
        
        try {
            FileWriter writer = new FileWriter("..\\core\\assets\\scoresheet.txt", false);
            writer.write(String.valueOf(cnt));
            for (int i =0; i < cnt; ++i)
                writer.write("\n"+Name[i]+"-"+Score[i]+"-");
            writer.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
               game.setScreen(new MainMenu(game));
               this.dispose();
        }
        
        game.batch.begin();
            game.batch.draw(background, 0, 0, game.V_WIDTH, game.V_HEIGHT);
        game.batch.end();
        
    }
    
    @Override
    public void show() {
    }
    
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
