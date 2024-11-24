package puppy.code;

public class ExplosiveBlockCollision implements CollisionStrategy {
    @Override
    public void handleCollision(Block block, PingBall ball, LevelManager levelManager) {
        levelManager.triggerExplosion(block);
    }
}
