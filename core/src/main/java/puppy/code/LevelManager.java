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
        if (currentLevel < 3) {
            currentLevel++; // Incrementa el nivel
        }
    }

    public void resetLevels() {
        currentLevel = 1; // Reinicia los niveles
    }

    public void configurarNivel(int level, GameInitializer initializer, ArrayList<Block> blocks, Paddle pad, PingBall ball) {
        int filas = 2 + level; // Incrementa el número de filas
        initializer.inicializarBloques(filas, blocks);

        // Configurar tamaño y velocidad de la paleta
        int paddleWidth = Math.max(60, 100 - (level * 10)); // Paleta más pequeña con cada nivel
        int paddleSpeed = 10 + (level * 2); // Incrementar velocidad de la paleta

        pad.setSize(paddleWidth, 10); // Cambia el tamaño
        pad.setSpeed(paddleSpeed); // Cambia la velocidad

        // Configurar velocidad de la bola
        int ballSpeedX = 5 + level;
        int ballSpeedY = 7 + level;
        ball.setSpeed(ballSpeedX, ballSpeedY);
    }
}
