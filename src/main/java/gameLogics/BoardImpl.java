package gameLogics;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class BoardImpl implements Board{

    private final Set<Box> boxSet;

    public BoardImpl(Set<Box> boxSet) {
        this.boxSet = boxSet;
    }

    public Box getBox(Pair<Integer, Integer> coord) {
        for(Box box : boxSet) {
            if(box.getPosition().equals(coord)) {
                return box;
            }
        }
        throw new NoSuchElementException("Box not found");
    }

    public List<Box> getNearBox(Box box) {
        return null;
    }

    public Iterator<Box> iterator() {
        return boxSet.iterator();
    }
}
