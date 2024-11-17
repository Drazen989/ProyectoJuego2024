package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Renderable {
    private int xSpeed, ySpeed;
    private Color color = Color.ORANGE;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size * 2, size * 2);  // Ajustamos el tamaño de la pelota
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.estaQuieto = iniciaQuieto;
    }

    public boolean estaQuieto() { return estaQuieto; }
    public void setEstaQuieto(boolean bb) { estaQuieto = bb; }
    public void setXY(int x, int y) { this.x = x; this.y = y; }

    // Métodos para ajustar la velocidad
    public void setSpeed(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, width / 2);  // Usar width para representar el tamaño de la pelota
    }

    @Override
    public void update() {
        if (estaQuieto) return;
        x += xSpeed;
        y += ySpeed;

        // Detectar colisiones con los bordes de la pantalla
        if (x - width / 2 < 0 || x + width / 2 > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed;
        }
        if (y + height / 2 > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }
    }

    public void checkCollision(Paddle paddle) {
        if (collidesWith(paddle)) {
            color = Color.GREEN;
            ySpeed = -ySpeed;  // Cambiar dirección en el eje Y
        } else {
            color = Color.ORANGE;
        }
    }

    private boolean collidesWith(GameObject obj) {
        boolean intersectaX = (obj.getX() + obj.getWidth() >= x - width / 2) && (obj.getX() <= x + width / 2);
        boolean intersectaY = (obj.getY() + obj.getHeight() >= y - height / 2) && (obj.getY() <= y + height / 2);
        return intersectaX && intersectaY;
    }

    public void checkCollision(Block block) {
        if (collidesWith(block)) {
            ySpeed = -ySpeed;  // Cambiar dirección en el eje Y al chocar con un bloque
            block.setDestroyed(true);  // Marca el bloque como destruido
        }
    }

    @Override
    public boolean isStill() {
        return estaQuieto;  // Implementación de isStill
    }
}

