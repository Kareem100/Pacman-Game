
package Scenes;

import Levels_Screen.PlayScreen;
import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD implements Disposable{
    
    public Stage stage;
    private Viewport viewport;
    
    private int worldTimer;
    private static int score;
    private int highScore;
    private static int lifes;
    private int enemyUnleash; 
    private float stateTimer;
    private boolean inkyOut, blinkyOut;
    private Image image;
    private TextureRegion region;
    
    private Label countdownVar;
    private Label scoreVar;
    private Label highscoreVar;
    public Label readyVar;
    private Label countdownText;
    private Label scoreText;
    private Label highscoreText;
    private Label unleashVar;
    private Label unleashText;
    
    public HUD(SpriteBatch batch){
        highScore=Main.highscore;
        worldTimer = 100;
        score = 0;
        lifes = 3;
        enemyUnleash = 3;
        inkyOut = blinkyOut = false;
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table topTable = new Table();
        topTable.top();
        topTable.setFillParent(true);
         
        BitmapFont FONT = new BitmapFont(Gdx.files.internal("Fonts\\pacFont.fnt"));
        BitmapFont FONT2 = new BitmapFont(Gdx.files.internal("Fonts\\numbers.fnt"));
        BitmapFont FONT4 = new BitmapFont(Gdx.files.internal("Fonts\\3Counter.fnt"));
        scoreText = new Label("SCORE", new Label.LabelStyle(FONT, null));
        highscoreText = new Label("HIGH SCORE", new Label.LabelStyle(FONT, null));
        countdownText = new Label("TIMER", new Label.LabelStyle(FONT, null));
        scoreVar = new Label(String.format("%04d", score), new Label.LabelStyle(FONT2, null));
        highscoreVar = new Label(String.format("%04d", highScore), new Label.LabelStyle(FONT2, null));
        countdownVar = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(FONT2, null));
        readyVar = new Label(String.format("%01d", 3), new Label.LabelStyle(FONT4, null));

        topTable.add(scoreText).expandX().padTop(10f);
        topTable.add(highscoreText).expandX().padTop(10f);
        topTable.add(countdownText).expandX().padTop(10f);
        topTable.row();
        topTable.add(scoreVar).expandX().padTop(2f);
        topTable.add(highscoreVar).expandX().padTop(2f);
        topTable.add(countdownVar).expandX().padTop(2f);
        
        Table bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.setFillParent(true);
        
        image = new Image();
        region = new TextureRegion(new Texture("Sprites\\pacLifes.png"), 0, 0, lifes * 28, 25);
        image.setDrawable(new TextureRegionDrawable(region));
        
        BitmapFont FONT3 = new BitmapFont(Gdx.files.internal("Fonts\\unleash.fnt"));
        unleashText = new Label("Unleash Blinky After: ", new Label.LabelStyle(FONT3, null));
        unleashVar = new Label(String.format("%02d", enemyUnleash), new Label.LabelStyle(FONT3, null));
        
        bottomTable.add().expandX().padTop(2f);
        bottomTable.add(image).expandX().padBottom(6f).center();
        bottomTable.add().expandX().padTop(2f);
        bottomTable.add(unleashText).expandX().padBottom(8f).right();
        bottomTable.add(unleashVar).expandX().padBottom(8f).left();
        
        readyVar.setPosition(Gdx.graphics.getWidth()/4-30, Gdx.graphics.getHeight()/4+10);
        
        stage.addActor(readyVar);
        stage.addActor(topTable);
        stage.addActor(bottomTable);
    }
    
    public void update(float dt){
        stateTimer += dt;
        if (stateTimer > 1){
            worldTimer--;
            if (enemyUnleash>=0){
                enemyUnleash--;
                if (enemyUnleash == -1){
                    enemyUnleash = 6;
                    unleashText.setText("Unleash Inky After: ");
                    if (blinkyOut)inkyOut = true;
                    blinkyOut = true;
                }
            }
                if (inkyOut && blinkyOut){
                    enemyUnleash=0;
                    unleashText.setText("ALL UNLEASHED");
                    unleashVar.setVisible(false);
                }
            stateTimer=0;
        }
        //UPDATE HUD VARIABLES
        scoreVar.setText(String.format("%4d",score));
        countdownVar.setText(String.format("%3d",worldTimer));
        if(score>highScore)
            highscoreVar.setText(String.format("%3d",score));
        if(unleashVar.isVisible())
            unleashVar.setText(String.format("%2d",enemyUnleash));
        if (worldTimer==0)PlayScreen.GameOver = true;
        
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Sprites\\pacLifes.png"), 0, 0, lifes * 28, 25)));
        }
    
        public static void  addScore(float newScore){
            score += newScore;
        }
        public static int getScore(){
            return score;
        }
        public static void lossesLife(){
            lifes--;
            if (lifes==0)
                PlayScreen.GameOver = true;
        }

    @Override
    public void dispose() {
        stage.dispose();
        region.getTexture().dispose();
    }
            
    }
    
    

