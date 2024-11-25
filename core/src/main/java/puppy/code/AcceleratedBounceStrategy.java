// AcceleratedBounceStrategy.java
package puppy.code;

public class AcceleratedBounceStrategy implements BounceStrategy {
    private int acceleration = 1;

    @Override
    public void bounce(IPingBall ball, GameObject other) {
        int newXSpeed = ball.getXSpeed();
        int newYSpeed = -ball.getYSpeed() - acceleration;
        ball.setXSpeed(newXSpeed);
        ball.setYSpeed(newYSpeed);
    }
}
