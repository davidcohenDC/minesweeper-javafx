package graphics;

/**
 * The tile Interface.
 * <p>
 * This interface manage the tile<br>
 *</p>
 */
public interface Tile {

    /**
     * Set the mine.
     *
     */
    void setMine();

    /**
     * Disable the {@link Tile}.
     *
     */
    void setDisable();

    /**
     * @return the x value
     */
    int getX();

    /**
     * @return the y value
     */
    int getY();

    /**
     * @return the value of {@link Tile}
     */
    int getValue();

    /**
     * Set the flag.
     */
    void setFlag();

    /**
     * Check if the flag is on.
     * 
     * @return the boolean value of flag
     */
    Boolean isFlagged();

    /**
     * Set the Value of the {@link Tile}.
     *
     * @param value
     *              the value of {@link Tile}
     *
     */
    void setValue(final int value);

    /**
     * Add style to {@link Tile} depending on the value.
     *
     * @param value
     *              the value of {@link Tile}
     *
     */
    void setStyle(final int value);

    /**
     * Set the effect of {@link Tile}.
     *
     */
    void setEffect();

    /**
     * Set the audio click of {@link Tile}.
     *
     */
    void audioClick();

    /**
     * Set the secondary audio click of {@link Tile}.
     *
     */
    void audioBigClick();

    /**
     * Set the audio of add flag of {@link Tile}.
     *
     */
    void audioAddFlag();

    /**
     * Set the audio remove flag of {@link Tile}.
     *
     */
    void audioRemoveFlag();

}
