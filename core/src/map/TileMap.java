package map;

import java.util.List;

import entity.Entity;

public class TileMap {
	
	private Entity player;
	private List<Entity> walls;
	public Entity getPlayer() {
		return player;
	}
	public void setPlayer(Entity player) {
		this.player = player;
	}
	public List<Entity> getWalls() {
		return walls;
	}
	public void setWalls(List<Entity> walls) {
		this.walls = walls;
	}
}
