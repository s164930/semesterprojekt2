/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.character;

import Platformer.enums.Facing;
import Platformer.level.LevelObject;
import java.util.HashMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author vikto
 */
public abstract class Character extends LevelObject {
    
    protected Facing facing;
    protected HashMap<Facing, Image> sprites;
    protected HashMap<Facing, Animation> movingAnimations;
    protected long lastTimeMoved;
    
    protected boolean moving = false;
    
    protected float accelerationSpeed = 1, decelerationSpeed = 1, maximumSpeed = 1;
    
    
    public Character(float x, float y) throws SlickException{
        super(x,y);
        setSprite(new Image("data/img/sprite_platformer/movingright1.png"));
        facing = Facing.RIGHT;
    }
    
    
    public void render(float offset_x, float offset_y){
        // updaterer kun sprite når man bevæger sig
        if(movingAnimations != null && moving){
            movingAnimations.get(facing).draw(x-2-offset_x, y-2-offset_y);
        } else{
            sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);
        }
        
    }
    
    protected void setSprite(Image i){
        sprites = new HashMap<Facing,Image>();
        sprites.put(Facing.RIGHT, i);
        // flipper billedet horisontalt men ikke vertikalt
        sprites.put(Facing.LEFT, i.getFlippedCopy(true, false));
    }
    
    protected void setMovingAnimation(Image[] images, int frameDuration){
        movingAnimations = new HashMap<Facing, Animation>();
        
        movingAnimations.put(Facing.RIGHT, new Animation(images, frameDuration));
        
        Animation facingLeftAnimation = new Animation();
        for (Image i : images){
            facingLeftAnimation.addFrame(i.getFlippedCopy(true, false), frameDuration);
        }
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);
    }
    
    public boolean isMoving(){
        return moving;
    }
    
    public void setMoving(boolean b){
        moving = b;
    }
    
    public void decelerate(int delta){
        if(x_velocity > 0){
            x_velocity -= decelerationSpeed * delta;
            if(x_velocity < 0){
                x_velocity = 0;
            }
        } else if(x_velocity < 0){
            x_velocity += decelerationSpeed *delta;
            if(x_velocity > 0){
                x_velocity = 0;
            }
        }
    }
    
    public void jump(){
        if(onGround){
            y_velocity = -0.4f;
        }
    }
    /*
    de to move funktioner har omvendt sammenligninger fordi det kun går ud fra x-aksen,
    der selvfølgelig bliver mindre når man går mod venstre.
    */
    public void moveLeft(int delta){
        // hvis vi ikke allerede er på max hastighed
        if(x_velocity > -maximumSpeed){
            x_velocity -= accelerationSpeed * delta;
            // hvis vi er over max speed så sæt til max speed
            if(x_velocity < -maximumSpeed){
                x_velocity = -maximumSpeed;
            }
        }
        moving = true;
        facing = Facing.LEFT;
    }
    
    public void moveRight(int delta){
    // hvis vi ikke allerede er på max hastighed
        if(x_velocity < maximumSpeed){
            x_velocity += accelerationSpeed * delta;
            // hvis vi er over max speed så sæt til max speed
            if(x_velocity > maximumSpeed){
                x_velocity = maximumSpeed;
            }
        }
        moving = true;
        facing = Facing.RIGHT;
    }
    
    
}
