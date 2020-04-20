package gamelogics;

/**
 * Interface for box managing.
 */
public interface Box {

    /**
     * Set or unset the flag on the {@link Box}.
     */
    void setFlag();

    /**
     * Set the {@link Box} as clicked.
     */
    void hit();

    /**
     * Get the position X Y of the {@link Box} in {@link Board}.
     * 
     * @return The position of the {@link Box}
     */
    Pair<Integer, Integer> getPosition();

    /**
     * Check if the {@link Box} contains a bomb.
     * 
     * @return True if the {@link Box} contains a bomb
     */
    boolean containsBomb();

    /**
     * Check if the {@link Box} is clicked.
     * 
     * @return True if the {@link Box} is clicked
     */
    boolean isClicked();

    /**
     * Check if the {@link Box} is flagged.
     * 
     * @return True if the {@link Box} is flagged
     */
    boolean isFlagged();

    /**
     * Set the number of near bombs.
     * 
     * @param bombNear
     *                     The number of near bomb
     */
    void setBombNear(int bombNear);

    /**
     * Get the number of near bombs.
     * 
     * @return the number of near bombs
     */
    int getBombNear();
}
