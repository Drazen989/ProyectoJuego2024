// ClassicBlock.java
package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random; // Importamos la clase Random para generar números aleatorios

public class ClassicBlock extends GameObject implements IBlock {
    private Color color;
    private boolean destroyed;

    public ClassicBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.destroyed = false;

        // Generamos un color aleatorio para cada bloque
        Random r = new Random(x + y); // Semilla basada en la posición para obtener variedad
        this.color = new Color(
            0.1f + r.nextFloat() * 0.9f, // Componente rojo (R) entre 0.1 y 1.0
            0.1f + r.nextFloat() * 0.9f, // Componente verde (G) entre 0.1 y 1.0
            0.1f + r.nextFloat() * 0.9f, // Componente azul (B) entre 0.1 y 1.0
            1f                              // Opacidad (A) al máximo
        );
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }

    @Override
    public void update() {
        // No necesita lógica de actualización
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
