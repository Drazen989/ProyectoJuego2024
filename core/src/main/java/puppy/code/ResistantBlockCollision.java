package puppy.code;

public class ResistantBlockCollision implements CollisionStrategy {
    private int durability = 2;

    @Override
    public void handleCollision(Block block, PingBall ball, LevelManager levelManager) {
        durability--;
        if (durability <= 0) {
            block.setDestroyed(true);
        }
    }
}
