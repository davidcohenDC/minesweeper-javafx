package gamelogics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The implementation of {@link Board}.
 */
public class BoardImpl implements Board {

    private static final int NEAR_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 1;

    private final int width;
    private final int height;
    private final Set<Box> boxSet;

    public BoardImpl(final int width, final int height, final Set<Box> boxSet) {
        this.boxSet = boxSet;
        this.width = width;
        this.height = height;
    }

    @Override
    public final int getWidth() {
        return this.width;
    }

    @Override
    public final int getHeight() {
        return this.height;
    }

    @Override
    public final Box getBox(final Pair<Integer, Integer> coord) {
        for (final Box box : boxSet) {
            if (box.getPosition().equals(coord)) {
                return box;
            }
        }
        throw new NoSuchElementException("Box not found");
    }

    @Override
    public final Set<Box> getNearBox(final Box selectedBox) {
        final Set<Box> set = new HashSet<>();
        set.add(selectedBox);
        final Pair<Integer, Integer> selectedBoxPos = selectedBox.getPosition();
        for (final Box box : this.boxSet) {
            if (this.isNear(selectedBoxPos, box.getPosition())) {
                set.add(box);
            }
        }

        return set;
    }

    @Override
    public final int size() {
        return boxSet.size();
    }

    @Override
    public final Iterator<Box> iterator() {
        return boxSet.iterator();
    }

    @Override
    public final String toString() {
        String boxString = "";
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                boxString = boxString + this.getBox(new Pair<>(i, j)).toString() + " ";
            }
            boxString = boxString + "\n";
        }

        return "width=" + width
                + ", height=" + height
                + ", board=\n" + boxString;
    }

    /**
     * Control if 2 coordinates are near without consider the diagonal.
     * @param pos1 first position to compare
     * @param pos2 second position to compare
     * @return true if 2 coordinates are near, false otherwise
     */
    private boolean isNear(final Pair<Integer, Integer> pos1, final Pair<Integer, Integer> pos2) {
        return !pos1.equals(pos2) && Math.abs(pos1.getX() - pos2.getX()) == NEAR_DISTANCE - DIAGONAL_DISTANCE
                && Math.abs(pos1.getY() - pos2.getY()) <= NEAR_DISTANCE
                ||
                !pos1.equals(pos2) && Math.abs(pos1.getX() - pos2.getX()) == NEAR_DISTANCE
                        && Math.abs(pos1.getY() - pos2.getY()) <= NEAR_DISTANCE - DIAGONAL_DISTANCE;
    }
}
