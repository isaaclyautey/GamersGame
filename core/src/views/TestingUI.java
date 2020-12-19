package views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class TestingUI implements Disposable {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private BitmapFont bitmapFont = new BitmapFont();

    public TestingUI() {

    }

    public void render(OrthographicCamera inputCamera) {
        shapeRenderer.setProjectionMatrix(inputCamera.projection);
        spriteBatch.setProjectionMatrix(inputCamera.projection);

        // Drawing is done offset the center of the screen so the input x and y must be offset.
        // Additionally the y axis is inverted and must be corrected for drawing.
        var mousePosition = new Vector2(Gdx.input.getX()-Gdx.graphics.getWidth()/2f, -Gdx.input.getY()+Gdx.graphics.getHeight()/2f);

        // Draw cross-hair
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.rect(mousePosition.x, mousePosition.y+5f, 5f, 25f);
        shapeRenderer.rect(mousePosition.x, mousePosition.y-25f, 5f, 25f);
        shapeRenderer.rect(mousePosition.x+5f, mousePosition.y, 25f, 5f);
        shapeRenderer.rect(mousePosition.x-25f, mousePosition.y, 25f, 5f);
        shapeRenderer.end();

        // Draw debug text for mouse position.
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, String.format("Mouse: %f %f",mousePosition.x,mousePosition.y), mousePosition.x+25f, mousePosition.y+25f);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        bitmapFont.dispose();
    }
}
