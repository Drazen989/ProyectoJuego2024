package puppy.code;

import com.badlogic.gdx.Gdx;

public abstract class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Template Method
    public void updateGameObject() {
        preUpdate();
        update();
        clampToWindowBounds(); // Nuevo método para limitar a los bordes de la ventana
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

    protected void clampToWindowBounds() {
        // Asegurar que no se salga por el borde izquierdo
        if (x < 0) {
            x = 0;
        }
        // Asegurar que no se salga por el borde derecho
        if (x + width > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - width;
        }
        // Asegurar que no se salga por el borde superior
        if (y + height > Gdx.graphics.getHeight()) {
            y = Gdx.graphics.getHeight() - height;
        }
        // Asegurar que no se salga por el borde inferior
        if (y < 0) {
            y = 0;
        }
    }




    // Métodos Getters y Setters...
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }


}
