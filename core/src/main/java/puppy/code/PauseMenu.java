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
        this.font = new BitmapFont();
    }

    public void showPauseMenu() {
        font.draw(batch, "Press P to Resume", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
    }

    public boolean isPaused() {
        return Gdx.input.isKeyPressed(Input.Keys.P);
    }
}
