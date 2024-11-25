package puppy.code;

public class ExplosiveBlockCollision implements CollisionStrategy {
    @Override
    public void handleCollision(Block block, PingBall ball, LevelManager levelManager) {
        block.setDestroyed(true); // Destruir el bloque actual
        levelManager.triggerExplosion(block); // Manejar la explosión
    }
}
