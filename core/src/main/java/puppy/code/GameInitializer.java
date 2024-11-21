package puppy.code;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class GameInitializer {
    private ArrayList<Block> blocks;

    public GameInitializer() {
        blocks = new ArrayList<>();
    }

    public void inicializarBloques(int filas, ArrayList<Block> blocks) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight();
        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    public Paddle crearPaleta() {
        return new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
    }

    public PingBall crearPelota(Paddle paddle) {
        return new PingBall(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11, 10);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
