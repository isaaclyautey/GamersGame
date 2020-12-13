package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import entity.Entity;
import entity.Player;
import manager.state.State;
import manager.state.StateController;
import util.Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestingState extends State{
	
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	private World world;
	private Box2DDebugRenderer boxRender;
	private RayHandler rayHandler;
	
	private SpriteBatch batch = new SpriteBatch();
	private BitmapFont font = new BitmapFont();
	
	private SpriteBatch entityBatch = new SpriteBatch();
	private BitmapFont entityFont = new BitmapFont();

	private HashMap<String, List<Entity>> entities;

	public TestingState(StateController stateController) {
		super(stateController);
	}

	@Override
	public void init() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        camera.update();
        
        world = new World(new Vector2(0,0), true);
		boxRender = new Box2DDebugRenderer(true, true, true, true, true, true);

		this.shapeRenderer = new ShapeRenderer();
		
		//lights
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(1f);
		rayHandler.setShadows(true);
		rayHandler.setBlurNum(6);

		entities = new HashMap<>();
		entities.put("PLAYER", Arrays.<Entity>asList(new Player(world,0,0,25,25,shapeRenderer)));
	}

	@Override
	public void enterState() {
		
	}

	@Override
	public void exitState() {
		
	}

	@Override
	public void update() {
		for(String s : entities.keySet())
			for(Entity e : entities.get(s))
				e.update();;

		this.stepWorld();
	}

	private void stepWorld() {
		world.step(Gdx.graphics.getDeltaTime(), 5, 2);
		rayHandler.update();
	}
	
	@Override
	public void render() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		entityBatch.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);

		for(String s : entities.keySet())
			for(Entity e : entities.get(s))
				e.render();;

		shapeRenderer.end();
		
		//lights
		rayHandler.setCombinedMatrix(camera.combined.cpy().scl(Version.PPM));
		rayHandler.render();
		
		batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 25, Gdx.graphics.getHeight()-25);
        batch.end();
	}

	@Override
	public void renderText() {
		
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		world.dispose();
		batch.dispose();
		font.dispose();
	}

}
