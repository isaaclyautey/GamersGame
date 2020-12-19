package states;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import entity.BasicEnemy;
import entity.Entity;
import manager.state.State;
import manager.state.StateController;
import map.MapCreator;
import map.TileMap;
import util.GeneralUtil;
import util.Version;
import views.TestingUI;

public class TestingState extends State {

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera gameCamera;
    private OrthographicCamera interfaceCamera;

    private World world;
    private Box2DDebugRenderer boxRender;
    private RayHandler rayHandler;

    private SpriteBatch batch;
    private BitmapFont font;

    private SpriteBatch entityBatch = new SpriteBatch();
    private BitmapFont entityFont = new BitmapFont();

    private HashMap<String, List<? extends Entity>> entities;
    private TestingUI testingUI;

    public TestingState(StateController stateController) {
        super(stateController);
    }

    @Override
    public void init() {
        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        gameCamera.update();

        interfaceCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // interfaceCamera.position.set(new Vector2(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f),0f);
        interfaceCamera.position.set(new Vector2(0f,0f),0f);
        interfaceCamera.update();

        world = new World(new Vector2(0, 0), true);
        boxRender = new Box2DDebugRenderer(true, true, true, true, true, true);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        entityBatch = new SpriteBatch();
        entityFont = new BitmapFont();

        testingUI = new TestingUI();

        //lights
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);
        rayHandler.setShadows(true);
        rayHandler.setBlurNum(6);

        entities = new HashMap<>();
        TileMap map = MapCreator.generateSquareMap(world, shapeRenderer, 25, 25, 50, 50);
        entities.put(map.getPlayer().getClass().getSimpleName(), Arrays.asList(map.getPlayer()));
        var walls = map.getWalls();
        entities.put(walls.get(0).getClass().getSimpleName(), walls);
        var testEnemy = Arrays.asList(new BasicEnemy(world, 250, 250, 25, 25, shapeRenderer));
        entities.put(testEnemy.get(0).getClass().getSimpleName(), testEnemy);
        System.out.println(testEnemy.get(0).getClass().getSimpleName());
    }

    @Override
    public void enterState() {

    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {
        gameCamera.position.set(
                GeneralUtil.lerpMoveCamera(
                        .1f,
                        gameCamera.position,
                        entities.get(Version.ENTITY_TYPE_PLAYER).get(0).getPosition(true)));

        for (String s : entities.keySet())
            for (Entity e : entities.get(s))
                e.update();

        gameCamera.update();
        this.stepWorld();
    }

    private void stepWorld() {
        world.step(Gdx.graphics.getDeltaTime(), 5, 2);
        rayHandler.update();
    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(gameCamera.combined);
        entityBatch.setProjectionMatrix(gameCamera.combined);

        // Shapes
        shapeRenderer.begin(ShapeType.Filled);
        for (String s : entities.keySet())
            for (Entity e : entities.get(s))
                e.render();
        ;

        entities.keySet().forEach(s -> entities.get(s).forEach(Entity::render));
        shapeRenderer.end();

        // Lights
        rayHandler.setCombinedMatrix(gameCamera.combined.cpy().scl(Version.PPM));
        rayHandler.render();

        // Debug collisions and box2d
        boxRender.render(world, gameCamera.combined.cpy().scl(Version.PPM));

        // UI
        interfaceCamera.setToOrtho(true,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        testingUI.render(interfaceCamera);

        // FPS Counter
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 25, 30);
        font.draw(batch, String.format("Mouse: %d %d",Gdx.input.getX(),Gdx.input.getY()), 25, 10);
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
        testingUI.dispose();
    }
}
