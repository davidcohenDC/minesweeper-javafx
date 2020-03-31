package timer;

/**
 * A Class designed to run two Timers one at a time.
 * <p>
 * Extends {@link MultipleTimers}.
 */
public interface DoubleTimer extends MultipleTimers {

    /**
     * @return The {@link Timer} associated with Player one.
     */
    Timer getPlayer1Timer();

    /**
     * @return The {@link Timer} associated with Player two.
     */
    Timer getPlayer2Timer();

}
