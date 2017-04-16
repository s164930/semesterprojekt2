/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.physics;

import Platformer.PlatformerGame;
import Platformer.level.Level;
import Platformer.level.LevelObject;
import Platformer.level.tile.Tile;
import java.util.ArrayList;
import Platformer.character.Character;
import Platformer.character.Player;
import Platformer.level.Objective;

/**
 *
 * @author vikto
 */
public class Physics {
    private final float gravity = 0.0015f;
    public int collected = 0;
    
    public void handlePhysics(Level level, int delta){
        handleCharacters(level,delta);
        handleLevelObjects(level, delta);
    }
    
    public int getCollected(){
        return collected;
    }
    
    private boolean checkCollision(LevelObject obj, Tile[][] mapTiles){
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles){
            if(t.getBoundingShape() != null){
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isOnGround(LevelObject obj, Tile[][] mapTiles){
        // får blokkende lige under karakteren
        ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
        // vi sænker bounding boxen en lille smule så vi kan se om vi faktisk er lidt over jorden
        obj.getBoundingShape().movePosition(0, 1);
        
        for (Tile t : tiles){
            // det er ikke hver blok der har en bounding shape (air tiles fx)
            if(t.getBoundingShape() != null){
                // hvis jorden og det sænkede objekt rører hinanden så er vi på jorden.
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())){
                    // vi rykker lige bounding shape tilbage
                    obj.getBoundingShape().movePosition(0, -1);
                    return true;
                }
            }
        }
        obj. getBoundingShape().movePosition(0, -1);
        return false;
    }
        
    private void handleCharacters(Level level, int delta){
        for (Character c : level.getCharacters()){
            if(!c.isMoving()){
                c.decelerate(delta);
            }
            handleGameObject(c,level,delta);
            
            if(c instanceof Player){
                ArrayList<LevelObject> removeQueue = new ArrayList<LevelObject>();
                
                for(LevelObject obj : level.getLevelObjects()){
                    
                    if(obj instanceof Objective){
                        if(obj.getBoundingShape().checkCollision(c.getBoundingShape())){
                            collected++;
                            removeQueue.add(obj);
                        }
                    }
                }
                level.removeObjects(removeQueue);
            }
        }
    }
    
    private void handleLevelObjects(Level level, int delta){
        for(LevelObject obj : level.getLevelObjects()){
            handleGameObject(obj, level, delta);
        }
    }
    
    private void handleGameObject(LevelObject obj, Level level, int delta){
        //opdater om objektet er på jorden
        obj.setOnGround(isOnGround(obj, level.getTiles()));
        
        // påfør tyngdekraft hvis objektet ikke er på jorden, eller hvis objektet er ved at hoppe
        if(!obj.isOnGround() || obj.getYVelocity() < 0){
            obj.applyGravity(gravity*delta);
        } else {
            obj.setYVelocity(0);
        }
        
        // udregn hvor mange pixels vi faktisk skal flytte os
        float x_movement = obj.getXVelocity()*delta;
        float y_movement = obj.getYVelocity()*delta;
        
        float step_x = 0;
        float step_y = 0;
        
        if(x_movement != 0){
            step_y = Math.abs(y_movement)/Math.abs(x_movement);
            if(y_movement < 0){
                step_y = -step_y;
            }
            
            if(x_movement > 0){
                step_x = 1;
            } else {
                step_x = -1;
            }
            
            if((step_y > 1 || step_y < -1) && step_y != 0){
                step_x = Math.abs(step_x)/Math.abs(step_y);
                if(x_movement < 0){
                    step_x = -step_x;
                }
                if(y_movement < 0){
                    step_y = -1;
                } else {
                    step_y = 1;
                }
            }
        } else if(y_movement != 0){
            // hvis vi kun har vertikal bevægelse, kan vi bruge et step af 1
            if(y_movement > 0){
                step_y = 1;
            } else {
                step_y = -1;
            }
        }
        
        // bevæg en pixel ad gangen indtil vi er færdige med at bevæge os
        while (x_movement != 0 || y_movement != 0){
            
            // først bevæger vi os i horizontal retning
            if(x_movement != 0){
                // når vi går et step, skal vi opdatere hvor meget vi skal bevæge os efter dette
                if((x_movement > 0 && x_movement < step_x) || (x_movement > step_x && x_movement < 0)){
                    step_x = x_movement;
                    x_movement = 0;
                } else {
                    x_movement -= step_x;
                }
                
                //så bevæger vi objektet 1 step
                obj.setX(obj.getX()+step_x);
                
                // hvis vi kolliderer med en eller anden bounding shape, skal vi returnere 1 step
                if(checkCollision(obj,level.getTiles())){
                    // annuller vores step, og set velocity til 0
                    obj.setX(obj.getX()-step_x);
                    obj.setXVelocity(0);
                    x_movement = 0;
                }
            }
            
            if (y_movement != 0){
                if((y_movement > 0 && y_movement < step_y) || (y_movement > step_y && y_movement < 0)){
                    step_y = y_movement;
                    y_movement = 0;
                } else {
                    y_movement -= step_y;
                }
                
                obj.setY(obj.getY()+step_y);
                
                if(checkCollision(obj, level.getTiles())){
                    obj.setY(obj.getY()-step_y);
                    obj.setYVelocity(0);
                    y_movement = 0;
                    break;
                }
            }
        }
    }

}
