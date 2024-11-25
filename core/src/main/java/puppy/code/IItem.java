// IItem.java
package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IItem extends Renderable {
    BounceStrategy getBounceStrategy();
    void update();
    void draw(ShapeRenderer shape);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
