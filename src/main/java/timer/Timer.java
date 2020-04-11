package timer;

/**
 * A Timer that can go upwards and downwards.
 * <p>
 * This Timer will count the time passed from its initial start excluding the
 * time that passes when it is paused.
 */
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

    /**
     * @return Returns the Timer's limit.
     */
    int getLimit();
}
