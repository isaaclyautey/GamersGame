package entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import util.Version;

public abstract class Entity {
	
	protected float width, height;
	protected boolean alive, shouldDelete;
	
	protected ShapeRenderer shapeRenderer;
	protected Body body;
	protected World world;
	
	protected abstract void createBody(World world, float x, float y, float width, float height);
	protected abstract void checkInput();
	protected abstract void updateLoop();
	protected abstract void draw();
	protected abstract void customDelete();
	public abstract void drawText(SpriteBatch entityBatch, BitmapFont entityFont);
	
	public abstract String getType();
	
	public Entity(World world, float x, float y, float width, float height, ShapeRenderer shapeRenderer) {
		this.world = world;
		this.shapeRenderer = shapeRenderer;
		
		this.width = width;
		this.height = height;
		
		this.alive = true;
		this.shouldDelete = false;
		
		createBody(world,x,y,width,height);
		Fixture fixture = body.getFixtureList().get(0);
		fixture.setUserData(this);
	}
	
	public void update() {
		if(alive) {
			checkInput();
			updateLoop();
		}
	}
	
	public void render() {
		if(alive)
			draw();
	}
	
	public Body getBody() {
		return body;
	}
	
	public Vector2 getPosition(boolean adjusted) {
		if(adjusted)
			return new Vector2(body.getPosition().x*Version.PPM, body.getPosition().y*Version.PPM);
		return body.getPosition();
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isShouldDelete() {
		return shouldDelete;
	}
	
	public void delete() {
		this.shouldDelete = true;
		world.destroyBody(body);
	}

}
