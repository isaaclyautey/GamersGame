package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import util.Version;

public class Wall extends Entity {
	
	private Color color;

	public Wall(World world, float x, float y, float width, float height, ShapeRenderer shapeRenderer, Color color) {
		super(world, x, y, width, height, shapeRenderer);
		this.color = color;
	}

	@Override
	protected void createBody(World world, float x, float y, float width, float height) {
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.StaticBody;
		def.position.set((x)/Version.PPM, y/Version.PPM);
		def.fixedRotation = true;
		
		body = world.createBody(def); 
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/Version.PPM/2, height/Version.PPM/2);
		
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.createFixture(fixtureDef);
        
        shape.dispose();
	}

	@Override
	protected void checkInput() {
		
	}

	@Override
	protected void updateLoop() {
		
	}

	@Override
	protected void draw() {
		this.shapeRenderer.setColor(color);
		this.shapeRenderer.rect(body.getPosition().x*Version.PPM-width/2, body.getPosition().y*Version.PPM-height/2, width, height);
	}

	@Override
	protected void customDelete() {
		
	}

	@Override
	public void drawText(SpriteBatch entityBatch, BitmapFont entityFont) {
		
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName().toUpperCase();
	}

}
