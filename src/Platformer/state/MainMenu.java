/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.state;

import Platformer.PlatformerGame;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Viktor Vase Frandsen
 */
public class MainMenu extends BasicGameState{
    
    public static final int ID = 0;
    private int playerChoice = 0;
    private static final int NOCHOICES = 2;
    private static final int START = 0;
    private static final int QUIT = 1;
    private String[] playersOptions = new String[NOCHOICES];
    private boolean exit = false;
    private Font font;
    private TrueTypeFont playersOptionsTTF, foo;
    private Color chosen = new Color(153, 204, 255);
    public MainMenu(){
        
    }
    
    
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        font = new Font("Verdana", Font.BOLD, 40);
        playersOptionsTTF = new TrueTypeFont(font, true);
        font = new Font("Verdana", Font.PLAIN, 20);
        foo = new TrueTypeFont(font, true);
        playersOptions[0] = "Start";
        playersOptions[1] = "Quit";
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        renderPlayerOptions();
        if(exit){
            gc.exit();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_DOWN)){
            if(playerChoice == (NOCHOICES -1)){
                playerChoice = 0;
            } else {
                playerChoice++;
            }
        }
        if(input.isKeyPressed(Input.KEY_UP)){
            if(playerChoice == 0){
                playerChoice = NOCHOICES - 1;
            } else {
                playerChoice--;
            }
        }
        if(input.isKeyPressed(Input.KEY_ENTER)){
            switch(playerChoice){
                case QUIT:
                    exit = true;
                    break;
                case START:
                    sbg.getState(PlatformerGame.LEVEL).init(gc, sbg);
                    sbg.enterState(PlatformerGame.LEVEL, new FadeOutTransition(), new FadeInTransition());
                    break;
            }
        }
    }
    
    private void renderPlayerOptions(){
        for(int i = 0; i < NOCHOICES; i++){
            if(playerChoice == i){
                playersOptionsTTF.drawString(PlatformerGame.WINDOW_WIDTH/2 - 70, i * 50 + 200, playersOptions[i], chosen);
                
            } else {
                playersOptionsTTF.drawString(PlatformerGame.WINDOW_WIDTH/2 - 70, i * 50 + 200, playersOptions[i]);
            }
        }
    }
    
}
