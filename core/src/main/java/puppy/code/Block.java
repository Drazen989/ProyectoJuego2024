package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block extends GameObject implements Renderable {
    private CollisionStrategy collisionStrategy;
    private Texture texture;
    private boolean destroyed;

    public Block(int x, int y, int width, int height, CollisionStrategy strategy, Texture texture) {
        super(x, y, width, height);
        this.collisionStrategy = strategy;
        this.texture = texture;
        this.destroyed = false;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed && texture != null) {
            SpriteBatch batch = new SpriteBatch();
            batch.begin();
            batch.draw(texture, x, y, width, height);
            batch.end();
            batch.dispose();
        }
    }

    @Override
    public void update() {
        // No se requiere lógica de actualización para los bloques
    }

    @Override
    public boolean isStill() {
        return true; // Los bloques siempre están "quietos"
    }

    public void onCollision(PingBall ball, LevelManager levelManager) {
        if (collisionStrategy != null) {
            collisionStrategy.handleCollision(this, ball, levelManager);
        }
        setDestroyed(true);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
