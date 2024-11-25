// IBlock.java
package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IBlock extends Renderable {
    void setDestroyed(boolean destroyed);
    boolean isDestroyed();
    void draw(ShapeRenderer shape);
    void update();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
