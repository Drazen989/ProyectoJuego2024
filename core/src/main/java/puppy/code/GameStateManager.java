package puppy.code;

public class GameStateManager {
    public enum GameState {
        MENU,
        JUGANDO,
        PAUSA
    }

    private GameState currentState;

    public GameStateManager() {
        currentState = GameState.MENU;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setState(GameState newState) {
        currentState = newState;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isJugando() {
        return currentState == GameState.JUGANDO;
    }

    public boolean isPausa() {
        return currentState == GameState.PAUSA;
    }
}
