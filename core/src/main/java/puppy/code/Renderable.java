package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Renderable {
    void draw(ShapeRenderer shape);

    // Método template para dibujar
    default void render(ShapeRenderer shape) {
        preDraw();
        draw(shape);
        postDraw();
    }

    // Opcional, código común antes de dibujar
    default void preDraw() {}

    // Opcional, código común después de dibujar
    default void postDraw() {}

    boolean isStill();
}
