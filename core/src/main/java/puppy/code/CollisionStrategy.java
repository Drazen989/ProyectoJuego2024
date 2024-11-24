package puppy.code;

public interface CollisionStrategy {
    void handleCollision(Block block, PingBall ball, LevelManager levelManager);
}
