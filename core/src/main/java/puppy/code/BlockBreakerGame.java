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

import java.util.ArrayList;
import java.util.Iterator;

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Block> blocks = new ArrayList<>();
    private int vidas;
    private int puntaje;
    private int nivel;

    private Texture img;  // Para la imagen del menú
    private boolean enMenu = true;  // Estado inicial del juego en el menú
    private boolean enPausa = false;  // Estado de pausa del juego

    private PauseMenu pauseMenu;  // Instancia del menú de pausa

    private float timeElapsed = 0f;  // Tiempo transcurrido
    private boolean showText = true;  // Visibilidad del texto

    @Override
    public void create() {
        inicializarJuego();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        if (enMenu) {
            mostrarMenu();
        } else if (enPausa) {
            mostrarPausa();
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                enPausa = true;
            }

            actualizarJuego();
            batch.begin();
            //pad.draw(batch);
            batch.end();
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

        float imgWidth = Gdx.graphics.getWidth() * 0.6f;
        float imgHeight = img.getHeight() * (imgWidth / img.getWidth());

        batch.draw(img, Gdx.graphics.getWidth() / 2 - imgWidth / 2, Gdx.graphics.getHeight() - imgHeight - 50, imgWidth, imgHeight);
        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);

        if (showText) {
            font.draw(batch, "1. Inicio de Juego", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "2. Salir", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);
        }

        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            enMenu = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
        }
    }

    private void mostrarPausa() {
        pauseMenu.showPauseMenu();
        int opcion = pauseMenu.handleInput();
        if (opcion == 1) {
            enPausa = false;
        } else if (opcion == 2) {
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
                ball.setEstaQuieto(false); // Cambia a setEstaQuieto
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
            reiniciarJuego();
        }

        if (blocks.isEmpty()) {
            avanzarNivel();
        }

        for (Iterator<Block> iter = blocks.iterator(); iter.hasNext(); ) {
            Block b = iter.next();
            b.draw(shape);
            ball.checkCollision(b);
            if (b.isDestroyed()) { // Asegúrate de que hay un método isDestroyed en Block
                puntaje++;
                iter.remove();
            }
        }

        ball.draw(shape);
        shape.end();
        dibujaTextos();
    }

    private void reiniciarBola() {
        ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
    }

    private void reiniciarJuego() {
        vidas = 3;
        nivel = 1;
        crearBloques(2 + nivel);
        reiniciarBola();
    }

    private void avanzarNivel() {
        nivel++;
        crearBloques(2 + nivel);
        reiniciarBola();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    private void crearBloques(int filas) {
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

    private void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Puntos: " + puntaje, 10, 25);
        font.draw(batch, "Vidas: " + vidas, Gdx.graphics.getWidth() - 200, 25);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
        img.dispose();
    }

    private void reiniciarAlMenu() {
        enMenu = true;
        enPausa = false;
        vidas = 3;
        puntaje = 0;
        nivel = 1;
        crearBloques(2 + nivel);
    }

    private void inicializarJuego() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        nivel = 1;

        img = new Texture(Gdx.files.internal("image.png"));
        crearBloques(2 + nivel);
        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, 5, 7, true);
        pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10, 10);
        vidas = 3;
        puntaje = 0;
        pauseMenu = new PauseMenu(batch, font);
    }

    class PauseMenu {
        private SpriteBatch batch;
        private BitmapFont font;

        public PauseMenu(SpriteBatch batch, BitmapFont font) {
            this.batch = batch;
            this.font = font;
        }

        public void showPauseMenu() {
            batch.begin();
            font.getData().setScale(1.5f);
            font.setColor(1, 1, 1, 1);
            font.draw(batch, "1. Volver al Juego", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100);
            font.draw(batch, "2. Volver al Menu", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "3. Salir", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);
            batch.end();
        }

        public int handleInput() {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                return 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                return 2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                return 3;
            }
            return 0;
        }
    }
}
