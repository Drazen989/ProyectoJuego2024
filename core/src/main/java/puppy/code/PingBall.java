package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Renderable, Collidable, Configurable, IPingBall {
    private int xSpeed, ySpeed;
    private Color color = Color.ORANGE;
    private boolean estaQuieto;
    private BounceStrategy bounceStrategy; // Estrategia de rebote

    public PingBall(int x, int y, int size) {
        super(x, y, size * 2, size * 2);
        applyConfig();
        this.estaQuieto = true;
        this.bounceStrategy = new NormalBounceStrategy(); // Estrategia por defecto
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

    @Override
    public void applyConfig() {
        GameConfigManager config = GameConfigManager.getInstance();
        this.xSpeed = config.getBallXSpeed();
        this.ySpeed = config.getBallYSpeed();
    }

    @Override
    public void checkCollision(GameObject other) {
        if (x < other.getX() + other.getWidth() &&
            x + width > other.getX() &&
            y < other.getY() + other.getHeight() &&
            y + height > other.getY()) {

            if (other instanceof Block) {
                ((Block) other).setDestroyed(true);
            }

            if (other instanceof Item) {
                // Cambiar estrategia de rebote al recoger el ítem
                this.setBounceStrategy(((Item) other).getBounceStrategy());
                // Aquí podrías eliminar el ítem del juego si es necesario
                // items.remove(other); // Si tienes acceso a la lista de items
            }

            // Utilizar la estrategia de rebote
            bounceStrategy.bounce(this, other);
        }
    }

    // Getters y Setters para xSpeed y ySpeed
    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setBounceStrategy(BounceStrategy bounceStrategy) {
        this.bounceStrategy = bounceStrategy;
    }

    public BounceStrategy getBounceStrategy() {
        return bounceStrategy;
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

    public boolean isStill() {
        return estaQuieto;
    }
}
