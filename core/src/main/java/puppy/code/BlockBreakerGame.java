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
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Block> blocks;

    private int vidas;
    private int nivel;

    private Texture img;  // Imagen del menú principal
    private Texture gameOverImage;
    private Texture victoryImage;


    private Music victoryMusic;
    private Music menuMusic;
    private Music gameMusic;
    private Music gameOverMusic;

    private GameStateManager gameStateManager;
    private GameInitializer gameInitializer;

    private LevelManager levelManager;

    private PauseMenu pauseMenu;  // Menú de pausa
    private float timeElapsed = 0f;  // Tiempo transcurrido
    private boolean showText = true;  // Visibilidad del texto del menú

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);

        shape = new ShapeRenderer();
        gameStateManager = new GameStateManager();
        gameInitializer = new GameInitializer();

        levelManager = new LevelManager();

        pauseMenu = new PauseMenu(batch, font);
        img = new Texture(Gdx.files.internal("image.png"));

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Start Demo - Arkanoid.mp3"));
        menuMusic.setLooping(true); // Reproduce la música en bucle
        menuMusic.play(); // Inicia la música del menú

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Arkanoid Win32 - Track1.mp3"));
        gameMusic.setLooping(true);

        gameOverImage = new Texture(Gdx.files.internal("gameOver.png")); // Imagen Game Over
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("Game Over - Arkanoid.mp3")); // Música Game Over

        victoryImage = new Texture(Gdx.files.internal("victory.png"));
        victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("Start Demo - Arkanoid.mp3"));


        // Inicializa objetos del juego
        blocks = new ArrayList<>();
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
        } else if (gameStateManager.getCurrentState() == GameStateManager.GameState.TRANSICION_NIVEL) {
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

        // Configuración de la imagen de fondo en el menú
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

        batch.draw(gameOverImage, (Gdx.graphics.getWidth() - imgWidth) / 2, (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1); // Rojo

        // Opciones de texto
        font.draw(batch, "WUAJAJA QUE LOSER!!", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "Seleccione una opción", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 125);

        // Texto parpadeante
        if (showText) {
            font.draw(batch, "1. Volver a intentarlo", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "2. Salir del juego", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 175);
        }

        batch.end();

        // Manejo de entrada
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { // Opción 1: Volver a intentarlo
            reiniciarJuego();
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            gameOverMusic.stop();
            gameMusic.play();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { // Opción 2: Salir del juego
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

        // Configuración de la imagen de fondo en el menú
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

        batch.draw(img, (Gdx.graphics.getWidth() - imgWidth) / 2, (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);

        if (showText) {
            font.draw(batch, "1. Inicio de Juego", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "2. Salir", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            gameStateManager.setState(GameStateManager.GameState.INSTRUCCIONES);
            menuMusic.stop(); // Detén la música del menú
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
        }
    }

    private void mostrarPausa() {
        pauseMenu.showPauseMenu();
        int opcion = pauseMenu.handleInput();
        if (opcion == 1) {
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
        } else if (opcion == 2) {
            gameMusic.stop(); // Detén la música del juego
            reiniciarAlMenu();
        } else if (opcion == 3) {
            Gdx.app.exit();
        }
    }

    private void actualizarJuego() {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        pad.draw(shape);
        pad.update();

        if (ball.estaQuieto()) {
            ball.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ball.setEstaQuieto(false);
            }
        } else {
            ball.update();
        }

        ball.checkCollision(pad);

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
            avanzarNivel(); // Progresar al siguiente nivel o mostrar pantalla de victoria
        }



        for (Iterator<Block> iter = blocks.iterator(); iter.hasNext(); ) {
            Block b = iter.next();
            b.draw(shape);
            ball.checkCollision(b);
            if (b.isDestroyed()) {
                iter.remove();
            }
        }

        ball.draw(shape);
        shape.end();

        dibujaVidas();
    }

    private void reiniciarBola() {
        ball = gameInitializer.crearPelota(pad);
    }

    private void reiniciarJuego() {
        levelManager.resetLevels(); // Reinicia los niveles
        vidas = 3;

        levelManager.configurarNivel(levelManager.getCurrentLevel(), gameInitializer, blocks, pad, ball); // Configura nivel inicial
        reiniciarBola();
    }


    private void avanzarNivel() {
        nivel++; // Incrementar el nivel

        if (nivel > 3) {
            gameStateManager.setState(GameStateManager.GameState.VICTORY);
            gameMusic.stop();
            victoryMusic.play();
        } else {
            // Cambiar al estado de transición de nivel
            gameStateManager.setState(GameStateManager.GameState.TRANSICION_NIVEL);

            // Configurar el nuevo nivel
            int newXSpeed = ball.getXSpeed() + 1; // Incremento para la pelota
            int newYSpeed = ball.getYSpeed() + 1;
            ball.setSpeed(newXSpeed, newYSpeed);

            int newPaddleWidth = Math.max(pad.getWidth() - 10, 50); // Reducir el tamaño de la paleta
            int newPaddleSpeed = pad.getSpeed() + 1; // Incrementar la velocidad
            pad.setSize(newPaddleWidth, pad.getHeight());
            pad.setSpeed(newPaddleSpeed);

            // Incrementar filas de bloques
            gameInitializer.inicializarBloques(2 + nivel, blocks);

            reiniciarBola(); // Reiniciar posición de la bola
        }
    }


    private float transicionTime = 0; // Temporizador para la transición

    private void mostrarMensajeNivel() {
        transicionTime += Gdx.graphics.getDeltaTime(); // Incrementar el temporizador

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(2f);
        font.setColor(1, 1, 1, 1); // Blanco
        font.draw(batch, "¡Felicidades! Pasaste al nivel " + nivel, Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);

        batch.end();

        if (transicionTime >= 2.0f) { // Mostrar el mensaje durante 2 segundos
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
        reiniciarBola();
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

        // Dibujar las instrucciones
        font.draw(batch, "Para mover la tablita utilice las flechas de izquierda y derecha", Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "Para iniciar el juego y mover la pelota aprete ESPACIO", Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Para pausar el juego aprete la tecla ESC", Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 - 50);
        font.draw(batch, "¡Espero que te encante el juego ^^!", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 100);

        // Mensaje parpadeante
        if (showText) {
            font.draw(batch, "Aprete cualquier letra para comenzar", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200);
        }

        batch.end();

        // Transición al estado JUGANDO
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            menuMusic.stop(); // Detén la música del menú
            gameMusic.play(); // Inicia la música del juego
        }
    }

    private void mostrarVictoria() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibuja la imagen de victoria en el centro de la pantalla
        float imgWidth = Gdx.graphics.getWidth();
        float imgHeight = Gdx.graphics.getHeight();
        batch.draw(victoryImage, (Gdx.graphics.getWidth() - imgWidth) / 2, (Gdx.graphics.getHeight() - imgHeight) / 2, imgWidth, imgHeight);

        // Opciones de texto
        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "¡Felicidades, ganaste!", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 50);
        font.draw(batch, "1. Jugar de nuevo", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "2. Salir del juego", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 150);

        batch.end();

        // Manejo de entrada
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { // Opción 1: Jugar de nuevo
            reiniciarJuego();
            gameStateManager.setState(GameStateManager.GameState.JUGANDO);
            victoryMusic.stop();
            gameMusic.play();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { // Opción 2: Salir del juego
            Gdx.app.exit();
        }
    }

}
