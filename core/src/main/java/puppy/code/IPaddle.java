// IPaddle.java
package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IPaddle extends Renderable, Configurable {
    void update();
    void draw(ShapeRenderer shape);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
