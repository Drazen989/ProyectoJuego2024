package puppy.code;

public interface Collidable {
    /**
     * Verifica si hay colisión con otro objeto.
     *
     * @param other Objeto con el que se verifica la colisión.
     * @return true si hay colisión, false de lo contrario.
     */
    boolean checkCollision(GameObject other);
}
