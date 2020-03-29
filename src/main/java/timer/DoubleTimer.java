package timer;
/**
 * A controller of two {@link Timer} that makes sure they run one at a time.
 * <p>
 * This interface extends {@link Runnable}.
 */
public interface DoubleTimer extends Runnable {

    /**
     * Switches which timer is running and which timer is on hold.
     */
    void switchTurn();

    /**
     * @return
     * Returns the {@link Timer} assigned to player 1.
     */
    Timer getPlayer1Timer();

    /**
     * @return
     * Returns the {@link Timer} assigned to player 2.
     */
    Timer getPlayer2Timer();

    /**
     * Starts the Timers.
     * <p>
     * player1 will begin.
     * <br>
     * player2 will be on hold waiting for his turn.
     */
    void startTimers();
}
