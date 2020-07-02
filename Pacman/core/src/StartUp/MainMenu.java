
package StartUp;

import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen{
    
    private Main game;
    private final int buttonOffset;
    private final int verticalPos;
    private int width;
    private int height;
    public static boolean fromScreen;
    public static boolean landscape; 
    public Stage stage;
    private Skin skin;
    private Texture background;
    private Music music;
    
    public MainMenu(Main game) {
        //music = game.manager.get("", Music.class); // SOUND TRACKS
        //music.play();
        Gdx.graphics.setWindowedMode(game.width, game.height);
        this.game = game;
        
        background = new Texture("Sprites\\Main Menu.jpg"); // BACKGROUND
        
        buttonOffset = 20;
        verticalPos = 40;
        width = (landscape)?1000:600;
        height = 600;
        if (fromScreen){
            width=305;
            height=420;
        }
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);   // MAKE THE STAGE ACCEPTS EVENTS
        
        skin = new Skin(Gdx.files.internal("Sprites\\uiskin.json"));
        createActions();
    }
    
    private void createActions() {
        TextButton startGame = new TextButton("Start Game", skin);
        startGame.setPosition(Gdx.graphics.getWidth() / 2 - startGame.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2*(startGame.getHeight() + buttonOffset) -verticalPos);
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
               game.setScreen(new PlayScreen(game));
               getThisClass().dispose();
            }
        });
        stage.addActor(startGame);
        
        TextButton gameInstructions = new TextButton("Game Instructions", skin);
        gameInstructions.setPosition(Gdx.graphics.getWidth() / 2 - gameInstructions.getWidth() / 2, Gdx.graphics.getHeight() / 2 + (startGame.getHeight() + buttonOffset) -verticalPos);
        gameInstructions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new Instruction(game));
                getThisClass().dispose();
            }
        });
        stage.addActor(gameInstructions);
        
        TextButton scoreBoard = new TextButton("Score Board", skin);
        scoreBoard.setPosition(Gdx.graphics.getWidth() / 2 - scoreBoard.getWidth() / 2, Gdx.graphics.getHeight() / 2 -verticalPos);
        scoreBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new Scoreboard(game));
                getThisClass().dispose();
            }
        });
        stage.addActor(scoreBoard);
        
        TextButton  settings= new TextButton("Settings", skin);
        settings.setPosition(Gdx.graphics.getWidth() / 2 - settings.getWidth() / 2, Gdx.graphics.getHeight() / 2 - (startGame.getHeight() + buttonOffset) -verticalPos);
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new Setting(game));
                getThisClass().dispose();
            }
        });
        stage.addActor(settings);
        
        TextButton exit = new TextButton("Exit", skin);
        exit.setPosition(Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 2*(startGame.getHeight() + buttonOffset) -verticalPos);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }
        });
        stage.addActor(exit);
    }
    
    @Override
    public void render(float dt) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       game.batch.begin();  
            game.batch.draw(background, 0, 0, width, height);
       game.batch.end();
       
      // stage.act();
       stage.draw();
    }
    
    private MainMenu getThisClass(){
        return this;
    }
    
    @Override
    public void show() {
    }

    @Override
    public void resize(int arg0, int arg1) {
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
        background.dispose();
    }
    
}
