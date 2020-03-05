package timer;

public interface Timer extends Runnable {
    /**
     * @return
     * returns the timers value
     */
    int getValue();

    /**
     * Pauses the Timer.
     */
    void pause();

    /**
     * Starts back the timer if it was paused.
     */
    void unPause();

    /**
     * Checks if the timer is on hold.
     * @return
     * returns a boolean which is true if the timer is currently paused 
     */
    boolean isPaused();
}
