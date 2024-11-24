package puppy.code;

import java.util.List;

public class CollisionManager {
    public static void handleCollisions(List<Block> blocks, PingBall ball, LevelManager levelManager) {
        for (Block block : blocks) {
            if (ball.checkCollision(block)) { // Ahora devuelve un boolean
                block.onCollision(ball, levelManager); // Delegar a la estrategia de colisión
            }
        }
    }
}
