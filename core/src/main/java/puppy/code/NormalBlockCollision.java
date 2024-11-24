package puppy.code;

public class NormalBlockCollision implements CollisionStrategy {
    @Override
    public void handleCollision(Block block, PingBall ball, LevelManager levelManager) {
        // Nada adicional, simplemente se destruye.
    }
}
