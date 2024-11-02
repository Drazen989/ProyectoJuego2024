package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject implements Renderable {
    private int speed;
    private Color color;
    private boolean moving;  // Nueva variable para rastrear el movimiento

    public Paddle(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        this.color = Color.BLUE;  // Color inicial de la paleta
        this.moving = false;      // Inicialmente, no se está moviendo
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.rect(x, y, width, height);
    }

    @Override
    public void update() {
        moving = false;  // Reiniciar estado de movimiento

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed;
            moving = true;  // Se está moviendo
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed;
            moving = true;  // Se está moviendo
        }

        // Evitar que la paleta se salga de la pantalla
        if (x < 0) x = 0;
        if (x + width > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - width;
    }

    @Override
    public boolean isStill() {
        return !moving;  // Devuelve true si no se está moviendo
    }

    public void setColor(Color color) {
        this.color = color;  // Permitir cambiar el color (por ejemplo, en colisiones)
    }
}
