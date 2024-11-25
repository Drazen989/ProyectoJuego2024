// NormalBounceStrategy.java
package puppy.code;

public class NormalBounceStrategy implements BounceStrategy {
    @Override
    public void bounce(IPingBall ball, GameObject other) {
        ball.setYSpeed(-ball.getYSpeed());
    }
}
