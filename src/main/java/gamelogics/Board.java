package gamelogics;

import java.util.Set;

/**
 * Interface for Board managing.
 */
public interface Board extends Iterable<Box> {

    /**
     * Obtain board width.
     * 
     * @return Returns the {@link Board}'s width
     */
    int getWidth();

    /**
     * Obtain board height.
     * 
     * @return Returns the {@link Board}'s height
     */
    int getHeight();

    /**
     * @param coord
     *                  The coordinates of the box
     * @return Returns the {@link Box} in that coordinates
     */
    Box getBox(Pair<Integer, Integer> coord);

    /**
     * @param selectedBox
     *                        The {@link Box} to get the nears
     * @return Returns a list of boxes near the passed {@link Box}
     */
    Set<Box> getNearBox(Box selectedBox);

    /**
     * @return Returns the number of {@link Box} in {@link Board}
     */
    int size();
}
