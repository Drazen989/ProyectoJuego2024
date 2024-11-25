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
        batch.begin();
        font.draw(batch, "Juego en Pausa", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "1. Reanudar Juego", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "2. Volver al Menú Principal", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);
        font.draw(batch, "3. Salir del Juego", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 100);
        batch.end();
    }

    public boolean isPaused() {
        return Gdx.input.isKeyPressed(Input.Keys.P);
    }
}
