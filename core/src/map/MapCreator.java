package map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;

import entity.Entity;
import entity.Player;
import entity.Wall;

public class MapCreator {

	public static TileMap generateSquareMap(World world, ShapeRenderer shapeRenderer, int width, int height, int tileWidth, int tileHeight) {
		List<Entity> walls = new ArrayList<>();
		Entity player = new Player(world, width/2*tileWidth/2, height/2*tileHeight, 25, 25, shapeRenderer);
		
		for(int i = 1; i < width-1; i++) {
			walls.add(new Wall(world, tileWidth*i, 0, tileWidth, tileHeight, shapeRenderer, Color.GRAY));
			walls.add(new Wall(world, tileWidth*i, tileHeight*(height-1), tileWidth, tileHeight, shapeRenderer, Color.GRAY));
		}
		
		for(int i = 0; i < height; i++) {
			walls.add(new Wall(world, 0, tileHeight*i, tileWidth, tileHeight, shapeRenderer, Color.GRAY));
			walls.add(new Wall(world, tileWidth*(width-1), tileHeight*i, tileWidth, tileHeight, shapeRenderer, Color.GRAY));
			
		}
		
		TileMap map = new TileMap();
		map.setPlayer(player);
		map.setWalls(walls);
		return map;
	}
	
	
}
