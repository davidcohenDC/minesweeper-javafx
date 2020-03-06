package gameLogics;

public class BoxImpl implements Box{

    private final Pair<Integer, Integer> coord;
    private boolean flag = false;
    private boolean withBomb = false;
    private boolean clicked = false;

    public BoxImpl(Pair<Integer, Integer> coord) {
        this.coord = coord;
    }

    public BoxImpl(Pair<Integer, Integer> coord, boolean withBomb) {
        this.coord = coord;
        this.withBomb = withBomb;
    }

    public void setFlag() {
        this.flag = true;
    }

    public void hit() {
        this.clicked = true;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.coord;
    }

    public boolean containsBomb() {
        return this.withBomb;
    }

    public boolean isClicked() {
        return this.clicked;
    }

}
