package gameLogics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BoardImpl implements Board{

    private static final int NEAR_DISTANCE = 1;
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

    public Set<Box> getNearBox(Box selectedBox) {
        final Set<Box> set = new HashSet<>();
        final Pair<Integer, Integer> selectedBoxPos = selectedBox.getPosition();
        for(Box box : this.boxSet) {
            if(this.isNear(selectedBoxPos, box.getPosition())) {
                set.add(box);
            }
        }

        return set;
    }

    public int size() {
        return boxSet.size();
    }

    public Iterator<Box> iterator() {
        return boxSet.iterator();
    }

    private boolean isNear(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        return !pos1.equals(pos2) &&
                Math.abs(pos1.getX() - pos2.getX()) <= NEAR_DISTANCE &&
                Math.abs(pos1.getY() - pos2.getY()) <= NEAR_DISTANCE;
    }
}
