package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Block> blocks;

    private int vidas;
    private int nivel;

    private Texture menuBackground;
    private Texture gameOverImage;
    private Texture victoryImage;

    private Music menuMusic;
    private Music gameMusic;
    private Music gameOverMusic;

    private GameInitializer gameInitializer;
    private LevelManager levelManager;

    private enum GameState {MENU, INSTRUCCIONES, JUGANDO, GAME_OVER, VICTORIA}
    private GameState gameState;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();
        font = new BitmapFont();
        shape = new ShapeRenderer();

        gameInitializer = new GameInitializer();

        // Inicializar la lista de bloques antes de crear LevelManager
        blocks = new ArrayList<>();

        // Ahora pasamos la lista de bloques al constructor de LevelManager
        levelManager = new LevelManager(blocks);
        levelManager.initializeTextures();

        menuBackground = new Texture(Gdx.files.internal("image.png"));
        gameOverImage = new Texture(Gdx.files.internal("gameOver.png"));
        victoryImage = new Texture(Gdx.files.internal("victory.png"));

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Start Demo - Arkanoid.mp3"));
        menuMusic.setLooping(true);
    // ----------
        //menuMusic.play();

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Arkanoid Win32 - Track1.mp3"));
        gameMusic.setLooping(true);

        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("Game Over - Arkanoid.mp3"));

        vidas = 3;
        nivel = 1;

        gameState = GameState.MENU;
        pad = gameInitializer.crearPaleta();
        ball = gameInitializer.crearPelota(pad);
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        switch (gameState) {
            case MENU:
                renderMenu();
                break;
            case INSTRUCCIONES:
                renderInstructions();
                break;
            case JUGANDO:
                renderGame();
                break;
            case GAME_OVER:
                renderGameOver();
                break;
            case VICTORIA:
                renderVictory();
                break;
        }
    }

    private void renderMenu() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(menuBackground, 0, 0, 800, 480);
        font.draw(batch, "Presione ESPACIO para comenzar", 300, 100);
        font.draw(batch, "Presione ESC para salir", 300, 70);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameState = GameState.INSTRUCCIONES;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void renderInstructions() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Instrucciones:", 350, 350);
        font.draw(batch, "Mover barra: Flechas Izquierda/Derecha", 200, 300);
        font.draw(batch, "Lanzar pelota: Presione ESPACIO", 200, 250);
        font.draw(batch, "Pausa: Presione ESC", 200, 200);
        font.draw(batch, "Presione ESPACIO para empezar", 200, 150);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            iniciarJuego();
        }
    }

    private void renderGame() {
        pad.update();
        ball.update();

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        pad.draw(shape);
        ball.draw(shape);

        for (Block block : blocks) {
            block.draw(shape);
        }

        shape.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Vidas: " + vidas, 700, 20);
        batch.end();

        verificarColisiones();

    }

    private void verificarColisiones() {
        if (ball.checkCollision(pad)) {
            ball.bounceVertical();
        }

        for (Block block : blocks) {
            if (!block.isDestroyed() && ball.checkCollision(block)) {
                block.onCollision(ball, levelManager);
                ball.bounceVertical(); // Asegúrate de que la pelota rebote
            }
        }
        blocks.removeIf(Block::isDestroyed);

        if (ball.getY() < 0) {
            vidas--;
            if (vidas > 0) {
                reiniciarPelota();
            } else {
                gameState = GameState.GAME_OVER;
                gameMusic.stop();
                gameOverMusic.play();
            }
        }

        if (blocks.isEmpty()) {
            nivel++;
            if (nivel > 3) {
                gameState = GameState.VICTORIA;
                gameMusic.stop();
            } else {
                reiniciarNivel();
            }
        }
    }

    private void reiniciarNivel() {
        levelManager.configurarNivel(nivel, gameInitializer, pad, ball);
        reiniciarPelota();
    }

    private void reiniciarPelota() {
        ball.adjuntarAPaleta();
    }

    private void iniciarJuego() {
        gameState = GameState.JUGANDO;
        vidas = 3;
        nivel = 1;

        pad = gameInitializer.crearPaleta();
        ball = gameInitializer.crearPelota(pad);
        reiniciarNivel();

        menuMusic.stop();
        //gameMusic.play();
    }

    private void renderGameOver() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(gameOverImage, 0, 0, 800, 480);
        font.draw(batch, "Game Over", 350, 240);
        font.draw(batch, "Presione R para reiniciar", 300, 200);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameState = GameState.MENU;
            menuMusic.play();
        }
    }

    private void renderVictory() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(victoryImage, 0, 0, 800, 480);
        font.draw(batch, "¡Felicidades! Ganaste", 300, 240);
        font.draw(batch, "Presione R para reiniciar", 300, 200);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameState = GameState.MENU;
            menuMusic.play();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
        menuBackground.dispose();
        gameOverImage.dispose();
        victoryImage.dispose();
        menuMusic.dispose();
        gameMusic.dispose();
        gameOverMusic.dispose();
        levelManager.disposeTextures();
    }
}
