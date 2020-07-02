package com.Launcher.desktop;

import StartUp.MainMenu;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Pacman.game.Main;
import java.io.FileReader;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class DesktopLauncher {
    public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                checkSettings();
                checkScores();
                config.title = "Pacman";
                config.width = Main.width;
                config.height = Main.height;
                config.y = 50;
                config.fullscreen = false;
            new LwjglApplication(new Main(), config);
    }
    
    private static void checkSettings() {
         try {
            FileReader reader = new FileReader("..\\core\\assets\\settings.txt");
            int c; boolean firstIteration = true;
            while ( (c = reader.read()) != -1){
                if ( (char)c == 'D' && firstIteration){ //DEFAULT DISPLAY
                    Main.width = 600;
                    Main.height = 600;
                    MainMenu.landscape = false;
                }
                else if ( (char)c == 'L' ){ //LANDSCAPE DISPLAY
                    Main.width = 1000;
                    Main.height = 600;
                    MainMenu.landscape = true;
                }
                else if ( (char)c == 'U' ){ //UNMUTE SOUNDS
                    Main.mute = false;
                }
                else if ( (char)c == 'M' ){ //MUTE SOUNDS
                    Main.mute = true;
                }
                else if ( (char)c == 'D' ){ //DEFAULT THEME
                    Main.mapTheme = "Map//Map (1-1).tmx";
                }
                else if ( (char)c == 'R' ){ //DREAMER THEME
                    Main.mapTheme = "Map//Map (1-2).tmx";
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
    private static void checkScores(){
        try{
            FileReader reader = new FileReader("..\\core\\assets\\scoresheet.txt");
            int c, cnt=0, COUNT=0, score[]=new int[10];
            String txt="";
            boolean readScore=false;
            
            while ((c=reader.read()) != -1){
                if (cnt > 1){
                    if(readScore&&(char)c=='-'){
                        readScore=false;
                        score[COUNT]=Integer.valueOf(txt);
                        txt=""; 
                        COUNT++;
                    }
                    else if ((char)c == '-'){
                        readScore=true;
                        continue;
                    }
                    if (readScore)
                        txt += (char)c;
                }
                cnt++;
            }
            Arrays.sort(score);
            Main.highscore=score[9];
            
            reader.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
