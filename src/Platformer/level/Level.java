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
import Platformer.character.Player;
import Platformer.level.tile.AirTile;
import Platformer.level.tile.SolidTile;
import Platformer.level.tile.Tile;
import Platformer.PlatformerGame;
import org.newdawn.slick.Image;

/**
 *
 * @author vikto
 */
public class Level {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> levelObjects;
    private ArrayList<Character> characters;
    private Player player;

    private Image background;

    public Level(String level, Player player) throws SlickException {
        map = new TiledMap("data/levels/" + level + ".tmx", "data/img/");
        characters = new ArrayList<Character>();
        levelObjects = new ArrayList<LevelObject>();
        this.player = player;
        addCharacter(player);
        background = new Image("data/img/backgrounds/" + map.getMapProperty("background", "grassy_field.png"));
        loadTileMap();
        loadObjects();
    }

    public void addLevelObject(LevelObject obj) {
        levelObjects.add(obj);
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public ArrayList<LevelObject> getLevelObjects() {
        return levelObjects;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    
    public int getNumberOfObjects(){
        return map.getObjectCount(0);
    }

    public void render() {
        int offset_x = getXOffset();
        int offset_y = getYOffset();

        renderBackground();
        // vi tegner en del udefra mappet til at starte med, fordi map.render ikke kan tegne pixels, men tiles.
        map.render(-(offset_x % 32), -(offset_y % 32), offset_x / 32, offset_y / 32, 33, 19);

        for (Character c : characters) {
            c.render(offset_x, offset_y);
        }

        for (LevelObject obj : levelObjects) {
            obj.render(offset_x, offset_y);
        }
    }

    public void loadTileMap() {
        tiles = new Tile[map.getWidth()][map.getHeight()];

        int layerIndex = map.getLayerIndex("CollisionLayer");

        if (layerIndex == -1) {
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {

                int tileID = map.getTileId(x, y, layerIndex);

                Tile tile = null;
                switch (map.getTileProperty(tileID, "tileType", "solid")) {
                    case "air":
                        tile = new AirTile(x, y);
                        break;
                    default:
                        tile = new SolidTile(x, y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
    }

    public void loadObjects() throws SlickException {
        int objectAmount = map.getObjectCount(0);

        for (int i = 0; i < objectAmount; i++) {
            switch (map.getObjectName(0, i)) {
                case "Objective":
                    addLevelObject(new Objective(map.getObjectX(0, i), map.getObjectY(0, i)));
                    break;
                default:
                    break;
            }
        }
    }

    public int getXOffset() {
        int offset_x = 0;

        // først findes halvdelen af skærmen for at se om spilleren er lige i midten
        int half_width = (int) (PlatformerGame.WINDOW_WIDTH / PlatformerGame.SCALE / 2);

        // så kommer max offset, som er højre del af skærmen minus halvedelen af skærmen
        int maxX = (int) (map.getWidth() * 32) - half_width;

        /*
        så har vi tre cases:
        1. spilleren kan endten være så langt til venstre at vi ikke skal rykke skærmen mere
        for ikke at tegne noget på skærmen som ikke er i mappet
        2. spilleren er langt til højre og samme regelsæt gælder
        3. spilleren er i midten af mappet og dermed er der ikke nogen kant han kan ramme
         */
        if (player.getX() < half_width) {
            // spilleren er i den venstre del af mappet
            offset_x = 0;
        } else if (player.getX() > maxX) {
            // spilleren er i den højre del af mappet, og derfor skal vi trække halvedelen af skærmen fra, da skærmen starter i øverste venstre hjørne
            offset_x = maxX - half_width;
        } else {
            // spilleren er imellem de to punkter, og derfor sætter vi offsettet til spilleren selv, og trækker halvdelen af skærmen fra.
            offset_x = (int) (player.getX() - half_width);
        }

        return offset_x;
    }

    // det samme gælder for vertikal bevægelse
    public int getYOffset() {
        int offset_y = 0;

        int half_height = (int) (PlatformerGame.WINDOW_HEIGHT / PlatformerGame.SCALE / 2);

        int maxY = (int) (map.getHeight() * 32) - half_height;

        if (player.getY() < half_height) {
            offset_y = 0;
        } else if (player.getY() > maxY) {
            offset_y = maxY - half_height;
        } else {
            offset_y = (int) (player.getY() - half_height);
        }

        return offset_y;
    }

    private void renderBackground() {
        //først finder vi ud af hvor meget vi kan scrolle vores baggrund i forhold til skærmens størrelse.
        float backgroundXScrollValue = (background.getWidth() - PlatformerGame.WINDOW_WIDTH / PlatformerGame.SCALE);
        float backgroundYScrollValue = (background.getHeight() - PlatformerGame.WINDOW_HEIGHT / PlatformerGame.SCALE);

        // vi gør det samme for mappet
        float mapXScrollValue = ((float) map.getWidth() * 32 - PlatformerGame.WINDOW_WIDTH / PlatformerGame.SCALE);
        float mapYScrollValue = ((float) map.getWidth() * 32 - PlatformerGame.WINDOW_HEIGHT / PlatformerGame.SCALE);

        // så finder vi ud af hvilken faktor vi skal tegne baggrunden med så den passer med mappet og skærmen
        float scrollXFactor = backgroundXScrollValue / mapXScrollValue * -1;
        float scrollYFactor = backgroundYScrollValue / mapYScrollValue * -1;

        // og så tegner vi baggrunden med offset
        background.draw(this.getXOffset() * scrollXFactor, this.getYOffset() * scrollYFactor);
    }

    public void removeObject(LevelObject obj) {
        levelObjects.remove(obj);
    }

    public void removeObjects(ArrayList<LevelObject> objects) {
        levelObjects.removeAll(objects);
    }
}
