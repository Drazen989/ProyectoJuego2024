// ClassicGameObjectFactory.java
package puppy.code;

public class ClassicGameObjectFactory implements GameObjectFactory {

    @Override
    public IBlock createBlock(int x, int y, int width, int height) {
        return new ClassicBlock(x, y, width, height);
    }

    @Override
    public IPaddle createPaddle(int x, int y, int width, int height) {
        return new ClassicPaddle(x, y, width, height);
    }

    @Override
    public IPingBall createPingBall(int x, int y, int size) {
        return new ClassicPingBall(x, y, size);
    }

    @Override
    public IItem createItem(int x, int y, int width, int height, BounceStrategy strategy) {
        return new ClassicItem(x, y, width, height, strategy);
    }
}
