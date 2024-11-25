package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Item extends GameObject implements Renderable {
    private Color color;
    private BounceStrategy bounceStrategy;

    public Item(int x, int y, int width, int height, BounceStrategy strategy) {
        super(x, y, width, height);
        this.color = Color.GOLD;
        this.bounceStrategy = strategy;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.rect(x, y, width, height);
    }

    @Override
    public void update() {
        // No se necesita lógica de actualización para Item
    }

    public BounceStrategy getBounceStrategy() {
        return bounceStrategy;
    }
}
