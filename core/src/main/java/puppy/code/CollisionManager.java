package puppy.code;
import java.util.List;

public class CollisionManager {
    public static void handleCollisions(List<Collidable> collidables, List<GameObject> gameObjects) {
        for (Collidable collidable : collidables) {
            for (GameObject object : gameObjects) {
                collidable.checkCollision(object);
            }
        }
    }
}
