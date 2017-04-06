/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.physics;

import Platformer.level.tile.Tile;
import java.util.ArrayList;

/**
 *
 * @author vikto
 */
public class AABoundingRect extends BoundingShape {
    
    public float x, y, width, height;
    
    public AABoundingRect(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void updatePosition(float newX, float newY){
        this.x = newX;
        this.y = newY;
    }
    
    public void movePosition(float x, float y){
        this.x += x;
        this.y += y;
    }
    /*
    Denne funktion tjekker om spillerens rektangel er udefra den givne rektangel, dette gør den ved
    nogle forskellige logiske konklusioner, som fx at hvis spillerens højeste punkt er under den givne rektangels laveste punkt
    kan de to ikke røre hinanden.
    */
    public boolean checkCollision(AABoundingRect rect){
        return !(rect.x > this.x+width || rect.x+rect.width < this.x || rect.y > this.y+height || rect.y+rect.height < this.y);
    }
    
    public ArrayList<Tile> getTilesOccupying(Tile[][] tiles){
        ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();
        
        /*
        %32 er fordi karakteren godt kan stå i mellem 2 kasser, og dermed vil den tjekke den forkerte hvis
        man bare ganger positionen med 32, derfor runder vi op til et multiplum af 32, så dette ikke sker.
        */
        for (int i = (int)x; i <= x+width+(32-width%32); i += 32){
            for (int j = (int)y; j <= y+height+(32-height%32); j += 32){
                occupiedTiles.add(tiles[i/32][j/32]);
            }
        }
        return occupiedTiles;
    }
    
    public ArrayList<Tile> getGroundTiles(Tile[][] tiles){
        ArrayList<Tile> tilesUnderneath = new ArrayList<Tile>();
        int j = (int) (y+height+1);
        
        for (int i = (int)x; i <= x+width+(32-width%32); i += 32){
            tilesUnderneath.add(tiles[i/32][j/32]);
        }
        return tilesUnderneath;
    }
}
