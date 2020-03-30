package gamelogics;

/**
 * Interface for box managing.
 * */
public interface Box {

    /**
     * set/unset the flag on the box.
     * */
    void setFlag();

    /**
     * set the box as clicked.
     */
    void hit();

    /**
     * get the position X Y of the box in board.
     * @return the position of the box
     */
    Pair<Integer, Integer> getPosition();

    /**
     * check if the box contains a bomb.
     * @return true if the box contains a bomb
     * */
    boolean containsBomb();

    /**
     * check if the box is clicked.
     * @return true if the box is clicked
     * */
    boolean isClicked();

    /**
     * check if the box is flagged.
     * @return true if the box is flagged
     */
    boolean isFlagged();
}
