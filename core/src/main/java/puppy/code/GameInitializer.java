// GameInitializer.java
package puppy.code;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;

public class GameInitializer {
    private GameObjectFactory factory;

    public GameInitializer(GameObjectFactory factory) {
        this.factory = factory;
    }

    // GameInitializer.java
    public void inicializarBloques(int filas, ArrayList<IBlock> blocks) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight();
        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(factory.createBlock(x, y, blockWidth, blockHeight));
            }
        }
    }


    public IPaddle crearPaleta() {
        return factory.createPaddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
    }

    public IPingBall crearPelota(IPaddle paddle) {
        return factory.createPingBall(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11, 10);
    }

    public IItem crearItem(int x, int y, BounceStrategy strategy) {
        return factory.createItem(x, y, 20, 20, strategy);
    }
}
