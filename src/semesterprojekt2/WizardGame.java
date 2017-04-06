/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semesterprojekt2;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Viktor Vase Frandsen
 */


public class WizardGame extends BasicGame {
    
    private TiledMap grassMap;
    private Animation sprite, up, down, left, right;
    private float x = 34f, y=34f;
    private Rectangle[][] blocked;
    private int PLAYERWIDTH = 24, PLAYERHEIGHT = 30;

    
    public WizardGame(){
        super("Wizard game");
    }
    
    public static void main(String[] args){
        try{
            AppGameContainer app = new AppGameContainer(new WizardGame());
            app.setDisplayMode(400,350,false);
            app.start();
        } catch (SlickException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void init(GameContainer container) throws SlickException{
        grassMap = new TiledMap("data/img/tiles/grassmap/grassmap.tmx");
        Image[] movementUp = {new Image("data/img/sprite_wizard/movingup1.png"), new Image("data/img/sprite_wizard/movingup2.png")};
        Image[] movementDown = {new Image("data/img/sprite_wizard/movingdown1.png"), new Image("data/img/sprite_wizard/movingdown2.png")};
        Image[] movementLeft = {new Image("data/img/sprite_wizard/movingleft1.png"), new Image("data/img/sprite_wizard/movingleft2.png")};
        Image[] movementRight = {new Image("data/img/sprite_wizard/movingright1.png"), new Image("data/img/sprite_wizard/movingright2.png")};
        int[] duration = {300,300};
        up = new Animation(movementUp, duration, false);
        down = new Animation(movementDown, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        sprite = right;
        blocked = new Rectangle[grassMap.getWidth()][grassMap.getHeight()];
        for (int x = 0; x < grassMap.getWidth(); x++){
            for (int y = 0; y < grassMap.getHeight(); y++){
                int tileID = grassMap.getTileId(x,y,0);
                if (17 == tileID){
                    blocked[x][y] = new Rectangle(x*grassMap.getTileWidth(),y*grassMap.getTileHeight(),32,32);
                } else {
                    blocked[x][y] = new Rectangle(0,0,0,0);
                }
            }
        }
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException{
        Input input = container.getInput();
        float xChange=0, yChange =0;
        boolean capturedInput = false;
        if (input.isKeyDown(Input.KEY_UP)){
            sprite = up;
            yChange -= delta * 0.1f;
            capturedInput = true;
        }
        else if(input.isKeyDown(Input.KEY_DOWN)){
            sprite = down;
            yChange += delta * 0.1f;
            capturedInput = true;
        }
        else if(input.isKeyDown(Input.KEY_LEFT)){
            sprite = left;
            xChange -= delta * 0.1f;
            capturedInput = true;
        }
        else if(input.isKeyDown(Input.KEY_RIGHT)){
            sprite = right;
            xChange += delta * 0.1f;
            capturedInput = true;
        }
        if (capturedInput == true && !blocked(xChange+x, yChange+y)){
            x += xChange;
            y += yChange;
            sprite.update(delta);
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException{
        grassMap.render(0,0);
        sprite.draw((int)x, (int)y);
    }
    
    private boolean blocked(float x, float y){
        Rectangle player = new Rectangle(x,y,PLAYERWIDTH, PLAYERHEIGHT);
        for (int i = 0; i < blocked.length; i++){
            for (int j = 0; j < blocked.length; j++){
                System.out.println(blocked[j][i].getX() + blocked[j][i].getY());
                if (blocked[j][i].intersects(player)){
                    return true;
                }
            }
        }
        return false;
    }
}
