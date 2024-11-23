package puppy.code;

public abstract class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Template Method
    public final void updateGameObject() {
        preUpdate();
        update();
        postUpdate();
    }

    // Este método es común a todas las clases, no se modifica
    protected void preUpdate() {
        // Código común si es necesario antes de cada actualización
    }

    // Método abstracto que las subclases implementan
    protected abstract void update();

    // Este método es común a todas las clases, no se modifica
    protected void postUpdate() {
        // Código común si es necesario después de cada actualización
    }

    // Métodos Getters y Setters...
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
