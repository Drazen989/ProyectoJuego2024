package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

public class Block extends GameObject {
    private Color cc;
    private boolean destroyed;

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        destroyed = false;
        Random r = new Random(x + y);
        cc = new Color(0.1f + r.nextFloat(1), r.nextFloat(1), r.nextFloat(1), 1);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(cc);
            shape.rect(x, y, width, height);
        }
    }

    @Override
    public void update() {
        // No necesita actualización en cada frame
    }

    public boolean isDestroyed() {
        return destroyed;  // Método para verificar si el bloque está destruido
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;  // Método para cambiar el estado de destruido
    }

    @Override
    public boolean isStill() {
        return !destroyed;  // Retorna true si el bloque no está destruido
    }
}
