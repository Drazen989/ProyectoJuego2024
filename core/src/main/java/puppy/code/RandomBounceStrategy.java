// RandomBounceStrategy.java
package puppy.code;

import java.util.Random;

public class RandomBounceStrategy implements BounceStrategy {
    private Random random = new Random();

    @Override
    public void bounce(IPingBall ball, GameObject other) {
        int newXSpeed = random.nextBoolean() ? -ball.getXSpeed() : ball.getXSpeed();
        int newYSpeed = -ball.getYSpeed();
        ball.setXSpeed(newXSpeed);
        ball.setYSpeed(newYSpeed);
    }
}

