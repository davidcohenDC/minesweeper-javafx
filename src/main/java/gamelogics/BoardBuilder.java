package gamelogics;

import java.util.Set;

/**
 * Interface for building Board.
 */
public interface BoardBuilder {

    /**
     * Set the {@link Board} width.
     * 
     * @param w
     *              The width of the {@link Board}.
     * @return {@link BoardBuilder}
     */
    BoardBuilder withWidth(int w);

    /**
     * Set the {@link Board} height.
     * 
     * @param h
     *              height of the {@link Board}
     * @return {@link BoardBuilder}
     */
    BoardBuilder withHeight(int h);

    /**
     * Add a {@link Box} to {@link Board}.
     * 
     * @param box
     *                {@link Box} to add in {@link Board}
     * @return {@link BoardBuilder}
     */
    BoardBuilder addBox(Box box);

    /**
     * Add a {@link Box} of list to {@link Board}.
     * 
     * @param boxSet
     *                   Set of {@link Box} to add in {@link Board}
     * @return {@link BoardBuilder}
     */
    BoardBuilder addBoxSet(Set<Box> boxSet);

    /**
     * Build the {@link Board}.
     * 
     * @return A builded {@link Board}
     */
    Board build();
}
