// IPingBall.java
package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IPingBall extends Renderable, Collidable, Configurable {
    void setBounceStrategy(BounceStrategy bounceStrategy);
    BounceStrategy getBounceStrategy();
    void update();
    void draw(ShapeRenderer shape);
    void setXY(int x, int y);
    void setEstaQuieto(boolean estaQuieto);
    boolean estaQuieto();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    int getXSpeed();
    int getYSpeed();
    void setXSpeed(int xSpeed);
    void setYSpeed(int ySpeed);
}
