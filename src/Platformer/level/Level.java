/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.level;

import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import Platformer.character.Character;
import Platformer.level.tile.AirTile;
import Platformer.level.tile.SolidTile;
import Platformer.level.tile.Tile;
/**
 *
 * @author vikto
 */
public class Level {
    
    private TiledMap map;
    
    private Tile[][] tiles;
    
    private ArrayList<Character> characters;
    
    public Level(String level) throws SlickException{
        map = new TiledMap("data/levels/" + level + ".tmx", "data/img/");
        characters = new ArrayList<Character>();
        
        loadTileMap();
    }
    
    public void addCharacter(Character c){
        characters.add(c);
    }
    
    public ArrayList<Character> getCharacters(){
        return characters;
    }
    
    public Tile[][] getTiles(){
        return tiles;
    }
    
    public void render(){
        map.render(0,0,0,0,32,18);
        
        for(Character c : characters){
            c.render();
        }
    }
    
    public void loadTileMap(){
        tiles = new Tile[map.getWidth()][map.getHeight()];
        
        int layerIndex = map.getLayerIndex("CollisionLayer");
        
        if(layerIndex == -1){
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }
        
        for (int x = 0; x < map.getWidth(); x++){
            for (int y = 0; y < map.getHeight(); y++){
                
                int tileID = map.getTileId(x, y, layerIndex);
                
                Tile tile = null;
                switch(map.getTileProperty(tileID, "tileType", "solid")){
                case "air":
                        tile = new AirTile(x,y);
                        break;
                    default:
                        tile = new SolidTile(x,y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
    }
}
