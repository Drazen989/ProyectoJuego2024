package puppy.code;

public class GameStateManager {

    // Enum que define los estados posibles del juego
    public enum GameState {
        MENU,
        INSTRUCCIONES,
        JUGANDO,
        PAUSA,
        GAME_OVER,
        VICTORY,
        TRANSICION_NIVEL
    }

    // Variable para almacenar el estado actual
    private GameState currentState;

    // Constructor que establece el estado inicial en MENU
    public GameStateManager() {
        currentState = GameState.MENU;
    }

    // Método para obtener el estado actual
    public GameState getCurrentState() {
        return currentState;
    }

    // Método para cambiar el estado del juego
    public void setState(GameState newState) {
        currentState = newState;
    }

    // Métodos para verificar el estado actual
    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isInstrucciones() {
        return currentState == GameState.INSTRUCCIONES;
    }

    public boolean isJugando() {
        return currentState == GameState.JUGANDO;
    }

    public boolean isPausa() {
        return currentState == GameState.PAUSA;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }

    public boolean isVictory() {
        return currentState == GameState.VICTORY;
    }

    public boolean isTransicionNivel() {
        return currentState == GameState.TRANSICION_NIVEL;
    }
}
