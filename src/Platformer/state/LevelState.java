/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.state;

import Platformer.PlatformerGame;
import Platformer.character.Player;
import Platformer.controller.MouseAndKeyBoardPlayerController;
import Platformer.controller.PlayerController;
import Platformer.level.Level;
import Platformer.level.Objective;
import Platformer.physics.Physics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
/**
 *
 * @author vikto
 */
public class LevelState extends BasicGameState{
    
    Level level;
    String startingLevel;
    int ID;
    
    private Player player;
    private PlayerController playerController;
    
    private Physics physics;
    
    Input input;
    
    public LevelState(String startingLevel, int ID){
        this.startingLevel = startingLevel;
        this.ID = ID;
    }
    
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        
        player = new Player(128, 415);
        level = new Level(startingLevel, player);

        playerController = new MouseAndKeyBoardPlayerController(player);
        physics = new Physics();
    }
    
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException{
        playerController.handleInput(container.getInput(), delta);
        physics.handlePhysics(level, delta);
        if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)){
            sbg.enterState(PlatformerGame.MAINMENU, new FadeOutTransition(), new FadeInTransition());
        }
        if(physics.getCollected() >= level.getNumberOfObjects()){
            
            sbg.enterState(PlatformerGame.MAINMENU, new FadeOutTransition(), new FadeInTransition());
        }
        

    }
    
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException{
        g.scale(PlatformerGame.SCALE, PlatformerGame.SCALE);
        level.render();
    }
    
    public int getID(){
        return ID;
    }
    
}
