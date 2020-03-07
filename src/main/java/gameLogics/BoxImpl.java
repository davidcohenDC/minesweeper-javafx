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

    @Override
    public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Box other = (Box) obj;
            if (this.coord.getX() == null) {
                if (other.getPosition().getX() != null) {
                    return false;
                }
            } else if (!this.coord.getX().equals(other.getPosition().getX())) {
                return false;
            }
            if (this.coord.getY() == null) {
                return other.getPosition().getY() == null;
            } else return this.coord.getY().equals(other.getPosition().getY());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.coord.getX() == null) ? 0 : this.coord.getX().hashCode());
        result = prime * result + ((this.coord.getY() == null) ? 0 : this.coord.getY().hashCode());
        return result;
    }
}
