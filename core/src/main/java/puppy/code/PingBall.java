package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Renderable {
    private int xSpeed, ySpeed;
    private Color color = Color.ORANGE;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size) {
        super(x, y, size * 2, size * 2);
        GameConfigManager config = GameConfigManager.getInstance();
        this.xSpeed = config.getBallXSpeed(); // Obtener velocidad inicial del Singleton
        this.ySpeed = config.getBallYSpeed();
        this.estaQuieto = true;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, width / 2);
    }

    @Override
    public void update() {
        if (estaQuieto) return;

        x += xSpeed;
        y += ySpeed;

        if (x - width / 2 < 0 || x + width / 2 > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed;
        }
        if (y + height / 2 > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }
    }

    public void applyConfig() {
        // Aplicar nueva configuración al cambiar de nivel
        GameConfigManager config = GameConfigManager.getInstance();
        this.xSpeed = config.getBallXSpeed();
        this.ySpeed = config.getBallYSpeed();
    }
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEstaQuieto(boolean estaQuieto) {
        this.estaQuieto = estaQuieto;
    }

    public boolean estaQuieto() {
        return estaQuieto;
    }

    // Mtodo para verificar colisiones
    public void checkCollision(GameObject other) {
        // Lógica básica de colisión usando bounding boxes
        if (x < other.getX() + other.getWidth() &&
            x + width > other.getX() &&
            y < other.getY() + other.getHeight() &&
            y + height > other.getY()) {

            // Verificar si el objeto es un bloque
            if (other instanceof Block) {
                Block block = (Block) other;
                block.setDestroyed(true); // Marcar el bloque como destruido
            }

            // Invertir la dirección vertical al colisionar
            ySpeed = -ySpeed;
        }
    }

    @Override
    public boolean isStill() {
        return estaQuieto; // Retorna true si la pelota está quieta
    }


}
