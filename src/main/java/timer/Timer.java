package timer;

/**
 * A Timer.
 */
public interface Timer extends Runnable {

    /**
     * @return
     * returns the timers current value
     */
    int getValue();

    /**
     * Pauses the Timer.
     */
    void pause();

    /**
     * Starts back the timer if it was paused.
     * 
     * Otherwise does nothing.
     */
    void play();

    /**
     * Checks if the timer is on hold.
     * @return
     * returns a boolean which is true if the timer is currently paused 
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
