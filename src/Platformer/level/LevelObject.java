/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.level;

import Platformer.physics.AABoundingRect;
import Platformer.physics.BoundingShape;

/**
 *
 * @author vikto
 */
public abstract class LevelObject {
    
    protected float x, y, x_velocity = 0, y_velocity = 0, maximumFallSpeed = 1;
    protected BoundingShape boundingShape;
    protected boolean onGround = true;
    
    public LevelObject(float x, float y){
        this.x = x;
        this.y = y;
        
        // default bound shape er 32x32 box
        boundingShape = new AABoundingRect(x,y,32,32);
    }
    
    public void applyGravity(float gravity){
        // hvis vi ikke allerede bevæger os ved max hastighed
        if(y_velocity < maximumFallSpeed){
            //accelerer
            y_velocity += gravity;
            if(y_velocity > maximumFallSpeed){
                // og hvis vi kommer over max fall speed, så set den til max fall speed
                y_velocity = maximumFallSpeed;
            }
        }
    }
    
    public float getYVelocity(){
        return y_velocity;
    }
    
    public float getXVelocity(){
        return x_velocity;
    }
    
    public void setXVelocity(float f){
        x_velocity = f;
    }
    
    public void setYVelocity(float f){
        y_velocity = f;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public void setY(float f){
        y = f;
        updateBoundingShape();
    }
    
    public void setX(float f){
        x = f;
        updateBoundingShape();
    }
    
    public void updateBoundingShape(){
        boundingShape.updatePosition(x, y);
    }
    
    public boolean isOnGround(){
        return onGround;
    }
    
    public void setOnGround(boolean b){
        onGround = b;
    }
    
    public BoundingShape getBoundingShape(){
        return boundingShape;
    }
}


