package gameLogics;

public class BombSystemImpl implements BombSystem {

    private final int bombNumber;

    public BombSystemImpl(int bombNumber) {
        this.bombNumber = bombNumber;
    }

    public boolean next() {
        return false;
    }
}
