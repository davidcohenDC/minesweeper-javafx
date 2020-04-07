package gamelogics;

/**
 * It creates a list of boolean where true represents bomb and false not.
 */
public interface BombGenerator {

    /**
     * @return Returns a boolean, true value represents a bomb, false value not
     *         represents bomb.
     */
    boolean next();
}
