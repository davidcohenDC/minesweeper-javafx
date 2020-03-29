package timer;

/**
 * A Display constantly showing a {@link Timer}'s value.
 */
public interface TimerView {

    /**
     * Shows the timer's value in real time.
     */
    void showTimer();

    /**
     * Stops showing the Timer.
     */
    void stopShowing();
}
