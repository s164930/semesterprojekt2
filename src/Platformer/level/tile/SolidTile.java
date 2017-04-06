/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.level.tile;

import Platformer.physics.AABoundingRect;

/**
 *
 * @author vikto
 */
public class SolidTile extends Tile {
    
    public SolidTile(int x, int y){
        super(x,y);
        boundingShape = new AABoundingRect(x*32, y*32, 32, 32);
    }
}
