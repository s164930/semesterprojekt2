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
/**
 *
 * @author vikto
 */
public class LevelState extends BasicGameState{
    
    Level level;
    String startingLevel;
    
    private Player player;
    private PlayerController playerController;
    
    private Physics physics;
    
    public LevelState(String startingLevel){
        this.startingLevel = startingLevel;
    }
    
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        
        player = new Player(128, 415);
        level = new Level(startingLevel, player);
        
        level.addLevelObject(new Objective(128, 315));
        level.addLevelObject(new Objective(528, 280));
        level.addLevelObject(new Objective(328, 615));
        
        
        playerController = new MouseAndKeyBoardPlayerController(player);
        physics = new Physics();
    }
    
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException{
        playerController.handleInput(container.getInput(), delta);
        physics.handlePhysics(level, delta);
    }
    
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException{
        g.scale(PlatformerGame.SCALE, PlatformerGame.SCALE);
        level.render();
    }
    
    public void keyPressed(int key, char code){
        if(key == Input.KEY_ESCAPE){
            System.exit(0);
        }
    }
    
    public int getID(){
        return 0;
    }
    
}
