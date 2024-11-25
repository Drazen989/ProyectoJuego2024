package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Renderable, Collidable {
    private int xSpeed, ySpeed; // Velocidad en X y Y
    private Color color = Color.ORANGE; // Color de la pelota
    private boolean estaQuieto; // Indica si la pelota está quieta
    private boolean adjuntoAPaleta;
    private Paddle paleta;

    public PingBall(int x, int y, int size, Paddle paleta) {
        super(x, y, size, size); // Inicializa la posición y el tamaño
        this.xSpeed = 4; // Velocidad inicial en X
        this.ySpeed = -4; // Velocidad inicial en Y
        this.estaQuieto = true; // La pelota comienza quieta
        this.adjuntoAPaleta = true;
        this.paleta = paleta;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x + width / 2, y + height / 2, width / 2); // Dibuja la pelota como un círculo
    }

    @Override
    public void update() {
        if (adjuntoAPaleta) {
            x = paleta.getX() + paleta.getWidth() / 2 - width / 2;
            y = paleta.getY() + paleta.getHeight();
            return;
        }

        if (estaQuieto) return; // Si la pelota está quieta, no se mueve

        // Actualizar posición
        x += xSpeed;
        y += ySpeed;

        // Rebote en los bordes de la pantalla
        if (x < 0 || x + width > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed; // Cambia la dirección en X
        }
        if (y + height > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed; // Cambia la dirección en Y
        }
    }

    public void liberar() {
        if (adjuntoAPaleta) {
            adjuntoAPaleta = false;
            estaQuieto = false;
        }
    }

    public void adjuntarAPaleta() {
        adjuntoAPaleta = true;
        estaQuieto = true;
    }


    @Override
    public boolean checkCollision(GameObject other) {
        // Detecta colisiones con otro objeto
        boolean collision = x < other.getX() + other.getWidth() &&
            x + width > other.getX() &&
            y < other.getY() + other.getHeight() &&
            y + height > other.getY();

        if (collision && other instanceof Paddle) {
            bounceVertical(); // Rebota verticalmente si colisiona con la paleta
        } else if (collision && other instanceof Block) {
            bounceVertical(); // Rebota verticalmente si colisiona con un bloque
        }
        return collision;
    }

    public void bounceVertical() {
        this.ySpeed = -this.ySpeed; // Cambia la dirección vertical
    }

    public void bounceHorizontal() {
        this.xSpeed = -this.xSpeed; // Cambia la dirección horizontal
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEstaQuieto(boolean estaQuieto) {
        this.estaQuieto = estaQuieto;
    }

    public boolean estaQuieto() {
        return estaQuieto;
    }

    @Override
    public boolean isStill() {
        return estaQuieto; // Indica si la pelota está quieta
    }
}
