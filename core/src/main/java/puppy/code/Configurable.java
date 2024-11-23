package puppy.code;

public interface Configurable {
    void applyConfig();

    // Método template para aplicar configuraciones
    default void configure() {
        preConfig();
        applyConfig();
        postConfig();
    }

    // Opcional, código común antes de aplicar configuración
    default void preConfig() {}

    // Opcional, código común después de aplicar configuración
    default void postConfig() {}
}
