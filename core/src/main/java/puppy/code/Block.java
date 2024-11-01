package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

public class Block extends GameObject {
    Color cc;
    boolean destroyed;

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
}
