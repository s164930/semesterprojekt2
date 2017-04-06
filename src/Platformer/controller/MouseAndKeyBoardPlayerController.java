/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.controller;

import Platformer.character.Player;

import org.newdawn.slick.Input;
/**
 *
 * @author vikto
 */
public class MouseAndKeyBoardPlayerController extends PlayerController{
    
    public MouseAndKeyBoardPlayerController(Player player){
        super(player);
    }
    
    public void handleInput(Input i, int delta){
        handleKeyboardInput(i, delta);
    }
    
    private void handleKeyboardInput(Input i, int delta){
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)){
            player.moveLeft(delta);
        } else if (i.isKeyDown(Input.KEY_D)|| i.isKeyDown(Input.KEY_RIGHT)){
            player.moveRight(delta);
        } else {
            player.setMoving(false);
        }
        
        if(i.isKeyDown(Input.KEY_SPACE)){
            player.jump();
        }
    }
    
}
