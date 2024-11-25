package puppy.code;

public class GameConfigManager {
    private static GameConfigManager instance;

    private int paddleSpeed = 10;
    private int paddleWidth = 100;

    private int ballXSpeed = 5;
    private int ballYSpeed = 7;

    private GameConfigManager() {}

    public static GameConfigManager getInstance() {
        if (instance == null) {
            instance = new GameConfigManager();
        }
        return instance;
    }

    public void updateConfigForLevel(int level) {
        paddleSpeed += level;
        paddleWidth = Math.max(50, paddleWidth - level * 5);

        ballXSpeed += level;
        ballYSpeed += level;
    }

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
