package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject implements Renderable, Configurable {
    private int speed;
    private Color color;
    private boolean moving;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        applyConfig();
        this.color = Color.PURPLE;
        this.moving = false;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.rect(x, y, width, height);
    }

    @Override
    public void update() {
        moving = false;

        // Movimiento hacia la izquierda
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed;
            moving = true;
            if (x < 0) x = 0;
        }

        // Movimiento hacia la derecha
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed;
            moving = true;
            if (x + width > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - width;
        }
    }

    @Override
    public void applyConfig() {
        GameConfigManager config = GameConfigManager.getInstance();
        this.speed = config.getPaddleSpeed();
        this.width = config.getPaddleWidth();
    }
}
