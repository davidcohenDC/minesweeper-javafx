package gameLogics;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class BoardBuilderImpl implements BoardBuilder {

    private int width;
    private int height;
    private final Set<Box> boxSet = new HashSet<>();

    public BoardBuilder setWidth(int w) {
        this.width = w;
        return this;
    }

    public BoardBuilder setHeight(int h) {
        this.height = h;
        return this;
    }

    public BoardBuilder addBox(Box box) {
        this.boxSet.add(box);
        return this;
    }

    public BoardBuilder addBoxSet(Set<Box> boxSet) {
        this.boxSet.addAll(boxSet);
        return this;
    }

    public Board build() {
        return new BoardImpl(this.boxSet);
    }
}
