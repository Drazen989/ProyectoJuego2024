// GameObjectFactory.java
package puppy.code;

public interface GameObjectFactory {
    IBlock createBlock(int x, int y, int width, int height);
    IPaddle createPaddle(int x, int y, int width, int height);
    IPingBall createPingBall(int x, int y, int size);
    IItem createItem(int x, int y, int width, int height, BounceStrategy strategy);
}
