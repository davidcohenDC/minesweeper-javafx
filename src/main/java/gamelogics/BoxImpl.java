package gamelogics;

public class BoxImpl implements Box {

    private final Pair<Integer, Integer> coord;
    private final boolean withBomb;
    private boolean flag;
    private boolean clicked;

    public BoxImpl(final Pair<Integer, Integer> coord) {
        this.coord = coord;
        this.withBomb = false;
        this.flag = false;
        this.clicked = false;
    }

    public BoxImpl(final Pair<Integer, Integer> coord, final boolean withBomb) {
        this.coord = coord;
        this.withBomb = withBomb;
        this.flag = false;
        this.clicked = false;
    }

    @Override
    public final void setFlag() {
        if (!this.isClicked()) {
            this.flag = !this.isFlagged();
        }
    }

    @Override
    public final void hit() {
        if (!this.isFlagged()) {
            this.clicked = true;
        }
    }

    @Override
    public final Pair<Integer, Integer> getPosition() {
        return this.coord;
    }

    @Override
    public final boolean containsBomb() {
        return this.withBomb;
    }

    @Override
    public final boolean isClicked() {
        return this.clicked;
    }

    @Override
    public final boolean isFlagged() {
        return this.flag;
    }

    @Override
    public final boolean equals(final Object obj) {
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
            } else {
                return this.coord.getY().equals(other.getPosition().getY());
            }
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.coord.getX() == null) ? 0 : this.coord.getX().hashCode());
        result = prime * result + ((this.coord.getY() == null) ? 0 : this.coord.getY().hashCode());
        return result;
    }
}
