
package StartUp;

import com.Pacman.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class Setting implements Screen{

    private Main game;
    private Texture background;
    private Stage stage;
    private Table table;
    private Label displayOpt, soundOpt, themeOpt, difficulityOpt;
    private TextButton defaultDisplay, landscapeDisplay, fullscreenDisplay; // Display Screen
    private TextButton unmuteSounds, muteSounds; // Sounds
    private TextButton defaultTheme, dreamerTheme;  // Themes
    private TextButton normalDifficulity, hardDifficulity; // Difficulity
    private ButtonGroup displayGroup, soundGroup, themeGroup, difficulityGroup; // For one click buttons
    private TextButton applyChanges, backToMenu;
    private BitmapFont font;
    private Skin skin;
    private Image image, image2;
    private TextureRegion region;
    private int width, height;
    
    public Setting(Main game) {
        this.game = game;
        
        if (MainMenu.fromScreen){
            width=305;
            height=420;
        }
        else {
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();
        }
        
        creatingPropperInstances();
        
        // LABELS
        createLabels();
        
        // BUTTONS
        createButtons();
        
        // TABLE
         addingDataToTable();
         
          applyChanges.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                saveData();
                checkSettings();
                Gdx.graphics.setWindowedMode(Main.width, Main.height);
                
                getThisClass().game.setScreen(new MainMenu(getThisClass().game));
                getThisClass().dispose();
            }
        });
          backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                getThisClass().game.setScreen(new MainMenu(getThisClass().game));
                getThisClass().dispose();
            }
        });
         
        stage.addActor(table);
    }
    private void creatingPropperInstances(){
        background = new Texture("Sprites\\Space Background.jpg");
        font = new BitmapFont(Gdx.files.internal("Fonts\\score.fnt"));
        region = new TextureRegion(new Texture("Sprites\\Themes.png"), 0, 0, 237, 238);
        image = new Image(); image2 = new Image();
        image.setDrawable(new TextureRegionDrawable(region));
        region = new TextureRegion(new Texture("Sprites\\Themes.png"), 237, 0, 237, 238);
        image2.setDrawable(new TextureRegionDrawable(region));
        createBasicSkin();
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
    }
    
    private void createBasicSkin() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts\\settings.fnt"));
        skin = new Skin();
        skin.add("default", font);
        
        // CREATE A TEXTURE
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 8, (int) Gdx.graphics.getHeight() / 14, Pixmap.Format.RGB888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        skin.add("Background", new Texture(pixmap));
        
        // CREATE A BUTTON STYLE
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("Background", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("Background", Color.YELLOW);
        textButtonStyle.checked = skin.newDrawable("Background", Color.GREEN);
        textButtonStyle.over = skin.newDrawable("Background", Color.WHITE);  // HOVER
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
    
    private void createLabels(){
        displayOpt = new Label (" Screen Display:", new Label.LabelStyle(font, null));
        soundOpt = new Label("Sounds: ", new Label.LabelStyle(font, null));
        themeOpt = new Label("Theme: ", new Label.LabelStyle(font, null));
        difficulityOpt = new Label("Difficulity: ", new Label.LabelStyle(font, null));
    }
    
    private void createButtons() {
        defaultDisplay = new TextButton(" 600 X 600 (DEFAULT)  ", skin);
        landscapeDisplay = new TextButton(" 1000 X 600 (LANDSCAPE)  ", skin);
        fullscreenDisplay = new TextButton(" max width X max height (FULLSCREEN)  ", skin);
        
        displayGroup = new ButtonGroup(defaultDisplay, landscapeDisplay, fullscreenDisplay);
        displayGroup.setMaxCheckCount(1);
        displayGroup.setMinCheckCount(0);
        
        defaultDisplay.setChecked(true);
        
        unmuteSounds = new TextButton(" Unmute  ", skin);
        muteSounds = new TextButton(" Mute  ", skin);
        
        soundGroup = new ButtonGroup(unmuteSounds, muteSounds);
        soundGroup.setMaxCheckCount(1);
        soundGroup.setMinCheckCount(0);
        
        unmuteSounds.setChecked(true);
        
        defaultTheme = new TextButton(" Default Theme  ", skin);
        dreamerTheme = new TextButton(" Dreamer Theme  ", skin);
        
        themeGroup = new ButtonGroup(defaultTheme, dreamerTheme);
        themeGroup.setMaxCheckCount(1);
        themeGroup.setMinCheckCount(0);
        
        defaultTheme.setChecked(true);
        
        normalDifficulity = new TextButton(" Normal  ", skin);
        hardDifficulity = new TextButton(" Hard  ", skin);
        
        difficulityGroup = new ButtonGroup(normalDifficulity, hardDifficulity);
        difficulityGroup.setMaxCheckCount(1);
        difficulityGroup.setMinCheckCount(0);
        
        normalDifficulity.setChecked(true);
        
        applyChanges = new TextButton(" Click Here To Apply Changes  ", skin);
        backToMenu = new TextButton(" Back To Main Menu  ", skin);
    }
    
    private void addingDataToTable(){
        table.add(displayOpt).expandX().padBottom(30);    // DISPLAY OPTIONS
        table.add(defaultDisplay).bottom().right().padBottom(30);
        table.add(landscapeDisplay).bottom().left().padBottom(30);
        table.row();
        table.add(soundOpt).expandX().padBottom(30);   // SOUND OPTIONS
        table.add(unmuteSounds).bottom().right().padBottom(30);
        table.add(muteSounds).bottom().left().padBottom(30);
        table.row();
        table.add(themeOpt).expandX(); // THEME OPTIONS
        table.add(defaultTheme).bottom().right();
        table.add(dreamerTheme).bottom().left();
        table.row();
        table.add().expandX();
        table.add(image).expandX().right();
        table.add(image2).expandX().left();
        table.row();
        table.add(difficulityOpt).expandX().padTop(20); // DIFICULTY OPTIONS
        table.add(normalDifficulity).bottom().right();
        table.add(hardDifficulity).bottom().left();
        table.row();
        table.add().expandX();
        table.add(applyChanges).expandX().padTop(30);
        table.add(backToMenu).expandX().padTop(30);
    }
    
    private void saveData(){
         try {
            FileWriter writer = new FileWriter("..\\core\\assets\\settings.txt", false);
            if (landscapeDisplay.isChecked())
                writer.write("L");
            else 
                writer.write("D");
            if (muteSounds.isChecked())
                writer.write("M");
            else 
                writer.write("U");
            if (dreamerTheme.isChecked())
                writer.write("R");
            else 
                writer.write("D");
            if (hardDifficulity.isChecked())
                writer.write("H");
            else 
                writer.write("N");
            writer.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private  void checkSettings() {
         try {
            FileReader reader = new FileReader("..\\core\\assets\\settings.txt");
            int c; boolean firstIteration = true;
            while ( (c = reader.read()) != -1){
                if ( (char)c == 'D' && firstIteration){ //DEFAULT DISPLAY
                    game.width = 600;
                    game.height = 600;
                }
                else if ( (char)c == 'L' ){ //LANDSCAPE DISPLAY
                    game.width = 1000;
                    game.height = 600;
                }
                else if ( (char)c == 'U' ){ //UNMUTE SOUNDS
                    game.mute = false;
                }
                else if ( (char)c == 'M' ){ //MUTE SOUNDS
                    game.mute = true;
                }
                else if ( (char)c == 'D' ){ //DEFAULT THEME
                    game.mapTheme = "Map//Map (1-1).tmx";
                }
                else if ( (char)c == 'R' ){ //DREAMER THEME
                    game.mapTheme = "Map//Map (1-2).tmx";
                }
                else if ( (char)c == 'N' ){ //NORMAL DIFFICULITY
                    // SPEED OF GHOSTS = 1
                }
                else if ( (char)c == 'H' ){ //HARD DIFFICULITY
                    // SPEED OF GHOSTS = 1.5
                }
                firstIteration = false;
            }
            reader.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private Setting getThisClass(){
        return this;
    }
    
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
            game.batch.draw(background, 0, 0, width, height);
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
        background.dispose();
        font.dispose();
        region.getTexture().dispose();
    }
    
}
