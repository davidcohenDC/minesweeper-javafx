package gameLogics;

/**
 * Interface for box managing
 * */
public interface Box {

    /**
     * set/unset the flag on the box
     * */
    void setFlag();

    /**
     * set the box as clicked
     */
    void hit();

    /**
     * check if the box contains a bomb
     * */
    boolean containsBomb();

    /**
     * check if the box is clicked
     * */
    boolean isClicked();
}
