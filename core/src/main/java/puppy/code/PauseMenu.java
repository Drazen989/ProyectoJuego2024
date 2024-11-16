package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private BitmapFont font;
    private SpriteBatch batch;

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            return 1;  // Volver al juego
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            return 2;  // Volver al menú principal
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            return 3;  // Salir del juego
        }
        return 0;  // No se ha seleccionado nada
    }

}
