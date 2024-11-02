package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Renderable {
    private int xSpeed, ySpeed;
    private Color color = Color.WHITE;
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
        if (x - width / 2 < 0 || x + width / 2 > Gdx.graphics.getWidth()) xSpeed = -xSpeed;
        if (y + height / 2 > Gdx.graphics.getHeight()) ySpeed = -ySpeed;
    }

    public void checkCollision(Paddle paddle) {
        if (collidesWith(paddle)) {
            color = Color.GREEN;
            ySpeed = -ySpeed;
        } else {
            color = Color.WHITE;
        }
    }

    private boolean collidesWith(GameObject obj) {
        boolean intersectaX = (obj.getX() + obj.getWidth() >= x - width / 2) && (obj.getX() <= x + width / 2);
        boolean intersectaY = (obj.getY() + obj.getHeight() >= y - height / 2) && (obj.getY() <= y + height / 2);
        return intersectaX && intersectaY;
    }

    public void checkCollision(Block block) {
        if (collidesWith(block)) {
            ySpeed = -ySpeed;
            block.setDestroyed(true);  // Usa el mtodo setDestroyed
        }
    }

    @Override
    public boolean isStill() {
        return estaQuieto;  // Implementación de isStill
    }
}
