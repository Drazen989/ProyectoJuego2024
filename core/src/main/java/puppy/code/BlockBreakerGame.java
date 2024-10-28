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

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        nivel = 1;

        // Cargar la imagen del menú
        img = new Texture(Gdx.files.internal("image.png"));

        // Crear otros objetos del juego
        crearBloques(2 + nivel);
        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, 5, 7, true);
        pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
        vidas = 3;
        puntaje = 0;

        // Crear el menú de pausa
        pauseMenu = new PauseMenu(batch, font);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();  // Asegúrate de actualizar la cámara antes de dibujar

        if (enMenu) {
            // Mostrar el menú inicial
            mostrarMenu();
        } else if (enPausa) {
            // Mostrar el menú de pausa usando la clase PauseMenu
            pauseMenu.showPauseMenu();
            int opcion = pauseMenu.handleInput();
            if (opcion == 1) {
                enPausa = false;  // Volver al juego
            } else if (opcion == 2) {
                enPausa = false;
                enMenu = true;  // Volver al menú principal
            } else if (opcion == 3) {
                Gdx.app.exit();  // Salir del juego
            }
        } else {
            // Lógica normal del juego (cuando el menú ha terminado)
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                enPausa = true;  // Pausar el juego si se presiona ESC
            }
            actualizarJuego();
        }
    }

    private void mostrarMenu() {
        // Mostrar el menú principal como antes
        batch.setProjectionMatrix(camera.combined);  // Usar la proyección de la cámara
        batch.begin();

        // Redimensionar la imagen dinámicamente según el tamaño de la ventana
        float imgWidth = Gdx.graphics.getWidth() * 0.6f;  // Ancho al 60% de la ventana
        float imgHeight = img.getHeight() * (imgWidth / img.getWidth());  // Mantener la relación de aspecto

        // Dibujar la imagen centrada
        batch.draw(img, Gdx.graphics.getWidth() / 2 - imgWidth / 2, Gdx.graphics.getHeight() - imgHeight - 50, imgWidth, imgHeight);

        // Cambiar el tamaño del texto si es necesario
        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);  // Color blanco
        font.draw(batch, "1. Inicio de Juego", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150);
        font.draw(batch, "2. Salir", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);

        batch.end();

        // Lógica para seleccionar opciones del menú
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            enMenu = false;  // Iniciar el juego
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();  // Salir del juego
        }
    }

    private void actualizarJuego() {
        shape.setProjectionMatrix(camera.combined);  // Proyección con la cámara
        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);
        if (ball.estaQuieto()) {
            ball.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
        } else ball.update();
        if (ball.getY() < 0) {
            vidas--;
            ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
        }
        if (vidas <= 0) {
            vidas = 3;
            nivel = 1;
            crearBloques(2 + nivel);
        }
        if (blocks.size() == 0) {
            nivel++;
            crearBloques(2 + nivel);
            ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
        }
        for (Block b : blocks) {
            b.draw(shape);
            ball.checkCollision(b);
        }
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.destroyed) {
                puntaje++;
                blocks.remove(b);
                i--;
            }
        }
        ball.checkCollision(pad);
        ball.draw(shape);
        shape.end();
        dibujaTextos();
    }

    @Override
    public void resize(int width, int height) {
        // Ajustar la cámara cuando se cambia el tamaño de la ventana
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

    public void dibujaTextos() {
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
        img.dispose();  // Liberar la textura del menú
    }

    // Clase interna PauseMenu
    class PauseMenu {
        private SpriteBatch batch;
        private BitmapFont font;

        public PauseMenu(SpriteBatch batch, BitmapFont font) {
            this.batch = batch;
            this.font = font;
        }

        public void showPauseMenu() {
            // Mostrar el menú de pausa
            batch.begin();

            // Dibujar las opciones de pausa
            font.getData().setScale(1.5f);
            font.setColor(1, 1, 1, 1);  // Color blanco
            font.draw(batch, "1. Volver al Juego", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100);
            font.draw(batch, "2. Volver al Menu", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150);
            font.draw(batch, "3. Salir", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);

            batch.end();
        }

        public int handleInput() {
            // Lógica para seleccionar opciones del menú de pausa
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                return 1;  // Volver al juego
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                return 2;  // Volver al menú principal
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                return 3;  // Salir del juego
            }
            return 0;  // No se ha seleccionado nada
        }
    }
}
