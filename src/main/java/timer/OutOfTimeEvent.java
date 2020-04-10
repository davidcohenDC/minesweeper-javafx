package timer;

import java.util.EventObject;

/**
 * This class represents a particular {@link EventObject}.
 * <p>
 * This event should occur if a {@link Timer} reaching its limit results in a
 * change of the game's course.
 */
public class OutOfTimeEvent extends EventObject {

    /**
     * The {@link OutOfTimeEvent} serial version's UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This constructor will create the event like in {@link EventObject}.
     * 
     * @param source
     *                   The {@link TimerView} that creates the Event.
     */
    public OutOfTimeEvent(final TimerView source) {
        super(source);
    }

}
