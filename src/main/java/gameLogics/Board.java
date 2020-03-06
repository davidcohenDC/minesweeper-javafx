package gameLogics;

import java.util.List;

/**
 * Interface for Board managing
 */
public interface Board {

    /**
     * @param coord represent the coord of the box
     * @return the box in position coord
     */
    Box getBox(Pair<Integer, Integer> coord);

    /**
     * @param box represent the box
     * @return a list of boxes near the passed box
     */
    List<Box> getNearBox(Box box);
}
