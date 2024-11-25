package puppy.code;

public class ResistantBlockCollision implements CollisionStrategy {
    @Override
    public void handleCollision(Block block, PingBall ball, LevelManager levelManager) {
        int durability = block.getDurability();
        durability--;
        block.setDurability(durability);
        if (durability <= 0) {
            block.setDestroyed(true);
        }
    }
}
