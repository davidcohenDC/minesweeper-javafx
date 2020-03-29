package timer;

/**
 * A Timer.
 * <p>
 * This class describes a constant value increment in a certain {@link Verse}.
 */
public interface Timer extends Runnable {

    /**
     * @return Returns the Timer's current value.
     */
    int getValue();

    /**
     * Pauses the Timer.
     */
    void pause();

    /**
     * Starts back the Timer if it was paused. <br>
     * Otherwise does nothing.
     */
    void play();

    /**
     * Checks if the Timer is on hold.
     * 
     * @return Returns a boolean which is true if the Timer is currently paused.
     */
    boolean isPaused();

    /**
     * Stops the Timer.
     */
    void stopTimer();

    /**
     * Starts the Timer.
     */
    void startTimer();
}
