// LevelManager.java
package puppy.code;

import java.util.ArrayList;

public class LevelManager {
    private int currentLevel;

    public LevelManager() {
        this.currentLevel = 1;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void nextLevel() {
        currentLevel++;
    }

    public void resetLevels() {
        currentLevel = 1;
    }

    public void configurarNivel(int level, GameInitializer initializer, ArrayList<IBlock> blocks, IPaddle pad, IPingBall ball) {
        GameConfigManager.getInstance().updateConfigForLevel(level);
        pad.applyConfig();
        ball.applyConfig();

        // Configuración de bloques
        int filas = 2 + level;
        initializer.inicializarBloques(filas, blocks);

        // Cambiar la estrategia de rebote según el nivel
        switch (level) {
            case 1:
                ball.setBounceStrategy(new NormalBounceStrategy());
                break;
            case 2:
                ball.setBounceStrategy(new RandomBounceStrategy());
                break;
            case 3:
                ball.setBounceStrategy(new AcceleratedBounceStrategy());
                break;
            default:
                ball.setBounceStrategy(new NormalBounceStrategy());
                break;
        }
    }
}
