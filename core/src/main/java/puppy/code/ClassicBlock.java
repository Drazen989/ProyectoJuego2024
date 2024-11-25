// ClassicBlock.java
package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ClassicBlock extends GameObject implements IBlock {
    private Color color;
    private boolean destroyed;

    public ClassicBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.destroyed = false;
        this.color = Color.BLUE; // Color específico para el tema clásico
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }

    @Override
    public void update() {
        // No necesita lógica de actualización
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
