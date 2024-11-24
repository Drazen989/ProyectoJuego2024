package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {
    private float x, y; // Coordenadas de la explosión
    private Texture texture; // Textura de la explosión
    private boolean active; // Indica si la explosión está activa
    private float duration; // Duración de la explosión en segundos
    private float elapsedTime; // Tiempo transcurrido desde que comenzó la explosión

    public Explosion(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.active = true;
        this.duration = 1.0f; // Duración de 1 segundo por defecto
        this.elapsedTime = 0;
    }

    public void draw(SpriteBatch batch, float deltaTime) {
        if (active) {
            elapsedTime += deltaTime;
            if (elapsedTime > duration) {
                active = false; // Marcar la explosión como inactiva
            } else {
                batch.draw(texture, x, y, 50, 50); // Dibujar la explosión
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}
