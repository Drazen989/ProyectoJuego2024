// ClassicPingBall.java
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ClassicPingBall extends GameObject implements IPingBall {
    private int xSpeed, ySpeed;
    private Color color = Color.ORANGE;
    private boolean estaQuieto;
    private BounceStrategy bounceStrategy;

    public ClassicPingBall(int x, int y, int size) {
        super(x, y, size * 2, size * 2);
        applyConfig();
        this.estaQuieto = true;
        this.bounceStrategy = new NormalBounceStrategy();
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

            if (other instanceof IBlock) {
                ((IBlock) other).setDestroyed(true);
            }

            if (other instanceof IItem) {
                this.setBounceStrategy(((IItem) other).getBounceStrategy());
                // Aquí podrías eliminar el ítem del juego si es necesario
            }

            bounceStrategy.bounce(this, other);
        }
    }

    @Override
    public int getXSpeed() {
        return xSpeed;
    }

    @Override
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    @Override
    public int getYSpeed() {
        return ySpeed;
    }

    @Override
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void setBounceStrategy(BounceStrategy bounceStrategy) {
        this.bounceStrategy = bounceStrategy;
    }

    @Override
    public BounceStrategy getBounceStrategy() {
        return bounceStrategy;
    }

    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setEstaQuieto(boolean estaQuieto) {
        this.estaQuieto = estaQuieto;
    }

    @Override
    public boolean estaQuieto() {
        return estaQuieto;
    }
}
