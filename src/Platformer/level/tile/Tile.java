/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.level.tile;

import Platformer.physics.BoundingShape;

/**
 *
 * @author vikto
 */
public class Tile {
    
    protected int x, y;
    protected BoundingShape boundingShape;
    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        boundingShape = null;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public BoundingShape getBoundingShape(){
        return boundingShape;
    }
}
