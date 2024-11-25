package puppy.code;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class GameInitializer {
    public void inicializarBloques(List<Block> blocks, Texture normal, Texture resistant, Texture explosive) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                int x = j * 80;
                int y = 400 - i * 40;
                CollisionStrategy strategy;
                int durability = 1; // Durabilidad por defecto

                if (i % 3 == 0) {
                    strategy = new NormalBlockCollision();
                    blocks.add(new Block(x, y, 80, 40, strategy, normal, durability));
                } else if (i % 3 == 1) {
                    strategy = new ResistantBlockCollision();
                    durability = 2; // Durabilidad para bloques resistentes
                    blocks.add(new Block(x, y, 80, 40, strategy, resistant, durability));
                } else {
                    strategy = new ExplosiveBlockCollision();
                    blocks.add(new Block(x, y, 80, 40, strategy, explosive, durability));
                }
            }
        }
    }


    public Paddle crearPaleta() {
        return new Paddle(360, 30, 110, 10);
    }

    public PingBall crearPelota(Paddle paddle) {
        int x = paddle.getX() + paddle.getWidth() / 2 - 10;
        int y = paddle.getY() + paddle.getHeight();
        return new PingBall(x, y, 20, paddle);
    }
}
