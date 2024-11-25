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
import java.util.Iterator;

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;

    private IPingBall ball;
    private IPaddle pad;
    private ArrayList<IBlock> blocks;
    private ArrayList<IItem> items;

    private int vidas;
    private int nivel;

    private Texture img;
    private Texture gameOverImage;
    private Texture victoryImage;

    private Music victoryMusic;
    private Music menuMusic;
    private Music gameMusic;
    private Music gameOverMusic;

    private GameStateManager gameStateManager;
    private GameInitializer gameInitializer;
    private GameObjectFactory factory;

    private LevelManager levelManager;

    private PauseMenu pauseMenu;
    private float timeElapsed = 0f;
    private boolean showText = true;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);

        shape = new ShapeRenderer();
        gameStateManager = new GameStateManager();

        factory = new ClassicGameObjectFactory();
        gameInitializer = new GameInitializer(factory);

        levelManager = new LevelManager();

        pauseMenu = new PauseMenu(batch, font);
        img = new Texture(Gdx.files.internal("image.png"));

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Start Demo - Arkanoid.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Arkanoid Win32 - Track1.mp3"));
        gameMusic.setLooping(true);

        gameOverImage = new Texture(Gdx.files.internal("gameOver.png"));
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("Game Over - Arkanoid.mp3"));

        victoryImage = new Texture(Gdx.files.internal("victory.png"));
        victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("Start Demo - Arkanoid.mp3"));

        blocks = new ArrayList<>();
        items = new ArrayList<>();
        reiniciarAlMenu();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        if (gameStateManager.isMenu()) {
            mostrarMenu();
        } else if (gameStateManager.isInstrucciones()) {
            mostrarInstrucciones();
        } else if (gameStateManager.isPausa()) {
            mostrarPausa();
        } else if (gameStateManager.isJugando()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                gameStateManager.setState(GameStateManager.GameState.PAUSA);
            }
            actualizarJuego();
        } else if (gameStateManager.isGameOver()) {
            mostrarGameOver();
        } else if (gameStateManager.isVictory()) {
            mostrarVictoria();
        } else if (gameStateManager.isTransicionNivel()) {
            mostrarMensajeNivel();
        }
    }

    private void mostrarGameOver() {
        timeElapsed += Gdx.graphics.getDeltaTime();
        if (timeElapsed >= 0.5f) {
            showText = !showText;
            timeElapsed = 0f;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float screenRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float imgRatio = (float) img.getWidth() / img.getHeight();
        float imgWidth, imgHeight;

        if (screenRatio > imgRatio) {
            imgWidth = Gdx.graphics.getWidth();
            imgHeight = imgWidth / imgRatio;
        } else {
            imgHeight = Gdx.graphics.getHeight();
            imgWidth = imgHeight * imgRatio;
        }

        batch.draw(gameOverImage, (Gdx.graphics.getWidth() - imgWidth) / 2,
            (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);

        font.draw(batch, "¡Has perdido!", Gdx.graphics.getWidth() / 2 - 150,
            Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "Seleccione una opción", Gdx.graphics.getWidth() / 2 - 150,
            Gdx.graphics.getHeight() / 2 - 125);

        if (showText) {
            font.draw(batch, "1. Volver a intentarlo", Gdx.graphics.getWidth() / 2 - 150,
                Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "2. Salir del juego", Gdx.graphics.getWidth() / 2 - 150,
                Gdx.graphics.getHeight() / 2 - 175);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            reiniciarJuego();
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            gameOverMusic.stop();
            gameMusic.play();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
        }
    }

    private void mostrarMenu() {
        timeElapsed += Gdx.graphics.getDeltaTime();
        if (timeElapsed >= 0.5f) {
            showText = !showText;
            timeElapsed = 0f;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float screenRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float imgRatio = (float) img.getWidth() / img.getHeight();
        float imgWidth, imgHeight;

        if (screenRatio > imgRatio) {
            imgWidth = Gdx.graphics.getWidth();
            imgHeight = imgWidth / imgRatio;
        } else {
            imgHeight = Gdx.graphics.getHeight();
            imgWidth = imgHeight * imgRatio;
        }

        batch.draw(img, (Gdx.graphics.getWidth() - imgWidth) / 2,
            (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);

        if (showText) {
            font.draw(batch, "1. Inicio de Juego", Gdx.graphics.getWidth() / 2 - 100,
                Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "2. Salir", Gdx.graphics.getWidth() / 2 - 100,
                Gdx.graphics.getHeight() / 2 - 200);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            gameStateManager.setState(GameStateManager.GameState.INSTRUCCIONES);
            menuMusic.stop();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
        }
    }

    private void mostrarPausa() {
        pauseMenu.showPauseMenu();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            gameMusic.stop();
            reiniciarAlMenu();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            Gdx.app.exit();
        }
    }

    private void actualizarJuego() {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        pad.draw(shape);
        pad.update();

        if (ball.estaQuieto()) {
            ball.setXY(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2,
                pad.getY() + pad.getHeight() + 5);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ball.setEstaQuieto(false);
            }
        } else {
            ball.update();
        }

        ball.checkCollision((GameObject) pad);

        for (Iterator<IBlock> iter = blocks.iterator(); iter.hasNext(); ) {
            IBlock b = iter.next();
            b.draw(shape);
            ball.checkCollision((GameObject) b);
            if (b.isDestroyed()) {
                iter.remove();

                // Posibilidad de crear un ítem al destruir un bloque
                if (Math.random() < 0.2) { // 20% de probabilidad
                    BounceStrategy strategy = new RandomBounceStrategy();
                    IItem newItem = factory.createItem(b.getX(), b.getY(), 20, 20, strategy);
                    items.add(newItem);
                }
            }
        }

        // Actualizar y dibujar los items
        for (Iterator<IItem> iter = items.iterator(); iter.hasNext(); ) {
            IItem item = iter.next();
            item.update();
            item.draw(shape);
            if (item.getY() + item.getHeight() < 0) {
                iter.remove();
                continue;
            }

            if (colisiona((GameObject) ball, (GameObject) item)) {
                ball.setBounceStrategy(item.getBounceStrategy());
                iter.remove();
            }
        }

        if (ball.getY() < 0) {
            vidas--;
            reiniciarBola();
        }

        if (vidas <= 0) {
            gameStateManager.setState(GameStateManager.GameState.GAME_OVER);
            gameMusic.stop();
            gameOverMusic.play();
        }

        if (blocks.isEmpty()) {
            avanzarNivel();
        }

        ball.draw(shape);
        shape.end();

        dibujaVidas();
    }

    private void reiniciarBola() {
        ball = gameInitializer.crearPelota(pad);
    }

    private void reiniciarJuego() {
        levelManager.resetLevels();
        vidas = 3;
        nivel = 1;

        levelManager.configurarNivel(levelManager.getCurrentLevel(), gameInitializer,
            blocks, pad, ball);
        reiniciarBola();
    }

    private void avanzarNivel() {
        nivel++;
        levelManager.nextLevel();

        if (nivel > 3) {
            gameStateManager.setState(GameStateManager.GameState.VICTORY);
            gameMusic.stop();
            victoryMusic.play();
        } else {
            gameStateManager.setState(GameStateManager.GameState.TRANSICION_NIVEL);
            levelManager.configurarNivel(nivel, gameInitializer, blocks, pad, ball);
            reiniciarBola();
        }
    }

    private float transicionTime = 0;

    private void mostrarMensajeNivel() {
        transicionTime += Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(2f);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "¡Felicidades! Pasaste al nivel " + nivel,
            Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);

        batch.end();

        if (transicionTime >= 2.0f) {
            transicionTime = 0;
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
        }
    }

    private void reiniciarAlMenu() {
        gameStateManager.setState(GameStateManager.GameState.MENU);
        vidas = 3;
        nivel = 1;

        gameInitializer.inicializarBloques(2 + nivel, blocks);
        pad = gameInitializer.crearPaleta();
        ball = gameInitializer.crearPelota(pad);
        menuMusic.play();
    }

    private void dibujaVidas() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Vidas: " + vidas, Gdx.graphics.getWidth() - 200, 25);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
        img.dispose();
        if (menuMusic != null) {
            menuMusic.dispose();
        }
        if (gameMusic != null) {
            gameMusic.dispose();
        }
        if (gameOverImage != null) {
            gameOverImage.dispose();
        }
        if (gameOverMusic != null) {
            gameOverMusic.dispose();
        }
        if (victoryImage != null) {
            victoryImage.dispose();
        }
        if (victoryMusic != null) {
            victoryMusic.dispose();
        }
    }

    private void mostrarInstrucciones() {
        timeElapsed += Gdx.graphics.getDeltaTime();
        if (timeElapsed >= 0.5f) {
            showText = !showText;
            timeElapsed = 0f;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);

        font.draw(batch, "Para mover la paleta, use las flechas izquierda y derecha",
            Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "Para iniciar el juego y mover la pelota, presione ESPACIO",
            Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Para pausar el juego, presione la tecla ESC",
            Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 - 50);
        font.draw(batch, "¡Espero que te encante el juego!",
            Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 100);

        if (showText) {
            font.draw(batch, "Presione cualquier tecla para comenzar",
                Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            menuMusic.stop();
            gameMusic.play();
        }
    }

    private void mostrarVictoria() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float imgWidth = Gdx.graphics.getWidth();
        float imgHeight = Gdx.graphics.getHeight();
        batch.draw(victoryImage, (Gdx.graphics.getWidth() - imgWidth) / 2,
            (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "¡Felicidades, ganaste!", Gdx.graphics.getWidth() / 2 - 150,
            Gdx.graphics.getHeight() / 2 - 50);
        font.draw(batch, "1. Jugar de nuevo", Gdx.graphics.getWidth() / 2 - 150,
            Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "2. Salir del juego", Gdx.graphics.getWidth() / 2 - 150,
            Gdx.graphics.getHeight() / 2 - 150);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            reiniciarJuego();
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            victoryMusic.stop();
            gameMusic.play();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
        }
    }

    private boolean colisiona(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth() &&
            a.getX() + a.getWidth() > b.getX() &&
            a.getY() < b.getY() + b.getHeight() &&
            a.getY() + a.getHeight() > b.getY();
    }
}
