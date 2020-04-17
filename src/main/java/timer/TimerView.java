package timer;

import graphicsutility.TimeEventsListener;

/**
 * A Class to show a {@link Timer} on a Label in real time.
 * <p>
 * TimerView should automatically set the designated Label's characteristics and
 * refresh its values accordingly to the Time's values without interfering with
 * the GUI's reactiveness.
 */
public interface TimerView {

    /**
     * Starts refreshing the Timer's value on screen.
     */
    void startDisplaying();

    /**
     * Stops refreshing the Timer's value on screen.
     */
    void stopDisplaying();

    /**
     * Sets a listener to take action if a {@link OutOfTimeEvent} occurs.
     * 
     * @param listener
     *                     the listener to add.
     */
    void setTimeEventListener(TimeEventsListener listener);
}
