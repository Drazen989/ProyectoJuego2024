package puppy.code;

import java.util.ArrayList;

public class LevelManager {
    private int currentLevel;

    public LevelManager() {
        this.currentLevel = 1; // Comienza en el nivel 1
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void nextLevel() {
        currentLevel++; // Incrementa el nivel
    }

    public void resetLevels() {
        currentLevel = 1; // Reinicia los niveles
    }

    public void configurarNivel(int level, GameInitializer initializer, ArrayList<Block> blocks, Paddle pad, PingBall ball) {
        // Actualizar configuraciones globales usando el Singleton
        GameConfigManager.getInstance().updateConfigForLevel(level);

        // Aplicar las configuraciones actualizadas al Paddle y la PingBall
        pad.applyConfig();
        ball.applyConfig();

        // Configuración de bloques
        int filas = 2 + level; // Incrementar el número de filas de bloques
        initializer.inicializarBloques(filas, blocks);
    }
}
