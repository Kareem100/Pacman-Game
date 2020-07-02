
package StartUp;

import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import javax.swing.JOptionPane;

public class Username implements Screen{
    
    private Main game;
    private Stage stage;
    private Skin skin;
    private Label enter;
    private TextField username;
    private TextButton play;
    private Viewport viewport;
    private Texture background;
    
    public Username( Main game) {
        this.game = game;
        background = new Texture(Gdx.files.internal("Sprites\\Space Background.jpg"));
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport, ((Main) game).batch);
        Gdx.input.setInputProcessor(stage);
        createWidgets();
    }
    
    private void createWidgets(){
        skin = new Skin(Gdx.files.internal("Sprites\\uiskin.json"));
        
        enter = new Label("Please Enter Your Name !", skin);
        enter.setSize(50, 50);
        enter.setPosition(game.V_WIDTH/2-enter.getWidth() - 30, game.V_HEIGHT/2+40);
        enter.setFontScale(1f);
        
        stage.addActor(enter);
        
        
        
        username = new TextField("", skin);
        username.setSize(200, 50);
        username.setAlignment(Align.center);
        username.setPosition(game.V_WIDTH/2-username.getWidth()/2 + 10, game.V_HEIGHT/2 - 20);
        
        stage.addActor(username);
        
        play = new TextButton("ADVANCE", skin);
        play.setSize(200, 50);
        play.setPosition(game.V_WIDTH/2-play.getWidth()/2 + 10, game.V_HEIGHT/2 - 80);
        
        play.addListener(new ClickListener() {
           
            @Override
            public void clicked(InputEvent event, float x, float y){
                if ( username.getText().length() < 3){
                    JOptionPane.showMessageDialog(null, "Are You Even Trying!\nPlease Enter A Real Name.");
                }
                else {
                Timer.schedule(new Task(){
                @Override
                public void run() {
                    MainMenu.fromScreen = true;
                    game.setScreen(new MainMenu(game));
                     getObjectClass().dispose();
                     }
                    }, 1.5f);
                    Label welcome = new Label("Welcome "+username.getText()+"!", skin);
                    welcome.setPosition(game.V_WIDTH/2-enter.getWidth() - 10, game.V_HEIGHT/2 - 140);
                    stage.addActor(welcome);
                    game.username = username.getText();
                }
            }
        });
        stage.addActor(play);
        
    }
    
    
    private Username getObjectClass(){
        return this;
    }
    
    @Override
    public void render(float dt) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          
       game.batch.begin();
           game.batch.draw(background, 0, 0, Main.V_WIDTH, Main.V_HEIGHT);
       game.batch.end();
       
       stage.act();
       stage.draw();
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
        stage.dispose();
    }
    
}
