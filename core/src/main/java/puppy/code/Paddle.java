package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject implements Renderable {
    private int speed;


    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.speed = 10;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.BLUE);
        shape.rect(x, y, width, height);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed; // Mover a la izquierda
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed; // Mover a la derecha
        }
    }


    @Override
    public boolean isStill() {
        return false; // La paleta nunca está "quieta"
    }

}
