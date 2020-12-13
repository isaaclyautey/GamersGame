package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import util.Version;

public class Player extends Entity {

	public Player(World world, float x, float y, float width, float height, ShapeRenderer shapeRenderer) {
		super(world, x, y, width, height, shapeRenderer);
	}

	@Override
	protected void createBody(World world, float x, float y, float width, float height) {
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x/Version.PPM, y/Version.PPM);
		def.fixedRotation = true;
		
		body = world.createBody(def); 
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/Version.PPM/2, height/Version.PPM/2);
		
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.filter.groupIndex = -1;
        body.createFixture(fixtureDef);
        
        shape.dispose();
	}

	@Override
	protected void checkInput() {
		float moveSpeed = 1f;
		Vector2 moveVector = new Vector2(0,0);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			moveSpeed = 2.5f;
		
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveVector.y = moveSpeed;
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveVector.y = -moveSpeed;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveVector.x = -moveSpeed;
		} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveVector.x = moveSpeed;
		}
		
		body.setLinearVelocity(moveVector);
	}

	@Override
	protected void updateLoop() {
		
	}

	@Override
	protected void draw() {
		this.shapeRenderer.setColor(Color.GRAY);
		this.shapeRenderer.rect(body.getPosition().x*Version.PPM-height/2, body.getPosition().y*Version.PPM-width/2, width, height);
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
