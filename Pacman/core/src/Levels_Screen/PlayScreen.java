
package Levels_Screen;

import Scenes.GameOver;
import Scenes.HUD;
import Scenes.NextLevel;
import Scenes.PopUpDialogBox;
import Sprites.Blinky;
import Sprites.Inky;
import Sprites.Pacman;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.Pacman.game.Main;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayScreen implements Screen{
    //Reference To The Game, Used To Set Screens
    private Main game;
    
    // ATLAS
    private TextureAtlas atlas;
    
    //Basic PlayScreen Variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private HUD hud;
    private Skin skin;
    private PopUpDialogBox dialog;
    private Stage dialogStage;
    
    //Tiled Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
    //Box2D Variables
    private World world;
    private Box2DDebugRenderer debuger;
    private B2WorldCreator creator;
    private Pacman player;
    private Blinky blinky;
    private Inky inky;
    
    //Helping variables
    public static boolean GameOver;
    public boolean isReady;
    public boolean isPaused;
    public boolean isDead;
    public boolean isWon;
    public boolean isKey;
    public boolean once;
    public boolean drawn;
    public float delta;
    public float ghostsDelta;
    private float timer, seconds;
    private int readyTimer;
    
    
    //Sounds
    public Music death;
    
    public PlayScreen(Main game){
        
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(game.V_WIDTH/Main.PPM, game.V_HEIGHT /Main.PPM, gameCam);
        hud = new HUD(game.batch);
        
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(this.game.mapTheme);
        renderer = new OrthogonalTiledMapRenderer(map, 1/Main.PPM);
        
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);
        world = new World(new Vector2(0, 0), true);
        debuger = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        
        skin = new Skin(Gdx.files.internal("Sprites\\uiskin.json"));
        Gdx.input.setInputProcessor(dialogStage = new Stage());
        dialog = new PopUpDialogBox("Confirm Exit", skin, this);
        
        atlas = new TextureAtlas("Sprites//PacmanAssets.pack");
        player = new Pacman(this);
        blinky = new Blinky(this, 168f, 208f);
        inky = new Inky(this, 136f, 208f);
        world.setContactListener(new WorldContactListener());
        
        GameOver = isReady = isPaused = isDead = isWon = isKey = once = drawn =false;
        delta = ghostsDelta = readyTimer = 0; 
        
        death = game.manager.get("Sounds//Death.wav", Music.class);
        death.setLooping(false);
    }
    
    public World getWorld(){
        return world;
    }

    public TiledMap getMap() {
        return map;
    }
    
    public TextureAtlas getAtlas(){
        return atlas;
    }

    public Main getGame() {
        return game;
    }
    
    public void update(float dt){
        
        //1 Step in the physics simulation is (60 times per second)
        world.step(1/60f, 6, 2);
        // Update Characters
        if (!isKey || ghostsDelta > 10){
            blinky.update(dt);
            inky.update(dt);
            isKey=false;
            ghostsDelta = 0;
        }
        else ghostsDelta += dt;
        player.update(dt);
        
        // HUD & CREATOR
        hud.update(dt);
        creator.showKey(dt);
        
        // GAME CAMERA
        renderer.setView(gameCam);
        gameCam.update();
    }
    
    private void gameState(float dt, float delta) {
        seconds+=dt;
        if (this.isWon){
            game.setScreen(new NextLevel(game));
            this.dispose();
        }
        if (this.GameOver){
            if (!death.isPlaying()){
               game.setScreen(new GameOver(game));
                this.dispose();
            }
        }
        if (this.isPaused){
            if (!once){
                dialog.show(dialogStage);
                once =true;
                drawn=true;
            }
            dt = 0;
        }
        if (this.isDead){
            this.isPaused=true;
            once=true;
            player.update(delta);
            if (!death.isPlaying()){
                this.isPaused=false;
                this.isDead=false;
                once=false;
                player.currentState=player.currentState.EATING;
                player.body.setTransform(152f/game.PPM, 56/game.PPM, 0);
            }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if (!this.isPaused){
                dialog.setVisible(true);
                this.isPaused=true;
            }
            else {
                dialog.setVisible(false);
                dialog.result("no");
            }
        }
        timer += dt;
        if(timer>0.1f)  //TIME TO RENDER THE MAP
           isReady=false;
       else isReady=true;
       if(!isReady&&timer>3){
           isReady=true;
        hud.readyVar.setPosition(-1000, -1000);
       }
       else {
           if(seconds>1){
               hud.readyVar.setText(String.format("%1d",2-readyTimer));
               readyTimer++;
               seconds=0;
           }
        hud.readyVar.setPosition(Gdx.graphics.getWidth()/4-30, Gdx.graphics.getHeight()/4+10);
       }
       if(isReady)
            update(dt);
    }
    
    @Override
    public void render(float dt) {
       delta  = dt; 
       gameState(dt, delta);
       
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       
      renderer.render(); 
      debuger.render(world, gameCam.combined);
      
      game.batch.setProjectionMatrix(gameCam.combined);
      game.batch.begin();
      player.draw(game.batch);
      blinky.draw(game.batch);
      inky.draw(game.batch);
      game.batch.end();
      
      game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
      hud.stage.draw();
      
      dialogStage.act();
      dialogStage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
       gamePort.update(width, height); 
    }
    
    @Override
    public void show() {
       
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
       map.dispose();
       if(drawn)dialogStage.dispose();
       hud.dispose();
    }

}