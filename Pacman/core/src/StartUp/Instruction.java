
package StartUp;

import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class Instruction implements Screen{
    
    private Main game;
    private OrthographicCamera screenCam;
    
    private Texture[] texture;
    
    public Instruction(Main game) {
        this.game = game;
        screenCam = new OrthographicCamera(game.V_WIDTH, game.V_HEIGHT);
        texture = new Texture[3];
        texture[0] = new Texture("Sprites\\Instructions0.jpg");
        texture[1] = new Texture("Sprites\\Instructions1.jpg");
        texture[2] = new Texture("Sprites\\Instructions2.jpg");
    }

    @Override
    public void render(float dt) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && screenCam.position.y > -640 * 1.3f)
            screenCam.translate(0, -5);
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) && screenCam.position.y < 0)
            screenCam.translate(0, 5);
        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            screenCam = new OrthographicCamera(1.9f*game.V_WIDTH, 1.4f*game.V_HEIGHT);
            screenCam.position.x = 300;
            screenCam.position.y = 300;
            MainMenu.fromScreen=false;
            game.setScreen(new MainMenu(game));
            this.dispose();
        }
        screenCam.update();
        game.batch.setProjectionMatrix(screenCam.combined);
        game.batch.begin();
            game.batch.draw(texture[0],-game.V_WIDTH/2, -game.V_HEIGHT/2, game.V_WIDTH, game.V_HEIGHT);
            game.batch.draw(texture[1],-game.V_WIDTH/2, -640, game.V_WIDTH, game.V_HEIGHT);
            game.batch.draw(texture[2],-game.V_WIDTH/2, -640 * 1.68f, game.V_WIDTH, game.V_HEIGHT);
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
        for(int i =0; i < 3; ++i)
            texture[i].dispose();
    }
    
}
