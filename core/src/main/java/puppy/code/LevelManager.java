package puppy.code;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class LevelManager {
    private Texture normalTexture;
    private Texture resistantTexture;
    private Texture explosiveTexture;

    public void initializeTextures() {
        normalTexture = new Texture("assets/block7.jpeg");
        resistantTexture = new Texture("assets/block8.jpeg");
        explosiveTexture = new Texture("assets/block1.jpeg");
    }

    public void configurarNivel(int nivel, GameInitializer gameInitializer, List<Block> blocks, Paddle pad, PingBall ball) {
        // Configurar bloques en la pantalla según el nivel.
        gameInitializer.inicializarBloques(blocks, normalTexture, resistantTexture, explosiveTexture);
    }

    public void disposeTextures() {
        if (normalTexture != null) normalTexture.dispose();
        if (resistantTexture != null) resistantTexture.dispose();
        if (explosiveTexture != null) explosiveTexture.dispose();
    }

    public void triggerExplosion(Block block) {
        // Implementar lógica de explosión.
    }
}
