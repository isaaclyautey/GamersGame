package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.TimeUtils;
import util.Version;

public class BasicEnemy extends Entity {
    long lastUpdateTime;

    public BasicEnemy(World world, float x, float y, float width, float height, ShapeRenderer shapeRenderer) {
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
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    protected void checkInput() {

    }

    @Override
    protected void updateLoop() {
        var currentTime = TimeUtils.millis();
        var timeDelta = currentTime-lastUpdateTime;

        var velocity = body.getLinearVelocity();
        var forceToApply = new Vector2(-(velocity.x-0.1f/timeDelta)*body.getMass(),-(velocity.y - 0.1f/timeDelta)*body.getMass());
        body.applyForceToCenter(forceToApply,true);
        lastUpdateTime = currentTime;
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
