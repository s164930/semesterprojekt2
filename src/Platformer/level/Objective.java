/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Platformer.level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author vikto
 */
public class Objective extends LevelObject{
    
    protected Image image;

    public Objective(float x, float y) throws SlickException {
        super(x, y);
        
        image = new Image("data/img/objekts/level_1/obj_chest.png");
    }
    
    public void render(float offset_x, float offset_y){
        image.draw(x-2-offset_x, y-2-offset_y);
    }
    
}
