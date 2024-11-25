// ClassicItem.java
    package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ClassicItem extends GameObject implements IItem {
    private Color color;
    private BounceStrategy bounceStrategy;

    public ClassicItem(int x, int y, int width, int height, BounceStrategy strategy) {
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
        // Lógica de caída del ítem
        y -= 2; // Velocidad de caída
    }

    @Override
    public BounceStrategy getBounceStrategy() {
        return bounceStrategy;
    }
}
