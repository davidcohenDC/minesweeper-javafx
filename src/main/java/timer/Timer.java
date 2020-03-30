package timer;

public interface Timer {

    /**
     * @return Returns the <i>milliseconds</i> passed from the start of the Timer.
     */
    long getValue();

    /**
     * Starts the Timer.
     */
    void start();

    /**
     * Stops the Timer.
     */
    void stop();

    /**
     * @return Returns {@code True} is a Timer is not on hold.
     */
    boolean isRunning();
}
