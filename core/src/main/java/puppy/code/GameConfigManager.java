package puppy.code;

public class GameConfigManager {

    // Instancia única
    private static GameConfigManager instance;

    // Configuraciones para Paddle
    private int paddleSpeed = 10; // Velocidad inicial
    private int paddleWidth = 100; // Ancho inicial

    // Configuraciones para PingBall
    private int ballXSpeed = 5; // Velocidad inicial en X
    private int ballYSpeed = 7; // Velocidad inicial en Y

    private GameConfigManager() {
        // Constructor privado para evitar instancias externas
    }

    public static GameConfigManager getInstance() {
        if (instance == null) {
            instance = new GameConfigManager();
        }
        return instance;
    }

    // Métodos para actualizar configuraciones con base en el nivel
    public void updateConfigForLevel(int level) {
        // Incrementar velocidad de la Paddle y PingBall
        paddleSpeed += level;
        paddleWidth = Math.max(50, paddleWidth - level * 5); // Reducir tamaño con un mínimo de 50

        ballXSpeed += level;
        ballYSpeed += level;
    }

    // Getters para las configuraciones
    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getBallXSpeed() {
        return ballXSpeed;
    }

    public int getBallYSpeed() {
        return ballYSpeed;
    }
}
