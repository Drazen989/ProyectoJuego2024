package puppy.code;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class LevelManager {
    private Texture normalTexture;
    private Texture resistantTexture;
    private Texture explosiveTexture;
    private List<Block> blocks; // Necesitas mantener una referencia a los bloques

    public void initializeTextures() {
        normalTexture = new Texture("assets/block7.jpeg");
        resistantTexture = new Texture("assets/block8.jpeg");
        explosiveTexture = new Texture("assets/block1.jpeg");
    }

    public void configurarNivel(int nivel, GameInitializer gameInitializer, Paddle pad, PingBall ball) {
        // Limpiar los bloques actuales
        blocks.clear();
        // Inicializar los bloques para el nivel actual
        gameInitializer.inicializarBloques(blocks, normalTexture, resistantTexture, explosiveTexture);
    }


    public void disposeTextures() {
        if (normalTexture != null) normalTexture.dispose();
        if (resistantTexture != null) resistantTexture.dispose();
        if (explosiveTexture != null) explosiveTexture.dispose();
    }

    public void triggerExplosion(Block explodedBlock) {
        for (Block block : blocks) {
            if (!block.isDestroyed() && esAdyacente(explodedBlock, block)) {
                block.setDestroyed(true);
            }
        }
    }

    public LevelManager(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    private boolean esAdyacente(Block b1, Block b2) {
        int distanciaX = Math.abs(b1.getX() - b2.getX());
        int distanciaY = Math.abs(b1.getY() - b2.getY());

        return (distanciaX == b1.getWidth() && distanciaY == 0) || (distanciaX == 0 && distanciaY == b1.getHeight());
    }
}
