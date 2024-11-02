package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Renderable {
    void draw(ShapeRenderer shape);
    void update();
    boolean isStill();
}
