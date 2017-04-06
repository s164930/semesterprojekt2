/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.character;

import Platformer.enums.Facing;
import Platformer.physics.AABoundingRect;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author vikto
 */
public class Player extends Character {

    public Player(float x, float y) throws SlickException {
        super(x, y);
        setSprite(new Image("data/img/sprite_platformer/movingright1.png"));

        setMovingAnimation(new Image[]{new Image("data/img/sprite_platformer/movingright1.png"), 
                           new Image("data/img/sprite_platformer/movingright2.png")}, 125);
        
        boundingShape = new AABoundingRect(x+3, y, 26, 32);
        
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }

    public void updateBoundingShape(){
        boundingShape.updatePosition(x+3, y);
    }
}
