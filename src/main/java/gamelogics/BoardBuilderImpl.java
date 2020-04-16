package gamelogics;

import java.util.HashSet;
import java.util.Set;

/**
 * The implementation of {@link BoardBuilder}.
 */
public class BoardBuilderImpl implements BoardBuilder {

    private static final int NEAR_DISTANCE = 1;

    private int width;
    private int height;
    private final Set<Box> boxSet = new HashSet<>();

    @Override
    public final BoardBuilder withWidth(final int w) {
        this.width = w;
        return this;
    }

    @Override
    public final BoardBuilder withHeight(final int h) {
        this.height = h;
        return this;
    }

    @Override
    public final BoardBuilder addBox(final Box box) {
        this.boxSet.add(box);
        return this;
    }

    @Override
    public final BoardBuilder addBoxSet(final Set<Box> boxSet) {
        this.boxSet.addAll(boxSet);
        return this;
    }

    @Override
    public final Board build() {
        for (final Box box : this.boxSet) {
            box.setBombNear(getBombNear(box));
        }
        return new BoardImpl(this.width, this.height, this.boxSet);
    }

    /**
     * Count the bomb near a box.
     * 
     * @param selectedBox
     *                        the box to search near bombs
     * @return the number of bombs near a box
     */
    private int getBombNear(final Box selectedBox) {
        final Pair<Integer, Integer> selectedBoxPos = selectedBox.getPosition();
        int count = 0;
        for (final Box box : this.boxSet) {
            if (this.isNear(selectedBoxPos, box.getPosition()) && box.containsBomb()) {
                count++;
            }
        }

        return count;
    }

    /**
     * Control if 2 coordinates are near considering also the diagonal.
     * 
     * @param pos1
     *                 first position to compare
     * @param pos2
     *                 second position to compare
     * @return true if 2 coordinates are near, false otherwise
     */
    private boolean isNear(final Pair<Integer, Integer> pos1, final Pair<Integer, Integer> pos2) {
        return !pos1.equals(pos2) && Math.abs(pos1.getX() - pos2.getX()) <= NEAR_DISTANCE
                && Math.abs(pos1.getY() - pos2.getY()) <= NEAR_DISTANCE;
    }
}
