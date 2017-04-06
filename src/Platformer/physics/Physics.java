/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.physics;

import Platformer.level.Level;
import Platformer.level.LevelObject;
import Platformer.level.tile.Tile;
import java.util.ArrayList;
import Platformer.character.Character;

/**
 *
 * @author vikto
 */
public class Physics {
    private final float gravity = 0.0015f;
    
    public void handePhysics(Level level, int delta){
        handleCharacters(level,delta);
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
            
        }
    }

}
