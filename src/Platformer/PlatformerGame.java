package Platformer;

import Platformer.level.Level;
import Platformer.state.LevelState;
import Platformer.character.Character;
import Platformer.state.MainMenu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
/**
 *
 * @author vikto
 */
public class PlatformerGame extends StateBasedGame {
    public static final int MAINMENU = 0;
    public static final int LEVEL = 1;
    
    // sætter vindue størrelsen og højden i forhold til aspect ratio af skærmen
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    public static final boolean FULLSCREEN = false;
    public static final boolean FPS = false;
    public static String startingLevel = "level_0";
    
    /*
    1280x720 er standarden, derfor skalerer vi tiles der er 32x32 store, men vi vil gerne have dem til at være
    40x40 i 1280x720, derfor skalerer vi med 1,25.
    */
    public static final float SCALE = (float)(1.25*((double)WINDOW_WIDTH/1280));
    public static final String GAME_NAME = "Platformer";
    
    public PlatformerGame(){
        super(GAME_NAME);
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MainMenu());
        this.addState(new LevelState(startingLevel));
        this.enterState(MAINMENU);
    }
    
    public static void main(String[] args) throws SlickException{
        AppGameContainer app = new AppGameContainer(new PlatformerGame());
        
        app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN);
        
        app.setTargetFrameRate(60);
        app.setShowFPS(FPS);
        
        app.start();
    }
    
    
}
