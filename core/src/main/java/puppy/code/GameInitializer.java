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

                if (i % 3 == 0) {
                    strategy = new NormalBlockCollision();
                    blocks.add(new Block(x, y, 80, 40, strategy, normal));
                } else if (i % 3 == 1) {
                    strategy = new ResistantBlockCollision();
                    blocks.add(new Block(x, y, 80, 40, strategy, resistant));
                } else {
                    strategy = new ExplosiveBlockCollision();
                    blocks.add(new Block(x, y, 80, 40, strategy, explosive));
                }
            }
        }
    }

    public Paddle crearPaleta() {
        return new Paddle(360, 20, 80, 20);
    }

    public PingBall crearPelota(Paddle paddle) {
        return new PingBall(paddle.getX() + paddle.getWidth() / 2 - 10, paddle.getY() + paddle.getHeight() + 10, 20);
    }
}
