
package StartUp;

import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Intro implements Screen{
    private Main game;
    private Texture texture[];
    private Sprite sprite[];
    private float alpha;
    private float stateTimer;
    private int cnt;

    public Intro(Main game) {
        this.game = game;
        
        texture = new Texture[2];
        texture[0] = new Texture("Sprites\\0.jpg");
        texture[1] = new Texture("Sprites\\1.jpg");
        
        sprite = new Sprite[2];
        sprite[0] = new Sprite(texture[0], 0, 0, 640, 400);
        sprite[1] = new Sprite(texture[1], 0, 0, 1920, 1080);
        
        sprite[0].setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite[1].setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        alpha = stateTimer = cnt = 0;
    }
    

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       
       /********** TIME CONTROLLER **************/
       stateTimer += dt;
       
        if (stateTimer < 3.95f)   //FADE IN
            alpha += (1f / 60f) / 2;
       else if (stateTimer > 6){ //FADE OUT
           alpha -= (1f / 60f) /2;
           if(stateTimer>9.5f){
            stateTimer=0;
            alpha=0;
            cnt++;
           }
       }
        /*****************************************/
        if (cnt==2){     // INTROS FINISHED 
            game.setScreen(new Username(game));
            this.dispose();
        }
        
        if (cnt<2)
            sprite[cnt].setAlpha(alpha);
       
       game.batch.begin();
       if (cnt<2)
           sprite[cnt].draw(game.batch);
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
        texture[0].dispose();
        texture[1].dispose();
    }
    
}
