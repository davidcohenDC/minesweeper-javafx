package gameLogics;

import java.util.Set;

/**
 * Interface for Board managing
 */
public interface Board extends Iterable<Box> {

    /**
     * @param coord represent the coord of the box
     * @return the box in position coord
     */
    Box getBox(Pair<Integer, Integer> coord);

    /**
     * @param selectedBox represent the box
     * @return a list of boxes near the passed box
     */
    Set<Box> getNearBox(Box selectedBox);

    /**
     * @return the number of box in board
     */
    int size();
}
